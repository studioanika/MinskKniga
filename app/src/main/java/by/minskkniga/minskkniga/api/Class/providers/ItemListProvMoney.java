package by.minskkniga.minskkniga.api.Class.providers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemListProvMoney {

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
    private int tovar;

    @SerializedName("vozvrat")
    @Expose
    private int vozvrat;

    @SerializedName("oplaty")
    @Expose
    private int oplaty;

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

    public int getTovar() {
        return tovar;
    }

    public void setTovar(int tovar) {
        this.tovar = tovar;
    }

    public int getVozvrat() {
        return vozvrat;
    }

    public void setVozvrat(int vozvrat) {
        this.vozvrat = vozvrat;
    }

    public int getOplaty() {
        return oplaty;
    }

    public void setOplaty(int oplaty) {
        this.oplaty = oplaty;
    }
}
