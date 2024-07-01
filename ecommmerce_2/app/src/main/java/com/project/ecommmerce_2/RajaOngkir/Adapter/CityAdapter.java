package com.project.ecommmerce_2.RajaOngkir.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.ecommmerce_2.RajaOngkir.DataProcessing.city.CityModel;
import com.project.ecommmerce_2.databinding.ListItemCityBinding;

import java.util.ArrayList;
import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {
    Context context;
    List<CityModel> data;

    public CityAdapter(Context context, List<CityModel> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public CityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemCityBinding bind;
        bind = ListItemCityBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(bind);
    }

    @Override
    public void onBindViewHolder(@NonNull CityAdapter.ViewHolder holder, int position) {
        holder.bind.tvCity.setText(data.get(position).getType()+" "+data.get(position).getCityName());
        holder.bind.tvProvince.setText("Provinsi "+data.get(position).getProvince());
        holder.bind.llCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (((Activity) context).getIntent().getExtras().getInt("requestCode")){
                    case 1:
                        Intent source = new Intent();
                        source.putExtra("city", holder.bind.tvCity.getText().toString());
                        source.putExtra("city_id", data.get(position).getCityId());
                        ((Activity) context).setResult(Activity.RESULT_OK, source);
                        break;
                    case 2:
                        Intent destination = new Intent();
                        destination.putExtra("city", holder.bind.tvCity.getText().toString());
                        destination.putExtra("city_id", data.get(position).getCityId());
                        ((Activity) context).setResult(Activity.RESULT_OK, destination);
                        break;
                }
                ((Activity) context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ListItemCityBinding bind;
        public ViewHolder(@NonNull ListItemCityBinding itemView) {
            super(itemView.getRoot());
            bind = itemView;
        }
    }
}
