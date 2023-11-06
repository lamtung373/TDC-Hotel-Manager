package com.example.tdchotel_manager.Menu_QuanLy;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdchotel_manager.Model.ca_lam;
import com.example.tdchotel_manager.Model.chuc_vu;
import com.example.tdchotel_manager.Model.nhan_vien;
import com.example.tdchotel_manager.R;

import java.util.List;

public class LichLamAdapter extends RecyclerView.Adapter<LichLamAdapter.ChamCongViewHolder>{

    private IClickListener iClickListener;
    public interface IClickListener {
        void onClickUpdateItem(nhan_vien nhan_vien);


    }
    public LichLamAdapter(List<nhan_vien> nhanVienList, List<chuc_vu> chucVuList, List<ca_lam> caLamList, IClickListener listener) {
        this.nhanVienList = nhanVienList;
        this.chucVuList = chucVuList;
        this.caLamList = caLamList;
        this.iClickListener = listener;
    }
    private List<nhan_vien> nhanVienList;
    private List<chuc_vu> chucVuList;
    private List<ca_lam> caLamList;

    @NonNull
    @Override
    public ChamCongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lichlam_custom,parent,false);
        return new ChamCongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChamCongViewHolder holder, int position) {
        nhan_vien nhan_vien = nhanVienList.get(position);
        holder.layoutNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("++ "+"Bao dep trai","phan_cong.getId_nhan_vien()");
                iClickListener.onClickUpdateItem(nhan_vien);
            }
        });

        if(nhan_vien==null)
        {
            return;
        }
        holder.tvTenNhanVien.setText(nhan_vien.getTen_nhan_vien());

        for(int i = 0; i < chucVuList.size(); i++)
        {
            if(chucVuList.get(i).getId_chuc_vu().equals(nhanVienList.get(position).getId_chuc_vu()))
            {
                holder.tvChucVu.setText(chucVuList.get(i).getTen_chuc_vu());
                break;
            }
        }
        int thu2Sang = -1,thu2Chieu = -1, thu3Sang  = -1, thu3Chieu  = -1, thu4Sang = -1, thu4Chieu = -1, thu5Sang = -1, thu5Chieu = -1, thu6Sang = -1, thu6Chieu = -1, thu7Sang = -1, thu7Chieu = -1, CNSang = -1, CNChieu = -1;
        for(int i = 0; i < nhan_vien.getPhanCongList().size(); i++)
        {
            Log.e(nhan_vien.getPhanCongList().get(i).getDayofweek()+"1","kkk");
            if(nhan_vien.getPhanCongList().get(i).getDayofweek()==2 && nhan_vien.getPhanCongList().get(i).getId_ca_lam().equals("1"))
            {
                thu2Sang = 1;
            }
            if(nhan_vien.getPhanCongList().get(i).getDayofweek()==2&&nhan_vien.getPhanCongList().get(i).getId_ca_lam().equals("2"))
            {
                thu2Chieu = 1;
            }
            if(nhan_vien.getPhanCongList().get(i).getDayofweek()==3&&nhan_vien.getPhanCongList().get(i).getId_ca_lam().equals("1"))
            {
                thu3Sang = 1;
            }
            if(nhan_vien.getPhanCongList().get(i).getDayofweek()==3&&nhan_vien.getPhanCongList().get(i).getId_ca_lam().equals("2"))
            {
                thu3Chieu = 1;
            }
            if(nhan_vien.getPhanCongList().get(i).getDayofweek()==4&&nhan_vien.getPhanCongList().get(i).getId_ca_lam().equals("1"))
            {
                thu4Sang = 1;
            }
            if(nhan_vien.getPhanCongList().get(i).getDayofweek()==4&&nhan_vien.getPhanCongList().get(i).getId_ca_lam().equals("2"))
            {
                thu4Chieu = 1;
            }
            if(nhan_vien.getPhanCongList().get(i).getDayofweek()==5&&nhan_vien.getPhanCongList().get(i).getId_ca_lam().equals("1"))
            {
                thu5Sang = 1;
            }
            if(nhan_vien.getPhanCongList().get(i).getDayofweek()==5&&nhan_vien.getPhanCongList().get(i).getId_ca_lam().equals("2"))
            {
                thu5Chieu = 1;
            }
            if(nhan_vien.getPhanCongList().get(i).getDayofweek()==6&&nhan_vien.getPhanCongList().get(i).getId_ca_lam().equals("1"))
            {
                thu6Sang = 1;
            }
            if(nhan_vien.getPhanCongList().get(i).getDayofweek()==6&&nhan_vien.getPhanCongList().get(i).getId_ca_lam().equals("2"))
            {
                thu6Chieu = 1;
            }
            if(nhan_vien.getPhanCongList().get(i).getDayofweek()==7&&nhan_vien.getPhanCongList().get(i).getId_ca_lam().equals("1"))
            {
                thu7Sang = 1;
            }
            if(nhan_vien.getPhanCongList().get(i).getDayofweek()==7&&nhan_vien.getPhanCongList().get(i).getId_ca_lam().equals("2"))
            {
                thu7Chieu = 1;
            }
            if(nhan_vien.getPhanCongList().get(i).getDayofweek()==1&&nhan_vien.getPhanCongList().get(i).getId_ca_lam().equals("1"))
            {
                CNSang = 1;
            }
            if(nhan_vien.getPhanCongList().get(i).getDayofweek()==1&&nhan_vien.getPhanCongList().get(i).getId_ca_lam().equals("2"))
            {
                CNChieu = 1;
            }
        }
        if(thu2Sang==1)
        {
            holder.cbSangT2.setChecked(true);
        }
        else
        {
            holder.cbSangT2.setChecked(false);
        }
        if(thu2Chieu==1)
        {
            holder.cbChieuT2.setChecked(true);
        }
        else
        {
            holder.cbChieuT2.setChecked(false);
        }
        if(thu3Sang==1)
        {
            holder.cbSangT3.setChecked(true);
        }
        else
        {
            holder.cbSangT3.setChecked(false);
        }
        if(thu3Chieu==1)
        {
            holder.cbChieuT3.setChecked(true);
        }
        else
        {
            holder.cbChieuT3.setChecked(false);
        }
        if(thu4Sang==1)
        {
            holder.cbSangT4.setChecked(true);
        }
        else
        {
            holder.cbSangT4.setChecked(false);
        }
        if(thu4Chieu==1)
        {
            holder.cbChieuT4.setChecked(true);
        }
        else
        {
            holder.cbChieuT4.setChecked(false);
        }
        if(thu5Sang==1)
        {
            holder.cbSangT5.setChecked(true);
        }
        else
        {
            holder.cbSangT5.setChecked(false);
        }
        if(thu5Chieu==1)
        {
            holder.cbChieuT5.setChecked(true);
        }
        else
        {
            holder.cbChieuT5.setChecked(false);
        }
        if(thu6Sang==1)
        {
            holder.cbSangT6.setChecked(true);
        }
        else
        {
            holder.cbSangT6.setChecked(false);
        }
        if(thu6Chieu==1)
        {
            holder.cbChieuT6.setChecked(true);
        }
        else
        {
            holder.cbChieuT6.setChecked(false);
        }
        if(thu7Sang==1)
        {
            holder.cbSangT7.setChecked(true);
        }
        else
        {
            holder.cbSangT7.setChecked(false);
        }
        if(thu7Chieu==1)
        {
            holder.cbChieuT7.setChecked(true);
        }
        else
        {
            holder.cbChieuT7.setChecked(false);
        }
        if(CNSang==1)
        {
            holder.cbSangCN.setChecked(true);
        }
        else
        {
            holder.cbSangCN.setChecked(false);
        }
        if(CNChieu==1)
        {
            holder.cbChieuCN.setChecked(true);
        }
        else
        {
            holder.cbChieuCN.setChecked(false);
        }

    }

    @Override
    public int getItemCount() {
        if(nhanVienList != null)
        {
            return  nhanVienList.size();
        }
        return 0;
    }

    class ChamCongViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tvTenNhanVien, tvChucVu;
        private LinearLayout layoutNhanVien;
        private CheckBox cbSangT2, cbChieuT2, cbSangT3, cbChieuT3, cbSangT4, cbChieuT4, cbSangT5, cbChieuT5, cbSangT6, cbChieuT6, cbSangT7, cbChieuT7, cbSangCN, cbChieuCN;

        public ChamCongViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenNhanVien = itemView.findViewById(R.id.tvTenNhanVien);
            tvChucVu = itemView.findViewById(R.id.tvChucVu);
            cbSangT2 = itemView.findViewById(R.id.cbSangT2);
            cbChieuT2 = itemView.findViewById(R.id.cbChieuT2);
            cbSangT3 = itemView.findViewById(R.id.cbSangT3);
            cbChieuT3 = itemView.findViewById(R.id.cbChieuT3);
            cbSangT4 = itemView.findViewById(R.id.cbSangT4);
            cbChieuT4 = itemView.findViewById(R.id.cbChieuT4);
            cbSangT5 = itemView.findViewById(R.id.cbSangT5);
            cbChieuT5 = itemView.findViewById(R.id.cbChieuT5);
            cbSangT6 = itemView.findViewById(R.id.cbSangT6);
            cbChieuT6 = itemView.findViewById(R.id.cbChieuT6);
            cbSangT7 = itemView.findViewById(R.id.cbSangT7);
            cbChieuT7 = itemView.findViewById(R.id.cbChieuT7);
            cbSangCN = itemView.findViewById(R.id.cbSangCN);
            cbChieuCN = itemView.findViewById(R.id.cbChieuCN);
            layoutNhanVien = itemView.findViewById(R.id.layoutNhanVien);

        }
    }
}
