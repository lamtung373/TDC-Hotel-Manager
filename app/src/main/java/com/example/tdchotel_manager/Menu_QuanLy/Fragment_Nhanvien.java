package com.example.tdchotel_manager.Menu_QuanLy;

import android.content.Intent;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tdchotel_manager.IOnClickItem;
import com.example.tdchotel_manager.Menu_QuanLy.Adapter_NhanVien.NhanVien_Adapter;
import com.example.tdchotel_manager.Menu_QuanLy.Adapter_NhanVien.ThemNhanVien;
import com.example.tdchotel_manager.Menu_QuanLy.Adapter_NhanVien.ThongTinNhanVien;
import com.example.tdchotel_manager.Model.nhan_vien;
import com.example.tdchotel_manager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Fragment_Nhanvien extends Fragment {
    ImageButton btnThemNV;
    EditText edtSearch;
    RecyclerView recyclerView;
    NhanVien_Adapter adapter;
    ArrayList<nhan_vien> data = new ArrayList<>();
    DatabaseReference databaseReference;
    HashMap<String, String> chucVuMapping = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment__nhanvien, container, false);

        // Kết nối RecyclerView trong layout
        recyclerView = view.findViewById(R.id.recyclerViewNhanVien);
        edtSearch = view.findViewById(R.id.edtSearch);

        // Thiết lập LayoutManager cho RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Thiết lập Adapter cho RecyclerView
        adapter = new NhanVien_Adapter(data, chucVuMapping, new IOnClickItem() {
            @Override
            public void OnItemClick(Object object) {
                nhan_vien selectedNhanVien = (nhan_vien) object;
                Intent intent = new Intent(getActivity(), ThongTinNhanVien.class);
                intent.putExtra("selectedNhanVien", selectedNhanVien); // Assuming nhan_vien is Serializable
                startActivity(intent);
            }
        });


        recyclerView.setAdapter(adapter);

        // Kết nối đến Firebase Realtime Database cho chức vụ
        DatabaseReference chucVuReference = FirebaseDatabase.getInstance().getReference("chuc_vu");
        chucVuReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chucVuMapping.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String id = snapshot.child("id_chuc_vu").getValue(String.class);
                    String tenChucVu = snapshot.child("ten_chuc_vu").getValue(String.class);
                    chucVuMapping.put(id, tenChucVu);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Lỗi: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        // Kết nối đến Firebase Realtime Database
        databaseReference = FirebaseDatabase.getInstance().getReference("nhan_vien"); // Thay thế "nhan_vien" bằng đường dẫn Firebase của bạn

        // Lắng nghe sự thay đổi trong dữ liệu Firebase
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                data.clear(); // Xóa dữ liệu cũ để cập nhật dữ liệu mới

                // Lặp qua các phần tử trong dataSnapshot
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    nhan_vien nv = snapshot.getValue(nhan_vien.class);
                    data.add(nv);
                }

                adapter.notifyDataSetChanged(); // Thông báo cho Adapter cập nhật dữ liệu
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý khi có lỗi
                Toast.makeText(getActivity(), "Lỗi: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Không cần thực hiện gì ở đây
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Không cần thực hiện gì ở đây
            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        // Kết nối các thành phần giao diện với các thành phần trong mã Java của các thành phần trong mã
        btnThemNV = view.findViewById(R.id.btnThemNV);

        // Thiết lập sự kiện khi nút "Thêm nhân viên" được nhấn
        btnThemNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo Intent để chuyển sang layout_themnhanvien.
                Intent intent = new Intent(getActivity(), ThemNhanVien.class);

                // Khởi động hoạt động mới.
                startActivity(intent);
            }
        });
        return view;
    }

    private void filter(String text) {
        ArrayList<nhan_vien> filteredList = new ArrayList<>();

        for (nhan_vien nv : data) {
            if (nv.getTen_nhan_vien().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(nv);
            }
        }

        adapter.filterList(filteredList);
    }
}