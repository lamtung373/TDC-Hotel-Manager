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
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tdchotel_manager.Model.nhan_vien;
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

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

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

    private ProgressBar progressBar;
    private View viewBlocking;

    private DatabaseReference mDatabaseRef;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thongtinnhanvien);
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
                        // Khởi tạo đối tượng DecimalFormat với mẫu định dạng "#.##"
                        DecimalFormat decimalFormat = new DecimalFormat("#.##");

                        // Chuyển đổi giá trị double thành chuỗi đã được định dạng
                        String formattedValue = decimalFormat.format(nv.getLuong());

                        // Thiết lập giá trị đã được định dạng vào trường edtLuong
                        edtLuong.setText(formattedValue);

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
                viewBlocking.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
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
                viewBlocking.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
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

                // Kiểm tra nếu có ảnh mới được chọn
                if (currentSelectedImageView != null) {
                    uploadImageToFirebaseStorage();  // Tải lên ảnh và cập nhật thông tin nhân viên
                } else {
                    // Thông báo và quay lại màn hình trước
                    Toast.makeText(ThongTinNhanVien.this, "Đã cập nhật thông tin nhân viên", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private void updateEmployeeInformation(ArrayList<String> imageUrls) {
        String id = getIntent().getStringExtra("nhanVienId");
        String chucVu = radioButtonLaoCong.isChecked() ? "1" : "2";
        String hoTen = tvHoTen.getText().toString();
        String tenDangNhap = edtTenDangNhap.getText().toString();
        String matKhau = edtMatKhau.getText().toString();
        String soDienThoai = edtSoDienThoai.getText().toString();
        double luong = Double.parseDouble(edtLuong.getText().toString());
        String imageUrlNV = imageUrls.get(0);
        String imageUrlCCCD_Truoc = imageUrls.get(1);
        String imageUrlCCCD_Sau = imageUrls.get(2);

        nhan_vien updatedEmployee = new nhan_vien(
                id,
                chucVu,
                hoTen,
                tenDangNhap,
                matKhau,
                imageUrlNV,  // Cập nhật URL ảnh mới
                soDienThoai,
                luong,
                imageUrlCCCD_Truoc,  // Cập nhật URL CCCD trước mới
                imageUrlCCCD_Sau   // Cập nhật URL CCCD sau mới
        );

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("nhan_vien").child(id);
        ref.setValue(updatedEmployee).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ThongTinNhanVien.this, "Cập nhật thông tin nhân viên thành công!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ThongTinNhanVien.this, "Lỗi khi cập nhật thông tin nhân viên!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadImageToFirebaseStorage() {
        ArrayList<String> imageUrls = new ArrayList<>();

        uploadSingleImage(imgNV, new OnImageUploadedListener() {
            @Override
            public void onImageUploaded(String imageUrl) {
                imageUrls.add(imageUrl);

                // Tiếp tục tải lên ảnh CCCD Trước sau khi đã tải lên ảnh NV
                uploadSingleImage(imgCCCD_Truoc, new OnImageUploadedListener() {
                    @Override
                    public void onImageUploaded(String imageUrl) {
                        imageUrls.add(imageUrl);

                        // Tiếp tục tải lên ảnh CCCD Sau sau khi đã tải lên ảnh CCCD Trước
                        uploadSingleImage(imgCCCD_Sau, new OnImageUploadedListener() {
                            @Override
                            public void onImageUploaded(String imageUrl) {
                                imageUrls.add(imageUrl);

                                // Sau khi tải lên cả 3 ảnh, cập nhật thông tin nhân viên
                                updateEmployeeInformation(imageUrls);
                            }
                        });
                    }
                });
            }
        });
    }

    // Định nghĩa interface OnImageUploadedListener
    public interface OnImageUploadedListener {
        void onImageUploaded(String imageUrl);
    }

    // Định nghĩa hàm uploadSingleImage
    private void uploadSingleImage(ImageView imageView,
                                   final OnImageUploadedListener listener) {
        // Lấy Bitmap từ ImageView của ảnh cần tải lên
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = imageView.getDrawingCache();

        // Tạo tên file duy nhất cho ảnh (ví dụ: "image123.jpg")
        String filename = System.currentTimeMillis() + ".jpg";

        // Lưu ảnh vào Firebase Storage
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference imageRef = storageRef.child("images/" + filename);

        // Chuyển đổi Bitmap thành byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        // Tải ảnh lên Firebase Storage
        UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            // Lấy URL của ảnh sau khi tải lên thành công
            imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                String imageUrl = uri.toString();
                listener.onImageUploaded(imageUrl); // Gọi callback với URL của ảnh
            });
        }).addOnFailureListener(e -> {
            // Xử lý khi có lỗi trong quá trình tải lên ảnh
            Toast.makeText(ThongTinNhanVien.this, "Lỗi khi tải ảnh lên!", Toast.LENGTH_SHORT).show();
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
        progressBar = findViewById(R.id.progressBar_TTNV);
        viewBlocking = findViewById(R.id.viewBlocking_TTNV);
    }
}
