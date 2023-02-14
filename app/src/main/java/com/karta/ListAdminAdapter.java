package com.karta;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.karta.admin.AdminResponse;
import com.karta.model.login.LoginResponse;

import java.util.List;

interface AdminAdapterOnClickListener {
    void onAccept(LoginResponse adminResponse);
    void onReject(LoginResponse adminResponse);
}

public class ListAdminAdapter extends RecyclerView.Adapter<ListAdminAdapter.ListViewHolder> {
    private AdminAdapterOnClickListener clickListener;
    private List<LoginResponse> listAdmin;

    public ListAdminAdapter(AdminAdapterOnClickListener clickListener, List<LoginResponse> listAdmin) {
        this.clickListener = clickListener;
        this.listAdmin = listAdmin;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_tampil_admin, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.bind(listAdmin.get(position));
    }

    @Override
    public int getItemCount() {
        return listAdmin.size();}

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaadmin, tvEmailadmin;
        Button btnAccept, btnReject;
        public ListViewHolder( View itemview) {
            super(itemview);
            tvNamaadmin = itemview.findViewById(R.id.tvNamaadmin);
            tvEmailadmin = itemview.findViewById(R.id.tvEmailadmin);
            btnAccept = itemview.findViewById(R.id.btnAccept);
            btnReject = itemview.findViewById(R.id.btnReject);
        }

        public void bind(LoginResponse adminResponse) {
            tvNamaadmin.setText("Nama Admin: " + adminResponse.getName());
            tvEmailadmin.setText("Email Admin: " + adminResponse.getEmail());
            btnAccept.setOnClickListener(v -> {
                clickListener.onAccept(adminResponse);
            });
            btnReject.setOnClickListener(v -> {
                clickListener.onReject(adminResponse);
            });
        }
    }
    }
