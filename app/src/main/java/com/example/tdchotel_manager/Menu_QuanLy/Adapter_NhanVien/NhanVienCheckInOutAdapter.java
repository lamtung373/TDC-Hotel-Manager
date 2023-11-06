package com.example.tdchotel_manager.Menu_QuanLy.Adapter_NhanVien;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private NhanVienCheckInOutAdapter.IClickListener iClickListener;
    private NhanVienCheckInOutAdapter.IClickListener iClickListenerCheck;

    public NhanVienCheckInOutAdapter(List<nhan_vien> nhanVienList, List<chuc_vu> chucVuList, NhanVienCheckOutAdapter.IClickListener iClickListener) {
    }

    public interface IClickListener {
        void onClickUpdateItem(nhan_vien nhan_vien);
        void onClickCheckItem(nhan_vien nhan_vien);
    }

    public NhanVienCheckInOutAdapter(List<nhan_vien> nhanVienList, List<chuc_vu> chucVuList, NhanVienCheckInOutAdapter.IClickListener iClickListener) {
        this.nhanVienList = nhanVienList;
        this.chucVuList = chucVuList;
        this.iClickListener = iClickListener;
    }

    @NonNull
    @Override
    public NhanVienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_checkinout,parent,false);
        return new NhanVienCheckInOutAdapter.NhanVienViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NhanVienCheckInOutAdapter.NhanVienViewHolder holder, int position) {
        nhan_vien nv = nhanVienList.get(position);
        if(nv==null)
        {
            return;
        }
        holder.tvTenNhanVien.setText(nv.getTen_nhan_vien());
        for(int i = 0; i < chucVuList.size(); i++)
        {
            if(chucVuList.get(i).getId_chuc_vu().equals(nhanVienList.get(position).getId_chuc_vu()))
            {
                holder.tvChucVu.setText(chucVuList.get(i).getTen_chuc_vu());
                break;
            }
        }
        holder.btnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickListener.onClickUpdateItem(nv);
            }
        });
        holder.btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickListener.onClickCheckItem(nv);
            }
        });
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
        Button btnCheck,btnOff;
        public NhanVienViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenNhanVien = itemView.findViewById(R.id.tvTenNhanVien);
            tvChucVu = itemView.findViewById(R.id.tvChucVu);
            btnCheck = itemView.findViewById(R.id.btnCheck);
            btnOff = itemView.findViewById(R.id.btnOff);
        }
    }
}
