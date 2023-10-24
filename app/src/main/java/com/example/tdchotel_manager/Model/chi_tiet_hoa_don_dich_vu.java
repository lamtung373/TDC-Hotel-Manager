package com.example.tdchotel_manager.Model;

import java.util.HashMap;
import java.util.Map;

public class chi_tiet_hoa_don_dich_vu {
    int id_hoa_don, id_dich_vu, so_luong;

    public chi_tiet_hoa_don_dich_vu(int id_hoa_don, int id_dich_vu, int so_luong) {
        this.id_hoa_don = id_hoa_don;
        this.id_dich_vu = id_dich_vu;
        this.so_luong = so_luong;
    }
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("so_luong", so_luong);

        return result;
    }
    public chi_tiet_hoa_don_dich_vu() {
    }

    public int getId_hoa_don() {
        return id_hoa_don;
    }

    public void setId_hoa_don(int id_hoa_don) {
        this.id_hoa_don = id_hoa_don;
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
