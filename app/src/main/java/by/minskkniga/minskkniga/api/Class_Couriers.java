package by.minskkniga.minskkniga.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Class_Couriers {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("summa")
    @Expose
    private Double summa;
    @SerializedName("knigi")
    @Expose
    private Integer knigi;

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

    public Double getSumma() {
        return summa;
    }

    public void setSumma(Double summa) {
        this.summa = summa;
    }

    public Integer getKnigi() {
        return knigi;
    }

    public void setKnigi(Integer knigi) {
        this.knigi = knigi;
    }

}
