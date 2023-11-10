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


import com.example.tdchotel_manager.Le_Tan.Fragment_LeTan.Adapter_DangSuDung.adapter_dangsudung;

import com.example.tdchotel_manager.Le_Tan.Fragment_LeTan.Adapter_DangSuDung.dichvu_letan;
import com.example.tdchotel_manager.Le_Tan.Fragment_LeTan.Adapter_DangSuDung.giahanthoigian;
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
        adapter.setOnItemLongClickListener(new adapter_dangsudung.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(int position) {
                // Xử lý khi người dùng giữ vào một item
                // Hiển thị dialog lựa chọn ở đây
                showItemOptions(position);
            }
        });

        rcv.setAdapter(adapter);
    }

    private void setControl(View view) {
        rcv = view.findViewById(R.id.rcv_DangSD);
    }

    private void showItemOptions(int position) {
        // Xử lý khi người dùng giữ vào một item
        // Hiển thị dialog lựa chọn ở đây
        // Ví dụ: AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Lựa chọn hình thức");
        builder.setItems(new CharSequence[]{"Gia hạn", "Trả phòng", "Dịch vụ"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Xử lý tùy thuộc vào lựa chọn của người dùng (Tác vụ 1 hoặc Tác vụ 2)
                if (which == 0) {
                    Intent intent = new Intent(getActivity(), giahanthoigian.class);
                    startActivity(intent);
                } else if (which == 1) {
                    // Thực hiện Tác vụ 2
                }else if (which == 2) {
                    Intent intent2 = new Intent(getActivity(), dichvu_letan.class);
                    startActivity(intent2);
                }
            }
        });
        builder.show();
    }
}
