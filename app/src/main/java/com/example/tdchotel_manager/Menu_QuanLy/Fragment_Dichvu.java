package com.example.tdchotel_manager.Menu_QuanLy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.tdchotel_manager.Menu_QuanLy.Adapter_DichVu.Viewpageadapter;
//import com.example.tdchotel_manager.Menu_QuanLy.Adapter_DichVu.adapter_dichvu;
import com.example.tdchotel_manager.R;
import com.google.android.material.tabs.TabLayout;


public class Fragment_Dichvu extends Fragment {
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    Viewpageadapter viewPagerAdapater;

    private RecyclerView recyclerView;

    public Fragment_Dichvu() {
    }
    public static Fragment_Dichvu newInstance() {
        return new Fragment_Dichvu();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__dichvu, container, false);
        tabLayout = view.findViewById(R.id.tldichvu);
        viewPager2 = view.findViewById(R.id.viewpage);
        viewPagerAdapater = new Viewpageadapter(this);
        viewPager2.setAdapter(viewPagerAdapater);
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
            }
        );
        setControl(view);
        setEvent();
        return view;
    }
    private void setEvent() {

    }

    private void setControl(View view) {
    }
}

