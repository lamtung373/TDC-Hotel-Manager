package com.example.tdchotel_manager.Model;

import java.util.HashMap;
import java.util.Map;

public class chi_tiet_tien_nghi {
    int so_luong;
String id_chi_tiet_tien_nghi, id_tien_nghi,id_phong;
    public chi_tiet_tien_nghi() {
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("so_luong", so_luong);
        return result;
    }

    public chi_tiet_tien_nghi(int so_luong, String id_chi_tiet_tien_nghi, String id_tien_nghi, String id_phong) {
        this.so_luong = so_luong;
        this.id_chi_tiet_tien_nghi = id_chi_tiet_tien_nghi;
        this.id_tien_nghi = id_tien_nghi;
        this.id_phong = id_phong;
    }
    public chi_tiet_tien_nghi(String id_tien_nghi) {
        this.id_tien_nghi = id_tien_nghi;
    }

    public String getId_chi_tiet_tien_nghi() {
        return id_chi_tiet_tien_nghi;
    }

    public void setId_chi_tiet_tien_nghi(String id_chi_tiet_tien_nghi) {
        this.id_chi_tiet_tien_nghi = id_chi_tiet_tien_nghi;
    }

    public String getId_tien_nghi() {
        return id_tien_nghi;
    }

    public void setId_tien_nghi(String id_tien_nghi) {
        this.id_tien_nghi = id_tien_nghi;
    }

    public String getId_phong() {
        return id_phong;
    }

    public void setId_phong(String id_phong) {
        this.id_phong = id_phong;
    }

    public int getSo_luong() {
        return so_luong;
    }

    public void setSo_luong(int so_luong) {
        this.so_luong = so_luong;
    }
}
