package com.example.tdchotel_manager.Le_Tan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.tdchotel_manager.DangNhap;
import com.example.tdchotel_manager.Model.chuc_vu;
import com.example.tdchotel_manager.Model.nhan_vien;
import com.example.tdchotel_manager.Model.phan_cong;
import com.example.tdchotel_manager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Activity_Xem_Thong_Tin_Le_Tan extends AppCompatActivity {

    ImageButton btnQuayLai;
    Button btnDangXuat;
    RadioGroup radioGroup_TTNV;

    ImageView imgNV ,imgCCCD_Truoc_TTNV, imgCCCD_Sau_TTNV;
    EditText edtHoVaTen, edt_CCCD, edtTenDangNhap, edtMail, edtSDT, edtLuong;
    RadioButton radioButtonLeTan_TTNV, radioButtonLaoCong_TTNV;
    CheckBox cbSangT2, cbChieuT2, cbSangT3, cbChieuT3, cbSangT4, cbChieuT4, cbSangT5, cbChieuT5, cbSangT6, cbChieuT6, cbSangT7, cbChieuT7, cbSangCN, cbChieuCN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_thong_tin_le_tan);
        setControl();
        setEvent();
    }

    private void setEvent() {
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(Activity_Xem_Thong_Tin_Le_Tan.this)
                        .setTitle("Đăng xuất")
                        .setMessage("Bạn có chắc chắn muốn đăng xuất?")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences sharedPreferences = Activity_Xem_Thong_Tin_Le_Tan.this.getSharedPreferences(DangNhap.SHARED_PRE, Activity_Xem_Thong_Tin_Le_Tan.this.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.clear();
                                editor.apply();
                                Activity_Xem_Thong_Tin_Le_Tan.this.finish();
                                Intent intent = new Intent(Activity_Xem_Thong_Tin_Le_Tan.this, DangNhap.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Không", null)
                        .setIcon(R.drawable.warning)
                        .show();

            }
        });
        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void setControl() {
        btnQuayLai = findViewById(R.id.btnQuayLai);
        btnDangXuat = findViewById(R.id.btnDangXuat);
        radioGroup_TTNV = findViewById(R.id.radioGroup_TTNV);
        imgNV = findViewById(R.id.imgNV);
        imgCCCD_Truoc_TTNV = findViewById(R.id.imgCCCD_Truoc_TTNV);
        imgCCCD_Sau_TTNV = findViewById(R.id.imgCCCD_Sau_TTNV);
        edtHoVaTen = findViewById(R.id.edtHoVaTen);
        edtTenDangNhap = findViewById(R.id.edtTenDangNhap);
        edtSDT = findViewById(R.id.edtSDT);
        edtLuong = findViewById(R.id.edtLuong);
        radioButtonLeTan_TTNV = findViewById(R.id.radioButtonLeTan_TTNV);
        radioButtonLaoCong_TTNV = findViewById(R.id.radioButtonLaoCong_TTNV);
        cbSangT2 = findViewById(R.id.cbSangT2);
        cbChieuT2 = findViewById(R.id.cbChieuT2);
        cbSangT3 = findViewById(R.id.cbSangT3);
        cbChieuT3 = findViewById(R.id.cbChieuT3);
        cbSangT4 = findViewById(R.id.cbSangT4);
        cbChieuT4 = findViewById(R.id.cbChieuT4);
        cbSangT5 = findViewById(R.id.cbSangT5);
        cbChieuT5 = findViewById(R.id.cbChieuT5);
        cbSangT6 = findViewById(R.id.cbSangT6);
        cbChieuT6 = findViewById(R.id.cbChieuT6);
        cbSangT7 = findViewById(R.id.cbSangT7);
        cbChieuT7 = findViewById(R.id.cbChieuT7);
        cbSangCN = findViewById(R.id.cbSangCN);
        cbChieuCN = findViewById(R.id.cbChieuCN);
        SharedPreferences sharedPreferences = getSharedPreferences(DangNhap.SHARED_PRE, MODE_PRIVATE);
        String idNhanVien = sharedPreferences.getString("id_staff", "");
        LoadNhanVien(idNhanVien);
    }
    private void LoadNhanVien(String idNhanVien) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("nhan_vien");
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("phan_cong");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    nhan_vien nhan_vien = dataSnapshot.getValue(nhan_vien.class);
                    if(nhan_vien.getId_nhan_vien().equals(idNhanVien))
                    {
                        edtHoVaTen.setText(nhan_vien.getTen_nhan_vien().toString());
                        edtSDT.setText(nhan_vien.getSo_dien_thoai().toString());

                        edtTenDangNhap.setText(nhan_vien.getUsername().toString());
                        edtLuong.setText(String.valueOf((int)nhan_vien.getLuong()));
                        String chucVuId = nhan_vien.getId_chuc_vu();
                        if ("1".equals(chucVuId)) {
                            radioGroup_TTNV.check(R.id.radioButtonLaoCong_TTNV);
                        } else if ("2".equals(chucVuId)) {
                            radioGroup_TTNV.check(R.id.radioButtonLeTan_TTNV);
                        }
                        Picasso.get().load(nhan_vien.getAnh_nhan_vien()).into(imgNV);
                        Picasso.get().load(nhan_vien.getAnh_CCCD_Truoc()).into(imgCCCD_Truoc_TTNV);
                        Picasso.get().load(nhan_vien.getAnh_CCCD_Sau()).into(imgCCCD_Sau_TTNV);
                        cbChieuT2.setChecked(true);
                        ref1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int thu2Sang = -1,thu2Chieu = -1, thu3Sang  = -1, thu3Chieu  = -1, thu4Sang = -1, thu4Chieu = -1, thu5Sang = -1, thu5Chieu = -1, thu6Sang = -1, thu6Chieu = -1, thu7Sang = -1, thu7Chieu = -1, CNSang = -1, CNChieu = -1;
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    phan_cong phan_cong = dataSnapshot.getValue(phan_cong.class);
                                    if(phan_cong.getId_nhan_vien().equals(nhan_vien.getId_nhan_vien()))
                                    {

                                        if(phan_cong.getDayofweek()==2 && phan_cong.getId_ca_lam().equals("1"))
                                        {
                                            thu2Sang = 1;
                                        }
                                        if(phan_cong.getDayofweek()==2&&phan_cong.getId_ca_lam().equals("2"))
                                        {
                                            thu2Chieu = 1;
                                        }
                                        if(phan_cong.getDayofweek()==3&&phan_cong.getId_ca_lam().equals("1"))
                                        {
                                            thu3Sang = 1;
                                        }
                                        if(phan_cong.getDayofweek()==3&&phan_cong.getId_ca_lam().equals("2"))
                                        {
                                            thu3Chieu = 1;
                                        }
                                        if(phan_cong.getDayofweek()==4&&phan_cong.getId_ca_lam().equals("1"))
                                        {
                                            thu4Sang = 1;
                                        }
                                        if(phan_cong.getDayofweek()==4&&phan_cong.getId_ca_lam().equals("2"))
                                        {
                                            thu4Chieu = 1;
                                        }
                                        if(phan_cong.getDayofweek()==5&&phan_cong.getId_ca_lam().equals("1"))
                                        {
                                            thu5Sang = 1;
                                        }
                                        if(phan_cong.getDayofweek()==5&&phan_cong.getId_ca_lam().equals("2"))
                                        {
                                            thu5Chieu = 1;
                                        }
                                        if(phan_cong.getDayofweek()==6&&phan_cong.getId_ca_lam().equals("1"))
                                        {
                                            thu6Sang = 1;
                                        }
                                        if(phan_cong.getDayofweek()==6&&phan_cong.getId_ca_lam().equals("2"))
                                        {
                                            thu6Chieu = 1;
                                        }
                                        if(phan_cong.getDayofweek()==7&&phan_cong.getId_ca_lam().equals("1"))
                                        {
                                            thu7Sang = 1;
                                        }
                                        if(phan_cong.getDayofweek()==7&&phan_cong.getId_ca_lam().equals("2"))
                                        {
                                            thu7Chieu = 1;
                                        }
                                        if(phan_cong.getDayofweek()==1&&phan_cong.getId_ca_lam().equals("1"))
                                        {
                                            CNSang = 1;
                                        }
                                        if(phan_cong.getDayofweek()==1&&phan_cong.getId_ca_lam().equals("2"))
                                        {
                                            CNChieu = 1;
                                        }
                                    }
                                }
                                if(thu2Sang==1)
                                {
                                    cbSangT2.setChecked(true);
                                }
                                else
                                {
                                    cbSangT2.setChecked(false);
                                }
                                if(thu2Chieu==1)
                                {
                                    cbChieuT2.setChecked(true);
                                }
                                else
                                {
                                    cbChieuT2.setChecked(false);
                                }
                                if(thu3Sang==1)
                                {
                                    cbSangT3.setChecked(true);
                                }
                                else
                                {
                                    cbSangT3.setChecked(false);
                                }
                                if(thu3Chieu==1)
                                {
                                    cbChieuT3.setChecked(true);
                                }
                                else
                                {
                                    cbChieuT3.setChecked(false);
                                }
                                if(thu4Sang==1)
                                {
                                    cbSangT4.setChecked(true);
                                }
                                else
                                {
                                    cbSangT4.setChecked(false);
                                }
                                if(thu4Chieu==1)
                                {
                                    cbChieuT4.setChecked(true);
                                }
                                else
                                {
                                    cbChieuT4.setChecked(false);
                                }
                                if(thu5Sang==1)
                                {
                                    cbSangT5.setChecked(true);
                                }
                                else
                                {
                                    cbSangT5.setChecked(false);
                                }
                                if(thu5Chieu==1)
                                {
                                    cbChieuT5.setChecked(true);
                                }
                                else
                                {
                                    cbChieuT5.setChecked(false);
                                }
                                if(thu6Sang==1)
                                {
                                    cbSangT6.setChecked(true);
                                }
                                else
                                {
                                    cbSangT6.setChecked(false);
                                }
                                if(thu6Chieu==1)
                                {
                                    cbChieuT6.setChecked(true);
                                }
                                else
                                {
                                    cbChieuT6.setChecked(false);
                                }
                                if(thu7Sang==1)
                                {
                                    cbSangT7.setChecked(true);
                                }
                                else
                                {
                                    cbSangT7.setChecked(false);
                                }
                                if(thu7Chieu==1)
                                {
                                    cbChieuT7.setChecked(true);
                                }
                                else
                                {
                                    cbChieuT7.setChecked(false);
                                }
                                if(CNSang==1)
                                {
                                    cbSangCN.setChecked(true);
                                }
                                else
                                {
                                    cbSangCN.setChecked(false);
                                }
                                if(CNChieu==1)
                                {
                                    cbChieuCN.setChecked(true);
                                }
                                else
                                {
                                    cbChieuCN.setChecked(false);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void LoadChucVu() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("chuc_vu");
        DatabaseReference refNhanVien = FirebaseDatabase.getInstance().getReference("nhan_vien");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    chuc_vu chucVu = dataSnapshot.getValue(chuc_vu.class);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}