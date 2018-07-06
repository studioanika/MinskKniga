package by.minskkniga.minskkniga.api.Class.cassa;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetDohodResponse {



    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("id")
    @Expose
    private String id;


    @SerializedName("schet_id")
    @Expose
    private String schet_id;

    @SerializedName("category_id")
    @Expose
    private String cat_id;

    @SerializedName("podcategory_id")
    @Expose
    private String pod_cat_id;

    @SerializedName("prihod")
    @Expose
    private String prihod;

    @SerializedName("kom")
    @Expose
    private String kom;

    @SerializedName("client")
    @Expose
    private String client;

    @SerializedName("client_id")
    @Expose
    private String client_id;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSchet_id() {
        return schet_id;
    }

    public void setSchet_id(String schet_id) {
        this.schet_id = schet_id;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getPod_cat_id() {
        return pod_cat_id;
    }

    public void setPod_cat_id(String pod_cat_id) {
        this.pod_cat_id = pod_cat_id;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }
}
