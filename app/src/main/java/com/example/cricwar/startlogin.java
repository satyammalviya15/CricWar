package com.example.cricwar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class startlogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startlogin);
        Button b1 = findViewById(R.id.button2);
        b1.setOnClickListener(view -> {
            Intent i2 = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i2);
        });
        }
    }