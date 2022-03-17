package com.karta

import android.util.Base64
import android.util.Base64DataException
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.karta.model.kegiatan.KegiatanResponse

class KegiatanAdapter(private val listKegiatan: List<KegiatanResponse>) : RecyclerView.Adapter<KegiatanAdapter.CardViewViewHolder>() {
    inner class CardViewViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        var tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        var tvDeskripsi: TextView = itemView.findViewById(R.id.tv_item_deskripsi)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_card_kegiatan,parent, false)
        return CardViewViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewViewHolder, position: Int) {
        val kegiatan = listKegiatan[position]

        val image = ImageUtil.convert(kegiatan.photo)
        Glide.with(holder.itemView.context)
                .load(image)
                .fitCenter()
                .into(holder.imgPhoto)

        holder.tvName.text = kegiatan.name
        holder.tvDeskripsi.text = kegiatan.description

        holder.itemView.setOnClickListener {
           // Toast.makeText(holder.itemView.context, "Kamu Memilih"+listKegiatan[holder.adapterPosition].name, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return listKegiatan.size
    }
}