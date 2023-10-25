package com.example.tdchotel_manager.Model;

import java.util.HashMap;
import java.util.Map;

public class trang_thai_phong {
    int id_trang_thai_phong;
    String ten_trang_thai;
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("ten_trang_thai", ten_trang_thai);
        return result;
    }
    public int getId_trang_thai_phong() {
        return id_trang_thai_phong;
    }

    public void setId_trang_thai_phong(int id_trang_thai_phong) {
        this.id_trang_thai_phong = id_trang_thai_phong;
    }

    public String getTen_trang_thai() {
        return ten_trang_thai;
    }

    public void setTen_trang_thai(String ten_trang_thai) {
        this.ten_trang_thai = ten_trang_thai;
    }

    public trang_thai_phong(int id_trang_thai_phong, String ten_trang_thai) {
        this.id_trang_thai_phong = id_trang_thai_phong;
        this.ten_trang_thai = ten_trang_thai;
    }

    public trang_thai_phong() {
    }
}
