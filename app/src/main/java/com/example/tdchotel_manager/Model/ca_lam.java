package com.example.tdchotel_manager.Model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ca_lam {
    String ten_ca_lam,id_ca_lam;
    String bat_dau, ket_thuc;

    public String getTen_ca_lam() {
        return ten_ca_lam;
    }

    public void setTen_ca_lam(String ten_ca_lam) {
        this.ten_ca_lam = ten_ca_lam;
    }

    public ca_lam(String ten_ca_lam, String id_ca_lam, String bat_dau, String ket_thuc) {
        this.ten_ca_lam = ten_ca_lam;
        this.id_ca_lam = id_ca_lam;
        this.bat_dau = bat_dau;
        this.ket_thuc = ket_thuc;
    }

    public ca_lam() {
    }


    public String getId_ca_lam() {
        return id_ca_lam;
    }

    public void setId_ca_lam(String id_ca_lam) {
        this.id_ca_lam = id_ca_lam;
    }


    public String getBat_dau() {
        return bat_dau;
    }

    public void setBat_dau(String bat_dau) {
        this.bat_dau = bat_dau;
    }

    public String getKet_thuc() {
        return ket_thuc;
    }

    public void setKet_thuc(String ket_thuc) {
        this.ket_thuc = ket_thuc;
    }
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("ten_ca", ten_ca_lam);
        result.put("bat_dau", bat_dau);
        result.put("ket_thuc", ket_thuc);

        return result;
    }
}
