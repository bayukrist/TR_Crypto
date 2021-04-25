package com.baykris.tr_crypto;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class RegisterActivity extends AppCompatActivity {
    private EditText edittxtRegisterFullName, edittxtRegisterUsername, edittxtRegisterEmail, edittxtRegisterPassword, edittxtConfirmPassword,edittxtRegisterPhone;
    private Button btnRegister;
    private TextView textViewLogin;

    DatabaseReference  mFirebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        try {
            this.getSupportActionBar().hide();
        }catch (NullPointerException e){}

        edittxtRegisterFullName = findViewById(R.id.edittxtRegisterFullName);
        edittxtRegisterUsername = findViewById(R.id.edittxtRegisterUsername);
        edittxtRegisterEmail = findViewById(R.id.edittxtRegisterEmail);
        edittxtRegisterPassword =  findViewById(R.id.edittxtRegisterPassword);
        edittxtConfirmPassword = findViewById(R.id.edittxtComfirmPassword);
        edittxtRegisterPhone = findViewById(R.id.edittxtRegisterPhone);
        textViewLogin = findViewById(R.id.textViewLogin);
        btnRegister = findViewById(R.id.btnRegister);

        FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("users");

        // store app title to 'app_title' node
       mFirebaseInstance.getReference("app_title").setValue("User Database");

        // app_title change listener
        mFirebaseInstance.getReference("app_title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               Log.e("TAG", "App title updated");

                String appTitle = dataSnapshot.getValue(String.class);

                // update toolbar title
               getSupportActionBar().setTitle(appTitle);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("TAG", "Failed to read app title value.", error.toException());
            }
        }
        );

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
        String fullname = edittxtRegisterFullName.getText().toString();
        String password = edittxtRegisterPassword.getText().toString();
        String phone = edittxtRegisterPhone.getText().toString();
        String username = edittxtRegisterUsername.getText().toString();
        String password2 = edittxtConfirmPassword.getText().toString();
        double wallet = 0, btc=0, husd=0, omg=0, eth=0, bnb=0;

        if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length() >= 8 && password.equals(password2)){
            if(!password.isEmpty()){
                if (TextUtils.isEmpty(username)) {
                    username = mFirebaseDatabase.push().getKey();
                }
                UserData userData = new UserData(bnb, btc, email,eth, fullname,husd,omg, password, phone, username,wallet);
                mFirebaseDatabase.child(username).setValue(userData);
                Toast.makeText(RegisterActivity.this, R.string.message5, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this , LoginActivity.class);
                startActivity(intent);
                finish();

            }else {
                edittxtRegisterPassword.setError(getText(R.string.message3));
            }
        }else if(email.isEmpty()){
            edittxtRegisterEmail.setError(getText(R.string.message3));
        }else if(!password.equals(password2)){
            Toast.makeText(RegisterActivity.this, R.string.message7, Toast.LENGTH_SHORT).show();
        }else if(password.length() < 8){
            edittxtRegisterPassword.setError(getText(R.string.message8));
        }else if(phone.isEmpty()){
            edittxtRegisterPhone.setError(getText(R.string.message3));
        }
        else {
            edittxtRegisterEmail.setError(getText(R.string.message4));
        }

    }
}
