package com.karta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.karta.api.ApiClient;
import com.karta.model.status.StatusRequest;
import com.karta.model.status.StatusResponse;
import com.karta.model.tampil.TampilResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahstatusActivity extends AppCompatActivity {

    private EditText etNamaketua, etNamaWakil, etRtStatus, etStatus;
    private Button btnTambahstatus;
    private TextView tvTambahdaftar;
    
    private ApiClient apiClient;

    public static String DATA_STATUS = "DATA_STATUS";
    public static String IS_EDIT = "IS_EDIT";

    private StatusResponse statusResponse;

    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambahstatus);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        
        apiClient = new ApiClient(this);
        
        etNamaketua = findViewById(R.id.etNamaketua);
        etNamaWakil = findViewById(R.id.etNamawakil);
        etRtStatus = findViewById(R.id.etRtStatus);
        etStatus = findViewById(R.id.etStatus);
        btnTambahstatus = findViewById(R.id.btnTambahstatus);
        tvTambahdaftar = findViewById(R.id.tvTambahdaftar);
        initView();

        if (getIntent().hasExtra(DATA_STATUS)){
            statusResponse = getIntent().getParcelableExtra(DATA_STATUS);
            isEdit = getIntent().getBooleanExtra(IS_EDIT, false);
            tampilStatus();
        }

        if (isEdit) {
            btnTambahstatus.setText("Ubah Data");
            tvTambahdaftar.setText("Edit Data Daftar Karang Taruna");
        }
    }

    private void tampilStatus() {
        etNamaketua.setText(statusResponse.getNameKetua());
        etNamaWakil.setText(statusResponse.getNameWakil());
        etRtStatus.setText(statusResponse.getRt());
        etStatus.setText(statusResponse.getStatus());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void initView() {
        btnTambahstatus.setOnClickListener(v -> {
            String nameketua = etNamaketua.getText().toString();
            String namewakil = etNamaWakil.getText().toString();
            String rtstatus = etRtStatus.getText().toString();
            String status = etStatus.getText().toString();
            StatusRequest request;
            if (isEdit) {
                request = new StatusRequest(
                        statusResponse.getId(), nameketua, namewakil, rtstatus, status
                );
                updateStatus(request);
            } else {
                request = new StatusRequest(
                        null, nameketua, namewakil, rtstatus, status
                );
                tambahstatus(request);
            }
        });
    }

    private void tambahstatus(StatusRequest request) {
        apiClient.getUserService().tambahstatus(request)
                .enqueue(new Callback<StatusResponse>() {
                    @Override
                    public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                        if (response.isSuccessful()) {
                            finish();
                            Toast.makeText(TambahstatusActivity.this, "Berhasil Tambah Data", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<StatusResponse> call, Throwable t) {
                        Toast.makeText(TambahstatusActivity.this, "Network Bermasalah", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateStatus(StatusRequest request) {
        apiClient.getUserService().updatestatus(request)
                .enqueue(new Callback<StatusResponse>() {
                    @Override
                    public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                        finish();
                        Toast.makeText(TambahstatusActivity.this, "Berhasil Ubah Data", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<StatusResponse> call, Throwable t) {
                        Toast.makeText(TambahstatusActivity.this, "Network Bermasalah", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}