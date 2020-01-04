package com.think2exam.projectt2e.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.ContentLoadingProgressBar;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.think2exam.projectt2e.R;

public class QuizActivity extends AppCompatActivity {

    RelativeLayout layoutOptionOne;
    RelativeLayout layoutOptionTwo;
    RelativeLayout layoutOptionThree;
    RelativeLayout layoutOptionFour;

    AppCompatTextView optionOneText;
    AppCompatTextView optionTwoText;
    AppCompatTextView optionThreeText;
    AppCompatTextView optionFourText;
    AppCompatTextView pointsCounter;
    AppCompatTextView questionCounter;

    public int points = 0;
    int counter = 0;
    AppCompatTextView questionView;

    private ContentLoadingProgressBar quizTimer;
    private Thread timerThread;

    private String[] questions = new String[10];
    Options[] options = new Options[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Toolbar toolbar = findViewById(R.id.toolbar_quiz);

        try{

            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Quiz");

        }catch(NullPointerException ex){

            ex.printStackTrace();

        }

        initializeQuizLayout();
        setQuestions();
        setNextQuestion();
        setListeners();

    }

    private void initializeQuizLayout() {
        layoutOptionOne = findViewById(R.id.option_1_btn);
        optionOneText = findViewById(R.id.option_1);
        layoutOptionTwo = findViewById(R.id.option_2_btn);
        optionTwoText = findViewById(R.id.option_2);
        layoutOptionThree = findViewById(R.id.option_3_btn);
        optionThreeText = findViewById(R.id.option_3);
        layoutOptionFour = findViewById(R.id.option_4_btn);
        optionFourText = findViewById(R.id.option_4);
        questionView = findViewById(R.id.quiz_question);
        quizTimer = findViewById(R.id.quiz_timer);
        pointsCounter = findViewById(R.id.quiz_point_counter_text);
        questionCounter = findViewById(R.id.quiz_question_number);
        String point = "+"+points+"XP";
        String questionNo = "Question: "+(counter+1)+"/10";
        pointsCounter.setText(point);
        questionCounter.setText(questionNo);

    }

    private void setListeners() {

        layoutOptionOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleResponse(layoutOptionOne,optionOneText,0);
            }
        });

        layoutOptionTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleResponse(layoutOptionTwo,optionTwoText,1);
            }
        });

        layoutOptionThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleResponse(layoutOptionThree,optionThreeText,2);
            }
        });

        layoutOptionFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleResponse(layoutOptionFour,optionFourText,3);
            }
        });
    }

    private void handleResponse(RelativeLayout layout,AppCompatTextView text,int index) {

        if(counter == 9){
            timerThread.interrupt();
        }

        if(index == options[counter].correctIndex){
            layout.setBackground(getResources().getDrawable(R.drawable.quiz_answer_correct));
            //text.setTextColor(getResources().getColor(R.color.answer_correct));
            points += 10;
            String point = "+"+points+"XP";
            pointsCounter.setText(point);

        }else{
            layout.setBackground(getResources().getDrawable(R.drawable.quiz_answer_wrong));
            //text.setTextColor(getResources().getColor(R.color.answer_wrong));
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

        timerThread.interrupt();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(counter == 9){
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
                setNextQuestion();
            }
        },2000);
    }

    private void viewCorrectAnswer() {

        switch(options[counter].correctIndex){
            case 0:
                highlightAnswer(layoutOptionOne,optionOneText);
                break;
            case 1:
                highlightAnswer(layoutOptionTwo,optionTwoText);
                break;
            case 2:
                highlightAnswer(layoutOptionThree,optionThreeText);
                break;
            case 3:
                highlightAnswer(layoutOptionFour,optionFourText);
                break;
            default:
                Toast.makeText(this,"Default options",Toast.LENGTH_LONG).show();
        }
    }

    private void highlightAnswer(RelativeLayout layout, AppCompatTextView text) {
        layout.setBackground(getResources().getDrawable(R.drawable.quiz_answer_correct));
        //text.setTextColor(getResources().getColor(R.color.answer_correct));
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

        layoutOptionOne.setClickable(true);
        layoutOptionOne.setFocusable(true);
        layoutOptionTwo.setClickable(true);
        layoutOptionTwo.setFocusable(true);
        layoutOptionThree.setClickable(true);
        layoutOptionThree.setFocusable(true);
        layoutOptionFour.setClickable(true);
        layoutOptionFour.setFocusable(true);

        layoutOptionOne.setBackground(getResources().getDrawable(R.drawable.quiz_option_button));
        //optionOneText.setTextColor(getResources().getColor(R.color.black));

        layoutOptionTwo.setBackground(getResources().getDrawable(R.drawable.quiz_option_button));
        //optionTwoText.setTextColor(getResources().getColor(R.color.black));

        layoutOptionThree.setBackground(getResources().getDrawable(R.drawable.quiz_option_button));
        //optionThreeText.setTextColor(getResources().getColor(R.color.black));

        layoutOptionFour.setBackground(getResources().getDrawable(R.drawable.quiz_option_button));
        //optionFourText.setTextColor(getResources().getColor(R.color.black));

        questionView.setText(questions[counter]);

        optionOneText.setText(options[counter].option1);
        optionTwoText.setText(options[counter].option2);
        optionThreeText.setText(options[counter].option3);
        optionFourText.setText(options[counter].option4);

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
                            if(counter == 9){
                                onDestroy();
                                return;
                            }
                            timerThread.interrupt();
                            counter++;
                            setNextQuestion();
                        }
                    }
                });
            }
        }

    }
}
