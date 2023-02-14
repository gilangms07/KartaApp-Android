package com.karta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.karta.admin.AdminResponse;
import com.karta.api.ApiClient;
import com.karta.model.login.LoginResponse;
import com.karta.model.status.StatusResponse;
import com.karta.model.user.UserResponse;

import java.util.List;

import okhttp3.ResponseBody;
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
                .enqueue(new Callback<AdminResponse>() {
                    @Override
                    public void onResponse(Call<AdminResponse> call, Response<AdminResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            setupRecyclerView(response.body().getData());
                        }
                    }

                    @Override
                    public void onFailure(Call<AdminResponse> call, Throwable t) {
                        Toast.makeText(AdminActivity.this, "Network Bermasalah", Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });
    }

    private void setupRecyclerView(List<LoginResponse> adminResponseList) {
        adapter = new ListAdminAdapter(this, adminResponseList);
        rvAdmin.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvAdmin.setAdapter(adapter);

    }


    @Override
    public void onAccept(LoginResponse adminResponse) {
        apiClient.getUserService().aktivasiuser(adminResponse.getId()).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AdminActivity.this, "Akun " + adminResponse.getName() + " diaktifkan.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AdminActivity.this, "Gagal aktivasi user", Toast.LENGTH_SHORT).show();
                }

                // refresh daftar admin
                tampilAdmin();
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(AdminActivity.this, "Network Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onReject(LoginResponse adminResponse) {
        apiClient.getUserService().deleteUser(adminResponse.getId()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AdminActivity.this, "Akun " + adminResponse.getName() + " ditolak.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AdminActivity.this, "Gagal hapus user", Toast.LENGTH_SHORT).show();
                }

                // refresh daftar admin
                tampilAdmin();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(AdminActivity.this, "Network Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
