package com.example.tdchotel_manager.Le_Tan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdchotel_manager.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Photo_Adapter extends RecyclerView.Adapter<Photo_Adapter.Photo_ViewHolder>{
    ArrayList<String>data_image;

    public Photo_Adapter(ArrayList<String> data_image) {
        this.data_image = data_image;
    }

    @NonNull
    @Override
    public Photo_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_iv,parent,false);
        return new Photo_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Photo_ViewHolder holder, int position) {
        if(!data_image.isEmpty()||data_image!=null){
            Picasso.get().load(data_image.get(position)).into(holder.iv, new Callback() {
                @Override
                public void onSuccess() {
                    holder.progressBar_anhphong.setVisibility(View.GONE);
                }

                @Override
                public void onError(Exception e) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(data_image.isEmpty()||data_image==null){
            return 0;
        }
        return data_image.size();
    }

    class Photo_ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        ProgressBar progressBar_anhphong;
        public Photo_ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv=itemView.findViewById(R.id.iv);
            progressBar_anhphong=itemView.findViewById(R.id.progressBar_anhphong);
        }
    }
}
