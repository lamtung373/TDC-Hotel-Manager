package com.example.tdchotel_manager.Model;

public class chi_tiet_tien_nghi {
    int id_chi_tiet_tien_nghi, id_phong, id_tien_nghi, so_luong;

    public chi_tiet_tien_nghi(int id_chi_tiet_tien_nghi, int id_phong, int id_tien_nghi, int so_luong) {
        this.id_chi_tiet_tien_nghi = id_chi_tiet_tien_nghi;
        this.id_phong = id_phong;
        this.id_tien_nghi = id_tien_nghi;
        this.so_luong = so_luong;
    }

    public int getId_chi_tiet_tien_nghi() {
        return id_chi_tiet_tien_nghi;
    }

    public void setId_chi_tiet_tien_nghi(int id_chi_tiet_tien_nghi) {
        this.id_chi_tiet_tien_nghi = id_chi_tiet_tien_nghi;
    }

    public int getId_phong() {
        return id_phong;
    }

    public void setId_phong(int id_phong) {
        this.id_phong = id_phong;
    }

    public int getId_tien_nghi() {
        return id_tien_nghi;
    }

    public void setId_tien_nghi(int id_tien_nghi) {
        this.id_tien_nghi = id_tien_nghi;
    }

    public int getSo_luong() {
        return so_luong;
    }

    public void setSo_luong(int so_luong) {
        this.so_luong = so_luong;
    }
}
