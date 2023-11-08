package com.example.tdchotel_manager.Menu_QuanLy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tdchotel_manager.Menu_QuanLy.Adapter_NhanVien.NhanVienNghiAdapter;
import com.example.tdchotel_manager.Model.cham_cong;
import com.example.tdchotel_manager.Model.chuc_vu;
import com.example.tdchotel_manager.Model.nhan_vien;
import com.example.tdchotel_manager.Model.phan_cong;
import com.example.tdchotel_manager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Activity_NghiLam extends AppCompatActivity {
    RecyclerView rcvNhanVien;
    Button btnThoiNhan;
    ImageButton btn_back;
    EditText edtNhanVien;
    private List<nhan_vien> nhanVienList = new ArrayList<>();
    private List<chuc_vu> chucVuList = new ArrayList<>();;
    NhanVienNghiAdapter nhanVienNghiAdapter;
    private DatePickerDialog.OnDateSetListener mDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nghi_lam);

        setControl();
        setEvent();
    }
    private void LayNgayHienTai()
    {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = formatter.format(date);
        btnThoiNhan.setText(strDate);
    }
    private void setEvent() {
        ChonThoiGianNhan();
        edtNhanVien.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    LoadNghiLam();
                    return true;
                }
                return false;
            }
        });
//        edtNhanVien.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//            @Override
//            public void afterTextChanged(Editable editable) {
//                nhanVienList.clear();
//                LoadNghiLam();
//            }
//        });
    }


    private void setControl() {

        btnThoiNhan=findViewById(R.id.btnThoiNhan);
        btn_back=findViewById(R.id.btn_back);
        rcvNhanVien=findViewById(R.id.rcvNhanVien);
        edtNhanVien=findViewById(R.id.edtNhanVien);
        LayNgayHienTai();
        LoadChucVu();
        LoadNghiLam();
        nhanVienNghiAdapter = new NhanVienNghiAdapter(nhanVienList, chucVuList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvNhanVien.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvNhanVien.addItemDecoration(decoration);
        rcvNhanVien.setAdapter(nhanVienNghiAdapter);
    }

    void ChonThoiGianNhan() {
        btnThoiNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                                Calendar calendar = Calendar.getInstance();

                                DatePickerDialog.OnDateSetListener dataListener = new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                        calendar.set(Calendar.YEAR, year);
                                        calendar.set(Calendar.MONTH, month);
                                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                        new AlertDialog.Builder(Activity_NghiLam.this)
                                                .setMessage("Bạn muốn lọc thông tin theo tháng hay ngày?")
                                                        .setCancelable(false)
                                                                .setPositiveButton("Theo ngày", new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialogInterface, int i) {

                                                                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                                                        btnThoiNhan.setText(dateFormat.format(calendar.getTime()));
                                                                        LoadNghiLam();
                                                                    }
                                                                }).setNegativeButton("Theo tháng", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy");
                                                        btnThoiNhan.setText(dateFormat.format(calendar.getTime()));
                                                        LoadNghiLam();
                                                    }
                                                })
                                                .show();

                                    }
                                };
                                new DatePickerDialog(Activity_NghiLam.this, dataListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();




            }
        });

    }
    private void LoadChucVu() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("chuc_vu");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chucVuList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    chuc_vu chucVu = dataSnapshot.getValue(chuc_vu.class);
                    chucVuList.add(chucVu);
                }
                nhanVienNghiAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void LoadNghiLam() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("nhan_vien");
        DatabaseReference refChamCong = FirebaseDatabase.getInstance().getReference("cham_cong");
        refChamCong.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nhanVienList.clear();
                refChamCong.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            cham_cong cham_cong = dataSnapshot.getValue(cham_cong.class);
                            String[] words = cham_cong.getCheck_in().split("\\s");
                            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                        nhan_vien nhan_vien = dataSnapshot.getValue(nhan_vien.class);

                                        if (cham_cong.getId_nhan_vien().equals(nhan_vien.getId_nhan_vien())&&words[1].equals("Nghỉ"))
                                        {
                                            if(!edtNhanVien.getText().toString().equals(""))
                                            {
                                                if(words[0].endsWith(btnThoiNhan.getText().toString())&&edtNhanVien.getText().toString().equals(nhan_vien.getTen_nhan_vien()))
                                                {
                                                    nhan_vien.setCham_cong(cham_cong);
                                                    nhanVienList.add(nhan_vien);


                                                }
                                            }
                                            else
                                            {
                                                if(words[0].endsWith(btnThoiNhan.getText().toString()))
                                                {
                                                    nhan_vien.setCham_cong(cham_cong);
                                                    nhanVienList.add(nhan_vien);


                                                }
                                            }
                                            Log.e("hdahsiu","dfas");
                                        }
                                    }
                                    nhanVienNghiAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                        nhanVienNghiAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                nhanVienNghiAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    void ChonThoiGianNhanThang() {
        btnThoiNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();

            }
        });

    }

}