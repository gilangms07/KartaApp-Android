package com.karta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.karta.model.iuran.IuranResponse;
import java.util.List;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ViewHolder> {


    Context context;
    List<IuranResponse> iuranResponseList;

    public StatusAdapter(Context context,List<IuranResponse> iuranResponseList) {
        this.context = context;
        this.iuranResponseList = iuranResponseList;
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
        if (iuranResponseList != null && iuranResponseList.size() > 0) {
            IuranResponse model = iuranResponseList.get(position);
            holder.tvKasRTRW.setText(model.getMonth());
            holder.tvKasNama.setText(model.getName());
            holder.tvKasStatus.setText(model.getStatus());
            holder.tvKasRt.setText(model.getRt());
        } else {
            return;
        }
    }

    @Override
    public int getItemCount() {
        return iuranResponseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvKasRTRW,tvKasNama,tvKasStatus, tvKasRt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvKasRTRW = itemView.findViewById(R.id.tvKasMonth);
            tvKasNama = itemView.findViewById(R.id.tvKasNama);
            tvKasRt = itemView.findViewById(R.id.tvKasRt);
            tvKasStatus = itemView.findViewById(R.id.tvKasStatus);
        }
    }
}
