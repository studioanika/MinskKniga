package by.minskkniga.minskkniga.api.Class.inventarizacia;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Kniga {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("img")
    @Expose
    private String img;

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("clas")
    @Expose
    private String clas;
    @SerializedName("izdatel")
    @Expose
    private String izdatel;
    @SerializedName("artikul")
    @Expose
    private String artikul;
    @SerializedName("obr")
    @Expose
    private Integer obr;
    @SerializedName("cour")
    @Expose
    private Integer cour;
    @SerializedName("fakt")
    @Expose
    private Integer fakt;
    @SerializedName("ost")
    @Expose
    private Integer ost;

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

    public String getIzdatel() {
        return izdatel;
    }

    public void setIzdatel(String izdatel) {
        this.izdatel = izdatel;
    }

    public String getArtikul() {
        return artikul;
    }

    public void setArtikul(String artikul) {
        this.artikul = artikul;
    }

    public Integer getObr() {
        return obr;
    }

    public void setObr(Integer obr) {
        this.obr = obr;
    }

    public Integer getCour() {
        return cour;
    }

    public void setCour(Integer cour) {
        this.cour = cour;
    }

    public Integer getFakt() {
        return fakt;
    }

    public void setFakt(Integer fakt) {
        this.fakt = fakt;
    }

    public Integer getOst() {
        return ost;
    }

    public void setOst(Integer ost) {
        this.ost = ost;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
