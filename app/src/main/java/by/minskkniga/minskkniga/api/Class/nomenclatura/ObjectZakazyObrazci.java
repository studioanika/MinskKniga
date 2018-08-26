package by.minskkniga.minskkniga.api.Class.nomenclatura;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import by.minskkniga.minskkniga.api.Class.Product;

public class ObjectZakazyObrazci implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("admin_id")
    @Expose
    private String admin_id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("koment")
    @Expose
    private String koment;

    @SerializedName("ves")
    @Expose
    private Double ves;

    @SerializedName("cena")
    @Expose
    private Double cena;

    @SerializedName("list")
    @Expose
    private List<Product> list;


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

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKoment() {
        return koment;
    }

    public void setKoment(String koment) {
        this.koment = koment;
    }

    public Double getVes() {
        return ves;
    }

    public void setVes(Double ves) {
        this.ves = ves;
    }

    public Double getCena() {
        return cena;
    }

    public void setCena(Double cena) {
        this.cena = cena;
    }

    public List<Product> getList() {
        return list;
    }

    public void setList(List<Product> list) {
        this.list = list;
    }


}
