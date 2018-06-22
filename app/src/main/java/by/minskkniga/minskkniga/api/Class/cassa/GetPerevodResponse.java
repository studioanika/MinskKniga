package by.minskkniga.minskkniga.api.Class.cassa;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPerevodResponse {

    // TODO здесь нужно id category, podcategory, schet, schet2

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("perevod")
    @Expose
    private String perevod;

    @SerializedName("kom")
    @Expose
    private String kom;

    @SerializedName("category")
    @Expose
    private String category;

    @SerializedName("iz")
    @Expose
    private String iz;

    @SerializedName("v")
    @Expose
    private String v;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPerevod() {
        return perevod;
    }

    public void setPerevod(String perevod) {
        this.perevod = perevod;
    }

    public String getKom() {
        return kom;
    }

    public void setKom(String kom) {
        this.kom = kom;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIz() {
        return iz;
    }

    public void setIz(String iz) {
        this.iz = iz;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }
}
