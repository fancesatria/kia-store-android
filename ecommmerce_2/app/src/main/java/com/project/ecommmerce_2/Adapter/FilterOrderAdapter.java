package com.project.ecommmerce_2.Adapter;

import static com.project.ecommmerce_2.User.UnpaidOrder.*;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.ecommmerce_2.Helper.Modul;
import com.project.ecommmerce_2.Model.OrderModel;
import com.project.ecommmerce_2.R;
import com.project.ecommmerce_2.Transaction.DetailOrder;
import com.project.ecommmerce_2.User.UnpaidOrder;

import java.text.ParseException;
import java.util.List;

public class FilterOrderAdapter extends RecyclerView.Adapter<FilterOrderAdapter.ViewHolder>{
    private Context context;
    private List<OrderModel> data;

    public FilterOrderAdapter(Context contex, List<OrderModel> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderModel orderModel = data.get(position);


        holder.nama.setText(orderModel.getNama());
        holder.total.setText("Rp. "+ Modul.numberFormat(String.valueOf(Integer.valueOf(orderModel.getTotal_harga()))));

        if (orderModel.getStatus().equalsIgnoreCase("Belum Bayar")) {
            holder.status.setBackgroundResource(R.drawable.backgound_status_maroon);
            holder.status.setText(orderModel.getStatus());
            holder.btnBayar.setVisibility(View.GONE);
        }

        holder.btnBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((UnpaidOrder) context).updateStatus(orderModel.getSnap_token(), context);
            }
        });

        holder.linearOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailOrder.class);
                i.putExtra("snap_token", orderModel.getSnap_token());
                i.putExtra("total_harga", orderModel.getTotal_harga());
                i.putExtra("status_order", orderModel.getStatus());
                try {
                    i.putExtra("tanggal_transaksi", orderModel.newDate());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView total, nama, status;
        Button btnBayar;
        LinearLayout linearOrder;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            total = itemView.findViewById(R.id.txtTotal);
            nama = itemView.findViewById(R.id.txtNames);
            status = itemView.findViewById(R.id.txtStatus);
            btnBayar = itemView.findViewById(R.id.btnBayar);
            linearOrder = itemView.findViewById(R.id.linearOrder);
        }
    }
}
