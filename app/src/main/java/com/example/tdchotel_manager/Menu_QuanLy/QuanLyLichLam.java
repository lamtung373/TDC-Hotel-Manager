package com.example.tdchotel_manager.Menu_QuanLy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.tdchotel_manager.LichLamAdapter;
import com.example.tdchotel_manager.Model.ca_lam;
import com.example.tdchotel_manager.Model.chuc_vu;
import com.example.tdchotel_manager.Model.nhan_vien;
import com.example.tdchotel_manager.Model.phan_cong;
import com.example.tdchotel_manager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuanLyLichLam extends AppCompatActivity {

    private RecyclerView rcvLichLam;
    List<ca_lam> caLamList = new ArrayList<>();
    List<nhan_vien> nhanVienList = new ArrayList<>();
    List<chuc_vu> chucVuList = new ArrayList<>();
    private LichLamAdapter lichLamAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_lich_lam);
        setControl();
        setEvent();
    }

    private void setEvent() {

    }

    private void LoadChucVu() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("chuc_vu");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    chuc_vu chucVu = dataSnapshot.getValue(chuc_vu.class);
                    chucVuList.add(chucVu);

                }
                lichLamAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void LoadNhanVien() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("nhan_vien");
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("phan_cong");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    nhan_vien nhan_vien = dataSnapshot.getValue(nhan_vien.class);

                    String idNhanVien = nhan_vien.getId_nhan_vien();


                    Query query = ref1.child("id_nhan_vien").equalTo(idNhanVien);
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            List<phan_cong> phanCongList = new ArrayList<>();

                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                phan_cong phan_cong = snapshot1.getValue(phan_cong.class);
                                phanCongList.add(phan_cong);
                                Log.e("Test"+phanCongList.size(),"");
                            }
                            nhan_vien.setPhanCongList(phanCongList);
                            nhanVienList.add(nhan_vien);

                            lichLamAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }


                lichLamAdapter.notifyDataSetChanged();

                // getListNhanVien(nhanVienList);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(String.valueOf(nhanVienList.size()) + " hao", "");
            }
        });
    }

    private void getListNhanVien(List<nhan_vien> nhanVienList1) {
        nhanVienList.addAll(nhanVienList1);
    }

    private void setControl() {
        rcvLichLam = findViewById(R.id.rcvLichLam);


        LoadNhanVien();
        LoadChucVu();

//        ArrayList<String>  CCCD = new ArrayList<>();
//        String a1 = "fdasdsf";
//        CCCD.add(a1);
//        phan_cong phan_cong = new phan_cong("1","1","2");
//        List<phan_cong> phanCongList = new ArrayList<>();
//        phanCongList.add(phan_cong);
//        nhan_vien nv = new nhan_vien("1","1","232","eafsd","fasdf","fdasfa",CCCD,"dfasfdfd",2332);
//        nv.setPhanCongList(phanCongList);
//        nhanVienList.add(nv);
//        chuc_vu chucVu = new chuc_vu("1","Bảo");
//        chucVuList.add(chucVu);
        lichLamAdapter = new LichLamAdapter(nhanVienList, chucVuList, caLamList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvLichLam.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvLichLam.addItemDecoration(decoration);
        rcvLichLam.setAdapter(lichLamAdapter);
    }
}