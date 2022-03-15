package com.karta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.karta.api.ApiClient;
import com.karta.api.ErrorAPIConverter;
import com.karta.api.ErrorResponse;
import com.karta.model.login.LoginRequest;
import com.karta.model.login.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail, edtPassword;
    private Button btnLogin;
    private TextView tvRegister;

    private ApiClient apiClient;

    private PreferenceUtil preferenceUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        apiClient = new ApiClient(this);

        preferenceUtil = new PreferenceUtil(this);

        edtEmail = findViewById(R.id.etEmail);
        edtPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        initView();
    }

    private void initView() {
        btnLogin.setOnClickListener(v -> {
            String email = edtEmail.getText().toString();
            String password = edtPassword.getText().toString();
            LoginRequest request = new LoginRequest(
                    email, password
            );

            login(request);
        });
    }

    private void login(LoginRequest request) {
        apiClient.getUserService().login(request)
                .enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            LoginResponse loginResponse = response.body();
                            preferenceUtil.setEmail(loginResponse.getEmail());
                            Toast.makeText(LoginActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                            preferenceUtil.setStatus(true);
                        } else {
                            ErrorResponse errorResponse = ErrorAPIConverter.getItemErrorBody(response.errorBody(), LoginActivity.this);
                            Toast.makeText(LoginActivity.this, errorResponse.getError(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Network Bermasalah " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
