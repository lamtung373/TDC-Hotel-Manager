package com.example.tdchotel_manager.Menu_QuanLy.Adapter_NhanVien;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdchotel_manager.IOnClickItem;
import com.example.tdchotel_manager.Model.chuc_vu;
import com.example.tdchotel_manager.Model.nhan_vien;
import com.example.tdchotel_manager.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NhanVien_Adapter extends RecyclerView.Adapter<NhanVien_Adapter.NhanVienViewHolder> {
    private ArrayList<nhan_vien> data = new ArrayList<>();

    private HashMap<String, String> chucVuMapping;

    public NhanVien_Adapter(ArrayList<nhan_vien> data, HashMap<String, String> chucVuMapping) {
        this.data = data;
        this.chucVuMapping = chucVuMapping;
    }
    private IOnClickItem iOnClickItem;

    @NonNull
    @Override
    public NhanVienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_quanlynhanvien, parent, false);
        return new NhanVienViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NhanVienViewHolder holder, int position) {
        nhan_vien nv = data.get(position);
        holder.tvTen.setText(nv.getTen_nhan_vien());
        String chucVu = chucVuMapping.get(nv.getId_chuc_vu());
        holder.tvLoai.setText(chucVu != null ? chucVu : "Không xác định");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class NhanVienViewHolder extends RecyclerView.ViewHolder {
        TextView tvTen, tvLoai;
        public NhanVienViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTen=itemView.findViewById(R.id.tvTenNV);
            tvLoai=itemView.findViewById(R.id.tvLoaiNV);
        }
    }
}
