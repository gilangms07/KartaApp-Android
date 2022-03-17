package com.karta

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.Kegiatan
import com.KegiatanData
import com.karta.api.ApiClient
import com.karta.model.kegiatan.KegiatanResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KegiatanActivity : AppCompatActivity() {
    private lateinit var rvKegiatan: RecyclerView
    private val list = ArrayList<Kegiatan>()

    private lateinit var apiClient: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kegiatan)
        apiClient = ApiClient(this)

        rvKegiatan = findViewById(R.id.rvKegiatan)
        rvKegiatan.setHasFixedSize(true)

        list.addAll(KegiatanData.listData)
        getKegiatanResponse()
    }

    private fun showRecyclerView(response: List<KegiatanResponse>) {
        rvKegiatan.layoutManager = LinearLayoutManager(this)
        val listKegiatanAdapter = KegiatanAdapter(response)
        rvKegiatan.adapter = listKegiatanAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.kegiatanmenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menukegiatan) {
            val intent = Intent(this@KegiatanActivity, TambahKegiatanActivity::class.java)
            startActivity(intent)
        }
        return true
    }

    private fun getKegiatanResponse() {
        apiClient.userService.daftarKegiatan().enqueue(object : Callback<List<KegiatanResponse>> {
            override fun onResponse(call: Call<List<KegiatanResponse>>, response: Response<List<KegiatanResponse>>) {
                if (response.isSuccessful && response.body() != null) {
                    showRecyclerView(response.body()!!)
                } else {
                    Toast.makeText(this@KegiatanActivity, "Gagal mendapatkan daftar kegiatan", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<KegiatanResponse>>, t: Throwable) {
                Toast.makeText(this@KegiatanActivity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        getKegiatanResponse()
    }
}