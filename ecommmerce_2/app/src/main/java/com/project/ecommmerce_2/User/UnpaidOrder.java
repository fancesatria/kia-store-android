package com.project.ecommmerce_2.User;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.project.ecommmerce_2.Adapter.FilterOrderAdapter;
import com.project.ecommmerce_2.Adapter.OrderAdapter;
import com.project.ecommmerce_2.Component.ErrorDialog;
import com.project.ecommmerce_2.Component.LoadingDialog;
import com.project.ecommmerce_2.Component.SuccessDialog;
import com.project.ecommmerce_2.Helper.API;
import com.project.ecommmerce_2.Helper.SPHelper;
import com.project.ecommmerce_2.Model.OrderModel;
import com.project.ecommmerce_2.R;
import com.project.ecommmerce_2.databinding.ActivityUnpaidOrderBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UnpaidOrder extends AppCompatActivity {

    private ActivityUnpaidOrderBinding bind;
    private SPHelper sp;
    private List<OrderModel> data = new ArrayList<>();
    private FilterOrderAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityUnpaidOrderBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        sp = new SPHelper(this);

        bind.item.setLayoutManager(new LinearLayoutManager(this));
        orderAdapter = new FilterOrderAdapter(UnpaidOrder.this, data);
        bind.item.setAdapter(orderAdapter);

        fetchData();
    }

    private void fetchData() {
        LoadingDialog.load(this);
        Call<List<OrderModel>> karyaGetRespCall = API.getRetrofit(this).getUnpaid(sp.getIdPengguna());
        karyaGetRespCall.enqueue(new Callback<List<OrderModel>>() {
            @Override
            public void onResponse(Call<List<OrderModel>> call, Response<List<OrderModel>> response) {
                LoadingDialog.close();
                if (response.isSuccessful()) {
                    List<OrderModel> orders = response.body();
                    if (orders == null || orders.isEmpty()) {
                        Toast.makeText(UnpaidOrder.this, getString(R.string.no_data), Toast.LENGTH_SHORT).show();
                    } else {
                        data.clear();
                        data.addAll(orders);
                        orderAdapter.notifyDataSetChanged();
                    }
                } else {
                    ErrorDialog.message(UnpaidOrder.this, getString(R.string.cant_access), bind.getRoot());
                }
            }

            @Override
            public void onFailure(Call<List<OrderModel>> call, Throwable t) {
                LoadingDialog.close();
                ErrorDialog.message(UnpaidOrder.this, getString(R.string.trouble), bind.getRoot());
            }
        });
    }


//    public void updateStatus(String snap_token, Context context) {
//        if (context == null) {
//            return; // Exit if the context is not available
//        }
//        new AlertDialog.Builder(context)
//                .setTitle("Konfirmasi")
//                .setMessage("Update status pembayaran?")
//                .setPositiveButton("Iya", (dialog, which) -> {
//                    LoadingDialog.load(context);
//                    Call<Void> call = API.getRetrofit(context).updateStatus(snap_token);
//                    call.enqueue(new Callback<Void>() {
//                        @Override
//                        public void onResponse(Call<Void> call, Response<Void> response) {
//                            LoadingDialog.close();
//                            if (response.isSuccessful()) {
//                                SuccessDialog.message(context, context.getString(R.string.saved), bind.getRoot());
//                                fetchData(); // Assuming fetchData() is a method in your fragment/activity
//                            } else {
//                                ErrorDialog.message(context, context.getString(R.string.unsaved), bind.getRoot());
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<Void> call, Throwable t) {
//                            LoadingDialog.close();
//                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                })
//                .setNegativeButton("Tidak", (dialog, which) -> {
//
//                })
//                .show();
//    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchData();
    }

}
