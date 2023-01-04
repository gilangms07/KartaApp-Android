package com.karta;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.karta.api.ApiClient;
import com.karta.model.delete.DeleteResponse;
import com.karta.model.tampil.TampilResponse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TampilagtActivity extends AppCompatActivity implements TampilAdapterOnClickListener {

    private String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/daftar_anggota.pdf";
    private final int RC_READ_WRITE_STORAGE = 14045;
    String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private RecyclerView rvAnggota;
    private ApiClient apiClient;


    private ListNamaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampilagt);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        EasyPermissions.requestPermissions(
                new PermissionRequest.Builder(this, RC_READ_WRITE_STORAGE, perms)
                        .setRationale(R.string.camera_and_location_rationale)
                        .setPositiveButtonText(R.string.rationale_ask_ok)
                        .setNegativeButtonText(R.string.rationale_ask_cancel)
                        .setTheme(R.style.Theme_AppCompat)
                        .build());

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
                        } else {
                            Toast.makeText(TampilagtActivity.this, "Gagal Menampilkan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<TampilResponse>> call, Throwable t) {
                        Toast.makeText(TampilagtActivity.this, "Network Bermasalah", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionsmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.cetak) {
            createPDFFile(pdfPath);
        } else {
            finish();
        }
        return true;
    }

    private void createPDFFile(String path) {
        if (new File(path).exists())
            new File(path).delete();
        try {
            Document document = new Document();

            PdfWriter.getInstance(document, new FileOutputStream(path));

            document.open();

            document.setPageSize(PageSize.A4);
            document.addCreationDate();
            document.addAuthor("Otenz");
            document.addCreator("Gilang MS");

            //Font Setting
            BaseColor colorAccent = new BaseColor(0, 153, 204, 255);
            float fontSize = 20.0f;

            BaseFont fontName = BaseFont.createFont("assets/fonts/brandon_medium.otf", "UTF-8", BaseFont.EMBEDDED);

            Font titleFont = new Font(fontName, fontSize, Font.NORMAL, BaseColor.BLACK);
            addNewItem(document, "Daftar Anggota", Element.ALIGN_CENTER, titleFont);

            List<TampilResponse> listAnggota = adapter.getListAnggota();

            for (int i = 0; i < listAnggota.size(); i++) {
                TampilResponse anggota = listAnggota.get(i);

                Font namaFont = new Font(fontName, fontSize, Font.NORMAL, colorAccent);
                addNewItem(document, "Nama:", Element.ALIGN_LEFT, namaFont);

                Font namaSayaFont = new Font(fontName, fontSize, Font.NORMAL, BaseColor.BLACK);
                addNewItem(document, anggota.getName(), Element.ALIGN_LEFT, namaSayaFont);

                addNewItem(document, "RT/RW", Element.ALIGN_LEFT, namaFont);
                addNewItem(document, anggota.getRt()+"/"+anggota.getRw(), Element.ALIGN_LEFT, namaSayaFont);

                addNewItem(document, "Email", Element.ALIGN_LEFT, namaFont);
                addNewItem(document, anggota.getEmail(), Element.ALIGN_LEFT, namaSayaFont);

                addLineSeperator(document);
            }

            document.close();

            Toast.makeText(this, "Berhasil Cetak", Toast.LENGTH_SHORT).show();

            if (EasyPermissions.hasPermissions(this, perms)) {
                printPDF();
            } else {
                new AppSettingsDialog.Builder(this).build().show();
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addLineSeperator(Document document) throws DocumentException {
        LineSeparator lineSeparator = new LineSeparator();
        lineSeparator.setLineColor(new BaseColor(0, 0, 0, 150));
        addLineSpace(document);
        document.add(new Chunk(lineSeparator));
        addLineSpace(document);
    }

    private void addLineSpace(Document document) throws DocumentException {
        document.add(new Paragraph(""));
    }

    private void printPDF() {
        PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
        try {
            PrintDocumentAdapter printDocumentAdapter = new PdfDocumentAdapter(TampilagtActivity.this, pdfPath);
            printManager.print("Document", printDocumentAdapter, new PrintAttributes.Builder().build());
        } catch (Exception ex) {
            Log.e("Otenz", "" + ex.getMessage());
        }
    }

    private void addNewItem(Document document, String text, int align, Font font) throws DocumentException {
        Chunk chunk = new Chunk(text, font);
        Paragraph paragraph = new Paragraph(chunk);
        paragraph.setAlignment(align);
        document.add(paragraph);
    }

    @AfterPermissionGranted(RC_READ_WRITE_STORAGE)
    private void methodRequiresTwoPermission() {

        if (EasyPermissions.hasPermissions(this, perms)) {
//            printPDF();
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.camera_and_location_rationale),
                    RC_READ_WRITE_STORAGE, perms);
        }
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
                .setMessage("Klik Ya untuk Menghapus!")
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
