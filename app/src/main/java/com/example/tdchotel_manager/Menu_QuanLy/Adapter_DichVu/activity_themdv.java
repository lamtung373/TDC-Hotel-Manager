package com.example.tdchotel_manager.Menu_QuanLy.Adapter_DichVu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
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

import com.example.tdchotel_manager.Menu_QuanLy.Fragment_Dichvu;
import com.example.tdchotel_manager.Model.dich_vu;
import com.example.tdchotel_manager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class activity_themdv extends AppCompatActivity {
    EditText edtTenDv,edtGiaDv;
    Button btnLuu;
    RadioGroup loaiphong;
    RadioButton rdNguoi,rdPhong;
    private int nextId = 1;


    private static final int PICK_IMAGE_REQUEST = 1;
    ImageButton imgButtonquaylai;

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_themdichvu);
        setControl();


        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int gia = Integer.parseInt(edtGiaDv.getText().toString().trim());
                int loaiPhong = Integer.parseInt(edtGiaDv.getText().toString().trim());
//                if(rdNguoi.isChecked()){
//                    loaiPhong = 1;
//                }else  if (rdPhong.isChecked()) {
//                    loaiPhong = 2;
//                }
                String idDichVu = String.valueOf(nextId);
                String ten = edtTenDv.getText().toString().trim();
                String anh = (String) imageView.getTag();
                dich_vu dv= new dich_vu(gia,loaiPhong,idDichVu,ten,anh);
                onClickAdd(dv);
                nextId++;
            }
        });



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tạo Intent để chọn hình ảnh từ thư viện
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });
        imgButtonquaylai = findViewById(R.id.imvbQuaylai);
        imgButtonquaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              finish();
            }
        });

    }
    private void onClickAdd(dich_vu dichvu){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("dich_vu");

        String object = String.valueOf(dichvu.getId_dich_vu());
        databaseReference.child(object).setValue(dichvu, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(activity_themdv.this, "add success", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            imageView.setImageURI(imageUri);
            
            
        }
    }
    private void setControl() {
        edtTenDv = findViewById(R.id.edtTenDv);
        edtGiaDv = findViewById(R.id.edtGiaDv);
        btnLuu = findViewById(R.id.btnLuu);
        loaiphong = findViewById(R.id.rdgLoaiphong);
        imageView = findViewById(R.id.imgthemDV);
    }
}