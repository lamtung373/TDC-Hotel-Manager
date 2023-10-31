package com.example.tdchotel_manager.Menu_QuanLy;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.tdchotel_manager.IOnClickItem;
import com.example.tdchotel_manager.Menu_QuanLy.Adapter_DichVu.Viewpageadapter;
//import com.example.tdchotel_manager.Menu_QuanLy.Adapter_DichVu.adapter_dichvu;
import com.example.tdchotel_manager.Menu_QuanLy.Adapter_DichVu.activity_themdv;
import com.example.tdchotel_manager.Menu_QuanLy.Adapter_DichVu.adapter_dich_vu;
import com.example.tdchotel_manager.Model.dich_vu;
import com.example.tdchotel_manager.R;
import com.google.android.material.tabs.TabLayout;

import java.util.List;


public class Fragment_Dichvu extends Fragment implements IOnClickItem {
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    Viewpageadapter viewPagerAdapater;
    ImageButton imgButtonthem;
    RecyclerView rcv;


    public Fragment_Dichvu() {
    }
    public static Fragment_Dichvu newInstance() {
        return new Fragment_Dichvu();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__dichvu, container, false);

        setControl(view);
        setEvent();
        return view;
    }
    private void setEvent() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });

        imgButtonthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), activity_themdv.class);
                startActivity(intent);
            }
        });

    }
    private void setControl(View view) {
        rcv = view.findViewById(R.id.rcvDV);

        imgButtonthem = view.findViewById(R.id.imgAdd);
        tabLayout = view.findViewById(R.id.tldichvu);
        viewPager2 = view.findViewById(R.id.viewpage);

        tabLayout.addTab(tabLayout.newTab().setText("Dịch vụ"));
        tabLayout.addTab(tabLayout.newTab().setText("Dịch vụ phòng"));
        tabLayout.addTab(tabLayout.newTab().setText("Tiện nghi"));

        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#26A1EE"));




        viewPagerAdapater = new Viewpageadapter(this);
        viewPager2.setAdapter(viewPagerAdapater);



    }
    @Override
    public void OnItemClick(int position) {

    }
}

