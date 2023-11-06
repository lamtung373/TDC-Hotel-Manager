package com.example.tdchotel_manager.Menu_QuanLy.Adapter_DichVu;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tdchotel_manager.Model.dich_vu;
import com.example.tdchotel_manager.Model.tien_nghi;
import com.example.tdchotel_manager.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class chinhsuatiennghi extends AppCompatActivity {
    ImageButton imgButtonquaylai;
    TextView tvTenDV;
    EditText edtGiaDV;
    RadioGroup radioGroup;
    RadioButton rdNguoi, rdPhong;
    Button btnXoa, btnSua;
    ImageView imageView;

    private Uri imageChanged =  null;

    private ImageView currentSelectedImageView;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_chinhsuadichvu);
        setControl();
        setEvent();
    }

    private void setEvent() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentSelectedImageView = imageView;
                showImagePickDialog();
            }
        });
        imgButtonquaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // Kết thúc Activity này và quay lại Activity trước đó
            }
        });
        String tiennghiid = getIntent().getStringExtra("tiennghiid");
        if (tiennghiid != null) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("tien_nghi").child(tiennghiid);
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    tien_nghi nv = dataSnapshot.getValue(tien_nghi.class);
                    if (nv != null) {
                        tvTenDV.setText(nv.getTen_tien_nghi());
                        edtGiaDV.setText(String.valueOf(nv.getGia_tien_nghi()));
                        Picasso.get().load(nv.getAnh_tien_nghi()).into(imageView);

                    }
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Xử lý khi có lỗi
                    Toast.makeText(chinhsuatiennghi.this, "Lỗi: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xoá nhân viên từ Firebase
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("tien_nghi").child(tiennghiid);
                ref.removeValue();

                // Thông báo và quay lại màn hình trước
                Toast.makeText(chinhsuatiennghi.this, "Đã xoá", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy dữ liệu từ các trường
                String giaDV = edtGiaDV.getText().toString();
                if (giaDV.isEmpty()) {
                    Toast.makeText(chinhsuatiennghi.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                int giaValue;
                try {
                    giaValue = Integer.parseInt(edtGiaDV.getText().toString().trim());
                } catch (NumberFormatException e) {
                    Toast.makeText(chinhsuatiennghi.this, "Giá không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Cập nhật dữ liệu trên Firebase
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("tien_nghi").child(tiennghiid);
                ref.child("gia_tien_nghi").setValue(giaValue);

                if (imageChanged != null) { // Kiểm tra xem ảnh đã thay đổi
                    // Nếu ảnh đã thay đổi, cập nhật ảnh mới lên Firebase
                    saveImageToFirebase(ref); // Truyền tham số ref để lưu đường dẫn ảnh
                } else {
                    // Thông báo và quay lại màn hình trước
                    Toast.makeText(chinhsuatiennghi.this, "Đã cập nhật thông tin", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
    private void saveImageToFirebase(final DatabaseReference ref) {
        if (imageChanged != null) {
            // Tạo một StorageReference đến Firebase Storage
            StorageReference storageRef = FirebaseStorage.getInstance().getReference();

            // Tạo một StorageReference đến thư mục bạn muốn lưu ảnh (thay thế "images" bằng thư mục tùy chọn)
            StorageReference imageRef = storageRef.child("images/" + System.currentTimeMillis() + ".jpg");

            // Upload ảnh lên Firebase Storage
            imageRef.putFile(imageChanged)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Lấy đường dẫn ảnh sau khi upload thành công
                            imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri downloadUri) {
                                    String imagePath = downloadUri.toString();

                                    // Cập nhật đường dẫn ảnh vào Firebase Realtime Database
                                    ref.child("anh_tien_nghi").setValue(imagePath);

                                    // Thông báo và quay lại màn hình trước
                                    Toast.makeText(chinhsuatiennghi.this, "Đã cập nhật thông tin", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Xử lý khi có lỗi
                            Toast.makeText(chinhsuatiennghi.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
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
                imageChanged = null; // Đặt selectedImageUri là null
                imageChanged = data.getData(); // Lấy đường dẫn của ảnh chụp
            } else if (requestCode == REQUEST_IMAGE_PICK) {
                imageChanged = data.getData(); // Lấy đường dẫn của ảnh từ thư viện
                currentSelectedImageView.setImageURI(imageChanged);
            }
        }
    }


    private void setControl() {
        imageView = findViewById(R.id.imgvDVCS);
        imgButtonquaylai = findViewById(R.id.imvbBack);
        tvTenDV = findViewById(R.id.tvTenDVcs);
        edtGiaDV = findViewById(R.id.edtGiaDv1);
        radioGroup = findViewById(R.id.rgLoaiDvCS);
        rdNguoi = findViewById(R.id.rdNguoiCS);
        rdPhong = findViewById(R.id.rdPhongCS);
        btnXoa = findViewById(R.id.btnXDV);
        btnSua = findViewById(R.id.btnCSDV);
    }
}