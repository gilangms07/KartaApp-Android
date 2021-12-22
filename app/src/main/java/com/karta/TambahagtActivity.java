package com.karta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.karta.api.ApiClient;
import com.karta.api.ErrorAPIConverter;
import com.karta.api.ErrorResponse;
import com.karta.model.tambah.TambahRequest;
import com.karta.model.tambah.TambahResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahagtActivity extends AppCompatActivity {
    private EditText etNamaagt, etAlamatagt, etRtagt, etRwagt, etEmailagt, etHpagt;
    private Button btnTambahagt;

    private ApiClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambahagt);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        apiClient = new ApiClient(this);

        etNamaagt = findViewById(R.id.etNamaagt);
        etAlamatagt = findViewById(R.id.etAlamatagt);
        etRtagt = findViewById(R.id.etRtagt);
        etRwagt = findViewById(R.id.etRwagt);
        etEmailagt = findViewById(R.id.etEmailagt);
        etHpagt = findViewById(R.id.etHpagt);
        btnTambahagt = findViewById(R.id.btnTambahagt);
        initView();
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
        btnTambahagt.setOnClickListener(v -> {
            String name = etNamaagt.getText().toString();
            String alamat = etAlamatagt.getText().toString();
            String rt = etRtagt.getText().toString();
            String rw = etRwagt.getText().toString();
            String email = etEmailagt.getText().toString();
            String nohp = etHpagt.getText().toString();
            TambahRequest request = new TambahRequest(
                    name, alamat, rt, rw, email, nohp
            );
            tambah(request);
        });
    }

    private void tambah(TambahRequest request) {
        apiClient.getUserService().tambah(request)
                .enqueue(new Callback<TambahResponse>() {
                    @Override
                    public void onResponse(Call<TambahResponse> call, Response<TambahResponse> response) {
                        if (response.isSuccessful()) {
                            Intent intent = new Intent(TambahagtActivity.this, TampilagtActivity.class);
                            startActivity(intent);
                            TambahResponse tambahResponse = response.body();
                            finish();
                            Toast.makeText(TambahagtActivity.this, "Berhasil Tambah Data", Toast.LENGTH_SHORT).show();
                        } else {
                            ErrorResponse errorResponse = ErrorAPIConverter.getItemErrorBody(response.errorBody(), TambahagtActivity.this);
                            Toast.makeText(TambahagtActivity.this, errorResponse.getError(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<TambahResponse> call, Throwable t) {
                        Toast.makeText(TambahagtActivity.this, "Network Bermasalah" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
