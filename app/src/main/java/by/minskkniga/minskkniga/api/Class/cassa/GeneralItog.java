package by.minskkniga.minskkniga.api.Class.cassa;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeneralItog {

    @SerializedName("activy")
    @Expose
    private Double activy;
    @SerializedName("obyazatelstva")
    @Expose
    private Integer obyazatelstva;
    @SerializedName("balans")
    @Expose
    private Double balans;

    public Double getActivy() {
        return activy;
    }

    public void setActivy(Double activy) {
        this.activy = activy;
    }

    public Integer getObyazatelstva() {
        return obyazatelstva;
    }

    public void setObyazatelstva(Integer obyazatelstva) {
        this.obyazatelstva = obyazatelstva;
    }

    public Double getBalans() {
        return balans;
    }

    public void setBalans(Double balans) {
        this.balans = balans;
    }
}
