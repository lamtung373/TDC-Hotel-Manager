package com.example.tdchotel_manager.Menu_QuanLy.Adapter_Phong;

// Android imports
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// Firebase imports
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

import java.util.ArrayList;

public class adapter_phong extends RecyclerView.Adapter<adapter_phong.MyViewHolder> {

    // Member Variables
    private Context context;
    private ArrayList<phong> room_list = new ArrayList<>();
    private ArrayList<trang_thai_phong> status_list = new ArrayList<>();
    private ArrayList<phong> originalRoomList = new ArrayList<>();
    private OnItemLongClickListener onItemLongClickListener;
    private OnItemClickListener onItemClickListener;

    // Constructor
    public adapter_phong(Context context) {
        this.context = context;
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
    public ArrayList<phong> getOriginalRoomList() {
        this.originalRoomList.addAll(room_list);
        return originalRoomList;
    }

    public ArrayList<phong> getRoomList() {
        return room_list;
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
        holder.tv_name_room.setText(String.valueOf(data.getTen_phong()));
        holder.tv_price.setText(String.valueOf(data.getGia()));
        holder.tv_price.setPaintFlags(holder.tv_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tv_sale.setText(String.valueOf(data.getSale()) + " VNĐ");
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
}
