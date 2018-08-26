package by.minskkniga.minskkniga.api.Class.couriers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ZakazCourier {

    @SerializedName("id_zakaz")
    @Expose
    private String id_zakaz;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("oplacheno")
    @Expose
    private String oplacheno;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("obrazec")
    @Expose
    private String obrazec;

    @SerializedName("summa")
    @Expose
    private String summa;


    public String getId_zakaz() {
        return id_zakaz;
    }

    public void setId_zakaz(String id_zakaz) {
        this.id_zakaz = id_zakaz;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getObrazec() {
        return obrazec;
    }

    public void setObrazec(String obrazec) {
        this.obrazec = obrazec;
    }

    public String getSumma() {
        return summa;
    }

    public void setSumma(String summa) {
        this.summa = summa;
    }
}
