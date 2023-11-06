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
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tdchotel_manager.Model.nhan_vien;
import com.example.tdchotel_manager.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ThemNhanVien extends AppCompatActivity {
    ImageButton btnQuayLai;
    EditText edtHoTen, edtTenDangNhap, edtMatKhau, edtSoDienThoai, edtLuong;
    ImageView imgNV, imgCCCD_Truoc, imgCCCD_Sau;
    RadioGroup radioGroup;
    Button btnLuu;

    // Firebase references
    private DatabaseReference mDatabaseRef;
    private StorageReference mStorageRef;

    // ...
    private ImageView currentSelectedImageView;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_themnhanvien);
        setControl();
        setEvent();

        // Initialize Firebase references
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("nhan_vien");
        mStorageRef = FirebaseStorage.getInstance().getReference("images");
    }

    private void setEvent() {
        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // Kết thúc Activity này và quay lại Activity trước đó
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lưu ảnh lên Firebase Storage
                uploadImageToFirebaseStorage();
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

    }

    private void uploadImageToFirebaseStorage() {
        if (imgNV.getDrawable() != null) {
            uploadSingleImage(imgNV, new OnImageUploadedListener() {
                @Override
                public void onImageUploaded(String imageUrl) {
                    uploadSingleImage(imgCCCD_Truoc, new OnImageUploadedListener() {
                        @Override
                        public void onImageUploaded(String cccdTruocUrl) {
                            uploadSingleImage(imgCCCD_Sau, new OnImageUploadedListener() {
                                @Override
                                public void onImageUploaded(String cccdSauUrl) {
                                    ArrayList<String> cccdImages = new ArrayList<>();
                                    cccdImages.add(cccdTruocUrl);
                                    cccdImages.add(cccdSauUrl);
                                    saveEmployeeToFirebase(imageUrl, cccdImages);
                                }
                            });
                        }
                    });
                }
            });
        } else {
            saveEmployeeToFirebase("", new ArrayList<>());
        }
    }

    private void uploadSingleImage(ImageView imageView, OnImageUploadedListener listener) {
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
            Toast.makeText(ThemNhanVien.this, "Lỗi khi tải ảnh lên!", Toast.LENGTH_SHORT).show();
        });
    }

    private void saveEmployeeToFirebase(String imageUrl, ArrayList<String> cccdImages) {
        String id = mDatabaseRef.push().getKey();
        String chucVu = radioGroup.getCheckedRadioButtonId() == R.id.radioButtonLeTan_ThemNV ? "2" : "1";
        String hoTen = edtHoTen.getText().toString();
        String tenDangNhap = edtTenDangNhap.getText().toString();
        String matKhau = edtMatKhau.getText().toString();
        String soDienThoai = edtSoDienThoai.getText().toString();
        double luong = Double.parseDouble(edtLuong.getText().toString());

        nhan_vien employee = new nhan_vien(
                id,
                chucVu,
                hoTen,
                tenDangNhap,
                matKhau,
                imageUrl,
                soDienThoai,
                luong,
                cccdImages.get(0),
                cccdImages.get(1)
        );

        mDatabaseRef.child(id).setValue(employee).addOnSuccessListener(aVoid -> {
            Toast.makeText(ThemNhanVien.this, "Thêm nhân viên thành công!", Toast.LENGTH_SHORT).show();
            finish();
        }).addOnFailureListener(e -> {
            Toast.makeText(ThemNhanVien.this, "Lỗi khi thêm nhân viên!", Toast.LENGTH_SHORT).show();
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

    private void setControl() {
        btnQuayLai = findViewById(R.id.btnQuayLai_ThemNV);
        edtHoTen = findViewById(R.id.edtHoTen_ThemNV);
        edtTenDangNhap = findViewById(R.id.edtTenDangNhap_ThemNV);
        edtMatKhau = findViewById(R.id.edtMatKhau_ThemNV);
        edtSoDienThoai = findViewById(R.id.edtSoDienThoai_ThemNV);
        edtLuong = findViewById(R.id.edtLuong_ThemNV);
        imgNV = findViewById(R.id.imgNV_ThemNV);
        radioGroup = findViewById(R.id.radioGroup_ThemNV);
        btnLuu = findViewById(R.id.btnLuu_ThemNV);
        imgCCCD_Truoc = findViewById(R.id.imgCCCD_Truoc_ThemNV);
        imgCCCD_Sau = findViewById(R.id.imgCCCD_Sau_ThemNV);
    }

}
