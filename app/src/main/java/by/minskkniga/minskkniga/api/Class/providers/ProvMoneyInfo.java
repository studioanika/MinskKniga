package by.minskkniga.minskkniga.api.Class.providers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProvMoneyInfo {

    @SerializedName("oplaty")
    @Expose
    private Double oplaty;

    @SerializedName("tovar")
    @Expose
    private Double tovar;

    @SerializedName("vozvrat")
    @Expose
    private Double vozvrat;

    @SerializedName("itog")
    @Expose
    private Double itog;

    public Double getOplaty() {
        return oplaty;
    }

    public void setOplaty(Double oplaty) {
        this.oplaty = oplaty;
    }

    public Double getTovar() {
        return tovar;
    }

    public void setTovar(Double tovar) {
        this.tovar = tovar;
    }

    public Double getVozvrat() {
        return vozvrat;
    }

    public void setVozvrat(Double vozvrat) {
        this.vozvrat = vozvrat;
    }

    public Double getItog() {
        return itog;
    }

    public void setItog(Double itog) {
        this.itog = itog;
    }
}
