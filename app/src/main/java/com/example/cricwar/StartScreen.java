package com.example.cricwar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cricwar.utils.FirebaseUtil;

public class StartScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(FirebaseUtil.isloggedIn()) {
                    Intent ihome = new Intent(StartScreen.this, MainActivity.class);
                    startActivity(ihome);
                }
                else{
                    Intent ihome = new Intent(StartScreen.this, startlogin.class);
                    startActivity(ihome);
                }
                finish();
                }
        },3000);
    }
}