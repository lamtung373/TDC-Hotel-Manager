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

import com.example.tdchotel_manager.Model.chi_tiet_tien_nghi;
import com.example.tdchotel_manager.Model.tien_nghi;
import com.example.tdchotel_manager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class adapter_tien_nghi extends RecyclerView.Adapter<adapter_tien_nghi.MyViewHolder> {
    private ArrayList<tien_nghi> dataList = new ArrayList<>();
    private ArrayList<chi_tiet_tien_nghi> chi_tiet_tien_nghis = new ArrayList<>();

    public void addChiTietDichVu(String dichVuPhongId, int soLuong) {
        for (int i = 0; i < chi_tiet_tien_nghis.size(); i++) {
            if (dichVuPhongId.equals(chi_tiet_tien_nghis.get(i).getId_tien_nghi())) {
                chi_tiet_tien_nghis.get(i).setSo_luong(soLuong);
                notifyDataSetChanged();
                return;
            }
        }
        // Nếu không tìm thấy, thêm đối tượng mới vào danh sách
        chi_tiet_tien_nghi cttn = new chi_tiet_tien_nghi();
        cttn.setId_tien_nghi(dichVuPhongId);
        cttn.setSo_luong(soLuong);
        chi_tiet_tien_nghis.add(cttn);
        notifyDataSetChanged();

    }


    public ArrayList<chi_tiet_tien_nghi> getChi_tiet_tien_nghis() {

        return chi_tiet_tien_nghis;

    }

    public adapter_tien_nghi() {
        khoi_tao();
    }

    @NonNull
    @Override
    public adapter_tien_nghi.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_dich_vu_phong, parent, false);
        return new adapter_tien_nghi.MyViewHolder(itemView);
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
        tien_nghi item = dataList.get(position);
        int value = item.getSo_luong();
        value++;
        item.setSo_luong(value); // Cập nhật giá trị trong dataList
        editText.setText(String.valueOf(value));
        notifyItemChanged(position); // Cập nhật chỉ item tại vị trí này
    }

    private void decreaseValue(int position, EditText editText) {
        tien_nghi item = dataList.get(position);
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
    public void onBindViewHolder(@NonNull adapter_tien_nghi.MyViewHolder holder, int position) {
        tien_nghi data = dataList.get(position);
        holder.tv_ten_tien_nghi.setText(data.getTen_tien_nghi());
        holder.edt_so_luong.setText(String.valueOf(data.getSo_luong()));
        holder.ib_decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseValue(position, holder.edt_so_luong);
                // Cập nhật chi tiết dịch vụ phòng
                addChiTietDichVu(data.getId_tien_nghi(), data.getSo_luong());
            }
        });

        holder.ib_increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseValue(position, holder.edt_so_luong);
                // Cập nhật chi tiết dịch vụ phòng
                addChiTietDichVu(data.getId_tien_nghi(), data.getSo_luong());
            }
        });
    }

    private chi_tiet_tien_nghi findChiTietTienNghiById(String id) {
        for (chi_tiet_tien_nghi cttn : chi_tiet_tien_nghis) {
            if (cttn.getId_tien_nghi().equals(id)) {
                return cttn;
            }
        }
        return null;
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void GoiDuLieu(String idPhong) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("chi_tiet_tien_nghi").child(idPhong);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<chi_tiet_tien_nghi> newChiTietTienNghis = new ArrayList<>();
                for (DataSnapshot facilitySnapshot : dataSnapshot.getChildren()) {
                    chi_tiet_tien_nghi comfortDetail = facilitySnapshot.getValue(chi_tiet_tien_nghi.class);
                    if (comfortDetail != null) {
                        newChiTietTienNghis.add(comfortDetail);
                    }
                    for (tien_nghi ct : dataList) {
                        if (ct.getId_tien_nghi().equals(comfortDetail.getId_tien_nghi())) {
                            ct.setSo_luong(comfortDetail.getSo_luong());
                        }
                        notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi
                Log.e("fetchComfortDetails", "Failed to read value.", databaseError.toException());
            }
        });
    }


    void khoi_tao() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("tien_nghi");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear(); // Xóa dữ liệu cũ trước khi thêm dữ liệu mới để tránh trùng lặp

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    tien_nghi tienNghi = dataSnapshot.getValue(tien_nghi.class);
                    if (tienNghi != null) {
                        dataList.add(tienNghi);
// Tạo một đối tượng chi tiết tiện nghi mới với số lượng mặc định là 0
                        chi_tiet_tien_nghi cttn = new chi_tiet_tien_nghi();
                        cttn.setId_tien_nghi(tienNghi.getId_tien_nghi());
                        cttn.setSo_luong(0); // Hoặc giá trị mặc định nào đó
                        chi_tiet_tien_nghis.add(cttn);
                    }
                }

                notifyDataSetChanged(); // Cập nhật RecyclerView khi dữ liệu thay đổi
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi nếu có
            }
        });
    }

}

