package by.minskkniga.minskkniga.api.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Zakazy_courier_knigi {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("otgruz")
    @Expose
    private String otgruz;
    @SerializedName("knigi")
    @Expose
    private List<Zakazy_courier_knigi_mas> knigi;

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

    public String getOtgruz() {
        return otgruz;
    }

    public void setOtgruz(String otgruz) {
        this.otgruz = otgruz;
    }

    public List<Zakazy_courier_knigi_mas> getKnigi() {
        return knigi;
    }

    public void setKnigi(List<Zakazy_courier_knigi_mas> knigi) {
        this.knigi = knigi;
    }

}
