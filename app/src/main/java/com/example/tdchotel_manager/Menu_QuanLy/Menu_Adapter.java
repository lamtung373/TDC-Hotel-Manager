package com.example.tdchotel_manager.Menu_QuanLy;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.tdchotel_manager.Menu_QuanLy.Adapter_DichVu.Fragment_Dichvu;

public class Menu_Adapter extends FragmentStateAdapter {
    public Menu_Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new Fragment_Trangchu();
            case 1:
                return new Fragment_Phong();
            case 2:
                return new Fragment_Dichvu();
            case 3:
                return new Fragment_Nhanvien();
            case 4:
                return new Fragment_Thongke();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
