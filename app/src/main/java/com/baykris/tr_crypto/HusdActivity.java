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

public class HusdActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    DatabaseReference mFirebaseDatabase;
    private String username,fullname,email,wallet,phone,btc,bnb,eth,husd,omg;
    private EditText edittxtbelanjaHUSD;
    private Button btnBeli4,btnJual4,btnHome4;
    private TextView txtviewHUSDWallet, txtviewhusd_aset,tview_price_husd;
    private double tmp_aset_husd, tmp_wallet,jumlah_beli_husd,jumlah_jual_husd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_husd);

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
        btc = intent.getStringExtra("btc");
        bnb = intent.getStringExtra("bnb");
        eth = intent.getStringExtra("eth");
        husd = intent.getStringExtra("husd");
        omg = intent.getStringExtra("omg");

        edittxtbelanjaHUSD = findViewById(R.id.edittxtbelanjaHUSD);
        txtviewHUSDWallet = findViewById(R.id.txtviewHUSDWallet);
        txtviewhusd_aset = findViewById(R.id.txtviewhusd_aset);
        tview_price_husd =  findViewById(R.id.tview_price_husd);
        btnBeli4 = findViewById(R.id.btnBeli4);
        btnJual4 = findViewById(R.id.btnJual4);
        btnHome4 =  findViewById(R.id.btnHome4);

        txtviewHUSDWallet.setText("Rp. "+wallet);
        txtviewhusd_aset.setText("Jumlah Koin : "+husd);
        tmp_aset_husd = Double.parseDouble(husd);
        tmp_wallet = Double.parseDouble(wallet);
        double hargahusd = Double.parseDouble(tview_price_husd.getText().toString());
        btnBeli4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumlah_beli_husd = Double.parseDouble(edittxtbelanjaHUSD.getText().toString());
                double totalbeli_husd = hargahusd*jumlah_beli_husd;
                if(!edittxtbelanjaHUSD.getText().toString().isEmpty()){
                    if(tmp_wallet > totalbeli_husd){
                        tmp_wallet= tmp_wallet-totalbeli_husd;
                        tmp_aset_husd = tmp_aset_husd+jumlah_beli_husd;
                        txtviewhusd_aset.setText("Jumlah Koin : "+Double.toString(tmp_aset_husd));
                        txtviewHUSDWallet.setText("Rp. "+tmp_wallet);
                        mFirebaseDatabase.child(username).child("husd").setValue(tmp_aset_husd);
                        mFirebaseDatabase.child(username).child("wallet").setValue(tmp_wallet);

                    }else {
                        Toast.makeText(HusdActivity.this, "Uang anda tidak cukup", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(HusdActivity.this, "Harap masukkan jumlah koin yang ingin dibeli!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnJual4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumlah_jual_husd = Double.parseDouble(edittxtbelanjaHUSD.getText().toString());
                double totaljual_husd = hargahusd*jumlah_jual_husd;
                if(!edittxtbelanjaHUSD.getText().toString().isEmpty()){
                    if(tmp_aset_husd > 0 && tmp_aset_husd >= Double.parseDouble(edittxtbelanjaHUSD.getText().toString())){
                        tmp_wallet= tmp_wallet+totaljual_husd;
                        tmp_aset_husd = tmp_aset_husd-jumlah_jual_husd;
                        txtviewhusd_aset.setText("Jumlah Koin : "+Double.toString(tmp_aset_husd));
                        txtviewHUSDWallet.setText("Rp. "+tmp_wallet);
                        mFirebaseDatabase.child(username).child("husd").setValue(tmp_aset_husd);
                        mFirebaseDatabase.child(username).child("wallet").setValue(tmp_wallet);

                    }else {
                        Toast.makeText(HusdActivity.this, "Anda tidak memiliki koin yang cukup", Toast.LENGTH_LONG).show();
                    }
                }else if(edittxtbelanjaHUSD.getText().toString().isEmpty()){
                    Toast.makeText(HusdActivity.this, "Harap masukkan jumlah koin yang ingin dijual!", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnHome4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentback2 = new Intent(HusdActivity.this, ProfileActivity.class);
                intentback2.putExtra("fullname", fullname);
                intentback2.putExtra("email", email);
                intentback2.putExtra("username", username);
                intentback2.putExtra("phone", phone);
                intentback2.putExtra("bnb",bnb);
                intentback2.putExtra("eth",eth);
                intentback2.putExtra("btc",btc);
                intentback2.putExtra("omg",omg);
                if(!edittxtbelanjaHUSD.getText().toString().isEmpty()){
                    intentback2.putExtra("wallet",Double.toString(tmp_wallet));
                    intentback2.putExtra("husd",tmp_aset_husd);
                } else{
                    intentback2.putExtra("wallet",wallet);
                    intentback2.putExtra("husd",husd);
                }
                startActivity(intentback2);
                finish();
            }
        });

    }
}