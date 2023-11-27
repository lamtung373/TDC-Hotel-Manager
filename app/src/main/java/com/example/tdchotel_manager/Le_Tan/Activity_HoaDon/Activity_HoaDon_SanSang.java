package com.example.tdchotel_manager.Le_Tan.Activity_HoaDon;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tdchotel_manager.Le_Tan.Activity_Chi_Tiet_Phong;
import com.example.tdchotel_manager.Menu_QuanLy.Adapter_NhanVien.ThemNhanVien;
import com.example.tdchotel_manager.Model.chi_tiet_hoa_don_dich_vu;
import com.example.tdchotel_manager.Model.chi_tiet_tien_nghi;
import com.example.tdchotel_manager.Model.dich_vu;
import com.example.tdchotel_manager.Model.hoa_don;
import com.example.tdchotel_manager.Model.nhan_vien;
import com.example.tdchotel_manager.Model.phong;
import com.example.tdchotel_manager.Model.tien_nghi;
import com.example.tdchotel_manager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Activity_HoaDon_SanSang extends AppCompatActivity {
    TextView tvTenPhong, tvNgayNhanPhong, tvNgayTraPhong, tvTienPhong, tvTienNghi, tvDichVu_a, tvDichVu_b, tvTongHD;
    ImageView imgCCCD_Truoc, imgCCCD_Sau;
    Button btnDatPhong;
    ImageButton btnBack_SS;
    EditText edtHoTenKH, edtSoDTKD;
    private ProgressBar progressBar;
    private View viewBlocking;
    private ImageView currentSelectedImageView;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;

    // Biến cấp lớp để theo dõi trạng thái của imgCCCD_Truoc Drawable
    private boolean isImgCCCD_TruocDrawableChanged = false;
    private boolean isImgCCCD_SauDrawableChanged = false;
    private DatabaseReference mDatabaseRef;
    private StorageReference mStorageRef;
    phong phong;
    String ngayNhan, ngayTra, soLuongKhach, id_account;
    public static String SHARED_PRE = "shared_pre";
    ArrayList<dich_vu> dichVuTheoNguoi, dichVuTheoPhong;
    double tienPhong = 0;

    //TỔNG HOÁ ĐƠN
    double tongHD = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_hoadon_sansang);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PRE, MODE_PRIVATE);
        id_account = sharedPreferences.getString("id_staff", "");
        //Lấy dữ liệu từ màn hình trước
        Intent intent = getIntent();
        phong = (phong) intent.getSerializableExtra("phong");
        ngayNhan = intent.getStringExtra("thoi_gian_nhan");
        ngayTra = intent.getStringExtra("thoi_gian_tra");
        soLuongKhach = intent.getStringExtra("so_luong_khach");
        dichVuTheoNguoi = (ArrayList<dich_vu>) getIntent().getSerializableExtra("dich_vu_theo_nguoi");
        dichVuTheoPhong = (ArrayList<dich_vu>) getIntent().getSerializableExtra("dich_vu_phong");

        setControl();
        setEvent();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("hoa_don");
        mStorageRef = FirebaseStorage.getInstance().getReference("images");
    }

    private void setEvent() {
        //Đổ dữ liệu lên tv
        tvTenPhong.setText("Phòng: " + phong.getTen_phong());
        tvNgayNhanPhong.setText(ngayNhan);
        tvNgayTraPhong.setText(ngayTra);

        try {
            // Chuyển đổi String sang Date
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date dateNhan = sdf.parse(ngayNhan);
            Date dateTra = sdf.parse(ngayTra);

            // Tính số ngày lưu trú
            long diff = dateTra.getTime() - dateNhan.getTime();
            int soNgayLuuTru = (int) (diff / (24 * 60 * 60 * 1000));

            // Hiển thị hoặc sử dụng giá trị tienPhong như mong muốn
            if (phong.getSale() > 0) {
                tienPhong = phong.getSale() * soNgayLuuTru;
                tvTienPhong.setText(MessageFormat.format("{0}đ", tienPhong));
                tongHD += tienPhong;
            } else {
                tienPhong = phong.getGia() * soNgayLuuTru;
                tvTienPhong.setText(MessageFormat.format("{0}đ", tienPhong));
                tongHD += tienPhong;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

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

        String dv_a = "", dv_b = "";
        for (dich_vu dv : dichVuTheoNguoi) {
            if (dv.getSo_luong() > 0) {
                double giaDichVu = dv.getGia_dich_vu();
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                String giaDichVuFormatted = decimalFormat.format(giaDichVu);

                // Tạo chuỗi hiển thị dịch vụ theo người
                dv_a += "• " + dv.getTen_dich_vu() + "\n";
                dv_b += giaDichVuFormatted + "đ x " + dv.getSo_luong() + "\n";
                tongHD += dv.getGia_dich_vu() * dv.getSo_luong();
            }
        }

        for (dich_vu dv : dichVuTheoPhong) {
            if (dv.getSo_luong() > 0) {
                double giaDichVu = dv.getGia_dich_vu();
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                String giaDichVuFormatted = decimalFormat.format(giaDichVu);

                // Tạo chuỗi hiển thị dịch vụ theo phòng
                dv_a += "• " + dv.getTen_dich_vu() + "\n";
                dv_b += giaDichVuFormatted + "đ \n";
                tongHD += dv.getGia_dich_vu();
            }
        }
        tvDichVu_a.setText(dv_a);
        tvDichVu_b.setText(dv_b);

        tvTongHD.setText(MessageFormat.format("{0}đ", tongHD));

        // Lấy ngày hiện tại
        Date ngayHT = new Date();

        // Định dạng ngày theo "dd/MM/yyyy"
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String ngayHienTai = sdf.format(ngayHT);

        imgCCCD_Truoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentSelectedImageView = imgCCCD_Truoc;
                showImagePickDialog();
            }
        });

        imgCCCD_Sau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentSelectedImageView = imgCCCD_Sau;
                showImagePickDialog();
            }
        });

        btnDatPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    viewBlocking.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    // Chỉ lưu ảnh và thông tin nhân viên nếu tất cả dữ liệu đầu vào đã hợp lệ
                    uploadImageToFirebaseStorage();
                }
            }
        });

        btnBack_SS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void uploadImageToFirebaseStorage() {
        uploadSingleImage(imgCCCD_Truoc, new Activity_HoaDon_SanSang.OnImageUploadedListener() {
            @Override
            public void onImageUploaded(String cccdTruocUrl) {
                uploadSingleImage(imgCCCD_Sau, new Activity_HoaDon_SanSang.OnImageUploadedListener() {
                    @Override
                    public void onImageUploaded(String cccdSauUrl) {
                        ArrayList<String> cccdImages = new ArrayList<>();
                        cccdImages.add(cccdTruocUrl);
                        cccdImages.add(cccdSauUrl);
                        luuHoaDonToFirebase(cccdImages);
                    }
                });
            }
        });
    }

    private void luuHoaDonToFirebase(ArrayList<String> cccdImages) {
        String id = mDatabaseRef.push().getKey();
        String id_laocong = "";
        String id_letan = id_account;
        String id_phong = phong.getId_phong();
        String soDienThoai = edtSoDTKD.getText().toString();
        String tenKhachHang = edtHoTenKH.getText().toString();
        String thoiGianCoc = "";
        String thoiGianDuyet = "";
        String thoiGianHuy = "";

        // Lấy thời gian hiện tại
        Date currentTime = new Date();
        // Định dạng thời gian theo "dd/MM/yyyy HH:mm"
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String thoiGianNhan = sdf.format(currentTime);
        String thoiGianNhanPhong = thoiGianNhan;

        String thoiGianThanhToan = "";
        String thoiGianTraPhong = ngayTra;
        double tienCoc = 0;
        double phiDichVu = 0;
        for (dich_vu dv : dichVuTheoNguoi) {
            if (dv.getSo_luong() > 0) {
                phiDichVu += dv.getGia_dich_vu() * dv.getSo_luong();
            }
        }

        for (dich_vu dv : dichVuTheoPhong) {
            if (dv.getSo_luong() > 0) {
                phiDichVu += dv.getGia_dich_vu();
            }
        }

        double phiDichVuPhong = 0;
        double phiTienNghi = 0;

        hoa_don hoaDon = new hoa_don(
                id,
                soDienThoai,
                tenKhachHang,
                id_phong,
                id_letan,
                id_laocong,
                cccdImages,
                tienCoc,
                tienPhong,
                phiDichVu,
                phiDichVuPhong,
                phiTienNghi,
                tongHD,
                thoiGianCoc,
                thoiGianNhanPhong,
                thoiGianTraPhong,
                thoiGianHuy,
                thoiGianThanhToan,
                thoiGianDuyet
        );

        mDatabaseRef.child(id_phong).child(id).setValue(hoaDon).addOnSuccessListener(aVoid -> {
            //Đổi trạng thái phòng sang "Đang sử dụng"
            DatabaseReference dataPhong = FirebaseDatabase.getInstance().getReference("phong");

            dataPhong.child(id_phong).child("id_trang_thai_phong").setValue("4");

            //Thêm chi_tiet_hoa_don_dich_vu
            DatabaseReference dataChiTietHoaDonDichVu = FirebaseDatabase.getInstance().getReference("chi_tiet_hoa_don_dich_vu");
            for (dich_vu dv : dichVuTheoNguoi) {
                chi_tiet_hoa_don_dich_vu chi_tiet_hoa_don_dich_vu = new chi_tiet_hoa_don_dich_vu(
                        dv.getSo_luong(),
                        id,
                        dv.getId_dich_vu()
                );
                dataChiTietHoaDonDichVu.child(id).child(dv.getId_dich_vu()).setValue(chi_tiet_hoa_don_dich_vu);
            }
            for (dich_vu dv : dichVuTheoPhong) {
                chi_tiet_hoa_don_dich_vu chi_tiet_hoa_don_dich_vu = new chi_tiet_hoa_don_dich_vu(
                        dv.getSo_luong(),
                        id,
                        dv.getId_dich_vu()
                );
                dataChiTietHoaDonDichVu.child(id).child(dv.getId_dich_vu()).setValue(chi_tiet_hoa_don_dich_vu);
            }

            Toast.makeText(Activity_HoaDon_SanSang.this, "Đặt phòng thành công!", Toast.LENGTH_SHORT).show();

            viewBlocking.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);

            finish();
        }).addOnFailureListener(e -> {
            Toast.makeText(Activity_HoaDon_SanSang.this, "Lỗi khi đặt phòng!", Toast.LENGTH_SHORT).show();
        });
    }

    private void showImagePickDialog() {
        // Tạo một dialog cho phép người dùng chọn Camera hoặc Thư viện ảnh
        String[] options = {"Camera", "Thư viện ảnh"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn ảnh từ");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    // Camera
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                } else if (which == 1) {
                    // Thư viện ảnh
                    Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhotoIntent, REQUEST_IMAGE_PICK);
                }
            }
        });
        builder.show();
    }

    interface OnImageUploadedListener {
        void onImageUploaded(String imageUrl);
    }

    private void uploadSingleImage(ImageView imageView, Activity_HoaDon_SanSang.OnImageUploadedListener listener) {
        StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + ".jpg");
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = imageView.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = fileReference.putBytes(data);
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                String imageUrl = uri.toString();
                listener.onImageUploaded(imageUrl);
            });
        }).addOnFailureListener(e -> {
            Toast.makeText(Activity_HoaDon_SanSang.this, "Lỗi khi tải ảnh lên!", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && currentSelectedImageView != null) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                currentSelectedImageView.setImageBitmap(imageBitmap);
                if (currentSelectedImageView == imgCCCD_Truoc) {
                    isImgCCCD_TruocDrawableChanged = true;
                }
                if (currentSelectedImageView == imgCCCD_Sau) {
                    isImgCCCD_SauDrawableChanged = true;
                }
            } else if (requestCode == REQUEST_IMAGE_PICK) {
                Uri imageUri = data.getData();
                currentSelectedImageView.setImageURI(imageUri);
                if (currentSelectedImageView == imgCCCD_Truoc) {
                    isImgCCCD_TruocDrawableChanged = true;
                }
                if (currentSelectedImageView == imgCCCD_Sau) {
                    isImgCCCD_SauDrawableChanged = true;
                }
            }
        }
    }

    private boolean validateInput() {
        if (edtHoTenKH.getText().toString().trim().isEmpty()) {
            edtHoTenKH.setError("Họ tên không được để trống");
            return false;
        }
        if (edtSoDTKD.getText().toString().trim().isEmpty()) {
            edtSoDTKD.setError("Số điện thoại không được để trống");
            return false;
        }
        if (!isImgCCCD_TruocDrawableChanged || !isImgCCCD_SauDrawableChanged) {
            Toast.makeText(this, "Cần phải cung cấp đầy đủ hình ảnh!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void setControl() {
        tvTenPhong = findViewById(R.id.tvTenPhong_SS);
        tvNgayNhanPhong = findViewById(R.id.tvNgayNhanPhong_SS);
        tvNgayTraPhong = findViewById(R.id.tvNgayTraPhong_SS);
        tvTienPhong = findViewById(R.id.tvTienPhong_SS);
        tvTienNghi = findViewById(R.id.tvTienNghi_SS);
        tvDichVu_a = findViewById(R.id.tvDichVu_a_SS);
        tvDichVu_b = findViewById(R.id.tvDichVu_b_SS);
        tvTongHD = findViewById(R.id.tvTongHD_SS);
        imgCCCD_Truoc = findViewById(R.id.imgCCCD_Truoc_SS);
        imgCCCD_Sau = findViewById(R.id.imgCCCD_Sau_SS);
        btnDatPhong = findViewById(R.id.btnDatPhong_SS);
        edtHoTenKH = findViewById(R.id.edtHoTenKH_SS);
        edtSoDTKD = findViewById(R.id.edtSoDTKH_SS);
        btnBack_SS = findViewById(R.id.btn_Back_SS);
        viewBlocking = findViewById(R.id.viewBlocking_SS);
        progressBar = findViewById(R.id.progressBar_SS);
    }
}
