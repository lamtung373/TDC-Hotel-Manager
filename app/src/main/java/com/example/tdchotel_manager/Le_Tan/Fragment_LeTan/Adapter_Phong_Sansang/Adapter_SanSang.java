package com.example.tdchotel_manager.Le_Tan.Fragment_LeTan.Adapter_Phong_Sansang;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdchotel_manager.Menu_QuanLy.Adapter_Phong.adapter_phong;
import com.example.tdchotel_manager.Model.phong;
import com.example.tdchotel_manager.Model.trang_thai_phong;
import com.example.tdchotel_manager.R;

import java.util.ArrayList;

public class Adapter_SanSang extends RecyclerView.Adapter<Adapter_SanSang.MyViewHolder> {
    // Member Variables
    private Context context;
    private ArrayList<phong> room_list = new ArrayList<>();
    private ArrayList<trang_thai_phong> status_list = new ArrayList<>();
    ProgressBar progressBar, progressBar_itemphong;

    public Adapter_SanSang(Context context, ProgressBar progressBar, ProgressBar progressBar_itemphong) {
        this.context = context;
        this.progressBar = progressBar;
        this.progressBar_itemphong = progressBar_itemphong;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    // ViewHolder Class
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_item_phong;
        TextView tv_name_room, tv_price, tv_sale, tv_type_room, tv_status_room;
        ProgressBar progressBar_itemphong;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv_item_phong = itemView.findViewById(R.id.iv_item_phong);
            tv_name_room = itemView.findViewById(R.id.tv_name_room);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_sale = itemView.findViewById(R.id.tv_sale);
            tv_type_room = itemView.findViewById(R.id.tv_type_room);
            tv_status_room = itemView.findViewById(R.id.tv_status_room);
            progressBar_itemphong = itemView.findViewById(R.id.progressBar_itemphong);
        }
    }
}
