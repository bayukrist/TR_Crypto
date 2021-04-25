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
    private double tmp_aset_bnb, tmp_wallet,jumlah_beli_bnb,jumlah_jual_bnb;

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
        txtviewbnb_aset.setText("Jumlah Koin : "+bnb);
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
                      txtviewbnb_aset.setText("Jumlah Koin : "+Double.toString(tmp_aset_bnb));
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

        btnJual1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumlah_jual_bnb = Double.parseDouble(edittxtbelanjaBNB.getText().toString());
                double totaljual_bnb = hargabnb*jumlah_jual_bnb;
                if(!edittxtbelanjaBNB.getText().toString().isEmpty()){
                    if(tmp_aset_bnb > 0 && tmp_aset_bnb >= Double.parseDouble(edittxtbelanjaBNB.getText().toString())){
                        tmp_wallet= tmp_wallet+totaljual_bnb;
                        tmp_aset_bnb = tmp_aset_bnb-jumlah_jual_bnb;
                        txtviewbnb_aset.setText("Jumlah Koin : "+Double.toString(tmp_aset_bnb));
                        txtviewBNBWallet.setText("Rp. "+tmp_wallet);
                        mFirebaseDatabase.child(username).child("bnb").setValue(tmp_aset_bnb);
                        mFirebaseDatabase.child(username).child("wallet").setValue(tmp_wallet);

                    }else {
                        Toast.makeText(BnbActivity.this, "Anda tidak memiliki koin yang cukup", Toast.LENGTH_LONG).show();
                    }
                }else if(edittxtbelanjaBNB.getText().toString().isEmpty()){
                    Toast.makeText(BnbActivity.this, "Harap masukkan jumlah koin yang ingin dijual!", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnHome1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentback1 = new Intent(BnbActivity.this, ProfileActivity.class);
                intentback1.putExtra("fullname", fullname);
                intentback1.putExtra("email", email);
                intentback1.putExtra("username", username);
                intentback1.putExtra("phone", phone);
                intentback1.putExtra("btc",btc);
                intentback1.putExtra("eth",eth);
                intentback1.putExtra("husd",husd);
                intentback1.putExtra("omg",omg);
                if(!edittxtbelanjaBNB.getText().toString().isEmpty()){
                    intentback1.putExtra("wallet",Double.toString(tmp_wallet));
                    intentback1.putExtra("bnb",tmp_aset_bnb);
                } else{
                    intentback1.putExtra("wallet",wallet);
                    intentback1.putExtra("bnb",bnb);
                }
                startActivity(intentback1);
                finish();
            }
        });


    }
}