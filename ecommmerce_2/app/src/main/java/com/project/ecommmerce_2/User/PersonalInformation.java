package com.project.ecommmerce_2.User;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.project.ecommmerce_2.Model.ProfileModel;
import com.project.ecommmerce_2.R;
import com.project.ecommmerce_2.RajaOngkir.Adapter.CityAdapter;
import com.project.ecommmerce_2.Transaction.SearchCity;
import com.project.ecommmerce_2.databinding.ActivityPersonalInformationBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalInformation extends AppCompatActivity {

    private ActivityPersonalInformationBinding bind;
    private EditText etPhone, etPostalCode, etAddress, etKecamatan;
    public static String new_province_id, new_province, new_city_id, new_city;
    private SPHelper sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityPersonalInformationBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        sp = new SPHelper(PersonalInformation.this);
        load(false);
        fetchData();

        bind.inputKotaTujuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchCity.class);
                intent.putExtra("requestCode", 2);
                startActivityForResult(intent, 2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            bind.inputKotaTujuan.setText(CityAdapter.city_name);
            bind.province.setText(CityAdapter.province_name);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        bind.inputKotaTujuan.setText(CityAdapter.city_name);
        bind.province.setText(CityAdapter.province_name);
    }

    public void load(boolean status){
        bind.phone.setEnabled(status);
        bind.inputKotaTujuan.setEnabled(status);
        bind.postalCode.setEnabled(status);
        bind.address.setEnabled(status);
        bind.kecamatan.setEnabled(status);
        bind.province.setEnabled(status);

        bind.btnEdit.setVisibility(View.VISIBLE);
        bind.btnSubmit.setVisibility(View.GONE);
    }

    public void btnEdit(View view){
        load(true);
        bind.btnEdit.setVisibility(View.GONE);
        bind.btnSubmit.setVisibility(View.VISIBLE);
    }

    public void btnSubmit(View view){
        load(false);
        bind.btnEdit.setVisibility(View.GONE);
        bind.btnSubmit.setVisibility(View.VISIBLE);

        new AlertDialog.Builder(PersonalInformation.this)
                .setTitle("Konfirmasi")
                .setMessage("Simpan data?")
                .setPositiveButton("Iya", (dialog, which) -> {
                    if (validasi()){

                    } else {
                        ProfileModel profileModel = new ProfileModel();
                        profileModel.setNomor_handphone(bind.phone.getText().toString());
                        profileModel.setKecamatan(bind.kecamatan.getText().toString());
                        profileModel.setKode_pos(bind.postalCode.getText().toString());
                        profileModel.setAlamat_lengkap(bind.postalCode.getText().toString());

                        profileModel.setProvinsi(CityAdapter.province_name);
                        profileModel.setKota_kabupaten(CityAdapter.city_name);

                        saveChanges(profileModel);
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    public void fetchData() {
        LoadingDialog.load(PersonalInformation.this);

        // Online
        Call<List<ProfileModel>> profileGetRespCall = API.getRetrofit(PersonalInformation.this).getProfile(sp.getIdPengguna());
        profileGetRespCall.enqueue(new Callback<List<ProfileModel>>() {
            @Override
            public void onResponse(Call<List<ProfileModel>> call, Response<List<ProfileModel>> response) {
                LoadingDialog.close();
                if (response.isSuccessful() && response.body() != null) {
                    bind.phone.setText(response.body().get(0).getNomor_handphone());
                    bind.postalCode.setText(response.body().get(0).getKode_pos());
                    bind.address.setText(response.body().get(0).getAlamat_lengkap());
                    bind.kecamatan.setText(response.body().get(0).getKecamatan());
                    bind.inputKotaTujuan.setText(response.body().get(0).getKota_kabupaten());
                    bind.province.setText(response.body().get(0).getProvinsi());
                } else {
                    ErrorDialog.message(PersonalInformation.this, getString(R.string.cant_access), bind.getRoot());
                }
            }

            @Override
            public void onFailure(Call<List<ProfileModel>> call, Throwable t) {
                LoadingDialog.close();
                ErrorDialog.message(PersonalInformation.this, getString(R.string.trouble), bind.getRoot());
//                Toast.makeText(PersonalInformation.this, ""+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    public void saveChanges(ProfileModel profileModel){
        LoadingDialog.load(PersonalInformation.this);
        SPHelper sp = new SPHelper(PersonalInformation.this);
        Call<ProfileModel> registerResponseCall = API.getRetrofit().updateProfile(sp.getIdPengguna() ,profileModel);
        registerResponseCall.enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                LoadingDialog.close();
                if (response.isSuccessful()){
                    load(true);
                    sp.setUserProvince(CityAdapter.province_name);
                    sp.setUserProvinceId(CityAdapter.province_id);
                    sp.setUserCity(CityAdapter.city_name);
                    sp.setUserCityId(CityAdapter.city_id);
                    sp.setUserAddress(bind.address.getText().toString());
                    sp.setUserPostalCode(bind.postalCode.getText().toString());
                    sp.setUserKecamatan(bind.kecamatan.getText().toString());

                    load(false);

                    SuccessDialog.message(PersonalInformation.this, getString(R.string.saved), bind.getRoot());
                    fetchData();
                } else {
                    ErrorDialog.message(PersonalInformation.this, getString(R.string.unsaved), bind.getRoot());
                }
//                Toast.makeText(Register.this, String.valueOf(response), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {
                LoadingDialog.close();
                ErrorDialog.message(PersonalInformation.this, getString(R.string.trouble), bind.getRoot());
                Toast.makeText(PersonalInformation.this, String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean validasi() {
        etPhone = bind.phone;
        etAddress = bind.address;
        etPostalCode = bind.postalCode;
        etKecamatan = bind.kecamatan;
        if(etPhone.getText().toString().isEmpty() || etAddress.getText().toString().isEmpty() || etPostalCode.getText().toString().isEmpty() || etKecamatan.getText().toString().isEmpty()) {
            etPhone.requestFocus();
            etPhone.setError("Harap diisi");

            etAddress.requestFocus();
            etAddress.setError("Harap diisi");

            etPostalCode.requestFocus();
            etPostalCode.setError("Harap diisi");

            etKecamatan.requestFocus();
            etKecamatan.setError("Harap diisi");
            return true;

        } else {
            Toast.makeText(this, "Harap tunggu...", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}