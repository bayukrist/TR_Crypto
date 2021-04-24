package com.baykris.tr_crypto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class buy_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_activity);

        try {
            this.getSupportActionBar().hide();
        }catch (NullPointerException e){}

        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();
        String stringHargaCoin = intent.getStringExtra("HargaCoin");
        String namaCoin = intent.getStringExtra("NamaCoin");
        Bitmap logoCoin = bundle.getParcelable("LogoCoin");

        TextView viewNamaCoin = findViewById(R.id.textSellCoinName);
        TextView totalCoin = findViewById(R.id.textTotalCoin);
        ImageView viewImageCoin = findViewById(R.id.imageSellCoin);
        EditText inputDollar = findViewById(R.id.inputBuy);
        Button hitungCoin = findViewById(R.id.buttonHitungCoin);

        viewNamaCoin.setText(namaCoin);
        viewImageCoin.setImageBitmap(logoCoin);
        String tempHargaCoin = stringHargaCoin.replaceAll("[^0-9.]", "");

        hitungCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hargaCoin = Integer.valueOf(tempHargaCoin);
                String tempJumlahDollar = inputDollar.getText().toString();
                int jumlahDollar = Integer.valueOf(tempJumlahDollar);
                int hasil = jumlahDollar / hargaCoin;
                String tempHasil = String.valueOf(hasil);
                totalCoin.setText(tempHasil);
            }
        });
    }

    public int hitungHargaCoin(int hargaCoin, int jumlahDollar)
    {
        int hasil = jumlahDollar / hargaCoin;
        return hasil;
    }
}