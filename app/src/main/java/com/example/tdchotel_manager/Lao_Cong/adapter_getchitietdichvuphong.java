package com.example.tdchotel_manager.Lao_Cong;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdchotel_manager.Model.chi_tiet_dich_vu_phong;
import com.example.tdchotel_manager.Model.dich_vu_phong;
import com.example.tdchotel_manager.Model.tien_nghi;
import com.example.tdchotel_manager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class adapter_getchitietdichvuphong extends RecyclerView.Adapter<adapter_getchitietdichvuphong.MyViewHolder> {
    private ArrayList<dich_vu_phong> dataList = new ArrayList<>();

    ArrayList<chi_tiet_dich_vu_phong> chiTietDVP = new ArrayList<>();
    String idphong;

    // Constructor để khởi tạo adapter và gọi phương thức GoiDuLieu để lấy dữ liệu
    public adapter_getchitietdichvuphong(String idphong) {
        this.idphong = idphong;
        GoiDuLieu(this.idphong);
    }

    public ArrayList<chi_tiet_dich_vu_phong> getChiTietDVP() {
        return chiTietDVP;
    }

    public String getName(String id) {
        for (dich_vu_phong item : dataList) {
            if (item.getId_dich_vu_phong().equals(id)) {
                return item.getTen_dich_vu_phong();
            }
        }
        return "Không tìm thấy tên dch vụ phòng";
    }

    @NonNull
    @Override
    public adapter_getchitietdichvuphong.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_chi_tiet_tien_nghi_va_dich_vu_phong, parent, false);
        return new adapter_getchitietdichvuphong.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_getchitietdichvuphong.MyViewHolder holder, int position) {
        dich_vu_phong DVP = dataList.get(position);
            holder.tv_ten_dich_vu_phong.setText(DVP.getTen_dich_vu_phong());
            holder.edt_so_luong.setText(String.valueOf(DVP.getSo_luong()));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_ten_dich_vu_phong;
        EditText edt_so_luong;
        ImageButton ib_increase, ib_decrease;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_ten_dich_vu_phong = itemView.findViewById(R.id.tv_chitiet);
            edt_so_luong = itemView.findViewById(R.id.edt_so_luong);
            edt_so_luong.setEnabled(false);
            edt_so_luong.setTextColor(Color.BLACK);
            ib_increase = itemView.findViewById(R.id.ib_increase);
            ib_decrease = itemView.findViewById(R.id.ib_decrease);
            ib_increase.setVisibility(View.GONE);
            ib_decrease.setVisibility(View.GONE);
        }
    }

    public void GoiDuLieu(String idPhong) {
        // Gọi danh sách tiện nghi
        DatabaseReference tienNghiReference = FirebaseDatabase.getInstance().getReference("dich_vu_phong");
        tienNghiReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot tienNghiSnapshot) {
                dataList.clear(); // Xóa dữ liệu cũ trước khi thêm dữ liệu mới để tránh trùng lặp

                for (DataSnapshot dataSnapshot : tienNghiSnapshot.getChildren()) {
                    dich_vu_phong dichVuPhong = dataSnapshot.getValue(dich_vu_phong.class);
                    if (dichVuPhong != null) {
                        dataList.add(dichVuPhong);
                    }
                }

                // Gọi danh sách chi tiết tiện nghi dựa trên idPhong
                DatabaseReference chiTietDichVuPhongReference = FirebaseDatabase.getInstance().getReference("chi_tiet_dich_vu_phong").child(idPhong);
                chiTietDichVuPhongReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot chiTietTienNghiSnapshot) {

                        for (DataSnapshot facilitySnapshot : chiTietTienNghiSnapshot.getChildren()) {
                            chi_tiet_dich_vu_phong comfortDetail = facilitySnapshot.getValue(chi_tiet_dich_vu_phong.class);
                            if (comfortDetail != null) {
                                chiTietDVP.add(comfortDetail);
                            }
                            for (dich_vu_phong ct : dataList) {
                                if (ct.getId_dich_vu_phong().equals(comfortDetail.getId_dich_vu_phong())) {
                                    ct.setSo_luong(comfortDetail.getSo_luong());
                                }
                            }
                        }
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Xử lý lỗi
                        Log.e("fetchComfortDetails", "Failed to read value.", databaseError.toException());
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi nếu có
            }
        });
    }
}

