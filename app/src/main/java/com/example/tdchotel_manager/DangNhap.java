package com.example.tdchotel_manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tdchotel_manager.Menu_QuanLy.Fragment_Trangchu;
import com.example.tdchotel_manager.Model.nhan_vien;
import com.example.tdchotel_manager.Model.tien_nghi;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DangNhap extends AppCompatActivity {
    EditText edtusername, edtPassword;
    Button btnLogin;
    ArrayList<nhan_vien> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        setControl();
        setEvent();
        khoi_tao();
    }

    private void setControl() {
        btnLogin = findViewById(R.id.btnLogin);
        edtusername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
    }

    private void setEvent() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isLoginSuccessful = false;

                for (nhan_vien nv : dataList) {
                    if (edtusername.getText().toString().equals(nv.getUsername())) {
                        if (edtPassword.getText().toString().equals(nv.getPassword())) {
                            // Đăng nhập thành công
                            isLoginSuccessful = true;

                            // Create an Intent to pass data to Fragment_Trangchu
                            Intent intent = new Intent(DangNhap.this, Fragment_Trangchu.class);
//                            intent.putExtra("username", nv.getUsername());
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(DangNhap.this, "Sai mật khẩu", Toast.LENGTH_SHORT).show();
                        }

                    }

                }

                if (isLoginSuccessful) {
                    // Hiển thị Toast thông báo đăng nhập thành công
                    Toast.makeText(DangNhap.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                } else {
                    // Hiển thị Toast thông báo đăng nhập không thành công
                    Toast.makeText(DangNhap.this, "Không có thông tin của bạn", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void khoi_tao() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("nhan_vien");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear(); // Xóa dữ liệu cũ trước khi thêm dữ liệu mới để tránh trùng lặp

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    nhan_vien tienNghi = dataSnapshot.getValue(nhan_vien.class);
                    if (tienNghi != null) {
                        dataList.add(tienNghi);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi nếu có
            }
        });
    }

}