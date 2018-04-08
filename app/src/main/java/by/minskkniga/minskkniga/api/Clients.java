package by.minskkniga.minskkniga.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Clients {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("sokr_name")
    @Expose
    private String sokrName;
    @SerializedName("info_for_print")
    @Expose
    private String infoForPrint;
    @SerializedName("zametka")
    @Expose
    private String zametka;
    @SerializedName("napravl")
    @Expose
    private String napravl;
    @SerializedName("sity")
    @Expose
    private String sity;
    @SerializedName("school")
    @Expose
    private String school;
    @SerializedName("smena")
    @Expose
    private String smena;
    @SerializedName("type_ceni")
    @Expose
    private String typeCeni;
    @SerializedName("dolg")
    @Expose
    private Double dolg;
    @SerializedName("podarki")
    @Expose
    private String podarki;
    @SerializedName("skidka")
    @Expose
    private String skidka;
    @SerializedName("obraz")
    @Expose
    private String obraz;

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

    public String getSokrName() {
        return sokrName;
    }

    public void setSokrName(String sokrName) {
        this.sokrName = sokrName;
    }

    public String getInfoForPrint() {
        return infoForPrint;
    }

    public void setInfoForPrint(String infoForPrint) {
        this.infoForPrint = infoForPrint;
    }

    public String getZametka() {
        return zametka;
    }

    public void setZametka(String zametka) {
        this.zametka = zametka;
    }

    public String getNapravl() {
        return napravl;
    }

    public void setNapravl(String napravl) {
        this.napravl = napravl;
    }

    public String getSity() {
        return sity;
    }

    public void setSity(String sity) {
        this.sity = sity;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getSmena() {
        return smena;
    }

    public void setSmena(String smena) {
        this.smena = smena;
    }

    public String getTypeCeni() {
        return typeCeni;
    }

    public void setTypeCeni(String typeCeni) {
        this.typeCeni = typeCeni;
    }

    public Double getDolg() {
        return dolg;
    }

    public void setDolg(Double dolg) {
        this.dolg = dolg;
    }

    public String getPodarki() {
        return podarki;
    }

    public void setPodarki(String podarki) {
        this.podarki = podarki;
    }

    public String getSkidka() {
        return skidka;
    }

    public void setSkidka(String skidka) {
        this.skidka = skidka;
    }

    public String getObraz() {
        return obraz;
    }

    public void setObraz(String obraz) {
        this.obraz = obraz;
    }
}