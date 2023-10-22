package com.example.tdchotel_manager.Model;

public class phong {
    String  ten_phong, mo_ta_chung, anh_phong;
    int id_phong,trang_thai, luot_thue, gioi_han_nguoi;
    double gia, danh_gia_sao;

    public phong(String ten_phong, String mo_ta_chung, String anh_phong, int id_phong, int trang_thai, int luot_thue, int gioi_han_nguoi, double gia, double danh_gia_sao) {
        this.ten_phong = ten_phong;
        this.mo_ta_chung = mo_ta_chung;
        this.anh_phong = anh_phong;
        this.id_phong = id_phong;
        this.trang_thai = trang_thai;
        this.luot_thue = luot_thue;
        this.gioi_han_nguoi = gioi_han_nguoi;
        this.gia = gia;
        this.danh_gia_sao = danh_gia_sao;
    }

    public String getAnh_phong() {
        return anh_phong;
    }

    public void setAnh_phong(String anh_phong) {
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

    public int getId_phong() {
        return id_phong;
    }

    public void setId_phong(int id_phong) {
        this.id_phong = id_phong;
    }

    public int getTrang_thai() {
        return trang_thai;
    }

    public void setTrang_thai(int trang_thai) {
        this.trang_thai = trang_thai;
    }

    public int getLuot_thue() {
        return luot_thue;
    }

    public void setLuot_thue(int luot_thue) {
        this.luot_thue = luot_thue;
    }

    public int getGioi_han_nguoi() {
        return gioi_han_nguoi;
    }

    public void setGioi_han_nguoi(int gioi_han_nguoi) {
        this.gioi_han_nguoi = gioi_han_nguoi;
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
