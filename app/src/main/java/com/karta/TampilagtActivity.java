package com.karta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class TampilagtActivity extends AppCompatActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampilagt);
    }

    @Override
    public void onDelete(Anggota anggota) {

    }

    @Override
    public void onEdit(Anggota anggota) {

    }
}