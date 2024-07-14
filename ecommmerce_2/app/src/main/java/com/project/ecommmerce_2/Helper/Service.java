package com.project.ecommmerce_2.Helper;

import com.project.ecommmerce_2.Model.BannerModel;
import com.project.ecommmerce_2.Model.CategoryModel;
import com.project.ecommmerce_2.Model.ForgotPasswordModel;
import com.project.ecommmerce_2.Model.LoginModel;
import com.project.ecommmerce_2.Model.OrderModel;
import com.project.ecommmerce_2.Model.PaymentModel;
import com.project.ecommmerce_2.Model.ProductModel;
import com.project.ecommmerce_2.Model.ProfileModel;
import com.project.ecommmerce_2.Model.RegisterModel;
import com.project.ecommmerce_2.Model.SourceAddressModel;
import com.project.ecommmerce_2.RajaOngkir.DataProcessing.city.CityResponse;
import com.project.ecommmerce_2.RajaOngkir.DataProcessing.cost.CostResponse;
import com.project.ecommmerce_2.Response.ForgotPasswordResponse;
import com.project.ecommmerce_2.Response.LoginResponse;
import com.project.ecommmerce_2.Response.OrderDetailResponse;
import com.project.ecommmerce_2.Response.PaymentResponse;
import com.project.ecommmerce_2.Response.RegisterResponse;
import com.project.ecommmerce_2.Response.SourceAdressResponse;
import com.project.ecommmerce_2.Response.SourcePhoneResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {
    //AUTH +++++++++++++++++++++++++++++++++++
    @POST("custom-login_api")
    Call<LoginResponse> login(@Body LoginModel loginModel);
    @POST("custom-registration_api")
    Call<RegisterResponse> register(@Body RegisterModel registerModel);
    @POST("forgot-password-api")
    Call<ForgotPasswordResponse> forgotPassword(@Body ForgotPasswordModel forgotPasswordModel);



    //PROFILE +++++++++++++++++++++++++++++++++++
    @GET("profile_api/{id}")
    Call<List<ProfileModel>> getProfile(@Path("id") int id);
    @POST("profile_api/update/{id}")
    Call<ProfileModel> updateProfile(@Path("id") int  id, @Body ProfileModel profileModel);
    @GET("alamat")
    Call<SourceAdressResponse> getSourceAddress();
    @GET("phone")
    Call<SourcePhoneResponse> getSourcePhone();



    //PRODUCT +++++++++++++++++++++++++++++++++++
    @GET("barangs_api")//get
    Call<List<ProductModel>> getDataProduct();
    @GET("barangs_api")//get
    Call<List<ProductModel>> getDataProduct(@Query("search") String search);
    @GET("barangs_api/{id}")
    Call<List<ProductModel>> getDataProduct(@Path("id") int id);


    //CATEGORY +++++++++++++++++++++++++++++++++++
    @GET("kategori")
    Call<List<CategoryModel>> getCategories();
    @GET("kategori/{id}")
    Call<List<ProductModel>> getProductsByCategory(@Path("id") int categoryId);


    //BANNER +++++++++++++++++++++++++++++++++++
    @GET("banner")
    Call<BannerModel> getBanner();



    //ORDER +++++++++++++++++++++++++++++++++++
    @GET("orders_api/user/{id}") // Index order
    Call<List<OrderModel>> getDataOrder(@Path("id") int id);
    @GET("orders/detail/{snap_token}") // Order detail
    Call<OrderDetailResponse> getOrderDetails(@Path("snap_token") String snapToken);
    @POST("orders/update_status/{snap_token}") // Update status
    Call<Void> updateStatus(@Path("snap_token") String snap_token);
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("orders") // Create order
    Call<PaymentResponse> createPayment(@Body PaymentModel paymentModel);


    //ORDER FILTER +++++++++++++++++++++++++++++++++++
    @GET("orders/filter/belum-bayar/{id}")
    Call<List<OrderModel>> getUnpaid(@Path("id") int id);
    @GET("orders/filter/sudah-bayar/{id}")//get
    Call<List<OrderModel>> getPaid(@Path("id") int id);
    @GET("orders/filter/dikirim/{id}")//get
    Call<List<OrderModel>> getShipped(@Path("id") int id);
    @GET("orders/filter/selesai/{id}")//get
    Call<List<OrderModel>> getFinished(@Path("id") int id);
    @GET("orders/filter/dibatalkan/{id}")//get
    Call<List<OrderModel>> getCancelled(@Path("id") int id);


    // RAJA ONGKIR +++++++++++++++++++++++++++++++++++
    @GET("city")
    Single<CityResponse> getCity();
    @FormUrlEncoded
    @POST("cost")
    Single<CostResponse> postCost(
            @Field("origin") String origin,
            @Field("destination") String destination,
            @Field("weight") int weight,
            @Field("courier") String courier
    );
}
