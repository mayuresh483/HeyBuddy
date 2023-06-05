package com.example.heybuddy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.example.heybuddy.Models.Users;
import com.example.heybuddy.databinding.ActivitySignInBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class SignInActivity extends AppCompatActivity {
    private ActivitySignInBinding binding;
    private ProgressDialog progressDialog;
    private FirebaseAuth auth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private GoogleSignInOptions googleSignInOptions;
    private GoogleSignInClient googleSignInClient;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        progressDialog = new ProgressDialog(SignInActivity.this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Login to your account");

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        binding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                if (binding.signup.getText().toString().equals("Login In")) {
                    auth.signInWithEmailAndPassword(binding.emailIp.getEditableText().toString(), binding.passwordField.getEditableText().toString()).
                            addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        progressDialog.hide();
                                        Toast.makeText(SignInActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                                Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    } else {
                                        progressDialog.hide();
                                        Toast.makeText(SignInActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                        @Override
                        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {

                        }

                        @Override
                        public void onVerificationFailed(@NonNull FirebaseException e) {
                            Toast.makeText(SignInActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCodeSent(@NonNull String verificationId,
                                               @NonNull PhoneAuthProvider.ForceResendingToken token) {
                            Intent intent = new Intent(SignInActivity.this, OTPActivity.class);
                            intent.putExtra("verificationId", verificationId);
                            intent.putExtra("mobileNumber", binding.phoneNumber.getEditableText().toString().trim());
                            startActivity(intent);
                        }
                    };

                    String mobnumber = binding.phoneNumber.getEditableText().toString();
                    mobnumber = mobnumber.replaceAll("\\s+", "");

                    PhoneAuthOptions options =
                            PhoneAuthOptions.newBuilder(auth)
                                    .setPhoneNumber(mobnumber)
                                    .setTimeout(60L, TimeUnit.SECONDS)
                                    .setActivity(SignInActivity.this)
                                    .setCallbacks(mCallbacks)
                                    .build();
                    PhoneAuthProvider.verifyPhoneNumber(options);
                }
            }
        });

        binding.signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        binding.emailIp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    if (Character.isDigit(s.charAt(0))) {
                        binding.emailIp.setVisibility(View.GONE);
                        binding.emailLayout.setVisibility(View.GONE);
                        binding.passwordLayout.setVisibility(View.GONE);
                        binding.passwordField.setVisibility(View.GONE);
                        binding.phoneNumber.setVisibility(View.VISIBLE);
                        binding.phoneNumberLayout.setVisibility(View.VISIBLE);
                        binding.phoneNumber.setText("+91 " + s);
                        binding.signup.setText("Get OTP");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("+") && start == 0) {
                    binding.phoneNumber.setVisibility(View.GONE);
                    binding.phoneNumberLayout.setVisibility(View.GONE);
                    binding.emailLayout.setVisibility(View.VISIBLE);
                    binding.emailIp.setVisibility(View.VISIBLE);
                    binding.passwordLayout.setVisibility(View.VISIBLE);
                    binding.passwordField.setVisibility(View.VISIBLE);
                    binding.emailIp.setText("");
                    binding.passwordField.setText("");
                    binding.signup.setText("LOGIN IN");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().
                build();

        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);

        binding.googleIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (progressDialog != null) progressDialog.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 65){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());

            } catch (ApiException e) {
                Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void SignIn(){
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent,65);
    }

    private void firebaseAuthWithGoogle(String idToken){
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = auth.getCurrentUser();
                            Users users = new Users();
                            users.setUserId(user.getUid());
                            users.setUserEmail(user.getEmail());
                            users.setProfilepic(user.getPhotoUrl().toString());
                            users.setUserName(user.getDisplayName());

                            database.getReference().child("Users").child(user.getUid()).setValue(users);
                            Intent intent = new Intent(SignInActivity.this,MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            Toast.makeText(SignInActivity.this, "User Login Successful", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(SignInActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}