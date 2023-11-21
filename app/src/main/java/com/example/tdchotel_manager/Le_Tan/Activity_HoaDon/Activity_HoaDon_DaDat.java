package com.example.tdchotel_manager.Le_Tan.Activity_HoaDon;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Activity_HoaDon_DaDat extends AppCompatActivity {
    TextView tvMaHD, tvSDT, tvTenPhong, tvNgayNhanPhong, tvNgayTraPhong, tvTienPhong, tvTienNghi, tvDichVu_a, tvDichVu_b, tvTongHD, tvTienCoc;
    EditText edtTenKH;
    ImageView imgCCCD_Truoc, imgCCCD_Sau;
    String tien_coc, ngayNhan, ngayTra;
    Button btnNhanPhong;
    hoa_don hoadon;
    phong phong;
    DatabaseReference dataPhong, dataHoaDon;
    private boolean isImgCCCD_TruocDrawableChanged = false;
    private boolean isImgCCCD_SauDrawableChanged = false;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;
    private ImageView currentSelectedImageView;
    private DatabaseReference mDatabaseRef;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_hoadon_dadat);

        //Lấy dữ liệu từ màn hình trước
        Intent intent = getIntent();
        hoadon = (hoa_don) intent.getSerializableExtra("hoa_don");

        dataPhong = FirebaseDatabase.getInstance().getReference("phong").child(hoadon.getId_phong());

        setControl();
        setEvent();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("hoa_don");
        mStorageRef = FirebaseStorage.getInstance().getReference("images");
    }

    private void setEvent() {
        //Đổ dữ liệu lên XML
        tvMaHD.setText(hoadon.getId_hoa_don());
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

        double tienCoc = hoadon.getTien_coc();
        DecimalFormat decimalFormatt = new DecimalFormat("###,###,###");
        String tienCocFormatted = decimalFormatt.format(tienCoc);
        tvTienCoc.setText(tienCocFormatted + "đ");

        edtTenKH.setText(hoadon.getTen_khach_hang());

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

        btnNhanPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImageToFirebaseStorage();
            }
        });
    }

    private void uploadImageToFirebaseStorage(){
        uploadSingleImage(imgCCCD_Truoc, new OnImageUploadedListener() {
            @Override
            public void onImageUploaded(String cccdTruocUrl) {
                uploadSingleImage(imgCCCD_Sau, new OnImageUploadedListener() {
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
        hoadon.setTen_khach_hang(edtTenKH.getText().toString());
        hoadon.setCCCD(cccdImages);
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String nowDate = dateFormat.format(date);
        hoadon.setThoi_gian_nhan_phong(nowDate);

        mDatabaseRef.child(hoadon.getId_phong()).child(hoadon.getId_hoa_don()).setValue(hoadon, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(Activity_HoaDon_DaDat.this, "Nhận phòng thành công!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        //Đổi trạng thái phòng sang "Đang sử dụng"
        DatabaseReference dataPhong = FirebaseDatabase.getInstance().getReference("phong");
        dataPhong.child(hoadon.getId_phong()).child("id_trang_thai_phong").setValue("4");
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

    private void uploadSingleImage(ImageView imageView, Activity_HoaDon_DaDat.OnImageUploadedListener listener) {
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
            Toast.makeText(Activity_HoaDon_DaDat.this, "Lỗi khi tải ảnh lên!", Toast.LENGTH_SHORT).show();
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
        if (edtTenKH.getText().toString().trim().isEmpty()) {
            edtTenKH.setError("Họ tên không được để trống");
            return false;
        }
        if (!isImgCCCD_TruocDrawableChanged || !isImgCCCD_SauDrawableChanged) {
            Toast.makeText(this, "Cần phải cung cấp đầy đủ hình ảnh!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void setControl() {
        tvMaHD = findViewById(R.id.tvMaHD_NP);
        tvSDT = findViewById(R.id.tvSDT_NP);
        tvTenPhong = findViewById(R.id.tvTenPhong_NP);
        tvNgayNhanPhong = findViewById(R.id.tvNgayNhanPhong_NP);
        tvNgayTraPhong = findViewById(R.id.tvNgayTraPhong_NP);
        tvTienPhong = findViewById(R.id.tvTienPhong_NP);
        tvTienNghi = findViewById(R.id.tvTienNghi_NP);
        tvDichVu_a = findViewById(R.id.tvDichVu_a_NP);
        tvDichVu_b = findViewById(R.id.tvDichVu_b_NP);
        tvTongHD = findViewById(R.id.tvTongHD_NP);
        tvTienCoc = findViewById(R.id.tvTienCoc_NP);
        edtTenKH = findViewById(R.id.edtHoTenKH_NP);
        imgCCCD_Truoc = findViewById(R.id.imgCCCD_Truoc_NP);
        imgCCCD_Sau = findViewById(R.id.imgCCCD_Sau_NP);
        btnNhanPhong = findViewById(R.id.btnNhanPhong_NP);
    }
}
