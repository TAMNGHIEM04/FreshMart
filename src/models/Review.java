/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

public class Review {
    private int maDG;
    private int maKH;
    private int maSP;
    private String danhGia;
    private String binhLuan;
    private String trangThai;

    // Constructors, Getters, Setters
    public Review(int maDG, int maKH, int maSP, String danhGia, String binhLuan, String trangThai) {
        this.maDG = maDG;
        this.maKH = maKH;
        this.maSP = maSP;
        this.danhGia = danhGia;
        this.binhLuan = binhLuan;
        this.trangThai = trangThai;
    }

    public int getMaDG() { return maDG; }
    public int getMaKH() { return maKH; }
    public int getMaSP() { return maSP; }
    public String getDanhGia() { return danhGia; }
    public String getBinhLuan() { return binhLuan; }
    public String getTrangThai() { return trangThai; }

    public void setTrangThai(String tt) { this.trangThai = tt; }

    @Override
    public String toString() {
        return "[" + trangThai + "] KH" + maKH + " - " + danhGia + " - " + binhLuan;
    }
}
