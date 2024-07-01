package com.project.ecommmerce_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.project.ecommmerce_2.Adapter.ProductAdapter;
import com.project.ecommmerce_2.Component.ErrorDialog;
import com.project.ecommmerce_2.Component.LoadingDialog;
import com.project.ecommmerce_2.Database.Repository.ProductRepository;
import com.project.ecommmerce_2.Helper.API;
import com.project.ecommmerce_2.Model.ProductModel;
import com.project.ecommmerce_2.databinding.ActivitySearchBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Search extends AppCompatActivity {
    private ActivitySearchBinding bind;
    private List<ProductModel> data = new ArrayList<>();
    private ProductAdapter adapter;
    private ProductRepository productRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        productRepository = new ProductRepository(getApplication());
        fetchData();
        cari();
    }

    public void fetchData(){
        LoadingDialog.load(Search.this);
        String keyword = bind.searhview.getQuery().toString();

        // Offline
        productRepository.getAllProduct(keyword).observe((LifecycleOwner) Search.this, new Observer<List<ProductModel>>() {
            @Override
            public void onChanged(List<ProductModel> productModels) {
                data.clear(); // Clean previous data to prevent duplication
                if (productModels != null && !productModels.isEmpty()) {
                    data.addAll(productModels);
                    bind.item.setLayoutManager(new GridLayoutManager(Search.this, 2));
                    adapter = new ProductAdapter(Search.this, data);
                    bind.item.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        // Online
        Call<List<ProductModel>> karyaGetRespCall = API.getRetrofit(Search.this).getDataProduct(keyword);
        karyaGetRespCall.enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                LoadingDialog.close();
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().size() == 0) {
                        Toast.makeText(Search.this, getString(R.string.no_data), Toast.LENGTH_SHORT).show();
                    } else {
                        productRepository.insertAll(response.body(), true); // Insert data to Room (clear prev data)
                    }
                } else {
                    ErrorDialog.message(Search.this, getString(R.string.cant_access), bind.getRoot());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                LoadingDialog.close();
                ErrorDialog.message(Search.this, getString(R.string.trouble), bind.getRoot());
//                Toast.makeText(Search.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void cari(){
        bind.searhview.setFocusable(false);
        bind.searhview.setClickable(true);

        bind.searhview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchData();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty()){
                    fetchData();
                }
                return false;
            }
        });
    }
}