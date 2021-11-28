package com.karta;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

interface OnClickListener {
    void onDelete(Anggota anggota);
    void onEdit(Anggota anggota);
}

public class ListNamaAdapter extends RecyclerView.Adapter<ListNamaAdapter.ListViewHolder> {
    private OnClickListener clickListener;
    private List<Anggota> listAnggota;

    public ListNamaAdapter(OnClickListener onClickListener, ArrayList<Anggota> list) {
        this.clickListener = onClickListener;
        this.listAnggota = list;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_tampil_anggota, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.bind(listAnggota.get(position));
    }

    @Override
    public int getItemCount() {
        return listAnggota.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamatampil, tvRTRWtampil, tvEmailtampil;
        Button btnEdittampil, btnHapustampil;
        ListViewHolder(View itemview) {
            super(itemview);
            tvNamatampil = itemview.findViewById(R.id.tvNamatampil);
            tvRTRWtampil = itemview.findViewById(R.id.tvRTRWtampil);
            tvEmailtampil = itemview.findViewById(R.id.tvEmailtampil);
            btnEdittampil = itemview.findViewById(R.id.btnEdittampil);
            btnHapustampil = itemview.findViewById(R.id.btnHapustampil);


        }

        void bind(Anggota anggota) {
            tvNamatampil.setText(anggota.getNama());
            tvRTRWtampil.setText(anggota.getRtrw());
            tvEmailtampil.setText(anggota.getEmail());
            btnEdittampil.setOnClickListener(v -> {
                clickListener.onEdit(anggota);
            });
        }
    }
}