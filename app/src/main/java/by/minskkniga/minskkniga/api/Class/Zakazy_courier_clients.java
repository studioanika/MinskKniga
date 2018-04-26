package by.minskkniga.minskkniga.api.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Zakazy_courier_clients {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("info")
    @Expose
    private String info;
    @SerializedName("koment")
    @Expose
    private String koment;
    @SerializedName("napravl")
    @Expose
    private String napravl;
    @SerializedName("sity")
    @Expose
    private String sity;
    @SerializedName("school")
    @Expose
    private String school;
    @SerializedName("smena")
    @Expose
    private String smena;
    @SerializedName("summa")
    @Expose
    private String summa;
    @SerializedName("status")
    @Expose
    private String status;

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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getKoment() {
        return koment;
    }

    public void setKoment(String koment) {
        this.koment = koment;
    }

    public String getNapravl() {
        return napravl;
    }

    public void setNapravl(String napravl) {
        this.napravl = napravl;
    }

    public String getSity() {
        return sity;
    }

    public void setSity(String sity) {
        this.sity = sity;
    }

    public Object getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getSmena() {
        return smena;
    }

    public void setSmena(String smena) {
        this.smena = smena;
    }

    public String getSumma() {
        return summa;
    }

    public void setSumma(String summa) {
        this.summa = summa;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
