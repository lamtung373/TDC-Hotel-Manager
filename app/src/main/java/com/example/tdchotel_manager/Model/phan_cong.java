package com.example.tdchotel_manager.Model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class phan_cong {
    String id_nhan_vien,id_ca_lam;
    String check_in,check_out;
    int dayofweek;

    public phan_cong(String id_nhan_vien, String id_ca_lam, String check_in, String check_out, int dayofweek) {
        this.id_nhan_vien = id_nhan_vien;
        this.id_ca_lam = id_ca_lam;
        this.check_in = check_in;
        this.check_out = check_out;
        this.dayofweek = dayofweek;
    }

    public int getDayofweek() {
        return dayofweek;
    }

    public void setDayofweek(int dayofweek) {
        this.dayofweek = dayofweek;
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



    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id_nhan_vien", id_nhan_vien);
        result.put("id_ca_lam", id_ca_lam);
        result.put("dayofweek", dayofweek);
        result.put("check_in", check_in);
        result.put("check_out", check_out);

        return result;
    }
    public phan_cong() {
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


}
