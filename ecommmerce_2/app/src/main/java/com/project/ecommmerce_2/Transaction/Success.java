package com.project.ecommmerce_2.Transaction;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import androidx.appcompat.app.AppCompatActivity;

import com.project.ecommmerce_2.databinding.ActivitySuccessBinding;

public class Success extends AppCompatActivity {
    ActivitySuccessBinding bind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivitySuccessBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        Animation fadeOut=new AlphaAnimation(1,0);
        bind.icon.setAnimation(fadeOut);

        bind.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}