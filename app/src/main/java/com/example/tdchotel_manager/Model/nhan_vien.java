package com.example.tdchotel_manager.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class nhan_vien {
    int id_nhan_vien,id_chuc_vu;
    String ten_nhan_vien,username, password,anh_nhan_vien,so_dien_thoai;
    double luong;
    ArrayList<String>  CCCD;

    public nhan_vien(int id_nhan_vien, int id_chuc_vu, String ten_nhan_vien, String username, String password, String anh_nhan_vien, ArrayList<String> CCCD, String so_dien_thoai, double luong) {
        this.id_nhan_vien = id_nhan_vien;
        this.id_chuc_vu = id_chuc_vu;
        this.ten_nhan_vien = ten_nhan_vien;
        this.username = username;
        this.password = password;
        this.anh_nhan_vien = anh_nhan_vien;
        this.CCCD = CCCD;
        this.so_dien_thoai = so_dien_thoai;
        this.luong = luong;
    }
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id_chuc_vu", id_chuc_vu);
        result.put("ten_nhan_vien", ten_nhan_vien);
        result.put("anh_nhan_vien", anh_nhan_vien);
        result.put("so_dien_thoai", so_dien_thoai);
        result.put("luong", luong);

        return result;
    }
    public nhan_vien() {
    }

    public String getAnh_nhan_vien() {
        return anh_nhan_vien;
    }

    public void setAnh_nhan_vien(String anh_nhan_vien) {
        this.anh_nhan_vien = anh_nhan_vien;
    }

    public int getId_nhan_vien() {
        return id_nhan_vien;
    }

    public void setId_nhan_vien(int id_nhan_vien) {
        this.id_nhan_vien = id_nhan_vien;
    }

    public int getId_chuc_vu() {
        return id_chuc_vu;
    }

    public void setId_chuc_vu(int id_chuc_vu) {
        this.id_chuc_vu = id_chuc_vu;
    }

    public String getTen_nhan_vien() {
        return ten_nhan_vien;
    }

    public void setTen_nhan_vien(String ten_nhan_vien) {
        this.ten_nhan_vien = ten_nhan_vien;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<String> getCCCD() {
        return CCCD;
    }

    public void setCCCD(ArrayList<String> CCCD) {
        this.CCCD = CCCD;
    }

    public String getSo_dien_thoai() {
        return so_dien_thoai;
    }

    public void setSo_dien_thoai(String so_dien_thoai) {
        this.so_dien_thoai = so_dien_thoai;
    }

    public double getLuong() {
        return luong;
    }

    public void setLuong(double luong) {
        this.luong = luong;
    }
}