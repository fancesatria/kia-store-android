package com.project.ecommmerce_2.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.project.ecommmerce_2.Component.ErrorDialog;
import com.project.ecommmerce_2.Component.LoadingDialog;
import com.project.ecommmerce_2.Component.SuccessDialog;
import com.project.ecommmerce_2.Helper.API;
import com.project.ecommmerce_2.Helper.SPHelper;
import com.project.ecommmerce_2.MainActivity;
import com.project.ecommmerce_2.Model.LoginModel;
import com.project.ecommmerce_2.R;
import com.project.ecommmerce_2.Response.LoginResponse;
import com.project.ecommmerce_2.Response.SourceAdressResponse;
import com.project.ecommmerce_2.User.PersonalInformation;
import com.project.ecommmerce_2.databinding.ActivityLoginBinding;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    ActivityLoginBinding bind;
    SPHelper sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityLoginBinding.inflate(getLayoutInflater());
        sp = new SPHelper(Login.this);
        setContentView(bind.getRoot());
    }

    public void login(View view){
        if (validasi()){

        } else {
            LoginModel loginModel = new LoginModel(bind.email.getText().toString(), bind.password.getText().toString());
            prosesLogin(loginModel);
//            startActivity(new Intent(Login.this, MainActivity.class));
//            finish();
        }
    }

    public void prosesLogin(LoginModel loginModel){

        LoadingDialog.load(Login.this);
        Call<LoginResponse> loginResponseCall = API.getRetrofit(Login.this).login(loginModel);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoadingDialog.close();
                if (response.isSuccessful()){
                    //simpan token dan username
                    sp.setToken(response.body().getToken());
                    sp.setEmail(response.body().getData().getEmail());
                    sp.setUsername(response.body().getData().getName());
                    sp.setIdPengguna(response.body().getData().getId());

                    startActivity(new Intent(Login.this, MainActivity.class));
                    finish();

                    getAddress();

                } else {

                    ErrorDialog.message(Login.this, "Akun tidak ditemukan, periksa kembali password anda", bind.getRoot());
                }
//                Toast.makeText(Login.this, String.valueOf(response), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                LoadingDialog.close();
                ErrorDialog.message(Login.this, "Akun tidak ditemukan, periksa kembali password anda", bind.getRoot());
//                Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void register(View view){
        startActivity(new Intent(Login.this, Register.class));
        finish();
    }

    public void forgotPassword(View view){
        startActivity(new Intent(Login.this, ForgotPassword.class));
        finish();
    }

    public boolean validasi(){
        EditText Username = bind.email;
        EditText Password = bind.password;
        if(Username.getText().toString().isEmpty() || Password.getText().toString().isEmpty()) {
            Username.requestFocus();
            Username.setError("Harap diisi");
            Password.requestFocus();
            Password.setError("Harap diisi");
            return true;
        } else if (!Username.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            ErrorDialog.message(Login.this, getString(R.string.is_email), bind.getRoot());
            return true;
        } else {

        }
        return false;
    }

    private void getAddress() {
        Call<SourceAdressResponse> call = API.getRetrofit(this).getSourceAddress();

        call.enqueue(new Callback<SourceAdressResponse>() {
            @Override
            public void onResponse(Call<SourceAdressResponse> call, Response<SourceAdressResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SourceAdressResponse SourceAdressResponse = response.body();
                    if (SourceAdressResponse.isSuccess()) {
                        sp.setSourceProvinceId(SourceAdressResponse.getData().getProvince());
                        sp.setSourceCityId(SourceAdressResponse.getData().getCity());
                        sp.setSourcePostalCode(SourceAdressResponse.getData().getCity());
                    } else {
                        Toast.makeText(Login.this, "Failed to get address", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Login.this, "Response not successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SourceAdressResponse> call, Throwable t) {
                Toast.makeText(Login.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}