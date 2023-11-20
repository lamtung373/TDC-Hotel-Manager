package com.example.tdchotel_manager.Le_Tan.Adapter_DangSuDung;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdchotel_manager.Le_Tan.Activity_XacNhanDatPhongDichVu;
import com.example.tdchotel_manager.Le_Tan.Adapter.Adapter_DVTheoNguoi;
import com.example.tdchotel_manager.Le_Tan.Adapter.Adapter_DVTheoPhong;
import com.example.tdchotel_manager.Model.chi_tiet_hoa_don_dich_vu;
import com.example.tdchotel_manager.Model.chi_tiet_hoa_don_dich_vu_phong;
import com.example.tdchotel_manager.Model.dich_vu;
import com.example.tdchotel_manager.Model.hoa_don;
import com.example.tdchotel_manager.Model.phong;
import com.example.tdchotel_manager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class dichvu_letan extends AppCompatActivity {
    ArrayList<dich_vu> data_dvphong = new ArrayList<>();
    ArrayList<dich_vu> data_dvtheonguoi = new ArrayList<>();
String idHoaDon;
    ArrayList<chi_tiet_hoa_don_dich_vu> chiTietHoaDonDichVus = new ArrayList<>();
    Button btnXacNhanDV;
    RecyclerView rcv_dvphong, rcv_dvtheonguoi;
    adapter_dvnguoi adapterDvTheoNguoi = new adapter_dvnguoi();
    adapter_dvphong adapterDvTheoPhong = new adapter_dvphong();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_themdichvu_le_tan);
        Intent intent = getIntent();
        idHoaDon = intent.getStringExtra("idHoaDon");
//        phong = (phong) getIntent().getSerializableExtra("phong");
        setControl();
        setEvent();
        if (idHoaDon!=null){
            fill_data(idHoaDon);
            Toast.makeText(this,idHoaDon,Toast.LENGTH_SHORT).show();
        }


    }
    void fill_data(String idHoaDon){
        adapterDvTheoNguoi.GoiDuLieu(idHoaDon);
    }

    private void setEvent() {
        DatabaseReference reference_hoadon = FirebaseDatabase.getInstance().getReference("hoa_don");
        btnXacNhanDV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // Lấy danh sách dịch vụ theo người đã chọn
                    ArrayList<dich_vu> dvTheoNguoi = adapterDvTheoNguoi.getData_dv();

                    // Lấy danh sách dịch vụ theo phòng đã chọn
                    ArrayList<dich_vu> dvTheoPhong = adapterDvTheoPhong.getData_dv();

//                    // Kiểm tra nếu không có dịch vụ nào được chọn thì thông báo
//                    if (dvTheoNguoi.isEmpty() && dvTheoPhong.isEmpty()) {
//                        Toast.makeText(dichvu_letan.this, "Vui lòng chọn ít nhất một dịch vụ", Toast.LENGTH_SHORT).show();
//                        return;
//                    }

                } catch (Exception e) {
                    Log.e("Lỗi chuyển đổi dữ liệu số lượng khách", e.getMessage());
                }
            }
        });



        rcv_dvtheonguoi.setLayoutManager(new LinearLayoutManager(dichvu_letan.this, LinearLayoutManager.VERTICAL, false));
        rcv_dvtheonguoi.addItemDecoration(new DividerItemDecoration(dichvu_letan.this, DividerItemDecoration.VERTICAL));
        rcv_dvtheonguoi.setAdapter(adapterDvTheoNguoi);

        rcv_dvphong.setLayoutManager(new LinearLayoutManager(dichvu_letan.this, LinearLayoutManager.VERTICAL, false));
        rcv_dvphong.addItemDecoration(new DividerItemDecoration(dichvu_letan.this, DividerItemDecoration.VERTICAL));
        rcv_dvphong.setAdapter(adapterDvTheoPhong);
    }


    private void setControl() {
        btnXacNhanDV = findViewById(R.id.btnXacNhanDV);
        rcv_dvphong = findViewById(R.id.rcv_dvphong);
        rcv_dvtheonguoi = findViewById(R.id.rcv_dvtheonguoi);
    }
}