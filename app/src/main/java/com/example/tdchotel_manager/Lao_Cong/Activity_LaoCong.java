package com.example.tdchotel_manager.Lao_Cong;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.tdchotel_manager.R;

public class Activity_LaoCong extends AppCompatActivity {
    RecyclerView rcv_phong_kiemtra,rcv_don_thuongnhat;
    ProgressBar progressBar;
    private apdapter_don_kiemtra adapter_don_kiemtra;
    private adapter_don_thuong_nhat adapter_don_thuong_nhat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lao_cong);
        adapter_don_kiemtra = new apdapter_don_kiemtra(this);
        adapter_don_thuong_nhat = new adapter_don_thuong_nhat(this);
        setControl();
        setEvent();
    }

    private void setEvent() {
        //Set data cho rcv_roomlist
        rcv_phong_kiemtra.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rcv_phong_kiemtra.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rcv_phong_kiemtra.setAdapter(adapter_don_kiemtra);

        rcv_don_thuongnhat.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rcv_don_thuongnhat.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        rcv_don_thuongnhat.setAdapter(adapter_don_thuong_nhat);
    }

    private void setControl() {
        rcv_phong_kiemtra = findViewById(R.id.rcv_phong_kiemtra);
        rcv_don_thuongnhat = findViewById(R.id.rcv_don_thuong_nhat);
        progressBar = findViewById(R.id.progressBar_phongdon);
    }
}