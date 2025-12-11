package Model;
import java.sql.Date;

public class PhanCong {
    private int id;
    private int manv;
    private String maca;
    private Date ngay;
    private String ghiChu;

    public PhanCong() {}

    public PhanCong(int id, int manv, String maca, Date ngay, String ghiChu) {
        this.id = id;
        this.manv = manv;
        this.maca = maca;
        this.ngay = ngay;
        this.ghiChu = ghiChu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getManv() {
        return manv;
    }

    public void setManv(int manv) {
        this.manv = manv;
    }

    public String getMaca() {
        return maca;
    }

    public void setMaca(String maca) {
        this.maca = maca;
    }

    public Date getNgay() {
        return ngay;
    }

    public void setNgay(Date ngay) {
        this.ngay = ngay;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}

