package com.think2exam.projectt2e.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.ContentLoadingProgressBar;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.modals.QuestionModel;
import com.think2exam.projectt2e.utilities.DBOperations;
import com.think2exam.projectt2e.utilities.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import static com.think2exam.projectt2e.Constants.*;


public class QuizActivity extends AppCompatActivity {

    private final User user = User.getInstance();
    RelativeLayout layoutOptionFive;

    RelativeLayout layoutOptionOne;
    RelativeLayout layoutOptionTwo;
    RelativeLayout layoutOptionThree;
    RelativeLayout layoutOptionFour;
    AppCompatTextView optionFiveText;

    AppCompatTextView optionOneText;
    AppCompatTextView optionTwoText;
    AppCompatTextView optionThreeText;
    AppCompatTextView optionFourText;
    int wrongAns = 0;
    AppCompatTextView pointsCounter;
    AppCompatTextView questionCounter;

    ProgressBar progressBar;

    ArrayList<QuestionModel> questionsModels;

    public int points = 0;
    int counter = 0;
    AppCompatTextView descriptionView;

    AppCompatTextView questionView;
    private boolean error = false;

    private ContentLoadingProgressBar quizTimer;
    private Thread timerThread;

    private String[] questions = new String[10];
    Options[] options = new Options[10];

    Vibrator vibrator;
    MediaPlayer buzzer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HTTPHandler handler = new HTTPHandler();
        Intent intent = getIntent();
        int catId = 0;
        int subId = 0;
        int paraId = 0;
        if(intent != null){
            catId = intent.getIntExtra(QUIZ_CATEGORY_ID,0);
            subId = intent.getIntExtra(QUIZ_SUBJECT_ID,0);
            paraId = intent.getIntExtra(QUIZ_PARA_ID,1);
        }
        if(questionsModels != null){
            setContentView(R.layout.activity_quiz);
        }else{
            setContentView(R.layout.loading);
            handler.execute(String.valueOf(catId),String.valueOf(subId),String.valueOf(paraId));
        }

    }

    private void getQuestions(JSONArray array) {
        questionsModels = new ArrayList<>();
        if(array !=null){
            try {
                for(int i = 0; i< array.length(); i++){
                    JSONObject object = array.getJSONObject(i);
                    questionsModels.add(new QuestionModel(
                            object.getString(QUIZ_QUESTION),
                            object.getString(QUIZ_OPTION_1),
                            object.getString(QUIZ_OPTION_2),
                            object.getString(QUIZ_OPTION_3),
                            object.getString(QUIZ_OPTION_4),
                            object.getString(QUIZ_OPTION_5),
                            Integer.parseInt(object.getString(QUIZ_ANSWER_KEY)),
                            object.getString(QUIZ_ANSWER_DES)
                    ));
                    //System.out.println(questionsModels.get(i).question);
                }
            }catch(JSONException ex){
                error = true;
                ex.printStackTrace();
            }

        }
    }

    private void initializeQuizLayout() {

        Toolbar toolbar = findViewById(R.id.toolbar_quiz);


        try{
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Quiz");
        }catch(NullPointerException ex){
            ex.printStackTrace();
        }

        layoutOptionOne = findViewById(R.id.option_1_btn);
        optionOneText = findViewById(R.id.option_1);
        layoutOptionTwo = findViewById(R.id.option_2_btn);
        optionTwoText = findViewById(R.id.option_2);
        layoutOptionThree = findViewById(R.id.option_3_btn);
        optionThreeText = findViewById(R.id.option_3);
        layoutOptionFour = findViewById(R.id.option_4_btn);
        optionFourText = findViewById(R.id.option_4);
        layoutOptionFive = findViewById(R.id.option_5_btn);
        optionFiveText = findViewById(R.id.option_5);
        questionView = findViewById(R.id.quiz_question);
        descriptionView = findViewById(R.id.quiz_ans_des);
        quizTimer = findViewById(R.id.quiz_timer);
        pointsCounter = findViewById(R.id.quiz_point_counter_text);
        questionCounter = findViewById(R.id.quiz_question_number);
        String point = "+"+points+"XP";
        String questionNo = "Question: "+(counter+1)+"/10";
        pointsCounter.setText(point);
        questionCounter.setText(questionNo);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        buzzer = MediaPlayer.create(this,R.raw.buzzer_sfx);
    }

    private void setListeners() {

        layoutOptionOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleResponse(layoutOptionOne,0);
            }
        });

        layoutOptionTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleResponse(layoutOptionTwo,1);
            }
        });

        layoutOptionThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleResponse(layoutOptionThree,2);
            }
        });

        layoutOptionFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleResponse(layoutOptionFour,3);
            }
        });

        layoutOptionFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleResponse(layoutOptionFive,4);
            }
        });
    }

    private void handleResponse(RelativeLayout layout,int index) {

        if(counter == questionsModels.size()-1){
            timerThread.interrupt();
        }

        if(index == questionsModels.get(counter).answerKey-1){
            layout.setBackground(getResources().getDrawable(R.drawable.quiz_answer_correct));
            points += 10;
            String point = "+"+points+"XP";
            pointsCounter.setText(point);

        }else{
            wrongAns++;
            if(Build.VERSION.SDK_INT >= 26){
                assert vibrator != null;
                vibrator.vibrate(VibrationEffect.createOneShot(200,VibrationEffect.DEFAULT_AMPLITUDE));
            }else{
                assert vibrator != null;
                vibrator.vibrate(200);
            }
            buzzer.start();
            if (layout != null){
                layout.setBackground(getResources().getDrawable(R.drawable.quiz_answer_wrong));
            }
            viewCorrectAnswer();
        }

        final Handler handler = new Handler();

        layoutOptionOne.setClickable(false);
        layoutOptionOne.setFocusable(false);
        layoutOptionTwo.setClickable(false);
        layoutOptionTwo.setFocusable(false);
        layoutOptionThree.setClickable(false);
        layoutOptionThree.setFocusable(false);
        layoutOptionFour.setClickable(false);
        layoutOptionFour.setFocusable(false);
        layoutOptionFive.setClickable(false);
        layoutOptionFive.setFocusable(false);

        timerThread.interrupt();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(counter == questionsModels.size()-1){
                    user.setTotalMatches(1);
                    user.setTotalPoints(points);
                    user.setAvgPoints();
                    user.setCorrectAns(points/10);
                    user.setWrongAns(wrongAns);
                    user.setNoAns(10-(points/10)-wrongAns);
                    if (points>=50){
                        user.setWins(1);
                    }
                    Intent intent = new Intent(getApplicationContext(),QuizResultsActivity.class);
                    intent.putExtra("score",points);
                    startActivity(intent);
                    try {
                        finish();
                    }catch (Throwable ex){
                        ex.printStackTrace();
                    }
                    return;
                }
                counter++;
                if (counter < questionsModels.size()){
                    setNextQuestion();
                }

            }
        },(index == questionsModels.get(counter).answerKey-1)?2000:8000);
    }

    private void viewCorrectAnswer() {

        descriptionView.setText(questionsModels.get(counter).description);

        switch(questionsModels.get(counter).answerKey-1){
            case 0:
                highlightAnswer(layoutOptionOne);
                break;
            case 1:
                highlightAnswer(layoutOptionTwo);
                break;
            case 2:
                highlightAnswer(layoutOptionThree);
                break;
            case 3:
                highlightAnswer(layoutOptionFour);
                break;
            case 4:
                highlightAnswer(layoutOptionFive);
                break;
            default:
                Toast.makeText(this,"option no out of range",Toast.LENGTH_LONG).show();
        }
    }

    private void highlightAnswer(RelativeLayout layout) {
        layout.setBackground(getResources().getDrawable(R.drawable.quiz_answer_correct));
    }

    private void setQuestions() {

        questions[0] = "Who Programmed the first Computer game 'Space war!' in 1962?";
        questions[1] = "Who is known as father of supercomputing?";
        questions[2] = "Who created the C programming language?";
        questions[3] = "When NASSCOM (National Association of Software and Services Companies was established?";
        questions[4] = "Who is known as father of Internet";
        questions[5] = "Which one is the first high level language?";
        questions[6] = "Which one is the first word processing application?";
        questions[7] = "Which one is the current fastest Supercomputer in India?";
        questions[8] = "India's first supercomputer PARAM 8000 was installed in";
        questions[9] = "Who developed Java programming language?";


        options[0] = new Options(
                "Steave Russel",
                "Konard Zuse",
                "Alan Emtage",
                "Tim Berners Lee",
                0);

        options[1] = new Options(
                "David J. Brown",
                "Gene Amdahl",
                "Adam Dunkels",
                "Seymour Cray",
                3);

        options[2] = new Options(
                "Ken Thomson",
                "Dennis Ritchie",
                "Robin Milner",
                "Frieder Nake",
                1);

        options[3] = new Options(
                "1988",
                "1997",
                "1993",
                "1882",
                0);

        options[4] = new Options(
                "Alan Perlis",
                "Jean E. Sammet",
                "Vint Cerf",
                "Steve Lawrence",
                2);
        options[5] = new Options(
                "C",
                "COBOL",
                "FORTRAN",
                "C++",
                2);

        options[6] = new Options(
                "MS Word",
                "Apple iWork",
                "Sun StarOffice",
                "WordStar",
                3);
        options[7] = new Options(
                "Aaditya",
                "SAGA-220",
                "SahasraT",
                "HP Apollo 6000",
                2);
        options[8] = new Options(
                "1988",
                "1991",
                "1995",
                "1882",
                1);
        options[9] = new Options(
                "James Gosling",
                "Douglas Engelbart",
                "Edmund M. Clarke",
                "James D. Foley",
                0);

    }

    @Override
    protected void onDestroy() {
        timerThread.interrupt();
        super.onDestroy();
    }

    @Override
    protected void finalize() throws Throwable {
        this.finish();
        super.finalize();
    }

    private void setNextQuestion(){

        String questionNo = "Question: "+(counter+1)+"/10";
        questionCounter.setText(questionNo);
        descriptionView.setText("");

        layoutOptionOne.setClickable(true);
        layoutOptionOne.setFocusable(true);
        layoutOptionTwo.setClickable(true);
        layoutOptionTwo.setFocusable(true);
        layoutOptionThree.setClickable(true);
        layoutOptionThree.setFocusable(true);
        layoutOptionFour.setClickable(true);
        layoutOptionFour.setFocusable(true);
        layoutOptionFive.setClickable(true);
        layoutOptionFive.setFocusable(true);
        layoutOptionOne.setBackground(getResources().getDrawable(R.drawable.quiz_option_button));

        layoutOptionTwo.setBackground(getResources().getDrawable(R.drawable.quiz_option_button));

        layoutOptionThree.setBackground(getResources().getDrawable(R.drawable.quiz_option_button));

        layoutOptionFour.setBackground(getResources().getDrawable(R.drawable.quiz_option_button));

        layoutOptionFive.setBackground(getResources().getDrawable(R.drawable.quiz_option_button));

        questionView.setText(questionsModels.get(counter).question);

        optionOneText.setText(questionsModels.get(counter).option1);
        optionTwoText.setText(questionsModels.get(counter).option2);
        optionThreeText.setText(questionsModels.get(counter).option3);
        optionFourText.setText(questionsModels.get(counter).option4);
        optionFiveText.setText(questionsModels.get(counter).option5);

        quizTimer.setVisibility(View.VISIBLE);
        timerThread = new TimerThread();
        timerThread.start();

    }

    private class Options{

        Options(String option1, String option2, String option3, String option4, int correctIndex) {
            this.option1 = option1;
            this.option2 = option2;
            this.option3 = option3;
            this.option4 = option4;
            this.correctIndex = correctIndex;
        }

        String option1;
        String option2;
        String option3;
        String option4;

        int correctIndex;

    }

    private class TimerThread extends Thread{

        final Handler handler = new Handler();

        @Override
        public void run() {
            super.run();
            final int max = 1000;
            int current = 1000;
            final int min = 0;
            quizTimer.setMax(max);

            while (current>min){
                try {
                    sleep(10);
                }catch (InterruptedException ex){
                    ex.printStackTrace();
                    return;
                }

                current = current - 1;
                quizTimer.setProgress(current);

                final int end = current;

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(end == min){
                            handleResponse(null,Integer.MAX_VALUE);
                            if(counter == questionsModels.size()-1){
                                onDestroy();
                                return;
                            }
                            if(Build.VERSION.SDK_INT >= 26){
                                assert vibrator != null;
                                vibrator.vibrate(VibrationEffect.createOneShot(200,VibrationEffect.DEFAULT_AMPLITUDE));
                            }else{
                                assert vibrator != null;
                                vibrator.vibrate(200);
                            }
                            timerThread.interrupt();
                        }
                    }
                });
            }
        }

    }

    private class HTTPHandler extends AsyncTask<String,Void,Void> {

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (error){
                setContentView(R.layout.no_questions_layout);
            }else {
                setContentView(R.layout.activity_quiz);
                initializeQuizLayout();
                setNextQuestion();
                setListeners();
            }
        }

        @Override
        protected Void doInBackground(String... strings) {

            DBOperations dbOperations = DBOperations.getInstance();
            JSONArray jsonArray = dbOperations.getQuestions(strings[0],strings[1],strings[2]);
            getQuestions(jsonArray);

            return null;
        }
    }

}


