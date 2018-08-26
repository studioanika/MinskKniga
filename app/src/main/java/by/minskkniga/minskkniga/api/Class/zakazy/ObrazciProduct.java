package by.minskkniga.minskkniga.api.Class.zakazy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import by.minskkniga.minskkniga.api.Class.Product;

public class ObrazciProduct extends Product{

    @SerializedName("id_poz")
    @Expose
    private String id_poz;

    @SerializedName("vidan")
    @Expose
    private String vidan;

    @SerializedName("vozvrashen")
    @Expose
    private String vozvrashen;

    public String getId_poz() {
        return id_poz;
    }

    public void setId_poz(String id_poz) {
        this.id_poz = id_poz;
    }

    public String getVidan() {
        return vidan;
    }

    public void setVidan(String vidan) {
        this.vidan = vidan;
    }

    public String getVozvrashen() {
        return vozvrashen;
    }

    public void setVozvrashen(String vozvrashen) {
        this.vozvrashen = vozvrashen;
    }

}
