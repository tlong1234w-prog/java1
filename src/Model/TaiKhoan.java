package Model;

public class TaiKhoan {
    private int manv;
    private String hoten;
    private String chucvu;
    private String taikhoan;

    public TaiKhoan() {}

    public TaiKhoan(int manv, String hoten, String chucvu, String taikhoan) {
        this.manv = manv;
        this.hoten = hoten;
        this.chucvu = chucvu;
        this.taikhoan = taikhoan;
    }

    public int getManv() {
        return manv;
    }

    public String getHoten() {
        return hoten;
    }

    public String getChucvu() {
        return chucvu;
    }

    public String getTaikhoan() {
        return taikhoan;
    }

    public void setManv(int manv) { this.manv = manv; }
    public void setHoten(String hoten) { this.hoten = hoten; }
    public void setChucvu(String chucvu) { this.chucvu = chucvu; }
    public void setTaikhoan(String taikhoan) { this.taikhoan = taikhoan; }
}
