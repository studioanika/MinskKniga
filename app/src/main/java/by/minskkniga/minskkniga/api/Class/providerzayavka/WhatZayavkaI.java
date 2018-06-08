package by.minskkniga.minskkniga.api.Class.providerzayavka;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 16.4.18.
 */

public class WhatZayavkaI {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("id_zayavki")
    @Expose
    private String idZayavki;
    @SerializedName("artikul")
    @Expose
    private String artikul;
    @SerializedName("zayavka")
    @Expose
    private String zayavka;
    @SerializedName("lists")
    @Expose
    private java.util.List<BookI> lists = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdZayavki() {
        return idZayavki;
    }

    public void setIdZayavki(String idZayavki) {
        this.idZayavki = idZayavki;
    }

    public String getArtikul() {
        return artikul;
    }

    public void setArtikul(String artikul) {
        this.artikul = artikul;
    }

    public String getZayavka() {
        return zayavka;
    }

    public void setZayavka(String zayavka) {
        this.zayavka = zayavka;
    }

    public java.util.List<BookI> getLists() {
        return lists;
    }

    public void setLists(java.util.List<BookI> lists) {
        this.lists = lists;
    }
}
