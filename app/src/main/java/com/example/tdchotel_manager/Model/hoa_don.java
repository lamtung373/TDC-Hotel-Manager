package com.example.tdchotel_manager.Model;

import java.util.Date;

public class hoa_don {
    int id_hoa_don, so_dien_thoai, id_phong, id_le_tan, id_dich_vu, id_tien_nghi, id_dich_vu_phong, id_lao_cong;
    String CCCD;
    double tien_coc, tien_phong, tong_phi_dich_vu, tong_phi_dich_vu_phong, tong_phi_tien_nghi, tong_thanh_toan;
    Date thoi_gian_coc, thoi_gian_nhan_phong, thoi_gian_tra_phong, thoi_gian_huy, thoi_gian_thanh_toan;

    //khách đặt trực tiếp
    public hoa_don(int id_hoa_don, int so_dien_thoai, int id_phong, int id_le_tan, double tien_coc, double tien_phong, double tong_thanh_toan, Date thoi_gian_coc, Date thoi_gian_nhan_phong, Date thoi_gian_tra_phong) {
        this.id_hoa_don = id_hoa_don;
        this.so_dien_thoai = so_dien_thoai;
        this.id_phong = id_phong;
        this.id_le_tan = id_le_tan;
        this.tien_coc = tien_coc;
        this.tien_phong = tien_phong;
        this.tong_thanh_toan = tong_thanh_toan;
        this.thoi_gian_coc = thoi_gian_coc;
        this.thoi_gian_nhan_phong = thoi_gian_nhan_phong;
        this.thoi_gian_tra_phong = thoi_gian_tra_phong;
    }

    //khách đặt online
    public hoa_don(int id_hoa_don, int so_dien_thoai, int id_phong, double tien_coc, double tien_phong, double tong_thanh_toan, Date thoi_gian_coc, Date thoi_gian_nhan_phong, Date thoi_gian_tra_phong) {
        this.id_hoa_don = id_hoa_don;
        this.so_dien_thoai = so_dien_thoai;
        this.id_phong = id_phong;
        this.tien_coc = tien_coc;
        this.tien_phong = tien_phong;
        this.tong_thanh_toan = tong_thanh_toan;
        this.thoi_gian_coc = thoi_gian_coc;
        this.thoi_gian_nhan_phong = thoi_gian_nhan_phong;
        this.thoi_gian_tra_phong = thoi_gian_tra_phong;
    }

    //đầy đủ thông tin
    public hoa_don(int id_hoa_don, int so_dien_thoai, int id_phong, int id_le_tan, int id_dich_vu, int id_tien_nghi, int id_dich_vu_phong, int id_lao_cong, String CCCD, double tien_coc, double tien_phong, double tong_phi_dich_vu, double tong_phi_dich_vu_phong, double tong_phi_tien_nghi, double tong_thanh_toan, Date thoi_gian_coc, Date thoi_gian_nhan_phong, Date thoi_gian_tra_phong, Date thoi_gian_huy, Date thoi_gian_thanh_toan) {
        this.id_hoa_don = id_hoa_don;
        this.so_dien_thoai = so_dien_thoai;
        this.id_phong = id_phong;
        this.id_le_tan = id_le_tan;
        this.id_dich_vu = id_dich_vu;
        this.id_tien_nghi = id_tien_nghi;
        this.id_dich_vu_phong = id_dich_vu_phong;
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
}
