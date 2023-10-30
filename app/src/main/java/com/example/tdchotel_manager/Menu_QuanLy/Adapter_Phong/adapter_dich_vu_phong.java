package com.example.tdchotel_manager.Menu_QuanLy.Adapter_Phong;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
    public adapter_dich_vu_phong(){
        khoitao();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_dich_vu_phong, parent, false);
        return new adapter_dich_vu_phong.MyViewHolder(itemView);
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_ten_dich_vu_phong;
        EditText edt_so_luong;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_ten_dich_vu_phong = itemView.findViewById(R.id.tv_ten_dich_vu_phong);
            edt_so_luong=itemView.findViewById(R.id.edt_so_luong_dvp);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        dich_vu_phong data = datalist.get(position);
        holder.tv_ten_dich_vu_phong.setText(data.getTen_dich_vu_phong());
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }
    void khoitao(){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("dich_vu_phong");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                datalist.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    dich_vu_phong dichVuPhong= dataSnapshot.getValue(dich_vu_phong.class);
                    if (dichVuPhong!=null){
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
