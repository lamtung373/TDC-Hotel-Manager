package com.example.tdchotel_manager.Menu_QuanLy;

import android.content.Intent;
import android.graphics.Color;
import android.icu.text.DecimalFormat;
import android.icu.text.DecimalFormatSymbols;
import android.icu.text.SimpleDateFormat;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tdchotel_manager.Menu_QuanLy.Adapter_Trangchu.adapter_calam;
import com.example.tdchotel_manager.Model.ca_lam;
import com.example.tdchotel_manager.Model.chuc_vu;
import com.example.tdchotel_manager.Model.hoa_don;
import com.example.tdchotel_manager.Model.nhan_vien;
import com.example.tdchotel_manager.Model.phan_cong;
import com.example.tdchotel_manager.Model.phong;
import com.example.tdchotel_manager.Model.trang_thai_phong;
import com.example.tdchotel_manager.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
    PieChart piechart_tinhhinhphong, piechart_doanhthu, piechart_hoadon;
    Button btn_chamcong;
    ArrayList<nhan_vien> arr_nhanvien_sang = new ArrayList<>();
    ArrayList<nhan_vien> arr_nhanvien_toi = new ArrayList<>();
    RecyclerView rcv_casang, rcv_catoi;

    TextView tv_phong, tv_dichvu, tv_dichvuphong, tv_phongdanghoatdong, tv_phongdangtrong, tv_phongdangsua, tv_hoadoncoc, tv_hoadonchuathanhtoan, tv_hoadondathanhtoan;

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

    private void setEvent() {
btn_chamcong.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
       // Intent intent=new Intent(getActivity(),)
    }
});
    }

    private void Initialization() {
        Chart_DoanhThu();
        Chart_Phong();
        Chart_Hoadon();
        adapter_calam adapterCa_Sang = new adapter_calam(arr_nhanvien_sang);
        adapter_calam adapterCa_Toi = new adapter_calam(arr_nhanvien_toi);
        Ca_Lam(adapterCa_Sang, "Ca sáng",arr_nhanvien_sang);
        rcv_casang.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rcv_casang.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        rcv_casang.setAdapter(adapterCa_Sang);
        Ca_Lam(adapterCa_Toi, "Ca tối",arr_nhanvien_toi);
        rcv_catoi.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rcv_catoi.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        rcv_catoi.setAdapter(adapterCa_Toi);
    }

    void Ca_Lam(adapter_calam adapterCalam, String ca,ArrayList<nhan_vien> arr_nhanvien) {

        arr_nhanvien.clear();
        DatabaseReference reference_nhanvien = FirebaseDatabase.getInstance().getReference("nhan_vien");
        DatabaseReference ref_chucvu = FirebaseDatabase.getInstance().getReference("chuc_vu");
        DatabaseReference ref_phancong = FirebaseDatabase.getInstance().getReference("phan_cong");
        DatabaseReference ref_calam = FirebaseDatabase.getInstance().getReference("ca_lam");

        ref_phancong.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arr_nhanvien.clear();
                Log.e("a",""+arr_nhanvien.size());
                adapterCalam.notifyDataSetChanged();
                ref_calam.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot item : snapshot.getChildren()) {
                            ca_lam ca_lam = item.getValue(ca_lam.class);
                            if (ca_lam.getTen_ca_lam().equals(ca)) {
                                ref_phancong.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        int thu_now = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                                        for (DataSnapshot item : snapshot.getChildren()) {
                                            phan_cong phanCong = item.getValue(phan_cong.class);
                                            if (ca_lam.getId_ca_lam().equals(phanCong.getId_ca_lam())) {

                                                if (thu_now == phanCong.getDayofweek()) {
                                                    reference_nhanvien.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                                                nhan_vien nv = dataSnapshot.getValue(nhan_vien.class);
                                                                if (nv.getId_nhan_vien().equals(phanCong.getId_nhan_vien())) {
                                                                    ref_chucvu.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                                                                chuc_vu cv = dataSnapshot.getValue(chuc_vu.class);
                                                                                if (nv.getId_chuc_vu().equals(cv.getId_chuc_vu())) {
                                                                                    nv.setChuc_vu(cv.getTen_chuc_vu());
                                                                                }
                                                                            }
                                                                            adapterCalam.notifyDataSetChanged();
                                                                        }

                                                                        @Override
                                                                        public void onCancelled(@NonNull DatabaseError error) {

                                                                        }
                                                                    });
                                                                    arr_nhanvien.add(nv);
                                                                }
                                                            }
                                                            adapterCalam.notifyDataSetChanged();
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError error) {

                                                        }
                                                    });

                                                }
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void Chart_Hoadon() {
        ArrayList<PieEntry> pieEntries = new ArrayList<>();

        //Lay du lieu firebase
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("hoa_don");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pieEntries.clear();

                //khai bao bien
                double phong = 0;
                double dich_vu = 0;
                double dich_vu_phong = 0;
                Date thoi_gian_thanh_toan = null;
                Date thoi_gian_coc = null;


                for (DataSnapshot item : snapshot.getChildren()) {
                    hoa_don hoa_don = item.getValue(hoa_don.class);
                    try {

                        //Chuyen doi thoi gian thanh toan tu firebase
                        thoi_gian_thanh_toan = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(hoa_don.getThoi_gian_thanh_toan());

                        //Chuyen doi thoi gian coc tu firebase
                        thoi_gian_coc = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(hoa_don.getThoi_gian_coc());

                        //Lay thoi gian hien tai
                        Date now = new Date();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        //  SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");

                        //So sanh thoi gian thanh toan voi thoi gian hien tai
                        if (dateFormat.format(now.getTime()).equals(dateFormat.format(thoi_gian_thanh_toan.getTime()))) {
                            phong += hoa_don.getTien_phong();
                            dich_vu += hoa_don.getTong_phi_dich_vu();
                            dich_vu_phong += hoa_don.getTong_phi_dich_vu_phong();
                        } else {

                            //So sanh thoi gian coc voi thoi gian hien tai
                            if (dateFormat.format(now.getTime()).equals(dateFormat.format(thoi_gian_coc.getTime()))) {
                                phong += hoa_don.getTien_coc();
                            }

                        }
                    } catch (ParseException e) {
                        Log.e("Lỗi chuyển đổi dữ liệu thời gian thanh toán", e.getMessage());
                    }
                }

                //thiet lap du lieu
                tv_phong.setText(phong + " đ");
                tv_dichvu.setText(dich_vu + " đ");
                tv_dichvuphong.setText(dich_vu_phong + " đ");
                double tong = phong + dich_vu + dich_vu_phong;


                //them du lieu cho chart
                if (tong != 0) {
                    if (phong != 0) {
                        pieEntries.add(new PieEntry((float) (phong / tong) * 100, "% Phòng"));

                    }
                    if (dich_vu != 0) {
                        pieEntries.add(new PieEntry((float) (dich_vu / tong) * 100, "% Dịch vụ"));
                    }
                    if (dich_vu_phong != 0) {
                        pieEntries.add(new PieEntry((float) (dich_vu_phong / tong) * 100, "% Dịch vụ phòng"));
                    }


                }

                //thiet lap giao dien cho chart
                if (!pieEntries.isEmpty()) {
                    PieDataSet pieDataSet = new PieDataSet(pieEntries, "Doanh thu");
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    pieDataSet.setValueTextColor(Color.WHITE);
                    pieDataSet.setValueTextSize(10f);
                    PieData pieData = new PieData(pieDataSet);
                    pieData.setValueFormatter(new PercentFormatter(piechart_doanhthu));
                    piechart_doanhthu.setData(pieData);
                    piechart_doanhthu.getDescription().setEnabled(false);
                    piechart_doanhthu.animateXY(1000, 1000);
                    piechart_doanhthu.animate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void Chart_DoanhThu() {
        ArrayList<PieEntry> pieEntries = new ArrayList<>();

        //Lay du lieu firebase
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("hoa_don");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pieEntries.clear();

                //khai bao bien
                int coc = 0, chua_thanh_toan = 0, da_thanh_toan = 0;
                String thoi_gian_thanh_toan = null;
                String thoi_gian_coc = null;
                String thoi_gian_nhan_phong = null;
                //Lay thoi gian hien tai
                Date now = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                for (DataSnapshot item : snapshot.getChildren()) {
                    hoa_don hoa_don = item.getValue(hoa_don.class);
                    try {

                        //Chuyen doi thoi gian thanh toan tu firebase
                        if (!hoa_don.getThoi_gian_thanh_toan().equals("")) {
                            thoi_gian_thanh_toan = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(hoa_don.getThoi_gian_thanh_toan()));
                        } else {
                            thoi_gian_thanh_toan = "";
                        }

                        //Chuyen doi thoi gian coc tu firebase
                        if (!hoa_don.getThoi_gian_coc().equals("")) {
                            thoi_gian_coc = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(hoa_don.getThoi_gian_coc()));
                        } else {
                            thoi_gian_coc = "";
                        }
                        if (!hoa_don.getThoi_gian_nhan_phong().equals("")) {
                            thoi_gian_nhan_phong = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(hoa_don.getThoi_gian_nhan_phong()));
                        } else {
                            thoi_gian_nhan_phong = "";
                        }


                        //  SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");

                        //So sanh thoi gian thanh toan voi thoi gian hien tai
                        if (dateFormat.format(now.getTime()).equals(thoi_gian_thanh_toan)) {
                            da_thanh_toan++;

                        } else {
                            //So sanh thoi gian coc voi thoi gian hien tai
                            if (dateFormat.format(now.getTime()).equals(thoi_gian_nhan_phong)) {
                                chua_thanh_toan++;
                            } else if ((dateFormat.format(now.getTime()).equals(thoi_gian_coc))) {
                                coc++;
                            }
                            //So sanh thoi gian coc voi thoi gian hien tai


                        }
                    } catch (ParseException e) {
                        Log.e("Lỗi chuyển đổi dữ liệu thời gian thanh toán", e.getMessage());
                    }
                }

                //thiet lap du lieu
                tv_hoadondathanhtoan.setText("Hóa đơn đã thanh toán: " + da_thanh_toan);
                tv_hoadonchuathanhtoan.setText("Hóa đơn đã thanh toán: " + chua_thanh_toan);
                tv_hoadoncoc.setText("Hóa đơn cọc: " + coc);
                double tong = da_thanh_toan + chua_thanh_toan + coc;


                //them du lieu cho chart
                if (tong != 0) {
                    if (da_thanh_toan != 0) {
                        pieEntries.add(new PieEntry((float) (da_thanh_toan / tong) * 100, "% Đã thanh toán"));

                    }
                    if (chua_thanh_toan != 0) {
                        pieEntries.add(new PieEntry((float) (chua_thanh_toan / tong) * 100, "% Chưa thanh toán"));
                    }
                    if (coc != 0) {
                        pieEntries.add(new PieEntry((float) (coc / tong) * 100, "% Cọc"));
                    }


                }

                //thiet lap giao dien cho chart
                if (!pieEntries.isEmpty()) {
                    PieDataSet pieDataSet = new PieDataSet(pieEntries, "Hóa đơn");
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    pieDataSet.setValueTextColor(Color.WHITE);
                    pieDataSet.setValueTextSize(10f);
                    PieData pieData = new PieData(pieDataSet);
                    pieData.setValueFormatter(new PercentFormatter(piechart_hoadon));
                    piechart_hoadon.setData(pieData);
                    piechart_hoadon.getDescription().setEnabled(false);
                    piechart_hoadon.animateXY(1000, 1000);
                    piechart_hoadon.animate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void Chart_Phong() {
        ArrayList<PieEntry> pieEntries = new ArrayList<>();

        //Lay du lieu firebase
        DatabaseReference reference_phong = FirebaseDatabase.getInstance().getReference("phong");
        DatabaseReference reference_trang_thai_phong = FirebaseDatabase.getInstance().getReference("trang_thai_phong");
        reference_phong.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pieEntries.clear();
                final int[] hoat_dong = {0};
                final int[] trong = {0};
                final int[] sua = {0};

                final long[] totalLoops = {snapshot.getChildrenCount()};
                final long[] completedLoops = {0};

                for (DataSnapshot item : snapshot.getChildren()) {
                    phong phong = item.getValue(phong.class);
                    reference_trang_thai_phong.addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for (DataSnapshot item : snapshot.getChildren()) {
                                trang_thai_phong trang_thai_phong = item.getValue(trang_thai_phong.class);

                                if (phong.getId_trang_thai_phong().equals(trang_thai_phong.getId_trang_thai_phong())) {

                                    if (trang_thai_phong.getTen_trang_thai().equals("Đang sử dụng")) {
                                        hoat_dong[0]++;

                                    } else if (trang_thai_phong.getTen_trang_thai().equals("Sẵn sàng")) {
                                        trong[0]++;
                                    } else if (trang_thai_phong.getTen_trang_thai().equals("Đang sửa")) {
                                        sua[0]++;
                                    }
                                }
                            }
                            completedLoops[0]++;
                            if (totalLoops[0] == completedLoops[0]) {
                                //thiet lap du lieu
                                //     Toast.makeText(getActivity(), "helllo" + hoat_dong[0], Toast.LENGTH_SHORT).show();
                                tv_phongdanghoatdong.setText("Phòng đang hoạt động: " + hoat_dong[0]);
                                tv_phongdangtrong.setText("Phòng đang trống: " + trong[0]);
                                tv_phongdangsua.setText("Phòng đang sửa: " + sua[0]);
                                double tong = hoat_dong[0] + trong[0] + sua[0];

                                //them du lieu cho chart
                                if (tong != 0) {
                                    if (hoat_dong[0] != 0) {
                                        pieEntries.add(new PieEntry((float) (hoat_dong[0] / tong) * 100, "% Hoạt động"));
                                    }
                                    if (trong[0] != 0) {
                                        pieEntries.add(new PieEntry((float) (trong[0] / tong) * 100, "% Trống"));
                                    }
                                    if (sua[0] != 0) {
                                        pieEntries.add(new PieEntry((float) (sua[0] / tong) * 100, "% Sửa"));
                                    }


                                }


                                //thiet lap giao dien cho chart
                                if (!pieEntries.isEmpty()) {
                                    PieDataSet pieDataSet = new PieDataSet(pieEntries, "Tình hình phòng");
                                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                                    pieDataSet.setValueTextColor(Color.WHITE);
                                    pieDataSet.setValueTextSize(10f);
                                    PieData pieData = new PieData(pieDataSet);
                                    pieData.setValueFormatter(new PercentFormatter(piechart_tinhhinhphong));
                                    piechart_tinhhinhphong.setData(pieData);
                                    piechart_tinhhinhphong.getDescription().setEnabled(false);
                                    piechart_tinhhinhphong.animateXY(1000, 1000);
                                    piechart_tinhhinhphong.animate();
                                }

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setControl(View view) {
        piechart_tinhhinhphong = view.findViewById(R.id.piechart_tinhhinhphong);
        btn_chamcong = view.findViewById(R.id.btn_chamcong);
        rcv_casang = view.findViewById(R.id.rcv_casang);
        rcv_catoi = view.findViewById(R.id.rcv_catoi);
        piechart_doanhthu = view.findViewById(R.id.piechart_doanhthu);
        piechart_hoadon = view.findViewById(R.id.piechart_hoadon);
        tv_phong = view.findViewById(R.id.tv_phong);
        tv_dichvu = view.findViewById(R.id.tv_dichvu);
        tv_dichvuphong = view.findViewById(R.id.tv_dichvuphong);
        tv_phongdanghoatdong = view.findViewById(R.id.tv_phongdanghoatdong);
        tv_phongdangtrong = view.findViewById(R.id.tv_phongdangtrong);
        tv_phongdangsua = view.findViewById(R.id.tv_phongdangsua);
        tv_hoadoncoc = view.findViewById(R.id.tv_hoadoncoc);
        tv_hoadonchuathanhtoan = view.findViewById(R.id.tv_hoadonchuathanhtoan);
        tv_hoadondathanhtoan = view.findViewById(R.id.tv_hoadondathanhtoan);
    }
}