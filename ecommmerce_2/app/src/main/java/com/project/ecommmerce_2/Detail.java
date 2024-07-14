package com.project.ecommmerce_2;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.project.ecommmerce_2.Component.ErrorDialog;
import com.project.ecommmerce_2.Component.LoadingDialog;
import com.project.ecommmerce_2.Component.SuccessDialog;
import com.project.ecommmerce_2.Helper.API;
import com.project.ecommmerce_2.Helper.Modul;
import com.project.ecommmerce_2.Helper.SPHelper;
import com.project.ecommmerce_2.Model.CartItem;
import com.project.ecommmerce_2.databinding.ActivityDetailBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detail extends AppCompatActivity {

    private ActivityDetailBinding bind;
    private int count = 1;
    private int total, ids, stock;
    private int weight;
    private String names, images, prices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        load();
        button();
    }

    public void load() {
        ids = getIntent().getIntExtra("id_product", 0);
        names = getIntent().getStringExtra("name");
        images = getIntent().getStringExtra("image");
        prices = getIntent().getStringExtra("price");
        stock = Integer.parseInt(getIntent().getStringExtra("stock"));

        String weight_intent = getIntent().getStringExtra("weight");
        if (weight_intent == null || weight_intent.isEmpty()){
            weight = 1;
        } else {
            weight = Integer.parseInt(weight_intent);
        }



        SPHelper sp = new SPHelper(this);
        bind.name.setText(names);
        Glide.with(this).load(API.ROOT_URL+images).into(bind.iv);
        bind.price.setText("Rp. "+ Modul.numberFormat(String.valueOf(Integer.valueOf(prices))));
        setTotal();
    }

    public void button() {
        bind.increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count <= stock ) {
                    count++;
                    bind.count.setText(String.valueOf(count));
                    setTotal();
                } else {
                    bind.increase.setClickable(false);
                }
            }
        });

        bind.decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count > 1) {
                    count--;
                    bind.count.setText(String.valueOf(count));
                    setTotal();
                } else {
                    bind.decrease.setClickable(false);
                }
            }
        });

        bind.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(Detail.this)
                        .setTitle("Konfirmasi")
                        .setMessage("Simpan di keranjang?")
                        .setPositiveButton("Iya", (dialog, which) -> {
                            addToCart();
                            Toast.makeText(Detail.this, "Item ditambahkan ke keranjang", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Tidak", (dialog, which) -> {

                        })
                        .show();

            }
        });
    }

    private void addToCart() {
        SPHelper spHelper = new SPHelper(this);

        // Buat objek CartItem
        CartItem cartItem = new CartItem(ids, names, prices, images, count, weight);

        // Ubah objek CartItem menjadi JSON string
        Gson gson = new Gson();
        String cartItemJson = gson.toJson(cartItem);

        // Tambahkan item ke keranjang
        spHelper.addToCart(cartItemJson);
    }

    private void setTotal() {
        int price = Modul.strToInt(prices);
        total = price * count;
        weight *= count;
        bind.total.setText("Total: Rp. " + Modul.numberFormat(String.valueOf(Integer.valueOf(total))));
    }
}
