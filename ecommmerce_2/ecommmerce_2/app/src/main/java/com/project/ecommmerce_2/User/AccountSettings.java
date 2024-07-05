package com.project.ecommmerce_2.User;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.project.ecommmerce_2.R;
import com.project.ecommmerce_2.databinding.ActivityAccountSettingsBinding;

public class AccountSettings extends AppCompatActivity {
    ActivityAccountSettingsBinding bind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityAccountSettingsBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());


    }

}