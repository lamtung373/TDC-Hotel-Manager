package com.example.tdchotel_manager.Model;

public class danh_sach_luu {
    String so_dien_thoai;
    int id_phong;

    public danh_sach_luu(String so_dien_thoai, int id_phong) {
        this.so_dien_thoai = so_dien_thoai;
        this.id_phong = id_phong;
    }

    public danh_sach_luu() {
    }

    public String getSo_dien_thoai() {
        return so_dien_thoai;
    }

    public void setSo_dien_thoai(String so_dien_thoai) {
        this.so_dien_thoai = so_dien_thoai;
    }

    public int getId_phong() {
        return id_phong;
    }

    public void setId_phong(int id_phong) {
        this.id_phong = id_phong;
    }
}
