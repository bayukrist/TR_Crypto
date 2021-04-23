package com.baykris.tr_crypto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;



public class sell_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_activity);

        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();
        String stringHargaCoin = intent.getStringExtra("HargaCoin");
        String namaCoin = intent.getStringExtra("NamaCoin");
        Bitmap logoCoin = bundle.getParcelable("LogoCoin");

        ImageView imageCoin = findViewById(R.id.imageSellCoin);
        TextView textNamaCoin = findViewById(R.id.textSellCoinName);
        textNamaCoin.setText(namaCoin);
        imageCoin.setImageBitmap(logoCoin);
    }
}