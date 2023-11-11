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
    Button btnNgayNhan, btnNgayTra, btnXacNhanDV;
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
        DatabaseReference reference_hoadon = FirebaseDatabase.getInstance().getReference("hoa_don");
        btnXacNhanDV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Date now = new Date();
                    thoi_gian_nhan = new SimpleDateFormat("dd/MM/yyyy").parse(btnNgayNhan.getText().toString());
                    thoi_gian_tra = new SimpleDateFormat("dd/MM/yyyy").parse(btnNgayTra.getText().toString());
                    if (thoi_gian_nhan.before(now) || thoi_gian_nhan.after(thoi_gian_tra)) {
                        Toast.makeText(giahanthoigian.this, "Thời gian đã đặt không hợp lệ!!!", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (((thoi_gian_tra.getTime() - thoi_gian_nhan.getTime()) / (24 * 3600 * 1000)) < 1) {
                        Toast.makeText(giahanthoigian.this, "Thời gian ở phải ít nhất 1 ngày!!!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Các kiểm tra khác ở đây...

                    Intent intent = new Intent(giahanthoigian.this, Activity_XacNhanDatPhongDichVu.class);
                    intent.putExtra("thoi_gian_nhan", btnNgayNhan.getText().toString());
                    intent.putExtra("thoi_gian_tra", btnNgayTra.getText().toString());
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e("Lỗi chuyển đổi dữ liệu thời gian đã đặt", e.getMessage());
                }
            }
        });

    }

    private void Initialization() {
        ChonThoiGian(btnNgayNhan);
        ChonThoiGian(btnNgayTra);
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
                new DatePickerDialog(giahanthoigian.this, dataListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void setControl() {
        btnNgayNhan = findViewById(R.id.btnTGdukien);
        btnNgayTra = findViewById(R.id.btnTGketthuc);
        btnXacNhanDV = findViewById(R.id.btnXacNhan);
    }
}