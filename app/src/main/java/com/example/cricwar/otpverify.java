package com.example.cricwar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class otpverify extends AppCompatActivity {

    EditText e1;
    Long timeoutseconds=60L;
    Button b4;
    TextView t1;
    ProgressBar progressBar;
    String phoneNumber;
    String verificationCode;
    PhoneAuthProvider.ForceResendingToken ResendingToken;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverify);
        t1 = findViewById(R.id.resendotptimer);
        e1 = findViewById(R.id.otptext);
        b4 = findViewById(R.id.verifybutton);
        progressBar = findViewById(R.id.progressBar); // Initialize progressBar

        // Extract phone number from intent and format it with "+91" prefix
        phoneNumber = "+91" + getIntent().getStringExtra("mobile");
        Toast.makeText(this, phoneNumber, Toast.LENGTH_SHORT).show();

        // Initialize and trigger OTP sending
        sendOtp(phoneNumber, false);

        t1.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View view) {
                                      sendOtp(phoneNumber,true);
                                  }
                              }
        );
        // Set onClick listener for verify button
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Verify OTP
                String otp = e1.getText().toString().trim();
                if (!otp.isEmpty()) {
                    verifyOtp(otp);
                } else {
                    Toast.makeText(otpverify.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Method to send OTP
    void sendOtp(String phoneNumber, boolean isResend) {
        startResendTimer();
        setInProgress(true); // Show progress bar during OTP sending

        // Build PhoneAuthOptions
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber) // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this) // Activity for callback binding
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                // Auto-retrieval or instant verification completed
                                signIn(phoneAuthCredential);
                                setInProgress(false); // Hide progress bar
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                // Verification failed
                                Toast.makeText(otpverify.this, "Verification Failed", Toast.LENGTH_SHORT).show();
                                setInProgress(false); // Hide progress bar

                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                // Code sent to the provided phone number
                                super.onCodeSent(s, forceResendingToken);
                                verificationCode = s;
                                ResendingToken = forceResendingToken;
                                Toast.makeText(otpverify.this, "OTP Sent Successfully", Toast.LENGTH_SHORT).show();
                                setInProgress(false); // Hide progress bar
                            }
                        })
                        .build();

        // Initiate phone number verification
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    void startResendTimer() {
        t1.setEnabled(false);
        Timer timer =new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeoutseconds--;
                t1.setText("Resend Otp in "+timeoutseconds+"seconds");
                if(timeoutseconds<=0){
                 timeoutseconds=60L;
                 timer.cancel();
                 runOnUiThread(() ->{
                     t1.setEnabled(true);
                 });
                }
            }
        },0,1000);
    }

    // Method to verify OTP
    void verifyOtp(String otp) {
        // Create PhoneAuthCredential object
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, otp);

        // Sign in with the credential
        signIn(credential);
    }

    // Method to set UI state based on progress
    void setInProgress(boolean inProgress) {
        if (inProgress) {
            b4.setVisibility(View.GONE); // Hide verify button
            progressBar.setVisibility(View.VISIBLE); // Show progress bar
        } else {
            b4.setVisibility(View.VISIBLE); // Show verify button
            progressBar.setVisibility(View.GONE); // Hide progress bar
        }
    }

    // Method to sign in with verified phone credential
    void signIn(PhoneAuthCredential credential) {
        // Implement your sign-in logic here
        setInProgress(true);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        setInProgress(false);
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = task.getResult().getUser();
                            Intent i4 = new Intent(getApplicationContext(), userregistration.class);
                            i4.putExtra("phone", phoneNumber);
                            finishAffinity();
                            startActivity(i4);

                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }
}
