package com

import com.karta.R

object KegiatanData {

    private val kegiatanNames = arrayOf(
            "Launching Produk Telor Asin",
            "Memberikan Bantuan Kepada Warga Yang Terkena Banjir",
            "Aksi Karangtaruna  Perangi Covid-19",
    )

    private val kegiatanDeskripsi = arrayOf(
            "Karang Taruna Kelurahan Sindangpalay Kecamatan Cibeureum Kota Sukabumi meluncurkan produk telur asin. Telur Asin karya kaum milenial tersebut diperlihatkan " +
                    "di acara Launching Produksi Telur Asin di Jalan Garuda Kampung Cibungur Kelurahan Sindangpalay Kecamatan Cibeureum",
            "Dengan Bantuan yang diberikan hampir menyeluruh di wilayah Baros , Cibeureum dan Lembursitu (Bacile) dan Subangjaya,Cikole selanjutnya akan ke wilayah Dayeuh Luhur dan yang lainnya telah " +
                    "memberikan manfaat dan  Pergerakan Positif Karang Taruna Kota Sukabumi dan Kepeduliannya  untuk membantu warga di wilayah Kota Sukabumi.",
            "Menyikapi mewabahnya Pandemik Covid-19, organisasi sosial kepemudaan KarangTaruna kelurahan sindangpalay ikut serta membantu dalam mengantisipasi penyebaran Covid-19",
    )

    private val kegiatanRt = arrayOf(
        "Status Kegiatan dari karang taruna mana",
        "Status RT",
        "Karang Taruna RT berapa"
    )

    private val kegiatanPhoto = intArrayOf(
            R.drawable.kt1,
            R.drawable.kt4,
            R.drawable.kt3,
    )

    val listData: ArrayList<Kegiatan>
        get() {
            var list = arrayListOf<Kegiatan>()
            for (position in kegiatanNames.indices){
                val kegiatan = Kegiatan()
                kegiatan.name = kegiatanNames[position]
                kegiatan.deskripsi = kegiatanDeskripsi[position]
                kegiatan.rt = kegiatanRt[position]
                kegiatan.photo = kegiatanPhoto[position]
                list.add(kegiatan)
            }
            return list
        }

}