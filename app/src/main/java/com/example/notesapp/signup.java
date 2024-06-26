package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class signup extends AppCompatActivity {

    private EditText msignUpEmail, msignUpPassword, msignUpName;
    private AppCompatButton msignUpbtn;
    private TextView mgotologin;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        msignUpEmail = findViewById(R.id.signUpEmail);
        msignUpPassword = findViewById(R.id.signUpPassword);
        msignUpName = findViewById(R.id.signUpName);
        msignUpbtn = findViewById(R.id.signUp_btn);
        mgotologin = findViewById(R.id.gotoLogin);
        firebaseAuth = FirebaseAuth.getInstance();


        mgotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        msignUpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = msignUpEmail.getText().toString().trim();
                String name = msignUpName.getText().toString().trim();
                String password = msignUpPassword.getText().toString().trim();

                if (mail.isEmpty() || password.isEmpty() || name.isEmpty()){
                    Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                }
                else if(password.length() < 7){
                    Toast.makeText(getApplicationContext(), "Password should be more than 7 characters", Toast.LENGTH_SHORT).show();
                }
                else{
                    //register user to fire base
                    firebaseAuth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                                sendEmailVerification();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), " Failed to register", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    //send email verification
    private void sendEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!= null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getApplicationContext(), "Verification Email Sent", Toast.LENGTH_SHORT).show();
                    firebaseAuth.signOut();
                    finish();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            });
        }
        else{
                    Toast.makeText(getApplicationContext(), "Verification Email Not Sent", Toast.LENGTH_SHORT).show();
        }
    }



}