package com.example.tdchotel_manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.tdchotel_manager.Lao_Cong.Activity_LaoCong;
import com.example.tdchotel_manager.Le_Tan.Activity_LeTan;
import com.example.tdchotel_manager.Menu_QuanLy.TrangChu_QuanLy;
import com.example.tdchotel_manager.Model.chuc_vu;
import com.example.tdchotel_manager.Model.nhan_vien;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DangNhap extends AppCompatActivity {
    public static String SHARED_PRE = "shared_pre";
    public static String id_staff = "id_staff";
    public static String name_staff = "name_staff";
    public static String chuc_vu_auto = "chuc_vu_auto";
    ProgressBar progressBar;

    EditText edtusername, edtPassword;
    Button btnLogin;
    private static final int REQUEST_STORAGE_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        // Kiểm tra xem quyền đã được cấp chưa
        checkStoragePermission();
        AutoLogin();
        setControl();
        setEvent();
    }

    private void checkStoragePermission() {
        // Kiểm tra quyền truy cập camera
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Nếu một trong hai quyền chưa được cấp, yêu cầu cấp quyền
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSION);
        }
    }

    private void AutoLogin() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PRE, MODE_PRIVATE);
        String id_staff_auto = sharedPreferences.getString("id_staff", "");
        String name_staff_staff_auto = sharedPreferences.getString("name_staff", "");
        String chuc_vu_auto = sharedPreferences.getString("chuc_vu_auto", "");
        switch (chuc_vu_auto.toLowerCase()) {
            case "lao công":
                Intent intent_laocong = new Intent(DangNhap.this, Activity_LaoCong.class);
//                intent_laocong.putExtra("id_staff", id_staff_auto);
                startActivity(intent_laocong);
                finish();
                break;
            case "lễ tân":
                Intent intent_letan = new Intent(DangNhap.this, Activity_LeTan.class);
//                intent_letan.putExtra("id_staff", id_staff_auto);
                startActivity(intent_letan);
                finish();
                break;
            case "quản lý":
                Intent intent = new Intent(DangNhap.this, TrangChu_QuanLy.class);
//                intent.putExtra("id_staff", id_staff_auto);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }

    private void setControl() {
        btnLogin = findViewById(R.id.btnLogin);
        edtusername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        progressBar = findViewById(R.id.progressBar_login);
    }

    private void loginUser(String enteredUsername, String enteredPassword) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("nhan_vien");
        DatabaseReference reference_cv = FirebaseDatabase.getInstance().getReference("chuc_vu");
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PRE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean userExists = false;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    nhan_vien nhanVien = dataSnapshot.getValue(nhan_vien.class);
                    if (nhanVien != null && enteredUsername.equals(nhanVien.getUsername())) {
                        userExists = true;
                        if (enteredPassword.equals(nhanVien.getPassword())) {
                            // Khi người dùng và mật khẩu đúng
                            checkUserRole(nhanVien, editor);
                        } else {
                            edtPassword.setError("Mật khẩu không đúng");
                            progressBar.setVisibility(View.GONE);
                            btnLogin.setVisibility(View.VISIBLE);
                        }
                        break;
                    }
                }
                if (!userExists) {
                    edtusername.setError("Người dùng không tồn tại");
                    progressBar.setVisibility(View.GONE);
                    btnLogin.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi nếu có
            }
        });
    }

    private void checkUserRole(nhan_vien nhanVien, SharedPreferences.Editor editor) {
        DatabaseReference reference_cv = FirebaseDatabase.getInstance().getReference("chuc_vu");
        reference_cv.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    chuc_vu cv = dataSnapshot.getValue(chuc_vu.class);
                    if (cv != null && nhanVien.getId_chuc_vu().equals(cv.getId_chuc_vu())) {
                        editor.putString(id_staff, nhanVien.getId_nhan_vien());
                        editor.putString(chuc_vu_auto, cv.getTen_chuc_vu());
                        editor.putString(name_staff, nhanVien.getTen_nhan_vien());
                        editor.apply();

                        switch (cv.getTen_chuc_vu().toLowerCase()) {
                            case "lao công":
                                startActivityWithIntent(Activity_LaoCong.class);
                                return;
                            case "lễ tân":
                                startActivityWithIntent(Activity_LeTan.class);
                                return;
                            case "quản lý":
                                startActivityWithIntent(TrangChu_QuanLy.class);
                                return;
                        }
                    }
                }
                progressBar.setVisibility(View.GONE);
                btnLogin.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi nếu có
            }
        });
    }


    private void startActivityWithIntent(Class<?> cls) {
        Intent intent = new Intent(DangNhap.this, cls);
        progressBar.setVisibility(View.GONE);
        btnLogin.setVisibility(View.VISIBLE);
        startActivity(intent);
        finish();
    }

    private void setEvent() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredUsername = edtusername.getText().toString().trim();
                String enteredPassword = edtPassword.getText().toString().trim();

                if (TextUtils.isEmpty(enteredUsername) || TextUtils.isEmpty(enteredPassword)) {
                    if (TextUtils.isEmpty(enteredUsername)) {
                        edtusername.setError("Tên đăng nhập không được để trống");
                    }
                    if (TextUtils.isEmpty(enteredPassword)) {
                        edtPassword.setError("Mật khẩu không được để trống");
                    }
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                btnLogin.setVisibility(View.GONE);
                loginUser(enteredUsername, enteredPassword);
            }
        });
    }


}