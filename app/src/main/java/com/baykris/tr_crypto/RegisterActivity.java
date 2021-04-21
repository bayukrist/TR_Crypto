package com.baykris.tr_crypto;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegisterActivity extends AppCompatActivity {
    private EditText edittxtRegisterEmail, edittxtRegisterPassword, edittxtConfirmPassword;
    private Button btnRegister;
    private TextView textViewLogin;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        try {
            this.getSupportActionBar().hide();
        }catch (NullPointerException e){}

        edittxtRegisterEmail = findViewById(R.id.edittxtRegisterEmail);
        edittxtRegisterPassword =  findViewById(R.id.edittxtRegisterPassword);
        edittxtConfirmPassword = findViewById(R.id.edittxtComfirmPassword);
        textViewLogin = findViewById(R.id.textViewLogin);
        btnRegister = findViewById(R.id.btnRegister);

        auth = FirebaseAuth.getInstance();

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this , LoginActivity.class);
                startActivity(intent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });
    }

    private void createUser(){
        String email = edittxtRegisterEmail.getText().toString();
        String password = edittxtRegisterPassword.getText().toString();
        String password2 = edittxtConfirmPassword.getText().toString();

        if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length() >= 8 && password.equals(password2)){
            if(!password.isEmpty()){
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(RegisterActivity.this, R.string.message5, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this , LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, R.string.message6, Toast.LENGTH_SHORT).show();
                    }
                });
            }else {
                edittxtRegisterPassword.setError(getText(R.string.message3));
            }
        }else if(email.isEmpty()){
            edittxtRegisterEmail.setError(getText(R.string.message3));
        }else if(!password.equals(password2)){
            Toast.makeText(RegisterActivity.this, R.string.message7, Toast.LENGTH_SHORT).show();
        }else if(password.length() < 8){
            edittxtRegisterPassword.setError(getText(R.string.message8));
        }
        else {
            edittxtRegisterEmail.setError(getText(R.string.message4));
        }

    }
}
