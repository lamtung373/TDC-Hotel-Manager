package com.example.tdchotel_manager.Le_Tan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.tdchotel_manager.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class Activity_LeTan extends AppCompatActivity {
    TabLayout tlphong;
    ViewPager2 vpphong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_le_tan);
        setControl();
        Initialization();
    }

    private void Initialization() {
        Tab_Layout_LeTan tab_layout_fragment_trang_chu = new Tab_Layout_LeTan(this);
        vpphong.setAdapter(tab_layout_fragment_trang_chu);
        new TabLayoutMediator(tlphong, vpphong, ((tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Sẵn sàng");
                    break;
                case 1:
                    tab.setText("Duyệt cọc");
                    break;
                case 2:
                    tab.setText("Đã đặt");
                    break;
                case 3:
                    tab.setText("Đang sử dụng");
                    break;
            }
        })).attach();
    }

    private void setControl() {
        tlphong = findViewById(R.id.tlphong);
        vpphong = findViewById(R.id.vpphong);
    }
}