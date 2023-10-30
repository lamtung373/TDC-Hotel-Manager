package com.example.tdchotel_manager.Menu_QuanLy.Adapter_Phong;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.tdchotel_manager.Model.trang_thai_phong;
import com.example.tdchotel_manager.R;

import java.util.List;

public class CustomSpinnerAdapter extends ArrayAdapter<trang_thai_phong> {
    public CustomSpinnerAdapter(Context context, int resource, List<trang_thai_phong> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(getContext(), R.layout.layout_item_trang_thai_phong, null);
        }
        trang_thai_phong item = getItem(position);
        if (item != null) {
            TextView itemName = convertView.findViewById(R.id.tv_ten_trang_thai);
            itemName.setText(item.getTen_trang_thai());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(getContext(), R.layout.layout_item_trang_thai_phong, null);
        }
        trang_thai_phong item = getItem(position);
        if (item != null) {
            TextView itemName = convertView.findViewById(R.id.tv_ten_trang_thai);
            itemName.setText(item.getTen_trang_thai());
        }
        return convertView;
    }
}
