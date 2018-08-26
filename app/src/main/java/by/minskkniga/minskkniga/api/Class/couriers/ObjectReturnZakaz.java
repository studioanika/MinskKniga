package by.minskkniga.minskkniga.api.Class.couriers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ObjectReturnZakaz {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("date_izm")
    @Expose
    private String date_izm;

    @SerializedName("zametka")
    @Expose
    private String zametka;

    @SerializedName("autor")
    @Expose
    private String autor;

    @SerializedName("courier")
    @Expose
    private String courier;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("what_zakazal")
    @Expose
    private List<ItemReturnForZakaz> what_zakazal;

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

    public String getDate_izm() {
        return date_izm;
    }

    public void setDate_izm(String date_izm) {
        this.date_izm = date_izm;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ItemReturnForZakaz> getWhat_zakazal() {
        return what_zakazal;
    }

    public void setWhat_zakazal(List<ItemReturnForZakaz> what_zakazal) {
        this.what_zakazal = what_zakazal;
    }
}
