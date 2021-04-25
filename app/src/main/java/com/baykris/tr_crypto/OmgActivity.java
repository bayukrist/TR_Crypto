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

public class OmgActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    DatabaseReference mFirebaseDatabase;
    private String username,fullname,email,wallet,phone,btc,bnb,eth,husd,omg;
    private EditText edittxtbelanjaOMG;
    private Button btnBeli5,btnJual5,btnHome5;
    private TextView txtviewOMGWallet, txtviewomg_aset,tview_price_omg;
    private double tmp_aset_omg, tmp_wallet,jumlah_beli_omg,jumlah_jual_omg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_omg);

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

        edittxtbelanjaOMG = findViewById(R.id.edittxtbelanjaOMG);
        txtviewOMGWallet = findViewById(R.id.txtviewOMGWallet);
        txtviewomg_aset = findViewById(R.id.txtviewomg_aset);
        tview_price_omg =  findViewById(R.id.tview_price_omg);
        btnBeli5 = findViewById(R.id.btnBeli5);
        btnJual5 = findViewById(R.id.btnJual5);
        btnHome5 =  findViewById(R.id.btnHome5);

        txtviewOMGWallet.setText("Rp. "+wallet);
        txtviewomg_aset.setText("Jumlah Koin : "+omg);
        tmp_aset_omg = Double.parseDouble(omg);
        tmp_wallet = Double.parseDouble(wallet);
        double hargaomg = Double.parseDouble(tview_price_omg.getText().toString());
        btnBeli5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumlah_beli_omg = Double.parseDouble(edittxtbelanjaOMG.getText().toString());
                double totalbeli_omg = hargaomg*jumlah_beli_omg;
                if(!edittxtbelanjaOMG.getText().toString().isEmpty()){
                    if(tmp_wallet > totalbeli_omg){
                        tmp_wallet= tmp_wallet-totalbeli_omg;
                        tmp_aset_omg = tmp_aset_omg+jumlah_beli_omg;
                        txtviewomg_aset.setText("Jumlah Koin : "+Double.toString(tmp_aset_omg));
                        txtviewOMGWallet.setText("Rp. "+tmp_wallet);
                        mFirebaseDatabase.child(username).child("omg").setValue(tmp_aset_omg);
                        mFirebaseDatabase.child(username).child("wallet").setValue(tmp_wallet);

                    }else {
                        Toast.makeText(OmgActivity.this, "Uang anda tidak cukup", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(OmgActivity.this, "Harap masukkan jumlah koin yang ingin dibeli!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnJual5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumlah_jual_omg = Double.parseDouble(edittxtbelanjaOMG.getText().toString());
                double totaljual_omg = hargaomg*jumlah_jual_omg;
                if(!edittxtbelanjaOMG.getText().toString().isEmpty()){
                    if(tmp_aset_omg > 0 && tmp_aset_omg >= Double.parseDouble(edittxtbelanjaOMG.getText().toString())){
                        tmp_wallet= tmp_wallet+totaljual_omg;
                        tmp_aset_omg = tmp_aset_omg-jumlah_jual_omg;
                        txtviewomg_aset.setText("Jumlah Koin : "+Double.toString(tmp_aset_omg));
                        txtviewOMGWallet.setText("Rp. "+tmp_wallet);
                        mFirebaseDatabase.child(username).child("omg").setValue(tmp_aset_omg);
                        mFirebaseDatabase.child(username).child("wallet").setValue(tmp_wallet);

                    }else {
                        Toast.makeText(OmgActivity.this, "Anda tidak memiliki koin yang cukup", Toast.LENGTH_LONG).show();
                    }
                }else if(edittxtbelanjaOMG.getText().toString().isEmpty()){
                    Toast.makeText(OmgActivity.this, "Harap masukkan jumlah koin yang ingin dijual!", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnHome5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentback2 = new Intent(OmgActivity.this, ProfileActivity.class);
                intentback2.putExtra("fullname", fullname);
                intentback2.putExtra("email", email);
                intentback2.putExtra("username", username);
                intentback2.putExtra("phone", phone);
                intentback2.putExtra("bnb",bnb);
                intentback2.putExtra("eth",eth);
                intentback2.putExtra("husd",husd);
                intentback2.putExtra("btc",btc);
                if(!edittxtbelanjaOMG.getText().toString().isEmpty()){
                    intentback2.putExtra("wallet",Double.toString(tmp_wallet));
                    intentback2.putExtra("omg",tmp_aset_omg);
                } else{
                    intentback2.putExtra("wallet",wallet);
                    intentback2.putExtra("omg",omg);
                }
                startActivity(intentback2);
                finish();
            }
        });

    }
}