package com.project.ecommmerce_2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.ecommmerce_2.Helper.API;
import com.project.ecommmerce_2.Helper.Modul;
import com.project.ecommmerce_2.Model.CartItem;
import com.project.ecommmerce_2.R;

import java.util.List;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.ViewHolder>{
    private List<CartItem> cartItems;
    private Context context;
    private CartAdapter.CartUpdateListener cartUpdateListener;

    public CheckoutAdapter(List<CartItem> cartItems, Context context, CartAdapter.CartUpdateListener cartUpdateListener) {
        this.cartItems = cartItems;
        this.context = context;
        this.cartUpdateListener = cartUpdateListener;
    }

    @NonNull
    @Override
    public CheckoutAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_checkout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutAdapter.ViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);
        holder.txtProduk.setText(Modul.upperCaseFirst(cartItem.getName()));
        holder.txtHarga.setText("Rp. " + Modul.numberFormat(String.valueOf(Integer.valueOf(cartItem.getPrice()))));
        holder.txtJumlah.setText(String.valueOf(cartItem.getQuantity()));

        Glide.with(context).load(API.ROOT_URL + cartItem.getImage()).into(holder.ivProduk);
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProduk;
        TextView txtProduk, txtHarga, txtJumlah;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProduk = itemView.findViewById(R.id.ivProduk);
            txtProduk = itemView.findViewById(R.id.txtProduk);
            txtHarga = itemView.findViewById(R.id.txtHarga);
            txtJumlah = itemView.findViewById(R.id.txtJumlah);
        }
    }
}
