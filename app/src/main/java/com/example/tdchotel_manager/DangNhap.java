package com.example.tdchotel_manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
    public static String chuc_vu_auto = "chuc_vu_auto";
    ProgressBar progressBar;

    EditText edtusername, edtPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        AutoLogin();
        setControl();
        setEvent();

        khoi_tao();
    }

    private void AutoLogin() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PRE, MODE_PRIVATE);
        String id_staff_auto = sharedPreferences.getString("id_staff", "");
        String chuc_vu_auto = sharedPreferences.getString("chuc_vu_auto", "");
        switch (chuc_vu_auto.toLowerCase()) {
            case "lao công":
                break;
            case "lễ tân":
                Intent intent_letan = new Intent(DangNhap.this, Activity_LeTan.class);
                intent_letan.putExtra("id_staff", id_staff_auto);
                startActivity(intent_letan);
                finish();
                break;
            case "quản lý":
                Intent intent = new Intent(DangNhap.this, TrangChu_QuanLy.class);
                intent.putExtra("id_staff", id_staff_auto);
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
        progressBar=findViewById(R.id.progressBar_phong);
    }

    private void setEvent() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("nhan_vien");
                DatabaseReference reference_cv = FirebaseDatabase.getInstance().getReference("chuc_vu");
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PRE, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        progressBar.setVisibility(View.VISIBLE);
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            nhan_vien nhan_vien = dataSnapshot.getValue(nhan_vien.class);
                            if (edtusername.getText().toString().equals(nhan_vien.getUsername())) {
                                if (edtPassword.getText().toString().equals(nhan_vien.getPassword())) {
                                    reference_cv.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                                chuc_vu cv = dataSnapshot.getValue(chuc_vu.class);

                                                if (nhan_vien.getId_chuc_vu().equals(cv.getId_chuc_vu())) {
                                                    editor.putString(id_staff, nhan_vien.getId_nhan_vien());
                                                    editor.putString(chuc_vu_auto, cv.getTen_chuc_vu());
                                                    editor.apply();
                                                    switch (cv.getTen_chuc_vu().toLowerCase()) {
                                                        case "lao công":
                                                            Intent intent_laocong = new Intent(DangNhap.this, Activity_LaoCong.class);
                                                            startActivity(intent_laocong);
                                                            progressBar.setVisibility(View.GONE);
                                                            finish();
                                                            break;
                                                        case "lễ tân":
                                                            Intent intent_letan = new Intent(DangNhap.this, Activity_LeTan.class);
                                                            startActivity(intent_letan);
                                                            progressBar.setVisibility(View.GONE);
                                                            finish();
                                                            break;
                                                        case "quản lý":
                                                            Intent intent = new Intent(DangNhap.this, TrangChu_QuanLy.class);
                                                            startActivity(intent);
                                                            progressBar.setVisibility(View.GONE);
                                                            finish();
                                                            break;
                                                    }
                                                }
                                            }


                                            // Create an Intent to pass data to Fragment_Trangchu

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                }
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Xử lý lỗi nếu có
                    }
                });

            }
        });
    }

    void khoi_tao() {

    }

}