package com.baykris.tr_crypto;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private TextView txtViewRegister;
    private Button btnLogin;
    private FirebaseAuth auth;
    DatabaseReference mFirebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try {
            this.getSupportActionBar().hide();
        }catch (NullPointerException e){}

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtViewRegister = findViewById(R.id.txtViewRegister);

        auth = FirebaseAuth.getInstance();
        FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("users");



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
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        if(!username.isEmpty()){
            if(!password.isEmpty()){
                Query checkUser = mFirebaseDatabase.orderByChild("username").equalTo(username);
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            String passwordFromDB = snapshot.child(username).child("password").getValue(String.class);

                            if(passwordFromDB.equals(password)){
                                String emailFromDB = snapshot.child(username).child("email").getValue(String.class);
                                String fullnameFromDB = snapshot.child(username).child("fullname").getValue(String.class);
                                String phoneFromDB = snapshot.child(username).child("phone").getValue(String.class);
                                String usernameFromDB = snapshot.child(username).child ("username").getValue(String.class);
                                double btcFromDB = snapshot.child(username).child("btc").getValue(double.class);
                                String btcstring = Double.toString(btcFromDB);
                                double bnbFromDB = snapshot.child(username).child("bnb").getValue(double.class);
                                String bnbstring = Double.toString(bnbFromDB);
                                double ethFromDB = snapshot.child(username).child("eth").getValue(double.class);
                                String ethstring = Double.toString(ethFromDB);
                                double husdFromDB = snapshot.child(username).child("husd").getValue(double.class);
                                String husdstring = Double.toString(husdFromDB);
                                double omgFromDB = snapshot.child(username).child("omg").getValue(double.class);
                                String omgstring = Double.toString(omgFromDB);
                                double walletFromDB = snapshot.child(username).child("wallet").getValue(double.class);
                                String walletstring = Double.toString(walletFromDB);

                                Intent intent2 = new Intent(getApplicationContext(), ProfileActivity.class);
                                intent2.putExtra("fullname",fullnameFromDB);
                                intent2.putExtra("username",usernameFromDB);
                                intent2.putExtra("email",emailFromDB);
                                intent2.putExtra("phone",phoneFromDB);
                                intent2.putExtra("wallet",walletstring);
                                intent2.putExtra("btc",btcstring);
                                intent2.putExtra("bnb",bnbstring);
                                intent2.putExtra("eth",ethstring);
                                intent2.putExtra("husd",husdstring);
                                intent2.putExtra("omg",omgstring);


                                startActivity(intent2);
                            }
                            else {
                                editTextPassword.setError("Wrong password");
                            }
                        }
                        else {
                            editTextUsername.setError("No such user exist");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }else {
                editTextPassword.setError(getText(R.string.message3));
            }
        }else if(username.isEmpty()){
            editTextUsername.setError(getText(R.string.message3));
        }else {
            editTextUsername.setError(getText(R.string.message4));
        }

    }

}
