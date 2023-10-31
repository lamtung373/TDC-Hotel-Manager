package com.example.tdchotel_manager.Menu_QuanLy.Adapter_NhanVien;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tdchotel_manager.Model.nhan_vien;
import com.example.tdchotel_manager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ThongTinNhanVien extends AppCompatActivity {
    ImageButton btnQuayLai;
    TextView tvHoTen;
    EditText edtTenDangNhap, edtMatKhau, edtSoDienThoai, edtLuong;
    ImageView imgNV, imgCCCD_Truoc, imgCCCD_Sau;
    RadioGroup radioGroup;
    RadioButton radioButtonLeTan, radioButtonLaoCong;
    Button btnXoa, btnSua;
    private ImageView currentSelectedImageView;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thongtinnhanvien);
        setControl();
        setEvent();
    }


    private void setEvent() {
        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // Kết thúc Activity này và quay lại Activity trước đó
            }
        });

        imgNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentSelectedImageView = imgNV;
                showImagePickDialog();
            }
        });

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

        String nhanVienId = getIntent().getStringExtra("nhanVienId");

        if (nhanVienId != null) {
            // Truy vấn dữ liệu từ Firebase dựa trên nhanVienId
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("nhan_vien").child(nhanVienId);
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    nhan_vien nv = dataSnapshot.getValue(nhan_vien.class);
                    if (nv != null) {
                        tvHoTen.setText(nv.getTen_nhan_vien());
                        edtTenDangNhap.setText(nv.getUsername());
                        edtMatKhau.setText(nv.getPassword());
                        edtSoDienThoai.setText(nv.getSo_dien_thoai());
                        edtLuong.setText(String.valueOf(nv.getLuong()));

                        String chucVuId = nv.getId_chuc_vu();
                        if ("1".equals(chucVuId)) {
                            radioGroup.check(R.id.radioButtonLaoCong_TTNV);
                        } else if ("2".equals(chucVuId)) {
                            radioGroup.check(R.id.radioButtonLeTan_TTNV);
                        }

                        // Hiển thị ảnh từ Firebase lên ImageView
                        Picasso.get().load(nv.getAnh_nhan_vien()).into(imgNV);
                        // Tại đây, tôi giả định bạn có các trường tương ứng trong model nhan_vien cho ảnh CCCD trước và sau
                        Picasso.get().load(nv.getAnh_CCCD_Truoc()).into(imgCCCD_Truoc);
                        Picasso.get().load(nv.getAnh_CCCD_Sau()).into(imgCCCD_Sau);
                    }
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Xử lý khi có lỗi
                    Toast.makeText(ThongTinNhanVien.this, "Lỗi: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xoá nhân viên từ Firebase
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("nhan_vien").child(nhanVienId);
                ref.removeValue();

                // Thông báo và quay lại màn hình trước
                Toast.makeText(ThongTinNhanVien.this, "Đã xoá nhân viên", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy dữ liệu từ các trường
                String tenDangNhap = edtTenDangNhap.getText().toString();
                String matKhau = edtMatKhau.getText().toString();
                String soDienThoai = edtSoDienThoai.getText().toString();
                String luongText = edtLuong.getText().toString();

                if (tenDangNhap.isEmpty() || matKhau.isEmpty() || soDienThoai.isEmpty() || luongText.isEmpty()) {
                    Toast.makeText(ThongTinNhanVien.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                double luongValue;
                try {
                    luongValue = Double.parseDouble(luongText);
                } catch (NumberFormatException e) {
                    Toast.makeText(ThongTinNhanVien.this, "Lương không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }
                String idChucVu = radioButtonLaoCong.isChecked() ? "1" : "2";

                // Cập nhật dữ liệu trên Firebase
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("nhan_vien").child(nhanVienId);
                ref.child("username").setValue(tenDangNhap);
                ref.child("password").setValue(matKhau);
                ref.child("so_dien_thoai").setValue(soDienThoai);
                ref.child("luong").setValue(luongValue);
                ref.child("id_chuc_vu").setValue(idChucVu);

                // Thông báo và quay lại màn hình trước
                Toast.makeText(ThongTinNhanVien.this, "Đã cập nhật thông tin nhân viên", Toast.LENGTH_SHORT).show();
                finish();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && currentSelectedImageView != null) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                currentSelectedImageView.setImageBitmap(imageBitmap);
            } else if (requestCode == REQUEST_IMAGE_PICK) {
                Uri imageUri = data.getData();
                currentSelectedImageView.setImageURI(imageUri);
            }
        }
    }

    private void setControl() {
        btnQuayLai = findViewById(R.id.btnQuayLai_TTNV);
        tvHoTen = findViewById(R.id.tvHoTen_TTNV);
        imgNV = findViewById(R.id.imgNV);
        imgCCCD_Truoc = findViewById(R.id.imgCCCD_Truoc_TTNV);
        imgCCCD_Sau = findViewById(R.id.imgCCCD_Sau_TTNV);
        edtTenDangNhap = findViewById(R.id.edtTenDangNhap_TTNV);
        edtMatKhau = findViewById(R.id.edtMatKhau_TTNV);
        edtSoDienThoai = findViewById(R.id.edtSoDienThoai_TTNV);
        edtLuong = findViewById(R.id.edtLuong_TTNV);
        radioGroup = findViewById(R.id.radioGroup_TTNV);
        radioButtonLeTan = findViewById(R.id.radioButtonLeTan_TTNV);
        radioButtonLaoCong = findViewById(R.id.radioButtonLaoCong_TTNV);
        btnXoa = findViewById(R.id.btnXoa_TTNV);
        btnSua = findViewById(R.id.btnSua_TTNV);
    }

}
