package com.example.tdchotel_manager.Menu_QuanLy.Adapter_Phong;

import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdchotel_manager.Menu_QuanLy.Activity_Thong_Tin_Phong;
import com.example.tdchotel_manager.Model.chi_tiet_dich_vu_phong;
import com.example.tdchotel_manager.Model.chi_tiet_tien_nghi;
import com.example.tdchotel_manager.Model.tien_nghi;
import com.example.tdchotel_manager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class adapter_tien_nghi extends RecyclerView.Adapter<adapter_tien_nghi.MyViewHolder> {
    private ArrayList<tien_nghi> dataList = new ArrayList<>();
    ArrayList<chi_tiet_tien_nghi> chi_tiet_tien_nghis = new ArrayList<>();

    public void addChiTietTienNghi(String tien_nghiID) {
        for (int i = 0; i < chi_tiet_tien_nghis.size(); i++) {
            if (tien_nghiID.equals(chi_tiet_tien_nghis.get(i).getId_tien_nghi())) {
                break;
            }
        }
        // Nếu không tìm thấy, thêm đối tượng mới vào danh sách
        chi_tiet_tien_nghi cttn = new chi_tiet_tien_nghi();
        cttn.setId_tien_nghi(tien_nghiID);
        cttn.setSo_luong(1);
        chi_tiet_tien_nghis.add(cttn);
        notifyDataSetChanged();
        for (chi_tiet_tien_nghi tn : chi_tiet_tien_nghis) {
            Log.e("Nội dung thông báo", tn.getId_tien_nghi());
        }

    }


    public ArrayList<chi_tiet_tien_nghi> getChi_tiet_tien_nghis() {

        return chi_tiet_tien_nghis;

    }

    public adapter_tien_nghi() {
        khoi_tao();
    }

    @Override
    public adapter_tien_nghi.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_tien_nghi, parent, false);
        return new adapter_tien_nghi.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(adapter_tien_nghi.MyViewHolder holder, int position) {
        tien_nghi data = dataList.get(position);
        holder.tv_tien_nghi.setText(data.getTen_tien_nghi());

        // Detach the listener before setting the check status
        holder.cb_tien_nghi.setOnCheckedChangeListener(null);

        boolean isChecked = isTienNghiSelected(data.getId_tien_nghi());
        holder.cb_tien_nghi.setChecked(isChecked);

        // Reattach the listener after setting the check status
        holder.cb_tien_nghi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    addChiTietTienNghi(data.getId_tien_nghi());
                    Toast.makeText(buttonView.getContext(), "ID đã chọn: " + data.getId_tien_nghi(), Toast.LENGTH_SHORT).show(); // Toast ID đã chọn
                } else {
                    removeChiTietTienNghi(data.getId_tien_nghi());
                }
            }
        });
    }

    // Hàm kiểm tra xem một tiện nghi có được chọn hay không
    private boolean isTienNghiSelected(String id_tien_nghi) {
        for (chi_tiet_tien_nghi cttn : chi_tiet_tien_nghis) {
            if (cttn.getId_tien_nghi().equals(id_tien_nghi)) {
                return true;
            }
        }
        return false;
    }


    public void removeChiTietTienNghi(String tien_nghiID) {
        for (int i = 0; i < chi_tiet_tien_nghis.size(); i++) {
            if (tien_nghiID.equals(chi_tiet_tien_nghis.get(i).getId_tien_nghi())) {
                chi_tiet_tien_nghis.remove(i);
                notifyDataSetChanged();
                return;
            }
        }
    }


    public ArrayList<chi_tiet_tien_nghi> getChiTietTienNghi() {
        return chi_tiet_tien_nghis;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox cb_tien_nghi;
        TextView tv_tien_nghi;

        public MyViewHolder(View itemView) {
            super(itemView);
            cb_tien_nghi = itemView.findViewById(R.id.cb_tien_nghi);
            tv_tien_nghi = itemView.findViewById(R.id.tv_tien_nghi);
        }
    }

    void khoi_tao() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("tien_nghi");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear(); // Xóa dữ liệu cũ trước khi thêm dữ liệu mới để tránh trùng lặp

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    tien_nghi tienNghi = dataSnapshot.getValue(tien_nghi.class);
                    if (tienNghi != null) {
                        dataList.add(tienNghi);
                    }
                }

                notifyDataSetChanged(); // Cập nhật RecyclerView khi dữ liệu thay đổi
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi nếu có
            }
        });
    }

}

