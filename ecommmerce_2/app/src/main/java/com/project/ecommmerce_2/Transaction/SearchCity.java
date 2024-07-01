package com.project.ecommmerce_2.Transaction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.project.ecommmerce_2.R;
import com.project.ecommmerce_2.RajaOngkir.Adapter.CityAdapter;
import com.project.ecommmerce_2.RajaOngkir.Contract.CityContract;
import com.project.ecommmerce_2.RajaOngkir.DataProcessing.city.CityModel;
import com.project.ecommmerce_2.RajaOngkir.DataProcessing.city.CityResponse;
import com.project.ecommmerce_2.RajaOngkir.Presenter.CityPresenter;
import com.project.ecommmerce_2.databinding.ActivitySearchCityBinding;

import java.util.ArrayList;
import java.util.List;

public class SearchCity extends AppCompatActivity implements CityContract.View {
    ActivitySearchCityBinding bind;
    List<CityModel> dataSource = new ArrayList<>();
    List<CityModel> dataList = new ArrayList<>();
    List<CityModel> dataSearch = new ArrayList<>();

    CityAdapter adapter;
    CityPresenter presenter;
    EditText etSearch;
    ImageView ivBarBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivitySearchCityBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        etSearch = findViewById(R.id.etSearch);
        ivBarBack = findViewById(R.id.ivBarBack);

        adapter = new CityAdapter(this, dataList);
        bind.rvCity.setLayoutManager(new LinearLayoutManager(this));
        bind.rvCity.setAdapter(adapter);

        presenter = new CityPresenter(this);
        bind.refreshCity.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getCity();
            }
        });

        ivBarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getCity();
    }

    @Override
    public void onLoadingSearch(boolean loading) {
        if (loading){
            bind.refreshCity.setRefreshing(true);
        } else {
            bind.refreshCity.setRefreshing(false);
        }
    }

    @Override
    public void onResultSearch(CityResponse response) {
        dataSource.clear();
        dataList.clear();
        dataSearch.clear();

        if (response.getRajaongkir().getResult().size() != 0){
            dataSource.addAll(response.getRajaongkir().getResult());
            dataList.addAll(response.getRajaongkir().getResult());

            adapter.notifyDataSetChanged();
            presenter.instantSearch(etSearch, dataSource);
        }
    }

    @Override
    public void onErrorSearch() {
        bind.refreshCity.setRefreshing(false);
    }

    @Override
    public void onResultInstantSearch(List<CityModel> data) {
        if (data.size() != 0){
            dataSource.clear(); dataList.clear();
            dataSearch.addAll(data); dataList.addAll(data);

            adapter.notifyDataSetChanged();
        } else {
            dataList.clear(); dataList.addAll(data);

            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}