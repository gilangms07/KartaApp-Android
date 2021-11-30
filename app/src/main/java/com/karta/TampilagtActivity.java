package com.karta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.karta.api.ApiClient;
import com.karta.model.tampil.TampilResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TampilagtActivity extends AppCompatActivity implements TampilAdapterOnClickListener {

    private RecyclerView rvAnggota;
    private ApiClient apiClient;

    private ListNamaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampilagt);

        apiClient = new ApiClient(this);

        rvAnggota = findViewById(R.id.rvAnggota);
        tampilAnggota();
    }

    private void tampilAnggota() {
        apiClient.getUserService().tampil()
                .enqueue(new Callback<List<TampilResponse>>() {
                    @Override
                    public void onResponse(Call<List<TampilResponse>> call, Response<List<TampilResponse>> response) {
                        if (response.isSuccessful()) {
                            setupRecyclerView(response.body());
                            Toast.makeText(TampilagtActivity.this,"List anggota" + response.body().toString(), Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(TampilagtActivity.this, "Gagal Menampilkan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<TampilResponse>> call, Throwable t) {
                        Toast.makeText(TampilagtActivity.this, "Network Bermasalah", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setupRecyclerView(List<TampilResponse> tampilResponseList) {
        adapter = new ListNamaAdapter(this, tampilResponseList);
        rvAnggota.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvAnggota.setAdapter(adapter);
    }

    @Override
    public void onDelete(TampilResponse anggota) {

    }

    @Override
    public void onEdit(TampilResponse anggota) {
        Toast.makeText(this, "nama Anggota " + anggota.getName(), Toast.LENGTH_SHORT).show();
    }
}
