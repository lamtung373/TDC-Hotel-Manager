package com.example.tdchotel_manager.Le_Tan;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdchotel_manager.Le_Tan.Activity_HoaDon.Activity_HoaDon_DuyetCoc;
import com.example.tdchotel_manager.Menu_QuanLy.Adapter_NhanVien.ThemNhanVien;
import com.example.tdchotel_manager.Menu_QuanLy.LichLamAdapter;
import com.example.tdchotel_manager.Model.cham_cong;
import com.example.tdchotel_manager.Model.hoa_don;
import com.example.tdchotel_manager.Model.khach_hang;
import com.example.tdchotel_manager.Model.nhan_vien;
import com.example.tdchotel_manager.Model.phong;
import com.example.tdchotel_manager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DuyetCocAdapter extends RecyclerView.Adapter<DuyetCocAdapter.DaDatViewHolder> {
    private List<hoa_don> hoaDonList;
    private List<phong> phongList;
    private List<khach_hang> khachHangList;
    private Context mContext;

    @NonNull
    @Override
    public DaDatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_duyet_coc, parent, false);
        return new DuyetCocAdapter.DaDatViewHolder(view);
    }

    public DuyetCocAdapter(Context context, List<hoa_don> hoaDonList, List<phong> phongList, List<khach_hang> khachHangList) {
        this.hoaDonList = hoaDonList;
        this.phongList = phongList;
        this.khachHangList = khachHangList;
        mContext = context;
    }

    public DuyetCocAdapter(Context context) {
        mContext = context;
        // Khởi tạo các giá trị khác của adapter
    }

    @Override
    public void onBindViewHolder(@NonNull DaDatViewHolder holder, int position) {
        hoa_don hoaDon = hoaDonList.get(position);
        if (hoaDon == null) {
            return;
        }
        holder.tvMaHoaDon.setText(hoaDon.getId_hoa_don().toString());
        holder.tvNgayDatPhong.setText(hoaDon.getThoi_gian_nhan_phong().toString());
        holder.tvNgayNghi.setText(hoaDon.getThoi_gian_tra_phong().toString());
        holder.edtTienDaTra.setText(hoaDon.getTien_coc() + "");
        holder.tvTienTong.setText(hoaDon.getTong_thanh_toan() + " đ (Phải trả: " + hoaDon.getTien_phong() / 2 + "đ)");
        for (int i = 0; i < phongList.size(); i++) {
            if (phongList.get(i).getId_phong().equals(hoaDon.getId_phong())) {
                holder.tv_name_room.setText(phongList.get(i).getTen_phong());
                break;
            }
        }

        holder.tvTenNguoiDung.setText(hoaDon.getTen_khach_hang());


        holder.btnNhanPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("hoa_don");
//                DatabaseReference child2Ref = ref.child(hoaDon.getId_phong().toString());
//                hoaDon.setTien_coc(Double.parseDouble(holder.edtTienDaTra.getText().toString()));
//                child2Ref.child(hoaDon.getId_hoa_don()).updateChildren(hoaDon.toMap(), new DatabaseReference.CompletionListener() {
//                    @Override
//                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//                        Toast.makeText(mContext, "Check thành công", Toast.LENGTH_SHORT).show();
//                    }
//                });

                Intent intent = new Intent(mContext, Activity_HoaDon_DuyetCoc.class);
                intent.putExtra("id_hd", hoaDon.getId_hoa_don());
                intent.putExtra("tien_coc", holder.edtTienDaTra.getText());
                mContext.startActivity(intent);
            }
        });
        holder.btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(mContext)
                        .setMessage("Bạn có chắc muốn hủy duyệt cộc?")
                        .setCancelable(false)
                        .setPositiveButton("Chắc chắn", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("hoa_don");
                                Log.e(hoaDon.getId_phong().toString(), "fdsfdsffd");
                                DatabaseReference child2Ref = ref.child(hoaDon.getId_phong().toString());
                                Date date = new Date();
                                SimpleDateFormat formatterChuan = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                String strDate1 = formatterChuan.format(date);
                                hoaDon.setThoi_gian_huy(strDate1);
                                child2Ref.child(hoaDon.getId_hoa_don()).updateChildren(hoaDon.toMap(), new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        Toast.makeText(mContext, "Check thành công", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (hoaDonList != null) {
            Log.e("be", hoaDonList.size() + "s");
            return hoaDonList.size();

        }
        return 0;
    }

    class DaDatViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name_room, tvMaHoaDon, tvTenNguoiDung, tvNgayDatPhong, tvNgayNghi, tvTienDaTra, tvTienTong;
        EditText edtTienDaTra;
        Button btnNhanPhong, btnHuy;

        public DaDatViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name_room = itemView.findViewById(R.id.tv_name_room);
            btnHuy = itemView.findViewById(R.id.btnHuy);
            tvMaHoaDon = itemView.findViewById(R.id.tvMaHoaDon);
            tvTenNguoiDung = itemView.findViewById(R.id.tvTenNguoiDung);
            tvNgayDatPhong = itemView.findViewById(R.id.tvNgayDatPhong);
            tvNgayNghi = itemView.findViewById(R.id.tvNgayNghi);
            edtTienDaTra = itemView.findViewById(R.id.edtTienDaTra);
            tvTienTong = itemView.findViewById(R.id.tvTienTong);
            btnNhanPhong = itemView.findViewById(R.id.btnNhanPhong);
        }
    }
}
