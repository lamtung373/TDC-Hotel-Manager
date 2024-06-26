package com.example.tdchotel_manager.Le_Tan.Fragment_LeTan;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.tdchotel_manager.Le_Tan.DaDatAdapter;
import com.example.tdchotel_manager.Le_Tan.DuyetCocAdapter;
import com.example.tdchotel_manager.Model.hoa_don;
import com.example.tdchotel_manager.Model.khach_hang;
import com.example.tdchotel_manager.Model.phong;
import com.example.tdchotel_manager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Duyetcoc#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Duyetcoc extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<hoa_don> hoaDonList = new ArrayList<>();
    private List<khach_hang> khachHangList = new ArrayList<>();
    private List<phong> phongList = new ArrayList<>();
    private RecyclerView rcv_roomlist;
    private EditText edt_search;
    private DuyetCocAdapter duyetCocAdapter;
    public Fragment_Duyetcoc() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Duyetcoc.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Duyetcoc newInstance(String param1, String param2) {
        Fragment_Duyetcoc fragment = new Fragment_Duyetcoc();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__duyetcoc, container, false);
        setControl(view);
        setEvent(view);
        return view;
    }

    private void setEvent(View view) {
    }

    private void setControl(View view) {
        rcv_roomlist = view.findViewById(R.id.rcv_roomlist);
        edt_search = view.findViewById(R.id.edt_search);
        LoadHoaDon();
        LoadPhong();

        duyetCocAdapter = new DuyetCocAdapter(getActivity(),hoaDonList,phongList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rcv_roomlist.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        rcv_roomlist.addItemDecoration(decoration);
        rcv_roomlist.setAdapter(duyetCocAdapter);
    }

    private void LoadHoaDon() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("hoa_don");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                hoaDonList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        hoa_don hoaDon = dataSnapshot1.getValue(hoa_don.class);

                        if(hoaDon.getThoi_gian_duyet().equals("") && hoaDon.getCCCD().get(0).equals("") && hoaDon.getCCCD().get(1).equals(""))
                        {
                            Log.e("eee"+hoaDon.getThoi_gian_huy()+"ee","555"+hoaDon.getThoi_gian_thanh_toan()+"dfsf");
                            if(hoaDon.getThoi_gian_thanh_toan().equals("")&&hoaDon.getThoi_gian_huy().equals(""))
                            {
                                Log.e("eee","666");
                                hoaDonList.add(hoaDon);
                            }
                        }
                    }
                }
                duyetCocAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void LoadPhong() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("phong");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                phongList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    phong phong = dataSnapshot.getValue(phong.class);
                    phongList.add(phong);
                }
                duyetCocAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}