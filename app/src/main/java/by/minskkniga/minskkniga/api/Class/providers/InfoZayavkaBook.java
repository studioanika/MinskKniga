package by.minskkniga.minskkniga.api.Class.providers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InfoZayavkaBook {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("clas")
    @Expose
    private String clas;

    @SerializedName("sokr_name")
    @Expose
    private String sokr_name;

    @SerializedName("obr")
    @Expose
    private String obr;

    @SerializedName("art")
    @Expose
    private String art;

    @SerializedName("izd")
    @Expose
    private String izd;

    @SerializedName("aut")
    @Expose
    private String aut;

    @SerializedName("standart")
    @Expose
    private String standart;

    @SerializedName("upak")
    @Expose
    private String upak;

    @SerializedName("zakazano")
    @Expose
    private Double zakazano;

    @SerializedName("vozvrat")
    @Expose
    private Double vozvrat;

    @SerializedName("ojidaem")
    @Expose
    private Double ojidaem;

    @SerializedName("ostatok")
    @Expose
    private Double ostatok;

    @SerializedName("cost")
    @Expose
    private String cost;

    @SerializedName("zayavka")
    @Expose
    private String zayavka;

    @SerializedName("sum")
    @Expose
    private Double sum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getSokr_name() {
        return sokr_name;
    }

    public void setSokr_name(String sokr_name) {
        this.sokr_name = sokr_name;
    }

    public String getObr() {
        return obr;
    }

    public void setObr(String obr) {
        this.obr = obr;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

    public String getIzd() {
        return izd;
    }

    public void setIzd(String izd) {
        this.izd = izd;
    }

    public String getAut() {
        return aut;
    }

    public void setAut(String aut) {
        this.aut = aut;
    }

    public String getStandart() {
        return standart;
    }

    public void setStandart(String standart) {
        this.standart = standart;
    }

    public String getUpak() {
        return upak;
    }

    public void setUpak(String upak) {
        this.upak = upak;
    }

    public Double getZakazano() {
        return zakazano;
    }

    public void setZakazano(Double zakazano) {
        this.zakazano = zakazano;
    }

    public Double getVozvrat() {
        return vozvrat;
    }

    public void setVozvrat(Double vozvrat) {
        this.vozvrat = vozvrat;
    }

    public Double getOjidaem() {
        return ojidaem;
    }

    public void setOjidaem(Double ojidaem) {
        this.ojidaem = ojidaem;
    }

    public Double getOstatok() {
        return ostatok;
    }

    public void setOstatok(Double ostatok) {
        this.ostatok = ostatok;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getZayavka() {
        return zayavka;
    }

    public void setZayavka(String zayavka) {
        this.zayavka = zayavka;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }
}
