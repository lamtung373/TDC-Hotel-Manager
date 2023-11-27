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

import com.example.tdchotel_manager.Model.chi_tiet_tien_nghi;
import com.example.tdchotel_manager.Model.tien_nghi;
import com.example.tdchotel_manager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class adapter_getchitiettiennghi extends RecyclerView.Adapter<adapter_getchitiettiennghi.MyViewHolder> {
    private ArrayList<tien_nghi> dataList = new ArrayList<>();
    ArrayList<chi_tiet_tien_nghi> newChiTietTienNghis = new ArrayList<>();
    String idphong;

    // Constructor để khởi tạo adapter và gọi phương thức GoiDuLieu để lấy dữ liệu
    public adapter_getchitiettiennghi(String idphong) {
        this.idphong = idphong;
        GoiDuLieu(this.idphong);
    }
public ArrayList<chi_tiet_tien_nghi>getChiTietTN(){
        return newChiTietTienNghis;
}
public String getName(String id){
     for (tien_nghi item:dataList){
         if (item.getId_tien_nghi().equals(id)){
             return item.getTen_tien_nghi();
         }
     }
     return "Không tìm thấy tên tiện nghi";
}
    @NonNull
    @Override
    public adapter_getchitiettiennghi.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_chi_tiet_tien_nghi_va_dich_vu_phong, parent, false);
        return new adapter_getchitiettiennghi.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_getchitiettiennghi.MyViewHolder holder, int position) {
        tien_nghi tienNghi = dataList.get(position);
            holder.tv_ten_tien_nghi.setText(tienNghi.getTen_tien_nghi());
            holder.edt_so_luong.setText(String.valueOf(tienNghi.getSo_luong()));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_ten_tien_nghi;
        EditText edt_so_luong;
        ImageButton ib_increase, ib_decrease;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_ten_tien_nghi = itemView.findViewById(R.id.tv_chitiet);
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
        DatabaseReference tienNghiReference = FirebaseDatabase.getInstance().getReference("tien_nghi");
        tienNghiReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot tienNghiSnapshot) {
                dataList.clear(); // Xóa dữ liệu cũ trước khi thêm dữ liệu mới để tránh trùng lặp

                for (DataSnapshot dataSnapshot : tienNghiSnapshot.getChildren()) {
                    tien_nghi tienNghi = dataSnapshot.getValue(tien_nghi.class);
                    if (tienNghi != null) {
                        dataList.add(tienNghi);
                    }
                }

                // Gọi danh sách chi tiết tiện nghi dựa trên idPhong
                DatabaseReference chiTietTienNghiReference = FirebaseDatabase.getInstance().getReference("chi_tiet_tien_nghi").child(idPhong);
                chiTietTienNghiReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot chiTietTienNghiSnapshot) {

                        for (DataSnapshot facilitySnapshot : chiTietTienNghiSnapshot.getChildren()) {
                            chi_tiet_tien_nghi comfortDetail = facilitySnapshot.getValue(chi_tiet_tien_nghi.class);
                            if (comfortDetail != null) {
                                newChiTietTienNghis.add(comfortDetail);
                            }
                            for (tien_nghi ct : dataList) {
                                if (ct.getId_tien_nghi().equals(comfortDetail.getId_tien_nghi())) {
                                    ct.setSo_luong(comfortDetail.getSo_luong());
                                    Log.d("Adapter", "ID Tiện nghi trong chi tiết: " + ct.getTen_tien_nghi()); // Kiểm tra log
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
