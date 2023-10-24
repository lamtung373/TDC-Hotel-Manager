package com.example.tdchotel_manager.Model;

public class chi_tiet_dich_vu {
    int id, id_phong, id_dich_vu, so_luong;

    public chi_tiet_dich_vu(int id_chi_tiet_dich_vu, int id_phong, int id_dich_vu, int so_luong) {
        this.id_chi_tiet_dich_vu = id_chi_tiet_dich_vu;
        this.id_phong = id_phong;
        this.id_dich_vu = id_dich_vu;
        this.so_luong = so_luong;
    }

    public int getId_chi_tiet_dich_vu() {
        return id_chi_tiet_dich_vu;
    }

    public void setId_chi_tiet_dich_vu(int id_chi_tiet_dich_vu) {
        this.id_chi_tiet_dich_vu = id_chi_tiet_dich_vu;
    }

    public int getId_phong() {
        return id_phong;
    }

    public void setId_phong(int id_phong) {
        this.id_phong = id_phong;
    }

    public int getId_dich_vu() {
        return id_dich_vu;
    }

    public void setId_dich_vu(int id_dich_vu) {
        this.id_dich_vu = id_dich_vu;
    }

    public int getSo_luong() {
        return so_luong;
    }

    public void setSo_luong(int so_luong) {
        this.so_luong = so_luong;
    }
}
