package com.baykris.tr_crypto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BnbActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    DatabaseReference mFirebaseDatabase;
    private String username,fullname,email,wallet,phone,btc,bnb,eth,husd,omg;
    private EditText edittxtbelanjaBNB;
    private Button btnBeli1,btnJual1,btnHome1;
    private TextView txtviewBNBWallet, txtviewbnb_aset,tview_price_bnb;
    private double tmp_aset_bnb, tmp_wallet,jumlah_beli_bnb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bnb);

        try {
            this.getSupportActionBar().hide();
        }catch (NullPointerException e){}

        auth = FirebaseAuth.getInstance();
        FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("users");

        Intent intentbnb = getIntent();
        username = intentbnb.getStringExtra("username");
        fullname = intentbnb.getStringExtra("fullname");
        email = intentbnb.getStringExtra("email");
        phone = intentbnb.getStringExtra("phone");
        wallet = intentbnb.getStringExtra("wallet");
        btc = intentbnb.getStringExtra("btc");
        bnb = intentbnb.getStringExtra("bnb");
        eth = intentbnb.getStringExtra("eth");
        husd = intentbnb.getStringExtra("husd");
        omg = intentbnb.getStringExtra("omg");

        edittxtbelanjaBNB = findViewById(R.id.edittxtbelanjaBNB);
        txtviewBNBWallet = findViewById(R.id.txtviewBNBWallet);
        txtviewbnb_aset = findViewById(R.id.txtviewbnb_aset);
        tview_price_bnb =  findViewById(R.id.tview_price_bnb);
        btnBeli1 = findViewById(R.id.btnBeli1);
        btnJual1 = findViewById(R.id.btnJual1);
        btnHome1 =  findViewById(R.id.btnHome1);

        txtviewBNBWallet.setText("Rp. "+wallet);
        txtviewbnb_aset.setText(bnb);
        tmp_aset_bnb = Double.parseDouble(bnb);
        tmp_wallet = Double.parseDouble(wallet);
        double hargabnb = Double.parseDouble(tview_price_bnb.getText().toString());
        btnBeli1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumlah_beli_bnb = Double.parseDouble(edittxtbelanjaBNB.getText().toString());
                double totalbeli_bnb = hargabnb*jumlah_beli_bnb;
              if(!edittxtbelanjaBNB.getText().toString().isEmpty()){
                  if(tmp_wallet > totalbeli_bnb){
                        tmp_wallet= tmp_wallet-totalbeli_bnb;
                        tmp_aset_bnb = tmp_aset_bnb+jumlah_beli_bnb;
                        txtviewbnb_aset.setText(Double.toString(tmp_aset_bnb));
                        txtviewBNBWallet.setText("Rp. "+tmp_wallet);
                      mFirebaseDatabase.child(username).child("bnb").setValue(tmp_aset_bnb);
                      mFirebaseDatabase.child(username).child("wallet").setValue(tmp_wallet);

                  }else {
                      Toast.makeText(BnbActivity.this, "Uang anda tidak cukup", Toast.LENGTH_SHORT).show();
                  }
              }else {
                  Toast.makeText(BnbActivity.this, "Harap masukkan jumlah koin yang ingin dibeli!", Toast.LENGTH_SHORT).show();
              }
            }
        });


    }
}