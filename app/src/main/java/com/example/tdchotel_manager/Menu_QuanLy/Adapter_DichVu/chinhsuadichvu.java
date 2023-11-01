package com.example.tdchotel_manager.Menu_QuanLy.Adapter_DichVu;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tdchotel_manager.Model.dich_vu;
import com.example.tdchotel_manager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class chinhsuadichvu extends AppCompatActivity {
    ImageButton imgButtonquaylai;
    TextView tvTenDV;
    EditText edtGiaDV;
    RadioGroup radioGroup;
    RadioButton rdNguoi, rdPhong;
    Button btnXoa, btnSua;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_chinhsuadichvu);
        setControl();
        setEvent();
    }

    private void setEvent() {
        imgButtonquaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // Kết thúc Activity này và quay lại Activity trước đó
            }
        });
        String dichvuid = getIntent().getStringExtra("dichvuid");
        if (dichvuid != null) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("dich_vu").child(dichvuid);
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    dich_vu nv = dataSnapshot.getValue(dich_vu.class);
                    if (nv != null) {
                        tvTenDV.setText(nv.getTen_dich_vu());
                        edtGiaDV.setText(String.valueOf(nv.getGia_dich_vu()));

                        String loaiDichVu = nv.getId_loai_dich_vu();
                        if ("Người".equals(loaiDichVu)) {
                            radioGroup.check(R.id.rdNguoiCS);
                        } else if ("Phòng".equals(loaiDichVu)) {
                            radioGroup.check(R.id.rdPhongCS);
                        }
                    }
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Xử lý khi có lỗi
                    Toast.makeText(chinhsuadichvu.this, "Lỗi: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xoá nhân viên từ Firebase
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("dich_vu").child(dichvuid);
                ref.removeValue();

                // Thông báo và quay lại màn hình trước
                Toast.makeText(chinhsuadichvu.this, "Đã xoá", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy dữ liệu từ các trường
                String giaDV = edtGiaDV.getText().toString();
                if (giaDV.isEmpty()) {
                    Toast.makeText(chinhsuadichvu.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                int giaValue;
                try {
                    giaValue = Integer.parseInt(edtGiaDV.getText().toString().trim());
                } catch (NumberFormatException e) {
                    Toast.makeText(chinhsuadichvu.this, "Lương không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }
                String idChucVu = rdNguoi.isChecked() ? "Người" : "Phòng";

                // Cập nhật dữ liệu trên Firebase
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("dich_vu").child(dichvuid);
                ref.child("gia_dich_vu").setValue(giaValue);
                ref.child("id_loai_dich_vu").setValue(idChucVu);

                // Thông báo và quay lại màn hình trước
                Toast.makeText(chinhsuadichvu.this, "Đã cập nhật thông tin nhân viên", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }


    private void setControl() {
        imgButtonquaylai = findViewById(R.id.imvbBack);
        tvTenDV = findViewById(R.id.tvTenDVcs);
        edtGiaDV = findViewById(R.id.edtGiaDv1);
        radioGroup = findViewById(R.id.rgLoaiDvCS);
        rdNguoi = findViewById(R.id.rdNguoiCS);
        rdPhong = findViewById(R.id.rdPhongCS);
        btnXoa = findViewById(R.id.btnXDV);
        btnSua = findViewById(R.id.btnCSDV);
    }
}
