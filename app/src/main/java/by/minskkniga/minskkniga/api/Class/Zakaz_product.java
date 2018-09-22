package by.minskkniga.minskkniga.api.Class;

import android.os.Parcel;
import android.os.Parcelable;

public class Zakaz_product implements Parcelable {

    public String id;
    public String name;
    public String artukil;
    public String cena;
    public String col_zakaz;
    public String col_podar;
    public String ispodar;
    public String const_podar;
    public String summa;
    public String otgruzeno;
    public String ves;
    public String image;
    public String clas;
    public String barcode;
    public String barcode_status;
    public String id_poz;
    public String sokr;

    public String getCol_zakaz() {
        return col_zakaz;
    }

    public void setCol_zakaz(String col_zakaz) {
        this.col_zakaz = col_zakaz;
    }

    public String getOtgruzeno() {
        return otgruzeno;
    }

    public void setOtgruzeno(String otgruzeno) {
        this.otgruzeno = otgruzeno;
    }

    public Zakaz_product(String id_poz, String id, String name, String artukil, String cena, String col_zakaz, String col_podar, String ispodar, String const_podar, String summa, String otgruzeno, String ves, String image, String clas, String barcode, String barcode_status) {
        this.id = id;
        this.id_poz = id_poz;
        this.name = name;
        this.artukil = artukil;
        this.cena = cena;
        this.col_zakaz = col_zakaz;
        this.col_podar = col_podar;
        this.ispodar = ispodar;
        this.const_podar = const_podar;
        this.summa = summa;
        this.otgruzeno = otgruzeno;
        this.ves = ves;
        this.image = image;
        this.clas = clas;
        this.barcode = barcode;
        this.barcode_status = barcode_status;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(id_poz);
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(artukil);
        parcel.writeString(cena);
        parcel.writeString(col_zakaz);
        parcel.writeString(col_podar);
        parcel.writeString(ispodar);
        parcel.writeString(const_podar);
        parcel.writeString(summa);
        parcel.writeString(otgruzeno);
        parcel.writeString(ves);
        parcel.writeString(image);
        parcel.writeString(clas);
        parcel.writeString(barcode);
        parcel.writeString(barcode_status);
        parcel.writeString(sokr);
    }

    public static final Parcelable.Creator<Zakaz_product> CREATOR = new Parcelable.Creator<Zakaz_product>() {
        public Zakaz_product createFromParcel(Parcel in) {
            return new Zakaz_product(in);
        }

        public Zakaz_product[] newArray(int size) {
            return new Zakaz_product[size];
        }
    };

    private Zakaz_product(Parcel parcel) {
        id_poz = parcel.readString();
        id = parcel.readString();
        name = parcel.readString();
        artukil = parcel.readString();
        cena = parcel.readString();
        col_zakaz = parcel.readString();
        col_podar = parcel.readString();
        ispodar = parcel.readString();
        const_podar = parcel.readString();
        summa = parcel.readString();
        otgruzeno = parcel.readString();
        ves = parcel.readString();
        image = parcel.readString();
        clas = parcel.readString();
        barcode = parcel.readString();
        barcode_status = parcel.readString();
        sokr = parcel.readString();
    }

    public String getSokr() {
        return sokr;
    }

    public void setSokr(String sokr) {
        this.sokr = sokr;
    }
}
