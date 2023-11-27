package com.example.tdchotel_manager.Menu_QuanLy.Adapter_DichVu;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdchotel_manager.Model.tien_nghi;
import com.example.tdchotel_manager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class adapter_tien_nghi_dv extends RecyclerView.Adapter<adapter_tien_nghi_dv.MyViewHolder> {
    ArrayList<tien_nghi> datalist = new ArrayList<>();

    Context context;
    public adapter_tien_nghi_dv(Context context) {
        this.context = context;
        khoi_tao();
    }    @NonNull
    @Override
    public adapter_tien_nghi_dv.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_dichvu, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull adapter_tien_nghi_dv.MyViewHolder holder, int position) {
        tien_nghi data = datalist.get(position);
        holder.tvten.setText(data.getTen_tien_nghi());
        holder.tvgia.setText(String.valueOf(data.getGia_tien_nghi()));
        holder.progressBar.setVisibility(View.VISIBLE);

        Picasso.get().load(data.getAnh_tien_nghi()).into(holder.imganhdv, new Callback() {
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
                Intent intent = new Intent(context, chinhsuatiennghi.class);
                intent.putExtra("tiennghiid",data.getId_tien_nghi());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }
    public class MyViewHolder extends  RecyclerView.ViewHolder{
        ImageView imganhdv;
        TextView tvten,tvgia;
        ProgressBar progressBar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imganhdv = itemView.findViewById(R.id.imgvDV);
            tvten=itemView.findViewById(R.id.tvTenDv1);
            tvgia=itemView.findViewById(R.id.tvGia1);
            progressBar = itemView.findViewById(R.id.progressBar_itemDichVu);

        }
    }
    void khoi_tao(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("tien_nghi");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                datalist.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    tien_nghi  tienNghi = dataSnapshot.getValue(tien_nghi.class);
                    if(tienNghi != null){
                        datalist.add(tienNghi);
                    }

                }
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}

