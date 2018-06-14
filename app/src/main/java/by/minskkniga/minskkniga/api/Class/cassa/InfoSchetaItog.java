package by.minskkniga.minskkniga.api.Class.cassa;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InfoSchetaItog {

    @SerializedName("nach_sum")
    @Expose
    private Double nach_sum;

    @SerializedName("start_seson_sum")
    @Expose
    private Double start_seson_sum;

    @SerializedName("dohod")
    @Expose
    private Double dohod;

    @SerializedName("rashod")
    @Expose
    private Double rashod;

    public Double getNach_sum() {
        return nach_sum;
    }

    public void setNach_sum(Double nach_sum) {
        this.nach_sum = nach_sum;
    }

    public Double getStart_seson_sum() {
        return start_seson_sum;
    }

    public void setStart_seson_sum(Double start_seson_sum) {
        this.start_seson_sum = start_seson_sum;
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
}
