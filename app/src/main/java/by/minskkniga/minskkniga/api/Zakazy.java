package by.minskkniga.minskkniga.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Zakazy {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("client")
    @Expose
    private String client;
    @SerializedName("summa")
    @Expose
    private String summa;
    @SerializedName("oplata")
    @Expose
    private String oplata;
    @SerializedName("autor")
    @Expose
    private String autor;
    @SerializedName("gorod")
    @Expose
    private String gorod;
    @SerializedName("napravl")
    @Expose
    private String napravl;
    @SerializedName("ispolnenno")
    @Expose
    private String ispolnenno;
    @SerializedName("oplacheno")
    @Expose
    private String oplacheno;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("komment")
    @Expose
    private String komment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getSumma() {
        return summa;
    }

    public void setSumma(String summa) {
        this.summa = summa;
    }

    public String getOplata() {
        return oplata;
    }

    public void setOplata(String oplata) {
        this.oplata = oplata;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getGorod() {
        return gorod;
    }

    public void setGorod(String gorod) {
        this.gorod = gorod;
    }

    public String getNapravl() {
        return napravl;
    }

    public void setNapravl(String napravl) {
        this.napravl = napravl;
    }

    public String getIspolnenno() {
        return ispolnenno;
    }

    public void setIspolnenno(String ispolnenno) {
        this.ispolnenno = ispolnenno;
    }

    public String getOplacheno() {
        return oplacheno;
    }

    public void setOplacheno(String oplacheno) {
        this.oplacheno = oplacheno;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKomment() {
        return komment;
    }

    public void setKomment(String komment) {
        this.komment = komment;
    }


}
