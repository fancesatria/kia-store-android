package com.project.ecommmerce_2.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.project.ecommmerce_2.Adapter.FilterOrderAdapter;
import com.project.ecommmerce_2.Component.ErrorDialog;
import com.project.ecommmerce_2.Component.LoadingDialog;
import com.project.ecommmerce_2.Helper.API;
import com.project.ecommmerce_2.Helper.SPHelper;
import com.project.ecommmerce_2.Model.OrderModel;
import com.project.ecommmerce_2.R;
import com.project.ecommmerce_2.databinding.ActivityCancelledBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CancelledOrder extends AppCompatActivity {
    
    ActivityCancelledBinding bind;
    private SPHelper sp;
    private List<OrderModel> data = new ArrayList<>();
    private FilterOrderAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityCancelledBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        sp = new SPHelper(this);

        bind.item.setLayoutManager(new LinearLayoutManager(this));
        orderAdapter = new FilterOrderAdapter(CancelledOrder.this, data);
        bind.item.setAdapter(orderAdapter);

        fetchData();
    }

    public void fetchData() {
        LoadingDialog.load(this);
        Call<List<OrderModel>> karyaGetRespCall = API.getRetrofit(this).getCancelled(sp.getIdPengguna());
        karyaGetRespCall.enqueue(new Callback<List<OrderModel>>() {
            @Override
            public void onResponse(Call<List<OrderModel>> call, Response<List<OrderModel>> response) {
                LoadingDialog.close();
                if (response.isSuccessful()) {
                    if (response.body() == null || response.body().isEmpty()) {
                        Toast.makeText(CancelledOrder.this, getString(R.string.no_data), Toast.LENGTH_SHORT).show();
                    } else {
                        data.clear();
                        data.addAll(response.body());
                        orderAdapter.notifyDataSetChanged();
                    }
                } else {
                    ErrorDialog.message(CancelledOrder.this, getString(R.string.cant_access), bind.getRoot());
                }
            }

            @Override
            public void onFailure(Call<List<OrderModel>> call, Throwable t) {
                LoadingDialog.close();
                ErrorDialog.message(CancelledOrder.this, getString(R.string.trouble), bind.getRoot());
//                Toast.makeText(CancelledOrder.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        fetchData();
    }
}