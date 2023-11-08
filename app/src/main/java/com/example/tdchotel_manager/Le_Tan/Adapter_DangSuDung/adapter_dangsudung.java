package com.example.tdchotel_manager.Le_Tan.Adapter_DangSuDung;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdchotel_manager.Menu_QuanLy.Activity_Thong_Tin_Phong;
import com.example.tdchotel_manager.Menu_QuanLy.Adapter_DichVu.adapter_dich_vu;
import com.example.tdchotel_manager.Menu_QuanLy.Adapter_DichVu.chinhsuadichvu;
import com.example.tdchotel_manager.Menu_QuanLy.Adapter_Phong.adapter_phong;
import com.example.tdchotel_manager.Model.dich_vu;
import com.example.tdchotel_manager.Model.hoa_don;
import com.example.tdchotel_manager.Model.khach_hang;
import com.example.tdchotel_manager.Model.phong;
import com.example.tdchotel_manager.Model.trang_thai_phong;
import com.example.tdchotel_manager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class adapter_dangsudung extends RecyclerView.Adapter<adapter_dangsudung.MyViewHolder> {

    private ArrayList<hoa_don> datalist = new ArrayList<>();
    Context context;
    public adapter_dangsudung(Context context) {
        this.context = context;
        khoi_tao();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_dang_su_dung, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_dangsudung.MyViewHolder holder, int position) {
        hoa_don dataItem = datalist.get(position);
        holder.tv_mahoadon.setText(dataItem.getId_hoa_don());
        holder.tv_ngaynhan.setText(dataItem.getThoi_gian_nhan_phong());
        holder.tv_ngaytra.setText(dataItem.getThoi_gian_tra_phong());
        holder.tv_datra.setText(String.valueOf(dataItem.getTien_coc()));
        holder.tv_tong.setText(String.valueOf(dataItem.getTong_thanh_toan()));


    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        TextView tv_mahoadon, tv_ngaynhan,tv_ngaytra, tv_datra,tv_tong;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_mahoadon = itemView.findViewById(R.id.tvMaHoaDon);
            tv_ngaynhan = itemView.findViewById(R.id.tvNgayNhanPhong);
            tv_ngaytra = itemView.findViewById(R.id.tvNgayTraPhong);
            layout = itemView.findViewById(R.id.layout_hoadon);
            tv_datra = itemView.findViewById(R.id.tvDaTra);
            tv_tong = itemView.findViewById(R.id.tvTong);
        }
    }

    void khoi_tao() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("hoa_don");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                datalist.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    hoa_don hoaDon = dataSnapshot.getValue(hoa_don.class);
                    if (hoaDon != null) {
                        datalist.add(hoaDon);
                    }
                }
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error if necessary
            }
        });
    }
}