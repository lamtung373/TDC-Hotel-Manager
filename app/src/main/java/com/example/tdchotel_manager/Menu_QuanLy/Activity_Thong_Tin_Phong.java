package com.example.tdchotel_manager.Menu_QuanLy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.tdchotel_manager.Menu_QuanLy.Adapter_Phong.adapter_dich_vu_phong;
import com.example.tdchotel_manager.Menu_QuanLy.Adapter_Phong.adapter_tien_nghi;
import com.example.tdchotel_manager.Model.tien_nghi;
import com.example.tdchotel_manager.R;

import java.util.ArrayList;
import java.util.List;

public class Activity_Thong_Tin_Phong extends AppCompatActivity {
    Spinner sp_status;
    ImageButton btn_back;
    private RecyclerView rcv_tien_nghi, rcv_dich_vu_phong;
    private adapter_tien_nghi adapterTienNghi = new adapter_tien_nghi();
    private adapter_dich_vu_phong adapterDichVuPhong = new adapter_dich_vu_phong();
    private ArrayList<tien_nghi> list_tien_nghi = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thong_tin_chi_tiet_phong);
        setControl();
        setEvent();
    }

    private void setEvent() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        List<String> spinnerData = new ArrayList<>();
        spinnerData.add("1 Người");
        spinnerData.add("2 Người");
        spinnerData.add("3 Người");
        spinnerData.add("4 Người");
        spinnerData.add("5 Người");

        ArrayAdapter<String> adapter_spinner = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, spinnerData);
        adapter_spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_status.setAdapter(adapter_spinner);

        rcv_tien_nghi.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rcv_tien_nghi.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rcv_tien_nghi.setAdapter(adapterTienNghi);

        rcv_dich_vu_phong.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rcv_dich_vu_phong.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rcv_dich_vu_phong.setAdapter(adapterDichVuPhong);
    }

    private void setControl() {
        sp_status = findViewById(R.id.sp_status);
        rcv_tien_nghi = findViewById(R.id.rcv_tien_nghi);
        rcv_dich_vu_phong = findViewById(R.id.rcv_dich_vu_phong);
        btn_back = findViewById(R.id.btn_back);
    }
}