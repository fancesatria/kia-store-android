package com.project.ecommmerce_2.Transaction;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.project.ecommmerce_2.Adapter.OrderDetailAdapter;
import com.project.ecommmerce_2.Component.ErrorDialog;
import com.project.ecommmerce_2.Component.LoadingDialog;
import com.project.ecommmerce_2.Component.SuccessDialog;
import com.project.ecommmerce_2.Helper.API;
import com.project.ecommmerce_2.Helper.Modul;
import com.project.ecommmerce_2.Model.OrderDetailModel;
import com.project.ecommmerce_2.R;
import com.project.ecommmerce_2.Response.OrderDetailResponse;
import com.project.ecommmerce_2.Response.SourcePhoneResponse;
import com.project.ecommmerce_2.User.PersonalInformation;
import com.project.ecommmerce_2.databinding.ActivityDetailOrderBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailOrder extends AppCompatActivity {
    ActivityDetailOrderBinding bind;
    private OrderDetailAdapter adapter;
    private List<OrderDetailModel> data = new ArrayList<>();
    private String snapToken;
    private String status, shipping_charge, total_price, resi, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityDetailOrderBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        snapToken = getIntent().getStringExtra("snap_token");

        adapter = new OrderDetailAdapter(this, data);

        bind.item.setLayoutManager(new LinearLayoutManager(this));
        bind.item.setAdapter(adapter);

        fetchOrderDetails(snapToken);
        getPhone();
        load(snapToken);
    }

    public void load(String snap_token){
        bind.txtNominalTransaksi.setText(String.valueOf("Rp. "+Modul.numberFormat(getIntent().getStringExtra("total_harga"))));
        bind.txtTanggalTransaksi.setText(String.valueOf(getIntent().getStringExtra("tanggal_transaksi")));

        bind.imgCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Copy resi to clipboard
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Resi", bind.txtResi.getText().toString());
                clipboardManager.setPrimaryClip(clip);

                Toast.makeText(DetailOrder.this, "Nomor resi telah disalin ke clipboard", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchOrderDetails(snapToken);

    }

    private void fetchOrderDetails(String snapToken) {
        LoadingDialog.load(DetailOrder.this);
        Call<OrderDetailResponse> call = API.getRetrofit(DetailOrder.this).getOrderDetails(snapToken);
        call.enqueue(new Callback<OrderDetailResponse>() {
            @Override
            public void onResponse(Call<OrderDetailResponse> call, Response<OrderDetailResponse> response) {
                LoadingDialog.close();
                if (response.isSuccessful() && response.body() != null) {
                    OrderDetailResponse orderDetailResponse = response.body();
                    data.clear();
                    data.addAll(orderDetailResponse.getOrders());
                    adapter.notifyDataSetChanged();

                    status = orderDetailResponse.getStatus();
                    shipping_charge = orderDetailResponse.getOngkir();
                    total_price = orderDetailResponse.getHarga_final();
                    resi = orderDetailResponse.getResi();

                    bind.txtStatus.setText(status);
                    bind.txtShippingCharge.setText(String.valueOf("Rp. "+Modul.numberFormat(shipping_charge)));
                    bind.txtResi.setText(resi);

                    if (status.equalsIgnoreCase("success")) {
                        bind.txtStatus.setBackgroundResource(R.drawable.background_status_teal);
                        bind.btnBayar.setVisibility(View.GONE);
                    } else {
                        bind.txtStatus.setBackgroundResource(R.drawable.backgound_status_maroon);
                        bind.btnBayar.setVisibility(View.VISIBLE);
                    }

                    Toast.makeText(DetailOrder.this, ""+orderDetailResponse.getStatus(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DetailOrder.this, "Response not successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrderDetailResponse> call, Throwable t) {
                LoadingDialog.close();
                ErrorDialog.message(DetailOrder.this, getString(R.string.trouble), bind.getRoot());
//                Toast.makeText(DetailOrder.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getPhone() {
        Call<SourcePhoneResponse> call = API.getRetrofit(DetailOrder.this).getSourcePhone();

        call.enqueue(new Callback<SourcePhoneResponse>() {
            @Override
            public void onResponse(Call<SourcePhoneResponse> call, Response<SourcePhoneResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SourcePhoneResponse sourcePhoneResponse = response.body();
                    if (response.isSuccessful()) {
                        phone = sourcePhoneResponse.getPhone();
                        bind.btnBayar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String message = "Halo, saya ingin membatalkan pesanan";
                                String url = "https://wa.me/" + phone + "?text=" + Uri.encode(message);

                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(url));
                                startActivity(intent);
                            }
                        });
                    } else {
                        Toast.makeText(DetailOrder.this, "Failed to get phone", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DetailOrder.this, getString(R.string.cant_access), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SourcePhoneResponse> call, Throwable t) {
                Toast.makeText(DetailOrder.this, getString(R.string.trouble), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
