package com.example.tdchotel_manager.Menu_QuanLy;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.tdchotel_manager.Menu_QuanLy.manhinhcheckinout.AdapterTabInOut;
import com.example.tdchotel_manager.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class CheckInOut extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in_out);
        TabLayout tlPhong = findViewById(R.id.tlphong);
        ViewPager2 vp = findViewById(R.id.vp);
        AdapterTabInOut tab_layout_fragment_trang_chu = new AdapterTabInOut(this);
        vp.setAdapter(tab_layout_fragment_trang_chu);
        new TabLayoutMediator(tlPhong, vp, ((tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("   Check In   ");
                    break;
                case 1:
                    tab.setText("   Check Out   ");
                    break;
            }
        })).attach();
    }
}