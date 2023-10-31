package com.example.tdchotel_manager.Menu_QuanLy;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdchotel_manager.Menu_QuanLy.Adapter_Phong.adapter_phong;
import com.example.tdchotel_manager.Model.phong;
import com.example.tdchotel_manager.R;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Phong extends Fragment {

    private Spinner sp_loai;
    private RecyclerView rcv_roomlist;
    // Tạo một danh sách tạm thời để lưu các phòng thỏa mãn điều kiện tìm kiếm
    private ArrayList<phong> filteredRoomList = new ArrayList<>();
    private ArrayList<phong> originalRoomList = new ArrayList<>();
    private adapter_phong adapter;
    ImageButton btn_add;
    EditText edt_search;

    public Fragment_Phong() {
        // Required empty public constructor
    }

    public static Fragment_Phong newInstance() {
        return new Fragment_Phong();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__phong, container, false);
        setControl(view);
        adapter = new adapter_phong(getContext());
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
                    adapter.updateRoomList(adapter.getOriginalRoomList());
                }
            }
        });

    }
    private void filterRoomList(String searchText) {
        filteredRoomList.clear(); // Thêm dòng này để xóa danh sách cũ
        for (phong room : adapter.getOriginalRoomList()) {
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
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void setControl(View view) {
        sp_loai = view.findViewById(R.id.spTypeRoom);
        rcv_roomlist = view.findViewById(R.id.rcv_roomlist);
        btn_add = view.findViewById(R.id.btn_save);
        edt_search=view.findViewById(R.id.edt_search);
    }
}
