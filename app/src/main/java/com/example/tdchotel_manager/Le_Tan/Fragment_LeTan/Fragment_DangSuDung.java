package com.example.tdchotel_manager.Le_Tan.Fragment_LeTan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.example.tdchotel_manager.Le_Tan.Adapter_DangSuDung.adapter_dangsudung;
import com.example.tdchotel_manager.Menu_QuanLy.Adapter_DichVu.Viewpageadapter;
import com.example.tdchotel_manager.Menu_QuanLy.Adapter_DichVu.themdichvu;
import com.example.tdchotel_manager.Menu_QuanLy.Adapter_DichVu.themdichvuphong;
import com.example.tdchotel_manager.Menu_QuanLy.Adapter_DichVu.themtiennghi;
import com.example.tdchotel_manager.Menu_QuanLy.Adapter_Phong.adapter_phong;
import com.example.tdchotel_manager.Menu_QuanLy.Fragment_Dichvu;
import com.example.tdchotel_manager.Model.hoa_don;
import com.example.tdchotel_manager.Model.phong;
import com.example.tdchotel_manager.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class Fragment_DangSuDung extends Fragment {

    RecyclerView rcv;


    public Fragment_DangSuDung() {
    }
    public static Fragment_DangSuDung newInstance() {
        return new Fragment_DangSuDung();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__dang_su_dung, container, false);

        setControl(view);
        setEvent();
        return view;
    }
    private void setEvent() {
        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter_dangsudung adapter = new adapter_dangsudung(getContext());
        rcv.setAdapter(adapter);
    }
    private void setControl(View view) {
        rcv = view.findViewById(R.id.rcv_DangSD);
    }
}

