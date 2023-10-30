package com.example.tdchotel_manager.Model;

public class danh_sach_luu {
    String id_luu,so_dien_thoai,id_phong;

    public danh_sach_luu(String id_luu, String so_dien_thoai, String id_phong) {
        this.id_luu = id_luu;
        this.so_dien_thoai = so_dien_thoai;
        this.id_phong = id_phong;
    }

    public String getId_luu() {
        return id_luu;
    }

    public void setId_luu(String id_luu) {
        this.id_luu = id_luu;
    }

    public danh_sach_luu() {
    }

    public String getSo_dien_thoai() {
        return so_dien_thoai;
    }

    public void setSo_dien_thoai(String so_dien_thoai) {
        this.so_dien_thoai = so_dien_thoai;
    }

    public String getId_phong() {
        return id_phong;
    }

    public void setId_phong(String id_phong) {
        this.id_phong = id_phong;
    }
}
