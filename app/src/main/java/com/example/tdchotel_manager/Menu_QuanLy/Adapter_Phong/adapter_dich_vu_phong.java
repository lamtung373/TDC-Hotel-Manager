package com.example.tdchotel_manager.Menu_QuanLy.Adapter_Phong;

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
import com.example.tdchotel_manager.Model.chi_tiet_tien_nghi;
import com.example.tdchotel_manager.Model.dich_vu_phong;
import com.example.tdchotel_manager.Model.tien_nghi;
import com.example.tdchotel_manager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class adapter_dich_vu_phong extends RecyclerView.Adapter<adapter_dich_vu_phong.MyViewHolder> {
    ArrayList<dich_vu_phong> datalist = new ArrayList<>();
    ArrayList<chi_tiet_dich_vu_phong> chi_tiet_dich_vu_phongs = new ArrayList<>();

    public void addChiTietDichVu(String dichVuPhongId, int soLuong) {
        for (int i = 0; i < chi_tiet_dich_vu_phongs.size(); i++) {
            if (dichVuPhongId.equals(chi_tiet_dich_vu_phongs.get(i).getId_dich_vu_phong())) {
                chi_tiet_dich_vu_phongs.get(i).setSo_luong(soLuong);
                notifyDataSetChanged();
                return;
            }
        }
        // Nếu không tìm thấy, thêm đối tượng mới vào danh sách
        chi_tiet_dich_vu_phong ctdvp = new chi_tiet_dich_vu_phong();
        ctdvp.setId_dich_vu_phong(dichVuPhongId);
        ctdvp.setSo_luong(soLuong);
        chi_tiet_dich_vu_phongs.add(ctdvp);
        notifyDataSetChanged();

    }


    public ArrayList<chi_tiet_dich_vu_phong> getChiTietDichVu() {

        return chi_tiet_dich_vu_phongs;

    }

    public adapter_dich_vu_phong() {
        khoitao();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_dich_vu_phong, parent, false);
        return new adapter_dich_vu_phong.MyViewHolder(itemView);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_ten_tien_nghi;
        EditText edt_so_luong;
        ImageButton ib_increase, ib_decrease;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            tv_ten_tien_nghi = itemView.findViewById(R.id.tv_chitiet);
            edt_so_luong = itemView.findViewById(R.id.edt_so_luong);
            ib_increase = itemView.findViewById(R.id.ib_increase);
            ib_decrease = itemView.findViewById(R.id.ib_decrease);
        }
    }

    private void increaseValue(int position, EditText editText) {
        dich_vu_phong item = datalist.get(position);
        int value = item.getSo_luong();
        value++;
        item.setSo_luong(value); // Cập nhật giá trị trong dataList
        editText.setText(String.valueOf(value));
        notifyItemChanged(position); // Cập nhật chỉ item tại vị trí này
    }

    private void decreaseValue(int position, EditText editText) {
        dich_vu_phong item = datalist.get(position);
        int value = item.getSo_luong();
        if (value > 0) {
            value--;
            item.setSo_luong(value); // Cập nhật giá trị trong dataList
            editText.setText(String.valueOf(value));
            notifyItemChanged(position); // Cập nhật chỉ item tại vị trí này
        }
    }

    // Trong onBindViewHolder, cập nhật các sự kiện click để sử dụng các phương thức trên
    @Override
    public void onBindViewHolder(@NonNull adapter_dich_vu_phong.MyViewHolder holder, int position) {
        dich_vu_phong data = datalist.get(position);
        holder.tv_ten_tien_nghi.setText(data.getTen_dich_vu_phong());
        holder.edt_so_luong.setText(String.valueOf(data.getSo_luong()));
        holder.ib_decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseValue(position, holder.edt_so_luong);
                // Cập nhật chi tiết dịch vụ phòng
                addChiTietDichVu(data.getId_dich_vu_phong(), data.getSo_luong());
            }
        });

        holder.ib_increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseValue(position, holder.edt_so_luong);
                // Cập nhật chi tiết dịch vụ phòng
                addChiTietDichVu(data.getId_dich_vu_phong(), data.getSo_luong());
            }
        });
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }
    public void GoiDuLieu(String idPhong) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("chi_tiet_dich_vu_phong").child(idPhong);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<chi_tiet_dich_vu_phong> newChiTietDichVuPhongs = new ArrayList<>();
                for (DataSnapshot FacilitySnapshot : dataSnapshot.getChildren()) {
                    chi_tiet_dich_vu_phong facilityDetail = FacilitySnapshot.getValue(chi_tiet_dich_vu_phong.class);
                    if (facilityDetail != null) {
                        newChiTietDichVuPhongs.add(facilityDetail);
                        for (dich_vu_phong dvp : datalist) {
                            if (dvp.getId_dich_vu_phong().equals(facilityDetail.getId_dich_vu_phong())) {
                                dvp.setSo_luong(facilityDetail.getSo_luong());
                            }
                            notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi
                Log.e("fetchFacilityDetails", "Failed to read value.", databaseError.toException());
            }
        });
    }

    void khoitao() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("dich_vu_phong");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                datalist.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    dich_vu_phong dichVuPhong = dataSnapshot.getValue(dich_vu_phong.class);
                    if (dichVuPhong != null) {
                        datalist.add(dichVuPhong);
                    }
                }
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
