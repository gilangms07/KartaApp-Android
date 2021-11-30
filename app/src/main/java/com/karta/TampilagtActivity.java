package com.karta;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
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

    private void hapusAnggota(TampilResponse anggota) {
        apiClient.getUserService().hapus(anggota)
                .enqueue(new Callback<DeleteResponse>() {
                    @Override
                    public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(TampilagtActivity.this, "Berhasil Hapus Data", Toast.LENGTH_SHORT).show();
                            tampilAnggota();
                        }else{
                            Toast.makeText(TampilagtActivity.this, "Gagal Menghapus Data", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DeleteResponse> call, Throwable t) {

                    }
                });
    }
    @Override
    public void onDelete(TampilResponse anggota) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Hapus Data Ini?");
            alert.setMessage("Apakah Kamu Yakin Ingin Menghapus Data?");
            alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    hapusAnggota(anggota);
                }
            });
            alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alert.show();
        }

    @Override
    public void onEdit(TampilResponse anggota) {
        Toast.makeText(this, "nama Anggota " + anggota.getName(), Toast.LENGTH_SHORT).show();
    }
}
