package com.mat.firenote.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mat.firenote.MainActivity;
import com.mat.firenote.R;

public class Login extends AppCompatActivity {

    EditText lEmail,lPassword;
    Button loginNow;
    TextView forgetPass,createAcc;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Login");

        lEmail = findViewById(R.id.email);
        lPassword = findViewById(R.id.lPassword);
        loginNow = findViewById(R.id.loginBtn);

        forgetPass = findViewById(R.id.forgotPasword);
        createAcc = findViewById(R.id.createAccount);

        fAuth = FirebaseAuth.getInstance();

        loginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mEmail = lEmail.getText().toString();
                String mPassword = lPassword.getText().toString();

                if (mEmail.isEmpty() || mPassword.isEmpty()){
                    Toast.makeText(Login.this, "Fields Are Required", Toast.LENGTH_SHORT).show();
                    return;
                }

                fAuth.signInWithEmailAndPassword(mEmail,mPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(Login.this, "Success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this, "Login Failed. " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

    }
}
