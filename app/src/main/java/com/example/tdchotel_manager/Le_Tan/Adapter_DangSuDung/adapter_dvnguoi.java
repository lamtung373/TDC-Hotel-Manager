package com.example.tdchotel_manager.Le_Tan.Adapter_DangSuDung;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdchotel_manager.Le_Tan.Adapter.Adapter_DVTheoNguoi;
import com.example.tdchotel_manager.Model.chi_tiet_hoa_don_dich_vu;
import com.example.tdchotel_manager.Model.chi_tiet_tien_nghi;
import com.example.tdchotel_manager.Model.dich_vu;
import com.example.tdchotel_manager.Model.loai_dich_vu;
import com.example.tdchotel_manager.Model.tien_nghi;
import com.example.tdchotel_manager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class adapter_dvnguoi extends RecyclerView.Adapter<adapter_dvnguoi.DV_TheoNguoi_Holder> {
    ArrayList<dich_vu> data_dv=new ArrayList<>();
ArrayList<chi_tiet_hoa_don_dich_vu> chiTietHoaDonDichVus = new ArrayList<>();

    public ArrayList<dich_vu> getData_dv() {
        return data_dv;
    }

    public void setData_dv(ArrayList<dich_vu> data_dv) {
        this.data_dv = data_dv;
    }

    public adapter_dvnguoi() {
        Initialization();
    }

    @NonNull
    @Override
    public DV_TheoNguoi_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_dichvu_nguoi, parent, false);
        return new DV_TheoNguoi_Holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DV_TheoNguoi_Holder holder, int position) {
        if(!data_dv.isEmpty()&&data_dv!=null) {
            holder.tv_dv.setText(data_dv.get(position).getTen_dich_vu());
            holder.tvGia.setText(data_dv.get(position).getGia_dich_vu() + "đ/người");
            holder.ivCong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int soluong = Integer.parseInt(holder.edtSonguoi.getText().toString().trim()) + 1;
                    holder.edtSonguoi.setText(soluong + "");
                    data_dv.get(holder.getAdapterPosition()).setSo_luong(soluong);
                }
            });
            holder.ivTru.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int soluong = Integer.parseInt(holder.edtSonguoi.getText().toString().trim()) - 1;
                    if (soluong >= 0) {
                        holder.edtSonguoi.setText(soluong + "");
                        data_dv.get(holder.getAdapterPosition()).setSo_luong(soluong);
                    }
                }
            });
        }
    }
    public void GoiDuLieu(String idHoaDon) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("chi_tiet_hoa_don_dich_vu").child(idHoaDon);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot facilitySnapshot : dataSnapshot.getChildren()) {
                    chi_tiet_hoa_don_dich_vu comfortDetail = facilitySnapshot.getValue(chi_tiet_hoa_don_dich_vu.class);
                    if (comfortDetail != null) {
                        chiTietHoaDonDichVus.add(comfortDetail);
                        //Log.e("ịgiejifejifjei","-"+comfortDetail.getSo_luong());
                        Log.e("ịgiejifejifjei","size"+data_dv.size());

                    }

                    for (int i=0;i<data_dv.size();i++){
                        if(comfortDetail.getId_dich_vu().equals(data_dv.get(i).getId_dich_vu())){
                            data_dv.get(i).setSo_luong(comfortDetail.getSo_luong());
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
    public int getItemCount() {
        return data_dv.size();
    }

    class DV_TheoNguoi_Holder extends RecyclerView.ViewHolder {
        TextView tv_dv, tvGia;
        EditText edtSonguoi;
        ImageView ivTru, ivCong;

        public DV_TheoNguoi_Holder(@NonNull View itemView) {
            super(itemView);
            tv_dv = itemView.findViewById(R.id.tvdichvu);
            edtSonguoi = itemView.findViewById(R.id.edtSonguoi);
            ivTru = itemView.findViewById(R.id.ivTru);
            ivCong = itemView.findViewById(R.id.ivCong);
            tvGia = itemView.findViewById(R.id.tvGia);
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
                                    if (loaiDichVu.getTen_loai_dich_vu().equals("Người")) {
                                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                            dich_vu dichVu = dataSnapshot.getValue(dich_vu.class);
                                            if (dichVu.getId_loai_dich_vu().equals(loaiDichVu.getId_loai_dich_vu())) {
                                                data_dv.add(dichVu);
                                                for(dich_vu dichVu1 : data_dv){
                                                    if(dichVu1.getId_dich_vu().equals(dichVu.getId_dich_vu()) ){
                                                        dichVu1.setSo_luong(dichVu.getSo_luong());
                                                    }
                                                }

                                            }
                                        }
                                        notifyDataSetChanged();
                                        Log.e("sizzzz",data_dv.size()+"");
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
