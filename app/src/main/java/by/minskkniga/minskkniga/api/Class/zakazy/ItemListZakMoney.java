package by.minskkniga.minskkniga.api.Class.zakazy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemListZakMoney {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("id_zak")
    @Expose
    private String id_zak;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("tovar")
    @Expose
    private Double tovar;

    @SerializedName("vozvrat")
    @Expose
    private Double vozvrat;

    @SerializedName("oplaty")
    @Expose
    private Double oplaty;

    @SerializedName("podarki")
    @Expose
    private Double podarki;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getTovar() {
        return tovar;
    }

    public Double getVozvrat() {
        return vozvrat;
    }

    public void setVozvrat(Double vozvrat) {
        this.vozvrat = vozvrat;
    }

    public Double getOplaty() {
        return oplaty;
    }

    public void setOplaty(Double oplaty) {
        this.oplaty = oplaty;
    }

    public void setTovar(Double tovar) {
        this.tovar = tovar;
    }

    public Double getPodarki() {
        return podarki;
    }

    public void setPodarki(Double podarki) {
        this.podarki = podarki;
    }

    public String getId_zak() {
        return id_zak;
    }

    public void setId_zak(String id_zak) {
        this.id_zak = id_zak;
    }
}
