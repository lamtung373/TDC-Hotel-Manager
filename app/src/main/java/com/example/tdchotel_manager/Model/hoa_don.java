package com.example.tdchotel_manager.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class hoa_don {
    int id_hoa_don, so_dien_thoai, id_phong, id_le_tan, id_lao_cong;
    ArrayList<String> CCCD;
    double tien_coc, tien_phong, tong_phi_dich_vu, tong_phi_dich_vu_phong, tong_phi_tien_nghi, tong_thanh_toan;
    String thoi_gian_coc, thoi_gian_nhan_phong, thoi_gian_tra_phong, thoi_gian_huy, thoi_gian_thanh_toan;

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("tien_coc", tien_coc);
        result.put("tien_phong", tien_phong);
        result.put("tong_thanh_toan", tong_thanh_toan);
        result.put("thoi_gian_coc", thoi_gian_coc);
        result.put("thoi_gian_nhan_phong", thoi_gian_nhan_phong);
        result.put("thoi_gian_tra_phong", thoi_gian_tra_phong);

        return result;
    }
    public hoa_don() {
    }

    //đầy đủ thông tin
    public hoa_don(int id_hoa_don, int so_dien_thoai, int id_phong, int id_le_tan, int id_lao_cong, ArrayList<String> CCCD, double tien_coc, double tien_phong, double tong_phi_dich_vu, double tong_phi_dich_vu_phong, double tong_phi_tien_nghi, double tong_thanh_toan, String thoi_gian_coc, String thoi_gian_nhan_phong, String thoi_gian_tra_phong, String thoi_gian_huy, String thoi_gian_thanh_toan) {
        this.id_hoa_don = id_hoa_don;
        this.so_dien_thoai = so_dien_thoai;
        this.id_phong = id_phong;
        this.id_le_tan = id_le_tan;
        this.id_lao_cong = id_lao_cong;
        this.CCCD = CCCD;
        this.tien_coc = tien_coc;
        this.tien_phong = tien_phong;
        this.tong_phi_dich_vu = tong_phi_dich_vu;
        this.tong_phi_dich_vu_phong = tong_phi_dich_vu_phong;
        this.tong_phi_tien_nghi = tong_phi_tien_nghi;
        this.tong_thanh_toan = tong_thanh_toan;
        this.thoi_gian_coc = thoi_gian_coc;
        this.thoi_gian_nhan_phong = thoi_gian_nhan_phong;
        this.thoi_gian_tra_phong = thoi_gian_tra_phong;
        this.thoi_gian_huy = thoi_gian_huy;
        this.thoi_gian_thanh_toan = thoi_gian_thanh_toan;
    }

    public int getId_hoa_don() {
        return id_hoa_don;
    }

    public void setId_hoa_don(int id_hoa_don) {
        this.id_hoa_don = id_hoa_don;
    }

    public int getSo_dien_thoai() {
        return so_dien_thoai;
    }

    public void setSo_dien_thoai(int so_dien_thoai) {
        this.so_dien_thoai = so_dien_thoai;
    }

    public int getId_phong() {
        return id_phong;
    }

    public void setId_phong(int id_phong) {
        this.id_phong = id_phong;
    }

    public int getId_le_tan() {
        return id_le_tan;
    }

    public void setId_le_tan(int id_le_tan) {
        this.id_le_tan = id_le_tan;
    }

    public int getId_lao_cong() {
        return id_lao_cong;
    }

    public void setId_lao_cong(int id_lao_cong) {
        this.id_lao_cong = id_lao_cong;
    }

    public ArrayList<String> getCCCD() {
        return CCCD;
    }

    public void setCCCD(ArrayList<String> CCCD) {
        this.CCCD = CCCD;
    }

    public double getTien_coc() {
        return tien_coc;
    }

    public void setTien_coc(double tien_coc) {
        this.tien_coc = tien_coc;
    }

    public double getTien_phong() {
        return tien_phong;
    }

    public void setTien_phong(double tien_phong) {
        this.tien_phong = tien_phong;
    }

    public double getTong_phi_dich_vu() {
        return tong_phi_dich_vu;
    }

    public void setTong_phi_dich_vu(double tong_phi_dich_vu) {
        this.tong_phi_dich_vu = tong_phi_dich_vu;
    }

    public double getTong_phi_dich_vu_phong() {
        return tong_phi_dich_vu_phong;
    }

    public void setTong_phi_dich_vu_phong(double tong_phi_dich_vu_phong) {
        this.tong_phi_dich_vu_phong = tong_phi_dich_vu_phong;
    }

    public double getTong_phi_tien_nghi() {
        return tong_phi_tien_nghi;
    }

    public void setTong_phi_tien_nghi(double tong_phi_tien_nghi) {
        this.tong_phi_tien_nghi = tong_phi_tien_nghi;
    }

    public double getTong_thanh_toan() {
        return tong_thanh_toan;
    }

    public void setTong_thanh_toan(double tong_thanh_toan) {
        this.tong_thanh_toan = tong_thanh_toan;
    }

    public String getThoi_gian_coc() {
        return thoi_gian_coc;
    }

    public void setThoi_gian_coc(String thoi_gian_coc) {
        this.thoi_gian_coc = thoi_gian_coc;
    }

    public String getThoi_gian_nhan_phong() {
        return thoi_gian_nhan_phong;
    }

    public void setThoi_gian_nhan_phong(String thoi_gian_nhan_phong) {
        this.thoi_gian_nhan_phong = thoi_gian_nhan_phong;
    }

    public String getThoi_gian_tra_phong() {
        return thoi_gian_tra_phong;
    }

    public void setThoi_gian_tra_phong(String thoi_gian_tra_phong) {
        this.thoi_gian_tra_phong = thoi_gian_tra_phong;
    }

    public String getThoi_gian_huy() {
        return thoi_gian_huy;
    }

    public void setThoi_gian_huy(String thoi_gian_huy) {
        this.thoi_gian_huy = thoi_gian_huy;
    }

    public String getThoi_gian_thanh_toan() {
        return thoi_gian_thanh_toan;
    }

    public void setThoi_gian_thanh_toan(String thoi_gian_thanh_toan) {
        this.thoi_gian_thanh_toan = thoi_gian_thanh_toan;
    }
}
