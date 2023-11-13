package com.example.tdchotel_manager.Menu_QuanLy.Adapter_Phong;

// Android imports

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// Firebase imports
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

// App-specific imports
import com.example.tdchotel_manager.Menu_QuanLy.Activity_Thong_Tin_Phong;
import com.example.tdchotel_manager.Model.phong;
import com.example.tdchotel_manager.Model.trang_thai_phong;
import com.example.tdchotel_manager.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class adapter_phong extends RecyclerView.Adapter<adapter_phong.MyViewHolder> {

    // Member Variables
    private Context context;
    private ArrayList<phong> room_list = new ArrayList<>();
    private ArrayList<trang_thai_phong> status_list = new ArrayList<>();
    private ArrayList<phong> danh_sach_phong = new ArrayList<>();
    private ArrayList<phong> danh_sach_phong_loc = new ArrayList<>();
    private OnItemLongClickListener onItemLongClickListener;
    private OnItemClickListener onItemClickListener;
    ProgressBar progressBar, progressBar_itemphong;

    // Constructor
    public adapter_phong(Context context, ProgressBar progressBar, ProgressBar progressBar_itemphong) {
        this.context = context;
        this.progressBar = progressBar;
        this.progressBar_itemphong = progressBar_itemphong;
        khoi_tao();
    }

    // Interface Definitions
    public interface OnItemLongClickListener {
        void onItemLongClick(int position);
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    // Accessors
    public ArrayList<phong> getDanh_sach_phong() {
        this.danh_sach_phong.addAll(room_list);
        return danh_sach_phong;
    }


    // Setters
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }
    // RecyclerView required methods
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_phong, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // Bind data to views
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
        java.text.DecimalFormat formatter = new java.text.DecimalFormat("#");
        if (data.getSale()!=0){
            holder.tv_sale.setVisibility(View.VISIBLE);
            holder.tv_price.setTextColor(Color.GRAY);
            holder.tv_price.setTextSize(13);
            holder.tv_price.setPaintFlags(holder.tv_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tv_price.setText(String.valueOf(formatter.format(data.getGia())));
            holder.tv_sale.setText(formatter.format(data.getSale()) + " VNĐ");
        }
        else {
            holder.tv_price.setText(formatter.format(data.getGia())+ " VNĐ");
            holder.tv_sale.setVisibility(View.GONE);
            holder.tv_price.setTextColor(Color.RED);
            holder.tv_price.setTextSize(15);
            holder.tv_price.setPaintFlags(holder.tv_price.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
        holder.tv_type_room.setText(data.getLoai_phong());
        holder.tv_status_room.setText(setStatusView(data.getId_trang_thai_phong()));

        // Long click listener for item
        holder.itemView.setOnLongClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION && onItemLongClickListener != null) {
                onItemLongClickListener.onItemLongClick(adapterPosition);
            }
            return true;
        });

        // Click listener to start a new activity
        holder.itemView.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                Intent intent = new Intent(context, Activity_Thong_Tin_Phong.class);
                intent.putExtra("phong", room_list.get(adapterPosition));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return room_list.size();
    }

    // Custom Methods
    public void removeItem(int position) {
        // Remove item from Firebase
        String objectIdToDelete = room_list.get(position).getId_phong();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("phong");
        databaseReference.child(objectIdToDelete).removeValue()
                .addOnSuccessListener(aVoid -> {
                    room_list.remove(position);
                    notifyItemRemoved(position);
                    Toast.makeText(context, "Xóa đối tượng thành công", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> Toast.makeText(context, "Xóa đối tượng thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    public String setStatusView(String id_status) {
        // Get the status from status_list based on id_status
        for (trang_thai_phong status : status_list) {
            if (id_status.equals(status.getId_trang_thai_phong())) {
                return status.getTen_trang_thai();
            }
        }
        return "Chưa tìm thấy dữ liệu trạng thái";
    }

    public void updateRoomList(ArrayList<phong> filteredRoomList) {
        room_list = filteredRoomList;
        notifyDataSetChanged();
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
                    phong rooms = dataSnapshot.getValue(phong.class);
                    if (rooms != null) room_list.add(rooms);
                }

                reference_status.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        status_list.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            trang_thai_phong status = dataSnapshot.getValue(trang_thai_phong.class);
                            if (status != null) status_list.add(status);
                        }
                        notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
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
        }
    }
}
