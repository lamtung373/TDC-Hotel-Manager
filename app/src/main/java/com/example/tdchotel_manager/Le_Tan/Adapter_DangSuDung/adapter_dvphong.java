package com.example.tdchotel_manager.Le_Tan.Adapter_DangSuDung;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdchotel_manager.Le_Tan.Adapter.Adapter_DVTheoPhong;
import com.example.tdchotel_manager.Model.chi_tiet_hoa_don_dich_vu;
import com.example.tdchotel_manager.Model.dich_vu;
import com.example.tdchotel_manager.Model.loai_dich_vu;
import com.example.tdchotel_manager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class adapter_dvphong extends RecyclerView.Adapter<adapter_dvphong.DVTheoPhong_Holder> {
    ArrayList<dich_vu> data_dv = new ArrayList<>();


    private String idHoaDon;

    public adapter_dvphong() {
        Initialization();
    }

    public void setIdHoaDon(String idHoaDon) {
        this.idHoaDon = idHoaDon;
    }

    public ArrayList<dich_vu> getData_dv() {
        return data_dv;
    }

    public void setData_dv(ArrayList<dich_vu> data_dv) {
        this.data_dv = data_dv;
    }

    @NonNull
    @Override
    public DVTheoPhong_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_dv_theo_phong, parent, false);
        return new DVTheoPhong_Holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DVTheoPhong_Holder holder, int position) {
        if (!data_dv.isEmpty() && data_dv != null) {
            dich_vu currentDV = data_dv.get(position);

            holder.tv_dv.setText(currentDV.getTen_dich_vu());
            holder.tvGia.setText(currentDV.getGia_dich_vu() + "đ/phòng");

            DatabaseReference referenceChiTietDV = FirebaseDatabase.getInstance().getReference("chi_tiet_hoa_don_dich_vu")
                    .child(idHoaDon)
                    .child(currentDV.getId_dich_vu());

            referenceChiTietDV.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        chi_tiet_hoa_don_dich_vu chiTietDV = snapshot.getValue(chi_tiet_hoa_don_dich_vu.class);
                        if (chiTietDV != null) {
                            int soLuong = chiTietDV.getSo_luong();

                            // Set trạng thái của CheckBox dựa trên giá trị của số lượng
                            holder.cb.setChecked(soLuong > 0);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

            holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        // Nếu CheckBox được chọn, set số lượng là 1 và lưu vào Firebase
                        currentDV.setSo_luong(1);
                        referenceChiTietDV.setValue(new chi_tiet_hoa_don_dich_vu(1, idHoaDon, currentDV.getId_dich_vu()));
                    } else {
                        // Nếu CheckBox không được chọn, set số lượng là 0 và xóa dữ liệu từ Firebase
                        currentDV.setSo_luong(0);
                        referenceChiTietDV.removeValue();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data_dv.size();
    }

    class DVTheoPhong_Holder extends RecyclerView.ViewHolder {
        TextView tv_dv, tvGia;
        CheckBox cb;

        public DVTheoPhong_Holder(@NonNull View itemView) {
            super(itemView);
            tv_dv = itemView.findViewById(R.id.tvdichvu);
            tvGia = itemView.findViewById(R.id.tvGia);
            cb = itemView.findViewById(R.id.cb);
        }
    }

    private void Initialization() {
        DatabaseReference reference_dichvu = FirebaseDatabase.getInstance().getReference("dich_vu");
        DatabaseReference reference_loaidichvu = FirebaseDatabase.getInstance().getReference("loai_dich_vu");

        reference_dichvu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                reference_loaidichvu.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        data_dv.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            loai_dich_vu loaiDichVu = dataSnapshot.getValue(loai_dich_vu.class);
                            reference_dichvu.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (loaiDichVu.getId_loai_dich_vu().equals("1")) {
                                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                            dich_vu dichVu = dataSnapshot.getValue(dich_vu.class);
                                            if (dichVu.getId_loai_dich_vu().equals(loaiDichVu.getId_loai_dich_vu())) {
                                                data_dv.add(dichVu);
                                            }
                                        }
                                        notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}