package com.example.tdchotel_manager.Menu_QuanLy.Adapter_DichVu;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdchotel_manager.Model.dich_vu;
import com.example.tdchotel_manager.Model.dich_vu_phong;
import com.example.tdchotel_manager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class adapter_dich_vu_phong extends RecyclerView.Adapter<adapter_dich_vu_phong.MyViewHolder> {
    ArrayList<dich_vu_phong> datalist = new ArrayList<>();
    public adapter_dich_vu_phong( ) {
        khoi_tao();
    }
    @NonNull
    @Override
    public adapter_dich_vu_phong.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_dichvu, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_dich_vu_phong.MyViewHolder holder, int position) {
        dich_vu_phong data = datalist.get(position);
//      holder.img.setText(String.valueOf(data.getId_phong()));
        holder.tvten.setText(data.getTen_dich_vu_phong());
        holder.tvgia.setText(String.valueOf(data.getGia_dich_vu_phong()));
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
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("dich_vu_phong");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                datalist.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    dich_vu_phong  dichVuPhong = dataSnapshot.getValue(dich_vu_phong.class);
                    if(dichVuPhong != null){
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
