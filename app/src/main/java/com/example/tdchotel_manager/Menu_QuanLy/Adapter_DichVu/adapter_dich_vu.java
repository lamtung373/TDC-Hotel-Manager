package com.example.tdchotel_manager.Menu_QuanLy.Adapter_DichVu;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdchotel_manager.Model.dich_vu;
import com.example.tdchotel_manager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class adapter_dich_vu extends RecyclerView.Adapter<adapter_dich_vu.MyViewHolder> {

    ArrayList<dich_vu> datalist = new ArrayList<>();
    public adapter_dich_vu() {
        khoi_tao();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_dichvu, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        dich_vu data = datalist.get(position);
//        holder.img.setText(String.valueOf(data.getId_phong()));
        holder.tvten.setText(data.getTen_dich_vu());
        holder.tvgia.setText(String.valueOf(data.getGia_dich_vu()));
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
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("dich_vu");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                datalist.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    dich_vu  dichVu = dataSnapshot.getValue(dich_vu.class);
                    if(dichVu != null){
                        datalist.add(dichVu);
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