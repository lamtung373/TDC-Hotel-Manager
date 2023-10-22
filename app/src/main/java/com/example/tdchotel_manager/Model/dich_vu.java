package com.example.tdchotel_manager.Model;

public class dich_vu {
    int id_dich_vu,gia_dich_vu;
    String ten_dich_vu;

    public dich_vu(int id_dich_vu, int gia_dich_vu, String ten_dich_vu) {
        this.id_dich_vu = id_dich_vu;
        this.gia_dich_vu = gia_dich_vu;
        this.ten_dich_vu = ten_dich_vu;
    }

    public int getId_dich_vu() {
        return id_dich_vu;
    }

    public void setId_dich_vu(int id_dich_vu) {
        this.id_dich_vu = id_dich_vu;
    }

    public int getGia_dich_vu() {
        return gia_dich_vu;
    }

    public void setGia_dich_vu(int gia_dich_vu) {
        this.gia_dich_vu = gia_dich_vu;
    }

    public String getTen_dich_vu() {
        return ten_dich_vu;
    }

    public void setTen_dich_vu(String ten_dich_vu) {
        this.ten_dich_vu = ten_dich_vu;
    }
}
