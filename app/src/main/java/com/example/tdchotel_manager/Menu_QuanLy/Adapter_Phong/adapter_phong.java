package com.example.tdchotel_manager.Menu_QuanLy.Adapter_Phong;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdchotel_manager.Model.phong;
import com.example.tdchotel_manager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class adapter_phong extends RecyclerView.Adapter<adapter_phong.MyViewHolder> {
    private ArrayList<phong> dataList = new ArrayList<>();

    public adapter_phong() {
        khoi_tao();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_phong, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //Set dữ liệu của item
        phong data = dataList.get(position);
        holder.tv_name_room.setText(String.valueOf(data.getTen_phong()));
        holder.tv_price.setText(String.valueOf(data.getGia()));
        holder.tv_price.setPaintFlags(holder.tv_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tv_sale.setText(String.valueOf(data.getSale()));
        holder.tv_type_room.setText(data.getLoai_phong());
        holder.tv_status_room.setText(String.valueOf(data.getTrang_thai()));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_item_phong;
        TextView tv_name_room, tv_price, tv_sale, tv_type_room, tv_status_room;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv_item_phong = itemView.findViewById(R.id.iv_item_phong);
            tv_name_room = itemView.findViewById(R.id.tv_name_room);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_sale = itemView.findViewById(R.id.tv_sale);
            tv_type_room = itemView.findViewById(R.id.tv_type_room);
            tv_status_room = itemView.findViewById(R.id.tv_status_room);
        }
    }

    private void khoi_tao() {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("phong");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    phong rooms= dataSnapshot.getValue(phong.class);
                    if (rooms!=null){
                        dataList.add(rooms);
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
