package com.example.tdchotel_manager.Model;

import java.util.HashMap;
import java.util.Map;

public class chi_tiet_tien_nghi {
    int id_tien_nghi,id_phong,so_luong;

    public chi_tiet_tien_nghi() {
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("so_luong", so_luong);
        return result;
    }
    public chi_tiet_tien_nghi(int id_tien_nghi, int id_phong, int so_luong) {
        this.id_tien_nghi = id_tien_nghi;
        this.id_phong = id_phong;
        this.so_luong = so_luong;
    }

    public int getId_tien_nghi() {
        return id_tien_nghi;
    }

    public void setId_tien_nghi(int id_tien_nghi) {
        this.id_tien_nghi = id_tien_nghi;
    }

    public int getId_phong() {
        return id_phong;
    }

    public void setId_phong(int id_phong) {
        this.id_phong = id_phong;
    }

    public int getSo_luong() {
        return so_luong;
    }

    public void setSo_luong(int so_luong) {
        this.so_luong = so_luong;
    }
}
