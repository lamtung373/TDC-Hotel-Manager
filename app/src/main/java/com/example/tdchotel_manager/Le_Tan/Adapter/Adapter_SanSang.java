package com.example.tdchotel_manager.Le_Tan.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdchotel_manager.Le_Tan.Activity_Chi_Tiet_Phong;
import com.example.tdchotel_manager.Model.phong;
import com.example.tdchotel_manager.Model.trang_thai_phong;
import com.example.tdchotel_manager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter_SanSang extends RecyclerView.Adapter<Adapter_SanSang.MyViewHolder> {
    // Member Variables
    private Context context;
    private ArrayList<phong> room_list = new ArrayList<>();
    ArrayList<phong> data_original = new ArrayList<>();

    public Adapter_SanSang(Context context, ProgressBar progressBar, ProgressBar progressBar_itemphong) {
        this.context = context;
        this.progressBar = progressBar;
        this.progressBar_itemphong = progressBar_itemphong;
        khoi_tao();

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_phong, parent, false);
        return new Adapter_SanSang.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        phong data = room_list.get(position);
        if (data.getAnh_phong() != null && !data.getAnh_phong().isEmpty()) {
            String imageUrl = data.getAnh_phong().get(0); // Sử dụng phần tử đầu tiên hoặc bất kỳ phần tử nào bạn muốn hiển thị

            // Hiển thị ProgressBar
            holder.progressBar_itemphong.setVisibility(View.VISIBLE);

            // Sử dụng Picasso để tải ảnh
            Picasso.get().load(imageUrl).into(holder.iv_item_phong, new Callback() {
                @Override
                public void onSuccess() {
                    // Ẩn ProgressBar khi tải thành công
                    holder.progressBar_itemphong.setVisibility(View.GONE);
                }

                @Override
                public void onError(Exception e) {
                    // Ẩn ProgressBar và xử lý lỗi
                    holder.progressBar_itemphong.setVisibility(View.GONE);
                    // Bạn có thể đặt một ảnh mặc định nếu tải ảnh thất bại
                    holder.iv_item_phong.setImageResource(R.drawable.phong); // Thay thế bằng ảnh mặc định của bạn
                }
            });
        } else {
            // Set a default image or hide the ImageView
            holder.iv_item_phong.setImageResource(R.drawable.phong); // Replace with your default image resource
            // Ẩn ProgressBar vì không có ảnh để tải
            holder.progressBar_itemphong.setVisibility(View.GONE);
        }
        holder.tv_name_room.setText(String.valueOf(data.getTen_phong()));
        holder.tv_price.setText(String.valueOf(data.getGia()));
        holder.tv_price.setPaintFlags(holder.tv_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tv_sale.setText(String.valueOf(data.getSale()) + " VNĐ");
        holder.tv_type_room.setText(data.getLoai_phong());

        // Click listener to start a new activity
        holder.itemView.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                Intent intent = new Intent(context, Activity_Chi_Tiet_Phong.class);
                intent.putExtra("phong", room_list.get(adapterPosition));
                context.startActivity(intent);
            }
        });
    }

    public ArrayList<phong> getData_original() {
        return data_original;
    }

    public void setData_original(ArrayList<phong> data_original) {
        this.data_original = data_original;
    }

    ProgressBar progressBar, progressBar_itemphong;

    public void ClearData() {
        room_list.clear();
        notifyDataSetChanged();
    }

    public void Load() {
        room_list.addAll(data_original);
        notifyDataSetChanged();
    }

    public ArrayList<phong> getRoom_list() {
        return room_list;
    }

    public void setRoom_list(ArrayList<phong> room_list) {
        this.room_list = room_list;
    }

    @Override
    public int getItemCount() {
        if (room_list == null || room_list.isEmpty()) {
            return 0;
        }
        return room_list.size();
    }

    private void khoi_tao() {
        progressBar.setVisibility(View.VISIBLE);
        // Fetch data from Firebase
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("phong");
        DatabaseReference reference_status = FirebaseDatabase.getInstance().getReference("trang_thai_phong");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                room_list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    phong phong = dataSnapshot.getValue(phong.class);
                    reference_status.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                trang_thai_phong status = dataSnapshot.getValue(trang_thai_phong.class);
                                if (status.getId_trang_thai_phong().equals(phong.getId_trang_thai_phong()) && status.getTen_trang_thai().equals("Sẵn sàng")) {
                                    room_list.add(phong);
                                    data_original.add(phong);
                                }
                            }
                            notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
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

    // ViewHolder Class
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_item_phong;
        TextView tv_name_room, tv_price, tv_sale, tv_type_room, tv_status_room;
        ProgressBar progressBar_itemphong;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv_item_phong = itemView.findViewById(R.id.iv_item_phong);
            tv_name_room = itemView.findViewById(R.id.tv_name_room);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_sale = itemView.findViewById(R.id.tv_sale);
            tv_type_room = itemView.findViewById(R.id.tv_type_room);
            tv_status_room = itemView.findViewById(R.id.tv_status_room);
            progressBar_itemphong = itemView.findViewById(R.id.progressBar_itemphong);
            tv_status_room.setVisibility(View.GONE);
        }
    }
}
