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
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdchotel_manager.Menu_QuanLy.Activity_Thong_Tin_Phong;
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
    private ArrayList<khach_hang> khachhangList = new ArrayList<>();
    private Context context;

    public adapter_dangsudung(Context context) {
        this.context = context;
        khoi_tao();
        loadPhongData();
        loadKhachData();

        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                showItemOptions(datalist.get(position).getId_phong(), datalist.get(position).getId_hoa_don());
            }
        });
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

    private void loadKhachData() {
        DatabaseReference khachHangReference = FirebaseDatabase.getInstance().getReference("khach_hang");
        khachHangReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                khachhangList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    khach_hang kh = dataSnapshot.getValue(khach_hang.class);
                    if (kh != null) {
                        khachhangList.add(kh);
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
        holder.tv_mahoadon.setText(dataItem.getId_hoa_don());
        holder.tv_ngaynhan.setText(dataItem.getThoi_gian_nhan_phong());
        holder.tv_ngaytra.setText(dataItem.getThoi_gian_tra_phong());
        holder.tv_datra.setText(String.valueOf(dataItem.getTien_coc()));
        holder.tv_tong.setText(String.valueOf(dataItem.getTong_thanh_toan()));
        holder.tv_tenphong.setText(findTenPhongById(dataItem.getId_phong()));
        holder.tv_tenkhach.setText(findTenKhachById(dataItem.getSo_dien_thoai()));
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
        ////////////////
        holder.itemView.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                Intent intent = new Intent(context, Activity_Thong_Tin_Phong.class);
                intent.putExtra("hoa_don", datalist.get(adapterPosition));
                context.startActivity(intent);
            }
        });
    }
    //////////////

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

                        // Kiểm tra điều kiện để lấy dữ liệu đang sử dụng
                        if (idPhong != null && !thoiGianNhanPhong.equals("") && thoiGianThanhToan.equals("")) {
                            hoa_don hoaDon = childSnapshot.getValue(hoa_don.class);
                            datalist.add(hoaDon);
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
                    Intent intent2 = new Intent(context, dichvu_letan.class);
                    intent2.putExtra("idPhong", idPhong);
                    context.startActivity(intent2);
                }
            }
        });
        builder.show();
    }

    // Xác nhận trả phòng và cập nhật trạng thái phòng
    private void confirmTraPhong(String idPhong, MyViewHolder holder) {
        DatabaseReference hoaDonReference = FirebaseDatabase.getInstance().getReference("hoa_don").child(idPhong);
        DatabaseReference phongReference = FirebaseDatabase.getInstance().getReference("phong");

        hoaDonReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    phong phong = dataSnapshot.getValue(phong.class);
                    if (phong != null) {
                        phongReference.child(idPhong).child("id_trang_thai_phong").setValue("5");
                        Toast.makeText(context, "Phòng đã trả và đang được kiểm tra!", Toast.LENGTH_SHORT).show();
                        holder.isItemEnabled = false; // Vô hiệu hóa item sau khi trả phòng
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

    // Tìm kiếm tên phòng dựa vào ID phòng
    private String findTenPhongById(String idPhong) {
        for (phong phong : phongList) {
            if (phong.getId_phong().equals(idPhong)) {
                return phong.getTen_phong();
            }
        }
        return "Phòng không tồn tại";
    }

    // Tìm kiếm tên khách hàng dựa vào số điện thoại
    private String findTenKhachById(String sdt) {
        for (khach_hang kh : khachhangList) {
            if (kh.getSo_dien_thoai().equals(sdt)) {
                return kh.getTen();
            }
        }
        return "Tên khách hàng không tồn tại";
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
