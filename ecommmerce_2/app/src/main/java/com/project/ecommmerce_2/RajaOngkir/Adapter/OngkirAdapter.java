package com.project.ecommmerce_2.RajaOngkir.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.ecommmerce_2.Helper.Modul;
import com.project.ecommmerce_2.R;
import com.project.ecommmerce_2.RajaOngkir.DataProcessing.cost.DataCourier;
import com.project.ecommmerce_2.databinding.ListItemMainBinding;

import java.util.ArrayList;
import java.util.List;

public class OngkirAdapter extends RecyclerView.Adapter<OngkirAdapter.ViewHolder> {
    Context context;
    List<DataCourier> data;
    int imgLogo;

    public OngkirAdapter(Context context, List<DataCourier> data, List<String> courier) {
        this.context = context;
        this.data = filterDataByCourier(data, courier, "JNE"); // Filter data di sini
    }

    @NonNull
    @Override
    public OngkirAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemMainBinding bind;
        bind = ListItemMainBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(bind);
    }

    @Override
    public void onBindViewHolder(@NonNull OngkirAdapter.ViewHolder holder, int position) {
        DataCourier currentData = data.get(position);
        imgLogo = R.drawable.logo_jne;

        holder.bind.imgLogo.setImageResource(imgLogo);
        holder.bind.tvType.setText("Jenis Layanan : " + currentData.getService());
        holder.bind.tvEst.setText(currentData.getCost().get(0).getEtd() + " hari");
        holder.bind.tvPrice.setText(Modul.formatRupiah(currentData.getCost().get(0).getValue()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ListItemMainBinding bind;
        public ViewHolder(@NonNull ListItemMainBinding itemView) {
            super(itemView.getRoot());

            bind = itemView;
        }
    }

    private List<DataCourier> filterDataByCourier(List<DataCourier> data, List<String> couriers, String courierName) {
        List<DataCourier> filteredData = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            if (couriers.get(i).equalsIgnoreCase(courierName)) {
                filteredData.add(data.get(i));
            }
        }
        return filteredData;
    }
}
