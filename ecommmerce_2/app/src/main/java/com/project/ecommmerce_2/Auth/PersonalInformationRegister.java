package com.project.ecommmerce_2.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.project.ecommmerce_2.Component.ErrorDialog;
import com.project.ecommmerce_2.Component.LoadingDialog;
import com.project.ecommmerce_2.Component.SuccessDialog;
import com.project.ecommmerce_2.Helper.API;
import com.project.ecommmerce_2.Helper.SPHelper;
import com.project.ecommmerce_2.MainActivity;
import com.project.ecommmerce_2.Model.RegisterModel;
import com.project.ecommmerce_2.R;
import com.project.ecommmerce_2.Response.RegisterResponse;
import com.project.ecommmerce_2.databinding.ActivityPersonalInformationRegisterBinding;
import com.project.ecommmerce_2.databinding.ActivityRegisterBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalInformationRegister extends AppCompatActivity {

    ActivityPersonalInformationRegisterBinding bind;
    EditText etPhone, etPostalCode, etAddress;
    String province_name, city_name;
    int province_id, city_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityPersonalInformationRegisterBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
    }

    public void buatAkun(View view) {
        SPHelper sp = new SPHelper(PersonalInformationRegister.this);
        if (validasi()){

        } else {
            RegisterModel rg = new RegisterModel();
            rg.setEmail(getIntent().getStringExtra("email"));
            rg.setPassword(getIntent().getStringExtra("password"));
            rg.setName(getIntent().getStringExtra("username"));
            rg.setNomor_handphone(bind.phone.getText().toString());
            rg.setAlamat_lengkap(bind.address.getText().toString());
            rg.setKode_pos(bind.postalCode.getText().toString());
            prosesBuatAkun(rg);

        }
    }

    public void prosesBuatAkun(RegisterModel registerModel){
        LoadingDialog.load(PersonalInformationRegister.this);
        SPHelper sp = new SPHelper(PersonalInformationRegister.this);
        Call<RegisterResponse> registerResponseCall = API.getRetrofit().register(registerModel);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                LoadingDialog.close();
                if (response.isSuccessful()){
                    //simpan token dan username
                    sp.setToken(response.body().getToken());
                    sp.setEmail(response.body().getData().getEmail());
                    sp.setUsername(response.body().getData().getName());
                    sp.setIdPengguna(response.body().getData().getId());

                    SuccessDialog.message(PersonalInformationRegister.this, "Akun berhasil dibuat", bind.getRoot());

                    startActivity(new Intent(PersonalInformationRegister.this, MainActivity.class));
                    finish();

                } else {
                    ErrorDialog.message(PersonalInformationRegister.this, "Tidak dapat membuat akun", bind.getRoot());
                    Toast.makeText(PersonalInformationRegister.this, String.valueOf(response), Toast.LENGTH_LONG).show();
                }
//                Toast.makeText(Register.this, String.valueOf(response), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                LoadingDialog.close();
                Toast.makeText(PersonalInformationRegister.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean validasi() {
        etPhone = bind.phone;
        etAddress = bind.address;
        etPostalCode = bind.postalCode;
        if(etPhone.getText().toString().isEmpty() || etAddress.getText().toString().isEmpty() || etPostalCode.getText().toString().isEmpty()) {
            etPhone.requestFocus();
            etPhone.setError("Harap diisi");

            etAddress.requestFocus();
            etAddress.setError("Harap diisi");

            etPostalCode.requestFocus();
            etPostalCode.setError("Harap diisi");
            return true;

        } else {
            Toast.makeText(this, "Harap tunggu...", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}