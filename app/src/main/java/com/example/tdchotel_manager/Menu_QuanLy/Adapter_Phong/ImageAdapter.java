package com.example.tdchotel_manager.Menu_QuanLy.Adapter_Phong;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdchotel_manager.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private Context context;
    private ArrayList<Uri> imageUris;
    private OnItemClickListener listener;
    ProgressBar progressBar_anhphong;

    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ImageAdapter(Context context, ArrayList<Uri> imageUris) {
        this.context = context;
        this.imageUris = imageUris;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_anh, parent, false);
        return new ImageViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.progressBar_anhphong.setVisibility(View.VISIBLE);

        Uri imageUri = imageUris.get(position);
        Log.e("ImageAdapter", "Binding image at position " + position + ": " + imageUri.toString());
        //holder.imageView.setImageURI(imageUri);
        Picasso.get().load(imageUri).into(holder.imageView, new Callback() {
            @Override
            public void onSuccess() {
                // Ẩn ProgressBar khi tải thành công
                holder.progressBar_anhphong.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {
                // Ẩn ProgressBar và xử lý lỗi
                holder.progressBar_anhphong.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageUris.size();
    }
    public void removeItem(int position) {
        imageUris.remove(position); // Xóa ảnh khỏi mảng
        notifyItemRemoved(position); // Thông báo rằng một item đã bị xóa
        notifyItemRangeChanged(position, imageUris.size()); // Cập nhật lại vị trí của các item còn lại
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageButton deleteButton;
        ProgressBar progressBar_anhphong;

        public ImageViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_anhchitietphong);
            deleteButton = itemView.findViewById(R.id.ib_deleteImage);
            progressBar_anhphong=itemView.findViewById(R.id.progressBar_anhphong);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }
}
