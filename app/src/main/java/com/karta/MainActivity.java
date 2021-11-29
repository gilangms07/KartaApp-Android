package com.karta;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.karta.api.ApiClient;


public class MainActivity extends AppCompatActivity {
    private TextView tvTambahanggota;
    private CardView cvTambahanggota, cvTampilanggota;
    private ApiClient apiClient;
    private ImageView imgLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiClient = new ApiClient();

        cvTampilanggota = findViewById(R.id.cvTampilanggota);
        cvTambahanggota = findViewById(R.id.cvTambahanggota);
        imgLogout = findViewById(R.id.imgLogout);
        initView();
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
        imgLogout.setOnClickListener(v -> {
            showDialog();
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