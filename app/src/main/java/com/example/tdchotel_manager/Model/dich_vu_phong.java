package com.example.tdchotel_manager.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class dich_vu_phong {
    String id_dich_vu_phong,ten_dich_vu_phong,anh_dich_vu_phong;
    double gia_dich_vu_phong;
    int so_luong;

    public dich_vu_phong(String id_dich_vu_phong, String ten_dich_vu_phong, String anh_dich_vu_phong, double gia_dich_vu_phong) {
        this.id_dich_vu_phong = id_dich_vu_phong;
        this.ten_dich_vu_phong = ten_dich_vu_phong;
        this.anh_dich_vu_phong = anh_dich_vu_phong;
        this.gia_dich_vu_phong = gia_dich_vu_phong;
    }

    public int getSo_luong() {
        return so_luong;
    }

    public void setSo_luong(int so_luong) {
        this.so_luong = so_luong;
    }

    public dich_vu_phong() {
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("ten_dich_vu_phong", ten_dich_vu_phong);
        result.put("anh_dich_vu_phong", anh_dich_vu_phong);
        result.put("gia_dich_vu_phong", gia_dich_vu_phong);
        return result;
    }
    public String getAnh_dich_vu_phong() {
        return anh_dich_vu_phong;
    }

    public void setAnh_dich_vu_phong(String anh_dich_vu_phong) {
        this.anh_dich_vu_phong = anh_dich_vu_phong;
    }

    public String getId_dich_vu_phong() {
        return id_dich_vu_phong;
    }

    public void setId_dich_vu_phong(String id_dich_vu_phong) {
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
