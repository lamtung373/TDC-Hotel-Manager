package com.example.tdchotel_manager.Menu_QuanLy.Adapter_DichVu;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdchotel_manager.Model.dich_vu;
import com.example.tdchotel_manager.Model.loai_dich_vu;

import com.example.tdchotel_manager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class adapter_dich_vu extends RecyclerView.Adapter<adapter_dich_vu.MyViewHolder> {

    private ArrayList<dich_vu> datalist = new ArrayList<>();
    private ArrayList<loai_dich_vu> loaiDichVuList = new ArrayList<>();

    Context context;

    public adapter_dich_vu(Context context) {
        this.context = context;
        khoi_tao();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_dichvu, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        dich_vu dataItem = datalist.get(position);
        holder.tvten.setText(dataItem.getTen_dich_vu());
        holder.tvgia.setText(String.valueOf(dataItem.getGia_dich_vu()) + "đ/");
        holder.tvloaidv.setText(findLoaiDichVuById(dataItem.getId_loai_dich_vu()));
        holder.progressBar.setVisibility(View.VISIBLE);

        Picasso.get().load(dataItem.getAnh_dich_vu()).into(holder.imganhdv, new Callback() {
            @Override
            public void onSuccess() {
                holder.progressBar.setVisibility(View.GONE);

            }
            @Override
            public void onError(Exception e) {
                holder.progressBar.setVisibility(View.GONE);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, chinhsuadichvu.class);
                intent.putExtra("dichvuid", dataItem.getId_dich_vu());
                context.startActivity(intent);
            }

        });

    }

    private String findLoaiDichVuById(String idLoaiDichVu) {
        for (loai_dich_vu loaiDichVu : loaiDichVuList) {
            if (loaiDichVu.getId_loai_dich_vu().equals(idLoaiDichVu)) {
                return loaiDichVu.getTen_loai_dich_vu();
            }
        }
        return ""; // Hoặc bạn có thể trả về chuỗi mặc định khác
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        ImageView imganhdv;
        TextView tvten, tvgia, tvloaidv;
        ProgressBar progressBar;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvten = itemView.findViewById(R.id.tvTenDv1);
            tvgia = itemView.findViewById(R.id.tvGia1);
            tvloaidv = itemView.findViewById(R.id.tvloaidv);
            layout = itemView.findViewById(R.id.layout_dv);
            imganhdv = itemView.findViewById(R.id.imgvDV);
            progressBar = itemView.findViewById(R.id.progressBar_itemDichVu);

        }
    }

    void khoi_tao() {
        DatabaseReference loaiDichVuReference = FirebaseDatabase.getInstance().getReference("loai_dich_vu");
        loaiDichVuReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                loaiDichVuList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    loai_dich_vu loaiDichVu = dataSnapshot.getValue(loai_dich_vu.class);
                    if (loaiDichVu != null) {
                        loaiDichVuList.add(loaiDichVu);
                    }
                }
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi nếu cần
            }
        });

        DatabaseReference dichVuReference = FirebaseDatabase.getInstance().getReference("dich_vu");
        dichVuReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                datalist.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    dich_vu dichVu = dataSnapshot.getValue(dich_vu.class);
                    if (dichVu != null) {
                        datalist.add(dichVu);
                    }
                }
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi nếu cần
            }
        });
    }
}