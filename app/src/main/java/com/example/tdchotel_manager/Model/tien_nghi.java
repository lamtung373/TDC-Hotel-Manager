package com.example.tdchotel_manager.Model;

public class tien_nghi {
    int id_tien_nghi, gia_tien_nghi;
    String ten_tien_nghi,anh_tien_nghi;

    public tien_nghi(int id_tien_nghi, int gia_tien_nghi, String ten_tien_nghi, String anh_tien_nghi) {
        this.id_tien_nghi = id_tien_nghi;
        this.gia_tien_nghi = gia_tien_nghi;
        this.ten_tien_nghi = ten_tien_nghi;
        this.anh_tien_nghi = anh_tien_nghi;
    }

    public String getAnh_tien_nghi() {
        return anh_tien_nghi;
    }

    public void setAnh_tien_nghi(String anh_tien_nghi) {
        this.anh_tien_nghi = anh_tien_nghi;
    }

    public int getId_tien_nghi() {
        return id_tien_nghi;
    }

    public void setId_tien_nghi(int id_tien_nghi) {
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
