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

public class BtcActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    DatabaseReference mFirebaseDatabase;
    private String username,fullname,email,wallet,phone,btc,bnb,eth,husd,omg;
    private EditText edittxtbelanjaBTC;
    private Button btnBeli2,btnJual2,btnHome2;
    private TextView txtviewBTCWallet, txtviewbtc_aset,tview_price_btc;
    private double tmp_aset_btc, tmp_wallet,jumlah_beli_btc,jumlah_jual_btc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btc);

        try {
            this.getSupportActionBar().hide();
        }catch (NullPointerException e){}

        auth = FirebaseAuth.getInstance();
        FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("users");

        Intent intentbtc = getIntent();
        username = intentbtc.getStringExtra("username");
        fullname = intentbtc.getStringExtra("fullname");
        email = intentbtc.getStringExtra("email");
        phone = intentbtc.getStringExtra("phone");
        wallet = intentbtc.getStringExtra("wallet");
        btc = intentbtc.getStringExtra("btc");
        bnb = intentbtc.getStringExtra("bnb");
        eth = intentbtc.getStringExtra("eth");
        husd = intentbtc.getStringExtra("husd");
        omg = intentbtc.getStringExtra("omg");

        edittxtbelanjaBTC = findViewById(R.id.edittxtbelanjaBTC);
        txtviewBTCWallet = findViewById(R.id.txtviewBTCWallet);
        txtviewbtc_aset = findViewById(R.id.txtviewbtc_aset);
        tview_price_btc =  findViewById(R.id.tview_price_btc);
        btnBeli2 = findViewById(R.id.btnBeli2);
        btnJual2 = findViewById(R.id.btnJual2);
        btnHome2 =  findViewById(R.id.btnHome2);

        txtviewBTCWallet.setText("Rp. "+wallet);
        txtviewbtc_aset.setText(getText(R.string.number_coin)+btc);
        tmp_aset_btc = Double.parseDouble(btc);
        tmp_wallet = Double.parseDouble(wallet);
        double hargabtc = Double.parseDouble(tview_price_btc.getText().toString());
        btnBeli2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumlah_beli_btc = Double.parseDouble(edittxtbelanjaBTC.getText().toString());
                double totalbeli_btc = hargabtc*jumlah_beli_btc;
                if(!edittxtbelanjaBTC.getText().toString().isEmpty()){
                    if(tmp_wallet > totalbeli_btc){
                        tmp_wallet= tmp_wallet-totalbeli_btc;
                        tmp_aset_btc = tmp_aset_btc+jumlah_beli_btc;
                        txtviewbtc_aset.setText(getText(R.string.number_coin)+Double.toString(tmp_aset_btc));
                        txtviewBTCWallet.setText("Rp. "+tmp_wallet);
                        mFirebaseDatabase.child(username).child("btc").setValue(tmp_aset_btc);
                        mFirebaseDatabase.child(username).child("wallet").setValue(tmp_wallet);

                    }else {
                        Toast.makeText(BtcActivity.this, getText(R.string.kurang_uang), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(BtcActivity.this, getText(R.string.please_coin), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnJual2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumlah_jual_btc = Double.parseDouble(edittxtbelanjaBTC.getText().toString());
                double totaljual_bnb = hargabtc*jumlah_jual_btc;
                if(!edittxtbelanjaBTC.getText().toString().isEmpty()){
                    if(tmp_aset_btc > 0 && tmp_aset_btc >= Double.parseDouble(edittxtbelanjaBTC.getText().toString())){
                        tmp_wallet= tmp_wallet+totaljual_bnb;
                        tmp_aset_btc = tmp_aset_btc-jumlah_jual_btc;
                        txtviewbtc_aset.setText(getText(R.string.number_coin)+Double.toString(tmp_aset_btc));
                        txtviewBTCWallet.setText("Rp. "+tmp_wallet);
                        mFirebaseDatabase.child(username).child("bnb").setValue(tmp_aset_btc);
                        mFirebaseDatabase.child(username).child("wallet").setValue(tmp_wallet);

                    }else {
                        Toast.makeText(BtcActivity.this, getText(R.string.please_coin), Toast.LENGTH_LONG).show();
                    }
                }else if(edittxtbelanjaBTC.getText().toString().isEmpty()){
                    Toast.makeText(BtcActivity.this, getText(R.string.please_coin), Toast.LENGTH_LONG).show();
                }
            }
        });

        btnHome2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentback2 = new Intent(BtcActivity.this, ProfileActivity.class);
                intentback2.putExtra("fullname", fullname);
                intentback2.putExtra("email", email);
                intentback2.putExtra("username", username);
                intentback2.putExtra("phone", phone);
                intentback2.putExtra("bnb",bnb);
                intentback2.putExtra("eth",eth);
                intentback2.putExtra("husd",husd);
                intentback2.putExtra("omg",omg);
                if(!edittxtbelanjaBTC.getText().toString().isEmpty()){
                    intentback2.putExtra("wallet",Double.toString(tmp_wallet));
                    intentback2.putExtra("btc",tmp_aset_btc);
                } else{
                    intentback2.putExtra("wallet",wallet);
                    intentback2.putExtra("btc",btc);
                }
                startActivity(intentback2);
                finish();
            }
        });

    }
}