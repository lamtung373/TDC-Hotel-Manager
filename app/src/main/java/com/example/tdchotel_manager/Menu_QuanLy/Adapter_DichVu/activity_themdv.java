package com.example.tdchotel_manager.Menu_QuanLy.Adapter_DichVu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tdchotel_manager.Menu_QuanLy.Fragment_Dichvu;
import com.example.tdchotel_manager.Model.dich_vu;
import com.example.tdchotel_manager.Model.dich_vu_phong;
import com.example.tdchotel_manager.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class activity_themdv extends AppCompatActivity {
    EditText edtTenDv, edtGiaDv;
    Button btnLuu;
    RadioGroup loaiDichVu;
    RadioButton rdNguoi, rdPhong;
    ImageButton imgButtonquaylai;

    ImageView imageView;

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
                    Toast.makeText(activity_themdv.this, "Add success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity_themdv.this, "Add failed", Toast.LENGTH_SHORT).show();
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