package com.karta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.karta.api.ApiClient;


public class MainActivity extends AppCompatActivity {
    private TextView tvTambahanggota;
    private CardView cvTambahanggota;
    private ApiClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiClient = new ApiClient();

        cvTambahanggota = findViewById(R.id.cvTambahanggota);
        initView();

    }

    private void initView() {
        cvTambahanggota.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TambahagtActivity.class);
            startActivity(intent);
        });
    }
}