package com.karta;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.karta.api.ApiClient;
import com.karta.model.delete.DeleteResponse;
import com.karta.model.status.StatusResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatuskartaActivity extends AppCompatActivity implements StatusAdapterOnClickListener {


    private RecyclerView rvStatuskarta;
    private ApiClient apiClient;

    private ListStatuskartaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statuskarta);

        apiClient = new ApiClient(this);
        rvStatuskarta = findViewById(R.id.rvStatuskarta);
        tampilStatus();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

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
    private void tampilStatus() {
        apiClient.getUserService().status()
                .enqueue(new Callback<List<StatusResponse>>() {
                    @Override
                    public void onResponse(Call<List<StatusResponse>> call, Response<List<StatusResponse>> response) {
                        if (response.isSuccessful()) {
                            setupRecyclerView(response.body());
                        } else {
                            Toast.makeText(StatuskartaActivity.this,"Gagal Menampilkan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<StatusResponse>> call, Throwable t) {
                        Toast.makeText(StatuskartaActivity.this,"Network Bermasalah", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.statusmenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menustatus) {
            Intent intent = new Intent(this, TambahstatusActivity.class);
            startActivity(intent);
        } else {
            finish();
        }
        return true;
    }

    private void setupRecyclerView(List<StatusResponse> statusResponseList) {
        adapter = new ListStatuskartaAdapter(this, statusResponseList);
        rvStatuskarta.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvStatuskarta.setAdapter(adapter);
    }

    @Override
    public void onDelete(StatusResponse statusResponse) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Hapus Data Ini?");
        alertDialogBuilder
                .setMessage("Klik Ya untuk Menghapus!")
                .setIcon(R.drawable.samm)
                .setCancelable(false)
                .setPositiveButton("Ya", (dialog, which) -> {
                    deleteStatus(statusResponse.getId());
                })
                .setNegativeButton("Tidak", (dialog, which) -> dialog.dismiss());
        alertDialogBuilder.show();
    }

    private void deleteStatus(long id) {
        apiClient.getUserService().deletestatus(id).enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(StatuskartaActivity.this, "Berhasil Hapus Data", Toast.LENGTH_SHORT).show();
                    tampilStatus();
                } else {
                    Toast.makeText(StatuskartaActivity.this, "Gagal Menghapus Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                Toast.makeText(StatuskartaActivity.this, "Jaringan Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onEdit(StatusResponse statusResponse) {
        Intent intent = new Intent(StatuskartaActivity.this, TambahstatusActivity.class);
        intent.putExtra(TambahstatusActivity.DATA_STATUS, statusResponse);
        intent.putExtra(TambahstatusActivity.IS_EDIT, true);
        startActivity(intent);
    }

    protected void onResume() {
        super.onResume();
        tampilStatus();
    }
}