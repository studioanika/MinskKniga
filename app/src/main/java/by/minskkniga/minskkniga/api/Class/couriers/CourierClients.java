package by.minskkniga.minskkniga.api.Class.couriers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CourierClients {


    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("all_summa")
    @Expose
    private String all_summa;

    @SerializedName("what_zakazal")
    @Expose
    private List<ZakazCourier> what_zakazal;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAll_summa() {
        return all_summa;
    }

    public void setAll_summa(String all_summa) {
        this.all_summa = all_summa;
    }

    public List<ZakazCourier> getWhat_zakazal() {
        return what_zakazal;
    }

    public void setWhat_zakazal(List<ZakazCourier> what_zakazal) {
        this.what_zakazal = what_zakazal;
    }
}
