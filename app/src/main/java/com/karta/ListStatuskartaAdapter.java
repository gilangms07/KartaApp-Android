package com.karta;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.karta.model.status.StatusResponse;

import java.util.List;

interface StatusAdapterOnClickListener {
    void onDelete(StatusResponse statusResponse);
    void onEdit(StatusResponse statusResponse);
}

public class ListStatuskartaAdapter extends RecyclerView.Adapter<ListStatuskartaAdapter.ListViewHolder> {
    private StatusAdapterOnClickListener clickListener;
    private List<StatusResponse> listStatus;

    public ListStatuskartaAdapter(StatusAdapterOnClickListener clickListener, List<StatusResponse> listStatus) {
        this.clickListener = clickListener;
        this.listStatus = listStatus;
    }


    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_tampil_status, parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.bind(listStatus.get(position));
    }

    @Override
    public int getItemCount() {
        return listStatus.size();}

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaketua, tvNamawakil, tvStatusrt, tvStatus;
        Button btnEditstatus, btnHapusstatus;
        public ListViewHolder( View itemview) {
            super(itemview);
            tvNamaketua = itemview.findViewById(R.id.tvNamaketua);
            tvNamawakil = itemview.findViewById(R.id.tvNamawakil);
            tvStatusrt = itemview.findViewById(R.id.tvStatusrt);
            tvStatus = itemview.findViewById(R.id.tvStatus);
            btnEditstatus = itemview.findViewById(R.id.btnEditstatus);
            btnHapusstatus = itemview.findViewById(R.id.btnHapusstatus);
        }
        public void bind(StatusResponse statusResponse) {
        tvNamaketua.setText("Nama Ketua: " + statusResponse.getNameKetua());
        tvNamawakil.setText("Nama Wakil: " + statusResponse.getNameWakil());
        tvStatusrt.setText("RT: " + statusResponse.getRt());
        tvStatus.setText("Status: " + statusResponse.getStatus());
        btnEditstatus.setOnClickListener(v -> {
            clickListener.onEdit(statusResponse);
        });
        btnHapusstatus.setOnClickListener(v -> {
            clickListener.onDelete(statusResponse);
        });
        }
    }
}
