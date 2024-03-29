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
import com.karta.model.register.RegisterRequest;
import com.karta.model.register.RegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

 public class RegisterActivity extends AppCompatActivity {

    private EditText etNamaregister, etEmailregister, etPasswordregister, etPasskonf, etAlamatregister, etRwregister, etRtregister;
    private Button btnRegister;

    private ApiClient apiClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        apiClient = new ApiClient(this);

        etNamaregister = findViewById(R.id.etNamaregister);
        etEmailregister = findViewById(R.id.etEmailregister);
        etPasswordregister = findViewById(R.id.etPasswordregister);
        etPasskonf = findViewById(R.id.etPasskonf);
        etAlamatregister = findViewById(R.id.etAlamatregister);
        etRwregister = findViewById(R.id.etRwregister);
        etRtregister = findViewById(R.id.etRtregister);
        btnRegister = findViewById(R.id.btnRegister);
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

     private void initView(){
        btnRegister.setOnClickListener(v -> {
            String name = etNamaregister.getText().toString();
            String email = etEmailregister.getText().toString();
            String password = etPasswordregister.getText().toString();
            String passwordkonfirmasi = etPasskonf.getText().toString();
            String alamat = etAlamatregister.getText().toString();
            String rw = etRwregister.getText().toString();
            String rt = etRtregister.getText().toString();
            RegisterRequest request = new RegisterRequest(
                    name, email, alamat, rt, rw, password, 0
            );
            if (name.equals("") || email.equals("") || password.equals("") || passwordkonfirmasi.equals("") || alamat.equals("") || rw.equals("") || rt.equals("")) {
                Toast.makeText(this, "Harap isi semua data", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.equals(passwordkonfirmasi)) {
                register(request);
            } else{
                Toast.makeText(this, "Password Tidak Sama", Toast.LENGTH_SHORT).show();
            }
        });
    }

     private void register(RegisterRequest request) {
        apiClient.getUserService().register(request)
                .enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        if (response.isSuccessful()) {
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            RegisterResponse registerResponse = response.body();
                            Toast.makeText(RegisterActivity.this, "Register Berhasil ", Toast.LENGTH_SHORT).show();
                        } else {
                            ErrorResponse errorResponse = ErrorAPIConverter.getItemErrorBody(response.errorBody(), RegisterActivity.this);
                            Toast.makeText(RegisterActivity.this, errorResponse.getError(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, "Network Bermasalah" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
     }
 }
