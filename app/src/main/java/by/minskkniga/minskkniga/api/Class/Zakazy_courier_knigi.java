package by.minskkniga.minskkniga.api.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Zakazy_courier_knigi {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("artikyl")
    @Expose
    private String artikyl;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("otgruz")
    @Expose
    private String otgruz;
    @SerializedName("mas")
    @Expose
    private List<Zakazy_courier_knigi_mas> mas;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArtikyl() {
        return artikyl;
    }

    public void setArtikyl(String artikyl) {
        this.artikyl = artikyl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOtgruz() {
        return otgruz;
    }

    public void setOtgruz(String otgruz) {
        this.otgruz = otgruz;
    }

    public List<Zakazy_courier_knigi_mas> getMas() {
        return mas;
    }

    public void setMas(List<Zakazy_courier_knigi_mas> mas) {
        this.mas = mas;
    }

}
