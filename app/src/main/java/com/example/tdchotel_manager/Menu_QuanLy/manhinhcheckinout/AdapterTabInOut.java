package com.example.tdchotel_manager.Menu_QuanLy.manhinhcheckinout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class AdapterTabInOut extends FragmentStateAdapter {


    public AdapterTabInOut(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new checkin();
            case 1:
                return new checkout();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
