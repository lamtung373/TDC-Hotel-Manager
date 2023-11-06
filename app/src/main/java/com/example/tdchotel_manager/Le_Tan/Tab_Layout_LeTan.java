package com.example.tdchotel_manager.Le_Tan;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.tdchotel_manager.Le_Tan.Fragment_LeTan.Fragment_DaDat;
import com.example.tdchotel_manager.Le_Tan.Fragment_LeTan.Fragment_DangSuDung;
import com.example.tdchotel_manager.Le_Tan.Fragment_LeTan.Fragment_Duyetcoc;
import com.example.tdchotel_manager.Le_Tan.Fragment_LeTan.Fragment_SanSang;
import com.example.tdchotel_manager.Menu_QuanLy.manhinhcheckinout.checkin;
import com.example.tdchotel_manager.Menu_QuanLy.manhinhcheckinout.checkout;

public class Tab_Layout_LeTan extends FragmentStateAdapter {
    public Tab_Layout_LeTan(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new Fragment_SanSang();
            case 1:
                return new Fragment_Duyetcoc();
            case 2:
                return new Fragment_DaDat();
            case 3:
                return new Fragment_DangSuDung();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
