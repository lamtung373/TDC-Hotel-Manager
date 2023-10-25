package com.example.tdchotel_manager.Menu_QuanLy;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.tdchotel_manager.Menu_QuanLy.Adapter_Trangchu.adapter_calam;
import com.example.tdchotel_manager.Model.hoa_don;
import com.example.tdchotel_manager.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Trangchu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Trangchu extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    PieChart piechart_tinhhinhphong;
    Button btn_chamcong;
    RecyclerView rcv_casang, rcv_catoi;
    public Fragment_Trangchu() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Trangchu.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Trangchu newInstance(String param1, String param2) {
        Fragment_Trangchu fragment = new Fragment_Trangchu();
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
        View view = inflater.inflate(R.layout.fragment__trangchu, container, false);
        setControl(view);
        Initialization();
        setEvent();
        return view;
    }

    private void Initialization() {
        Chart();
        adapter_calam adapterCalam=new adapter_calam();
        rcv_casang.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        rcv_casang.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        rcv_casang.setAdapter(adapterCalam);
    }

    private void setEvent() {

    }

    private void Chart() {
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry((int) 5, "Hoạt động"));
        pieEntries.add(new PieEntry((int) 15, "Trống"));
        pieEntries.add(new PieEntry((int) 2, "Sửa"));

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Tình hình phòng");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.WHITE);
        pieDataSet.setValueTextSize(10f);
        PieData pieData = new PieData(pieDataSet);
        piechart_tinhhinhphong.setData(pieData);
        piechart_tinhhinhphong.getDescription().setEnabled(false);
//        piechart_tinhhinhphong.setCenterText("center");
        piechart_tinhhinhphong.animateXY(1000, 1000);
        piechart_tinhhinhphong.animate();
    }

    private void setControl(View view) {
        piechart_tinhhinhphong = view.findViewById(R.id.piechart_tinhhinhphong);
        btn_chamcong = view.findViewById(R.id.btn_chamcong);
        rcv_casang = view.findViewById(R.id.rcv_casang);
        rcv_catoi = view.findViewById(R.id.rcv_catoi);
    }
}