package com.example.tdchotel_manager.Model;

import java.util.HashMap;
import java.util.Map;

public class khach_hang {
    String so_dien_thoai, ten;

    public khach_hang(String so_dien_thoai, String ten) {
        this.so_dien_thoai = so_dien_thoai;
        this.ten = ten;
    }
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("so_dien_thoai", so_dien_thoai);
        result.put("ten", ten);

        return result;
    }
    public khach_hang() {
    }

    public String getSo_dien_thoai() {
        return so_dien_thoai;
    }

    public void setSo_dien_thoai(String so_dien_thoai) {
        this.so_dien_thoai = so_dien_thoai;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }
}
