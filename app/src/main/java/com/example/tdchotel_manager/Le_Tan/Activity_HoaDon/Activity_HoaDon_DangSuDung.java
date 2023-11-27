package com.example.tdchotel_manager.Le_Tan.Activity_HoaDon;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tdchotel_manager.Model.chi_tiet_hoa_don_dich_vu;
import com.example.tdchotel_manager.Model.chi_tiet_hoa_don_dich_vu_phong;
import com.example.tdchotel_manager.Model.chi_tiet_tien_nghi;
import com.example.tdchotel_manager.Model.dich_vu;
import com.example.tdchotel_manager.Model.dich_vu_phong;
import com.example.tdchotel_manager.Model.hoa_don;
import com.example.tdchotel_manager.Model.phong;
import com.example.tdchotel_manager.Model.tien_nghi;
import com.example.tdchotel_manager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Activity_HoaDon_DangSuDung extends AppCompatActivity {
    TextView tvMaHD, tvTenKH, tvSDT, tvTenPhong, tvNgayNhanPhong, tvNgayTraPhong, tvTienPhong, tvTienNghi, tvDVPhong_a, tvDVPhong_b, tvDichVu_a, tvDichVu_b, tvTongHD, tvTienCoc;
    LinearLayout layoutPB_a, layoutPB_b, layoutTienIch, layoutTienCoc;
    Button btnXacNhan;
    ImageButton btnBack;
    hoa_don hoadon;
    String ngayNhan, ngayTra;
    double tongHD;
    boolean done_a = false, done_b = false;
    private int firebaseRequestCount = 0;
    private final int TOTAL_FIREBASE_REQUESTS = 3;
    double tongDV, tongDVP, tongTN;
    phong phong;
    DatabaseReference dataPhong, dataHoaDon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_hoadon_dangsudung);

        //Lấy dữ liệu từ màn hình trước
        Intent intent = getIntent();
        hoadon = (hoa_don) intent.getSerializableExtra("hoa_don");

        dataPhong = FirebaseDatabase.getInstance().getReference("phong").child(hoadon.getId_phong());

        setControl();
        setEvent();
    }

    private void setEvent() {
        //Đổ dữ liệu lên tv
        tvMaHD.setText("Mã HĐ: " + hoadon.getId_hoa_don());
        tvTenKH.setText("Khách hàng: " + hoadon.getTen_khach_hang());
        tvSDT.setText("Số điện thoại: " + hoadon.getSo_dien_thoai());

        dataPhong.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    phong = snapshot.getValue(phong.class);
                    tvTenPhong.setText("Phòng: " + phong.getTen_phong());

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
        layDuLieuTienNghi(hoadon);
        layDuLieuDichVuPhong(hoadon);
        if (hoadon.getTien_coc() != 0) {
            layoutTienCoc.setVisibility(View.VISIBLE);
            tvTienCoc.setText(MessageFormat.format("{0}đ", hoadon.getTien_coc()));
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String nowDate = dateFormat.format(date);
                hoadon.setThoi_gian_thanh_toan(nowDate);
                hoadon.setTong_phi_dich_vu(tongDV);
                hoadon.setTong_phi_dich_vu_phong(tongDVP);
                hoadon.setTong_phi_tien_nghi(tongTN);
                hoadon.setTong_thanh_toan(tongHD);
                dataHoaDon.setValue(hoadon);
                //Chỉ thêm lượt thuê khi đã thanh toán...
                dataPhong.child("luot_thue").setValue(phong.getLuot_thue() + 1);
                dataPhong.child("id_trang_thai_phong").setValue("1");

                Toast.makeText(Activity_HoaDon_DangSuDung.this, "Thanh toán thành công!", Toast.LENGTH_SHORT).show();

                finish();
            }
        });
    }

    private void layDuLieuDichVuPhong(hoa_don hoadon) {
        DatabaseReference reference_chi_tiet_hoa_don_dich_vu_phong = FirebaseDatabase.getInstance().getReference("chi_tiet_hoa_don_dich_vu_phong");
        DatabaseReference reference_dich_vu_phong = FirebaseDatabase.getInstance().getReference("dich_vu_phong");

        reference_chi_tiet_hoa_don_dich_vu_phong.child(hoadon.getId_hoa_don()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    // Trường hợp không có dữ liệu
                    return;
                }
                layoutPB_a.setVisibility(View.GONE);
                layoutTienIch.setVisibility(View.VISIBLE);
                boolean allZero = true;
                String dichvu_a[] = {""};
                String dichvu_b[] = {""};
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    chi_tiet_hoa_don_dich_vu_phong chi_tiet_hoa_don_dich_vu_phong = dataSnapshot.getValue(com.example.tdchotel_manager.Model.chi_tiet_hoa_don_dich_vu_phong.class);
                    if (chi_tiet_hoa_don_dich_vu_phong.getSo_luong() != 0) {
                        allZero = false;
                        reference_dich_vu_phong.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    dich_vu_phong dich_vu_phong = dataSnapshot.getValue(com.example.tdchotel_manager.Model.dich_vu_phong.class);
                                    if (chi_tiet_hoa_don_dich_vu_phong.getId_dich_vu_phong().equals(dich_vu_phong.getId_dich_vu_phong())) {
                                        dichvu_a[0] += "• " + dich_vu_phong.getTen_dich_vu_phong() + "\n";

                                        double giaDichVu = dich_vu_phong.getGia_dich_vu_phong();
                                        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                                        String giaDichVuFormatted = decimalFormat.format(giaDichVu);

                                        dichvu_b[0] += (giaDichVuFormatted + "đ x " + chi_tiet_hoa_don_dich_vu_phong.getSo_luong() + "\n");

                                        tvDVPhong_a.setText(dichvu_a[0]);
                                        tvDVPhong_b.setText(dichvu_b[0]);

                                        tongDVP += giaDichVu * chi_tiet_hoa_don_dich_vu_phong.getSo_luong();
                                    }
                                }
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Cập nhật UI và tính toán
                                        checkAndActivateButton();
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
                if (allZero) {
                    tvDVPhong_a.setText("• Không sử dụng dịch vụ phòng.");
                }
                firebaseRequestCount++;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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

                                        tongDV += giaDichVu * chi_tiet_hoa_don_dich_vu.getSo_luong();
                                    }
                                }
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Cập nhật UI và tính toán
                                        checkAndActivateButton();
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
                firebaseRequestCount++;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void layDuLieuTienNghi(hoa_don hoadon) {
        DatabaseReference reference_chi_tiet_hoa_don_tien_nghi = FirebaseDatabase.getInstance().getReference("chi_tiet_hoa_don_tien_nghi");
        DatabaseReference reference_tien_nghi = FirebaseDatabase.getInstance().getReference("tien_nghi");
        reference_chi_tiet_hoa_don_tien_nghi.child(hoadon.getId_hoa_don()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    // Trường hợp không có dữ liệu
                    return;
                }
                layoutPB_b.setVisibility(View.GONE);
                tvTienNghi.setVisibility(View.VISIBLE);
                boolean allZero = true;
                String tiennghi[] = {""};
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    chi_tiet_tien_nghi chiTietTienNghi = dataSnapshot.getValue(chi_tiet_tien_nghi.class);
                    if (chiTietTienNghi.getSo_luong() != 0) {
                        allZero = false;
                        reference_tien_nghi.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    tien_nghi tien_nghi = dataSnapshot.getValue(tien_nghi.class);
                                    if (chiTietTienNghi.getId_tien_nghi().equals(tien_nghi.getId_tien_nghi())) {
                                        tiennghi[0] += "• " + tien_nghi.getTen_tien_nghi() + " x " + chiTietTienNghi.getSo_luong() + "\n";
                                        tvTienNghi.setText(tiennghi[0]);
                                        tongTN += tien_nghi.getGia_tien_nghi() * chiTietTienNghi.getSo_luong();
                                    }
                                }
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Cập nhật UI và tính toán
                                        checkAndActivateButton();
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                }
                if (allZero) {
                    tvTienNghi.setText("• Không có tiện nghi bị hư hại.");
                }
                firebaseRequestCount++;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void checkAndActivateButton() {
        if (firebaseRequestCount == TOTAL_FIREBASE_REQUESTS) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    btnXacNhan.setEnabled(true);
                    btnXacNhan.setBackgroundResource(R.drawable.border_button_a_nhanvien);
                    tongHD = hoadon.getTien_phong() + tongDV + tongDVP + tongTN;
                    tvTongHD.setText(MessageFormat.format("{0}đ", tongHD));
                }
            });
        }
    }

    private void setControl() {
        btnBack = findViewById(R.id.btn_Back_TT);
        tvMaHD = findViewById(R.id.tvMaHD_TT);
        tvTenKH = findViewById(R.id.tvTenKhachHang_TT);
        tvSDT = findViewById(R.id.tvSDT_TT);
        tvTenPhong = findViewById(R.id.tvTenPhong_TT);
        tvNgayNhanPhong = findViewById(R.id.tvNgayNhanPhong_TT);
        tvNgayTraPhong = findViewById(R.id.tvNgayTraPhong_TT);
        tvTienPhong = findViewById(R.id.tvTienPhong_TT);
        tvTienNghi = findViewById(R.id.tvTienNghi_TT);
        layoutPB_a = findViewById(R.id.layoutProgressBar_a_TT);
        layoutPB_b = findViewById(R.id.layoutProgressBar_b_TT);
        layoutTienIch = findViewById(R.id.layoutTienIch_TT);
        layoutTienCoc = findViewById(R.id.layoutTienCoc_TT);
        tvDVPhong_a = findViewById(R.id.tvDVPhong_a_TT);
        tvDVPhong_b = findViewById(R.id.tvDVPhong_b_TT);
        tvDichVu_a = findViewById(R.id.tvDichVu_a_TT);
        tvDichVu_b = findViewById(R.id.tvDichVu_b_TT);
        tvTongHD = findViewById(R.id.tvTongHD_TT);
        tvTienCoc = findViewById(R.id.tvTienCoc_TT);
        btnXacNhan = findViewById(R.id.btnXacNhan_TT);
    }
}