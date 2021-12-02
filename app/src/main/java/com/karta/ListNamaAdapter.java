package com.karta;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.karta.model.tampil.TampilResponse;

import java.util.List;

interface TampilAdapterOnClickListener {
    void onDelete(TampilResponse anggota);
    void onEdit(TampilResponse anggota);
}

public class ListNamaAdapter extends RecyclerView.Adapter<ListNamaAdapter.ListViewHolder> {
    private TampilAdapterOnClickListener clickListener;
    private List<TampilResponse> listAnggota;

    public ListNamaAdapter(TampilAdapterOnClickListener tampilAdapterOnClickListener, List<TampilResponse> tampilResponseList) {
        this.clickListener = tampilAdapterOnClickListener;
        this.listAnggota = tampilResponseList;
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

        void bind(TampilResponse anggota) {
            tvNamatampil.setText(anggota.getName());
            tvRTRWtampil.setText(anggota.getRt() + "/" + anggota.getRw());
            tvEmailtampil.setText(anggota.getEmail());
            btnEdittampil.setOnClickListener(v -> {
                clickListener.onEdit(anggota);
            });
            btnHapustampil.setOnClickListener(v -> {
                clickListener.onDelete(anggota);
            });
        }
    }
}
