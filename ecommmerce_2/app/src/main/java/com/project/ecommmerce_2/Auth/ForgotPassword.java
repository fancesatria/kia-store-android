package com.project.ecommmerce_2.Auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Toast;

import com.project.ecommmerce_2.Helper.API;
import com.project.ecommmerce_2.Model.ForgotPasswordModel;
import com.project.ecommmerce_2.R;
import com.project.ecommmerce_2.Response.ForgotPasswordResponse;
import com.project.ecommmerce_2.databinding.ActivityForgotPasswordBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassword extends AppCompatActivity {
    ActivityForgotPasswordBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        bind.countDown.setVisibility(View.INVISIBLE);


        bind.btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = bind.email.getText().toString().trim();
                if (!email.isEmpty()) {
                    ForgotPasswordModel fp = new ForgotPasswordModel(bind.email.getText().toString());
                    sendForgotPasswordRequest(fp);
                } else {
                    Toast.makeText(ForgotPassword.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendForgotPasswordRequest(ForgotPasswordModel forgotPasswordModel) {
        bind.btnlogin.setEnabled(false);
        bind.btnlogin.setBackgroundColor(ContextCompat.getColor(this, R.color.lightergrey));

        Call<ForgotPasswordResponse> call = API.getRetrofit(this).forgotPassword(forgotPasswordModel);
        call.enqueue(new Callback<ForgotPasswordResponse>() {
            @Override
            public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {
                if (response.isSuccessful()) {
                    ForgotPasswordResponse forgotPasswordResponse = response.body();
                    if (forgotPasswordResponse != null && "success".equals(forgotPasswordResponse.getStatus())) {
                        Toast.makeText(ForgotPassword.this, "LInk berhasil dikirimkan ke email anda", Toast.LENGTH_SHORT).show();
                        startCountdown();
                        startActivity(new Intent(ForgotPassword.this, Login.class));
                        finish();
                    } else {
                        Toast.makeText(ForgotPassword.this, ""+response.message(), Toast.LENGTH_SHORT).show();
                        bind.btnlogin.setEnabled(true);
                    }
                } else {
                    Toast.makeText(ForgotPassword.this, "Failed to send reset link", Toast.LENGTH_SHORT).show();
                    bind.btnlogin.setEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {
                Toast.makeText(ForgotPassword.this, "An error occurred", Toast.LENGTH_SHORT).show();
                bind.btnlogin.setEnabled(true);
            }
        });
    }

    private void startCountdown() {
        bind.countDown.setVisibility(View.VISIBLE);
        new CountDownTimer(120000, 1000) {

            public void onTick(long millisUntilFinished) {
                long minutes = millisUntilFinished / 60000;
                long seconds = (millisUntilFinished % 60000) / 1000;
                String timeLeft = String.format("%02d:%02d", minutes, seconds);
                bind.countDown.setText(timeLeft);
            }

            public void onFinish() {
                bind.countDown.setText("05:00");
                bind.btnlogin.setEnabled(true);
                bind.btnlogin.setBackgroundColor(ContextCompat.getColor(ForgotPassword.this, R.color.black));
            }
        }.start();
    }
}
