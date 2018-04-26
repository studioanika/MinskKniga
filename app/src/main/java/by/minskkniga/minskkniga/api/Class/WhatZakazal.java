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
    private String sokrName;
    @SerializedName("zakazano")
    @Expose
    private String zakazano;
    @SerializedName("ves")
    @Expose
    private String ves;
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

    public String getSokrName() {
        return sokrName;
    }

    public void setSokrName(String sokrName) {
        this.sokrName = sokrName;
    }

    public String getZakazano() {
        return zakazano;
    }

    public void setZakazano(String zakazano) {
        this.zakazano = zakazano;
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
}
