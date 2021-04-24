package com.baykris.tr_crypto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {
    private EditText edittxtprofilFullName, edittxtprofilEmail ,edittxtxprofilPhone, edittxtxprofilWallet;
    private String username;
    private Button btnUpdate, btnLogout, btnDeposit;
    private FirebaseAuth auth;
    DatabaseReference mFirebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        try {
            this.getSupportActionBar().hide();
        }catch (NullPointerException e){}

        auth = FirebaseAuth.getInstance();
        FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("users");

        edittxtprofilFullName = findViewById(R.id.edittxtprofilFullName);
        edittxtprofilEmail = findViewById(R.id.edittxtprofilEmail);
        edittxtxprofilPhone = findViewById(R.id.edittxtprofilPhone);
        edittxtxprofilWallet = findViewById(R.id.edittxtprofilWallet);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnLogout = findViewById(R.id.btnLogout);
        btnDeposit = findViewById(R.id.btnDeposit);

        Intent intent = getIntent();
        String fullname = intent.getStringExtra("fullname");
        username = intent.getStringExtra("username");
        String phone = intent.getStringExtra("phone");
        String email = intent.getStringExtra("email");
        String wallet = intent.getStringExtra("wallet");

        edittxtprofilFullName.setText(fullname);
        edittxtprofilEmail.setText(email);
        edittxtxprofilPhone.setText(phone);
        edittxtxprofilWallet.setText(wallet);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Patterns.EMAIL_ADDRESS.matcher(edittxtprofilEmail.getText().toString()).matches() && !edittxtprofilFullName.getText().toString().isEmpty() && !edittxtprofilEmail.getText().toString().isEmpty() && !edittxtxprofilPhone.getText().toString().isEmpty() ){
                    mFirebaseDatabase.child(username).child("fullname").setValue(edittxtprofilFullName.getText().toString());
                    mFirebaseDatabase.child(username).child("phone").setValue(edittxtxprofilPhone.getText().toString());
                    mFirebaseDatabase.child(username).child("email").setValue(edittxtprofilEmail.getText().toString());
                } else if(edittxtprofilFullName.getText().toString().isEmpty()){
                    edittxtprofilFullName.setError(getText(R.string.message3));
                } else if(edittxtprofilEmail.getText().toString().isEmpty()){
                    edittxtprofilFullName.setError(getText(R.string.message3));
                }   else if(edittxtxprofilPhone.getText().toString().isEmpty()){
                    edittxtprofilFullName.setError(getText(R.string.message3));
                } else {
                    edittxtprofilEmail.setError(getText(R.string.message4));
                }
            }
        });

        btnDeposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edittxtxprofilWallet.getText().toString().isEmpty()){
                    Double walletdouble = Double.parseDouble(edittxtxprofilWallet.getText().toString());
                    mFirebaseDatabase.child(username).child("wallet").setValue(walletdouble);
                }else{
                    edittxtxprofilWallet.setError(getText(R.string.message3));
                }
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intentlogout = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intentlogout);
                finish();
            }
        });
    }


}