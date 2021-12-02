package com.karta;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.karta.api.ApiClient;
import com.karta.model.delete.DeleteResponse;
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
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Hapus Data Ini?");
        alertDialogBuilder
                .setMessage("Klik Ya untuk delete!")
                .setIcon(R.drawable.kartalogo)
                .setCancelable(false)
                .setPositiveButton("Ya", (dialog, which) -> {
                    deleteMember(anggota);
                })
                .setNegativeButton("Tidak", (dialog, which) -> dialog.dismiss());
        alertDialogBuilder.show();
    }

    private void deleteMember(TampilResponse anggota) {
        apiClient.getUserService().delete(anggota).enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(TampilagtActivity.this, "Berhasil Hapus Data", Toast.LENGTH_SHORT).show();
                    tampilAnggota();
                } else {
                    Toast.makeText(TampilagtActivity.this, "Gagal Menghapus Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                Toast.makeText(TampilagtActivity.this, "Jaringan Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onEdit(TampilResponse anggota) {
        Intent intent = new Intent(TampilagtActivity.this, EdittampilagtActivity.class);
        intent.putExtra("DATA_ANGGOTA", anggota);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tampilAnggota();
    }
}
