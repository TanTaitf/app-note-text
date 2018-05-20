package note.media.com.note;

import java.io.Serializable;

/**
 * Created by VP-T on 6/4/2017.
 */

public class Note implements Comparable<Note>, Serializable {
    int id;
    String ten;
    String noidung;
    String ngay;

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public int getId() {
        return id;
    }

    public String getTen() {
        return ten;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public Note(int id, String ten, String noidung, String ngay) {
        this.id = id;
        this.ten = ten;
        this.ngay = ngay;
        this.noidung = noidung;
    }


    @Override
    public String toString() {
        return id + " - " + ten + " - " + noidung + " - " + ngay;
    }

    // sắp xếp theo tên và theo ngay.
    @Override
    public int compareTo(Note o) {
        return this.ten.compareTo(o.ten);

    }


}
