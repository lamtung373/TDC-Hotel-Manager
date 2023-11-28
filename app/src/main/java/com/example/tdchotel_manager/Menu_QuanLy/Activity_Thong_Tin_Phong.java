package com.example.tdchotel_manager.Menu_QuanLy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.DecimalFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tdchotel_manager.Menu_QuanLy.Adapter_Phong.ImageAdapter;
import com.example.tdchotel_manager.Menu_QuanLy.Adapter_Phong.adapter_dich_vu_phong;
import com.example.tdchotel_manager.Menu_QuanLy.Adapter_Phong.adapter_tien_nghi;
import com.example.tdchotel_manager.Model.chi_tiet_dich_vu_phong;
import com.example.tdchotel_manager.Model.chi_tiet_tien_nghi;
import com.example.tdchotel_manager.Model.phong;
import com.example.tdchotel_manager.Model.trang_thai_phong;
import com.example.tdchotel_manager.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Activity_Thong_Tin_Phong extends AppCompatActivity {
    Spinner sp_status;
    RecyclerView rcv_anhphong;
    ImageButton btn_back, btn_save, ibChosseImg;
    EditText edt_name, edt_description, edt_price, edt_sale;
    RadioGroup radiogroup;
    ArrayList<trang_thai_phong> list_status = new ArrayList<>();
    private RecyclerView rcv_tien_nghi, rcv_dich_vu_phong;
    private ArrayList<String> list_ten_anh = new ArrayList<>();
    private adapter_tien_nghi adapterTienNghi = new adapter_tien_nghi();
    private adapter_dich_vu_phong adapterDichVuPhong = new adapter_dich_vu_phong();
    ArrayList<chi_tiet_dich_vu_phong> list_chi_tietDVP = new ArrayList<>();
    ArrayList<chi_tiet_tien_nghi> list_chi_tietTN = new ArrayList<>();
    //danh sách tải về tiện nghi của phòng
    ArrayList<chi_tiet_tien_nghi> list_tiennghi_dowload = new ArrayList<>();
    String IDphong = "";
    phong detail_infor_room = new phong();
    ArrayList<Uri> picture_list = new ArrayList<>();
    private ImageView currentSelectedImageView;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;
    ProgressBar progressBar_luuphong;
    ImageAdapter imageAdapter;
    phong thong_tin_phong;
    View viewBlocking;

    private void setControl() {
        radiogroup = findViewById(R.id.radiogroup);
        edt_sale = findViewById(R.id.edt_price_sale);
        edt_price = findViewById(R.id.edt_price_room);
        edt_description = findViewById(R.id.edt_description);
        sp_status = findViewById(R.id.sp_status);
        rcv_tien_nghi = findViewById(R.id.rcv_tien_nghi);
        rcv_dich_vu_phong = findViewById(R.id.rcv_dich_vu_phong);
        btn_back = findViewById(R.id.btn_back);
        btn_save = findViewById(R.id.btn_save_phong);
        edt_name = findViewById(R.id.edt_name_room);
        ibChosseImg = findViewById(R.id.ibChosseImg);
        rcv_anhphong = findViewById(R.id.rcv_anh_phong);
        progressBar_luuphong = findViewById(R.id.progressBar_luuphong);
        viewBlocking = findViewById(R.id.viewBlocking);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thong_tin_chi_tiet_phong);
        Intent intent = getIntent();
        thong_tin_phong = (phong) intent.getSerializableExtra("phong");
        setControl();
        imageAdapter = new ImageAdapter(this, picture_list);
        rcv_anhphong.setAdapter(imageAdapter);
        rcv_anhphong.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        loadTrangThaiPhong();
        setEvent();// Method call to load room status from Firebase
        if (thong_tin_phong != null) {
            fill_data(thong_tin_phong);
        }
    }

    public void fill_data(phong data_phong) {
        edt_name.setText(data_phong.getTen_phong());
        edt_description.setText(data_phong.getMo_ta_chung());
        java.text.DecimalFormat formatter = new java.text.DecimalFormat("#");
        edt_price.setText(formatter.format(data_phong.getGia()));
        edt_sale.setText(formatter.format(data_phong.getSale()));
        loadchitiettiennghi(data_phong.getId_phong());
        adapterTienNghi.GoiDuLieu(data_phong.getId_phong());
        adapterDichVuPhong.GoiDuLieu(data_phong.getId_phong());
        loadImagesFromFirebase(data_phong);
        switch (data_phong.getLoai_phong()) {
            case "1 Người":
                radiogroup.check(R.id.rdo_1_nguoi);
                break;
            case "2 Người":
                radiogroup.check(R.id.rdo_2_nguoi);
                break;
            case "3 Người":
                radiogroup.check(R.id.rdo_3_nguoi);
                break;
            case "4 Người":
                radiogroup.check(R.id.rdo_4_nguoi);
                break;
            case "5 Người":
                radiogroup.check(R.id.rdo_5_nguoi);
                break;
        }
    }

    private void loadchitiettiennghi(String id_phong) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("chi_tiet_tien_nghi");

// Tạo một query để lọc các phần tử có id_phong cần tìm
        Query query = ref.orderByChild("id_phong").equalTo(id_phong);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Object> danhSachTienNghi = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    // Giả sử Object là class của bạn có thuộc tính id_phong
                    chi_tiet_tien_nghi tienNghi = postSnapshot.getValue(chi_tiet_tien_nghi.class);
                    list_tiennghi_dowload.add(tienNghi);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Lỗi khi không thể thực hiện truy vấn hoặc truy vấn bị hủy
                System.out.println("loadPost:onCancelled: " + databaseError.toException());
            }
        });

    }

    //truyền ảnh từ url vào adapter để hiện ảnh lên recyclerview
    private void loadImagesFromFirebase(phong thongtin) {
        for (String url : thongtin.getAnh_phong()) {
            list_ten_anh.add(url);
            Uri imageUri = Uri.parse(url);
            picture_list.add(imageUri);
            Log.e("urianhr", imageUri.toString());
        }
        imageAdapter.notifyDataSetChanged();
    }

    private void setEvent() {
        imageAdapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                removeImageAtPosition(position);
            }
        });
        ibChosseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentSelectedImageView = ibChosseImg; // Lưu lại ImageView hiện tại để sử dụng sau
                showImagePickDialog(); // Hiển thị dialog để người dùng chọn ảnh
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // Finish the current activity
            }

            ;
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (thong_tin_phong == null) {
                    save_data();
                } else {
                    update_data(thong_tin_phong.getId_phong());

                }
            }
        });
        sp_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                trang_thai_phong selectedTrangThai = list_status.get(position);
                if (selectedTrangThai != null) {
                    String selectedId = selectedTrangThai.getId_trang_thai_phong();
                    String selectedTenTrangThai = selectedTrangThai.getTen_trang_thai();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Toast.makeText(Activity_Thong_Tin_Phong.this, "Vui lòng chọn trạng thái phòng", Toast.LENGTH_SHORT).show();
                sp_status.findFocus();
            }
        });
        sp_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        rcv_tien_nghi.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rcv_tien_nghi.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rcv_tien_nghi.setAdapter(adapterTienNghi);

        rcv_dich_vu_phong.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rcv_dich_vu_phong.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rcv_dich_vu_phong.setAdapter(adapterDichVuPhong);
    }

    //sửa phòng
    private void update_data(String idPhong) {
        if (validateRoomData()) {
            viewBlocking.setVisibility(View.VISIBLE);
            progressBar_luuphong.setVisibility(View.VISIBLE); // Hiển thị ProgressBar
            SuaThongTinCoBan(idPhong);

        }
    }

    void SuaThongTinCoBan(String id_phong) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("phong").child(id_phong);
        String ten_phong = edt_name.getText().toString();
        String mo_ta_chung = edt_description.getText().toString();
        String loai_phong = typeRoom();
        int selectedPosition = sp_status.getSelectedItemPosition();
        trang_thai_phong selectedTrangThai = list_status.get(selectedPosition);
        String id_trang_thai_phong = selectedTrangThai.getId_trang_thai_phong();
        double gia = Double.parseDouble(edt_price.getText().toString());
        double sale = Double.parseDouble(edt_sale.getText().toString());

        // Tạo một HashMap để cập nhật thông tin của phòng
        Map<String, Object> updates = new HashMap<>();
        updates.put("ten_phong", ten_phong);
        updates.put("mo_ta_chung", mo_ta_chung);
        updates.put("loai_phong", loai_phong);
        updates.put("id_trang_thai_phong", id_trang_thai_phong);
        updates.put("gia", gia);
        updates.put("sale", sale);


        // Cập nhật thông tin của phòng
        ref.updateChildren(updates).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Cập nhật thành công
                Log.d("Firebase", "Thông tin phòng đã được cập nhật.");
            } else {
                // Cập nhật thất bại, xử lý lỗi
                Log.d("Firebase", "Cập nhật thông tin phòng thất bại: " + task.getException().getMessage());
            }
        });

        // Sau khi cập nhật thông tin cơ bản của phòng, bạn có thể tiếp tục với việc cập nhật danh sách chi tiết dịch vụ phòng và tiện nghi.
        SuaChiTiet(id_phong);
        uploadImagesUpdate(picture_list, new OnAllImagesUploadedListener() {
            @Override
            public void onAllImagesUploaded(List<String> imageUrls) {
                // Xử lý khi tất cả ảnh đã được tải lên và bạn đã nhận được danh sách đường dẫn URL của ảnh
                progressBar_luuphong.setVisibility(View.GONE);
                viewBlocking.setVisibility(View.GONE);
                Toast.makeText(Activity_Thong_Tin_Phong.this, "Thêm phòng thành công", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void updatePhongAnh(ArrayList<String> newImageUrls, String phongId) {
        // Lấy tham chiếu đến Firebase Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference roomRef = database.getReference("phong").child(phongId);

        // Cập nhật trường `anh_phong` với mảng mới
        roomRef.child("anh_phong").setValue(newImageUrls)
                .addOnSuccessListener(aVoid -> {
                    // Cập nhật thành công
                    System.out.println("Images updated successfully.");
                })
                .addOnFailureListener(e -> {
                    // Có lỗi xảy ra
                    e.printStackTrace();
                });
    }

    private void uploadImagesUpdate(List<Uri> imageUris, final OnAllImagesUploadedListener listener) {
        // Khởi tạo StorageReference
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        final ArrayList<String> downloadUrls = new ArrayList<>();
        final AtomicInteger uploadCount = new AtomicInteger(0);

        // Kiểm tra nếu imageUris trống
        if (imageUris.isEmpty()) {
            listener.onAllImagesUploaded(downloadUrls); // Gọi listener với danh sách trống
            return;
        }

        Log.e("Trước",imageUris.size()+"");
        for (Uri imageUri : imageUris) {
            Log.e("Trước",imageUri+"");
            // Đặt tên duy nhất cho ảnh tải lên (ví dụ: theo thời gian hiện tại)
            String uniqueName = "image" + System.currentTimeMillis() + "_" + uploadCount.incrementAndGet();

            // Xây dựng đường dẫn lưu trữ trên Firebase Storage
            StorageReference imageRef = storageReference.child("images/" + uniqueName);

            // Tải lên ảnh lên Firebase Storage
            imageRef.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        // Ảnh đã tải lên thành công, lấy URL của ảnh
                        taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(downloadUri -> {
                            downloadUrls.add(downloadUri.toString());
                            if (uploadCount.get() == imageUris.size()) {
                                // Gọi listener khi tất cả ảnh đã được tải lên và lấy URL thành công
                                listener.onAllImagesUploaded(downloadUrls);
                                list_ten_anh.addAll(downloadUrls);
                                updatePhongAnh(list_ten_anh, thong_tin_phong.getId_phong());
                            }
                        });
                    })
                    .addOnFailureListener(e -> {
                        // Xử lý lỗi khi tải lên ảnh thất bại
                        Log.e("Firebase", "Lỗi khi tải lên ảnh: " + e.getMessage());
                        // Nếu muốn, bạn cũng có thể thông báo cho listener về lỗi ở đây
                    });
        }
    }

    public void SuaChiTiet(String id_phong) {
        list_chi_tietDVP.clear();
        //sách chi tiết dịch vụ phòng từ adapter
        list_chi_tietDVP = adapterDichVuPhong.getChiTietDichVu();
        // Thêm danh sách chi tiết dịch vụ phòng vào Firebase
        onClickUpdatefacilities(list_chi_tietDVP, id_phong);


        list_chi_tietTN.clear();
        list_chi_tietTN = adapterTienNghi.getChi_tiet_tien_nghis();
        onClickUpdateComfort(list_chi_tietTN, id_phong);
    }

    //hàm cập nhật tiện nghi
    private void onClickUpdateComfort(ArrayList<chi_tiet_tien_nghi> comfortList, String idPhong) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> childUpdates = new HashMap<>();

        // Tạo các cập nhật cho mỗi chi tiết tiện nghi
        for (chi_tiet_tien_nghi comfort : comfortList) {
            String comfortID = comfort.getId_tien_nghi();
            if (comfortID != null) {
                // Đường dẫn sẽ là /chi_tiet_tien_nghi/idPhong/comfortID
                Map<String, Object> comfortValues = comfort.toMap();
                childUpdates.put("/chi_tiet_tien_nghi/" + idPhong + "/" + comfortID, comfortValues);
            }
        }

        // Thực hiện cập nhật thông tin
        databaseReference.updateChildren(childUpdates).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Cập nhật thành công
                Log.e("sửa tiện nghi", "thành công");
                //Toast.makeText(Activity_Thong_Tin_Phong.this, "Comfort updated successfully", Toast.LENGTH_SHORT).show();
            } else {
                // Cập nhật thất bại
                Log.e("sửa tiện nghi", "thất bại");
                //Toast.makeText(Activity_Thong_Tin_Phong.this, "Failed to update comfort", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //hàm cập nhật dịch vụ phòng
    private void onClickUpdatefacilities(ArrayList<chi_tiet_dich_vu_phong> facilityList, String idPhong) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> childUpdates = new HashMap<>();

        // Tạo các cập nhật cho mỗi chi tiết tiện nghi
        for (chi_tiet_dich_vu_phong facility : facilityList) {
            String facilityID = facility.getId_dich_vu_phong();
            if (facilityID != null) {
                // Đường dẫn sẽ là /chi_tiet_tien_nghi/idPhong/comfortID
                Map<String, Object> facilityValues = facility.toMap();
                childUpdates.put("/chi_tiet_dich_vu_phong/" + idPhong + "/" + facilityID, facilityValues);
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

    //Lưu phòng mới
    void save_data() {
        if (validateRoomData()) {
            viewBlocking.setVisibility(View.VISIBLE);
            progressBar_luuphong.setVisibility(View.VISIBLE); // Hiển thị ProgressBar
            uploadImages(picture_list, new OnAllImagesUploadedListener() {
                @Override
                public void onAllImagesUploaded(List<String> imageUrls) {
                    Log.e("hhhhhhh", "đã thêm ảnh");
                    phong room = new_room();
                    onClickAdd_room(new_room());
                    progressBar_luuphong.setVisibility(View.GONE);
                    viewBlocking.setVisibility(View.GONE);
                    Toast.makeText(Activity_Thong_Tin_Phong.this, "Thêm phòng thành công", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }
    }

    public void uploadImages(ArrayList<Uri> imageUris, final OnAllImagesUploadedListener listener) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://tdc-hotel-beb50.appspot.com");
        List<Task<Uri>> tasks = new ArrayList<>();

        for (Uri imageUri : imageUris) {
            final String fileName = "images/" + System.currentTimeMillis() + ".jpg";
            StorageReference fileReference = storageReference.child(fileName);
            UploadTask uploadTask = fileReference.putFile(imageUri);

            // Chaining task to get download URL after upload is successful
            Task<Uri> urlTask = uploadTask.continueWithTask(task -> {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                // Continue with the task to get the download URL
                return fileReference.getDownloadUrl();
            }).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    list_ten_anh.add(downloadUri.toString());
                }
            });
            tasks.add(urlTask);
        }

        // Wait for all the tasks to complete
        Tasks.whenAllSuccess(tasks).addOnSuccessListener(new OnSuccessListener<List<Object>>() {
            @Override
            public void onSuccess(List<Object> list) {
                // All images have been uploaded successfully
                if (listener != null) {
                    listener.onAllImagesUploaded(list_ten_anh);
                }
            }
        });
    }

    public interface OnAllImagesUploadedListener {
        void onAllImagesUploaded(List<String> imageUrls);
    }

    private void showImagePickDialog() {
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
                    pickPhotoIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true); // Cho phép chọn nhiều ảnh
                    startActivityForResult(pickPhotoIntent, REQUEST_IMAGE_PICK);
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_PICK) {
                // Xử lý chọn ảnh từ thư viện
                handleImageSelection(data);
            } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
                // Xử lý chụp ảnh từ camera
                handleCameraImage(data);
            }
        }
    }

    private void handleImageSelection(Intent data) {
        if (data.getClipData() != null) {
            int count = data.getClipData().getItemCount(); // Lấy số lượng ảnh đã chọn
            for (int i = 0; i < count; i++) {
                Uri imageUri = data.getClipData().getItemAt(i).getUri();
                picture_list.add(imageUri); // Thêm Uri vào mảng
            }
        } else if (data.getData() != null) {
            Uri imageUri = data.getData();
            picture_list.add(imageUri); // Thêm Uri vào mảng
        }
        updateRecyclerView();
    }

    private void handleCameraImage(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            // Lưu bitmap vào một file tạm thời và lấy URI của nó
            Uri imageUri = saveImageToTempFile(imageBitmap);
            if (imageUri != null) {
                picture_list.add(imageUri); // Thêm Uri vào mảng
                updateRecyclerView();
            }
        }
    }

    private Uri saveImageToTempFile(Bitmap bitmap) {
        // Tạo một file tạm thời để lưu ảnh
        File tempDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File tempFile = null;
        try {
            tempFile = File.createTempFile("captured_", ".jpg", tempDir);
            FileOutputStream fos = new FileOutputStream(tempFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
            return Uri.fromFile(tempFile);
        } catch (IOException e) {
            e.printStackTrace();
            // Xử lý lỗi
        }
        return null;
    }

    private void removeImageAtPosition(int position) {
        if (position >= 0 && position < picture_list.size()) {
            // Remove the image URI from the list at the specified position
            picture_list.remove(position);
            // Notify the adapter that an item has been removed
            imageAdapter.notifyItemRemoved(position);
            // Notify any registered observers that the itemCount items starting at position positionStart have changed.
            imageAdapter.notifyItemRangeChanged(position, picture_list.size());
        } else {
            // Optionally, you can add a log or a Toast message here to inform that the removal was not successful
            Toast.makeText(this, "Invalid position: " + position, Toast.LENGTH_SHORT).show();
        }
    }

    private void updateRecyclerView() {
        if (imageAdapter == null) {
            imageAdapter = new ImageAdapter(this, picture_list);
            rcv_anhphong.setAdapter(imageAdapter);
            rcv_anhphong.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        } else {
            imageAdapter.notifyDataSetChanged(); // Thông báo rằng dữ liệu đã thay đổi
        }
    }

    chi_tiet_dich_vu_phong facilities(String id_facility, int quantity, String id_phong) {
        chi_tiet_dich_vu_phong new_facilities = new chi_tiet_dich_vu_phong();
        new_facilities.setId_dich_vu_phong(id_facility);
        new_facilities.setSo_luong(quantity);
        new_facilities.setId_phong(id_phong);
        return new_facilities;
    }


    chi_tiet_tien_nghi comfort(String id_comfort, int quantity, String id_phong) {

        chi_tiet_tien_nghi new_comfort = new chi_tiet_tien_nghi();
        new_comfort.setId_tien_nghi(id_comfort);
        new_comfort.setSo_luong(quantity);
        new_comfort.setId_phong(id_phong);
        return new_comfort;
    }

    private void onClickAdd_comfort(ArrayList<chi_tiet_tien_nghi> comfortList, String idPhong) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> chilUpdates = new HashMap<>();
//Tạo các cập nhật cho mỗi chi tiêt tiện nghi
        for (chi_tiet_tien_nghi comfort : comfortList) {
            String comfortID = comfort.getId_tien_nghi();
            if (comfortID != null) {
                // Đường dẫn sẽ là /chi_tiet_tien_nghi/idPhong/key
                Map<String, Object> comfortValues = comfort.toMap();
                chilUpdates.put("/chi_tiet_tien_nghi/" + idPhong + "/" + comfortID, comfortValues);
            }
        }
        //Thưc hiện cập nhật thông báo
        databaseReference.updateChildren(chilUpdates).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Cập nhật thành công
                Toast.makeText(Activity_Thong_Tin_Phong.this, "Comfort added successfully", Toast.LENGTH_SHORT).show();
            } else {
                // Cập nhật thất bại
                Toast.makeText(Activity_Thong_Tin_Phong.this, "Failed to add comfort", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onClickAddFacilities(ArrayList<chi_tiet_dich_vu_phong> facilitiesList, String idPhong) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> childUpdates = new HashMap<>();

        // Tạo các cập nhật cho mỗi chi tiết dịch vụ phòng
        for (chi_tiet_dich_vu_phong facility : facilitiesList) {
            String facilityID = facility.getId_dich_vu_phong();
            if (facilityID != null) {
                // Đường dẫn sẽ là /chi_tiet_dich_vu_phong/idPhong/key
                Map<String, Object> facilityValues = facility.toMap();
                childUpdates.put("/chi_tiet_dich_vu_phong/" + idPhong + "/" + facilityID, facilityValues);
            }
        }

        // Thực hiện cập nhật thông báo
        databaseReference.updateChildren(childUpdates).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Cập nhật thành công
                Toast.makeText(Activity_Thong_Tin_Phong.this, "Facilities added successfully", Toast.LENGTH_SHORT).show();
            } else {
                // Cập nhật thất bại
                Toast.makeText(Activity_Thong_Tin_Phong.this, "Failed to add facilities", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void ThemChiTiet(String id_phong) {
        // Lấy danh sách chi tiết dịch vụ phòng từ adapter
        list_chi_tietDVP = adapterDichVuPhong.getChiTietDichVu();
        // Thêm danh sách chi tiết dịch vụ phòng vào Firebase
        onClickAddFacilities(list_chi_tietDVP, id_phong);

        list_chi_tietTN = adapterTienNghi.getChi_tiet_tien_nghis();
        onClickAdd_comfort(list_chi_tietTN, id_phong);
    }

    private void onClickAdd_room(phong phong) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("phong");
        DatabaseReference new_room = databaseReference.push(); // Tạo một khóa con mới

        new_room.setValue(phong, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null) {
                    String generatedId = new_room.getKey(); // Lấy khóa con duy nhất đã tạo
                    IDphong = generatedId;
                    ThemChiTiet(IDphong);
                    if (generatedId != null) {
                        phong.setId_phong(generatedId); // Gán khóa con duy nhất làm id_dich_vu cho dichvu
                        new_room.setValue(phong); // Cập nhật lại dữ liệu với id_dich_vu mới
                        Toast.makeText(Activity_Thong_Tin_Phong.this, "Add success", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Activity_Thong_Tin_Phong.this, "Add failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Activity_Thong_Tin_Phong.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    String typeRoom() {
        String type_room = "";
        if (radiogroup.getCheckedRadioButtonId() != -1) {
            RadioButton selectedRadioButton = findViewById(radiogroup.getCheckedRadioButtonId());
            type_room = selectedRadioButton.getText().toString(); // Lấy dữ liệu của radio button đã chọn
        } else {
            Toast.makeText(Activity_Thong_Tin_Phong.this, "Chưa chọn loại phòng", Toast.LENGTH_SHORT).show();
            radiogroup.findFocus();
        }
        return type_room;
    }

    phong new_room() {
        String name = edt_name.getText().toString();
        String description = edt_description.getText().toString();
        int price = Integer.parseInt(edt_price.getText().toString());
        int sale = 0;
        if (!edt_sale.getText().toString().isEmpty()) {
            sale = Integer.parseInt(edt_sale.getText().toString());
        }

        String type = typeRoom();
        //trạng thái
        int selectedPosition = sp_status.getSelectedItemPosition();
        trang_thai_phong selectedTrangThai = list_status.get(selectedPosition);
        String statusID = selectedTrangThai.getId_trang_thai_phong();
        int luot_thue = 0;
        ArrayList<String>list_anh;
        int rating = 5;if (list_ten_anh!=null){
           list_anh= list_ten_anh;
        }else {
            list_anh=new ArrayList<>();
        }
        String ngay_don_phong = "";
        phong room = new phong(null, name, description, list_anh, type, statusID, luot_thue, price, sale, rating, ngay_don_phong);
        return room;
    }


    private void loadTrangThaiPhong() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("trang_thai_phong");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list_status.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    trang_thai_phong trangThai = dataSnapshot.getValue(trang_thai_phong.class);
                    if (trangThai != null) {
                        list_status.add(trangThai);
                    }
                }
                // Cập nhật Spinner sp_status khi có dữ liệu mới
                updateSpinnerStatus();
                if (thong_tin_phong != null) {
                    for (int i = 0; i < list_status.size(); i++) {
                        trang_thai_phong status = list_status.get(i);
                        Log.e("trang thai phong", status.getId_trang_thai_phong().toString());
                        if (status.getId_trang_thai_phong().equals(thong_tin_phong.getId_trang_thai_phong())) {
                            sp_status.setSelection(i);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi nếu có
            }
        });
    }

    private void updateSpinnerStatus() {
        List<String> spinnerData = new ArrayList<>();
        for (trang_thai_phong trangThai : list_status) {
            spinnerData.add(trangThai.getTen_trang_thai());
        }

        ArrayAdapter<String> adapter_spinner = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, spinnerData);
        adapter_spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_status.setAdapter(adapter_spinner);
    }

    private boolean validateRoomData() {
        String name = edt_name.getText().toString().trim();
        String des = edt_description.getText().toString().trim();
        String priceText = edt_price.getText().toString().trim();
        String saleText = edt_sale.getText().toString().trim();

        if (name.isEmpty()) {
            setErrorAndFocus(edt_name, "Vui lòng nhập tên phòng");
            return false;
        }

        if (des.isEmpty()) {
            setErrorAndFocus(edt_description, "Vui lòng nhập mô tả phòng");
            return false;
        }

        if (priceText.isEmpty()) {
            setErrorAndFocus(edt_price, "Vui lòng nhập giá phòng");
            return false;
        }

        int price = Integer.parseInt(priceText);
        int sale = 0;

        if (!saleText.isEmpty()) {
            sale = Integer.parseInt(saleText);

            if (sale >= price) {
                setErrorAndFocus(edt_sale, "Giá sale phải nhỏ hơn giá phòng");
                return false;
            }
        } else {
            edt_sale.setText("0");
        }

        if (picture_list.isEmpty()) {
            showErrorMessage("Vui lòng chọn ảnh phòng");
            return false;
        }

        return true;
    }

    private void setErrorAndFocus(EditText editText, String errorMessage) {
        editText.setError(errorMessage);
        editText.requestFocus();
    }

    private void showErrorMessage(String errorMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Lỗi");
        builder.setMessage(errorMessage);
        builder.setPositiveButton("OK", (dialog, id) -> dialog.dismiss());

        builder.create().show();
    }

}
