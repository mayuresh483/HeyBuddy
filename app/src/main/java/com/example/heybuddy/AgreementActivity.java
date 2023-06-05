package com.example.heybuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class AgreementActivity extends AppCompatActivity {

    private Button agreeButton;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);
        getSupportActionBar().hide();
        agreeButton = findViewById(R.id.agree_btn);
        auth = FirebaseAuth.getInstance();

        agreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AgreementActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });

        if(auth.getCurrentUser()!=null){
            Intent intent = new Intent(AgreementActivity.this, SplashScreenActivity.class);
            startActivity(intent);
        }
    }
}