package by.minskkniga.minskkniga.api.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Zakaz {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("date_izm")
    @Expose
    private String dateIzm;
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

    @SerializedName("skidka")
    @Expose
    private String skidka;

    @SerializedName("ves")
    @Expose
    private Double ves;

    @SerializedName("summa")
    @Expose
    private String summa;

    @SerializedName("what_zakazal")
    @Expose
    private ArrayList<WhatZakazal> whatZakazal = null;

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

    public String getDateIzm() {
        return dateIzm;
    }

    public void setDateIzm(String dateIzm) {
        this.dateIzm = dateIzm;
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

    public ArrayList<WhatZakazal> getWhatZakazal() {
        return whatZakazal;
    }

    public void setWhatZakazal(ArrayList<WhatZakazal> whatZakazal) {
        this.whatZakazal = whatZakazal;
    }

    public String getSkidka() {
        return skidka;
    }

    public void setSkidka(String skidka) {
        this.skidka = skidka;
    }

    public Double getVes() {
        return ves;
    }

    public void setVes(Double ves) {
        this.ves = ves;
    }

    public String getSumma() {
        return summa;
    }

    public void setSumma(String summa) {
        this.summa = summa;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
