package com.project.ecommmerce_2.Category;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.project.ecommmerce_2.Adapter.CategoryAdapter;
import com.project.ecommmerce_2.Adapter.ProductAdapter;
import com.project.ecommmerce_2.Component.ErrorDialog;
import com.project.ecommmerce_2.Component.LoadingDialog;
import com.project.ecommmerce_2.Database.Repository.ProductRepository;
import com.project.ecommmerce_2.Model.CategoryModel;
import com.project.ecommmerce_2.Model.ProductModel;
import com.project.ecommmerce_2.R;
import com.project.ecommmerce_2.Search;
import com.project.ecommmerce_2.databinding.FragmentCategoryBinding;
import com.project.ecommmerce_2.Helper.API;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment {

    private FragmentCategoryBinding bind;
    private CategoryAdapter categoryAdapter;
    private List<CategoryModel> categoryList;
    private ProductRepository productRepository;
    private List<ProductModel> data = new ArrayList<>();
    private ProductAdapter productAdapter;

    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentCategoryBinding.inflate(inflater, container, false);
        productRepository = new ProductRepository(requireActivity().getApplication());

        fetchCategories();
        fetchData();

        bind.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Search.class);
                startActivity(i);
            }
        });

        return bind.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchCategories();
    }

    private void fetchCategories() {
        Call<List<CategoryModel>> call = API.getRetrofit(getContext()).getCategories();
        call.enqueue(new Callback<List<CategoryModel>>() {
            @Override
            public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    categoryList = response.body();

                    // Add 1 static items "Semua/All"
                    CategoryModel allCategory = new CategoryModel();
                    allCategory.setId(-1); // ID khusus untuk "All"
                    allCategory.setNama("Semua");
                    categoryList.add(0, allCategory);

                    bind.itemCategory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                    categoryAdapter = new CategoryAdapter(CategoryFragment.this, categoryList);
                    bind.itemCategory.setAdapter(categoryAdapter);
                } else {
                    ErrorDialog.message(getContext(), getString(R.string.cant_access), bind.getRoot());
                }
            }

            @Override
            public void onFailure(Call<List<CategoryModel>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                ErrorDialog.message(getContext(), getString(R.string.trouble), bind.getRoot());
            }
        });
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
                    productAdapter.notifyDataSetChanged();
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
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                LoadingDialog.close();
                ErrorDialog.message(getContext(), getString(R.string.trouble), bind.getRoot());
            }
        });
    }

    public void fetchDataByCategory(int categor_id) {
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
                    productAdapter.notifyDataSetChanged();
                }
            }
        });

        // Online
        Call<List<ProductModel>> karyaGetRespCall = API.getRetrofit(getContext()).getProductsByCategory(categor_id);
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
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                LoadingDialog.close();
                ErrorDialog.message(getContext(), getString(R.string.trouble), bind.getRoot());
            }
        });
    }

}
