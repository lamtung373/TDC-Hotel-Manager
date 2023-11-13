package com.example.tdchotel_manager.Le_Tan.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdchotel_manager.Model.dich_vu;
import com.example.tdchotel_manager.Model.loai_dich_vu;
import com.example.tdchotel_manager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Adapter_DVTheoNguoi extends RecyclerView.Adapter<Adapter_DVTheoNguoi.DV_TheoNguoi_Holder> {
    ArrayList<dich_vu> data_dv=new ArrayList<>();

    public ArrayList<dich_vu> getData_dv() {
        return data_dv;
    }

    public void setData_dv(ArrayList<dich_vu> data_dv) {
        this.data_dv = data_dv;
    }

    public Adapter_DVTheoNguoi() {
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
