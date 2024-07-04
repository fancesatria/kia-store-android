package com.project.ecommmerce_2.RajaOngkir.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.project.ecommmerce_2.Helper.Modul;
import com.project.ecommmerce_2.R;
import com.project.ecommmerce_2.RajaOngkir.DataProcessing.cost.DataCourier;
import com.project.ecommmerce_2.databinding.ListItemCourierBinding;

import java.util.List;

public class OngkirAdapter extends RecyclerView.Adapter<OngkirAdapter.ViewHolder> {
    private Context context;
    private List<DataCourier> data;
    private List<String> courier;
    private int imgLogo;
    public static int selected_shipping_charge = 0;
    public static String selected_courier, selected_service;
    private int selectedPosition = RecyclerView.NO_POSITION;

    public OngkirAdapter(Context context, List<DataCourier> data, List<String> courier) {
        this.context = context;
        this.data = data;
        this.courier = courier;
    }

    @NonNull
    @Override
    public OngkirAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemCourierBinding bind;
        bind = ListItemCourierBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(bind);
    }

    @Override
    public void onBindViewHolder(@NonNull OngkirAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (courier.isEmpty() || data.isEmpty()) return;

        String strLogo = courier.get(position);

        switch (strLogo.toLowerCase()) {
            case "jne":
                imgLogo = R.drawable.logo_jne;
                break;
            case "pos":
                imgLogo = R.drawable.logo_pos;
                break;
            default:
                imgLogo = R.drawable.logo_tiki;
                break;
        }

        if (selectedPosition == position) {
            holder.bind.cvCourier.setBackgroundColor(ContextCompat.getColor(context, R.color.lightergrey));
        } else {
            holder.bind.cvCourier.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }

        holder.bind.imgLogo.setImageResource(imgLogo);
        holder.bind.tvType.setText("Jenis Layanan : " + data.get(position).getService());
        holder.bind.tvEst.setText(data.get(position).getCost().get(0).getEtd() + " hari");
        holder.bind.tvPrice.setText(Modul.formatRupiah(data.get(position).getCost().get(0).getValue()));
        holder.bind.cvCourier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int previousSelectedPosition = selectedPosition;
                selectedPosition = position;

                notifyItemChanged(previousSelectedPosition);
                notifyItemChanged(selectedPosition);

                selected_shipping_charge = data.get(position).getCost().get(0).getValue();
                selected_courier = strLogo;
                selected_service = data.get(position).getService();
            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ListItemCourierBinding bind;
        public ViewHolder(@NonNull ListItemCourierBinding itemView) {
            super(itemView.getRoot());
            bind = itemView;
        }
    }
}
