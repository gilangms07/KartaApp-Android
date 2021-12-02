package com.karta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.karta.api.ApiClient;
import com.karta.api.ErrorAPIConverter;
import com.karta.api.ErrorResponse;
import com.karta.model.edit.EditResponse;
import com.karta.model.tampil.TampilResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EdittampilagtActivity extends AppCompatActivity {

    EditText etNamaagt, etAlamatagt, etRtagt, etRwagt, etEmailagt, etHpagt;
    Button btnEditdata;
    ApiClient apiClient;

    public String DATA_ANGGOTA = "DATA_ANGGOTA";
    private TampilResponse tampilResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edittampilagt);
        apiClient = new ApiClient(this);
        etNamaagt = findViewById(R.id.etNamaagt);
        etAlamatagt = findViewById(R.id.etAlamatagt);
        etRtagt = findViewById(R.id.etRtagt);
        etRwagt = findViewById(R.id.etRwagt);
        etEmailagt = findViewById(R.id.etEmailagt);
        etHpagt = findViewById(R.id.etHpagt);
        btnEditdata = findViewById(R.id.btnEditdata);

        initView();

        if (getIntent().hasExtra(DATA_ANGGOTA)){
            tampilResponse = getIntent().getParcelableExtra(DATA_ANGGOTA);
            tampilData();
        }
    }

    private void initView(){
        btnEditdata.setOnClickListener(v -> {
            TampilResponse anggotaUpdate = new TampilResponse(
                    tampilResponse.getId(),
                    etNamaagt.getText().toString(),
                    etAlamatagt.getText().toString(),
                    etRtagt.getText().toString(),
                    etRwagt.getText().toString(),
                    etEmailagt.getText().toString(),
                    etHpagt.getText().toString()
            );
            update(anggotaUpdate);
        });
    }
    private void tampilData(){
        etNamaagt.setText(tampilResponse.getName());
        etAlamatagt.setText(tampilResponse.getAddress());
        etRtagt.setText(tampilResponse.getRt());
        etRwagt.setText(tampilResponse.getRw());
        etEmailagt.setText(tampilResponse.getEmail());
        etHpagt.setText(tampilResponse.getPhoneNumber());

    }
    private void update(TampilResponse anggota) {
        apiClient.getUserService().update(anggota).enqueue(new Callback<EditResponse>() {
            @Override
            public void onResponse(Call<EditResponse> call, Response<EditResponse> response) {
                if (response.isSuccessful()){
                    Toast.makeText(EdittampilagtActivity.this, "Update Berhasil", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    ErrorResponse errorResponse = ErrorAPIConverter.getItemErrorBody(response.errorBody(), EdittampilagtActivity.this);
                    Toast.makeText(EdittampilagtActivity.this, errorResponse.getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EditResponse> call, Throwable t) {

            }
        });
    }
}
