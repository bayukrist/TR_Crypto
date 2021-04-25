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
    private Button btnDeposit,btnBack;
    private String username,fullname,email,wallet,phone,btc,bnb,eth,husd,omg;
    private  double amount;
    private boolean klik = false;

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
       username = intent.getStringExtra("username");
       fullname = intent.getStringExtra("fullname");
       email = intent.getStringExtra("email");
       phone = intent.getStringExtra("phone");
       wallet = intent.getStringExtra("wallet");
       double walletdouble = Double.parseDouble(wallet);
        btc = intent.getStringExtra("btc");
        bnb = intent.getStringExtra("bnb");
        eth = intent.getStringExtra("eth");
        husd = intent.getStringExtra("husd");
        omg = intent.getStringExtra("omg");

        edittxtDeposit = findViewById(R.id.edittxtDeposit);
        txtviewDepositWallet = findViewById(R.id.txtviewDepositWallet);
        btnDeposit = findViewById(R.id.btnDeposit);
        btnBack = findViewById(R.id.btnBack);

        txtviewDepositWallet.setText("Rp. "+wallet);

        btnDeposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edittxtDeposit.getText().toString().isEmpty()){
                    double depositdouble = Double.parseDouble(edittxtDeposit.getText().toString());
                    amount = walletdouble+depositdouble;
                    mFirebaseDatabase.child(username).child("wallet").setValue(amount);
                    txtviewDepositWallet.setText("Rp. "+Double.toString(amount));
                } else{
                    edittxtDeposit.setError(getText(R.string.message3));
                }

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                klik = true;
                Intent intentback = new Intent(DepositActivity.this, ProfileActivity.class);
                intentback.putExtra("fullname", fullname);
                intentback.putExtra("email", email);
                intentback.putExtra("username", username);
                intentback.putExtra("phone", phone);
                intentback.putExtra("btc",btc);
                intentback.putExtra("bnb",bnb);
                intentback.putExtra("eth",eth);
                intentback.putExtra("husd",husd);
                intentback.putExtra("omg",omg);
                if(!edittxtDeposit.getText().toString().isEmpty()){
                    intentback.putExtra("wallet",Double.toString(amount));
                } else{
                    intentback.putExtra("wallet",wallet);
                }
                startActivity(intentback);
                finish();
            }
        });


    }
}