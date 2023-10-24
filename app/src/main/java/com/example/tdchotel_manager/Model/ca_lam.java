package com.example.tdchotel_manager.Model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ca_lam {
    int id_ca_lam;
    String ten_ca;
    Date bat_dau, ket_thuc;

    public ca_lam() {
    }

    public ca_lam(int id_ca_lam, String ten_ca, Date bat_dau, Date ket_thuc) {
        this.id_ca_lam = id_ca_lam;
        this.ten_ca = ten_ca;
        this.bat_dau = bat_dau;
        this.ket_thuc = ket_thuc;
    }

    public int getId_ca_lam() {
        return id_ca_lam;
    }

    public void setId_ca_lam(int id_ca_lam) {
        this.id_ca_lam = id_ca_lam;
    }

    public String getTen_ca() {
        return ten_ca;
    }

    public void setTen_ca(String ten_ca) {
        this.ten_ca = ten_ca;
    }

    public Date getBat_dau() {
        return bat_dau;
    }

    public void setBat_dau(Date bat_dau) {
        this.bat_dau = bat_dau;
    }

    public Date getKet_thuc() {
        return ket_thuc;
    }

    public void setKet_thuc(Date ket_thuc) {
        this.ket_thuc = ket_thuc;
    }
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("ten_ca", ten_ca);
        result.put("bat_dau", bat_dau);
        result.put("ket_thuc", ket_thuc);

        return result;
    }
}
