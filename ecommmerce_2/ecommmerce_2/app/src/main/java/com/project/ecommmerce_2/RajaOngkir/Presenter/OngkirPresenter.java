package com.project.ecommmerce_2.RajaOngkir.Presenter;

import android.os.Handler;

import androidx.annotation.NonNull;

import com.project.ecommmerce_2.Helper.API;
import com.project.ecommmerce_2.Helper.Modul;
import com.project.ecommmerce_2.Helper.RajaOngkirHelper;
import com.project.ecommmerce_2.Helper.Service;
import com.project.ecommmerce_2.RajaOngkir.Contract.OngkirContract;
import com.project.ecommmerce_2.RajaOngkir.DataProcessing.cost.CostResponse;
import com.project.ecommmerce_2.RajaOngkir.DataProcessing.cost.DataCourier;

import java.util.ArrayList;
import java.util.List;


import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class OngkirPresenter implements OngkirContract.Presenter {
    OngkirContract.View view;
    Service endPoint;

    String origin = "";
    String destination = "";
    int weight = 0;

    List<DataCourier> output = new ArrayList<>();
    List<String> courire = new ArrayList<>();

    public OngkirPresenter(OngkirContract.View view) {
        this.view = view;
        this.endPoint = API.getRetrofitRajaOngkir().create(Service.class);
    }

    @Override
    public void getJNE() {
        output.clear();
        courire.clear();
        endPoint.postCost(origin, destination, weight, "jne")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<CostResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull CostResponse costResponse) {
                        for (DataCourier data : costResponse.getRajaongkir().getResults().get(0).getCosts()){
                            output.add(data);
                            courire.add("JNE");
                        }
                        RajaOngkirHelper.jne = true;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showMessage(e.getMessage());
                    }
                });


    }

    @Override
    public void getPOS() {
        output.clear();
        courire.clear();
        endPoint.postCost(origin, destination, weight, "pos")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<CostResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull CostResponse costResponse) {
                        for(DataCourier data : costResponse.getRajaongkir().getResults().get(0).getCosts()){
                            output.add(data);
                            courire.add("POS");
                        }
                        RajaOngkirHelper.pos = true;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showMessage(e.getMessage());
                    }
                });
    }

    @Override
    public void getTIKI() {
        output.clear();
        courire.clear();
        endPoint.postCost(origin, destination, weight, "tiki")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<CostResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull CostResponse costResponse) {
                        for(DataCourier data : costResponse.getRajaongkir().getResults().get(0).getCosts()){
                            output.add(data);
                            courire.add("TIKI");
                        }
                        RajaOngkirHelper.tiki = true;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showMessage(e.getMessage());
                    }
                });
    }

    @Override
    public void setENV(String origin, String destination, int weight) {
        this.origin = origin;
        this.destination = destination;
        this.weight = weight;

        view.onLoadingCost(true, 25);
        getJNE();
        getPOS();
        getTIKI();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (RajaOngkirHelper.jne && RajaOngkirHelper.pos && RajaOngkirHelper.tiki){
                    view.onLoadingCost(false, 100);
                    view.onResultCost(output, courire);
                }
            }
        }, 4000L);
    }
}
