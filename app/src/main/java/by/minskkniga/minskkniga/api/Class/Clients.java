package by.minskkniga.minskkniga.api.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Clients {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("id_client")
    @Expose
    private String id_client;

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

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("client_name")
    @Expose
    private String client_name;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("oplacheno")
    @Expose
    private String oplacheno;

    @SerializedName("col_vo")
    @Expose
    private int col_vo;

    @SerializedName("ves")
    @Expose
    private Double ves;

    @SerializedName("summa")
    @Expose
    private Double summa;


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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOplacheno() {
        return oplacheno;
    }

    public void setOplacheno(String oplacheno) {
        this.oplacheno = oplacheno;
    }

    public int getCol_vo() {
        return col_vo;
    }

    public void setCol_vo(int col_vo) {
        this.col_vo = col_vo;
    }

    public Double getVes() {
        return ves;
    }

    public void setVes(Double ves) {
        this.ves = ves;
    }

    public Double getSumma() {
        return summa;
    }

    public String getId_client() {
        return id_client;
    }

    public void setId_client(String id_client) {
        this.id_client = id_client;
    }

    public void setSumma(Double summa) {
        this.summa = summa;
    }
}