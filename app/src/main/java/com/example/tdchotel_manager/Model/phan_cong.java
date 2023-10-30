package com.example.tdchotel_manager.Model;

public class phan_cong {
    String id_phan_cong;
    String id_nhan_vien;
    String id_ca_lam;
    int dayofweek;

    public phan_cong(String id_phan_cong, String id_nhan_vien, String id_ca_lam, int dayofweek) {
        this.id_phan_cong = id_phan_cong;
        this.id_nhan_vien = id_nhan_vien;
        this.id_ca_lam = id_ca_lam;
        this.dayofweek = dayofweek;
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

    public phan_cong() {
    }
}
