package com.example.tdchotel_manager.Menu_QuanLy.manhinhcheckinout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdchotel_manager.Menu_QuanLy.Adapter_NhanVien.NhanVienCheckInOutAdapter;
import com.example.tdchotel_manager.Menu_QuanLy.Adapter_NhanVien.NhanVienCheckOutAdapter;
import com.example.tdchotel_manager.Menu_QuanLy.QuanLyLichLam;
import com.example.tdchotel_manager.Model.cham_cong;
import com.example.tdchotel_manager.Model.chuc_vu;
import com.example.tdchotel_manager.Model.nhan_vien;
import com.example.tdchotel_manager.Model.phan_cong;
import com.example.tdchotel_manager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link checkout#newInstance} factory method to
 * create an instance of this fragment.
 */
public class checkout extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private NhanVienCheckOutAdapter nhanVienAdapter;
    private List<nhan_vien> nhanVienList = new ArrayList<>();
    private List<phan_cong> phanCongList = new ArrayList<>();
    private List<chuc_vu> chucVuList = new ArrayList<>();
    private RecyclerView rcvCheckOut;
    private Spinner spnCaLam;
    private EditText edtTimKiem;

    public checkout() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment checkout.
     */
    // TODO: Rename and change types and number of parameters
    public static checkout newInstance(String param1, String param2) {
        checkout fragment = new checkout();
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
        View view = inflater.inflate(R.layout.fragment_checkout, container, false);
        setControl(view);
        setEvent(view);
        return view;
    }

    private void setEvent(View view) {
        spnCaLam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                loadCheckOut(spnCaLam.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        edtTimKiem.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {

                    loadCheckOut(spnCaLam.getSelectedItem().toString());

                    return true;
                }
                return false;
            }
        });
    }

    private void loadCheckOut(String caLam) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("nhan_vien");
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("phan_cong");
        DatabaseReference refChamCong = FirebaseDatabase.getInstance().getReference("cham_cong");
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = formatter.format(date)+" Nghỉ";
        refChamCong.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nhanVienList.clear();
                refChamCong.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            cham_cong cham_cong = dataSnapshot.getValue(cham_cong.class);
                            Calendar ca = Calendar.getInstance();
                            int dayofweek = ca.get(Calendar.DAY_OF_WEEK);
                            String the = "1";
                            if(caLam.equals("Ca sáng"))
                            {
                                the = "1";
                            }
                            else
                            {
                                the = "2";
                            }
                            String finalThe = the;
                            ref.addListenerForSingleValueEvent(new ValueEventListener()
                            {

                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                        nhan_vien nhan_vien = dataSnapshot.getValue(nhan_vien.class);
                                        if(nhan_vien.getId_nhan_vien().equals(cham_cong.getId_nhan_vien())&&cham_cong.getDayofweek()==dayofweek&&cham_cong.getId_ca_lam().equals(finalThe))
                                        {
                                            if(edtTimKiem.getText().toString().equals(""))
                                            {
                                                if(cham_cong.getCheck_out().equals("")&&!cham_cong.getCheck_in().equals(strDate))
                                                {
                                                    nhanVienList.add(nhan_vien);
                                                }
                                                else
                                                {

                                                }
                                            }
                                            else if(edtTimKiem.getText().toString().equals(nhan_vien.getTen_nhan_vien()))
                                            {
                                                if(cham_cong.getCheck_out().equals("")&&!cham_cong.getCheck_in().equals(strDate))
                                                {
                                                    nhanVienList.add(nhan_vien);
                                                }
                                                else
                                                {

                                                }

                                            }


                                        }
                                    }
                                    nhanVienAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }
                        nhanVienAdapter.notifyDataSetChanged();
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

    private void setControl(View view) {
        rcvCheckOut = view.findViewById(R.id.rcvCheckOut);
        edtTimKiem = view.findViewById(R.id.edtTimKiem);

        spnCaLam = view.findViewById(R.id.spnCaLam);
        ArrayList<String> arrayLoai = new ArrayList<>();
        arrayLoai.add("Ca sáng");
        arrayLoai.add("Ca tối");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item,arrayLoai);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCaLam.setAdapter(arrayAdapter);
        LoadChucVu();

        nhanVienAdapter = new NhanVienCheckOutAdapter(nhanVienList, chucVuList, new NhanVienCheckOutAdapter.IClickListener() {
            @Override
            public void onClickUpdateItem(nhan_vien nhan_vien) {
                new AlertDialog.Builder(getActivity())
                        .setMessage("Bạn có muốn lưu thời gian kết thúc ca?")
                        .setCancelable(false)
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                CheckOut(nhan_vien);
                            }
                        }).setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();

            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rcvCheckOut.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        rcvCheckOut.addItemDecoration(decoration);
        rcvCheckOut.setAdapter(nhanVienAdapter);
    }

    private void CheckOut(nhan_vien nhanVien) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("cham_cong");
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatterChuan = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String strDate = formatter.format(date)+" Nghỉ";
        String strDate1 = formatterChuan.format(date);
        String the = "1";
        if(spnCaLam.getSelectedItem().equals("Ca sáng"))
        {
            the = "1";
        }
        else
        {
            the = "2";
        }
        String finalThe = the;
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    cham_cong cham_cong = dataSnapshot.getValue(cham_cong.class);
                    if(nhanVien.getId_nhan_vien().equals(cham_cong.getId_nhan_vien())&&!strDate.equals(cham_cong.getCheck_in()))
                    {
                        SimpleDateFormat formatterChuan = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        String strDate1 = formatterChuan.format(date);
                        cham_cong.setCheck_out(strDate1);
                        ref.child(cham_cong.getId_cham_cong()).updateChildren(cham_cong.toMap(), new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(getActivity(), "Check thành công", Toast.LENGTH_SHORT).show();
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
    }
}