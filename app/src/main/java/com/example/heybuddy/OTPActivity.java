package com.example.heybuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.heybuddy.databinding.ActivityOtpactivityBinding;
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

import java.util.concurrent.TimeUnit;

public class OTPActivity extends AppCompatActivity {

    private ActivityOtpactivityBinding binding;
    private FirebaseAuth auth;
    public String verificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();

        binding.otpBox1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    binding.otpBox2.requestFocus();
                } else {
                    binding.otpBox1.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.otpBox2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    binding.otpBox3.requestFocus();
                } else {
                    binding.otpBox1.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.otpBox3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    binding.otpBox4.requestFocus();
                } else {
                    binding.otpBox2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.otpBox4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    binding.otpBox5.requestFocus();
                } else {
                    binding.otpBox3.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.otpBox5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    binding.otpBox6.requestFocus();
                } else {
                    binding.otpBox4.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.otpBox6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    binding.otpBox6.requestFocus();
                } else {
                    binding.otpBox5.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        String phoneNumber = getIntent().getStringExtra("mobileNumber");
        this.verificationId = getIntent().getStringExtra("verificationId");
        setVerificationId(this.verificationId);

        binding.resendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.otpBox1.setText("");
                binding.otpBox2.setText("");
                binding.otpBox3.setText("");
                binding.otpBox4.setText("");
                binding.otpBox5.setText("");
                binding.otpBox6.setText("");

                mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(OTPActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String verificationId,
                                           @NonNull PhoneAuthProvider.ForceResendingToken token) {
                        setVerificationId(verificationId);
                        Toast.makeText(OTPActivity.this, "Resent the OTP", Toast.LENGTH_SHORT).show();
                    }
                };

                PhoneAuthOptions options =
                        PhoneAuthOptions.newBuilder(auth)
                                .setPhoneNumber(phoneNumber)
                                .setTimeout(60L, TimeUnit.SECONDS)
                                .setActivity(OTPActivity.this)
                                .setCallbacks(mCallbacks)
                                .build();
                PhoneAuthProvider.verifyPhoneNumber(options);
            }
        });

        binding.verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredOtp = binding.otpBox1.getText().toString()
                        + binding.otpBox2.getText().toString()
                        + binding.otpBox3.getText().toString()
                        + binding.otpBox4.getText().toString()
                        + binding.otpBox5.getText().toString()
                        + binding.otpBox6.getText().toString();

                String verID = getVerificationId();
                Log.d("enteredOtp",enteredOtp);
                Log.d("verID",verID);

                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verID, enteredOtp);
                FirebaseAuth.getInstance().signInWithCredential(credential).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        binding.verify.setVisibility(View.INVISIBLE);
                        binding.progressBar.setVisibility(View.VISIBLE);
                        if(task.isSuccessful()){
                            Intent intent = new Intent(OTPActivity.this,MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                    Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }else{
                            binding.progressBar.setVisibility(View.GONE);
                            binding.verify.setVisibility(View.VISIBLE);
                            Toast.makeText(OTPActivity.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    public void setVerificationId(String verificationId){
        this.verificationId = verificationId;
    }

    public String getVerificationId(){
        return verificationId;
    }
}