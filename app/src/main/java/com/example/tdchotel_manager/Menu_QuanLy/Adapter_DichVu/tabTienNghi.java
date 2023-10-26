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


public class tabTienNghi extends Fragment {

    private RecyclerView rcvDV;
    private adapter_tien_nghi_dv adapterTienNghi = new adapter_tien_nghi_dv();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_tien_nghi, container, false);
        setControl(view);
        setEvent();
        return view;
    }
    private void setEvent() {
        rcvDV.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        rcvDV.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        rcvDV.setAdapter(adapterTienNghi);
    }

    private void setControl(View view) {
        rcvDV = view.findViewById(R.id.rcvDV);
    }
}