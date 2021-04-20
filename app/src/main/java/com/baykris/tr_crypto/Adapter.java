package com.baykris.tr_crypto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    LayoutInflater inflater;
    List<Coin> coins;

    public Adapter(Context ctx, List<Coin> coins){
        this.inflater = LayoutInflater.from(ctx);
        this.coins = coins;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_list_layout , parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.coinName.setText(coins.get(position).getName());
        holder.coinSymbol.setText(coins.get(position).getSymbol());
        holder.coinPrice.setText("Rp. "+coins.get(position).getPrice_rp());
        holder.percent1h.setText("1h "+coins.get(position).getPercent_change_1h()+"%");
        Picasso.get().load(coins.get(position).getIconUrl()).into(holder.coinIcon);
    }

    @Override
    public int getItemCount() {
        return coins.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView coinSymbol, coinName, coinPrice, percent1h;
        ImageView coinIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            coinSymbol = itemView.findViewById(R.id.coinSymbol);
            coinName = itemView.findViewById(R.id.coinName);
            coinPrice = itemView.findViewById(R.id.coinPrice);
            percent1h= itemView.findViewById(R.id.percent1h);
            coinIcon = itemView.findViewById(R.id.coinIcon);
        }
    }
}
