package by.minskkniga.minskkniga.api.Class.inventarizacia;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RangItem {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("zakazano")
    @Expose
    private String zakazano;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("otgruzeno")
    @Expose
    private String otgruzeno;

    @SerializedName("oplacheno")
    @Expose
    private String oplacheno;

    @SerializedName("client")
    @Expose
    private String client;

    @SerializedName("kom")
    @Expose
    private String kom;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getOplacheno() {
        return oplacheno;
    }

    public void setOplacheno(String oplacheno) {
        this.oplacheno = oplacheno;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getKom() {
        return kom;
    }

    public void setKom(String kom) {
        this.kom = kom;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
