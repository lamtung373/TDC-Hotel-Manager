package com.example.tdchotel_manager.Menu_QuanLy.Adapter_Phong;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tdchotel_manager.Model.phong;
import com.example.tdchotel_manager.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class adapter_phong extends RecyclerView.Adapter<adapter_phong.MyViewHolder> {
    private List<phong> dataList;

    public adapter_phong() {
        dataList = new ArrayList<>();
        khoi_tao();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_phong, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //Set dữ liệu của item
        phong data = dataList.get(position);
        holder.tv_name_room.setText(String.valueOf(data.getTen_phong()));
        holder.tv_price.setText(String.valueOf(data.getGia()));
        holder.tv_price.setPaintFlags(holder.tv_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tv_sale.setText(String.valueOf(data.getSale()));
        holder.tv_type_room.setText(data.getLoai_phong());
        holder.tv_status_room.setText(String.valueOf(data.getTrang_thai()));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_item_phong;
        TextView tv_name_room, tv_price, tv_sale, tv_type_room, tv_status_room;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv_item_phong = itemView.findViewById(R.id.iv_item_phong);
            tv_name_room = itemView.findViewById(R.id.tv_name_room);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_sale = itemView.findViewById(R.id.tv_sale);
            tv_type_room = itemView.findViewById(R.id.tv_type_room);
            tv_status_room = itemView.findViewById(R.id.tv_status_room);
        }
    }

    private void khoi_tao() {
        phong phong1 = new phong(
                1,
                "Phòng Deluxe",
                "Phòng Deluxe rộng rãi và thoải mái.",
                new ArrayList<>(Arrays.asList("anh1.jpg", "anh2.jpg", "anh3.jpg")),
                "Phòng 2 người",
                1,
                0,
                100.0,
                10.0,
                4.5
        );

        phong phong2 = new phong(
                2,
                "Phòng Family",
                "Phòng Family phù hợp cho gia đình lớn.",
                new ArrayList<>(Arrays.asList("anh4.jpg", "anh5.jpg", "anh6.jpg")),
                "Phòng 2 người",
                1,
                0,
                150.0,
                15.0,
                4.7
        );

        phong phong3 = new phong(
                3,
                "Phòng Standard",
                "Phòng Standard tiện nghi và giá cả phải chăng.",
                new ArrayList<>(Arrays.asList("anh7.jpg", "anh8.jpg", "anh9.jpg")),
                "Phòng 2 người",
                1,
                0,
                80.0,
                8.0,
                4.2
        );
        phong phong4 = new phong(
                4,
                "Phòng Suite",
                "Phòng Suite sang trọng với đầy đủ tiện nghi.",
                new ArrayList<>(Arrays.asList("anh10.jpg", "anh11.jpg", "anh12.jpg")),
                "Phòng 2 người",
                1,
                0,
                200.0,
                20.0,
                4.9
        );

        phong phong5 = new phong(
                5,
                "Phòng Studio",
                "Phòng Studio với không gian sáng sủa.",
                new ArrayList<>(Arrays.asList("anh13.jpg", "anh14.jpg", "anh15.jpg")),
                "Phòng 2 người",
                1,
                0,
                120.0,
                12.0,
                4.6
        );

        phong phong6 = new phong(
                6,
                "Phòng Superior",
                "Phòng Superior với view đẹp.",
                new ArrayList<>(Arrays.asList("anh16.jpg", "anh17.jpg", "anh18.jpg")),
                "Phòng 2 người",
                1,
                0,
                90.0,
                9.0,
                4.3
        );

        phong phong7 = new phong(
                7,
                "Phòng VIP",
                "Phòng VIP cao cấp với nhiều tiện ích.",
                new ArrayList<>(Arrays.asList("anh19.jpg", "anh20.jpg", "anh21.jpg")),
                "Phòng 2 người",
                1,
                0,
                300.0,
                30.0,
                5.0
        );

        dataList.add(phong1);
        dataList.add(phong2);
        dataList.add(phong3);
        dataList.add(phong4);
        dataList.add(phong5);
        dataList.add(phong6);
        dataList.add(phong7);
    }
}
