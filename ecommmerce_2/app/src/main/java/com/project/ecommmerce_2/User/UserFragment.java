package com.project.ecommmerce_2.User;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.project.ecommmerce_2.Auth.Login;
import com.project.ecommmerce_2.Helper.API;
import com.project.ecommmerce_2.Helper.SPHelper;
import com.project.ecommmerce_2.R;
import com.project.ecommmerce_2.Response.SourceAdressResponse;
import com.project.ecommmerce_2.Response.SourcePhoneResponse;
import com.project.ecommmerce_2.databinding.FragmentUserBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of getContext() fragment.
 */
public class UserFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FragmentUserBinding bind;
    private SPHelper sp;

    private String mParam1;
    private String mParam2;

    public String phone;

    public UserFragment() {
        // Required empty public constructor
    }

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for getContext() fragment
        bind = FragmentUserBinding.inflate(inflater, container, false);
        sp = new SPHelper(getContext());
        getPhone();
        load();
        return bind.getRoot();
    }

    public void load() {
        bind.txtUsername.setText(sp.getUsername());
        bind.txtEmail.setText(sp.getEmail());

        bind.unpaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), UnpaidOrder.class));
            }
        });
        bind.packed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PackedOrder.class));
            }
        });
        bind.shipped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ShippedOrder.class));
            }
        });
        bind.cancelled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CancelledOrder.class));
            }
        });
        bind.finished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), FinishedOrder.class));
            }
        });
        bind.history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Order.class));
            }
        });

        bind.userSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PersonalInformation.class);
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

    private void getPhone() {
        Call<SourcePhoneResponse> call = API.getRetrofit(getContext()).getSourcePhone();
        call.enqueue(new Callback<SourcePhoneResponse>() {
            @Override
            public void onResponse(Call<SourcePhoneResponse> call, Response<SourcePhoneResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SourcePhoneResponse sourcePhoneResponse = response.body();
                    if (response.isSuccessful()) {
                        phone = sourcePhoneResponse.getPhone();

                        bind.callSeller.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String message = "Halo, saya butuh bantuan";
                                String url = "https://wa.me/" + phone + "?text=" + Uri.encode(message);

                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(url));
                                startActivity(intent);
                            }
                        });
                    } else {
                        Toast.makeText(getContext(), "Failed to get phone", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), getString(R.string.cant_access), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SourcePhoneResponse> call, Throwable t) {
                Toast.makeText(getContext(), getString(R.string.trouble), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
