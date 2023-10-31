package com.example.tdchotel_manager.Menu_QuanLy.Adapter_NhanVien;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdchotel_manager.Model.chuc_vu;
import com.example.tdchotel_manager.Model.nhan_vien;
import com.example.tdchotel_manager.R;

import java.util.List;

public class NhanVienCheckInOutAdapter extends RecyclerView.Adapter<NhanVienCheckInOutAdapter.NhanVienViewHolder>{
    private List<nhan_vien> nhanVienList;
    private List<chuc_vu> chucVuList;

    public NhanVienCheckInOutAdapter(List<nhan_vien> nhanVienList, List<chuc_vu> chucVuList) {
        this.nhanVienList = nhanVienList;
        this.chucVuList = chucVuList;
    }

    @NonNull
    @Override
    public NhanVienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_checkinout,parent,false);
        return new NhanVienCheckInOutAdapter.NhanVienViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NhanVienCheckInOutAdapter.NhanVienViewHolder holder, int position) {

    }
    @Override
    public int getItemCount() {
        if(nhanVienList != null)
        {
            return  nhanVienList.size();
        }
        return 0;
    }

    class NhanVienViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvTenNhanVien,tvChucVu;
        public NhanVienViewHolder(@NonNull View itemView) {

            super(itemView);
            tvTenNhanVien = itemView.findViewById(R.id.tvTenNhanVien);
            tvChucVu = itemView.findViewById(R.id.tvChucVu);
        }
    }
}
