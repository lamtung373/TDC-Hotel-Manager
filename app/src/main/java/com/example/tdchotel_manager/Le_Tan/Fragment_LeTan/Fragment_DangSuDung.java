package com.example.tdchotel_manager.Le_Tan.Fragment_LeTan;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.tdchotel_manager.Menu_QuanLy.Activity_Thong_Tin_Phong;
import com.example.tdchotel_manager.Menu_QuanLy.Adapter_Phong.adapter_phong;
import com.example.tdchotel_manager.Menu_QuanLy.Fragment_Phong;
import com.example.tdchotel_manager.Model.phong;
import com.example.tdchotel_manager.R;

import java.util.ArrayList;
import java.util.List;


public class Fragment_DangSuDung extends Fragment {

    private Spinner sp_loai;
    private RecyclerView rcv_roomlist;
    // Tạo một danh sách tạm thời để lưu các phòng thỏa mãn điều kiện tìm kiếm
    private ArrayList<phong> filteredRoomList = new ArrayList<>();
    private ArrayList<phong> originalRoomList = new ArrayList<>();
    private adapter_phong adapter;
    ImageButton btn_add;
    EditText edt_search;
    ProgressBar progressBar, progressBar_itemphong;

    public Fragment_DangSuDung() {
        // Required empty public constructor
    }

    public static Fragment_DangSuDung newInstance() {
        return new Fragment_DangSuDung();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__phong, container, false);
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
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Activity_Thong_Tin_Phong.class);
                startActivity(intent);
            }
        });
        adapter.setOnItemLongClickListener(new adapter_phong.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(int position) {
                showDeleteConfirmationDialog(position);
            }
        });
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Không làm gì ở đây
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Không làm gì ở đây
            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchText = s.toString().trim();
                if (!searchText.isEmpty()) {
                    filterRoomList(searchText);
                } else {
                    // Khi người dùng xóa hết nội dung tìm kiếm, hiển thị lại danh sách phòng ban đầu
                    adapter.updateRoomList(adapter.getDanh_sach_phong());
                }
            }
        });
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


    private void showDeleteConfirmationDialog(final int position) {
        new AlertDialog.Builder(getContext())
                .setTitle("Xóa Phòng")
                .setMessage("Bạn chắc chắn muốn xoá phòng này chứ?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Gọi hàm removeItem của adapter và truyền vị trí position vào
                        adapter.removeItem(position);
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(R.drawable.warning)
                .show();
    }

    private void setControl(View view) {
        rcv_roomlist = view.findViewById(R.id.rcv_roomlist);
        btn_add = view.findViewById(R.id.btn_save_phong);
        edt_search = view.findViewById(R.id.edt_search);
        progressBar = view.findViewById(R.id.progressBar_phong);
    }
}
