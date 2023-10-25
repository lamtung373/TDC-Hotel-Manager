package com.example.tdchotel_manager.Menu_QuanLy.Adapter_Phong;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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
import java.util.Arrays;
import java.util.List;

public class adapter_tien_nghi extends RecyclerView.Adapter<adapter_tien_nghi.MyViewHolder> {
    private ArrayList<tien_nghi> dataList= new ArrayList<>();

    public adapter_tien_nghi() {
        khoi_tao();
    }

    @Override
    public adapter_tien_nghi.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_tien_nghi, parent, false);
        return new adapter_tien_nghi.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(adapter_tien_nghi.MyViewHolder holder, int position) {
        //Set dữ liệu của item
        tien_nghi data = dataList.get(position);
        holder.cb_tien_nghi.setChecked(false);
        holder.tv_tien_nghi.setText(data.getTen_tien_nghi());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox cb_tien_nghi;
        TextView tv_tien_nghi;

        public MyViewHolder(View itemView) {
            super(itemView);
            cb_tien_nghi = itemView.findViewById(R.id.cb_tien_nghi);
            tv_tien_nghi = itemView.findViewById(R.id.tv_tien_nghi);
        }
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

