package com.think2exam.projectt2e.ui.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.think2exam.projectt2e.MainActivity;
import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.adapters.EditProfileAdapter;
import com.think2exam.projectt2e.modals.EditProfileModel;
import com.think2exam.projectt2e.modals.UserModel;
import com.think2exam.projectt2e.utilities.DBOperations;
import com.think2exam.projectt2e.utilities.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.think2exam.projectt2e.Constants.CORRECT_ANS;
import static com.think2exam.projectt2e.Constants.EMAIL_ID;
import static com.think2exam.projectt2e.Constants.ERROR;
import static com.think2exam.projectt2e.Constants.ERRORS;
import static com.think2exam.projectt2e.Constants.FIRST_NAME;
import static com.think2exam.projectt2e.Constants.ID;
import static com.think2exam.projectt2e.Constants.IMAGE;
import static com.think2exam.projectt2e.Constants.LAST_NAME;
import static com.think2exam.projectt2e.Constants.NO_ANS;
import static com.think2exam.projectt2e.Constants.PHONE_NO;
import static com.think2exam.projectt2e.Constants.TOTAL_MATCHES;
import static com.think2exam.projectt2e.Constants.TOTAL_POINTS;
import static com.think2exam.projectt2e.Constants.WINS;
import static com.think2exam.projectt2e.Constants.WRONG_ANS;

public class EditProfileActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditProfileAdapter profileAdapter;
    ArrayList<EditProfileModel> editProfileModels;
    User user = User.getInstance();
    ProgressDialog progressDialog;
    String fname = null,lname = null,email = null;
    ImageView editImage;
    CircleImageView Image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try {
            getSupportActionBar().setTitle("Edit Profile");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (Exception e){
            e.printStackTrace();
        }

        Image = findViewById(R.id.image);
        editImage = findViewById(R.id.edit_image);
        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionDialog();
            }
        });

        recyclerView = findViewById(R.id.edit_profile_recycler_view);
        setModels();
        setAdapter();

    }

    public void optionDialog(){

            final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choose your profile picture");
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (options[item].equals("Take Photo")) {
                        Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(takePicture, 0);

                    } else if (options[item].equals("Choose from Gallery")) {
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pickPhoto , 1);

                    } else if (options[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!=RESULT_CANCELED){
            switch (requestCode){
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        Image.setImageBitmap(selectedImage);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage =  data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                Image.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                        }

                    }
                    break;
            }
        }
    }

    public void setModels(){
        editProfileModels = new ArrayList<>();
        editProfileModels.add(new EditProfileModel(R.drawable.ic_person_black_24dp,R.string.profile_fname,user.fname));
        editProfileModels.add(new EditProfileModel(R.drawable.ic_person_black_24dp,R.string.profile_lname,user.lname));
        editProfileModels.add(new EditProfileModel(R.drawable.ic_email_black_24dp,R.string.profile_email,user.email));
        editProfileModels.add(new EditProfileModel(R.drawable.outline_call_black_48,R.string.profile_phone,user.phoneNo));
    }

    public void setAdapter(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        profileAdapter = new EditProfileAdapter(editProfileModels, this, new EditProfileAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                dialog(pos);
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(profileAdapter);

    }

    public void dialog(final int position){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.edit_profile_dialog);
        dialog.setCancelable(true);
        WindowManager.LayoutParams wm = new WindowManager.LayoutParams();

        try {
            wm.copyFrom(dialog.getWindow().getAttributes());
            wm.width = WindowManager.LayoutParams.MATCH_PARENT;
            wm.height = WindowManager.LayoutParams.WRAP_CONTENT;

        }catch(NullPointerException e){
            System.out.println(e.toString());
        }

        TextView itemName = dialog.findViewById(R.id.dialog_name);
        final EditText itemField = dialog.findViewById(R.id.dialog_field);
        TextView cancel = dialog.findViewById(R.id.dialog_cancel);
        TextView save = dialog.findViewById(R.id.dialog_save);
        itemName.setText(getResources().getString(editProfileModels.get(position).getItemName()));
        itemField.setText(editProfileModels.get(position).getItemField());
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkValidity(position, itemField.getText().toString().trim())) {
                    saveItem(position, itemField.getText().toString().trim());
                    dialog.cancel();
                }else {
                    Toast.makeText(EditProfileActivity.this, "Invalid "+getString(editProfileModels.get(position).getItemName()), Toast.LENGTH_SHORT).show();
                }
            }
        });


        dialog.show();
        dialog.getWindow().setAttributes(wm);
    }

    public boolean checkValidity(int position,String field){
        boolean ans = false;
        if(position==0 || position==1){
            if(field.length()!=0)
                ans = true;
            else
                ans = false;
        }else {
            if(field.contains("@"))
                ans = true;
            else
                ans = false;
        }
        return ans;
    }


    public void saveItem(int position,String field){
        if(position==0){
            fname = field;
            lname = user.lname;
            email = user.email;

        }else if(position==1){
            fname = user.fname;
            lname = field;
            email = user.email;

        }else if(position == 2){
            fname = user.fname;
            lname = user.lname;
            email = field;
        }
        setProgressDialog(position);
        new HTTPHandler().execute(fname,lname,email);

    }

    private class HTTPHandler extends AsyncTask<String,Void,Void>{
        JSONObject jsonObject;
        @Override
        protected Void doInBackground(String... strings) {
            DBOperations dbOperations = DBOperations.getInstance();
            jsonObject = dbOperations.updateProfile(strings[0],strings[1],strings[2],user.phoneNo);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(progressDialog!=null)
                progressDialog.dismiss();
            try {
                if(jsonObject!=null && !jsonObject.getBoolean(ERROR)){
                    user.setFname(fname);
                    user.setLname(lname);
                    user.setEmail(email);
                    setModels();
                    setAdapter();
                    saveUserToSharedPref();
                    Toast.makeText(EditProfileActivity.this, "Profile Successfully Saved", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(EditProfileActivity.this, "OOPS! Some Error Occurred, may be your Internet connection down", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void setProgressDialog(int pos){
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(getResources().getString(editProfileModels.get(pos).getItemName()));
        progressDialog.setMessage("Saving...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }


    public UserModel convertToUserModel() {
        UserModel userModel = null;

        userModel = new UserModel(user.id,
                user.fname,
                user.lname,
                user.phoneNo,
                user.email,
                user.image,
                Integer.parseInt(user.totalMatches),
                Integer.parseInt(user.totalPoints),
                Integer.parseInt(user.wins),
                Integer.parseInt(user.correctAns),
                Integer.parseInt(user.wrongAns),
                Integer.parseInt(user.noAns));

        return userModel;
    }

    public void saveUserToSharedPref(){
        UserModel userModel;
        userModel = convertToUserModel();
        SharedPreferences Prefs= getSharedPreferences("user",MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = Prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userModel);
        prefsEditor.putString("user_details", json);
        prefsEditor.apply();
    }




    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
