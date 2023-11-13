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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdchotel_manager.Menu_QuanLy.Activity_NghiLam;
import com.example.tdchotel_manager.Menu_QuanLy.Adapter_NhanVien.NhanVienCheckInOutAdapter;
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
    private Spinner spnCaLam;
    private EditText edtTimKiem;

    private List<nhan_vien> nhanVien;
    private NhanVienCheckInOutAdapter nhanVienAdapter;
    private List<nhan_vien> nhanVienList = new ArrayList<>();
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
        setEvent(view);
        return view;
    }

    private void setEvent(View view) {
        spnCaLam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loadCheckIn(spnCaLam.getSelectedItem().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        edtTimKiem.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {

                        loadCheckIn(spnCaLam.getSelectedItem().toString());

                    return true;
                }
                return false;
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
    private void setControl(View view) {
        rcvCheckIn = view.findViewById(R.id.rcvCheckIn);

        spnCaLam = view.findViewById(R.id.spnCaLam);
        edtTimKiem = view.findViewById(R.id.edtTimKiem);

        ArrayList<String> arrayLoai = new ArrayList<>();
        arrayLoai.add("Ca sáng");
        arrayLoai.add("Ca tối");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item,arrayLoai);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCaLam.setAdapter(arrayAdapter);
        LoadChucVu();

        nhanVienAdapter = new NhanVienCheckInOutAdapter(nhanVienList, chucVuList, new NhanVienCheckInOutAdapter.IClickListener() {
            @Override
            public void onClickUpdateItem(nhan_vien nhan_vien) {
                XinNghi(nhan_vien);
            }

            @Override
            public void onClickCheckItem(nhan_vien nhan_vien) {
                CheckIn(nhan_vien);
            }


        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rcvCheckIn.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        rcvCheckIn.addItemDecoration(decoration);
        rcvCheckIn.setAdapter(nhanVienAdapter);
    }
    private void CheckIn(nhan_vien nhan_vien) {
        new AlertDialog.Builder(getActivity())
                .setMessage("Bạn có muốn lưu thời gian bắt đầu ca?")
                .setCancelable(false)
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Calendar ca= Calendar.getInstance();
                        int dayofweek = ca.get(Calendar.DAY_OF_WEEK);
                        String idCaLam = "";
                        if(spnCaLam.getSelectedItem().toString().equals("Ca sáng"))
                        {
                            idCaLam = "1";
                        }
                        else
                        {
                            idCaLam = "2";
                        }
                        Date date = new Date();
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        String strDate = formatter.format(date);

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("cham_cong");
                        String id_chamCong = ref.push().getKey();
                        cham_cong cham_cong = new cham_cong(id_chamCong,nhan_vien.getId_nhan_vien(),dayofweek,idCaLam,strDate,"");
                        ref.child(cham_cong.getId_cham_cong()).setValue(cham_cong, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                if(error==null)
                                {

                                }

                            }
                        });
                    }
                }).setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();

    }
    private void XinNghi(nhan_vien nhan_vien)
    {
        new AlertDialog.Builder(getActivity())
                .setMessage("Bạn có muốn cho nhân viên ngày nghỉ ca này không?")
                .setCancelable(false)
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Calendar ca= Calendar.getInstance();
                        int dayofweek = ca.get(Calendar.DAY_OF_WEEK);
                        String idCaLam = "";
                        if(spnCaLam.getSelectedItem().toString().equals("Ca sáng"))
                        {
                            idCaLam = "1";
                        }
                        else
                        {
                            idCaLam = "2";
                        }
                        Date date = new Date();
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        String strDate = formatter.format(date)+" Nghỉ";

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("cham_cong");
                        String id_chamCong = ref.push().getKey();
                        cham_cong cham_cong = new cham_cong(id_chamCong,nhan_vien.getId_nhan_vien(),dayofweek,idCaLam,strDate,"");
                        ref.child(cham_cong.getId_cham_cong()).setValue(cham_cong, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                if(error==null)
                                {

                                }

                            }
                        });
                    }
                }).setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();

    }

    private void loadCheckIn(String caLam) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("nhan_vien");
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("phan_cong");
        DatabaseReference refChamCong = FirebaseDatabase.getInstance().getReference("cham_cong");
        refChamCong.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nhanVienList.clear();
                ref1.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            phan_cong phanCong = dataSnapshot.getValue(phan_cong.class);
                            Calendar ca= Calendar.getInstance();
                            int dayofweek = ca.get(Calendar.DAY_OF_WEEK);

                            if(phanCong.getDayofweek()==dayofweek)
                            {                    Log.e(dayofweek+"","dfsaf"+phanCong.getDayofweek());
                                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        nhanVienList.clear();
                                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                            nhan_vien nhan_vien = dataSnapshot.getValue(nhan_vien.class);
                                            if(nhan_vien.getId_nhan_vien().equals(phanCong.getId_nhan_vien())&&edtTimKiem.getText().toString().equals(""))
                                            {
                                                refChamCong.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        int check = 0;
                                                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                                            cham_cong cham_cong = dataSnapshot.getValue(cham_cong.class);

                                                            Date date = new Date();
                                                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                                                            String strDate = formatter.format(date);
                                                            String[] words = cham_cong.getCheck_in().split("\\s");
                                                            String the = "";
                                                            if(caLam.equals("Ca sáng"))
                                                            {
                                                                the="1";
                                                            }
                                                            else
                                                            {
                                                                the="2";
                                                            }
                                                            Log.e("Cham cong id"+cham_cong.getId_ca_lam()+"nhan vien id"+nhan_vien.getId_nhan_vien()+"ngay h"+nhan_vien.getId_nhan_vien(),"Cham cong id"+the+"nhan vien id"+cham_cong.getId_nhan_vien()+"ngay h"+strDate);
                                                            if(words[0].equals(strDate))
                                                            {

                                                            }
                                                            if(phanCong.getId_ca_lam().equals(the))
                                                            {
                                                                if(nhan_vien.getId_nhan_vien().equals(cham_cong.getId_nhan_vien())&&words[0].equals(strDate)&&cham_cong.getId_ca_lam().equals(the))                                                                {

                                                                    Log.e("Ceck",""+strDate);
                                                                    check++;
                                                                }
                                                                else
                                                                {

                                                                }
                                                            }
                                                            else
                                                            {
                                                                check++;
                                                            }


                                                        }
                                                        if(check==0)
                                                        {
                                                            nhanVienList.add(nhan_vien);
                                                        }
                                                        nhanVienAdapter.notifyDataSetChanged();
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });
                                            }
                                            else if(nhan_vien.getId_nhan_vien().equals(phanCong.getId_nhan_vien())&&edtTimKiem.getText().toString().equals(nhan_vien.getTen_nhan_vien()))
                                            {
                                                refChamCong.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        int check = 0;
                                                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                                            cham_cong cham_cong = dataSnapshot.getValue(cham_cong.class);

                                                            Date date = new Date();
                                                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                                                            String strDate = formatter.format(date);
                                                            String[] words = cham_cong.getCheck_in().split("\\s");
                                                            String the = "";
                                                            if(caLam.equals("Ca sáng"))
                                                            {
                                                                the="1";
                                                            }
                                                            else
                                                            {
                                                                the="2";
                                                            }
                                                            Log.e("Cham cong id"+cham_cong.getId_ca_lam()+"nhan vien id"+nhan_vien.getId_nhan_vien()+"ngay h"+nhan_vien.getId_nhan_vien(),"Cham cong id"+the+"nhan vien id"+cham_cong.getId_nhan_vien()+"ngay h"+strDate);
                                                            if(words[0].equals(strDate))
                                                            {

                                                            }
                                                            if(phanCong.getId_ca_lam().equals(the))
                                                            {
                                                                if(nhan_vien.getId_nhan_vien().equals(cham_cong.getId_nhan_vien())&&words[0].equals(strDate)&&cham_cong.getId_ca_lam().equals(the))                                                                {

                                                                    Log.e("Ceck",""+strDate);
                                                                    check++;
                                                                }
                                                                else
                                                                {

                                                                }
                                                            }
                                                            else
                                                            {
                                                                check++;
                                                            }


                                                        }
                                                        if(check==0)
                                                        {
                                                            nhanVienList.add(nhan_vien);
                                                        }
                                                        nhanVienAdapter.notifyDataSetChanged();
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });
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
                        nhanVienAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                nhanVienAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}