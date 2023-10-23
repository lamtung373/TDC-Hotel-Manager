package com.example.tdchotel_manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.tdchotel_manager.Menu_QuanLy.Adapter_DichVu.Fragment_Dichvu;

public class DangNhap extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        setControl();
        setEvent();


    }


    private void setControl() {
    }

    private void setEvent() {
        
    }
}