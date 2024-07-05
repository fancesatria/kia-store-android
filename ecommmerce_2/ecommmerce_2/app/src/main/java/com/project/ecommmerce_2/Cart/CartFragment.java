package com.project.ecommmerce_2.Cart;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.ecommmerce_2.Adapter.CartAdapter;
import com.project.ecommmerce_2.Auth.Login;
import com.project.ecommmerce_2.Component.ErrorDialog;
import com.project.ecommmerce_2.Component.LoadingDialog;
import com.project.ecommmerce_2.Helper.API;
import com.project.ecommmerce_2.Helper.Modul;
import com.project.ecommmerce_2.Helper.SPHelper;
import com.project.ecommmerce_2.Model.CartItem;
import com.project.ecommmerce_2.Model.PaymentModel;
import com.project.ecommmerce_2.R;
import com.project.ecommmerce_2.Response.PaymentResponse;
import com.project.ecommmerce_2.Transaction.Checkout;
import com.project.ecommmerce_2.Transaction.Payment;
import com.project.ecommmerce_2.User.PersonalInformation;
import com.project.ecommmerce_2.databinding.FragmentCartBinding;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment implements CartAdapter.CartUpdateListener {

    private FragmentCartBinding binding;
    private CartAdapter cartAdapter;
    private List<CartItem> cartItems;
    private SPHelper spHelper;
    private int totalPrice, weight;

    private static final String TAG = "CartFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);

        initCart();
        setupCheckoutButton();

        return binding.getRoot();
    }

    private void setupCheckoutButton() {
        binding.btnCheckout.setOnClickListener(view -> {
            new AlertDialog.Builder(getContext())
                    .setTitle("Konfirmasi")
                    .setMessage("Checkout sekarang?")
                    .setPositiveButton("Iya", (dialog, which) -> {
                        List<CartItem> selectedItems = new ArrayList<>();
                        StringBuilder selectedItemsInfo = new StringBuilder();

                        // Kumpulkan item yang dipilih
                        for (CartItem item : cartItems) {
                            if (item.isChecked()) {
                                selectedItems.add(item);
                                selectedItemsInfo.append("Item ID: ").append(item.getId())
                                        .append(", Quantity: ").append(item.getQuantity())
                                        .append("\n");
                            }
                        }

                        if (!selectedItems.isEmpty()) {
//                            Toast.makeText(getContext(), selectedItemsInfo.toString(), Toast.LENGTH_LONG).show();

                            // createPaymentRequest(selectedItems);
                            Intent i = new Intent(getContext(), Checkout.class);
                            i.putExtra("selectedItems", new ArrayList<>(selectedItems));
                            i.putExtra("total_price", totalPrice);
                            i.putExtra("total_weight", weight);
                            startActivity(i);
                        } else {
                            Toast.makeText(getContext(), getString(R.string.no_selected), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();

        });
    }

    private void initCart() {
        spHelper = new SPHelper(getActivity());
        cartItems = getCartItems(spHelper);

        if (cartItems == null || cartItems.isEmpty()) {
            if (getContext() != null) {
                Toast.makeText(getContext(), "Tak ada item untuk ditampilkan", Toast.LENGTH_SHORT).show();
            }
        }

        cartAdapter = new CartAdapter(cartItems, getActivity(), this);
        binding.item.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.item.setAdapter(cartAdapter);

        updateTotalPriceAndCheckoutButton();
    }


    private List<CartItem> getCartItems(SPHelper spHelper) {
        List<String> cartItemsJson = spHelper.getCartItems();
        Gson gson = new Gson();
        Type type = new TypeToken<List<CartItem>>() {}.getType();
        return gson.fromJson(cartItemsJson.toString(), type);
    }

    private void updateTotalPriceAndCheckoutButton() {
        totalPrice = 0;
        weight = 0;
        boolean hasCheckedItems = false;

        for (CartItem item : cartItems) {
            if (item.isChecked()) {
                totalPrice += Integer.parseInt(item.getPrice()) * item.getQuantity();
                weight += item.getWeight();
                hasCheckedItems = true;
            }
        }

        binding.txtTotalPrice.setText("Total: Rp. " + Modul.numberFormat(String.valueOf(totalPrice)));

        if (hasCheckedItems) {
            binding.btnCheckout.setEnabled(true);
            binding.btnCheckout.setBackgroundColor(Color.parseColor("#800000")); // Maroon color
        } else {
            binding.btnCheckout.setEnabled(false);
            binding.btnCheckout.setBackgroundColor(Color.parseColor("#DADADA")); // Gray color
        }
    }

    @Override
    public void onCartUpdated() {
        updateTotalPriceAndCheckoutButton();
    }

}
