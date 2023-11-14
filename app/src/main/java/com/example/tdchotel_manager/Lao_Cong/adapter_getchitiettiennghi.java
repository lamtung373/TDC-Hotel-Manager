package com.example.tdchotel_manager.Lao_Cong;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdchotel_manager.Model.chi_tiet_tien_nghi;
import com.example.tdchotel_manager.Model.tien_nghi;
import com.example.tdchotel_manager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class adapter_getchitiettiennghi extends RecyclerView.Adapter<adapter_getchitiettiennghi.MyViewHolder> {
    private ArrayList<tien_nghi> dataList = new ArrayList<>();
    String idphong;

    // Constructor để khởi tạo adapter và gọi phương thức GoiDuLieu để lấy dữ liệu
    public adapter_getchitiettiennghi(String idphong) {
        this.idphong=idphong;
        GoiDuLieu( this.idphong);
    }

    @NonNull
    @Override
    public adapter_getchitiettiennghi.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_dich_vu_phong, parent, false);
        return new adapter_getchitiettiennghi.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_getchitiettiennghi.MyViewHolder holder, int position) {
        tien_nghi cttn = dataList.get(position);
        if (cttn.getSo_luong() > 0) { // Kiểm tra số lượng > 0 trước khi hiển thị
            holder.tv_ten_tien_nghi.setText(cttn.getTen_tien_nghi());
            holder.edt_so_luong.setText(String.valueOf(cttn.getSo_luong()));
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_ten_tien_nghi;
        EditText edt_so_luong;
        ImageButton ib_increase, ib_decrease;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_ten_tien_nghi = itemView.findViewById(R.id.tv_chitiet);
            edt_so_luong = itemView.findViewById(R.id.edt_so_luong);
            ib_increase = itemView.findViewById(R.id.ib_increase);
            ib_decrease = itemView.findViewById(R.id.ib_decrease);
            ib_increase.setVisibility(View.GONE);
            ib_decrease.setVisibility(View.GONE);
        }
    }

    // Phương thức để lấy dữ liệu từ bảng chi_tiet_tien_nghi và cập nhật dataList
    public void GoiDuLieu(String idPhong) {
        DatabaseReference chiTietTienNghiRef = FirebaseDatabase.getInstance().getReference().child("chi_tiet_tien_nghi");
        chiTietTienNghiRef.orderByChild("idPhong").equalTo(idPhong).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<chi_tiet_tien_nghi> dataList = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    chi_tiet_tien_nghi chiTiet = snapshot.getValue(chi_tiet_tien_nghi.class);
                    if (chiTiet != null && chiTiet.getSo_luong() > 0) {
                        dataList.add(chiTiet);
                    }
                }

                // Tiếp theo, bạn có thể sử dụng id của tiện nghi để truy vấn bảng "tien-nghi"
                for (chi_tiet_tien_nghi chiTiet : dataList) {
                    DatabaseReference tienNghiRef = FirebaseDatabase.getInstance().getReference().child("tien-nghi").child(chiTiet.getId_tien_nghi());
                    tienNghiRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            tien_nghi tienNghi = dataSnapshot.getValue(tien_nghi.class);
                            if (tienNghi != null) {
                                int soluong = chiTiet.getSo_luong();
                                // Sử dụng tên của tiện nghi và soluong theo nhu cầu của bạn
                                String tenTienNghi = tienNghi.getTen_tien_nghi();
                                // Gán giá trị soluong cho tiện nghi
                                tienNghi.setSo_luong(soluong);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Xử lý lỗi nếu cần
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu cần
            }
        });

    }
}
