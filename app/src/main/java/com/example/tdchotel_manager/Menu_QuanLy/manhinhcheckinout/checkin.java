package com.example.tdchotel_manager.Menu_QuanLy.manhinhcheckinout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdchotel_manager.Menu_QuanLy.Adapter_NhanVien.NhanVienCheckInOutAdapter;
import com.example.tdchotel_manager.Model.chuc_vu;
import com.example.tdchotel_manager.Model.nhan_vien;
import com.example.tdchotel_manager.Model.phan_cong;
import com.example.tdchotel_manager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link checkin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class checkin extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView rcvCheckIn;
    private List<nhan_vien> nhanVien;
    private NhanVienCheckInOutAdapter nhanVienAdapter;
    private List<phan_cong> phanCongList = new ArrayList<>();
    private List<chuc_vu> chucVuList = new ArrayList<>();
    public checkin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment checkin.
     */
    // TODO: Rename and change types and number of parameters
    public static checkin newInstance(String param1, String param2) {
        checkin fragment = new checkin();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkin, container, false);
        setControl(view);
        return view;
    }
    private void LoadChucVu() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("chuc_vu");
        DatabaseReference refNhanVien = FirebaseDatabase.getInstance().getReference("nhan_vien");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chucVuList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    chuc_vu chucVu = dataSnapshot.getValue(chuc_vu.class);
                    chucVuList.add(chucVu);

                }
                nhanVienAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        refNhanVien.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nhanVien.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    nhan_vien nhan_vien = dataSnapshot.getValue(nhan_vien.class);
                    nhanVien.add(nhan_vien);
                }
                nhanVienAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void setControl(View view) {
        rcvCheckIn = view.findViewById(R.id.rcvCheckIn);
        loadCheckIn();
        LoadChucVu();
        nhanVienAdapter = new NhanVienCheckInOutAdapter(nhanVien,chucVuList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rcvCheckIn.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        rcvCheckIn.addItemDecoration(decoration);
        rcvCheckIn.setAdapter(nhanVienAdapter);
    }

    private void loadCheckIn() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("nhan_vien");
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("phan_cong");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    phan_cong phanCong = dataSnapshot.getValue(phan_cong.class);
                    Calendar ca= Calendar.getInstance();
                    int dayofweek = ca.get(Calendar.DAY_OF_WEEK);
                    if(phanCong.getDayofweek()==dayofweek)
                    {
                        phanCongList.add(phanCong);
                    }

                }
                nhanVienAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}