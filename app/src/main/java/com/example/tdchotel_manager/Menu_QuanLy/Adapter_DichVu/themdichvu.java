package com.example.tdchotel_manager.Menu_QuanLy.Adapter_DichVu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Toast;

import com.example.tdchotel_manager.Menu_QuanLy.Adapter_NhanVien.ThemNhanVien;
import com.example.tdchotel_manager.Model.dich_vu;
import com.example.tdchotel_manager.Model.dich_vu_phong;
import com.example.tdchotel_manager.Model.nhan_vien;
import com.example.tdchotel_manager.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class themdichvu extends AppCompatActivity {
    EditText edtTenDv, edtGiaDv;
    Button btnLuu;
    RadioGroup loaiDichVu;
    RadioButton rdNguoi, rdPhong;
    ImageButton imgButtonquaylai;
    ImageView imageView;

    private DatabaseReference mDatabaseRef;
    private StorageReference mStorageRef;
    private ImageView currentSelectedImageView;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_themdichvu);
        setControl();
        setEvent();


        mDatabaseRef = FirebaseDatabase.getInstance().getReference("dich_vu");
        mStorageRef = FirebaseStorage.getInstance().getReference("images");
    }

    private void setEvent() {
        imgButtonquaylai = findViewById(R.id.imvbQuaylai);
        imgButtonquaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInput()) {
                    uploadImageToFirebaseStorage();

                }
            }

        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentSelectedImageView = imageView;
                showImagePickDialog();
            }
        });

    }
    private void uploadImageToFirebaseStorage() {
        if (imageView.getDrawable() != null) {
            uploadSingleImage(imageView, new OnImageUploadedListener() {
                @Override
                public void onImageUploaded(String imageUrl) {
                    saveEmployeeToFirebase(imageUrl);
                }
                });
            } else {
                saveEmployeeToFirebase("");
            }
        }

    private void uploadSingleImage(ImageView imageView, OnImageUploadedListener onImageUploadedListener) {
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
                onImageUploadedListener.onImageUploaded(imageUrl);
            });
        }).addOnFailureListener(e -> {
            Toast.makeText(themdichvu.this, "Lỗi khi tải ảnh lên!", Toast.LENGTH_SHORT).show();
        });
    }
    private void saveEmployeeToFirebase(String imageUrl) {
        String idDichVu = mDatabaseRef.push().getKey();
        String id_Loai_Dich_Vu = rdNguoi.isChecked() ? "2" : "1";
        String tenDichVu = edtTenDv.getText().toString();
        int giaDichVu = Integer.parseInt(edtGiaDv.getText().toString());

        dich_vu employee = new dich_vu(
              id_Loai_Dich_Vu,
                giaDichVu,
                idDichVu,
                tenDichVu,
                imageUrl

        );

        mDatabaseRef.child(idDichVu).setValue(employee).addOnSuccessListener(aVoid -> {
            Toast.makeText(themdichvu.this, "Thêm dịch vụ thành công", Toast.LENGTH_SHORT).show();
            finish();
        }).addOnFailureListener(e -> {
            Toast.makeText(themdichvu.this, "Lỗi khi thêm dịch vụ", Toast.LENGTH_SHORT).show();
        });
    }



    interface OnImageUploadedListener {
        void onImageUploaded(String imageUrl);
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
    private boolean validateInput() {
        if (edtTenDv.getText().toString().trim().isEmpty()) {
            edtTenDv.setError("Tên dịch vụ không được để trống");
            return false;
        }
        if (edtGiaDv.getText().toString().trim().isEmpty()) {
            edtGiaDv.setError("Giá dịch vụ không được để trống");
            return false;
        }
        if (!rdNguoi.isChecked() && !rdPhong.isChecked()) {
            // Nếu không có RadioButton nào được chọn, hiển thị thông báo
            Toast.makeText(this, "Vui lòng chọn loại dịch vụ", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void setControl() {
        edtTenDv = findViewById(R.id.edtTenDv);
        edtGiaDv = findViewById(R.id.edtGiaDv);
        btnLuu = findViewById(R.id.btnLuu);
        loaiDichVu = findViewById(R.id.rdgLoaiphong);
        rdNguoi = findViewById(R.id.rdNguoi);
        rdPhong = findViewById(R.id.rdPhong);
        imageView = findViewById(R.id.imgthemDV);

    }
}