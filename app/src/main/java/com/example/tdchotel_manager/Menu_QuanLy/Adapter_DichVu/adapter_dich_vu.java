package com.example.tdchotel_manager.Menu_QuanLy.Adapter_DichVu;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdchotel_manager.Model.dich_vu;
import com.example.tdchotel_manager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class adapter_dich_vu extends RecyclerView.Adapter<adapter_dich_vu.MyViewHolder> {
    Context context;
    private ArrayList<dich_vu> datalist = new ArrayList<>();

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
        holder.tvloaidv.setText(dataItem.getId_loai_dich_vu());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, chinhsuadichvu.class);
                intent.putExtra("dichvuid",dataItem.getId_dich_vu());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        ImageView imganhdv;
        TextView tvten, tvgia, tvloaidv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvten = itemView.findViewById(R.id.tvTenDv1);
            tvgia = itemView.findViewById(R.id.tvGia1);
            tvloaidv = itemView.findViewById(R.id.tvloaidv);
            layout = itemView.findViewById(R.id.layout_dv);
        }
    }

    void khoi_tao() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("dich_vu");
        reference.addValueEventListener(new ValueEventListener() {
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
                // Handle the error if necessary
            }
        });
    }
}