package com.example.tdchotel_manager.Le_Tan.Fragment_LeTan;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.tdchotel_manager.Le_Tan.Activity_XacNhanDatPhongDichVu;
import com.example.tdchotel_manager.Le_Tan.Adapter_DangSuDung.adapter_dangsudung;

import com.example.tdchotel_manager.Le_Tan.Adapter_DangSuDung.dichvu_letan;
import com.example.tdchotel_manager.Le_Tan.Adapter_DangSuDung.giahanthoigian;
import com.example.tdchotel_manager.R;



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
