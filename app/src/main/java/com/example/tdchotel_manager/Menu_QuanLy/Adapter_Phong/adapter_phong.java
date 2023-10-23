package com.example.tdchotel_manager.Menu_QuanLy.Adapter_Phong;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tdchotel_manager.Model.phong;
import com.example.tdchotel_manager.R;

import java.util.List;

public class adapter_phong extends RecyclerView.Adapter<adapter_phong.MyViewHolder> {
    private List<phong> dataList;

    public adapter_phong(List<phong> dataList) {
        this.dataList = dataList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_phong, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        phong data = dataList.get(position);
        // Cập nhật giao diện người dùng dựa trên data
        holder.tv_name_room.setText(data.getId_phong());
        holder.tv_price.setText(String.valueOf(data.getGia()));
        holder.tv_sale.setText(String.valueOf(data.getSale()));
        holder.tv_type_room.setText(data.getLoai_phong());
        holder.tv_status_room.setText(data.getTrang_thai());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // Tham chiếu đến các phần tử trong item layout
        RecyclerView rv_roomlist;
        ImageView iv_item_phong;
        TextView tv_name_room, tv_price, tv_sale, tv_type_room, tv_status_room;

        public MyViewHolder(View itemView) {
            super(itemView);
            // Khai báo và ánh xạ các phần tử tại đây
            rv_roomlist= itemView.findViewById(R.id.rcv_roomlist);
            iv_item_phong = itemView.findViewById(R.id.iv_item_phong);
            tv_name_room = itemView.findViewById(R.id.tv_name_room);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_sale = itemView.findViewById(R.id.tv_sale);
            tv_type_room = itemView.findViewById(R.id.tv_type_room);
            tv_status_room = itemView.findViewById(R.id.tv_status_room);
        }
    }

    private void khoi_tao() {


    }
}

