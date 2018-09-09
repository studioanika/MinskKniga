package by.minskkniga.minskkniga.api.Class.inventarizacia;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RangObject {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("clas")
    @Expose
    private String clas;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("zakazano")
    @Expose
    private int zakazano;

    @SerializedName("otgruzeno")
    @Expose
    private int otgruzeno;

    @SerializedName("what_zakaz")
    @Expose
    private List<RangItem> what_zakaz;

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

    public String getClas() {
        return clas;
    }

    public void setClas(String clas) {
        this.clas = clas;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getZakazano() {
        return zakazano;
    }

    public void setZakazano(int zakazano) {
        this.zakazano = zakazano;
    }

    public int getOtgruzeno() {
        return otgruzeno;
    }

    public void setOtgruzeno(int otgruzeno) {
        this.otgruzeno = otgruzeno;
    }

    public List<RangItem> getWhat_zakaz() {
        return what_zakaz;
    }

    public void setWhat_zakaz(List<RangItem> what_zakaz) {
        this.what_zakaz = what_zakaz;
    }
}
