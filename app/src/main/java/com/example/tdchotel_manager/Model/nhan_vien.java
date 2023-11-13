package com.example.tdchotel_manager.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class nhan_vien implements Serializable {
    String id_nhan_vien,id_chuc_vu;
    String ten_nhan_vien, username, password, anh_nhan_vien, so_dien_thoai, chuc_vu;
    double luong;
    private String anh_CCCD_Truoc;
    private String anh_CCCD_Sau;
    private cham_cong cham_cong;

    public com.example.tdchotel_manager.Model.cham_cong getCham_cong() {
        return cham_cong;
    }

    public void setCham_cong(com.example.tdchotel_manager.Model.cham_cong cham_cong) {
        this.cham_cong = cham_cong;
    }

    List<phan_cong> phanCongList;

    public List<phan_cong> getPhanCongList() {
        return phanCongList;
    }

    public void setPhanCongList(List<phan_cong> phanCongList) {
        this.phanCongList = phanCongList;
    }

    public String getChuc_vu() {
        return chuc_vu;
    }

    public void setChuc_vu(String chuc_vu) {
        this.chuc_vu = chuc_vu;
    }

    public nhan_vien(String id_nhan_vien, String id_chuc_vu, String ten_nhan_vien, String username, String password, String anh_nhan_vien, String so_dien_thoai, double luong, String anh_CCCD_Truoc, String anh_CCCD_Sau) {
        this.id_nhan_vien = id_nhan_vien;
        this.id_chuc_vu = id_chuc_vu;
        this.ten_nhan_vien = ten_nhan_vien;
        this.username = username;
        this.password = password;
        this.anh_nhan_vien = anh_nhan_vien;
        this.so_dien_thoai = so_dien_thoai;
        this.luong = luong;
        this.anh_CCCD_Truoc = anh_CCCD_Truoc;
        this.anh_CCCD_Sau = anh_CCCD_Sau;
    }
    public Map<String, Object> toMap() {
        // Tạo một Map chứa dữ liệu của đối tượng NhanVien để đẩy lên Firebase
        Map<String, Object> result = new HashMap<>();
        result.put("id_chuc_vu", id_chuc_vu);
        result.put("ten_nhan_vien", ten_nhan_vien);
        result.put("username", username);
        result.put("password", password);
        result.put("anh_nhan_vien", anh_nhan_vien);
        result.put("so_dien_thoai", so_dien_thoai);
        result.put("luong", luong);
        result.put("chuc_vu", chuc_vu);
        result.put("anh_CCCD_Truoc", anh_CCCD_Truoc);
        result.put("anh_CCCD_Sau", anh_CCCD_Sau);

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

    public String getId_nhan_vien() {
        return id_nhan_vien;
    }

    public void setId_nhan_vien(String id_nhan_vien) {
        this.id_nhan_vien = id_nhan_vien;
    }

    public String getId_chuc_vu() {
        return id_chuc_vu;
    }

    public void setId_chuc_vu(String id_chuc_vu) {
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

    public String getAnh_CCCD_Truoc() {
        return anh_CCCD_Truoc;
    }

    public void setAnh_CCCD_Truoc(String anh_CCCD_Truoc) {
        this.anh_CCCD_Truoc = anh_CCCD_Truoc;
    }

    public String getAnh_CCCD_Sau() {
        return anh_CCCD_Sau;
    }

    public void setAnh_CCCD_Sau(String anh_CCCD_Sau) {
        this.anh_CCCD_Sau = anh_CCCD_Sau;
    }
}
