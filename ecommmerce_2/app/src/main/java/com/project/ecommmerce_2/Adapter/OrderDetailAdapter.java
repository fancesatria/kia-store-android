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
import com.project.ecommmerce_2.Model.OrderDetailModel;
import com.project.ecommmerce_2.R;

import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder>{
    private Context context;
    private List<OrderDetailModel> data;

    public OrderDetailAdapter(Context context, List<OrderDetailModel> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_detail, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderDetailModel orderDetailModel = data.get(position);
        holder.harga.setText("Rp. " + Modul.numberFormat(String.valueOf(orderDetailModel.getHarga())));
        holder.nama.setText(Modul.upperCaseFirst(orderDetailModel.getNama()));
        holder.jumlah.setText(String.valueOf(orderDetailModel.getJumlah()));
        Glide.with(context).load(API.ROOT_URL + orderDetailModel.getGambar_barang()).into(holder.ivProduk);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama, harga, jumlah;
        ImageView ivProduk;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.txtProduk);
            harga = itemView.findViewById(R.id.txtHarga);
            jumlah = itemView.findViewById(R.id.txtJumlah);
            ivProduk = itemView.findViewById(R.id.ivProduk);
        }
    }
}
