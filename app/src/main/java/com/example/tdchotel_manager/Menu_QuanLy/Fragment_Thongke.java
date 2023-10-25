package com.example.tdchotel_manager.Menu_QuanLy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.tdchotel_manager.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

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
    Spinner sp_thoigian,sp_loaithongke;
    ArrayList<String>arr_sp_loaithongke=new ArrayList<>();
    BarChart barchart_thongke;

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
        View view= inflater.inflate(R.layout.fragment__thongke, container, false);
        setControl(view);
        Initialization();
        Chart();
        return view;
    }

    private void Chart() {

    }

    private void Initialization() {
        arr_sp_loaithongke.add("Doanh thu");
        arr_sp_loaithongke.add("Lượt thuê phòng");
        arr_sp_loaithongke.add("Hiệu suất lao công");
        arr_sp_loaithongke.add("Đánh giá tốt");
        sp_loaithongke.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,arr_sp_loaithongke));
    }

    private void setControl(View view) {
        sp_thoigian=view.findViewById(R.id.sp_thoigian);
        sp_loaithongke=view.findViewById(R.id.sp_loaithongke);
        barchart_thongke=view.findViewById(R.id.barchart_thongke);
    }
}