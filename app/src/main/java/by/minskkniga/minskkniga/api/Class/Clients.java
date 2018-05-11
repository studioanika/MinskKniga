package by.minskkniga.minskkniga.api.Class;

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
    @SerializedName("zametka")
    @Expose
    private String zametka;
    @SerializedName("print")
    @Expose
    private String print;
    @SerializedName("type_c")
    @Expose
    private String typeC;
    @SerializedName("nacenka")
    @Expose
    private String nacenka;
    @SerializedName("podarki")
    @Expose
    private String podarki;
    @SerializedName("skidka")
    @Expose
    private String skidka;
    @SerializedName("dolg")
    @Expose
    private String dolg;
    @SerializedName("napravl")
    @Expose
    private String napravl;
    @SerializedName("gorod_id")
    @Expose
    private String gorodId;
    @SerializedName("school")
    @Expose
    private String school;
    @SerializedName("smena")
    @Expose
    private String smena;
    @SerializedName("obrazec")
    @Expose
    private String obrazec;
    @SerializedName("gorod")
    @Expose
    private String gorod;

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

    public String getZametka() {
        return zametka;
    }

    public void setZametka(String zametka) {
        this.zametka = zametka;
    }

    public String getPrint() {
        return print;
    }

    public void setPrint(String print) {
        this.print = print;
    }

    public String getTypeC() {
        return typeC;
    }

    public void setTypeC(String typeC) {
        this.typeC = typeC;
    }

    public String getNacenka() {
        return nacenka;
    }

    public void setNacenka(String nacenka) {
        this.nacenka = nacenka;
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

    public String getDolg() {
        return dolg;
    }

    public void setDolg(String dolg) {
        this.dolg = dolg;
    }

    public String getNapravl() {
        return napravl;
    }

    public void setNapravl(String napravl) {
        this.napravl = napravl;
    }

    public String getGorodId() {
        return gorodId;
    }

    public void setGorodId(String gorodId) {
        this.gorodId = gorodId;
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

    public String getObrazec() {
        return obrazec;
    }

    public void setObrazec(String obrazec) {
        this.obrazec = obrazec;
    }

    public String getGorod() {
        return gorod;
    }

    public void setGorod(String gorod) {
        this.gorod = gorod;
    }
}