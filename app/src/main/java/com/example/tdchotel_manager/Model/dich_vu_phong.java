package com.example.tdchotel_manager.Model;

public class dich_vu_phong {
    int id_dich_vu_phong;
    String ten_dich_vu_phong;
    double gia_dich_vu_phong;

    public dich_vu_phong(int id_dich_vu_phong, String ten_dich_vu_phong, double gia_dich_vu_phong) {
        this.id_dich_vu_phong = id_dich_vu_phong;
        this.ten_dich_vu_phong = ten_dich_vu_phong;
        this.gia_dich_vu_phong = gia_dich_vu_phong;
    }

    public int getId_dich_vu_phong() {
        return id_dich_vu_phong;
    }

    public void setId_dich_vu_phong(int id_dich_vu_phong) {
        this.id_dich_vu_phong = id_dich_vu_phong;
    }

    public String getTen_dich_vu_phong() {
        return ten_dich_vu_phong;
    }

    public void setTen_dich_vu_phong(String ten_dich_vu_phong) {
        this.ten_dich_vu_phong = ten_dich_vu_phong;
    }

    public double getGia_dich_vu_phong() {
        return gia_dich_vu_phong;
    }

    public void setGia_dich_vu_phong(double gia_dich_vu_phong) {
        this.gia_dich_vu_phong = gia_dich_vu_phong;
    }
}
