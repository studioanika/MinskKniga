package by.minskkniga.minskkniga.api.Class.providers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ZavInfo {

    @SerializedName("id")
    @Expose
    private String id;

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    @SerializedName("autor")
    @Expose
    private String autor;

    @SerializedName("provedires")
    @Expose
    private String provedires;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("date2")
    @Expose
    private String date2;

    @SerializedName("oplacheno")
    @Expose
    private String oplacheno;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("komment")
    @Expose
    private String komment;

    @SerializedName("summa")
    @Expose
    private Double summa;

    @SerializedName("ves")
    @Expose
    private Double ves;

    @SerializedName("what_zakazal")
    @Expose
    private List<ZavInfoTovar> what_zakazal;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProvedires() {
        return provedires;
    }

    public void setProvedires(String provedires) {
        this.provedires = provedires;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate2() {
        return date2;
    }

    public void setDate2(String date2) {
        this.date2 = date2;
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

    public String getKomment() {
        return komment;
    }

    public void setKomment(String komment) {
        this.komment = komment;
    }

    public Double getSumma() {
        return summa;
    }

    public void setSumma(Double summa) {
        this.summa = summa;
    }

    public Double getVes() {
        return ves;
    }

    public void setVes(Double ves) {
        this.ves = ves;
    }

    public List<ZavInfoTovar> getWhat_zakazal() {
        return what_zakazal;
    }

    public void setWhat_zakazal(List<ZavInfoTovar> what_zakazal) {
        this.what_zakazal = what_zakazal;
    }
}
