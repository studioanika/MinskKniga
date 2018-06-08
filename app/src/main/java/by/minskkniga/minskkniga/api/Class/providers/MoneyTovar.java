package by.minskkniga.minskkniga.api.Class.providers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MoneyTovar {

    @SerializedName("tovar")
    @Expose
    private String tovar;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("oplata")
    @Expose
    private String oplata;

    @SerializedName("voz")
    @Expose
    private String voz;

    public String getTovar() {
        return tovar;
    }

    public void setTovar(String tovar) {
        this.tovar = tovar;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOplata() {
        return oplata;
    }

    public void setOplata(String oplata) {
        this.oplata = oplata;
    }

    public String getVoz() {
        return voz;
    }

    public void setVoz(String voz) {
        this.voz = voz;
    }
}
