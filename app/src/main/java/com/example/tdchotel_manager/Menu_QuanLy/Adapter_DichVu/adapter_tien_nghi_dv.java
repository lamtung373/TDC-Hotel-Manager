package com.example.tdchotel_manager.Menu_QuanLy.Adapter_DichVu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdchotel_manager.Model.tien_nghi;
import com.example.tdchotel_manager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class adapter_tien_nghi_dv extends RecyclerView.Adapter<adapter_tien_nghi_dv.MyViewHolder> {

    ArrayList<tien_nghi> datalist = new ArrayList<>();
    public adapter_tien_nghi_dv() {
        khoi_tao();
    }
    @NonNull
    @Override
    public adapter_tien_nghi_dv.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_dichvu, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull adapter_tien_nghi_dv.MyViewHolder holder, int position) {
        tien_nghi data = datalist.get(position);
//        holder.img.setText(String.valueOf(data.getId_phong()));
        holder.tvten.setText(data.getTen_tien_nghi());
        holder.tvgia.setText(String.valueOf(data.getGia_tien_nghi()));
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }
    public class MyViewHolder extends  RecyclerView.ViewHolder{
        ImageView imganhdv;
        TextView tvten,tvgia;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
//            imganhdv=itemView.findViewById(R.id.imgvDV);
            tvten=itemView.findViewById(R.id.tvTenDv1);
            tvgia=itemView.findViewById(R.id.tvGia1);
        }
    }
    void khoi_tao(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("tien_nghi");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                datalist.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    tien_nghi  tienNghi = dataSnapshot.getValue(tien_nghi.class);
                    if(tienNghi != null){
                        datalist.add(tienNghi);
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

