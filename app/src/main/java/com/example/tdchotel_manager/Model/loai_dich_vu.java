package com.example.tdchotel_manager.Model;

import java.util.HashMap;
import java.util.Map;

public class loai_dich_vu {
    String id_loai_dich_vu;
    String ten_loai_dich_vu;

    public loai_dich_vu(String id_loai_dich_vu, String ten_loai_dich_vu) {
        this.id_loai_dich_vu = id_loai_dich_vu;
        this.ten_loai_dich_vu = ten_loai_dich_vu;
    }
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("ten_loai_dich_vu", ten_loai_dich_vu);

        return result;
    }
    public loai_dich_vu() {
    }

    public String getId_loai_dich_vu() {
        return id_loai_dich_vu;
    }

    public void setId_loai_dich_vu(String id_loai_dich_vu) {
        this.id_loai_dich_vu = id_loai_dich_vu;
    }

    public String getTen_loai_dich_vu() {
        return ten_loai_dich_vu;
    }

    public void setTen_loai_dich_vu(String ten_loai_dich_vu) {
        this.ten_loai_dich_vu = ten_loai_dich_vu;
    }
}
