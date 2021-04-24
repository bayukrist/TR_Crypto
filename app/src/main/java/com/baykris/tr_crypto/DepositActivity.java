package com.baykris.tr_crypto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DepositActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    DatabaseReference mFirebaseDatabase;
    private EditText edittxtDeposit;
    private TextView txtviewDepositWallet;
    private Button btnDeposit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);


        try {
            this.getSupportActionBar().hide();
        }catch (NullPointerException e){}

        auth = FirebaseAuth.getInstance();
        FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("users");


        Intent intent = getIntent();
       String username = intent.getStringExtra("username");
       String wallet = intent.getStringExtra("wallet");
       double walletdouble = Double.parseDouble(wallet);

        edittxtDeposit = findViewById(R.id.edittxtDeposit);
        txtviewDepositWallet = findViewById(R.id.txtviewDepositWallet);
        btnDeposit = findViewById(R.id.btnDeposit);

        txtviewDepositWallet.setText("Rp. "+wallet);

        btnDeposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double depositdouble = Double.parseDouble(edittxtDeposit.getText().toString());
                double amount = walletdouble+depositdouble;
                mFirebaseDatabase.child(username).child("wallet").setValue(amount);
                txtviewDepositWallet.setText("Rp. "+Double.toString(amount));
            }
        });




    }
}