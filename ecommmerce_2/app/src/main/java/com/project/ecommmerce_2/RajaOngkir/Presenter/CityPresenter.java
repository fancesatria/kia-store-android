package com.project.ecommmerce_2.RajaOngkir.Presenter;

import android.widget.EditText;

import androidx.annotation.NonNull;

import com.jakewharton.rxbinding3.widget.RxTextView;
import com.jakewharton.rxbinding3.widget.TextViewTextChangeEvent;
import com.project.ecommmerce_2.Helper.API;
import com.project.ecommmerce_2.Helper.Service;
import com.project.ecommmerce_2.RajaOngkir.Contract.CityContract;
import com.project.ecommmerce_2.RajaOngkir.DataProcessing.city.CityModel;
import com.project.ecommmerce_2.RajaOngkir.DataProcessing.city.CityResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class CityPresenter implements CityContract.Presenter {
    CityContract.View view;
    Service endPoint;

    public CityPresenter(CityContract.View view) {
        this.view = view;
        this.endPoint = API.getRetrofitRajaOngkir().create(Service.class);
    }

    @Override
    public void getCity() {
        view.onLoadingSearch(true);
        endPoint.getCity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<CityResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull CityResponse cityResponse) {
                        view.onLoadingSearch(false);
                        view.onResultSearch(cityResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.onLoadingSearch(false);
                        view.showMessage(e.getMessage());
                    }
                });
    }

    @Override
    public void instantSearch(EditText editText, List<CityModel> data) {
        new CompositeDisposable().add(
                RxTextView.textChangeEvents(editText)
                        .skipInitialValue()
                        .debounce(500, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(observer(data))
        );
    }

    @Override
    public void searchCity(List<CityModel> data, String keyword) {
        List<CityModel> output = new ArrayList<>();
        for(int i = 0; i < data.size(); i++){
            if(data.get(i).getCityName().equalsIgnoreCase(keyword)){
                output.add(data.get(i));
            }
        }
        view.onResultInstantSearch(output);
    }

    @Override
    public DisposableObserver<TextViewTextChangeEvent> observer(List<CityModel> data) {
        return new DisposableObserver<TextViewTextChangeEvent>() {
            @Override
            public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                String keyword = textViewTextChangeEvent.getText().toString();
                searchCity(data, keyword);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }
}
