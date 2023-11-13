package com.example.tdchotel_manager.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class tien_nghi {
    int  gia_tien_nghi;
    String id_tien_nghi,ten_tien_nghi;
    String anh_tien_nghi;

    int so_luong;

    public int getSo_luong() {
        return so_luong;
    }

    public void setSo_luong(int so_luong) {
        this.so_luong = so_luong;
    }

    public tien_nghi(String id_tien_nghi, int gia_tien_nghi, String ten_tien_nghi, String anh_tien_nghi) {
        this.id_tien_nghi = id_tien_nghi;
        this.gia_tien_nghi = gia_tien_nghi;
        this.ten_tien_nghi = ten_tien_nghi;
        this.anh_tien_nghi = anh_tien_nghi;
    }

    public tien_nghi() {
    }


    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("gia_tien_nghi", gia_tien_nghi);
        result.put("ten_tien_nghi", ten_tien_nghi);
        result.put("anh_tien_nghi", anh_tien_nghi);
        return result;
    }
    public String getAnh_tien_nghi() {
        return anh_tien_nghi;
    }

    public void setAnh_tien_nghi(String anh_tien_nghi) {
        this.anh_tien_nghi = anh_tien_nghi;
    }

    public String getId_tien_nghi() {
        return id_tien_nghi;
    }

    public void setId_tien_nghi(String id_tien_nghi) {
        this.id_tien_nghi = id_tien_nghi;
    }

    public int getGia_tien_nghi() {
        return gia_tien_nghi;
    }

    public void setGia_tien_nghi(int gia_tien_nghi) {
        this.gia_tien_nghi = gia_tien_nghi;
    }

    public String getTen_tien_nghi() {
        return ten_tien_nghi;
    }

    public void setTen_tien_nghi(String ten_tien_nghi) {
        this.ten_tien_nghi = ten_tien_nghi;
    }
}
