package com.example.tdchotel_manager.Menu_QuanLy.Adapter_DichVu;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.tdchotel_manager.Menu_QuanLy.Fragment_Dichvu;

public class Viewpageadapter extends FragmentStateAdapter {
    public Viewpageadapter(@NonNull Fragment_Dichvu fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new tabDichVu();
            case 1:
                return new tabDichVuPhong();
            case 2:
                return new tabTienNghi();
        }
        return null;
    }
    @Override
    public int getItemCount() {
        return 3;
    }
}
