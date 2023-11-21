package com.example.tdchotel_manager.Le_Tan.Adapter_DangSuDung;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdchotel_manager.Model.chi_tiet_hoa_don_dich_vu;
import com.example.tdchotel_manager.Model.dich_vu;
import com.example.tdchotel_manager.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Activity_dichvu_letan extends AppCompatActivity {
    private String idHoaDon;
    private ArrayList<chi_tiet_hoa_don_dich_vu> chiTietHoaDonDichVus = new ArrayList<>();
    private Button btnXacNhanDV;
    private RecyclerView rcv_dvphong, rcv_dvtheonguoi;
    private adapter_dvnguoi adapterDvTheoNguoi;
    private adapter_dvphong adapterDvTheoPhong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_themdichvu_le_tan);

        Intent intent = getIntent();
        idHoaDon = intent.getStringExtra("idHoaDon");

        setControl();
        setEvent();

    }


    private void setEvent() {
        btnXacNhanDV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    chiTietHoaDonDichVus.clear();

                    // Lấy danh sách dịch vụ theo người đã chọn
                    ArrayList<dich_vu> dvTheoNguoi = adapterDvTheoNguoi.getData_dv();
                    for (dich_vu dv : dvTheoNguoi) {
                        if (dv.getSo_luong() > 0) {
                            // Tạo một đối tượng chi tiết hóa đơn dịch vụ
                            chi_tiet_hoa_don_dich_vu chiTietDV = new chi_tiet_hoa_don_dich_vu(dv.getSo_luong(),idHoaDon, dv.getId_dich_vu());
                            chiTietHoaDonDichVus.add(chiTietDV);

                            // Lưu chi tiết hóa đơn dịch vụ vào Firebase
                            DatabaseReference reference_chi_tiet_hoa_don_dich_vu = FirebaseDatabase.getInstance().getReference("chi_tiet_hoa_don_dich_vu")
                                    .child(idHoaDon)
                                    .child(dv.getId_dich_vu());
                            reference_chi_tiet_hoa_don_dich_vu.setValue(chiTietDV);
                        }
                    }

                    // Lấy danh sách dịch vụ theo phòng đã chọn
                    ArrayList<dich_vu> dvTheoPhong = adapterDvTheoPhong.getData_dv();
                    for (dich_vu dv : dvTheoPhong) {
                        if (dv.getSo_luong() > 0) {
                            // Tạo một đối tượng chi tiết hóa đơn dịch vụ
                            chi_tiet_hoa_don_dich_vu chiTietDV = new chi_tiet_hoa_don_dich_vu(dv.getSo_luong(),idHoaDon, dv.getId_dich_vu());
                            chiTietHoaDonDichVus.add(chiTietDV);

                            // Lưu chi tiết hóa đơn dịch vụ vào Firebase
                            DatabaseReference reference_chi_tiet_hoa_don_dich_vu = FirebaseDatabase.getInstance().getReference("chi_tiet_hoa_don_dich_vu")
                                    .child(idHoaDon)
                                    .child(dv.getId_dich_vu());
                            reference_chi_tiet_hoa_don_dich_vu.setValue(chiTietDV);
                        }
                    }

                    // Kiểm tra nếu không có dịch vụ nào được chọn thì thông báo
                    if (chiTietHoaDonDichVus.isEmpty()) {
                        Toast.makeText(Activity_dichvu_letan.this, "Vui lòng chọn ít nhất một dịch vụ", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Toast.makeText(Activity_dichvu_letan.this, "Đã lưu dữ liệu thành công", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Log.e("Lỗi chuyển đổi dữ liệu số lượng khách", e.getMessage());
                }
            }
        });


        rcv_dvtheonguoi.setLayoutManager(new LinearLayoutManager(Activity_dichvu_letan.this));
        rcv_dvtheonguoi.addItemDecoration(new DividerItemDecoration(Activity_dichvu_letan.this, DividerItemDecoration.VERTICAL));
        rcv_dvtheonguoi.setAdapter(adapterDvTheoNguoi);

        rcv_dvphong.setLayoutManager(new LinearLayoutManager(Activity_dichvu_letan.this));
        rcv_dvphong.addItemDecoration(new DividerItemDecoration(Activity_dichvu_letan.this, DividerItemDecoration.VERTICAL));
        rcv_dvphong.setAdapter(adapterDvTheoPhong);
        adapterDvTheoNguoi.setIdHoaDon(idHoaDon);
        adapterDvTheoPhong.setIdHoaDon(idHoaDon);

    }


    private void setControl() {
        btnXacNhanDV = findViewById(R.id.btnXacNhanDV);
        rcv_dvphong = findViewById(R.id.rcv_dvphong);
        rcv_dvtheonguoi = findViewById(R.id.rcv_dvtheonguoi);

        adapterDvTheoNguoi = new adapter_dvnguoi();
        adapterDvTheoPhong = new adapter_dvphong();
    }
}
