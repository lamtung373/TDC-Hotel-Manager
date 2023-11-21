package com.example.tdchotel_manager.Le_Tan.Activity_HoaDon;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tdchotel_manager.Model.chi_tiet_hoa_don_dich_vu;
import com.example.tdchotel_manager.Model.chi_tiet_tien_nghi;
import com.example.tdchotel_manager.Model.dich_vu;
import com.example.tdchotel_manager.Model.hoa_don;
import com.example.tdchotel_manager.Model.phong;
import com.example.tdchotel_manager.Model.tien_nghi;
import com.example.tdchotel_manager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Activity_HoaDon_DuyetCoc extends AppCompatActivity {
    TextView tvMaHD, tvTenKH, tvSDT, tvTenPhong, tvNgayNhanPhong, tvNgayTraPhong, tvTienPhong, tvTienNghi, tvDichVu_a, tvDichVu_b, tvTongHD, tvTienCoc;
    String tien_coc, ngayNhan, ngayTra;
    Button btnXacNhan;
    hoa_don hoadon;
    ImageButton btnBack_SS;
    phong phong;
    DatabaseReference dataPhong, dataHoaDon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_hoadon_duyetcoc);

        //Lấy dữ liệu từ màn hình trước
        Intent intent = getIntent();
        hoadon = (hoa_don) intent.getSerializableExtra("hoa_don");
        tien_coc = intent.getStringExtra("tien_coc");

        dataPhong = FirebaseDatabase.getInstance().getReference("phong").child(hoadon.getId_phong());

        setControl();
        setEvent();
    }

    private void setEvent() {
        //Đổ dữ liệu lên XML
        tvMaHD.setText("Mã HĐ: " + hoadon.getId_hoa_don());
        tvTenKH.setText("Khách hàng: " + hoadon.getTen_khach_hang());
        tvSDT.setText("Số điện thoại: " + hoadon.getSo_dien_thoai());

        dataPhong.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    phong = snapshot.getValue(phong.class);
                    tvTenPhong.setText("Phòng: " + phong.getTen_phong());
                    layDuLieuTienNghi(phong);

                    //Lấy dữ liệu hoá đơn
                    dataHoaDon = FirebaseDatabase.getInstance().getReference("hoa_don").child(phong.getId_phong()).child(hoadon.getId_hoa_don());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi nếu có
            }
        });

        ngayNhan = hoadon.getThoi_gian_nhan_phong();
        ngayTra = hoadon.getThoi_gian_tra_phong();
        tvNgayNhanPhong.setText(ngayNhan);
        tvNgayTraPhong.setText(ngayTra);
        tvTienPhong.setText(MessageFormat.format("{0}đ", hoadon.getTien_phong()));
        layDuLieuDichVu(hoadon);

        double tongHD = hoadon.getTong_thanh_toan();
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        String tongHDFormatted = decimalFormat.format(tongHD);
        tvTongHD.setText(tongHDFormatted + "đ");

        try {
            double tienCoc = Double.parseDouble(tien_coc);
            DecimalFormat decimalFormatt = new DecimalFormat("###,###,###");
            String tienCocFormatted = decimalFormatt.format(tienCoc);
            tvTienCoc.setText(tienCocFormatted + "đ");
        } catch (NumberFormatException e) {
        }

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String nowDate = dateFormat.format(date);
                dataHoaDon.child("thoi_gian_duyet").setValue(nowDate);
                double tienCoc = Double.parseDouble(tien_coc);
                dataHoaDon.child("tien_coc").setValue(tienCoc);

                finish();
            }
        });
    }

    private void layDuLieuDichVu(hoa_don hoadon) {
        DatabaseReference reference_chi_tiet_hoa_don_dich_vu = FirebaseDatabase.getInstance().getReference("chi_tiet_hoa_don_dich_vu");
        DatabaseReference reference_dich_vu = FirebaseDatabase.getInstance().getReference("dich_vu");
        reference_chi_tiet_hoa_don_dich_vu.child(hoadon.getId_hoa_don()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String dichvu_a[] = {""};
                String dichvu_b[] = {""};
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    chi_tiet_hoa_don_dich_vu chi_tiet_hoa_don_dich_vu = dataSnapshot.getValue(com.example.tdchotel_manager.Model.chi_tiet_hoa_don_dich_vu.class);
                    if (chi_tiet_hoa_don_dich_vu.getSo_luong() != 0) {
                        reference_dich_vu.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    dich_vu dich_vu = dataSnapshot.getValue(dich_vu.class);
                                    if (chi_tiet_hoa_don_dich_vu.getId_dich_vu().equals(dich_vu.getId_dich_vu())) {
                                        dichvu_a[0] += "• " + dich_vu.getTen_dich_vu() + "\n";

                                        double giaDichVu = dich_vu.getGia_dich_vu();
                                        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                                        String giaDichVuFormatted = decimalFormat.format(giaDichVu);

                                        dichvu_b[0] += (giaDichVuFormatted + "đ x " + chi_tiet_hoa_don_dich_vu.getSo_luong() + "\n");

                                        tvDichVu_a.setText(dichvu_a[0]);
                                        tvDichVu_b.setText(dichvu_b[0]);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void layDuLieuTienNghi(phong phong) {
        DatabaseReference reference_chi_tiet_tien_nghi = FirebaseDatabase.getInstance().getReference("chi_tiet_tien_nghi");
        DatabaseReference reference_tien_nghi = FirebaseDatabase.getInstance().getReference("tien_nghi");
        reference_chi_tiet_tien_nghi.child(phong.getId_phong()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String tiennghi[] = {""};
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    chi_tiet_tien_nghi chiTietTienNghi = dataSnapshot.getValue(chi_tiet_tien_nghi.class);
                    if (chiTietTienNghi.getSo_luong() != 0) {
                        reference_tien_nghi.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    tien_nghi tien_nghi = dataSnapshot.getValue(tien_nghi.class);
                                    if (chiTietTienNghi.getId_tien_nghi().equals(tien_nghi.getId_tien_nghi())) {
                                        tiennghi[0] += "• " + tien_nghi.getTen_tien_nghi() + " x " + chiTietTienNghi.getSo_luong() + "\n";
                                        tvTienNghi.setText(tiennghi[0]);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void setControl() {
        tvMaHD = findViewById(R.id.tvMaHD_DC);
        tvTenKH = findViewById(R.id.tvTenKhachHang_DC);
        tvSDT = findViewById(R.id.tvSDT_DC);
        tvTenPhong = findViewById(R.id.tvTenPhong_DC);
        tvNgayNhanPhong = findViewById(R.id.tvNgayNhanPhong_DC);
        tvNgayTraPhong = findViewById(R.id.tvNgayTraPhong_DC);
        tvTienPhong = findViewById(R.id.tvTienPhong_DC);
        tvTienNghi = findViewById(R.id.tvTienNghi_DC);
        tvDichVu_a = findViewById(R.id.tvDichVu_a_DC);
        tvDichVu_b = findViewById(R.id.tvDichVu_b_DC);
        tvTongHD = findViewById(R.id.tvTongHD_DC);
        tvTienCoc = findViewById(R.id.tvTienCoc_DC);
        btnBack_SS = findViewById(R.id.btn_Back_DC);
        btnXacNhan = findViewById(R.id.btnXacNhan_DC);
    }
}
