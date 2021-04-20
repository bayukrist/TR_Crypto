package com.baykris.tr_crypto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Coin> coins;
    Adapter adapter;
    private static String JSON_URL = "https://my-json-server.typicode.com/bayukrist/cryptolist/crypto";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.coinList);
        coins = new ArrayList<>();

        extractCoins();

    }

    private void extractCoins() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject coinObject = response.getJSONObject(i);

                        Coin coin = new Coin();
                        coin.setName(coinObject.getString("name").toString());
                        coin.setSymbol(coinObject.getString("symbol").toString());
                        coin.setPrice_rp(coinObject.getString("price_rp").toString());
                        coin.setPercent_change_1h(coinObject.getString("percent_change_1h").toString());
                        coin.setIconUrl(coinObject.getString("iconURL").toString());

                        coins.add(coin);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter =  new Adapter(getApplicationContext(), coins);
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: "+error.getMessage());
            }
        });

        queue.add(jsonArrayRequest);
    }
}