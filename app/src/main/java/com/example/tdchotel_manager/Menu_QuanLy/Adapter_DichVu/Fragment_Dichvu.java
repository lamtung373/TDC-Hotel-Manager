package com.example.tdchotel_manager.Menu_QuanLy.Adapter_DichVu;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.tdchotel_manager.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Dichvu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Dichvu extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<DichVu> dvArraylist;
    private String[] newDichVu;
    private int[] newImg;
    private RecyclerView recyclerView;

    public Fragment_Dichvu() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Dichvu.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Dichvu newInstance(String param1, String param2) {
        Fragment_Dichvu fragment = new Fragment_Dichvu();
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
       return inflater.inflate(R.layout.fragment__dichvu, container, false);

    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataInitialize();
        recyclerView = view.findViewById(R.id.revSP);

        // Thiết lập LayoutManager cho RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Tạo Adapter và thiết lập cho RecyclerView
        Adapter_DichVu adapter = new Adapter_DichVu(getContext(), dvArraylist);
        recyclerView.setAdapter(adapter);
    }

    private void dataInitialize() {
        dvArraylist = new ArrayList<>();
        newDichVu = new String[]{
                getString(R.string.app_name),
        };
        newImg = new int[]{
                R.drawable.hoboi,
        };
        for (int i=0;i < newDichVu.length;i++) {
            DichVu dv = new DichVu(newDichVu[i],newDichVu[i],newImg[i]);
            dvArraylist.add(dv);
        }
    }
}