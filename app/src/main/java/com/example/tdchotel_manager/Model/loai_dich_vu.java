package com.example.tdchotel_manager.Model;

public class loai_dich_vu {
    int id_loai_dich_vu;
    String ten_loai_dich_vu;

    public loai_dich_vu(int id_loai_dich_vu, String ten_loai_dich_vu) {
        this.id_loai_dich_vu = id_loai_dich_vu;
        this.ten_loai_dich_vu = ten_loai_dich_vu;
    }

    public int getId_loai_dich_vu() {
        return id_loai_dich_vu;
    }

    public void setId_loai_dich_vu(int id_loai_dich_vu) {
        this.id_loai_dich_vu = id_loai_dich_vu;
    }

    public String getTen_loai_dich_vu() {
        return ten_loai_dich_vu;
    }

    public void setTen_loai_dich_vu(String ten_loai_dich_vu) {
        this.ten_loai_dich_vu = ten_loai_dich_vu;
    }
}
