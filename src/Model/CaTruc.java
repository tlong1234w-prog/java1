package Model;

public class CaTruc {
    private String maca;
    private String tenca;
    private String thoigianbatdau;
    private String thoigianketthuc;

    public CaTruc() {}

    public CaTruc(String maca, String tenca, String thoigianbatdau, String thoigianketthuc) {
        this.maca = maca;
        this.tenca = tenca;
        this.thoigianbatdau = thoigianbatdau;
        this.thoigianketthuc = thoigianketthuc;
    }

    public String getMaca() {
        return maca;
    }

    public void setMaca(String maca) {
        this.maca = maca;
    }

    public String getTenca() {
        return tenca;
    }

    public void setTenca(String tenca) {
        this.tenca = tenca;
    }

    public String getThoigianbatdau() {
        return thoigianbatdau;
    }

    public void setThoigianbatdau(String thoigianbatdau) {
        this.thoigianbatdau = thoigianbatdau;
    }

    public String getThoigianketthuc() {
        return thoigianketthuc;
    }

    public void setThoigianketthuc(String thoigianketthuc) {
        this.thoigianketthuc = thoigianketthuc;
    }
}

