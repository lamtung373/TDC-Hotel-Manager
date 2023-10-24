package com.example.tdchotel_manager.Model;

import java.util.HashMap;
import java.util.Map;

public class chi_tiet_hoa_don_tien_nghi {
    int id_hoa_don, id_tien_nghi, so_luong;

    public chi_tiet_hoa_don_tien_nghi(int id_hoa_don, int id_tien_nghi, int so_luong) {
        this.id_hoa_don = id_hoa_don;
        this.id_tien_nghi = id_tien_nghi;
        this.so_luong = so_luong;
    }
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("so_luong", so_luong);

        return result;
    }
    public chi_tiet_hoa_don_tien_nghi() {
    }

    public int getId_hoa_don() {
        return id_hoa_don;
    }

    public void setId_hoa_don(int id_hoa_don) {
        this.id_hoa_don = id_hoa_don;
    }

    public int getId_tien_nghi() {
        return id_tien_nghi;
    }

    public void setId_tien_nghi(int id_tien_nghi) {
        this.id_tien_nghi = id_tien_nghi;
    }

    public int getSo_luong() {
        return so_luong;
    }

    public void setSo_luong(int so_luong) {
        this.so_luong = so_luong;
    }
}
