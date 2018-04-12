package by.minskkniga.minskkniga.api.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WhatZakazal {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("id_zakaza")
    @Expose
    private String idZakaza;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("artikyl")
    @Expose
    private String artikyl;
    @SerializedName("class")
    @Expose
    private String _class;
    @SerializedName("izdanie")
    @Expose
    private String izdanie;
    @SerializedName("sokr")
    @Expose
    private String sokr;
    @SerializedName("cena")
    @Expose
    private String cena;
    @SerializedName("zak")
    @Expose
    private String zak;
    @SerializedName("stoim")
    @Expose
    private String stoim;
    @SerializedName("ves")
    @Expose
    private String ves;
    @SerializedName("otgruz")
    @Expose
    private String otgruz;
    @SerializedName("upak")
    @Expose
    private String upak;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdZakaza() {
        return idZakaza;
    }

    public void setIdZakaza(String idZakaza) {
        this.idZakaza = idZakaza;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtikyl() {
        return artikyl;
    }

    public void setArtikyl(String artikyl) {
        this.artikyl = artikyl;
    }

    public String getClass_() {
        return _class;
    }

    public void setClass_(String _class) {
        this._class = _class;
    }

    public String getIzdanie() {
        return izdanie;
    }

    public void setIzdanie(String izdanie) {
        this.izdanie = izdanie;
    }

    public String getSokr() {
        return sokr;
    }

    public void setSokr(String sokr) {
        this.sokr = sokr;
    }

    public String getCena() {
        return cena;
    }

    public void setCena(String cena) {
        this.cena = cena;
    }

    public String getZak() {
        return zak;
    }

    public void setZak(String zak) {
        this.zak = zak;
    }

    public String getStoim() {
        return stoim;
    }

    public void setStoim(String stoim) {
        this.stoim = stoim;
    }

    public String getVes() {
        return ves;
    }

    public void setVes(String ves) {
        this.ves = ves;
    }

    public String getOtgruz() {
        return otgruz;
    }

    public void setOtgruz(String otgruz) {
        this.otgruz = otgruz;
    }

    public String getUpak() {
        return upak;
    }

    public void setUpak(String upak) {
        this.upak = upak;
    }

}
