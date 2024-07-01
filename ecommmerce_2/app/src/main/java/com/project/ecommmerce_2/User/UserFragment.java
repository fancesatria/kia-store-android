package com.project.ecommmerce_2.User;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.ecommmerce_2.Auth.Login;
import com.project.ecommmerce_2.Helper.SPHelper;
import com.project.ecommmerce_2.R;
import com.project.ecommmerce_2.Transaction.OrderFragment;
import com.project.ecommmerce_2.Transaction.PackedFragment;
import com.project.ecommmerce_2.Transaction.ShippedFragment;
import com.project.ecommmerce_2.Transaction.UnpaidFragment;
import com.project.ecommmerce_2.databinding.FragmentUserBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FragmentUserBinding bind;
    private SPHelper sp;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment User.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bind = FragmentUserBinding.inflate(inflater, container, false);
        sp = new SPHelper(getContext());


        load();
        return bind.getRoot();
    }

    public void load(){
        bind.txtUsername.setText(sp.getUsername());
        bind.txtEmail.setText(sp.getEmail());

        bind.unpaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UnpaidFragment unpaidFragment = new UnpaidFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, unpaidFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        bind.packed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackedFragment packedFragment = new PackedFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, packedFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        bind.shipped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShippedFragment shippedFragment = new ShippedFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, shippedFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        bind.history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderFragment orderFragment = new OrderFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, orderFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        bind.userSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AccountSettings.class);
                startActivity(intent);
            }
        });

        bind.logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }


    public void logout() {
        if (getContext() == null) {
            return;
        }
        new AlertDialog.Builder(getContext())
                .setTitle("Konfirmasi")
                .setMessage("Ingin keluar?")
                .setPositiveButton("Iya", (dialog, which) -> {
                    sp.clearData();
                    startActivity(new Intent(getContext(), Login.class));
                    getActivity().finish();
                })
                .setNegativeButton("Tidak", (dialog, which) -> {

                })
                .show();
    }
}