package com.example.tdchotel_manager.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class phong implements Serializable{
    String  ten_phong, mo_ta_chung,  loai_phong,id_phong, id_trang_thai_phong;
    ArrayList<String> anh_phong;
    int luot_thue;
    double gia,sale, danh_gia_sao;
    String ngay_don_phong;

    public String getNgay_don_phong() {
        return ngay_don_phong;
    }

    public void setNgay_don_phong(String ngay_don_phong) {
        this.ngay_don_phong = ngay_don_phong;
    }

    public phong(String id_phong, String ten_phong, String mo_ta_chung, ArrayList<String> anh_phong, String loai_phong, String id_trang_thai_phong, int luot_thue, double gia, double sale, double danh_gia_sao) {
        this.ten_phong = ten_phong;
        this.mo_ta_chung = mo_ta_chung;
        this.anh_phong = anh_phong;
        this.loai_phong = loai_phong;
        this.id_phong = id_phong;
        this.id_trang_thai_phong = id_trang_thai_phong;
        this.luot_thue = luot_thue;
        this.gia = gia;
        this.sale = sale;
        this.danh_gia_sao = danh_gia_sao;
    }
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("ten_phong", ten_phong);
        result.put("mo_ta_chung", mo_ta_chung);
        result.put("anh_phong", anh_phong);
        result.put("loai_phong", loai_phong);
        result.put("trang_thai", id_trang_thai_phong);
        result.put("luot_thue", luot_thue);
        result.put("gia", gia);
        result.put("sale", sale);

        return result;
    }
    public phong() {
    }

    public double getSale() {
        return sale;
    }

    public void setSale(double sale) {
        this.sale = sale;
    }

    public String getLoai_phong() {
        return loai_phong;
    }

    public void setLoai_phong(String loai_phong) {
        this.loai_phong = loai_phong;
    }

    public ArrayList<String> getAnh_phong() {
        return anh_phong;
    }

    public void setAnh_phong(ArrayList<String> anh_phong) {
        this.anh_phong = anh_phong;
    }

    public String getTen_phong() {
        return ten_phong;
    }

    public void setTen_phong(String ten_phong) {
        this.ten_phong = ten_phong;
    }

    public String getMo_ta_chung() {
        return mo_ta_chung;
    }

    public void setMo_ta_chung(String mo_ta_chung) {
        this.mo_ta_chung = mo_ta_chung;
    }

    public String getId_phong() {
        return id_phong;
    }

    public void setId_phong(String id_phong) {
        this.id_phong = id_phong;
    }

    public String getId_trang_thai_phong() {
        return id_trang_thai_phong;
    }

    public void setId_trang_thai_phong(String id_trang_thai_phong) {
        this.id_trang_thai_phong = id_trang_thai_phong;
    }

    public int getLuot_thue() {
        return luot_thue;
    }

    public void setLuot_thue(int luot_thue) {
        this.luot_thue = luot_thue;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public double getDanh_gia_sao() {
        return danh_gia_sao;
    }

    public void setDanh_gia_sao(double danh_gia_sao) {
        this.danh_gia_sao = danh_gia_sao;
    }
}
