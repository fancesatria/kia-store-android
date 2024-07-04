package com.project.ecommmerce_2.RajaOngkir.Contract;

import com.project.ecommmerce_2.RajaOngkir.DataProcessing.cost.DataCourier;

import java.util.List;

public interface OngkirContract {
    interface Presenter{
        void getJNE();
        void getPOS();
        void getTIKI();
        void setENV(String origin, String destination, int weight);
    }

    interface View{
        void onLoadingCost(boolean loading, int progress);
        void onResultCost(List<DataCourier> data, List<String> courier);
        void onErrorCost();

        void showMessage(String message);
        String getOrigin();
        String getDestination();

        void onCartUpdated();
    }
}
