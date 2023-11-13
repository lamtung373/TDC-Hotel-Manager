package com.example.tdchotel_manager.Menu_QuanLy;

import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tdchotel_manager.Model.chuc_vu;
import com.example.tdchotel_manager.Model.hoa_don;
import com.example.tdchotel_manager.Model.nhan_vien;
import com.example.tdchotel_manager.Model.phong;
import com.example.tdchotel_manager.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Thongke#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Thongke extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Spinner sp_thoigian, sp_loaithongke, sp_nam, sp_nv;
    ArrayList<String> arr_sp_loaithongke = new ArrayList<>();
    ArrayList<String> arr_sp_thoigian = new ArrayList<>();
    ArrayList<String> arr_sp_nam = new ArrayList<>();
    ArrayList<nhan_vien> arr_sp_nv = new ArrayList<>();
    BarChart barchart_thongke;
    TextView tvthoigian, tv_nam, tv_nv;
    Button btnThongke;

    public Fragment_Thongke() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Thongke.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Thongke newInstance(String param1, String param2) {
        Fragment_Thongke fragment = new Fragment_Thongke();
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
        View view = inflater.inflate(R.layout.fragment__thongke, container, false);
        setControl(view);
        Initialization();
        setEvent();
        return view;
    }

    private void setEvent() {
        sp_loaithongke.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Them thong tin can thong ke
                if (arr_sp_loaithongke.get(i).toString().equals("Lượt thuê phòng") || arr_sp_loaithongke.get(i).toString().equals("Đánh giá tốt")) {
                    sp_thoigian.setVisibility(View.GONE);
                    sp_nam.setVisibility(View.GONE);
                    tvthoigian.setVisibility(View.GONE);
                    tv_nam.setVisibility(View.GONE);
                    tv_nv.setVisibility(View.GONE);
                    sp_nv.setVisibility(View.GONE);
                } else {
                    if (arr_sp_loaithongke.get(i).toString().equals("Hiệu suất lao công")) {
                        tv_nv.setVisibility(View.VISIBLE);
                        sp_nv.setVisibility(View.VISIBLE);
                    } else {
                        tv_nv.setVisibility(View.GONE);
                        sp_nv.setVisibility(View.GONE);
                    }
                    sp_thoigian.setVisibility(View.VISIBLE);
                    sp_nam.setVisibility(View.VISIBLE);
                    tvthoigian.setVisibility(View.VISIBLE);
                    tv_nam.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sp_thoigian.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (sp_thoigian.getSelectedItem().toString().equals("Tháng")) {
                    sp_nam.setVisibility(View.VISIBLE);
                    tv_nam.setVisibility(View.VISIBLE);
                } else {
                    tv_nam.setVisibility(View.GONE);
                    sp_nam.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnThongke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                //Thong ke
//                if (sp_loaithongke.getSelectedItem().toString().equals("Doanh thu")) {
//                    if (sp_thoigian.getSelectedItem().toString().equals("Tháng")) {
//
//                        Doanh_Thu_Thang();
//                    } else {
//                        Doanh_Thu_Nam();
//                    }
//                } else if (sp_loaithongke.getSelectedItem().toString().equals("Lượt thuê phòng")) {
//                    Thong_Ke_Luot_Thue_Phong();
//                } else if (sp_loaithongke.getSelectedItem().toString().equals("Đánh giá tốt")) {
//                    Thong_Ke_Danh_Gia_Tot();
//                } else if (sp_loaithongke.getSelectedItem().toString().equals("Hiệu suất lao công")) {
//                    if (sp_thoigian.getSelectedItem().toString().equals("Tháng")) {
//                        Hieu_Suat_Thang();
//                    } else {
//                        Hieu_Suat_Nam();
//                    }
//                }
            }
        });
    }

//    private void Hieu_Suat_Nam() {
//        ArrayList<BarEntry> barEntries = new ArrayList<>();
//
//        ArrayList<String> labels = new ArrayList<>();
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("hoa_don");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                labels.clear();
//                barEntries.clear();
//                Date thoi_gian_coc = null;
//                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
//                for (int i = 0; i < arr_sp_nam.size(); i++) {
//                    labels.add(arr_sp_nam.get(i));
//                    double dem = 0;
//                    for (DataSnapshot item : snapshot.getChildren()) {
//                        hoa_don hoa_don = item.getValue(hoa_don.class);
//                        if (hoa_don.getId_lao_cong().equals(arr_sp_nv.get(sp_nv.getSelectedItemPosition()).getId_nhan_vien())) {
//                            try {
//                                //Chuyen doi thoi gian coc tu firebase
//                                thoi_gian_coc = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(hoa_don.getThoi_gian_coc());
//
//                            } catch (ParseException e) {
//                                Log.e("Lỗi chuyển đổi dữ liệu thời gian thanh toán", e.getMessage());
//                            }
//
//
//                            if (dateFormat.format(thoi_gian_coc.getTime()).equals(arr_sp_nam.get(i))) {
//                                dem++;
//                            }
//                        }
//                    }
//                    barEntries.add(new BarEntry(i, (float) dem));
//
//
//                }
//                if (!barEntries.isEmpty()) {
//                    BarDataSet barDataSet = new BarDataSet(barEntries, "Hiệu suất theo năm");
//                    barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
//
//                    BarData barData = new BarData(barDataSet);
//                    barchart_thongke.getDescription().setEnabled(false);
//                    barchart_thongke.setData(barData);
//                    barchart_thongke.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
//                    barchart_thongke.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
//                    barchart_thongke.getXAxis().setGranularity(1f);
//                    barchart_thongke.getXAxis().setLabelCount(12);
//                    barchart_thongke.getXAxis().setGranularityEnabled(true);
//                    barchart_thongke.animateY(1000);
//                    barchart_thongke.invalidate();
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

//    private void Hieu_Suat_Thang() {
//        ArrayList<BarEntry> barEntries = new ArrayList<>();
//
//        ArrayList<String> labels = new ArrayList<>();
//
//        DatabaseReference reference_hoadon = FirebaseDatabase.getInstance().getReference("hoa_don");
//        reference_hoadon.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                labels.clear();
//                barEntries.clear();
//                Date thoi_gian_coc = null;
//                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy");
//                for (int i = 0; i < 12; i++) {
//                    labels.add("" + (i + 1));
//                    long dem = 0;
//                    for (DataSnapshot item : snapshot.getChildren()) {
//                        hoa_don hoa_don = item.getValue(hoa_don.class);
//                        if (hoa_don.getId_lao_cong().equals(arr_sp_nv.get(sp_nv.getSelectedItemPosition()).getId_nhan_vien())) {
//                            try {
//
//                                //Chuyen doi thoi gian coc tu firebase
//                                thoi_gian_coc = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(hoa_don.getThoi_gian_coc());
//
//                            } catch (ParseException e) {
//                                Log.e("Lỗi chuyển đổi dữ liệu thời gian thanh toán", e.getMessage());
//                            }
//                            String thang = "";
//                            if (i + 1 < 10) {
//                                thang = "0" + (i + 1);
//                            } else {
//                                thang = "" + (i + 1);
//                            }
//                            if (dateFormat.format(thoi_gian_coc.getTime()).equals(thang + "/" + sp_nam.getSelectedItem().toString())) {
//                                dem++;
//                            }
//                        }
//                        barEntries.add(new BarEntry(i, (float) dem));
//                    }
//
//                }
//                if (!barEntries.isEmpty()) {
//                    BarDataSet barDataSet = new BarDataSet(barEntries, "Hiệu suất theo tháng");
//                    barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
//
//                    BarData barData = new BarData(barDataSet);
//                    barchart_thongke.getDescription().setEnabled(false);
//                    barchart_thongke.setData(barData);
//                    barchart_thongke.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
//                    barchart_thongke.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
//                    barchart_thongke.getXAxis().setGranularity(1f);
//                    barchart_thongke.getXAxis().setLabelCount(12);
//                    barchart_thongke.getXAxis().setGranularityEnabled(true);
//                    barchart_thongke.animateY(1000);
//                    barchart_thongke.invalidate();
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

//    private void Doanh_Thu_Nam() {
//        ArrayList<BarEntry> barEntries = new ArrayList<>();
//
//        ArrayList<String> labels = new ArrayList<>();
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("hoa_don");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                labels.clear();
//                barEntries.clear();
//                Date thoi_gian_coc = null;
//                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
//                for (int i = 0; i < arr_sp_nam.size(); i++) {
//                    labels.add(arr_sp_nam.get(i));
//                    double tongtien = 0;
//                    for (DataSnapshot item : snapshot.getChildren()) {
//                        hoa_don hoa_don = item.getValue(hoa_don.class);
//                        try {
//                            //Chuyen doi thoi gian coc tu firebase
//                            thoi_gian_coc = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(hoa_don.getThoi_gian_coc());
//
//                        } catch (ParseException e) {
//                            Log.e("Lỗi chuyển đổi dữ liệu thời gian thanh toán", e.getMessage());
//                        }
//
//
//                        if (dateFormat.format(thoi_gian_coc.getTime()).equals(arr_sp_nam.get(i))) {
//                            tongtien += hoa_don.getTong_thanh_toan();
//                        }
//                    }
//                    barEntries.add(new BarEntry(i, (float) tongtien));
//
//                }
//                if (!barEntries.isEmpty()) {
//                    BarDataSet barDataSet = new BarDataSet(barEntries, "Doanh thu theo năm");
//                    barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
//
//                    BarData barData = new BarData(barDataSet);
//                    barchart_thongke.getDescription().setEnabled(false);
//                    barchart_thongke.setData(barData);
//                    barchart_thongke.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
//                    barchart_thongke.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
//                    barchart_thongke.getXAxis().setGranularity(1f);
//                    barchart_thongke.getXAxis().setLabelCount(12);
//                    barchart_thongke.getXAxis().setGranularityEnabled(true);
//                    barchart_thongke.animateY(1000);
//                    barchart_thongke.invalidate();
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

    private void Thong_Ke_Danh_Gia_Tot() {
        ArrayList<phong> arr_DanhGia = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("phong");
        reference.orderByChild("danh_gia_sao").startAt(4).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arr_DanhGia.clear();
                //Lay du lieu firebase
                for (DataSnapshot item : snapshot.getChildren()) {
                    phong phong = item.getValue(phong.class);
                    arr_DanhGia.add(phong);
                }

                //Dua du lieu vao chart
                barchart_thongke.getDescription().setEnabled(false);
                ArrayList<BarEntry> barEntries = new ArrayList<>();
                barEntries.clear();
                ArrayList<String> labels = new ArrayList<>();

                if (arr_DanhGia.size() > 10) {
                    for (int i = 0; i < 10; i++) {
                        barEntries.add(new BarEntry(i, (float) arr_DanhGia.get(i).getDanh_gia_sao()));
                        labels.add(arr_DanhGia.get(i).getTen_phong());

                    }
                } else {
                    for (int i = 0; i < arr_DanhGia.size(); i++) {
                        barEntries.add(new BarEntry(i, (float) arr_DanhGia.get(i).getDanh_gia_sao()));
                        labels.add(arr_DanhGia.get(i).getTen_phong());

                    }
                }
                if (!barEntries.isEmpty()) {
                    BarDataSet barDataSet = new BarDataSet(barEntries, "Đánh giá tốt");
                    barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    BarData barData = new BarData(barDataSet);
                    barchart_thongke.setData(barData);
                    barchart_thongke.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
                    barchart_thongke.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                    barchart_thongke.getXAxis().setGranularity(1f);
                    barchart_thongke.getXAxis().setLabelCount(10);
                    barchart_thongke.getXAxis().setGranularityEnabled(true);
                    barchart_thongke.animateY(2000);
                    barchart_thongke.invalidate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void Thong_Ke_Luot_Thue_Phong() {
        ArrayList<phong> arr_LuotThue = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("phong");
        reference.orderByChild("luot_thue").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //Lay du lieu firebase
                for (DataSnapshot item : snapshot.getChildren()) {
                    phong phong = item.getValue(com.example.tdchotel_manager.Model.phong.class);
                    arr_LuotThue.add(0, phong);
                }

                //Dua du lieu vao chart
                barchart_thongke.getDescription().setEnabled(false);
                ArrayList<BarEntry> barEntries = new ArrayList<>();
                barEntries.clear();
                ArrayList<String> labels = new ArrayList<>();
                labels.clear();
                if (arr_LuotThue.size() > 10) {
                    for (int i = 0; i < 10; i++) {
                        barEntries.add(new BarEntry(i, arr_LuotThue.get(i).getLuot_thue()));
                        labels.add(arr_LuotThue.get(i).getTen_phong());

                    }
                } else {
                    for (int i = 0; i < arr_LuotThue.size(); i++) {
                        barEntries.add(new BarEntry(i, arr_LuotThue.get(i).getLuot_thue()));
                        labels.add(arr_LuotThue.get(i).getTen_phong());

                    }
                }
                if (!barEntries.isEmpty()) {
                    BarDataSet barDataSet = new BarDataSet(barEntries, "Lượt thuê");
                    barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

                    BarData barData = new BarData(barDataSet);
                    barchart_thongke.setData(barData);

                    barchart_thongke.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
                    barchart_thongke.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                    barchart_thongke.getXAxis().setGranularity(1f);
                    barchart_thongke.getXAxis().setLabelCount(10);
                    barchart_thongke.getXAxis().setGranularityEnabled(true);
                    barchart_thongke.animateY(1000);
                    barchart_thongke.invalidate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

//    private void Doanh_Thu_Thang() {
//        ArrayList<BarEntry> barEntries = new ArrayList<>();
//
//        ArrayList<String> labels = new ArrayList<>();
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("hoa_don");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                labels.clear();
//                barEntries.clear();
//                Date thoi_gian_coc = null;
//                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy");
//                for (int i = 0; i < 12; i++) {
//                    labels.add("" + (i + 1));
//                    double tongtien = 0;
//                    for (DataSnapshot item : snapshot.getChildren()) {
//                        hoa_don hoa_don = item.getValue(hoa_don.class);
//                        try {
//
//                            //Chuyen doi thoi gian coc tu firebase
//                            thoi_gian_coc = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(hoa_don.getThoi_gian_coc());
//
//                        } catch (ParseException e) {
//                            Log.e("Lỗi chuyển đổi dữ liệu thời gian thanh toán", e.getMessage());
//                        }
//                        String thang = "";
//                        if (i + 1 < 10) {
//                            thang = "0" + (i + 1);
//                        } else {
//                            thang = "" + (i + 1);
//                        }
//                        if (dateFormat.format(thoi_gian_coc.getTime()).equals(thang + "/" + sp_nam.getSelectedItem().toString())) {
//                            tongtien += hoa_don.getTong_thanh_toan();
//                        }
//                    }
//                    barEntries.add(new BarEntry(i, (float) tongtien));
//
//                }
//                if (!barEntries.isEmpty()) {
//                    BarDataSet barDataSet = new BarDataSet(barEntries, "Doanh thu theo tháng");
//                    barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
//
//                    BarData barData = new BarData(barDataSet);
//                    barchart_thongke.getDescription().setEnabled(false);
//                    barchart_thongke.setData(barData);
//                    barchart_thongke.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
//                    barchart_thongke.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
//                    barchart_thongke.getXAxis().setGranularity(1f);
//                    barchart_thongke.getXAxis().setLabelCount(12);
//                    barchart_thongke.getXAxis().setGranularityEnabled(true);
//                    barchart_thongke.animateY(1000);
//                    barchart_thongke.invalidate();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//    }


    private void Initialization() {

        //spinner loai thong ke
        arr_sp_loaithongke.add("Doanh thu");
        arr_sp_loaithongke.add("Lượt thuê phòng");
        arr_sp_loaithongke.add("Hiệu suất lao công");
        arr_sp_loaithongke.add("Đánh giá tốt");
        sp_loaithongke.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, arr_sp_loaithongke));

        //spinner thoi gian
        arr_sp_thoigian.add("Tháng");
        arr_sp_thoigian.add("Năm");
        sp_thoigian.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, arr_sp_thoigian));

        //spinner thoi gian
        ArrayAdapter adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, arr_sp_nam);
        sp_nam.setAdapter(adapter);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("hoa_don");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                arr_sp_nam.clear();
//                Date thoi_gian_hoa_don = null;
//                java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy");
//                for (DataSnapshot item : snapshot.getChildren()) {
//                    hoa_don hoa_don = item.getValue(com.example.tdchotel_manager.Model.hoa_don.class);
//                    try {
//                        thoi_gian_hoa_don = new SimpleDateFormat("dd/MM/yyyy").parse(hoa_don.getThoi_gian_coc());
//                        if (arr_sp_nam.size() > 0) {
//                            boolean check_contain = false;
//
//                            //kiem tra su ton tai cua nam muon them
//                            for (int i = 0; i < arr_sp_nam.size(); i++) {
//                                if (arr_sp_nam.get(i).equals(dateFormat.format(thoi_gian_hoa_don))) {
//                                    check_contain = true;
//
//                                }
//                            }
//                            if (check_contain == false) {
//                                arr_sp_nam.add(dateFormat.format(thoi_gian_hoa_don));
//                            }
//                        } else {
//                            arr_sp_nam.add(dateFormat.format(thoi_gian_hoa_don));
//                        }
//                    } catch (ParseException e) {
//                        Log.e("Lỗi chuyển thời gian", e.getMessage());
//                    }
//                }
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        //spinner nhan vien
        ArrayList<String> arr_sp_nv1 = new ArrayList<>();
        ArrayAdapter adapter_nv = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, arr_sp_nv1);
        sp_nv.setAdapter(adapter_nv);
        DatabaseReference reference_nv = FirebaseDatabase.getInstance().getReference("nhan_vien");
        DatabaseReference reference_chucvu = FirebaseDatabase.getInstance().getReference("chuc_vu");
        reference_nv.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arr_sp_nv.clear();
                arr_sp_nv1.clear();
                for (DataSnapshot item : snapshot.getChildren()) {
                    nhan_vien nv = item.getValue(nhan_vien.class);
                    reference_chucvu.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot item : snapshot.getChildren()) {
                                chuc_vu cv = item.getValue(chuc_vu.class);
                                if (nv.getId_chuc_vu().equals(cv.getId_chuc_vu()) && cv.getTen_chuc_vu().equals("Lao công")) {
                                    arr_sp_nv1.add(nv.getTen_nhan_vien());
                                    arr_sp_nv.add(nv);
                                }
                            }
                            adapter_nv.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
                adapter_nv.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void setControl(View view) {
        sp_thoigian = view.findViewById(R.id.sp_thoigian);
        sp_loaithongke = view.findViewById(R.id.sp_loaithongke);
        sp_nam = view.findViewById(R.id.sp_nam);
        sp_nv = view.findViewById(R.id.sp_nv);
        barchart_thongke = view.findViewById(R.id.barchart_thongke);
        tvthoigian = view.findViewById(R.id.tvthoigian);
        tv_nam = view.findViewById(R.id.tv_nam);
        tv_nv = view.findViewById(R.id.tv_nv);
        btnThongke = view.findViewById(R.id.btnThongke);
    }
}