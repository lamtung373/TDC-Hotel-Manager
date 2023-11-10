package com.example.tdchotel_manager.Le_Tan.Fragment_LeTan.Adapter_DangSuDung;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
    public interface OnItemLongClickListener {
        void onItemLongClick(int position);
    }

    private OnItemLongClickListener onItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }

    private ArrayList<hoa_don> datalist = new ArrayList<>();
    private ArrayList<phong> phongList = new ArrayList(); // Danh sách các phòng
    private ArrayList<khach_hang> khachhangList = new ArrayList(); // Danh sách các phòng

    Context context;

    public adapter_dangsudung(Context context) {
        this.context = context;
        khoi_tao();
        loadPhongData(); // Tải dữ liệu của các phòng
        loadKhachData(); // Tải dữ liệu của các phòng


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
        DatabaseReference phongReference = FirebaseDatabase.getInstance().getReference("khach_hang");
        phongReference.addValueEventListener(new ValueEventListener() {
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

        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickListener != null) {
                    onItemLongClickListener.onItemLongClick(position);
                }
                return true;
            }
        });
    }

    private String findTenPhongById(String idPhong) {
        for (phong phong : phongList) {
            if (phong.getId_phong().equals(idPhong)) {
                return phong.getTen_phong();
            }
        }
        return "Phòng không tồn tại"; // Hoặc bạn có thể trả về chuỗi mặc định khác
    }

    private String findTenKhachById(String sdt) {
        for (khach_hang kh : khachhangList) {
            if (kh.getSo_dien_thoai().equals(sdt)) {
                return kh.getTen();
            }
        }
        return "Tên khách hàng không tồn tại"; // Hoặc bạn có thể trả về chuỗi mặc định khác
    }


    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        TextView tv_mahoadon, tv_ngaynhan, tv_ngaytra, tv_datra, tv_tong, tv_tenphong, tv_tenkhach;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

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

    void khoi_tao() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("hoa_don");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                datalist.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    hoa_don hoaDon = dataSnapshot.getValue(hoa_don.class);
                    if (hoaDon != null) {
                        // Kiểm tra trạng thái của phòng trước khi thêm vào danh sách
                        if (isPhongDangSuDung(hoaDon.getId_phong())) {
                            datalist.add(hoaDon);
                        }
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

    private boolean isPhongDangSuDung(String idPhong) {
        for (phong phong : phongList) {
            if (phong.getId_phong().equals(idPhong) && phong.getId_trang_thai_phong().equals("4")) {
                return true;
            }
        }
        return false;
    }
}