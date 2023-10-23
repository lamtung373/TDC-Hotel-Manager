package com.example.tdchotel_manager.Menu_QuanLy.Adapter_DichVu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdchotel_manager.R;

import java.util.ArrayList;

public class Adapter_DichVu extends RecyclerView.Adapter<Adapter_DichVu.MyViewHolder> {

    Context context;
    ArrayList<DichVu> newArrayList;

    public Adapter_DichVu(Context context, ArrayList<DichVu> newArrayList) {
        this.context = context;
        this.newArrayList = newArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_item_dichvu,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DichVu dv = newArrayList.get(position);
        holder.txtTen.setText(dv.tenDichVu);
        holder.txtTen.setText(dv.giaDichVu);
        holder.imgV.setImageResource(dv.hinhanhDV);

    }

    @Override
    public int getItemCount() {
        return newArrayList.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{
        ImageView imgV;
        TextView txtTen,txtGia;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            imgV = itemView.findViewById(R.id.imgvDV);
            txtTen = itemView.findViewById(R.id.tvTenDv1);
            txtGia = itemView.findViewById(R.id.tvGia1);
        }
    }
}
