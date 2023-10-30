package com.example.tdchotel_manager.Menu_QuanLy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tdchotel_manager.Menu_QuanLy.Adapter_Phong.adapter_dich_vu_phong;
import com.example.tdchotel_manager.Menu_QuanLy.Adapter_Phong.adapter_tien_nghi;
import com.example.tdchotel_manager.Model.phong;
import com.example.tdchotel_manager.Model.trang_thai_phong;
import com.example.tdchotel_manager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Activity_Thong_Tin_Phong extends AppCompatActivity {
    Spinner sp_status;
    ArrayList<trang_thai_phong> list_status = new ArrayList<>();
    ImageButton btn_back, btn_save;
    EditText edt_name, edt_description, edt_price, edt_sale;
    private RecyclerView rcv_tien_nghi, rcv_dich_vu_phong;
    private adapter_tien_nghi adapterTienNghi = new adapter_tien_nghi();
    private adapter_dich_vu_phong adapterDichVuPhong = new adapter_dich_vu_phong();

    private void setControl() {
        edt_sale = findViewById(R.id.edt_price_sale);
        edt_price = findViewById(R.id.edt_price_room);
        edt_description = findViewById(R.id.edt_description);
        sp_status = findViewById(R.id.sp_status);
        rcv_tien_nghi = findViewById(R.id.rcv_tien_nghi);
        rcv_dich_vu_phong = findViewById(R.id.rcv_dich_vu_phong);
        btn_back = findViewById(R.id.btn_back);
        btn_save = findViewById(R.id.btn_save);
        edt_name = findViewById(R.id.edt_name_room);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thong_tin_chi_tiet_phong);
        setControl();
        setEvent();
        loadTrangThaiPhong(); // Gọi phương thức để lấy danh sách trạng thái phòng từ Firebase
    }

    private void setEvent() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy vị trí (position) đã chọn trong Spinner
                int selectedPosition = sp_status.getSelectedItemPosition();

                // Kiểm tra nếu vị trí đã chọn hợp lệ
                if (selectedPosition != AdapterView.INVALID_POSITION) {
                    trang_thai_phong selectedTrangThai = list_status.get(selectedPosition);
                    String selectedId = selectedTrangThai.getId_trang_thai_phong();
                    String selectedTenTrangThai = selectedTrangThai.getTen_trang_thai();

                    String name = edt_name.getText().toString();
                    String description = edt_description.getText().toString();
                    String price = edt_price.getText().toString();
                    String sale = edt_sale.getText().toString();

                    // Toast ID và tên trạng thái phòng
                    Toast.makeText(Activity_Thong_Tin_Phong.this, "ID: " + selectedId + ", Tên Trạng thái: " + selectedTenTrangThai, Toast.LENGTH_SHORT).show();

                }
            }
        });

        sp_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                trang_thai_phong selectedTrangThai = list_status.get(position);
                if (selectedTrangThai != null) {
                    String selectedId = selectedTrangThai.getId_trang_thai_phong();
                    String selectedTenTrangThai = selectedTrangThai.getTen_trang_thai();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
               Toast.makeText(Activity_Thong_Tin_Phong.this, "Vui lòng chọn trạng thái phòng", Toast.LENGTH_SHORT).show();
               sp_status.findFocus();
            }
        });
        rcv_tien_nghi.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rcv_tien_nghi.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rcv_tien_nghi.setAdapter(adapterTienNghi);

        rcv_dich_vu_phong.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rcv_dich_vu_phong.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rcv_dich_vu_phong.setAdapter(adapterDichVuPhong);
    }
    phong new_room(){
        String name = edt_name.getText().toString();
        String description = edt_description.getText().toString();
        String price = edt_price.getText().toString();
        String sale = edt_sale.getText().toString();
        //trạng thái
        int selectedPosition = sp_status.getSelectedItemPosition();
        trang_thai_phong selectedTrangThai = list_status.get(selectedPosition);
        String selectedId = selectedTrangThai.getId_trang_thai_phong();

        phong room=new phong();
        room.setTen_phong(edt_name.getText().toString());
        room.setMo_ta_chung(edt_description.getText().toString());
        return room;
    }
    private void onClickAdd_room(phong phong) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("phong");
        DatabaseReference new_room = databaseReference.push(); // Tạo một khóa con mới
        new_room.setValue(phong, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                String generatedId = new_room.getKey(); // Lấy khóa con duy nhất đã tạo
                if (generatedId != null) {
                    phong.setId_phong(generatedId); // Gán khóa con duy nhất làm id_dich_vu cho dichvu
                    new_room.setValue(phong); // Cập nhật lại dữ liệu với id_dich_vu mới
                    Toast.makeText(Activity_Thong_Tin_Phong.this, "Add success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Activity_Thong_Tin_Phong.this, "Add failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void loadTrangThaiPhong() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("trang_thai_phong");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list_status.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    trang_thai_phong trangThai = dataSnapshot.getValue(trang_thai_phong.class);
                    if (trangThai != null) {
                        list_status.add(trangThai);
                    }
                }

                // Cập nhật Spinner sp_status khi có dữ liệu mới
                updateSpinnerStatus();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi nếu có
            }
        });
    }

    private void updateSpinnerStatus() {
        List<String> spinnerData = new ArrayList<>();
        for (trang_thai_phong trangThai : list_status) {
            spinnerData.add(trangThai.getTen_trang_thai());
        }

        ArrayAdapter<String> adapter_spinner = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, spinnerData);
        adapter_spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_status.setAdapter(adapter_spinner);
    }

}
