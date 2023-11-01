package com.example.tdchotel_manager.Menu_QuanLy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tdchotel_manager.Menu_QuanLy.Adapter_Phong.adapter_dich_vu_phong;
import com.example.tdchotel_manager.Menu_QuanLy.Adapter_Phong.adapter_tien_nghi;
import com.example.tdchotel_manager.Model.chi_tiet_dich_vu_phong;
import com.example.tdchotel_manager.Model.chi_tiet_tien_nghi;
import com.example.tdchotel_manager.Model.dich_vu_phong;
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
    ImageButton btn_back, btn_save, iv_decrease,iv_increase;
    EditText edt_name, edt_description, edt_price, edt_sale, edt_giuong_don, edt_giuong_doi;
    RadioGroup radiogroup;
    ArrayList<trang_thai_phong> list_status = new ArrayList<>();
    private RecyclerView rcv_tien_nghi, rcv_dich_vu_phong;
    private adapter_tien_nghi adapterTienNghi = new adapter_tien_nghi();
    private adapter_dich_vu_phong adapterDichVuPhong = new adapter_dich_vu_phong();
    ArrayList<chi_tiet_dich_vu_phong> list_chi_tietDVP = new ArrayList<>();
    ArrayList<chi_tiet_tien_nghi> list_chi_tietTN = new ArrayList<>();
    String IDphong = "";
    phong detail_infor_room = new phong();

    private void setControl() {
        radiogroup = findViewById(R.id.radiogroup);
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

    public boolean kiemtrathongtinphong() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            detail_infor_room = getIntent().getSerializableExtra("phong", phong.class);
        }
        return false;
    }

    private void setEvent() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // Finish the current activity
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAdd_room(new_room(), new Runnable() {
                    @Override
                    public void run() {
                        list_chi_tietDVP = adapterDichVuPhong.getChiTietDichVu();
                        ArrayList<chi_tiet_dich_vu_phong> tempDVPList = new ArrayList<>();
                        for (chi_tiet_dich_vu_phong chiTietDVP : list_chi_tietDVP) {
                            if (chiTietDVP.getSo_luong() != 0) {
                                tempDVPList.add(chiTietDVP);
                            }
                        }
                        list_chi_tietDVP = tempDVPList;
                        for (chi_tiet_dich_vu_phong detail_facility : list_chi_tietDVP) {
                            onClickAdd_facilities(facilities(detail_facility.getId_dich_vu_phong(), detail_facility.getSo_luong(), IDphong));
                        }

                        list_chi_tietTN = adapterTienNghi.getChi_tiet_tien_nghis();
                        for (chi_tiet_tien_nghi detail_comfort : list_chi_tietTN) {
                            onClickAdd_comfort(comfort(detail_comfort.getId_tien_nghi(), detail_comfort.getSo_luong(), IDphong));
                        }
                        finish();
                    }
                });
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

    chi_tiet_dich_vu_phong facilities(String id_facility, int quantity, String id_phong) {
        chi_tiet_dich_vu_phong new_facilities = new chi_tiet_dich_vu_phong();
        new_facilities.setId_dich_vu_phong(id_facility);
        new_facilities.setSo_luong(quantity);
        new_facilities.setId_phong(id_phong);
        return new_facilities;
    }

    private void onClickAdd_facilities(chi_tiet_dich_vu_phong facility) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("chi_tiet_dich_vu_phong");
        DatabaseReference new_facilityt = databaseReference.push(); // Tạo một khóa con mới
        new_facilityt.setValue(facility, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                String generatedId_facility = new_facilityt.getKey(); // Lấy khóa con duy nhất đã tạo
                if (generatedId_facility != null) {
                    facility.setId_chi_tiet_dich_vu_phong(generatedId_facility); // Gán khóa con duy nhất làm id_dich_vu cho dichvu
                    new_facilityt.setValue(facility); // Cập nhật lại dữ liệu với id_dich_vu mới
                    Toast.makeText(Activity_Thong_Tin_Phong.this, "Add success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Activity_Thong_Tin_Phong.this, "Add failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    chi_tiet_tien_nghi comfort(String id_comfort, int quantity, String id_phong) {

        chi_tiet_tien_nghi new_comfort = new chi_tiet_tien_nghi();
        new_comfort.setId_tien_nghi(id_comfort);
        new_comfort.setSo_luong(quantity);
        new_comfort.setId_phong(id_phong);
        return new_comfort;
    }

    private void onClickAdd_comfort(chi_tiet_tien_nghi comfort) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("chi_tiet_tien_nghi");
        DatabaseReference new_comfort = databaseReference.push(); // Tạo một khóa con mới
        new_comfort.setValue(comfort, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                String generatedId_comfort = new_comfort.getKey(); // Lấy khóa con duy nhất đã tạo
                if (generatedId_comfort != null) {
                    comfort.setId_chi_tiet_tien_nghi(generatedId_comfort); // Gán khóa con duy nhất làm id_dich_vu cho dichvu
                    new_comfort.setValue(comfort); // Cập nhật lại dữ liệu với id_dich_vu mới
                    Toast.makeText(Activity_Thong_Tin_Phong.this, "Add success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Activity_Thong_Tin_Phong.this, "Add failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void increaseValue(EditText editText) {
        int value = Integer.parseInt(editText.getText().toString());
        value++;
        editText.setText(String.valueOf(value));
    }

    private void decreaseValue(EditText editText) {
        int value = Integer.parseInt(editText.getText().toString());
        if (value > 0) {
            value--;
            editText.setText(String.valueOf(value));
        }
    }

    private void onClickAdd_room(phong phong, final Runnable afterAdding) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("phong");
        DatabaseReference new_room = databaseReference.push(); // Tạo một khóa con mới

        new_room.setValue(phong, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null) {
                    IDphong = new_room.getKey(); // Lấy khóa con duy nhất đã tạo
                    if (IDphong != null) {
                        phong.setId_phong(IDphong); // Gán khóa con duy nhất làm id_phong cho phong

                        Toast.makeText(Activity_Thong_Tin_Phong.this, "Add success", Toast.LENGTH_SHORT).show();
                        if (afterAdding != null)
                            afterAdding.run(); // Gọi hàm callback sau khi thêm phòng
                    } else {
                        Toast.makeText(Activity_Thong_Tin_Phong.this, "Add failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Activity_Thong_Tin_Phong.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    String typeRoom() {
        String type_room = "";
        if (radiogroup.getCheckedRadioButtonId() != -1) {
            RadioButton selectedRadioButton = findViewById(radiogroup.getCheckedRadioButtonId());
            type_room = selectedRadioButton.getText().toString(); // Lấy dữ liệu của radio button đã chọn
        } else {
            Toast.makeText(Activity_Thong_Tin_Phong.this, "Chưa chọn loại phòng", Toast.LENGTH_SHORT).show();
            radiogroup.findFocus();
        }
        return type_room;
    }

    phong new_room() {
        String name = edt_name.getText().toString();
        String description = edt_description.getText().toString();
        int price = Integer.parseInt(edt_price.getText().toString());
        int sale = Integer.parseInt(edt_sale.getText().toString());
        String type = typeRoom();
        ArrayList<String> anh = new ArrayList<>();
        anh.add("anh 1");
        anh.add("anh2");
        //trạng thái
        int selectedPosition = sp_status.getSelectedItemPosition();
        trang_thai_phong selectedTrangThai = list_status.get(selectedPosition);
        String statusID = selectedTrangThai.getId_trang_thai_phong();
        int luot_thue = 0;
        int rating = 0;

        phong room = new phong(null, name, description, anh, type, statusID, luot_thue, price, sale, rating);
        return room;
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
