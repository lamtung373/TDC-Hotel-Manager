package com.example.tdchotel_manager.Menu_QuanLy.Adapter_DichVu;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tdchotel_manager.Model.dich_vu_phong;
import com.example.tdchotel_manager.Model.tien_nghi;
import com.example.tdchotel_manager.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class themtiennghi extends AppCompatActivity {
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
                int gia = Integer.parseInt(edtGiaDv.getText().toString().trim());
                String idDichVu = null;
                String ten = edtTenDv.getText().toString().trim();
                String anh = (String) imageView.getTag();
                tien_nghi dv = new tien_nghi(idDichVu,gia,ten,anh);
                onClickAdd(dv);
            }
        });
    }


    private void onClickAdd(tien_nghi tiennghi) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("tien_nghi");
        DatabaseReference newDichVuRef = databaseReference.push(); // Tạo một khóa con mới
        newDichVuRef.setValue(tiennghi, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                String generatedId = newDichVuRef.getKey(); // Lấy khóa con duy nhất đã tạo
                if (generatedId != null) {
                    tiennghi.setId_tien_nghi(generatedId); // Gán khóa con duy nhất làm id_dich_vu cho dichvu
                    newDichVuRef.setValue(tiennghi); // Cập nhật lại dữ liệu với id_dich_vu mới
                    Toast.makeText(themtiennghi.this, "Add success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(themtiennghi.this, "Add failed", Toast.LENGTH_SHORT).show();
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
