package by.minskkniga.minskkniga.api.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Zakaz_info {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("zametka")
    @Expose
    private String zametka;
    @SerializedName("autor")
    @Expose
    private String autor;
    @SerializedName("courier")
    @Expose
    private String courier;
    @SerializedName("oplacheno")
    @Expose
    private String oplacheno;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("what_zakazal")
    @Expose
    private List<WhatZakazal> whatZakazal = null;

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

    public String getZametka() {
        return zametka;
    }

    public void setZametka(String zametka) {
        this.zametka = zametka;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCourier() {
        return courier;
    }

    public void setCourier(String courier) {
        this.courier = courier;
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

    public List<WhatZakazal> getWhatZakazal() {
        return whatZakazal;
    }

    public void setWhatZakazal(List<WhatZakazal> whatZakazal) {
        this.whatZakazal = whatZakazal;
    }

}
