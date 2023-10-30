package com.example.tdchotel_manager.Model;

public class danh_gia {
    int  so_sao;
    String id_danh_gia,id_phong,so_dien_thoai,chi_tiet_danh_gia,thoi_gian;

    public String getId_phong() {
        return id_phong;
    }

    public void setId_phong(String id_phong) {
        this.id_phong = id_phong;
    }

    public danh_gia(int so_sao, String id_danh_gia, String id_phong, String so_dien_thoai, String chi_tiet_danh_gia, String thoi_gian) {
        this.so_sao = so_sao;
        this.id_danh_gia = id_danh_gia;
        this.id_phong = id_phong;
        this.so_dien_thoai = so_dien_thoai;
        this.chi_tiet_danh_gia = chi_tiet_danh_gia;
        this.thoi_gian = thoi_gian;
    }

    public String getThoi_gian() {
        return thoi_gian;
    }

    public void setThoi_gian(String thoi_gian) {
        this.thoi_gian = thoi_gian;
    }

    public danh_gia() {
    }

    public String getId_danh_gia() {
        return id_danh_gia;
    }

    public void setId_danh_gia(String id_danh_gia) {
        this.id_danh_gia = id_danh_gia;
    }

    public int getSo_sao() {
        return so_sao;
    }

    public void setSo_sao(int so_sao) {
        this.so_sao = so_sao;
    }

    public String getSo_dien_thoai() {
        return so_dien_thoai;
    }

    public void setSo_dien_thoai(String so_dien_thoai) {
        this.so_dien_thoai = so_dien_thoai;
    }

    public String getChi_tiet_danh_gia() {
        return chi_tiet_danh_gia;
    }

    public void setChi_tiet_danh_gia(String chi_tiet_danh_gia) {
        this.chi_tiet_danh_gia = chi_tiet_danh_gia;
    }
}
