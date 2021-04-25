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

public class EthActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    DatabaseReference mFirebaseDatabase;
    private String username,fullname,email,wallet,phone,btc,bnb,eth,husd,omg;
    private EditText edittxtbelanjaETH;
    private Button btnBeli3,btnJual3,btnHome3;
    private TextView txtviewETHWallet, txtvieweth_aset,tview_price_eth;
    private double tmp_aset_eth, tmp_wallet,jumlah_beli_eth,jumlah_jual_eth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eth);

        try {
            this.getSupportActionBar().hide();
        }catch (NullPointerException e){}

        auth = FirebaseAuth.getInstance();
        FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("users");

        Intent intenteth = getIntent();
        username = intenteth.getStringExtra("username");
        fullname = intenteth.getStringExtra("fullname");
        email = intenteth.getStringExtra("email");
        phone = intenteth.getStringExtra("phone");
        wallet = intenteth.getStringExtra("wallet");
        btc = intenteth.getStringExtra("btc");
        bnb = intenteth.getStringExtra("bnb");
        eth = intenteth.getStringExtra("eth");
        husd = intenteth.getStringExtra("husd");
        omg = intenteth.getStringExtra("omg");

        edittxtbelanjaETH = findViewById(R.id.edittxtbelanjaETH);
        txtviewETHWallet = findViewById(R.id.txtviewETHWallet);
        txtvieweth_aset = findViewById(R.id.txtvieweth_aset);
        tview_price_eth =  findViewById(R.id.tview_price_eth);
        btnBeli3 = findViewById(R.id.btnBeli3);
        btnJual3 = findViewById(R.id.btnJual3);
        btnHome3 =  findViewById(R.id.btnHome3);

        txtviewETHWallet.setText("Rp. "+wallet);
        txtvieweth_aset.setText(getText(R.string.number_coin)+eth);
        tmp_aset_eth = Double.parseDouble(eth);
        tmp_wallet = Double.parseDouble(wallet);
        double hargaeth = Double.parseDouble(tview_price_eth.getText().toString());
        btnBeli3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumlah_beli_eth = Double.parseDouble(edittxtbelanjaETH.getText().toString());
                double totalbeli_eth = hargaeth*jumlah_beli_eth;
                if(!edittxtbelanjaETH.getText().toString().isEmpty()){
                    if(tmp_wallet > totalbeli_eth){
                        tmp_wallet= tmp_wallet-totalbeli_eth;
                        tmp_aset_eth = tmp_aset_eth+jumlah_beli_eth;
                        txtvieweth_aset.setText(getText(R.string.number_coin)+Double.toString(tmp_aset_eth));
                        txtviewETHWallet.setText("Rp. "+tmp_wallet);
                        mFirebaseDatabase.child(username).child("eth").setValue(tmp_aset_eth);
                        mFirebaseDatabase.child(username).child("wallet").setValue(tmp_wallet);

                    }else {
                        Toast.makeText(EthActivity.this, getText(R.string.kurang_uang), Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(EthActivity.this, getText(R.string.please_coin), Toast.LENGTH_LONG).show();
                }
            }
        });

        btnJual3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumlah_jual_eth = Double.parseDouble(edittxtbelanjaETH.getText().toString());
                double totaljual_eth = hargaeth*jumlah_jual_eth;
                if(!edittxtbelanjaETH.getText().toString().isEmpty()){
                    if(tmp_aset_eth > 0 && tmp_aset_eth >= Double.parseDouble(edittxtbelanjaETH.getText().toString())){
                        tmp_wallet= tmp_wallet+totaljual_eth;
                        tmp_aset_eth = tmp_aset_eth-jumlah_jual_eth;
                        txtvieweth_aset.setText(getText(R.string.number_coin)+Double.toString(tmp_aset_eth));
                        txtviewETHWallet.setText("Rp. "+tmp_wallet);
                        mFirebaseDatabase.child(username).child("eth").setValue(tmp_aset_eth);
                        mFirebaseDatabase.child(username).child("wallet").setValue(tmp_wallet);

                    }else {
                        Toast.makeText(EthActivity.this, getText(R.string.kurang_koin), Toast.LENGTH_LONG).show();
                    }
                }else if(edittxtbelanjaETH.getText().toString().isEmpty()){
                    Toast.makeText(EthActivity.this, getText(R.string.please_coin), Toast.LENGTH_LONG).show();
                }
            }
        });

        btnHome3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentback2 = new Intent(EthActivity.this, ProfileActivity.class);
                intentback2.putExtra("fullname", fullname);
                intentback2.putExtra("email", email);
                intentback2.putExtra("username", username);
                intentback2.putExtra("phone", phone);
                intentback2.putExtra("bnb",bnb);
                intentback2.putExtra("btc",btc);
                intentback2.putExtra("husd",husd);
                intentback2.putExtra("omg",omg);
                if(!edittxtbelanjaETH.getText().toString().isEmpty()){
                    intentback2.putExtra("wallet",Double.toString(tmp_wallet));
                    intentback2.putExtra("eth",tmp_aset_eth);
                } else{
                    intentback2.putExtra("wallet",wallet);
                    intentback2.putExtra("eth",eth);
                }
                startActivity(intentback2);
                finish();
            }
        });

    }
}