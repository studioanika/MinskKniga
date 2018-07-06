package by.minskkniga.minskkniga.api.Class.providers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import by.minskkniga.minskkniga.api.Class.Products;

public class ZayavkaInfo extends Products {

    @SerializedName("zakazano")
    @Expose
    private String zakazano;

    @SerializedName("ostatok")
    @Expose
    private String ostatok;


    @SerializedName("ojidaem")
    @Expose
    private String ojidaem;


    @SerializedName("zayavka")
    @Expose
    private String zayavka;

    public String getZakazano() {
        return zakazano;
    }

    public void setZakazano(String zakazano) {
        this.zakazano = zakazano;
    }

    public String getOstatok() {
        return ostatok;
    }

    public void setOstatok(String ostatok) {
        this.ostatok = ostatok;
    }

    public String getOjidaem() {
        return ojidaem;
    }

    public void setOjidaem(String ojidaem) {
        this.ojidaem = ojidaem;
    }

    public String getZayavka() {
        return zayavka;
    }

    public void setZayavka(String zayavka) {
        this.zayavka = zayavka;
    }
}
