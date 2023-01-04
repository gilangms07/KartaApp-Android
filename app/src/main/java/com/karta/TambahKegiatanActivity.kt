package com.karta
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.karta.api.ApiClient
import com.karta.api.ErrorAPIConverter
import com.karta.model.kegiatan.KegiatanRequest
import com.karta.model.kegiatan.KegiatanResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.InputStream


class TambahKegiatanActivity : AppCompatActivity() {
    private var WRITE_EXTERNAL_STORAGE_PERMISSION_CODE: Int = 1
    private var READ_EXTERNAL_STORAGE_PERMISSION_CODE: Int = 2
    private var CAMERA_PERMISSION_CODE: Int = 3

    private lateinit var ivResult: ImageView
    private lateinit var edtNama: EditText
    private lateinit var edtDeskripsi: EditText
    private lateinit var btnTake: Button
    private lateinit var btnKirimKegiatan: Button
    private lateinit var edtRt :EditText
    private lateinit var apiClient: ApiClient

    private var gambarKegiatan: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_kegiatan)
        apiClient = ApiClient(this)
        init()
        checkPermission()
    }

    private fun init() {
        ivResult = findViewById(R.id.ivResult)
        edtNama = findViewById(R.id.edtNamaKegiatan)
        edtDeskripsi = findViewById(R.id.edtDeskripsi)
        edtRt = findViewById(R.id.edtRt)
        btnTake = findViewById(R.id.btnTake)
        btnKirimKegiatan = findViewById(R.id.btnKirimKegiatan)
        btnKirimKegiatan.setOnClickListener {
            val nama = edtNama.text.toString()
            val deskripsi = edtDeskripsi.text.toString()
            val rt = edtRt.text.toString()
            if (gambarKegiatan != null && nama.isNotEmpty() && deskripsi.isNotEmpty()) {
                val base64Image = ImageUtil.convert(gambarKegiatan!!)
                val kegiatanRequest = KegiatanRequest(
                        id = 0,
                        photo = base64Image,
                        name = nama,
                        description = deskripsi,
                        rt = rt
                )
                kirimKegiatan(kegiatanRequest)
            } else {
                Toast.makeText(this, "Semua data harus diisi", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun kirimKegiatan(request: KegiatanRequest) {
        apiClient.userService.buatKegiatan(request).enqueue(object: Callback<KegiatanResponse> {
            override fun onResponse(call: Call<KegiatanResponse>, response: Response<KegiatanResponse>) {
                if (response.isSuccessful){
                    Toast.makeText(this@TambahKegiatanActivity, "Berhasil Tambah Kegiatan", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    val errorResponse = ErrorAPIConverter.getItemErrorBody(response.errorBody(), this@TambahKegiatanActivity)
                    Toast.makeText(this@TambahKegiatanActivity, errorResponse.error, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<KegiatanResponse>, t: Throwable) {
                Toast.makeText(this@TambahKegiatanActivity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when (PackageManager.PERMISSION_DENIED) {
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) -> {
                    requestPermissions(
                            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            WRITE_EXTERNAL_STORAGE_PERMISSION_CODE
                    )
                }
                checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                    requestPermissions(
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            READ_EXTERNAL_STORAGE_PERMISSION_CODE
                    )
                }
                checkSelfPermission(Manifest.permission.CAMERA) -> {
                    requestPermissions(
                            arrayOf(Manifest.permission.CAMERA),
                            CAMERA_PERMISSION_CODE
                    )
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            WRITE_EXTERNAL_STORAGE_PERMISSION_CODE -> if (grantResults.isNotEmpty()) {
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, "Anda perlu memberikan semua izin untuk menggunakan aplikasi ini.", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
            READ_EXTERNAL_STORAGE_PERMISSION_CODE -> if (grantResults.isNotEmpty()) {
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, "Anda perlu memberikan semua izin untuk menggunakan aplikasi ini.", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
            CAMERA_PERMISSION_CODE -> if (grantResults.isNotEmpty()) {
                if (grantResults[0] == PackageManager.PERMISSION_DENIED){
                    Toast.makeText(this, "Anda perlu memberikan semua izin untuk menggunakan aplikasi ini.", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }

    fun clickTake(view: View) {
        val takePictOptions = arrayOf<String>("Camera", "Gallery")
        AlertDialog.Builder(this)
                .setTitle("Ambil gambar melalui")
                .setItems(takePictOptions) { _, which-> when (which) {
                    0 -> openCamera()
                    1 -> openGallery()
                } }
                .create()
                .show()
    }

    private fun openCamera() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
                requestPermissions(arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE)
            } else {
                Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                    takePictureIntent.resolveActivity(packageManager)?.also {
                        resultLauncherCamera.launch(takePictureIntent)
                    }
                }
            }
        } else {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                takePictureIntent.resolveActivity(packageManager)?.also {
                    resultLauncherCamera.launch(takePictureIntent)
                }
            }
        }
    }

    var resultLauncherCamera = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            gambarKegiatan = data!!.extras?.get("data") as Bitmap

            gambarKegiatan.let {
                Glide.with(this)
                        .load(it)
                        .into(ivResult)
            }
        }
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        resultLauncherGallery.launch(galleryIntent)
    }

    private var resultLauncherGallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val imageUri = data!!.data!!
            gambarKegiatan = if (Build.VERSION.SDK_INT >= 29){
                val inputStream: InputStream = contentResolver.openInputStream(imageUri)!!
                BitmapFactory.decodeStream(inputStream)
            } else{
                MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
            }

            gambarKegiatan.let {
                Glide.with(this)
                        .load(it)
                        .into(ivResult)
            }
        }
    }
}