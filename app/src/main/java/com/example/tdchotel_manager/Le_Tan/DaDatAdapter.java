package com.example.tdchotel_manager.Le_Tan;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdchotel_manager.Le_Tan.Activity_HoaDon.Activity_HoaDon_DaDat;
import com.example.tdchotel_manager.Le_Tan.Activity_HoaDon.Activity_HoaDon_DuyetCoc;
import com.example.tdchotel_manager.Menu_QuanLy.LichLamAdapter;
import com.example.tdchotel_manager.Model.hoa_don;
import com.example.tdchotel_manager.Model.khach_hang;
import com.example.tdchotel_manager.Model.nhan_vien;
import com.example.tdchotel_manager.Model.phong;
import com.example.tdchotel_manager.R;

import java.text.DecimalFormat;
import java.util.List;

public class DaDatAdapter extends RecyclerView.Adapter<DaDatAdapter.DaDatViewHolder> {
    private List<hoa_don> hoaDonList;
    private List<phong> phongList;
    private List<khach_hang> khachHangList;
    private Context mContext;

    @NonNull
    @Override
    public DaDatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_da_dat, parent, false);
        return new DaDatAdapter.DaDatViewHolder(view);
    }

    public DaDatAdapter(Context context, List<hoa_don> hoaDonList, List<phong> phongList, List<khach_hang> khachHangList) {
        mContext = context;
        this.hoaDonList = hoaDonList;
        this.phongList = phongList;
        this.khachHangList = khachHangList;
    }

    @Override
    public void onBindViewHolder(@NonNull DaDatViewHolder holder, int position) {
        hoa_don hoaDon = hoaDonList.get(position);
        if (hoaDon == null) {
            return;
        }
        holder.tvMaHoaDon.setText(hoaDon.getId_hoa_don().toString());
        holder.tvNgayDatPhong.setText(hoaDon.getThoi_gian_nhan_phong().toString());
        holder.tvNgayNghi.setText(hoaDon.getThoi_gian_tra_phong().toString());
        DecimalFormat decimalFormatt = new DecimalFormat("###,###,###");
        String tienCocFormatted = decimalFormatt.format(hoaDon.getTien_coc());
        holder.tvTienDaTra.setText(tienCocFormatted + "đ");
        String tongTienFormatted = decimalFormatt.format(hoaDon.getTong_thanh_toan());
        holder.tvTienTong.setText(tongTienFormatted + "đ");
        for (int i = 0; i < phongList.size(); i++) {
            if (phongList.get(i).getId_phong().equals(hoaDon.getId_phong())) {
                holder.tv_name_room.setText(phongList.get(i).getTen_phong());
                break;
            }
        }
        for (int i = 0; i < khachHangList.size(); i++) {
            if (khachHangList.get(i).getSo_dien_thoai().equals(hoaDon.getSo_dien_thoai())) {
                holder.tvTenNguoiDung.setText(khachHangList.get(i).getTen());
                break;
            }
        }
        holder.btnNhanPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Activity_HoaDon_DaDat.class);
                intent.putExtra("hoa_don", hoaDon);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (hoaDonList != null) {
            return hoaDonList.size();

        }
        return 0;
    }

    class DaDatViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name_room, tvMaHoaDon, tvTenNguoiDung, tvNgayDatPhong, tvNgayNghi, tvTienDaTra, tvTienTong;
        Button btnNhanPhong;

        public DaDatViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name_room = itemView.findViewById(R.id.tv_name_room);
            tvMaHoaDon = itemView.findViewById(R.id.tvMaHoaDon);
            tvTenNguoiDung = itemView.findViewById(R.id.tvTenNguoiDung);
            tvNgayDatPhong = itemView.findViewById(R.id.tvNgayDatPhong);
            tvNgayNghi = itemView.findViewById(R.id.tvNgayNghi);
            tvTienDaTra = itemView.findViewById(R.id.tvTienDaTra);
            tvTienTong = itemView.findViewById(R.id.tvTienTong);
            btnNhanPhong = itemView.findViewById(R.id.btnNhanPhong);
        }
    }
}
