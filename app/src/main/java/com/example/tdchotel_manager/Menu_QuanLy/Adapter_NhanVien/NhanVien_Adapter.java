package com.example.tdchotel_manager.Menu_QuanLy.Adapter_NhanVien;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdchotel_manager.IOnItemClick;
import com.example.tdchotel_manager.Model.nhan_vien;
import com.example.tdchotel_manager.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class NhanVien_Adapter extends RecyclerView.Adapter<NhanVien_Adapter.NhanVienViewHolder> {
    private ArrayList<nhan_vien> data = new ArrayList<>();

    private HashMap<String, String> chucVuMapping;

    private IOnItemClick onClickItem;

    public NhanVien_Adapter(ArrayList<nhan_vien> data, HashMap<String, String> chucVuMapping, IOnItemClick onClickItem) {
        this.data = data;
        this.chucVuMapping = chucVuMapping;
        this.onClickItem = onClickItem;
    }

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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ThongTinNhanVien.class);
                intent.putExtra("nhanVienId", nv.getId_nhan_vien());
                v.getContext().startActivity(intent);
            }
        });
        // Hiển thị ProgressBar khi bắt đầu tải ảnh
        holder.progressBar.setVisibility(View.VISIBLE);

        Picasso.get().load(nv.getAnh_nhan_vien()).into(holder.imgNV, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                // Ảnh đã được tải, ẩn ProgressBar
                holder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {
                // Có lỗi khi tải ảnh, ẩn ProgressBar và có thể hiển thị ảnh lỗi
                holder.progressBar.setVisibility(View.GONE);
                // Set ảnh lỗi nếu có
                holder.imgNV.setImageResource(R.color.green);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void filterList(ArrayList<nhan_vien> filteredList) {
        data = filteredList;
        notifyDataSetChanged();
    }

    public class NhanVienViewHolder extends RecyclerView.ViewHolder {
        TextView tvTen, tvLoai;
        ImageView imgNV;
        ProgressBar progressBar;

        public NhanVienViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTen = itemView.findViewById(R.id.tvTenNV);
            tvLoai = itemView.findViewById(R.id.tvLoaiNV);
            imgNV = itemView.findViewById(R.id.imgNV); // Khởi tạo ImageView
            progressBar = itemView.findViewById(R.id.progressBar_itemQLNV);
        }

    }
}
