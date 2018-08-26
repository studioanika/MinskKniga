package by.minskkniga.minskkniga.api.Class.couriers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CourierKnigi {

    @SerializedName("prod_id")
    @Expose
    private String prod_id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("kur")
    @Expose
    private String kur;

    @SerializedName("list_zakaz")
    @Expose
    private List<CurZakaz> list_zakaz;

    public List<CurZakaz> getList_zakaz() {
        return list_zakaz;
    }

    public void setList_zakaz(List<CurZakaz> list_zakaz) {
        this.list_zakaz = list_zakaz;
    }

    public String getProd_id() {
        return prod_id;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKur() {
        return kur;
    }

    public void setKur(String kur) {
        this.kur = kur;
    }
}
