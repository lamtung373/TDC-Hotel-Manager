package com.example.tdchotel_manager.Le_Tan.Fragment_LeTan;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import com.example.tdchotel_manager.Le_Tan.Adapter_DangSuDung.adapter_dangsudung;

import com.example.tdchotel_manager.R;



public class Fragment_DangSuDung extends Fragment {

    RecyclerView rcv;
    EditText edt_search;

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
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Không cần trong trường hợp này
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Lọc adapter khi người dùng gõ vào thanh tìm kiếm
                adapter.filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Không cần trong trường hợp này
            }
        });

        rcv.setAdapter(adapter);
    }
    private void setControl(View view) {
        edt_search= view.findViewById(R.id.edt_searchDSD);
        rcv = view.findViewById(R.id.rcv_DangSD);
    }
}
