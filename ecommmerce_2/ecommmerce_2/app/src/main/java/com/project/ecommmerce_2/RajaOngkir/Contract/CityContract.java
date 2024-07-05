package com.project.ecommmerce_2.RajaOngkir.Contract;

import android.widget.EditText;

import com.jakewharton.rxbinding3.widget.TextViewTextChangeEvent;
import com.project.ecommmerce_2.RajaOngkir.DataProcessing.city.CityModel;
import com.project.ecommmerce_2.RajaOngkir.DataProcessing.city.CityResponse;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

public interface CityContract {
    interface View{
        void onLoadingSearch(boolean loading);
        void onResultSearch(CityResponse response);
        void onErrorSearch();

        void onResultInstantSearch(List<CityModel> data);

        void showMessage(String msg);
    }

    interface Presenter{
        void getCity();
        void instantSearch(EditText editText, List<CityModel> data);
        void searchCity(List<CityModel> data, String keyword);
        DisposableObserver<TextViewTextChangeEvent> observer(List<CityModel> data);
    }
}
