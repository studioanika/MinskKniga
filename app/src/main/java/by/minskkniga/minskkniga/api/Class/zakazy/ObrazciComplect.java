package by.minskkniga.minskkniga.api.Class.zakazy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ObrazciComplect {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("komplect_name")
    @Expose
    private String komplect_name;

    @SerializedName("komplekt_ves")
    @Expose
    private String komplect_ves;

    @SerializedName("komplekt_cena")
    @Expose
    private String komplect_cena;

    @SerializedName("summa")
    @Expose
    private String summa;

    @SerializedName("komplekt_list")
    @Expose
    private List<ObrazciProduct> komplect_list;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKomplect_name() {
        return komplect_name;
    }

    public void setKomplect_name(String komplect_name) {
        this.komplect_name = komplect_name;
    }

    public String getKomplect_cena() {
        return komplect_cena;
    }

    public void setKomplect_cena(String komplect_cena) {
        this.komplect_cena = komplect_cena;
    }

    public List<ObrazciProduct> getKomplect_list() {
        return komplect_list;
    }

    public void setKomplect_list(List<ObrazciProduct> komplect_list) {
        this.komplect_list = komplect_list;
    }

    public String getKomplect_ves() {
        return komplect_ves;
    }

    public void setKomplect_ves(String komplect_ves) {
        this.komplect_ves = komplect_ves;
    }

    public String getSumma() {
        return summa;
    }

    public void setSumma(String summa) {
        this.summa = summa;
    }
}
