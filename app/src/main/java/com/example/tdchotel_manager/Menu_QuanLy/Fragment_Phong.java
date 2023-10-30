package com.example.tdchotel_manager.Menu_QuanLy;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdchotel_manager.Menu_QuanLy.Adapter_Phong.adapter_phong;
import com.example.tdchotel_manager.R;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Phong extends Fragment {

    private Spinner sp_loai;
    private RecyclerView rcv_roomlist;
    private adapter_phong adapter = new adapter_phong();
    ImageButton btn_add;

    public Fragment_Phong() {
        // Required empty public constructor
    }

    public static Fragment_Phong newInstance() {
        return new Fragment_Phong();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__phong, container, false);
        setControl(view);
        setEvent();
        return view;
    }

    private void setEvent() {
        //Set data cho spinner
        List<String> spinnerData = new ArrayList<>();
        spinnerData.add("1 Người");
        spinnerData.add("2 Người");
        spinnerData.add("3 Người");
        spinnerData.add("4 Người");
        spinnerData.add("5 Người");

        ArrayAdapter<String> adapter_spinner = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, spinnerData);
        adapter_spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_loai.setAdapter(adapter_spinner);

        //Set data cho rcv_roomlist
        rcv_roomlist.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rcv_roomlist.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        rcv_roomlist.setAdapter(adapter);
        //click btn_add
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Activity_Thong_Tin_Phong.class);
                startActivity(intent);
            }
        });

    }

    private void setControl(View view) {
        sp_loai = view.findViewById(R.id.spTypeRoom);
        rcv_roomlist = view.findViewById(R.id.rcv_roomlist);
        btn_add = view.findViewById(R.id.btn_save);
    }
}
