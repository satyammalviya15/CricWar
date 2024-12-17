package com.example.cricwar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;

import com.google.android.material.textfield.TextInputEditText;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextInputEditText editText = findViewById(R.id.editText);
        CheckBox checkBox = findViewById(R.id.checkBox);
        Button button = findViewById(R.id.button);
        ImageButton b1 = findViewById(R.id.imagebutton11);
        Button b2 =findViewById(R.id.button);
        b2.setOnClickListener(view -> {
            Intent i3 = new Intent(getApplicationContext(), otpverify.class);
            String mobileNumber = editText.getText().toString().replaceAll("\\D", ""); // Remove non-numeric characters
            i3.putExtra("mobile", mobileNumber);
            startActivity(i3);
        });

        b1.setOnClickListener(view -> {
            Intent i2 = new Intent(getApplicationContext(), startlogin.class);
            startActivity(i2);
        });

// Set initial state of the Button
        button.setEnabled(false);
        button.setBackgroundColor(getResources().getColor(R.color.grey)); // Set initial color to grey

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                // Check if the EditText is not empty
                if (editable.toString().trim().length() > 9 && checkBox.isChecked()) {
                    button.setEnabled(true);
                    button.setBackgroundColor(getResources().getColor(R.color.green)); // Set color to green
                } else {
                    button.setEnabled(false);
                    button.setBackgroundColor(getResources().getColor(R.color.grey)); // Set color to grey
                }
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                // Check if the CheckBox is checked and EditText is not empty
                if (isChecked && editText.getText().toString().trim().length() > 9) {
                    button.setEnabled(true);
                    button.setBackgroundColor(getResources().getColor(R.color.green)); // Set color to green
                } else {
                    button.setEnabled(false);
                    button.setBackgroundColor(getResources().getColor(R.color.grey)); // Set color to grey
                }
            }
        });
    }
}