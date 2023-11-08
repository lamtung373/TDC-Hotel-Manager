package com.example.tdchotel_manager.Le_Tan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tdchotel_manager.Model.phong;
import com.example.tdchotel_manager.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import me.relex.circleindicator.CircleIndicator3;

public class Activity_Chi_Tiet_Phong extends AppCompatActivity {
    ViewPager2 vpiv;
    CircleIndicator3 ci;
    Button btnDatphong;
    TextView tvGiacu, tv_tenphong, tvGiamoi, tv_tiennghi, tv_danhgia, tv_mota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_phong);
        setControl();
        Initialization();
    }

    private void Initialization() {
        //Lay du lieu duoc truyen tu man hinh danh sach phong san sang
        phong phong = (phong) getIntent().getSerializableExtra("phong");
        //Chuyen anh
        if (phong.getAnh_phong() != null && !phong.getAnh_phong().isEmpty()) {
            Photo_Adapter adapter = new Photo_Adapter(phong.getAnh_phong());
            vpiv.setAdapter(adapter);
            ci.setViewPager(vpiv);
        }
        //Tao hieu ung khi chuyen anh
        vpiv.setOffscreenPageLimit(3);
        vpiv.setClipToPadding(false);
        vpiv.setClipChildren(false);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });
        vpiv.setPageTransformer(compositePageTransformer);

        //Tu dong chuyen anh
        AutoSlideImage();

        //Gach ngang chu
        tvGiacu.setPaintFlags(tvGiacu.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        tvGiacu.setText(phong.getSale() + "");

        //Tên phòng
        tv_tenphong.setText(phong.getTen_phong());

        //Gia
        tvGiamoi.setText(phong.getGia() + "đ/đêm");

        //
    }

    void LayDuLieuTienNghi(String id_phong) {
//        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("");
    }

    private void AutoSlideImage() {
//        if(mTimer==null){
//            mTimer=new Timer();
//        }
//        mTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                new Handler(Looper.getMainLooper()).post(new Runnable() {
//                    @Override
//                    public void run() {
//                        int curentItem=vpiv.getCurrentItem();
//                        int totalItem=5-1;
//                        if(curentItem<totalItem){
//                            curentItem++;
//                            vpiv.setCurrentItem(curentItem);
//                        }
//                        else{
//                            vpiv.setCurrentItem(0);
//                        }
//                    }
//                });
//            }
//        },500,3000);
        Handler handler = new Handler(Looper.getMainLooper());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int currentItem = vpiv.getCurrentItem();
                if (currentItem == 5 - 1) {
                    vpiv.setCurrentItem(0);
                } else {
                    vpiv.setCurrentItem(vpiv.getCurrentItem() + 1);
                }
            }
        };
        vpiv.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 3000);
            }
        });
    }

    private void setControl() {
        vpiv = findViewById(R.id.vpiv);
        ci = findViewById(R.id.ci);
        btnDatphong = findViewById(R.id.btnDatphong);
        tvGiacu = findViewById(R.id.tvGiacu);
        tv_tenphong = findViewById(R.id.tv_tenphong);
        tv_tiennghi = findViewById(R.id.tv_tiennghi);
        tv_danhgia = findViewById(R.id.tv_danhgia);
        tv_mota = findViewById(R.id.tv_mota);
        tvGiamoi = findViewById(R.id.tvGiamoi);

    }
}