package com.example.tdchotel_manager.Menu_QuanLy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.example.tdchotel_manager.R;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Phong extends Fragment {

    private Spinner sp_loai;

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
        List<String> spinnerData = new ArrayList<>();
        spinnerData.add("1 Người");
        spinnerData.add("2 Người");
        spinnerData.add("3 Người");
        spinnerData.add("4 Người");
        spinnerData.add("5 Người");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, spinnerData);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp_loai.setAdapter(adapter);
    }

    private void setControl(View view) {
        sp_loai = view.findViewById(R.id.spTypeRoom);
    }
}
