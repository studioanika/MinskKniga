package by.minskkniga.minskkniga.api.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WhatZakazal {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("artikul")
    @Expose
    private String artikul;
    @SerializedName("clas")
    @Expose
    private String clas;
    @SerializedName("izdatel")
    @Expose
    private String izdatel;
    @SerializedName("sokr_name")
    @Expose
    private String sokrName;
    @SerializedName("zakazano")
    @Expose
    private String zakazano;

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

    public String getIzdatel() {
        return izdatel;
    }

    public void setIzdatel(String izdatel) {
        this.izdatel = izdatel;
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

}
