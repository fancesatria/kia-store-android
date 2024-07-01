package com.project.ecommmerce_2.Transaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.project.ecommmerce_2.Helper.RajaOngkirHelper;
import com.project.ecommmerce_2.R;
import com.project.ecommmerce_2.RajaOngkir.Adapter.OngkirAdapter;
import com.project.ecommmerce_2.RajaOngkir.Contract.OngkirContract;
import com.project.ecommmerce_2.RajaOngkir.DataProcessing.cost.DataCourier;
import com.project.ecommmerce_2.RajaOngkir.Presenter.OngkirPresenter;
import com.project.ecommmerce_2.databinding.ActivityCheckoutBinding;

import java.util.ArrayList;
import java.util.List;

public class Checkout extends AppCompatActivity implements OngkirContract.View  {

    private static final int REQUEST_SOURCE = 1;
    private static final int REQUEST_DESTINATION = 2;

    private String source_id = RajaOngkirHelper.source_city_id;;
    private String destination_id = "";

    private OngkirPresenter presenter;
    private OngkirAdapter adapter;

    private List<DataCourier> data = new ArrayList<>();
    private List<String> courier = new ArrayList<>();
    private ActivityCheckoutBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        presenter = new OngkirPresenter(this);
        adapter = new OngkirAdapter(Checkout.this, data, courier);
        bind.rvMain.setLayoutManager(new LinearLayoutManager(Checkout.this));
        bind.rvMain.setAdapter(adapter);

        bind.inputKotaTujuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchCity.class);
                intent.putExtra("requestCode", REQUEST_DESTINATION);
                startActivityForResult(intent, REQUEST_DESTINATION);
            }
        });
    }

    @Override
    public void onLoadingCost(boolean loading, int progress) {
        if(loading){
            bind.llMain.setVisibility(View.VISIBLE);
            bind.rvMain.setVisibility(View.GONE);
            bind.progressBar.setProgress(progress);
        } else {
            bind.progressBar.setProgress(progress);
            bind.llMain.setVisibility(View.GONE);
            bind.rvMain.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResultCost(List<DataCourier> data, List<String> courier) {
        this.data.addAll(data);
        this.courier.addAll(courier);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onErrorCost() {
        bind.rvMain.setVisibility(View.GONE);
        bind.llMain.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getOrigin() {
        return source_id;
    }

    @Override
    public String getDestination() {
        return destination_id;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SOURCE && resultCode == RESULT_OK) {

        } else if (requestCode == REQUEST_DESTINATION && resultCode == RESULT_OK) {
            bind.inputKotaTujuan.setText(data.getStringExtra("city"));
            destination_id = data.getStringExtra("city_id");
        }
    }
}