package com.project.ecommmerce_2.User;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.project.ecommmerce_2.Adapter.FilterOrderAdapter;
import com.project.ecommmerce_2.Adapter.OrderAdapter;
import com.project.ecommmerce_2.Component.ErrorDialog;
import com.project.ecommmerce_2.Component.LoadingDialog;
import com.project.ecommmerce_2.Helper.API;
import com.project.ecommmerce_2.Helper.SPHelper;
import com.project.ecommmerce_2.Model.OrderModel;
import com.project.ecommmerce_2.R;
import com.project.ecommmerce_2.databinding.ActivityPackedOrderBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PackedOrder extends AppCompatActivity {

    private ActivityPackedOrderBinding bind;
    private SPHelper sp;
    private List<OrderModel> data = new ArrayList<>();
    private FilterOrderAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityPackedOrderBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        sp = new SPHelper(this);

        bind.item.setLayoutManager(new LinearLayoutManager(this));
        orderAdapter = new FilterOrderAdapter(PackedOrder.this, data);
        bind.item.setAdapter(orderAdapter);

        fetchData();
    }

    public void fetchData() {
        LoadingDialog.load(this);
        Call<List<OrderModel>> karyaGetRespCall = API.getRetrofit(this).getPaid(sp.getIdPengguna());
        karyaGetRespCall.enqueue(new Callback<List<OrderModel>>() {
            @Override
            public void onResponse(Call<List<OrderModel>> call, Response<List<OrderModel>> response) {
                LoadingDialog.close();
                if (response.isSuccessful()) {
                    if (response.body() == null || response.body().isEmpty()) {
                        Toast.makeText(PackedOrder.this, getString(R.string.no_data), Toast.LENGTH_SHORT).show();
                    } else {
                        data.clear();
                        data.addAll(response.body());
                        orderAdapter.notifyDataSetChanged();
                    }
                } else {
                    ErrorDialog.message(PackedOrder.this, getString(R.string.cant_access), bind.getRoot());
                }
            }

            @Override
            public void onFailure(Call<List<OrderModel>> call, Throwable t) {
                LoadingDialog.close();
                ErrorDialog.message(PackedOrder.this, getString(R.string.trouble), bind.getRoot());
//                Toast.makeText(PackedOrder.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        fetchData();
    }
}
