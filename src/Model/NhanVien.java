package Model;

import java.sql.Date;

public class NhanVien {
    private int manv;
    private String hoten;
    private String chucvu;
    private String sdt;
    private Date ngaysinh;
    private String taikhoan;
    private String matkhau;

    public NhanVien() {
    }

    public NhanVien(int manv, String hoten, String chucvu, String sdt, Date ngaysinh, String taikhoan, String matkhau) {
        this.manv = manv;
        this.hoten = hoten;
        this.chucvu = chucvu;
        this.sdt = sdt;
        this.ngaysinh = ngaysinh;
        this.taikhoan = taikhoan;
        this.matkhau = matkhau;
    }

    public int getManv() {
        return manv;
    }

    public void setManv(int manv) {
        this.manv = manv;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getChucvu() {
        return chucvu;
    }

    public void setChucvu(String chucvu) {
        this.chucvu = chucvu;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public Date getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(Date ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getTaikhoan() {
        return taikhoan;
    }

    public void setTaikhoan(String taikhoan) {
        this.taikhoan = taikhoan;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }
}
