package com.example.tdchotel_manager.Menu_QuanLy.Adapter_DichVu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tdchotel_manager.R;


public class tabDichVu extends Fragment {


    private RecyclerView rcvDV;
    private adapter_dich_vu adapter_dich_vu = new adapter_dich_vu(getContext());
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_dich_vu, container, false);
        setControl(view);
        setEvent();
        return view;
    }

    private void setEvent() {
        rcvDV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rcvDV.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        adapter_dich_vu = new adapter_dich_vu(getContext()); // Khởi tạo adapter với context
        rcvDV.setAdapter(adapter_dich_vu);
    }

    private void setControl(View view) {
        rcvDV = view.findViewById(R.id.rcvDV);
    }
}