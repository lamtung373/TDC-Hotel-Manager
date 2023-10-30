package com.example.tdchotel_manager.Model;

import java.util.HashMap;
import java.util.Map;

public class chi_tiet_dich_vu_phong {
    int so_luong;
    String id_chi_tiet_dich_vu_phong,id_dich_vu_phong,id_phong;

    public chi_tiet_dich_vu_phong() {
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("so_luong", so_luong);
        return result;
    }

    public String getId_chi_tiet_dich_vu_phong() {
        return id_chi_tiet_dich_vu_phong;
    }

    public void setId_chi_tiet_dich_vu_phong(String id_chi_tiet_dich_vu_phong) {
        this.id_chi_tiet_dich_vu_phong = id_chi_tiet_dich_vu_phong;
    }

    public chi_tiet_dich_vu_phong(int so_luong, String id_chi_tiet_dich_vu_phong, String id_dich_vu_phong, String id_phong) {
        this.so_luong = so_luong;
        this.id_chi_tiet_dich_vu_phong = id_chi_tiet_dich_vu_phong;
        this.id_dich_vu_phong = id_dich_vu_phong;
        this.id_phong = id_phong;
    }

    public String getId_dich_vu_phong() {
        return id_dich_vu_phong;
    }

    public void setId_dich_vu_phong(String id_dich_vu_phong) {
        this.id_dich_vu_phong = id_dich_vu_phong;
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
