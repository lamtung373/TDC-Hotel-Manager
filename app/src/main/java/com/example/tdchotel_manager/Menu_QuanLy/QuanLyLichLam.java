package com.example.tdchotel_manager.Menu_QuanLy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;


import com.example.tdchotel_manager.Model.ca_lam;
import com.example.tdchotel_manager.Model.chuc_vu;
import com.example.tdchotel_manager.Model.nhan_vien;
import com.example.tdchotel_manager.Model.phan_cong;
import com.example.tdchotel_manager.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class QuanLyLichLam extends AppCompatActivity {

    private RecyclerView rcvLichLam;
    private ImageButton btn_back;
    List<phan_cong> phanCongList12 = new ArrayList<>();
    List<ca_lam> caLamList = new ArrayList<>();
    List<nhan_vien> nhanVienList = new ArrayList<>();
    List<chuc_vu> chucVuList = new ArrayList<>();
    List<phan_cong> phanCongList = new ArrayList<>();
    private LichLamAdapter lichLamAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_lich_lam);
        setControl();
        setEvent();
    }

    private void setEvent() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuanLyLichLam.this, Fragment_Nhanvien.class);
                startActivity(intent);
            }
        });
    }

    private void LoadChucVu() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("chuc_vu");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chucVuList.clear();

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
    private Task<Void> LoadPhanCong(){
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("phan_cong");
        CompletableFuture<Void> completableFuture = new CompletableFuture<>();

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                phan_cong phanCong = snapshot.getValue(phan_cong.class);
                if(phanCong!=null)
                {   Log.e("kkkkkkk","");
                    phanCongList.add(phanCong);
                    lichLamAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        ref1.addChildEventListener(childEventListener);
        return Tasks.forResult(null);


    }
    private void LoadNhanVien() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("nhan_vien");
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("phan_cong");

        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        nhanVienList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                            nhan_vien nhan_vien = dataSnapshot.getValue(nhan_vien.class);
                            if (!nhan_vien.getId_chuc_vu().equals("3")) {


                                String idNhanVien = nhan_vien.getId_nhan_vien();


                                ref1.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        List<phan_cong> phanCongList1 = new ArrayList<>();

                                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                            phan_cong phan_cong = snapshot1.getValue(phan_cong.class);
                                            if (nhan_vien.getId_nhan_vien().toString().equals(phan_cong.getId_nhan_vien().toString())) {
                                                phanCongList1.add(phan_cong);
                                            }
                                        }

                                        nhan_vien.setPhanCongList(phanCongList1);
                                        nhanVienList.add(nhan_vien);
                                        for (int i = 0; i < nhanVienList.size(); i++) {
                                            for (int j = 0; j < nhanVienList.size(); j++) {
                                                if (j != i && nhanVienList.get(i).getId_nhan_vien().equals(nhanVienList.get(j).getId_nhan_vien())) {
                                                    try {
                                                        nhanVienList.remove(i);
                                                    } catch (Exception e) {
                                                        Log.e("Error", e.getMessage());
                                                    }
                                                }
                                            }
                                        }
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

                        lichLamAdapter.notifyDataSetChanged();
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(String.valueOf(nhanVienList.size()) + " hao", "");
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//    ref.addChildEventListener(new ChildEventListener() {
//        @Override
//        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//            nhan_vien nhanVien = snapshot.getValue(nhan_vien.class);
//            if(nhanVien!=null)
//            {
//                ref1.addListenerForSingleValueEvent(new ValueEventListener() {
//                       @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            phanCongList.clear();
//                            List<phan_cong> phanCongList1 = new ArrayList<>();
//
//                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
//                                phan_cong phan_cong = snapshot1.getValue(phan_cong.class);
//                                phanCongList.add(phan_cong);
//                                if(nhanVien.getId_nhan_vien().toString().equals(phan_cong.getId_nhan_vien().toString()))
//                                {
//                                    phanCongList1.add(phan_cong);
//                                }
//                            }
//
//                            nhanVien.setPhanCongList(phanCongList1);
//                            nhanVienList.add(nhanVien);
//
//                            lichLamAdapter.notifyDataSetChanged();
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//
//                lichLamAdapter.notifyDataSetChanged();
//            }
//        }
//
//        @Override
//        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//        }
//
//        @Override
//        public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//        }
//
//        @Override
//        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//        }
//
//        @Override
//        public void onCancelled(@NonNull DatabaseError error) {
//
//        }
//    });


    }


    private void getListNhanVien(List<nhan_vien> nhanVienList1) {
        nhanVienList.addAll(nhanVienList1);
    }

    private void setControl() {
        rcvLichLam = findViewById(R.id.rcvLichLam);
        btn_back = findViewById(R.id.btn_back);


        LoadNhanVien();
        LoadPhanCong();
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
        lichLamAdapter = new LichLamAdapter(nhanVienList, chucVuList, caLamList, new LichLamAdapter.IClickListener() {
            @Override
            public void onClickUpdateItem(nhan_vien nhan_vien) {
                openDialogUpdateItem(nhan_vien);
            }


        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvLichLam.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvLichLam.addItemDecoration(decoration);
        rcvLichLam.setAdapter(lichLamAdapter);
    }
    private void openDialogUpdateItem(nhan_vien nhan_vien) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_update_lich_lam);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT
        );
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        CheckBox cbSangT2 = dialog.findViewById(R.id.cbSangT2);
        CheckBox cbChieuT2 = dialog.findViewById(R.id.cbChieuT2);
        CheckBox cbSangT3 = dialog.findViewById(R.id.cbSangT3);
        CheckBox cbChieuT3 = dialog.findViewById(R.id.cbChieuT3);
        CheckBox cbSangT4 = dialog.findViewById(R.id.cbSangT4);
        CheckBox cbChieuT4 = dialog.findViewById(R.id.cbChieuT4);
        CheckBox cbSangT5 = dialog.findViewById(R.id.cbSangT5);
        CheckBox cbChieuT5 = dialog.findViewById(R.id.cbChieuT5);
        CheckBox cbSangT6 = dialog.findViewById(R.id.cbSangT6);
        CheckBox cbChieuT6 = dialog.findViewById(R.id.cbChieuT6);
        CheckBox cbSangT7 = dialog.findViewById(R.id.cbSangT7);
        CheckBox cbChieuT7 = dialog.findViewById(R.id.cbChieuT7);
        CheckBox cbSangCN = dialog.findViewById(R.id.cbSangCN);
        CheckBox cbChieuCN = dialog.findViewById(R.id.cbChieuCN);
        Button btnLuu = dialog.findViewById(R.id.btnLuu);
        Button btnThoat = dialog.findViewById(R.id.btnThoat);
        int thu2Sang = -1,thu2Chieu = -1, thu3Sang  = -1, thu3Chieu  = -1, thu4Sang = -1, thu4Chieu = -1, thu5Sang = -1, thu5Chieu = -1, thu6Sang = -1, thu6Chieu = -1, thu7Sang = -1, thu7Chieu = -1, CNSang = -1, CNChieu = -1;
        for(int i = 0; i < nhan_vien.getPhanCongList().size(); i++)
        {
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
            cbSangT2.setChecked(true);
        }
        else
        {
            cbSangT2.setChecked(false);
        }
        if(thu2Chieu==1)
        {
            cbChieuT2.setChecked(true);
        }
        else
        {
            cbChieuT2.setChecked(false);
        }
        if(thu3Sang==1)
        {
            cbSangT3.setChecked(true);
        }
        else
        {
            cbSangT3.setChecked(false);
        }
        if(thu3Chieu==1)
        {
            cbChieuT3.setChecked(true);
        }
        else
        {
            cbChieuT3.setChecked(false);
        }
        if(thu4Sang==1)
        {
            cbSangT4.setChecked(true);
        }
        else
        {
            cbSangT4.setChecked(false);
        }
        if(thu4Chieu==1)
        {
            cbChieuT4.setChecked(true);
        }
        else
        {
            cbChieuT4.setChecked(false);
        }
        if(thu5Sang==1)
        {
            cbSangT5.setChecked(true);
        }
        else
        {
            cbSangT5.setChecked(false);
        }
        if(thu5Chieu==1)
        {
            cbChieuT5.setChecked(true);
        }
        else
        {
            cbChieuT5.setChecked(false);
        }
        if(thu6Sang==1)
        {
            cbSangT6.setChecked(true);
        }
        else
        {
            cbSangT6.setChecked(false);
        }
        if(thu6Chieu==1)
        {
            cbChieuT6.setChecked(true);
        }
        else
        {
            cbChieuT6.setChecked(false);
        }
        if(thu7Sang==1)
        {
            cbSangT7.setChecked(true);
        }
        else
        {
            cbSangT7.setChecked(false);
        }
        if(thu7Chieu==1)
        {
            cbChieuT7.setChecked(true);
        }
        else
        {
            cbChieuT7.setChecked(false);
        }
        if(CNSang==1)
        {
            cbSangCN.setChecked(true);
        }
        else
        {
            cbSangCN.setChecked(false);
        }
        if(CNChieu==1)
        {
            cbChieuCN.setChecked(true);
        }
        else
        {
            cbChieuCN.setChecked(false);
        }
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {

                if(cbChieuT2.isChecked())
                {
                    Log.e("Đã1","");
                    themLichLam(nhan_vien,"2",2);
                }
                else
                {
                    xoaLichLam(nhan_vien,"2",2);
                }
                if(cbSangT2.isChecked())
                {
                    themLichLam(nhan_vien,"1",2);
                    Log.e("Đã","");
                }
                else
                {
                    xoaLichLam(nhan_vien,"1",2);
                }
                if(cbChieuT3.isChecked())
                {
                    Log.e("Đã3","");
                    themLichLam(nhan_vien,"2",3);
                }
                else
                {
                    xoaLichLam(nhan_vien,"2",3);
                }
                if(cbSangT3.isChecked())
                {
                    Log.e("Đã3","");
                    themLichLam(nhan_vien,"1",3);
                }
                else
                {
                    xoaLichLam(nhan_vien,"1",3);
                }
                if(cbChieuT4.isChecked())
                {
                    Log.e("Đã3","");
                    themLichLam(nhan_vien,"2",4);
                }
                else
                {
                    xoaLichLam(nhan_vien,"2",4);
                }
                if(cbSangT4.isChecked())
                {
                    Log.e("Đã3","");
                    themLichLam(nhan_vien,"1",4);
                }
                else
                {
                    xoaLichLam(nhan_vien,"1",4);
                }
                if(cbChieuT5.isChecked())
                {
                    Log.e("Đã3","");
                    themLichLam(nhan_vien,"2",5);
                }
                else
                {
                    xoaLichLam(nhan_vien,"2",5);
                }
                if(cbSangT5.isChecked())
                {
                    Log.e("Đã3","");
                    themLichLam(nhan_vien,"1",5);
                }
                else
                {
                    xoaLichLam(nhan_vien,"1",5);
                }
                if(cbChieuT6.isChecked())
                {
                    Log.e("Đã3","");
                    themLichLam(nhan_vien,"2",6);
                }
                else
                {
                    xoaLichLam(nhan_vien,"2",6);
                }
                if(cbSangT6.isChecked())
                {
                    Log.e("Đã3","");
                    themLichLam(nhan_vien,"1",6);
                }
                else
                {
                    xoaLichLam(nhan_vien,"1",6);
                }
                if(cbChieuT7.isChecked())
                {
                    Log.e("Đã3","");
                    themLichLam(nhan_vien,"2",7);
                }
                else
                {
                    xoaLichLam(nhan_vien,"2",7);
                }
                if(cbSangT7.isChecked())
                {
                    Log.e("Đã3","");
                    themLichLam(nhan_vien,"1",7);
                }
                else
                {
                    xoaLichLam(nhan_vien,"1",7);
                }
                if(cbChieuCN.isChecked())
                {
                    Log.e("Đã3","");
                    themLichLam(nhan_vien,"2",1);
                }
                else
                {
                    xoaLichLam(nhan_vien,"2",1);
                }
                if(cbSangCN.isChecked())
                {
                    Log.e("Đã3","");
                    themLichLam(nhan_vien,"1",1);
                }
                else
                {
                    xoaLichLam(nhan_vien,"1",1);
                }
            }
        });
        dialog.show();
    }

    private void xoaLichLam(nhan_vien nhan_vien,String idCa,int date) {
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("phan_cong");
        ref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    phan_cong phanCongXoa = dataSnapshot.getValue(phan_cong.class);
                    if(phanCongXoa.getId_nhan_vien().equals(nhan_vien.getId_nhan_vien())&&phanCongXoa.getId_ca_lam().equals(idCa)&&phanCongXoa.getDayofweek()==date)
                    {
                        ref1.child(phanCongXoa.getId_phan_cong()).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(QuanLyLichLam.this, "Xóa", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void themLichLam(nhan_vien nhan_vien,String idCa,int date) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("phan_cong");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int kiemTra = 0;
                int dem = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    phan_cong phanCongTim = dataSnapshot.getValue(phan_cong.class);
                    if(phanCongTim.getId_nhan_vien().equals(nhan_vien.getId_nhan_vien())&&phanCongTim.getId_ca_lam().equals(idCa)&&phanCongTim.getDayofweek()==date)
                    {
                        Log.e("Đúng","dhasb");
                        kiemTra = 1;
                        break;
                    }
                    else
                    {
                        Log.e("Sai","dhasb");
                    }
                }
                lichLamAdapter.notifyDataSetChanged();
                if(kiemTra == 0)
                {

                    phan_cong phanCong = null;
                    if (!phanCongList.isEmpty()) {

                        String idPhanCong = ref.push().getKey();
                        phanCong = new phan_cong(idPhanCong, nhan_vien.getId_nhan_vien(),idCa,date );
                    } else {
                        phanCong = new phan_cong("1", nhan_vien.getId_nhan_vien(),idCa,date );
                    }
                    Map<String,Object> taskMap = new HashMap<>();
                    taskMap.put("id_phan_cong", phanCong.getId_phan_cong().toString());
                    taskMap.put("id_nhan_vien", phanCong.getId_nhan_vien().toString());
                    taskMap.put("id_ca_lam", phanCong.getId_ca_lam().toString());
                    taskMap.put("dayofweek", phanCong.getDayofweek());
                    ref.child(phanCong.getId_phan_cong()).setValue(taskMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            Toast.makeText(QuanLyLichLam.this, "Thêm", Toast.LENGTH_SHORT).show();
                            if(error==null)
                            {
                                LoadPhanCong();
                            }

                        }
                    });

                } else if(kiemTra == 1)
                {
                    Toast.makeText(QuanLyLichLam.this, "Đã thêm", Toast.LENGTH_SHORT).show();
                    Log.e("Đã thêm","");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}