package com.karta;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.karta.api.ApiClient;
import com.karta.api.ErrorAPIConverter;
import com.karta.api.ErrorResponse;
import com.karta.model.iuran.IuranRequest;
import com.karta.model.iuran.IuranResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormKeuanganActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    StatusAdapter adapter;
    ApiClient apiClient;
    EditText edtStatus, edtNama, edtUrt;
    Button btnTambahData;
    Spinner spinner;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formkeuangan);
        apiClient = new ApiClient(this);
        edtStatus = findViewById(R.id.edtStatus);
        edtNama = findViewById(R.id.edtNama);
        edtUrt = findViewById(R.id.edtUrt);
        btnTambahData = findViewById(R.id.btnTambahData);
        spinner = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        initView();
    }

    private void initView() {
        btnTambahData.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                long id = 0;
                String name = edtNama.getText().toString();
                String month = spinner.getSelectedItem().toString();
                String rt = edtUrt.getText().toString();
                String status = edtStatus.getText().toString();
                if (name != null && name != "" && month != null && month != "" && rt != null && rt!= "" && status != null && status != ""){
                    IuranRequest request = new IuranRequest(id, name, month, status, rt);
                    buatIuran(request);
                }
            }
        });
    }

    private void buatIuran(IuranRequest iuran) {
        apiClient.getUserService().buatIuran(iuran).enqueue(new Callback<IuranResponse>() {
            @Override
            public void onResponse(Call<IuranResponse> call, Response<IuranResponse> response) {
                if (response.isSuccessful()){
                    Toast.makeText(FormKeuanganActivity.this, "Berhasil Tambah Data", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    ErrorResponse errorResponse = ErrorAPIConverter.getItemErrorBody(response.errorBody(), FormKeuanganActivity.this);
                    Toast.makeText(FormKeuanganActivity.this, errorResponse.getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<IuranResponse> call, Throwable t) {

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}

