package by.minskkniga.minskkniga.api.Class.providers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ZavInfoTovar {

    @SerializedName("k_id")
    @Expose
    private String k_id;

    @SerializedName("prod_id")
    @Expose
    private String prod_id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("clas")
    @Expose
    private String clas;

    @SerializedName("izdatel")
    @Expose
    private String izdatel;

    @SerializedName("artikul")
    @Expose
    private String artikul;

    @SerializedName("sokr_name")
    @Expose
    private String sokr_name;

    @SerializedName("zayavka")
    @Expose
    private String zayavka;

    @SerializedName("ves")
    @Expose
    private String ves;

    @SerializedName("cena")
    @Expose
    private String cena;

    public String getK_id() {
        return k_id;
    }

    public void setK_id(String k_id) {
        this.k_id = k_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClas() {
        return clas;
    }

    public void setClas(String clas) {
        this.clas = clas;
    }

    public String getIzdatel() {
        return izdatel;
    }

    public void setIzdatel(String izdatel) {
        this.izdatel = izdatel;
    }

    public String getArtikul() {
        return artikul;
    }

    public void setArtikul(String artikul) {
        this.artikul = artikul;
    }

    public String getSokr_name() {
        return sokr_name;
    }

    public void setSokr_name(String sokr_name) {
        this.sokr_name = sokr_name;
    }

    public String getZayavka() {
        return zayavka;
    }

    public void setZayavka(String zayavka) {
        this.zayavka = zayavka;
    }

    public String getVes() {
        return ves;
    }

    public void setVes(String ves) {
        this.ves = ves;
    }

    public String getCena() {
        return cena;
    }

    public void setCena(String cena) {
        this.cena = cena;
    }

    public String getProd_id() {
        return prod_id;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }
}
