package com.example.tdchotel_manager.Le_Tan.Fragment_LeTan;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.tdchotel_manager.Le_Tan.Adapter_DangSuDung.giahan_thoigian;
import com.example.tdchotel_manager.Menu_QuanLy.Adapter_Phong.adapter_phong;
import com.example.tdchotel_manager.Model.phong;
import com.example.tdchotel_manager.R;

import java.util.ArrayList;


public class Fragment_DangSuDung extends Fragment {

    private RecyclerView rcv_roomlist;
    // Tạo một danh sách tạm thời để lưu các phòng thỏa mãn điều kiện tìm kiếm
    private ArrayList<phong> filteredRoomList = new ArrayList<>();
    private ArrayList<phong> originalRoomList = new ArrayList<>();
    private adapter_phong adapter;
    ProgressBar progressBar, progressBar_itemphong;

    public Fragment_DangSuDung() {
        // Required empty public constructor
    }

    public static Fragment_DangSuDung newInstance() {
        return new Fragment_DangSuDung();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__dang_su_dung, container, false);
        setControl(view);
        adapter = new adapter_phong(getContext(), progressBar, progressBar_itemphong);
        setEvent();
        return view;
    }


    private void setEvent() {


        //Set data cho rcv_roomlist
        rcv_roomlist.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rcv_roomlist.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        rcv_roomlist.setAdapter(adapter);
        //click btn_add
        adapter.setOnItemLongClickListener(new adapter_phong.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(int position) {
                showDeleteConfirmationDialog(position);
            }
        });
    }
    private void showDeleteConfirmationDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Lựa chọn");
        builder.setItems(new String[]{"Gia hạn", "Trả phòng", "Dịch vụ"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        Intent intent = new Intent(getActivity(), giahan_thoigian.class);
                        startActivity(intent);
                        break;
                    case 1:
                        // Xử lý khi chọn "Xóa phòng"
                        break;
                }
            }
        });
        builder.show();
    }

    private void setControl(View view) {
        rcv_roomlist = view.findViewById(R.id.rcv_roomlist);
        progressBar = view.findViewById(R.id.progressBar_phong);
    }
}