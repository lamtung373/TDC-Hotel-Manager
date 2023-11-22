package com.example.tdchotel_manager.Le_Tan.Adapter_DangSuDung;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.tdchotel_manager.Le_Tan.Activity_XacNhanDatPhongDichVu;
import com.example.tdchotel_manager.Le_Tan.Adapter.Adapter_DVTheoNguoi;
import com.example.tdchotel_manager.Le_Tan.Adapter.Adapter_DVTheoPhong;
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

import me.relex.circleindicator.CircleIndicator3;

public class giahanthoigian extends AppCompatActivity {
    Button btnTGdukien, btnTGketthuc, btnXacNhanDV;
    Date thoi_gian_nhan = null;
    Date thoi_gian_tra = null;
    ImageButton imgButtonquaylai;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_gia_han_thoi_gian);
        setControl();
        Initialization();
        setEvent();
    }
    private void updateThoiGianNhanTraPhong(String idPhong, String idHoaDon, String thoiGianNhan, String thoiGianTra) {
        DatabaseReference hoaDonRef = FirebaseDatabase.getInstance().getReference("hoa_don").child(idPhong).child(idHoaDon);
        hoaDonRef.child("thoi_gian_tra_phong").setValue(thoiGianTra);
    }


    private void setEvent() {
        imgButtonquaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnXacNhanDV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Date now = new Date();
                    thoi_gian_nhan = new SimpleDateFormat("dd/MM/yyyy").parse(btnTGdukien.getText().toString());
                    thoi_gian_tra = new SimpleDateFormat("dd/MM/yyyy").parse(btnTGketthuc.getText().toString());

                    // Kiểm tra thời gian nếu hợp lệ
                    if (thoi_gian_nhan.before(now) || thoi_gian_nhan.after(thoi_gian_tra)) {
                        Toast.makeText(giahanthoigian.this, "Thời gian đã đặt không hợp lệ!!!", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (((thoi_gian_nhan.getTime() - now.getTime()) / (24 * 3600 * 1000)) < 1) {
                        Toast.makeText(giahanthoigian.this, "Ngày gia hạn phải lớn hơn ngày trả hiện tại ít nhất 1 ngày!!!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Intent intent = getIntent();
                    if (intent != null) {
                        String idPhong = intent.getStringExtra("idPhong");
                        String idHoaDon = intent.getStringExtra("idHoaDon");
                        updateThoiGianNhanTraPhong(idPhong, idHoaDon, new SimpleDateFormat("dd/MM/yyyy").format(thoi_gian_nhan), new SimpleDateFormat("dd/MM/yyyy").format(thoi_gian_tra));
                    }

                    Intent resultIntent = new Intent();
                    setResult(RESULT_OK, resultIntent);
                    finish();
                } catch (Exception e) {
                    Log.e("Lỗi chuyển đổi dữ liệu thời gian đã đặt", e.getMessage());
                }
            }
        });
    }
    private void Initialization() {
        // Assuming that idPhong and idHoaDon are available
        Intent intent = getIntent();
        if (intent != null) {
            String idPhong = intent.getStringExtra("idPhong");
            String idHoaDon = intent.getStringExtra("idHoaDon");

            // Retrieve the current return time from Firebase
            DatabaseReference hoaDonRef = FirebaseDatabase.getInstance().getReference("hoa_don").child(idPhong).child(idHoaDon);
            hoaDonRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        hoa_don hoaDon = snapshot.getValue(hoa_don.class);
                        if (hoaDon != null) {
                            String thoiGianTra = hoaDon.getThoi_gian_tra_phong();
                            if (thoiGianTra != null && !thoiGianTra.isEmpty()) {
                                try {
                                    Date returnTime = new SimpleDateFormat("dd/MM/yyyy").parse(thoiGianTra);
                                    // Update the button text with the current return time
                                    btnTGdukien.setText(new SimpleDateFormat("dd/MM/yyyy").format(returnTime));
                                } catch (Exception e) {
                                    Log.e("Lỗi chuyển đổi dữ liệu thời gian trả phòng từ Firebase", e.getMessage());
                                }
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle errors if needed
                }
            });
        }
        ChonThoiGian(btnTGketthuc);
    }


    void ChonThoiGian(Button btnThoigian) {
        btnThoigian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog.OnDateSetListener dataListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        btnThoigian.setText(dateFormat.format(calendar.getTime()));
                    }
                };
                // Bỏ đi TimePickerDialog để chỉ chọn ngày, tháng, và năm
                new DatePickerDialog(giahanthoigian.this, dataListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void setControl() {
        imgButtonquaylai = findViewById(R.id.imvbQuaylai);
        btnTGdukien = findViewById(R.id.btnTGdukien);
        btnTGketthuc = findViewById(R.id.btnTGketthuc);
        btnXacNhanDV = findViewById(R.id.btnXacNhan);
    }
}