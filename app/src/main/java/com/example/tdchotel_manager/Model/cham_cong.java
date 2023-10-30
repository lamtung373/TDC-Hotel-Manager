package com.example.tdchotel_manager.Model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class cham_cong {
    String id_cham_cong, id_nhan_vien, dayofweek, id_ca_lam, check_in, check_out;
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id_nhan_vien", id_nhan_vien);
        result.put("dayofweek", dayofweek);
        result.put("id_ca_lam", id_ca_lam);
        result.put("check_in", check_in);
        result.put("check_out", check_out);
        return result;
    }
    public cham_cong() {
    }

    public cham_cong(String id_cham_cong, String id_nhan_vien, String dayofweek, String id_ca_lam, String check_in, String check_out) {
        this.id_cham_cong = id_cham_cong;
        this.id_nhan_vien = id_nhan_vien;
        this.dayofweek = dayofweek;
        this.id_ca_lam = id_ca_lam;
        this.check_in = check_in;
        this.check_out = check_out;
    }

    public String getId_cham_cong() {
        return id_cham_cong;
    }

    public void setId_cham_cong(String id_cham_cong) {
        this.id_cham_cong = id_cham_cong;
    }

    public String getId_nhan_vien() {
        return id_nhan_vien;
    }

    public void setId_nhan_vien(String id_nhan_vien) {
        this.id_nhan_vien = id_nhan_vien;
    }

    public String getDayofweek() {
        return dayofweek;
    }

    public void setDayofweek(String dayofweek) {
        this.dayofweek = dayofweek;
    }

    public String getId_ca_lam() {
        return id_ca_lam;
    }

    public void setId_ca_lam(String id_ca_lam) {
        this.id_ca_lam = id_ca_lam;
    }

    public String getCheck_in() {
        return check_in;
    }

    public void setCheck_in(String check_in) {
        this.check_in = check_in;
    }

    public String getCheck_out() {
        return check_out;
    }

    public void setCheck_out(String check_out) {
        this.check_out = check_out;
    }
}
