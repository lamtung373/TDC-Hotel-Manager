package com.example.tdchotel_manager.Model;

import java.util.HashMap;
import java.util.Map;

public class chi_tiet_hoa_don_dich_vu_phong {
    int so_luong;
String id_chi_tiet_hoa_don_dich_vu_phong,id_hoa_don, id_dich_vu_phong;

    public String getId_chi_tiet_hoa_don_dich_vu_phong() {
        return id_chi_tiet_hoa_don_dich_vu_phong;
    }

    public void setId_chi_tiet_hoa_don_dich_vu_phong(String id_chi_tiet_hoa_don_dich_vu_phong) {
        this.id_chi_tiet_hoa_don_dich_vu_phong = id_chi_tiet_hoa_don_dich_vu_phong;
    }

    public chi_tiet_hoa_don_dich_vu_phong(int so_luong, String id_chi_tiet_hoa_don_dich_vu_phong, String id_hoa_don, String id_dich_vu_phong) {
        this.so_luong = so_luong;
        this.id_chi_tiet_hoa_don_dich_vu_phong = id_chi_tiet_hoa_don_dich_vu_phong;
        this.id_hoa_don = id_hoa_don;
        this.id_dich_vu_phong = id_dich_vu_phong;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("so_luong", so_luong);

        return result;
    }
    public chi_tiet_hoa_don_dich_vu_phong() {
    }

    public String getId_hoa_don() {
        return id_hoa_don;
    }

    public void setId_hoa_don(String id_hoa_don) {
        this.id_hoa_don = id_hoa_don;
    }

    public String getId_dich_vu_phong() {
        return id_dich_vu_phong;
    }

    public void setId_dich_vu_phong(String id_dich_vu_phong) {
        this.id_dich_vu_phong = id_dich_vu_phong;
    }

    public int getSo_luong() {
        return so_luong;
    }

    public void setSo_luong(int so_luong) {
        this.so_luong = so_luong;
    }
}
