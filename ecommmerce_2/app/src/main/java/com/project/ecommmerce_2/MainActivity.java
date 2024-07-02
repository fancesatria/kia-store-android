package com.project.ecommmerce_2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.project.ecommmerce_2.Cart.CartFragment;
import com.project.ecommmerce_2.Category.CategoryFragment;
import com.project.ecommmerce_2.Database.Repository.ProductRepository;
import com.project.ecommmerce_2.Home.HomeFragment;
import com.project.ecommmerce_2.User.UserFragment;
import com.project.ecommmerce_2.databinding.ActivityMainBinding;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding bind;
    private MeowBottomNavigation meowBottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

//        getSupportActionBar().hide();
        meow();
    }

    public void meow(){
        meowBottomNavigation = bind.meownav;

        meowBottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_home));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_category));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_cart));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.ic_account));

        meowBottomNavigation.show(1, true);
        meowBottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId()){
                    case 1 :
                        replace(new HomeFragment());
                        break;
                    case 2:
                        replace(new CategoryFragment());
                        break;
                    case 3 :
                        replace(new CartFragment());
                        break;
                    case 4 :
                        replace(new UserFragment());
                        break;
                }
                return null;
            }
        });
    }

    private void replace(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(Integer.valueOf(R.id.framelayout), fragment).commit();
    }
}