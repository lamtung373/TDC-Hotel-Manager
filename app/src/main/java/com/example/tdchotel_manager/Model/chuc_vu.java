package com.example.tdchotel_manager.Model;

public class chuc_vu {
    int id_chuc_vu;
    String ten_chuc_vu;

    public chuc_vu(int id_chuc_vu, String ten_chuc_vu) {
        this.id_chuc_vu = id_chuc_vu;
        this.ten_chuc_vu = ten_chuc_vu;
    }
    public chuc_vu() {
    }

    public int getId_chuc_vu() {
        return id_chuc_vu;
    }

    public void setId_chuc_vu(int id_chuc_vu) {
        this.id_chuc_vu = id_chuc_vu;
    }

    public String getTen_chuc_vu() {
        return ten_chuc_vu;
    }

    public void setTen_chuc_vu(String ten_chuc_vu) {
        this.ten_chuc_vu = ten_chuc_vu;
    }
}
