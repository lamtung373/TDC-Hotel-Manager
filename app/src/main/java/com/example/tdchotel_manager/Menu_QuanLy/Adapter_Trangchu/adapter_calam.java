package com.example.tdchotel_manager.Menu_QuanLy.Adapter_Trangchu;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdchotel_manager.Model.chuc_vu;
import com.example.tdchotel_manager.Model.nhan_vien;
import com.example.tdchotel_manager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class adapter_calam extends RecyclerView.Adapter<adapter_calam.calam_viewholder> {
    ArrayList<nhan_vien> arr_nhanvien = new ArrayList<>();
    // ArrayList<chuc_vu> arr_chucvu = new ArrayList<>();
    public adapter_calam(ArrayList<nhan_vien> arr_nhanvien) {
        this.arr_nhanvien = arr_nhanvien;
    }



    @NonNull
    @Override
    public calam_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new calam_viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_calam, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull calam_viewholder holder, int position) {
        if (!arr_nhanvien.isEmpty()) {
            holder.tv_tennhanvien.setText(arr_nhanvien.get(position).getTen_nhan_vien());
          //  holder.tv_chucvu.setText(arr_chucvu.get(position).getTen_chuc_vu());
            holder.tv_chucvu.setText(arr_nhanvien.get(position).getChuc_vu());
        }
    }

    @Override
    public int getItemCount() {
        if (!arr_nhanvien.isEmpty()) {
            return arr_nhanvien.size();
        }
        return 0;
    }

    class calam_viewholder extends RecyclerView.ViewHolder {
        TextView tv_tennhanvien, tv_chucvu;

        public calam_viewholder(@NonNull View itemView) {
            super(itemView);
            tv_tennhanvien = itemView.findViewById(R.id.tv_tennhanvien);
            tv_chucvu = itemView.findViewById(R.id.tv_chucvu);
        }
    }
}
