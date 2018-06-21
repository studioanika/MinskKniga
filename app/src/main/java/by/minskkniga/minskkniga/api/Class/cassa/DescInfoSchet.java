package by.minskkniga.minskkniga.api.Class.cassa;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DescInfoSchet {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("dohod")
    @Expose
    private Double dohod;

    @SerializedName("rashod")
    @Expose
    private Double rashod;

    @SerializedName("operation_sum")
    @Expose
    private Double operation_sum;

    @SerializedName("schet")
    @Expose
    private String schet;

    @SerializedName("schet_perevoda")
    @Expose
    private String schet_perevoda;

    @SerializedName("category")
    @Expose
    private String category;

    @SerializedName("pod_cat")
    @Expose
    private String pod_cat;

    @SerializedName("comment")
    @Expose
    private String comment;

    @SerializedName("kontragent")
    @Expose
    private String kontragent;

    public String getKontragent() {
        return kontragent;
    }

    public void setKontragent(String kontragent) {
        this.kontragent = kontragent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getDohod() {
        return dohod;
    }

    public void setDohod(Double dohod) {
        this.dohod = dohod;
    }

    public Double getRashod() {
        return rashod;
    }

    public void setRashod(Double rashod) {
        this.rashod = rashod;
    }

    public Double getOperation_sum() {
        return operation_sum;
    }

    public void setOperation_sum(Double operation_sum) {
        this.operation_sum = operation_sum;
    }

    public String getSchet() {
        return schet;
    }

    public void setSchet(String schet) {
        this.schet = schet;
    }

    public String getSchet_perevoda() {
        return schet_perevoda;
    }

    public void setSchet_perevoda(String schet_perevoda) {
        this.schet_perevoda = schet_perevoda;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPod_cat() {
        return pod_cat;
    }

    public void setPod_cat(String pod_cat) {
        this.pod_cat = pod_cat;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
