package com.karta;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.karta.api.ApiClient;
import com.karta.model.register.RegisterResponse;
import com.karta.model.user.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    private CardView cvTambahanggota, cvTampilanggota, cvKegiatan, cvKeuangan, cvDaftar, cvAdmin;
    private ApiClient apiClient;
    private ImageView imgLogout;
    private TextView tvNamamain, tvRTRW;

    private PreferenceUtil preferenceUtil;

    private UserResponse userResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferenceUtil = new PreferenceUtil(this);
        apiClient = new ApiClient(this);

        cvTampilanggota = findViewById(R.id.cvTampilanggota);
        cvTambahanggota = findViewById(R.id.cvTambahanggota);
        cvKegiatan = findViewById(R.id.cvKegiatan);
        cvKeuangan = findViewById(R.id.cvKeuangan);
        cvDaftar = findViewById(R.id.cvDaftar);
        cvAdmin = findViewById(R.id.cvAdmin);
        tvNamamain = findViewById(R.id.tvNamamain);
        tvRTRW = findViewById(R.id.tvRWRT);
        imgLogout = findViewById(R.id.imgLogout);
        initView();

        getUser(preferenceUtil.getEmail());
    }

    private void initView() {
        cvTambahanggota.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TambahagtActivity.class);
            startActivity(intent);
        });

        cvTampilanggota.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TampilagtActivity.class);
            startActivity(intent);
        });

        cvKeuangan.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, KeuanganActivity.class);
            startActivity(intent);
        });

        cvKegiatan.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, KegiatanActivity.class);
            startActivity(intent);
        });

        cvDaftar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, StatuskartaActivity.class);
            startActivity(intent);
        });

        cvAdmin.setOnClickListener(v -> {
            if (userResponse.getLevel() == 2) {
                Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                startActivity(intent);
            } else {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("Peringatan");
                alertDialog.setMessage("Menu hanya bisa diakses oleh Super Admin");
                alertDialog.create().show();
            }
        });

        imgLogout.setOnClickListener(v -> {
            showDialog();
            preferenceUtil.setStatus(false);
        });
    }

    private void getUser(String email) {
        apiClient.getUserService().getUser(email)
                .enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        if (response.isSuccessful()) {
                            userResponse = response.body();
                            tvNamamain.setText(userResponse.getName());
                            tvRTRW.setText("RT " + userResponse.getRt() + "/ RW" + userResponse.getRw());
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {

                    }
                });
    }

    private void showDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Yakin ingin logout?");
        alertDialogBuilder
                .setMessage("Kelik Ya untuk Keluar!")
                .setIcon(R.drawable.kartalogo)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                            MainActivity.this.finish();
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }
}
