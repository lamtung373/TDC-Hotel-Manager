package com.example.tdchotel_manager.Lao_Cong;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.tdchotel_manager.Model.phong;
import com.example.tdchotel_manager.R;

public class Activity_ChiTietDonPhong extends AppCompatActivity {
RecyclerView rcv_chitiettiennghi,rcv_chitietdichvuphong;
String idphong;
private adapter_getchitiettiennghi adapter_getchitiettiennghi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_don_phong);
        Intent intent = getIntent();
        idphong = intent.getStringExtra("idphong");
        adapter_getchitiettiennghi=new adapter_getchitiettiennghi(idphong);
        setControl();
        setEvent();
    }

    private void setControl() {
        rcv_chitiettiennghi=findViewById(R.id.rcv_danhsach_tiennghi);
    }

    private void setEvent() {
        rcv_chitiettiennghi.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rcv_chitiettiennghi.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rcv_chitiettiennghi.setAdapter(adapter_getchitiettiennghi);
    }
}