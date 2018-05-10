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
    public String summa;
    public String otgruzeno;
    public String ves;

    public Zakaz_product(String id, String name, String artukil, String cena, String col_zakaz, String col_podar, String summa, String otgruzeno, String ves) {
        this.id = id;
        this.name = name;
        this.artukil = artukil;
        this.cena = cena;
        this.col_zakaz = col_zakaz;
        this.col_podar = col_podar;
        this.summa = summa;
        this.otgruzeno = otgruzeno;
        this.ves = ves;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(artukil);
        parcel.writeString(cena);
        parcel.writeString(col_zakaz);
        parcel.writeString(col_podar);
        parcel.writeString(summa);
        parcel.writeString(otgruzeno);
        parcel.writeString(ves);
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
        id = parcel.readString();
        name = parcel.readString();
        artukil = parcel.readString();
        cena = parcel.readString();
        col_zakaz = parcel.readString();
        col_podar = parcel.readString();
        summa = parcel.readString();
        otgruzeno = parcel.readString();
        ves = parcel.readString();
    }
}
