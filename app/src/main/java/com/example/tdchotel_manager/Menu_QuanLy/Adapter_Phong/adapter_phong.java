package com.example.tdchotel_manager.Menu_QuanLy.Adapter_Phong;

import android.app.ProgressDialog;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdchotel_manager.Model.phong;
import com.example.tdchotel_manager.Model.trang_thai_phong;
import com.example.tdchotel_manager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class adapter_phong extends RecyclerView.Adapter<adapter_phong.MyViewHolder> {
    private ArrayList<phong> room_list = new ArrayList<>();
    private ArrayList<trang_thai_phong> status_list = new ArrayList<>();
    private ProgressDialog progressDialog;

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
        // Đặt dữ liệu cho mỗi item
        phong data = room_list.get(position);
        holder.tv_name_room.setText(String.valueOf(data.getTen_phong()));
        holder.tv_price.setText(String.valueOf(data.getGia()));
        holder.tv_price.setPaintFlags(holder.tv_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tv_sale.setText(String.valueOf(data.getSale()) + " VNĐ");
        holder.tv_type_room.setText(data.getLoai_phong());
        holder.tv_status_room.setText(setStatusView(data.getId_trang_thai_phong()));
    }

    public String setStatusView(String id_status) {
        String status = "Chưa tìm thấy dữ liệu trạng thái";
        for (int i = 0; i < status_list.size(); i++) {
            if (id_status.equals(status_list.get(i).getId_trang_thai_phong())) {
                status = status_list.get(i).getTen_trang_thai();
                Log.e("status_list_12345678", status.toString());
                return status;
            }
        }
        return status;
    }

    @Override
    public int getItemCount() {
        return room_list.size();
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
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("phong");
        DatabaseReference reference_status = FirebaseDatabase.getInstance().getReference("trang_thai_phong");
        room_list.clear();
        status_list.clear();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    phong rooms = dataSnapshot.getValue(phong.class);
                    if (rooms != null) {
                        room_list.add(rooms);
                    }
                }

                // Sau khi lấy dữ liệu xong, thêm listener cho reference_status
                reference_status.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            trang_thai_phong status = dataSnapshot.getValue(trang_thai_phong.class);
                            if (status != null) {
                                status_list.add(status);
                            } else {
                                Log.e("Lỗi getdata từ Firebase", "Không thể tải dữ liệ phòng");
                            }
                        }

                        // Sau khi cập nhật status_list, gọi notifyDataSetChanged()
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Xử lý lỗi
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi
            }
        });
    }
}
