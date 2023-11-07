package com.example.tdchotel_manager.Le_Tan.Fragment_LeTan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.tdchotel_manager.Menu_QuanLy.Adapter_Phong.adapter_phong;
import com.example.tdchotel_manager.Model.phong;
import com.example.tdchotel_manager.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_SanSang#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_SanSang extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText edt_search;
    RecyclerView rcv_roomlist;
    ProgressBar progressBar, progressBar_itemphong;
    private ArrayList<phong> filteredRoomList = new ArrayList<>();
    adapter_phong adapter;

    public Fragment_SanSang() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_SanSang.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_SanSang newInstance(String param1, String param2) {
        Fragment_SanSang fragment = new Fragment_SanSang();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__san_sang, container, false);
        setControl(view);
        setEvent();
        return view;
    }

    private void filterRoomList(String searchText) {
        filteredRoomList.clear(); // Thêm dòng này để xóa danh sách cũ
        for (phong room : adapter.getDanh_sach_phong()) {
            if (room.getTen_phong().toLowerCase().contains(searchText.toLowerCase())) {
                filteredRoomList.add(room);
            }
        }

        adapter.updateRoomList(filteredRoomList);
    }

    private void setEvent() {
        //Set data cho rcv_roomlist
        adapter = new adapter_phong(getContext(), progressBar, progressBar_itemphong);
        rcv_roomlist.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rcv_roomlist.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        rcv_roomlist.setAdapter(adapter);
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String searchText = editable.toString().trim();
                if (!searchText.isEmpty()) {
                    filterRoomList(searchText);
                } else {
                    // Khi người dùng xóa hết nội dung tìm kiếm, hiển thị lại danh sách phòng ban đầu
                    adapter.updateRoomList(adapter.getDanh_sach_phong());
                }
            }
        });
    }

    private void setControl(View view) {
        rcv_roomlist = view.findViewById(R.id.rcv_roomlist);
        edt_search = view.findViewById(R.id.edt_search);
        progressBar = view.findViewById(R.id.progressBar_phong);
    }
}