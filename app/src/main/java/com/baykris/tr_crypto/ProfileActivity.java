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
    private EditText edittxtprofilFullName, edittxtprofilEmail ,edittxtxprofilPhone;
    private TextView txtviewprofilWallet;
    private String username,wallet;
    private Button btnUpdate, btnLogout;
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
        txtviewprofilWallet = findViewById(R.id.txtviewprofilWallet);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnLogout = findViewById(R.id.btnLogout);



        Intent intent = getIntent();
        String fullname = intent.getStringExtra("fullname");
        username = intent.getStringExtra("username");
        String phone = intent.getStringExtra("phone");
        String email = intent.getStringExtra("email");
        wallet = intent.getStringExtra("wallet");

        edittxtprofilFullName.setText(fullname);
        edittxtprofilEmail.setText(email);
        edittxtxprofilPhone.setText(phone);
        txtviewprofilWallet.setText("Rp. "+wallet);

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

    public void deposit(View view){
        Intent intentdepo = new Intent(ProfileActivity.this, DepositActivity.class);
        intentdepo.putExtra("username", username);
        intentdepo.putExtra("wallet",wallet);
        startActivity(intentdepo);
    }


}