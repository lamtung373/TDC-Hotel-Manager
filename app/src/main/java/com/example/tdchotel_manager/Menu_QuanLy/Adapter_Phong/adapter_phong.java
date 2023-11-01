package com.example.tdchotel_manager.Menu_QuanLy.Adapter_Phong;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdchotel_manager.Menu_QuanLy.Activity_Thong_Tin_Phong;
import com.example.tdchotel_manager.Menu_QuanLy.Fragment_Phong;
import com.example.tdchotel_manager.Model.phong;
import com.example.tdchotel_manager.Model.trang_thai_phong;
import com.example.tdchotel_manager.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import android.content.Context;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class adapter_phong extends RecyclerView.Adapter<adapter_phong.MyViewHolder> {
    private AdapterView.OnItemClickListener onItemClickListener;

    private ArrayList<phong> room_list = new ArrayList<>();
    private ArrayList<trang_thai_phong> status_list = new ArrayList<>();
    private ArrayList<phong> originalRoomList = new ArrayList<>();
    private Context context;

    public adapter_phong(Context context) {
        this.context = context;
        khoi_tao();
    }

    // Lấy danh sách ban đầu cho việc tìm kiếm
    public ArrayList<phong> getOriginalRoomList() {
        this.originalRoomList.addAll(room_list);
        return originalRoomList;
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

        // Xử lý sự kiện nhấn giữ
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    if (onItemLongClickListener != null) {
                        onItemLongClickListener.onItemLongClick(adapterPosition);
                    }
                }
                return true;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    if (onItemLongClickListener != null) {
                        Intent intent=new Intent(context, Activity_Thong_Tin_Phong.class);
                        intent.putExtra("phong",room_list.get(adapterPosition));
                        context.startActivity(intent);
                    }
                }
            }
        });
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(int position);
    }
    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    public void removeItem(int position) {
        String objectIdToDelete = room_list.get(position).getId_phong();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("phong");
        databaseReference.child(objectIdToDelete).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                room_list.remove(position);  // Cập nhật dữ liệu
                notifyItemRemoved(position);
                Toast.makeText(context, "Xóa đối tượng thành công", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Xóa đối tượng thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private OnItemLongClickListener onItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
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

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (onItemLongClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onItemLongClickListener.onItemLongClick(position);
                            return true;
                        }
                    }
                    return false;
                }
            });
        }
    }

    public void updateRoomList(ArrayList<phong> filteredRoomList) {
        room_list = filteredRoomList; // Cập nhật danh sách phòng
        notifyDataSetChanged(); // Thông báo cho adapter cập nhật dữ liệu
    }

    public ArrayList<phong> getRoomList() {
        return room_list;
    }

    private void khoi_tao() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("phong");
        DatabaseReference reference_status = FirebaseDatabase.getInstance().getReference("trang_thai_phong");


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                room_list.clear();
                status_list.clear();
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
                                Log.e("Lỗi getdata từ Firebase", "Không thể tải dữ liệu phòng");
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