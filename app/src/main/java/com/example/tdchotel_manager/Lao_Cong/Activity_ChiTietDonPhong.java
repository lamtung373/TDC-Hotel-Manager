package com.example.tdchotel_manager.Lao_Cong;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.tdchotel_manager.DangNhap;
import com.example.tdchotel_manager.Menu_QuanLy.Activity_Thong_Tin_Phong;
import com.example.tdchotel_manager.Menu_QuanLy.Adapter_Phong.adapter_dich_vu_phong;
import com.example.tdchotel_manager.Menu_QuanLy.Adapter_Phong.adapter_tien_nghi;
import com.example.tdchotel_manager.Model.chi_tiet_dich_vu_phong;
import com.example.tdchotel_manager.Model.chi_tiet_hoa_don_dich_vu_phong;
import com.example.tdchotel_manager.Model.chi_tiet_hoa_don_tien_nghi;
import com.example.tdchotel_manager.Model.chi_tiet_tien_nghi;
import com.example.tdchotel_manager.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Activity_ChiTietDonPhong extends AppCompatActivity {
    public static String SHARED_PRE = "shared_pre";
    RecyclerView rcv_chitiettiennghi, rcv_chitietdichvuphong, rcv_sudung_tien_nghi, rcv_sudung_dichvuphong;
    Button btn_xacnhan;
    ImageButton btn_back;
    String idphong, idhoadon, id_staff_auto;
    ArrayList<chi_tiet_hoa_don_tien_nghi> chi_tiet_hoa_don_tien_nghis = new ArrayList<>();
    ArrayList<chi_tiet_hoa_don_dich_vu_phong> chi_tiet_hoa_don_dich_vu_phongs = new ArrayList<>();
    private adapter_getchitiettiennghi adapter_getchitiettiennghi;
    private adapter_getchitietdichvuphong adapter_getchitietdichvuphong;
    private adapter_tien_nghi adapterTienNghi = new adapter_tien_nghi();
    private adapter_dich_vu_phong adapterDichVuPhong = new adapter_dich_vu_phong();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_don_phong);
        Intent intent = getIntent();
        idphong = intent.getStringExtra("idphong");
        idhoadon = intent.getStringExtra("id_hoa_don");
        adapter_getchitiettiennghi = new adapter_getchitiettiennghi(idphong);
        adapter_getchitietdichvuphong = new adapter_getchitietdichvuphong(idphong);
        adapterTienNghi = new adapter_tien_nghi();
        adapterDichVuPhong = new adapter_dich_vu_phong();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PRE, MODE_PRIVATE);
        id_staff_auto = sharedPreferences.getString("id_staff", "");
        setControl();
        setEvent();
    }

    private void setControl() {
        rcv_chitiettiennghi = findViewById(R.id.rcv_danhsach_tiennghi);
        rcv_chitietdichvuphong = findViewById(R.id.rcv_danhsach_dichvuphong);
        rcv_sudung_tien_nghi = findViewById(R.id.rcv_sudung_tiennghi);
        rcv_sudung_dichvuphong = findViewById(R.id.rcv_sudung_dichvuphong);
        btn_xacnhan = findViewById(R.id.btn_xacnhan_don);
        btn_back = findViewById(R.id.btn_back_don);
    }

    private void setEvent() {
        rcv_chitiettiennghi.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rcv_chitiettiennghi.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rcv_chitiettiennghi.setAdapter(adapter_getchitiettiennghi);

        rcv_chitietdichvuphong.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rcv_chitietdichvuphong.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rcv_chitietdichvuphong.setAdapter(adapter_getchitietdichvuphong);

        rcv_sudung_tien_nghi.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rcv_sudung_tien_nghi.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rcv_sudung_tien_nghi.setAdapter(adapterTienNghi);

        rcv_sudung_dichvuphong.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rcv_sudung_dichvuphong.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rcv_sudung_dichvuphong.setAdapter(adapterDichVuPhong);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chitiet();
               if (kiemtTra_ThongTin()){
                   onClickAdd_comfort(chi_tiet_hoa_don_tien_nghis, idhoadon);
                   onClickUpdatefacilities(chi_tiet_hoa_don_dich_vu_phongs, idhoadon);
                   capnhatlaocong(id_staff_auto, idhoadon, idphong);
                   capnhattrangthaiphong(idphong, "6");
                   finish();
               }
            }
        });
    }
    private void showErrorMessage(String errorMessage) {
        // Tạo một AlertDialog để hiển thị thông báo lỗi
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Lỗi");
        builder.setMessage(errorMessage);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Xử lý sự kiện khi người dùng nhấn nút OK (nếu cần)
                dialog.dismiss(); // Đóng dialog
            }
        });

        // Hiển thị AlertDialog
        builder.create().show();
    }

    private boolean kiemtTra_ThongTin() {
        // Kiểm tra số lượng tiện nghi
        for (chi_tiet_hoa_don_tien_nghi item : chi_tiet_hoa_don_tien_nghis) {
            String idTienNghi = item.getId_tien_nghi();
            int quantityInBill = item.getSo_luong();

            chi_tiet_tien_nghi correspondingItem = findCorrespondingItemTienNghi(adapter_getchitiettiennghi.getChiTietTN(), idTienNghi);

            if (correspondingItem != null) {
                int quantityInAdapter = correspondingItem.getSo_luong();
                if (quantityInBill > quantityInAdapter) {
                    showErrorMessage("Số lượng kiểm tra vượt quá số lượng có sẵn của "+adapter_getchitiettiennghi.getName(correspondingItem.getId_tien_nghi()));
                   return false;
                }
            }
        }

        // Kiểm tra số lượng dịch vụ phòng nếu tiện nghi hợp lệ
            for (chi_tiet_hoa_don_dich_vu_phong item : chi_tiet_hoa_don_dich_vu_phongs) {
                String idDichVuPhong = item.getId_dich_vu_phong();
                int quantityInBill = item.getSo_luong();

                chi_tiet_dich_vu_phong correspondingItem = findCorrespondingItemDichVuPhong(adapter_getchitietdichvuphong.getChiTietDVP(), idDichVuPhong);

                if (correspondingItem != null) {
                    int quantityInAdapter = correspondingItem.getSo_luong();
                    if (quantityInBill > quantityInAdapter) {
                        showErrorMessage("Số lượng kiểm tra vượt quá số lượng có sẵn của "+adapter_getchitietdichvuphong.getName(correspondingItem.getId_dich_vu_phong()));
                        return false;
                    }
                }
            }
            return true;
    }

    private chi_tiet_tien_nghi findCorrespondingItemTienNghi(ArrayList<chi_tiet_tien_nghi> items, String idTienNghi) {
        for (chi_tiet_tien_nghi item : items) {
            if (item.getId_tien_nghi().equals(idTienNghi)) {
                return item;
            }
        }
        return null;
    }

    private chi_tiet_dich_vu_phong findCorrespondingItemDichVuPhong(ArrayList<chi_tiet_dich_vu_phong> items, String idDichVuPhong) {
        for (chi_tiet_dich_vu_phong item : items) {
            if (item.getId_dich_vu_phong().equals(idDichVuPhong)) {
                return item;
            }
        }
        return null;
    }


    void capnhattrangthaiphong(String idPhong, String idtrangthai) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/phong/" + idPhong + "/" + "id_trang_thai_phong", idtrangthai);
        databaseReference.updateChildren(childUpdates).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Cập nhật thành công
                Log.e("sửa đúng", "thành công");
            } else {
                // Cập nhật thất bại
                Log.e("sửa sai", "thất bại");
            }
        });
    }

    void capnhatlaocong(String idstaff, String idhoadon, String idPhong) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> childUpdates = new HashMap<>();

        childUpdates.put("/hoa_don/" + idPhong + "/" + idhoadon + "/" + "id_lao_cong", idstaff);

        // Thực hiện cập nhật thông tin
        databaseReference.updateChildren(childUpdates).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Cập nhật thành công
                Log.e("sửa đúng", "thành công");
            } else {
                // Cập nhật thất bại
                Log.e("sửa sai", "thất bại");
            }
        });
    }

    void chitiet() {
        chi_tiet_hoa_don_tien_nghis.clear(); // Xóa danh sách cũ
        chi_tiet_hoa_don_dich_vu_phongs.clear(); // Xóa danh sách cũ

        // Thêm dữ liệu mới vào danh sách sau khi kiểm tra
        // Đảm bảo rằng cthdtn.getSo_luong() và cthddvp.getSo_luong() đã được kiểm tra và hợp lệ
        for (chi_tiet_tien_nghi cthdtn : adapterTienNghi.getChi_tiet_tien_nghis()) {
            chi_tiet_hoa_don_tien_nghis.add(new chi_tiet_hoa_don_tien_nghi(idhoadon, cthdtn.getId_tien_nghi().toString(), cthdtn.getSo_luong()));
        }
        for (chi_tiet_dich_vu_phong cthddvp : adapterDichVuPhong.getChiTietDichVu()) {
            chi_tiet_hoa_don_dich_vu_phongs.add(new chi_tiet_hoa_don_dich_vu_phong(idhoadon, cthddvp.getId_dich_vu_phong(), cthddvp.getSo_luong()));
        }
    }


    private void onClickAdd_comfort(ArrayList<chi_tiet_hoa_don_tien_nghi> comfortList, String idhoadon) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> chilUpdates = new HashMap<>();
        //Tạo các cập nhật cho mỗi chi tiêt tiện nghi
        for (chi_tiet_hoa_don_tien_nghi comfort : comfortList) {
            String comfortID = comfort.getId_tien_nghi();
            if (comfortID != null) {
                // Đường dẫn sẽ là /chi_tiet_tien_nghi/idPhong/key
                Map<String, Object> comfortValues = comfort.toMap();
                chilUpdates.put("/chi_tiet_hoa_don_tien_nghi/" + idhoadon + "/" + comfortID, comfortValues);
            }
        }
        //Thưc hiện cập nhật thông báo
        databaseReference.updateChildren(chilUpdates).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Cập nhật thành công
                //Toast.makeText(Activity_Thong_Tin_Phong.this, "Comfort added successfully", Toast.LENGTH_SHORT).show();
            } else {
                // Cập nhật thất bại
                //Toast.makeText(Activity_Thong_Tin_Phong.this, "Failed to add comfort", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onClickUpdatefacilities(ArrayList<chi_tiet_hoa_don_dich_vu_phong> facilityList, String idhoadon) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> childUpdates = new HashMap<>();

        // Tạo các cập nhật cho mỗi chi tiết tiện nghi
        for (chi_tiet_hoa_don_dich_vu_phong facility : facilityList) {
            String facilityID = facility.getId_dich_vu_phong();
            if (facilityID != null) {
                // Đường dẫn sẽ là /chi_tiet_tien_nghi/idPhong/comfortID
                Map<String, Object> facilityValues = facility.toMap();
                childUpdates.put("/chi_tiet_hoa_don_dich_vu_phong/" + idhoadon + "/" + facilityID, facilityValues);
            }
        }

        // Thực hiện cập nhật thông tin
        databaseReference.updateChildren(childUpdates).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Cập nhật thành công
                Log.e("sửa dịch vụ phòng", "thành công");
            } else {
                // Cập nhật thất bại
                Log.e("sửa dịch vụ phòng", "thất bại");
            }
        });
    }
}