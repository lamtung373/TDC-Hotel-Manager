package com.example.tdchotel_manager.Menu_QuanLy.Adapter_NhanVien;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdchotel_manager.Model.chuc_vu;
import com.example.tdchotel_manager.Model.nhan_vien;
import com.example.tdchotel_manager.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NhanVienCheckOutAdapter extends RecyclerView.Adapter<NhanVienCheckOutAdapter.NhanVienViewHolder>{
    private List<nhan_vien> nhanVienList;
    private List<chuc_vu> chucVuList;

    public NhanVienCheckOutAdapter(List<nhan_vien> nhanVienList, List<chuc_vu> chucVuList, IClickListener iClickListenerCheck) {
        this.nhanVienList = nhanVienList;
        this.chucVuList = chucVuList;
        this.iClickListenerCheck = iClickListenerCheck;
    }

    private NhanVienCheckOutAdapter.IClickListener iClickListenerCheck;
    public interface IClickListener {
        void onClickUpdateItem(nhan_vien nhan_vien);
    }
    @NonNull
    @Override
    public NhanVienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_checkout,parent,false);
        return new NhanVienCheckOutAdapter.NhanVienViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NhanVienViewHolder holder, int position) {
        nhan_vien nv = nhanVienList.get(position);
        if(nv==null)
        {
            return;
        }
        holder.tvTenNhanVien.setText(nv.getTen_nhan_vien());
        Picasso.get().load(nv.getAnh_nhan_vien()).into(holder.ivAnhNhanVien);
        for(int i = 0; i < chucVuList.size(); i++)
        {
            if(chucVuList.get(i).getId_chuc_vu().equals(nhanVienList.get(position).getId_chuc_vu()))
            {
                holder.tvChucVu.setText(chucVuList.get(i).getTen_chuc_vu());
                break;
            }
        }

        holder.btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickListenerCheck.onClickUpdateItem(nv);
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

    class NhanVienViewHolder extends RecyclerView.ViewHolder{

        TextView tvTenNhanVien,tvChucVu;
        Button btnCheck;
        ImageView ivAnhNhanVien;
        public NhanVienViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenNhanVien = itemView.findViewById(R.id.tvTenNhanVien);
            tvChucVu = itemView.findViewById(R.id.tvChucVu);
            btnCheck = itemView.findViewById(R.id.btnCheck);
            ivAnhNhanVien = itemView.findViewById(R.id.ivAnhNhanVien);
        }
    }
}
