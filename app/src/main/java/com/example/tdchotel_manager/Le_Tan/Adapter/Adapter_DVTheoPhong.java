package com.example.tdchotel_manager.Le_Tan.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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

public class Adapter_DVTheoPhong extends RecyclerView.Adapter<Adapter_DVTheoPhong.DVTheoPhong_Holder> {
    ArrayList<dich_vu> data_dv = new ArrayList<>();

    public Adapter_DVTheoPhong() {
        Initialization();
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
            holder.tv_dv.setText(data_dv.get(position).getTen_dich_vu());
            holder.tvGia.setText(data_dv.get(position).getGia_dich_vu() + "đ/phòng");
            holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        data_dv.get(holder.getAdapterPosition()).setSo_luong(1);
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
        EditText edtSonguoi;
        CheckBox cb;

        public DVTheoPhong_Holder(@NonNull View itemView) {
            super(itemView);
            tv_dv = itemView.findViewById(R.id.tvdichvu);
            edtSonguoi = itemView.findViewById(R.id.edtSonguoi);
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
                                    if (loaiDichVu.getTen_loai_dich_vu().equals("Phòng")) {
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
