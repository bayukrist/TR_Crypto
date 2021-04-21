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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private TextView txtViewRegister;
    private Button btnLogin;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try {
            this.getSupportActionBar().hide();
        }catch (NullPointerException e){}

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtViewRegister = findViewById(R.id.txtViewRegister);

        auth = FirebaseAuth.getInstance();
        txtViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this , RegisterActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void loginUser(){
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            if(!password.isEmpty()){
                auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(LoginActivity.this, R.string.message1, Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent2);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, R.string.message2, Toast.LENGTH_SHORT).show();
                    }
                });
            }else {
                editTextPassword.setError(getText(R.string.message3));
            }
        }else if(email.isEmpty()){
            editTextEmail.setError(getText(R.string.message3));
        }else {
            editTextEmail.setError(getText(R.string.message4));
        }

    }

}
