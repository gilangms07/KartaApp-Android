package com.karta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ViewHolder> {


    Context context;
    List<StatusModel> statusModelList;

    public StatusAdapter(Context context,List<StatusModel> statusModelList) {
        this.context = context;
        this.statusModelList = statusModelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_tabelkas,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (statusModelList != null && statusModelList.size() > 0) {
            StatusModel model = statusModelList.get(position);
            holder.tvKasRTRW.setText(model.getRtrw());
            holder.tvKasNama.setText(model.getNama());
            holder.tvKasStatus.setText(model.getStatus());
        } else {
            return;
        }
    }

    @Override
    public int getItemCount() {
        return statusModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvKasRTRW,tvKasNama,tvKasStatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvKasRTRW = itemView.findViewById(R.id.tvKasRTRW);
            tvKasNama = itemView.findViewById(R.id.tvKasNama);
            tvKasStatus = itemView.findViewById(R.id.tvKasStatus);
        }
    }
}
