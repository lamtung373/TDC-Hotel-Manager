package com.example.tdchotel_manager.Lao_Cong;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

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


public class apdapter_don_kiemtra extends RecyclerView.Adapter<apdapter_don_kiemtra.MyViewHolder>{
    ArrayList<phong>clean_room_list=new ArrayList<>();
    ProgressBar progressBar_danhsach;
    private ArrayList<trang_thai_phong> trangthai = new ArrayList<>();
    Context context;
    public apdapter_don_kiemtra(Context context) {
        this.context=context;
        khoi_tao();
    }

    private void khoi_tao() {
        DatabaseReference reference_status = FirebaseDatabase.getInstance().getReference("trang_thai_phong");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("phong");

        // Lấy dữ liệu trạng thái phòng "Đang kiểm tra"
        reference_status.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> checkingStatusIds = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    trang_thai_phong status = dataSnapshot.getValue(trang_thai_phong.class);
                    if (status != null && "Đang kiểm tra".equals(status.getTen_trang_thai())) {
                        checkingStatusIds.add(status.getId_trang_thai_phong()); // Lưu ID
                    }
                }

                // Lấy và lọc dữ liệu phòng
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        clean_room_list.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            phong room = dataSnapshot.getValue(phong.class);
                            if (room != null && checkingStatusIds.contains(room.getId_trang_thai_phong())) {
                                clean_room_list.add(room);
                            }
                        }

                        // Cập nhật giao diện
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


    @NonNull
    @Override
    public apdapter_don_kiemtra.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_phong_laocong, parent, false);
        return new apdapter_don_kiemtra.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull apdapter_don_kiemtra.MyViewHolder holder, int position) {
        phong data = clean_room_list.get(position);
        if (data.getAnh_phong() != null && !data.getAnh_phong().isEmpty()) {
            String imageUrl = data.getAnh_phong().get(0); // Sử dụng phần tử đầu tiên hoặc bất kỳ phần tử nào bạn muốn hiển thị

            // Hiển thị ProgressBar
            holder.progressBar.setVisibility(View.VISIBLE);

            // Sử dụng Picasso để tải ảnh
            Picasso.get().load(imageUrl).into(holder.iv_anhphong_don, new Callback() {
                @Override
                public void onSuccess() {
                    // Ẩn ProgressBar khi tải thành công
                    holder.progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError(Exception e) {
                    // Ẩn ProgressBar và xử lý lỗi
                    holder.progressBar.setVisibility(View.GONE);
                    // Bạn có thể đặt một ảnh mặc định nếu tải ảnh thất bại
                    holder.iv_anhphong_don.setImageResource(R.drawable.phong); // Thay thế bằng ảnh mặc định của bạn
                }
            });
        }
        holder.tv_tenphong_don.setText(String.valueOf(data.getTen_phong()));
        holder.tv_trangthaiphong_don.setText("Đang kiểm tra");
        holder.btn_kiemtra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Activity_ChiTietDonPhong.class);
                intent.putExtra("idphong", data.getId_phong());
                context.startActivity(intent);
            }
        });
        holder.btn_hoanthanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog(data);
            }
        });
    }

    @Override
    public int getItemCount() {
        return clean_room_list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_anhphong_don;
        TextView tv_tenphong_don, tv_trangthaiphong_don;
        Button btn_kiemtra, btn_hoanthanh;
        ProgressBar progressBar;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_anhphong_don=itemView.findViewById(R.id.iv_phong_candon);
            tv_tenphong_don=itemView.findViewById(R.id.tv_tenphong_candon);
            tv_trangthaiphong_don=itemView.findViewById(R.id.tv_trangthaiphong_candon);
            btn_kiemtra=itemView.findViewById(R.id.btn_kiemtra);
            btn_hoanthanh=itemView.findViewById(R.id.btn_donxong);
            progressBar=itemView.findViewById(R.id.progressBar_itemphong_don);
        }
    }
    public void updateNgayDon(String phongId) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("phong");

        // Cập nhật thuộc tính ngay_don của phòng có ID cụ thể
        databaseReference.child(phongId).child("id_trang_thai_phong").setValue("1")
                .addOnSuccessListener(aVoid -> {
                    // Thành công, xử lý nếu cần
                })
                .addOnFailureListener(e -> {
                    // Lỗi, xử lý nếu cần
                });
    }
    private void showConfirmationDialog(phong data) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xác Nhận");
        builder.setMessage("Bạn xác nhận thực sự đã dọn xong phòng "+data.getTen_phong());

        // Nút "Có"
        builder.setPositiveButton("ĐÚNG", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Hành động khi người dùng chọn "Có"
                updateNgayDon(data.getId_phong());
            }
        });

        // Nút "Không"
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Hành động khi người dùng chọn "Không", ví dụ đóng dialog
                dialog.dismiss();
            }
        });

        // Tạo và hiển thị dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}