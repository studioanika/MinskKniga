package by.minskkniga.minskkniga.api.Class.zakazy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemListZakMoney {

    @SerializedName("id")
    @Expose
    private String id;


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
    private int vozvrat;

    @SerializedName("oplaty")
    @Expose
    private Double oplaty;

    @SerializedName("podarki")
    @Expose
    private int podarki;

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

    public int getVozvrat() {
        return vozvrat;
    }

    public void setVozvrat(int vozvrat) {
        this.vozvrat = vozvrat;
    }

    public Double getOplaty() {
        return oplaty;
    }

    public void setOplaty(Double oplaty) {
        this.oplaty = oplaty;
    }

    public int getPodarki() {
        return podarki;
    }

    public void setPodarki(int podarki) {
        this.podarki = podarki;
    }
}
