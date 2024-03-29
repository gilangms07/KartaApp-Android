package com.karta;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.karta.api.ApiClient;
import com.karta.api.ErrorAPIConverter;
import com.karta.api.ErrorResponse;
import com.karta.model.iuran.IuranRequest;
import com.karta.model.iuran.IuranResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KeuanganActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    RecyclerView rvKas;
    StatusAdapter adapter;
    ApiClient apiClient;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabelkas);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        apiClient = new ApiClient(this);
        rvKas = findViewById(R.id.rvKas);
     //   Spinner spinner = (Spinner) findViewById(R.id.spinner1);
       // ArrayAdapter<CharSequence>adapter = ArrayAdapter.createFromResource(this,R.array.planets_array, android.R.layout.simple_spinner_item);
       // adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
       // spinner.setAdapter(adapter);
       // spinner.setOnItemSelectedListener(this);
        daftarIuran();
    }

    private void daftarIuran() {
        apiClient.getUserService().daftarIuran().enqueue(new Callback<List<IuranResponse>>() {
            @Override
            public void onResponse(Call<List<IuranResponse>> call, Response<List<IuranResponse>> response) {
                if (response.isSuccessful()){
                    Toast.makeText(KeuanganActivity.this, "Berhasil Menampilkan Data", Toast.LENGTH_SHORT).show();
                    setRecyclerView(response.body());
                } else {
                    ErrorResponse errorResponse = ErrorAPIConverter.getItemErrorBody(response.errorBody(), KeuanganActivity.this);
                    Toast.makeText(KeuanganActivity.this, errorResponse.getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<IuranResponse>> call, Throwable t) {

            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.iuranmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuiuran) {
            Intent intent = new Intent(KeuanganActivity.this, FormKeuanganActivity.class);
            startActivity(intent);
        } else {
            finish();
        }
        return true;
    }


    private void setRecyclerView(List<IuranResponse> iuranResponseList) {
        rvKas.setHasFixedSize(true);
        rvKas.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StatusAdapter(this, iuranResponseList);
        rvKas.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        daftarIuran();
    }
}
