package by.minskkniga.minskkniga.api.Class.cassa;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPerevodResponse {

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

    @SerializedName("iz_id")
    @Expose
    private String iz_id;

    @SerializedName("v")
    @Expose
    private String v;

    @SerializedName("v_id")
    @Expose
    private String v_id;

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

    public String getIz_id() {
        return iz_id;
    }

    public void setIz_id(String iz_id) {
        this.iz_id = iz_id;
    }

    public String getV_id() {
        return v_id;
    }

    public void setV_id(String v_id) {
        this.v_id = v_id;
    }
}
