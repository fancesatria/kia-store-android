package com.project.ecommmerce_2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.ecommmerce_2.Detail;
import com.project.ecommmerce_2.Helper.API;
import com.project.ecommmerce_2.Helper.Modul;
import com.project.ecommmerce_2.Model.ProductModel;
import com.project.ecommmerce_2.databinding.ItemProductBinding;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private Context context;
    private List<ProductModel> data;

    public ProductAdapter(Context context, List<ProductModel> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductBinding bind;
        bind = ItemProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(bind);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductModel product = data.get(position);
        Glide.with(context).load(API.ROOT_URL+product.getGambar()).into(holder.bind.image);
        holder.bind.name.setText(Modul.upperCaseFirst(product.getNama()));
        holder.bind.price.setText("Rp. "+ Modul.numberFormat(String.valueOf(Integer.valueOf(product.getHarga()))));
        holder.bind.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Detail.class);
                i.putExtra("id_product", product.getId());
                i.putExtra("image", product.getGambar());
                i.putExtra("name", product.getNama());
                i.putExtra("price", product.getHarga());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        //if(data==null) return 0;
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemProductBinding bind;
        public ViewHolder(ItemProductBinding itemView) {
            super(itemView.getRoot());

            bind = itemView;
        }
    }
}
