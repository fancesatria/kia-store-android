package com.project.ecommmerce_2.Home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;

import com.project.ecommmerce_2.Database.Repository.ProductRepository;
import com.project.ecommmerce_2.Search;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.project.ecommmerce_2.Adapter.ProductAdapter;
import com.project.ecommmerce_2.Adapter.SliderAdapter;
import com.project.ecommmerce_2.Auth.Login;
import com.project.ecommmerce_2.Component.ErrorDialog;
import com.project.ecommmerce_2.Component.LoadingDialog;
import com.project.ecommmerce_2.Helper.API;
import com.project.ecommmerce_2.Helper.SPHelper;
import com.project.ecommmerce_2.Model.ProductModel;
import com.project.ecommmerce_2.Model.SliderItem;
import com.project.ecommmerce_2.R;
import com.project.ecommmerce_2.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    FragmentHomeBinding bind;
    SliderView sliderView;
    private SliderAdapter adapter;
    List<ProductModel> data = new ArrayList<>();
    ProductAdapter productAdapter;
    ProductRepository productRepository;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private SPHelper sp;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bind = FragmentHomeBinding.inflate(inflater, container, false);
        productRepository = new ProductRepository(requireActivity().getApplication());

        load();
        bind.searchButton.setOnClickListener(view -> {
            Intent i = new Intent(getContext(), Search.class);
            startActivity(i);
        });

        return bind.getRoot();
    }

    public void load(){
        sp = new SPHelper(getContext());
        slider();
        fetchData();
        bind.greetings.setText("Hello, " + sp.getUsername());
    }

    public void slider(){
        sliderView = bind.imageSlider;
        adapter = new SliderAdapter(getContext());
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();
        renewItems();
        removeLastItem();
    }

    public void renewItems() {
        List<SliderItem> sliderItemList = new ArrayList<>();
        // Dummy data
        for (int i = 0; i < 5; i++) {
            SliderItem sliderItem = new SliderItem();
            sliderItem.setDescription("");
            if (i % 2 == 0) {
                sliderItem.setImageUrl("https://img.freepik.com/free-vector/flat-design-minimal-boutique-sale-background_23-2149337460.jpg");
            } else {
                sliderItem.setImageUrl("https://img.freepik.com/free-vector/horizontal-sale-banner-template_23-2148897328.jpg");
            }
            sliderItemList.add(sliderItem);
        }
        adapter.renewItems(sliderItemList);
    }

    public void removeLastItem() {
        if (adapter.getCount() - 1 >= 0)
            adapter.deleteItem(adapter.getCount() - 1);
    }

    public void fetchData() {
        LoadingDialog.load(getContext());

        // Offline
        LiveData<List<ProductModel>> offlineData = productRepository.getAllProduct();
        offlineData.observe((LifecycleOwner) getContext(), new Observer<List<ProductModel>>() {
            @Override
            public void onChanged(List<ProductModel> productModels) {
                data.clear(); // Clean previous data to prevent duplication
                if (productModels != null && !productModels.isEmpty()) {
                    data.addAll(productModels);
                    bind.item.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    productAdapter = new ProductAdapter(getContext(), data);
                    bind.item.setAdapter(productAdapter);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        // Online
        Call<List<ProductModel>> karyaGetRespCall = API.getRetrofit(getContext()).getDataProduct();
        karyaGetRespCall.enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                LoadingDialog.close();
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().size() == 0) {
                        Toast.makeText(getContext(), getString(R.string.no_data), Toast.LENGTH_SHORT).show();
                    } else {
                        productRepository.insertAll(response.body(), true); // Insert data to Room (clear prev data)
                    }
                } else {
                    ErrorDialog.message(getContext(), getString(R.string.cant_access), bind.getRoot());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                LoadingDialog.close();
                ErrorDialog.message(getContext(), getString(R.string.trouble), bind.getRoot());
            }
        });
    }


    public void logout() {
        sp = new SPHelper(getContext());
        new AlertDialog.Builder(getContext())
                .setTitle("Hapus Item")
                .setMessage("Ingin keluar?")
                .setPositiveButton("Iya", (dialog, which) -> {
                    sp.clearData();
                    startActivity(new Intent(getContext(), Login.class));
                    requireActivity().finish();
                })
                .setNegativeButton("Tidak", null)
                .show();
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchData();
    }
}
