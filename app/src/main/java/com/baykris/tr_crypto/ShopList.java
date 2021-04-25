package com.baykris.tr_crypto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ShopList extends AppCompatActivity {
    private FirebaseAuth auth;
    DatabaseReference mFirebaseDatabase;
    private String username,fullname,email,wallet,phone,btc,bnb,eth,husd,omg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);

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
    }

    public void bnb(View view){
        Intent tobnb = new Intent(ShopList.this , BnbActivity.class);
        tobnb.putExtra("fullname", fullname);
        tobnb.putExtra("email", email);
        tobnb.putExtra("username", username);
        tobnb.putExtra("phone", phone);
        tobnb.putExtra("wallet",wallet);
        tobnb.putExtra("btc",btc);
        tobnb.putExtra("bnb",bnb);
        tobnb.putExtra("eth",eth);
        tobnb.putExtra("husd",husd);
        tobnb.putExtra("omg",omg);
        startActivity(tobnb);
        finish();
    }
}