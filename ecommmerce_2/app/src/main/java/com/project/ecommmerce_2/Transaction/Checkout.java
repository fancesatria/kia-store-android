package com.project.ecommmerce_2.Transaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.project.ecommmerce_2.Adapter.CartAdapter;
import com.project.ecommmerce_2.Adapter.CheckoutAdapter;
import com.project.ecommmerce_2.Component.ErrorDialog;
import com.project.ecommmerce_2.Component.LoadingDialog;
import com.project.ecommmerce_2.Helper.API;
import com.project.ecommmerce_2.Helper.Modul;
import com.project.ecommmerce_2.Helper.RajaOngkirHelper;
import com.project.ecommmerce_2.Helper.SPHelper;
import com.project.ecommmerce_2.Model.CartItem;
import com.project.ecommmerce_2.Model.PaymentModel;
import com.project.ecommmerce_2.R;
import com.project.ecommmerce_2.RajaOngkir.Adapter.CityAdapter;
import com.project.ecommmerce_2.RajaOngkir.Adapter.OngkirAdapter;
import com.project.ecommmerce_2.RajaOngkir.Contract.OngkirContract;
import com.project.ecommmerce_2.RajaOngkir.DataProcessing.cost.DataCourier;
import com.project.ecommmerce_2.RajaOngkir.Presenter.OngkirPresenter;
import com.project.ecommmerce_2.Response.PaymentResponse;
import com.project.ecommmerce_2.databinding.ActivityCheckoutBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Checkout extends AppCompatActivity implements OngkirContract.View, CartAdapter.CartUpdateListener {

    private static final int REQUEST_SOURCE = 1;
    private static final int REQUEST_DESTINATION = 2;
    private CheckoutAdapter cartAdapter;
    private List<CartItem> cartItems;
    private SPHelper spHelper;
    private int subtotalPrice;
    private int shipping_charge = 0;
    private String selectedCourier, selectedService, totalPrice;

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
        subtotalPrice = Integer.parseInt(getIntent().getStringExtra("total_price"));
        loadCourier();
        loadCartItems();
        setUpCartItems();
        loadSelectedCourier();
        calculateTotal();


        bind.inputKotaTujuan.setFocusable(false);
        bind.inputKotaTujuan.setClickable(true);
        bind.inputKotaTujuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchCity.class);
                intent.putExtra("requestCode", REQUEST_DESTINATION);
                startActivityForResult(intent, REQUEST_DESTINATION);
            }
        });

        bind.btnPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(shipping_charge == 0){

                } else {
                    createPaymentRequest(cartItems);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSelectedCourier();
        calculateTotal();
    }

    public void loadSelectedCourier(){
        shipping_charge = OngkirAdapter.selected_shipping_charge;
        selectedCourier = OngkirAdapter.selected_courier;
        selectedService = OngkirAdapter.selected_service;
    }

    public void loadCartItems(){

        if(getIntent().hasExtra("selectedItems")){
            cartItems = (List<CartItem>) getIntent().getSerializableExtra("selectedItems");
        } else {
            cartItems = new ArrayList<>();
            Toast.makeText(this, getString(R.string.no_selected), Toast.LENGTH_SHORT).show();
        }
    }

    public void loadCourier(){
        adapter = new OngkirAdapter(this, data, courier);
        bind.rvMain.setLayoutManager(new LinearLayoutManager(this));
        bind.rvMain.setAdapter(adapter);
    }

    public void setUpCartItems(){
        cartAdapter = new CheckoutAdapter(cartItems, this, this);
        bind.cartItems.setLayoutManager(new LinearLayoutManager(this));
        bind.cartItems.setAdapter(cartAdapter);
    }

    @Override
    public void onLoadingCost(boolean loading, int progress) {
        if (loading) {
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
        if (data.isEmpty() || courier.isEmpty()) {
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        }
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
            presenter.setENV(getOrigin(), getDestination(), 1000);
        }
    }

    private void createPaymentRequest(List<CartItem> selectedItems) {
        LoadingDialog.load(Checkout.this);

        List<PaymentModel.Item> items = new ArrayList<>();
        for (CartItem item : selectedItems) {
            items.add(new PaymentModel.Item(String.valueOf(item.getId()), String.valueOf(item.getQuantity())));
        }

        PaymentModel paymentModel = new PaymentModel(
                spHelper.getIdPengguna(),
                items,
                CityAdapter.province_name,
                CityAdapter.city_name,
                "Alamat",
                OngkirAdapter.selected_courier,
                String.valueOf(shipping_charge)
                );

        Call<PaymentResponse> call = API.getRetrofit(Checkout.this).createPayment(paymentModel);
        call.enqueue(new Callback<PaymentResponse>() {
            @Override
            public void onResponse(Call<PaymentResponse> call, Response<PaymentResponse> response) {
                LoadingDialog.close();
                if (response.isSuccessful() && response.body() != null) {
                    PaymentResponse paymentResponse = response.body();
                    if (paymentResponse.isSuccess()) {
                        String snapToken = paymentResponse.getSnap_token();

                        Intent intent = new Intent(Checkout.this, Payment.class);
                        intent.putExtra("snap_token", snapToken);
                        intent.putExtra("total_price", totalPrice);
                        startActivity(intent);
                    } else {
                        ErrorDialog.message(Checkout.this, getString(R.string.payment_failed), bind.getRoot());
                    }
                } else {
                    ErrorDialog.message(Checkout.this, getString(R.string.payment_unable), bind.getRoot());
                }
            }

            @Override
            public void onFailure(Call<PaymentResponse> call, Throwable t) {
                LoadingDialog.close();
                ErrorDialog.message(Checkout.this, getString(R.string.trouble), bind.getRoot());
            }
        });
    }

    @Override
    public void onCartUpdated() {
        // Update the cart here
    }

    public void calculateTotal(){
        totalPrice= String.valueOf(subtotalPrice + shipping_charge);
        bind.txtTotalPrice.setText("Total: Rp. " + Modul.numberFormat(String.valueOf(totalPrice)));
    }
}
