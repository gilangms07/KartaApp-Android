package com.karta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.karta.admin.AdminResponse;
import com.karta.api.ApiClient;
import com.karta.model.status.StatusResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminActivity extends AppCompatActivity implements AdminAdapterOnClickListener {

    private RecyclerView rvAdmin;
    private ApiClient apiClient;

    private ListAdminAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        apiClient = new ApiClient(this);
        rvAdmin = findViewById(R.id.rvAdmin);
        tampilAdmin();

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
    public void onBackPressed() {
        super.onBackPressed();
    }
    private void tampilAdmin() {
        apiClient.getUserService().admin()
                .enqueue(new Callback<List<AdminResponse>>() {
                    @Override
                    public void onResponse(Call<List<AdminResponse>> call, Response<List<AdminResponse>> response) {
                        if (response.isSuccessful()) {
                            setupRecyclerView(response.body());
                        } else {
                            Toast.makeText(AdminActivity.this,"Gagal Menampilkan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<AdminResponse>> call, Throwable t) {
                        Toast.makeText(AdminActivity.this,"Network Bermasalah", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setupRecyclerView(List<AdminResponse> adminResponseList) {
        adapter = new ListAdminAdapter(this, adminResponseList);
        rvAdmin.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvAdmin.setAdapter(adapter);

    }


    @Override
    public void onAccept(AdminResponse adminResponse) {

    }

    @Override
    public void onReject(AdminResponse adminResponse) {

    }
}