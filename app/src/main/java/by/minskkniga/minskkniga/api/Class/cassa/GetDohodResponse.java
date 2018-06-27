package by.minskkniga.minskkniga.api.Class.cassa;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetDohodResponse {

    // TODO здесь нужно id, category, podcategory, schet

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("prihod")
    @Expose
    private String prihod;

    @SerializedName("kom")
    @Expose
    private String kom;

    @SerializedName("client")
    @Expose
    private String client;

    @SerializedName("schet")
    @Expose
    private String schet;

    @SerializedName("category")
    @Expose
    private String category;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrihod() {
        return prihod;
    }

    public void setPrihod(String prihod) {
        this.prihod = prihod;
    }

    public String getKom() {
        return kom;
    }

    public void setKom(String kom) {
        this.kom = kom;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getSchet() {
        return schet;
    }

    public void setSchet(String schet) {
        this.schet = schet;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
