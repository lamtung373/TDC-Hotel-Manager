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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_gia_han_thoi_gian);
        setControl();
        Initialization();
        setEvent();
    }

    private void setEvent() {
        btnXacNhanDV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Date now = new Date();
                    thoi_gian_nhan = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(btnTGdukien.getText().toString());
                    thoi_gian_tra = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(btnTGketthuc.getText().toString());

                    // Kiểm tra thời gian nếu hợp lệ
                    if (thoi_gian_nhan.before(now) || thoi_gian_nhan.after(thoi_gian_tra)) {
                        Toast.makeText(giahanthoigian.this, "Thời gian đã đặt không hợp lệ!!!", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (((thoi_gian_tra.getTime() - thoi_gian_nhan.getTime()) / (24 * 3600 * 1000)) < 1) {
                        Toast.makeText(giahanthoigian.this, "Thời gian ở phải ít nhất 1 ngày!!!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Cập nhật thời gian đã chọn lên Firebase
                    DatabaseReference hoaDonRef = FirebaseDatabase.getInstance().getReference("hoa_don");
                    String hoaDonId = "1";  // Đặt ID hoa_don tương ứng
                    hoaDonRef.child(hoaDonId).child("thoi_gian_nhan_phong").setValue(btnTGdukien.getText().toString());
                    hoaDonRef.child(hoaDonId).child("thoi_gian_tra_phong").setValue(btnTGketthuc.getText().toString());

                    // Các kiểm tra khác ở đây...
                } catch (Exception e) {
                    Log.e("Lỗi chuyển đổi dữ liệu thời gian đã đặt", e.getMessage());
                }
            }
        });
    }

    private void Initialization() {
        ChonThoiGian(btnTGdukien);
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
                        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calendar.set(Calendar.MINUTE, minute);
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                btnThoigian.setText(dateFormat.format(calendar.getTime()));
                            }
                        };
                        new TimePickerDialog(giahanthoigian.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
                    }
                };
                new DatePickerDialog(giahanthoigian.this, dataListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void setControl() {
        btnTGdukien = findViewById(R.id.btnTGdukien);
        btnTGketthuc = findViewById(R.id.btnTGketthuc);
        btnXacNhanDV = findViewById(R.id.btnXacNhan);
    }
}