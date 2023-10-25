package com.example.tdchotel_manager.Model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class phan_cong {
    int id_nhan_vien,id_ca_lam;
    Date ngay_lam,check_in,check_out;
    public phan_cong( int id_nhan_vien, int id_ca_lam, Date ngay_lam, Date check_in, Date check_out) {
        this.id_nhan_vien = id_nhan_vien;
        this.id_ca_lam = id_ca_lam;
        this.ngay_lam = ngay_lam;
        this.check_in = check_in;
        this.check_out = check_out;
    }
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id_nhan_vien", id_nhan_vien);
        result.put("id_ca_lam", id_ca_lam);
        result.put("ngay_lam", ngay_lam);
        result.put("check_in", check_in);
        result.put("check_out", check_out);

        return result;
    }
    public phan_cong() {
    }

    public int getId_nhan_vien() {
        return id_nhan_vien;
    }

    public void setId_nhan_vien(int id_nhan_vien) {
        this.id_nhan_vien = id_nhan_vien;
    }

    public int getId_ca_lam() {
        return id_ca_lam;
    }

    public void setId_ca_lam(int id_ca_lam) {
        this.id_ca_lam = id_ca_lam;
    }

    public Date getNgay_lam() {
        return ngay_lam;
    }

    public void setNgay_lam(Date ngay_lam) {
        this.ngay_lam = ngay_lam;
    }

    public Date getCheck_in() {
        return check_in;
    }

    public void setCheck_in(Date check_in) {
        this.check_in = check_in;
    }

    public Date getCheck_out() {
        return check_out;
    }

    public void setCheck_out(Date check_out) {
        this.check_out = check_out;
    }
}
