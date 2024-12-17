package com.example.cricwar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cricwar.model.UserModel;
import com.example.cricwar.utils.FirebaseUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

public class userregistration extends AppCompatActivity {

    EditText user,email;
    Button login;
    String username;
    ProgressBar progressBar;
    String phoneNumber;
    UserModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userregistration);
        login = findViewById(R.id.loginbutton);
        user=findViewById(R.id.username);
        email=findViewById(R.id.emailname);
        progressBar=findViewById(R.id.progressBar2);
        phoneNumber =getIntent().getExtras().getString("phone");
        getUsername();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUsername();
            }
        });
    }
    void setUsername(){
        username =user.getText().toString();
        String emailname=email.getText().toString();

        if(username.isEmpty() || username.length()<3){
            user.setError("Username length should be at least 3 chars");
            return;
        }
        if(emailname.isEmpty()){
            email.setError("Please Fill Emailfield");
            return;
        }
        setInProgress(true);
        if(userModel!=null){
            userModel.setUsername(username);
            userModel.setEmail(emailname);
        }
        else{
            userModel= new UserModel(phoneNumber,username,emailname, Timestamp.now());
        }
      FirebaseUtil.currentUserDetails().set(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                setInProgress(false);
                if(task.isSuccessful()){
                    Intent i6 =new Intent(getApplicationContext(), MainActivity.class);
                    i6.putExtra("emails",emailname);
                    i6.putExtra("users",username);
                    i6.putExtra("phones",phoneNumber);
                    startActivity(i6);
                }
                else {
                    // Handle error here, for example, log the error message
                    Toast.makeText(userregistration.this, "fAIL", Toast.LENGTH_SHORT).show();
                    setInProgress(false);
                    Log.e("UserRegistration", "Failed to set user details: " + task.getException().getMessage());

                }
            }
        });
    }
     void getUsername() {
        setInProgress(true);
         FirebaseUtil.currentUserDetails().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
             @Override
             public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                 setInProgress(false);
                 if (task.isSuccessful()) {
                     userModel =task.getResult().toObject(UserModel.class);
                     if(userModel!=null){
                         user.setText(userModel.getUsername());
                         email.setText(userModel.getEmail());
                     }
                 }
             }
         });

    }

    void setInProgress(boolean inProgress) {
        if (inProgress) {
            login.setVisibility(View.GONE); // Hide verify button
            progressBar.setVisibility(View.VISIBLE); // Show progress bar
        } else {
            login.setVisibility(View.VISIBLE); // Show verify button
            progressBar.setVisibility(View.GONE); // Hide progress bar
        }
    }
}