package com.example.tdchotel_manager.Le_Tan.Adapter_DangSuDung;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdchotel_manager.Le_Tan.Activity_HoaDon.Activity_HoaDon_DangSuDung;
import com.example.tdchotel_manager.Model.hoa_don;
import com.example.tdchotel_manager.Model.khach_hang;
import com.example.tdchotel_manager.Model.phong;
import com.example.tdchotel_manager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class adapter_dangsudung extends RecyclerView.Adapter<adapter_dangsudung.MyViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    private OnItemClickListener onItemClickListener;
    private ArrayList<hoa_don> datalist = new ArrayList<>();
    private ArrayList<phong> phongList = new ArrayList<>();
    private Context context;
    private ArrayList<hoa_don> originalDataList;

    public adapter_dangsudung(Context context) {
        this.context = context;
        this.datalist = new ArrayList<>(datalist);
        this.originalDataList = new ArrayList<>(datalist);
        khoi_tao();
        loadPhongData();

        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                showItemOptions(datalist.get(position).getId_phong(), datalist.get(position).getId_hoa_don());
            }
        });
    }
    public void filter(String text) {
        ArrayList<hoa_don> filteredList = new ArrayList<>();

        for (hoa_don hoaDon : originalDataList) {
            String tenPhong = findTenPhongById(hoaDon.getId_phong()).toLowerCase();
            if (tenPhong.contains(text.toLowerCase())) {
                filteredList.add(hoaDon);
            }
        }

        datalist.clear();
        datalist.addAll(filteredList);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    private void loadPhongData() {
        DatabaseReference phongReference = FirebaseDatabase.getInstance().getReference("phong");
        phongReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                phongList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    phong phong = dataSnapshot.getValue(phong.class);
                    if (phong != null) {
                        phongList.add(phong);
                    }
                }
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi nếu cần
            }
        });
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_dang_su_dung, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_dangsudung.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        hoa_don dataItem = datalist.get(position);

        // Kiểm tra nếu trạng thái phòng là 1
        if (findTenTrangThaiPhongById(dataItem.getId_phong()).equals("Sẵn sàng")) {
         return;
        } else {
            // Nếu trạng thái phòng không phải là 1, tiến hành với quy trình gắn kết thông thường
            holder.itemView.setVisibility(View.VISIBLE);
        }
        if (findTenTrangThaiPhongById(dataItem.getId_phong()).equals("Đang kiểm tra")) {
            holder.isItemEnabled = false;
        } else {
            holder.isItemEnabled = true;
        }
        holder.tv_mahoadon.setText(dataItem.getId_hoa_don());
        holder.tv_ngaynhan.setText(dataItem.getThoi_gian_nhan_phong());
        holder.tv_ngaytra.setText(dataItem.getThoi_gian_tra_phong());
        holder.tv_datra.setText(String.valueOf(dataItem.getTien_coc()));
        holder.tv_tong.setText(String.valueOf(dataItem.getTong_thanh_toan()));
        holder.tv_tenphong.setText(findTenPhongById(dataItem.getId_phong()));
        holder.tv_tenkhach.setText(dataItem.getTen_khach_hang());

        holder.tv_trangthai.setText(findTenTrangThaiPhongById(dataItem.getId_phong()));

        holder.traphong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.isItemEnabled) {
                    confirmTraPhong(dataItem.getId_phong(), holder);
                } else {
                    // Bạn có thể hiển thị một thông báo hoặc không làm gì cả khi item bị vô hiệu hóa
                }
            }
        });


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null && holder.isItemEnabled) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });
        holder.thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Activity_HoaDon_DangSuDung.class);
                intent.putExtra("hoa_don", dataItem);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        boolean isItemEnabled = true;
        LinearLayout layout;
        Button traphong,thanhtoan;
        TextView tv_trangthai, tv_mahoadon, tv_ngaynhan, tv_ngaytra, tv_datra, tv_tong, tv_tenphong, tv_tenkhach;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            thanhtoan = itemView.findViewById(R.id.btnXacNhanThanhToan);
            tv_trangthai = itemView.findViewById(R.id.tvTrangThaiPhong);
            traphong = itemView.findViewById(R.id.btnXacNhanTraPhong);
            tv_tenkhach = itemView.findViewById(R.id.tvTenKhachHang);
            tv_tenphong = itemView.findViewById(R.id.tvTenPhong);
            tv_mahoadon = itemView.findViewById(R.id.tvMaHoaDon);
            tv_ngaynhan = itemView.findViewById(R.id.tvNgayNhanPhong);
            tv_ngaytra = itemView.findViewById(R.id.tvNgayTraPhong);
            layout = itemView.findViewById(R.id.layout_hoadon);
            tv_datra = itemView.findViewById(R.id.tvDaTra);
            tv_tong = itemView.findViewById(R.id.tvTong);
        }
    }

    // Phương thức này khởi tạo dữ liệu từ Firebase và thêm vào ArrayList datalist
    void khoi_tao() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference hoaDonRef = database.getReference("hoa_don");

        hoaDonRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                datalist.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        String idPhong = childSnapshot.child("id_phong").getValue(String.class);
                        String thoiGianNhanPhong = childSnapshot.child("thoi_gian_nhan_phong").getValue(String.class);
                        String thoiGianThanhToan = childSnapshot.child("thoi_gian_thanh_toan").getValue(String.class);

                        // Kiểm tra điều kiện để lấy dữ liệu đang sử dụng và trạng thái phòng khác 1
                        if (idPhong != null && !thoiGianNhanPhong.equals("") && thoiGianThanhToan.equals("") && !findTenTrangThaiPhongById(idPhong).equals("Sẵn sàng")) {
                            hoa_don hoaDon = childSnapshot.getValue(hoa_don.class);
                            datalist.add(hoaDon);
                            originalDataList.add(hoaDon);
                        }
                    }
                }
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý lỗi
            }
        });
    }


    // Hiển thị dialog chọn hình thức khi click vào item
    private void showItemOptions(String idPhong, String idHoaDon) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Lựa chọn hình thức");
        builder.setItems(new CharSequence[]{"Gia hạn", "Dịch vụ"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    Intent intent = new Intent(context, giahanthoigian.class);
                    intent.putExtra("idPhong", idPhong);
                    intent.putExtra("idHoaDon", idHoaDon);
                    context.startActivity(intent);
                } else if (which == 1) {
                    Intent intent2 = new Intent(context, Activity_dichvu_letan.class);
//                    intent2.putExtra("idPhong", idPhong);
                    intent2.putExtra("idHoaDon", idHoaDon);
                    context.startActivity(intent2);
                }
            }
        });
        builder.show();
    }

    private void confirmTraPhong(String idPhong, MyViewHolder holder) {
        DatabaseReference hoaDonReference = FirebaseDatabase.getInstance().getReference("hoa_don").child(idPhong);
        DatabaseReference phongReference = FirebaseDatabase.getInstance().getReference("phong");

        hoaDonReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    phong phong = dataSnapshot.getValue(phong.class);
                    if (phong != null) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Xác nhận trả phòng");
                        builder.setMessage("Bạn có chắc muốn trả phòng?");
                        builder.setIcon(R.drawable.warning);
                        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                phongReference.child(idPhong).child("id_trang_thai_phong").setValue("5", new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        if (error == null) {
                                            Toast.makeText(context, "Phòng đã trả và đang được kiểm tra!", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                            notifyDataSetChanged();
                                        } else {
                                            Toast.makeText(context, "Đã xảy ra lỗi khi cập nhật trạng thái phòng!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        });
                        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Đóng dialog nếu không muốn trả phòng
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi nếu cần
            }
        });
    }

    // Tìm kiếm tên phòng dựa vào ID phòng
    private String findTenPhongById(String idPhong) {
        for (phong phong : phongList) {
            if (phong.getId_phong().equals(idPhong)) {
                return phong.getTen_phong();
            }
        }
        return "Phòng không tồn tại";
    }

    // Tìm kiếm tên trạng thái phòng dựa vào ID phòng
    private String findTenTrangThaiPhongById(String idPhong) {
        for (phong phong : phongList) {
            if (phong.getId_phong().equals(idPhong)) {
                return mapIdToTenTrangThai(phong.getId_trang_thai_phong());
            }
        }
        return "Trạng thái không xác định";
    }

    // Ánh xạ ID trạng thái phòng sang tên trạng thái
    private String mapIdToTenTrangThai(String idTrangThai) {
        switch (idTrangThai) {
            case "1":
                return "Sẵn sàng";
            case "2":
                return "Đang sửa";
            case "3":
                return "Đã đặt";
            case "4":
                return "Đang sử dụng";
            case "5":
                return "Đang kiểm tra";
            case "6":
                return "Đang dọn";
            default:
                return "Trạng thái không xác định";
        }
    }
}
