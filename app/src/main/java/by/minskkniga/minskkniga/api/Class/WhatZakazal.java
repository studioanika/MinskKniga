package by.minskkniga.minskkniga.api.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WhatZakazal {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("artikul")
    @Expose
    private String artikul;
    @SerializedName("clas")
    @Expose
    private String clas;
    @SerializedName("sokr_name")
    @Expose
    private String sokrName;
    @SerializedName("izdatel")
    @Expose
    private String izdatel;
    @SerializedName("ves")
    @Expose
    private String ves;
    @SerializedName("zakazano")
    @Expose
    private String zakazano;
    @SerializedName("otgruzeno")
    @Expose
    private String otgruzeno;
    @SerializedName("podarki")
    @Expose
    private String podarki;
    @SerializedName("cena")
    @Expose
    private String cena;

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

    public String getArtikul() {
        return artikul;
    }

    public void setArtikul(String artikul) {
        this.artikul = artikul;
    }

    public String getClas() {
        return clas;
    }

    public void setClas(String clas) {
        this.clas = clas;
    }

    public String getSokrName() {
        return sokrName;
    }

    public void setSokrName(String sokrName) {
        this.sokrName = sokrName;
    }

    public String getIzdatel() {
        return izdatel;
    }

    public void setIzdatel(String izdatel) {
        this.izdatel = izdatel;
    }

    public String getVes() {
        return ves;
    }

    public void setVes(String ves) {
        this.ves = ves;
    }

    public String getZakazano() {
        return zakazano;
    }

    public void setZakazano(String zakazano) {
        this.zakazano = zakazano;
    }

    public String getOtgruzeno() {
        return otgruzeno;
    }

    public void setOtgruzeno(String otgruzeno) {
        this.otgruzeno = otgruzeno;
    }

    public String getPodarki() {
        return podarki;
    }

    public void setPodarki(String podarki) {
        this.podarki = podarki;
    }

    public String getCena() {
        return cena;
    }

    public void setCena(String cena) {
        this.cena = cena;
    }

}
