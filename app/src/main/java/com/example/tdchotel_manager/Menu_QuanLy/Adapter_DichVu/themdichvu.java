package com.example.tdchotel_manager.Menu_QuanLy.Adapter_DichVu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.tdchotel_manager.Model.dich_vu;
import com.example.tdchotel_manager.Model.dich_vu_phong;
import com.example.tdchotel_manager.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class themdichvu extends AppCompatActivity {
    EditText edtTenDv, edtGiaDv;
    Button btnLuu;
    RadioGroup loaiDichVu;
    RadioButton rdNguoi, rdPhong;
    ImageButton imgButtonquaylai;

    ImageView imageView;
    private TabLayout tabLayout;
    private int tabDichVu;
    private int tabDichVuPhong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_themdichvu);
        setControl();
        setEvent();



    }

    private void setEvent() {
        imgButtonquaylai = findViewById(R.id.imvbQuaylai);
        imgButtonquaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loaiDichVu = "";
                if (rdNguoi.isChecked()) {
                    loaiDichVu = "Người"; // RadioButton "Người"
                } else if (rdPhong.isChecked()) {
                    loaiDichVu = "Phòng"; // RadioButton "Phòng"
                }
                int gia = Integer.parseInt(edtGiaDv.getText().toString().trim());
                String idDichVu = null;
                String ten = edtTenDv.getText().toString().trim();
                String anh = (String) imageView.getTag();

                dich_vu dv = new dich_vu(loaiDichVu, gia, idDichVu, ten, anh);
                onClickAdd(dv);
            }
        });
    }


    private void onClickAdd(dich_vu dichvu) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("dich_vu");
        DatabaseReference newDichVuRef = databaseReference.push(); // Tạo một khóa con mới
        newDichVuRef.setValue(dichvu, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                String generatedId = newDichVuRef.getKey(); // Lấy khóa con duy nhất đã tạo
                if (generatedId != null) {
                    dichvu.setId_dich_vu(generatedId); // Gán khóa con duy nhất làm id_dich_vu cho dichvu
                    newDichVuRef.setValue(dichvu); // Cập nhật lại dữ liệu với id_dich_vu mới
                    Toast.makeText(themdichvu.this, "Add success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(themdichvu.this, "Add failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void onClickAdd1(dich_vu_phong dichvup) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("dich_vu_phong");
        DatabaseReference newDichVuRef = databaseReference.push(); // Tạo một khóa con mới
        newDichVuRef.setValue(dichvup, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                String generatedId = newDichVuRef.getKey(); // Lấy khóa con duy nhất đã tạo
                if (generatedId != null) {
                    dichvup.setId_dich_vu_phong(generatedId); // Gán khóa con duy nhất làm id_dich_vu cho dichvu
                    newDichVuRef.setValue(dichvup); // Cập nhật lại dữ liệu với id_dich_vu mới
                    Toast.makeText(themdichvu.this, "Add success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(themdichvu.this, "Add failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setControl() {
        edtTenDv = findViewById(R.id.edtTenDv);
        edtGiaDv = findViewById(R.id.edtGiaDv);
        btnLuu = findViewById(R.id.btnLuu);
        loaiDichVu = findViewById(R.id.rdgLoaiphong);
        rdNguoi = findViewById(R.id.rdNguoi);
        rdPhong = findViewById(R.id.rdPhong);
        imageView = findViewById(R.id.imgthemDV);
    }
}