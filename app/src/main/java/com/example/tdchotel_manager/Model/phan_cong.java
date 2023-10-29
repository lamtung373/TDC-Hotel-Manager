package com.example.tdchotel_manager.Model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class phan_cong {
    String id_phan_cong,id_nhan_vien,id_ca_lam;
    int dayofweek;

    public phan_cong(String id_phan_cong, String id_nhan_vien, String id_ca_lam, int dayofweek) {
        this.id_phan_cong = id_phan_cong;
        this.id_nhan_vien = id_nhan_vien;
        this.id_ca_lam = id_ca_lam;
        this.dayofweek = dayofweek;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id_nhan_vien", id_nhan_vien);
        result.put("id_ca_lam", id_ca_lam);
        result.put("ngay_lam", dayofweek);

        return result;
    }
    public phan_cong() {
    }

    public String getId_phan_cong() {
        return id_phan_cong;
    }

    public void setId_phan_cong(String id_phan_cong) {
        this.id_phan_cong = id_phan_cong;
    }

    public String getId_nhan_vien() {
        return id_nhan_vien;
    }

    public void setId_nhan_vien(String id_nhan_vien) {
        this.id_nhan_vien = id_nhan_vien;
    }

    public String getId_ca_lam() {
        return id_ca_lam;
    }

    public void setId_ca_lam(String id_ca_lam) {
        this.id_ca_lam = id_ca_lam;
    }

    public int getDayofweek() {
        return dayofweek;
    }

    public void setDayofweek(int dayofweek) {
        this.dayofweek = dayofweek;
    }
}
