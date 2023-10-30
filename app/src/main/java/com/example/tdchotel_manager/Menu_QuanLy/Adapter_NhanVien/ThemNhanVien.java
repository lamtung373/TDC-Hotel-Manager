package com.example.tdchotel_manager.Menu_QuanLy.Adapter_NhanVien;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tdchotel_manager.R;

public class ThemNhanVien extends AppCompatActivity {
    ImageButton btnQuayLai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_themnhanvien);
        setControl();
        setEvent();
    }

    private void setEvent() {
        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();  // Kết thúc Activity hiện tại và quay lại Activity trước đó
            }
        });
    }

    private void setControl() {
        btnQuayLai = findViewById(R.id.btnQuayLai_ThemNV);
    }

}
