package by.minskkniga.minskkniga.api.Class.zakazy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ZakMoneyInfo {

    @SerializedName("vozvrat")
    @Expose
    private Double vozvrat;

    @SerializedName("doljny")
    @Expose
    private Double doljny;

    @SerializedName("podarki")
    @Expose
    private Double podarki;

    @SerializedName("oplaty")
    @Expose
    private Double oplaty;

    @SerializedName("tovar")
    @Expose
    private Double tovar;

    @SerializedName("skidka")
    @Expose
    private Double skidka;

    @SerializedName("skidka_proc")
    @Expose
    private Double skidka_proc;

    @SerializedName("itog")
    @Expose
    private Double itog;

    public Double getVozvrat() {
        return vozvrat;
    }

    public void setVozvrat(Double vozvrat) {
        this.vozvrat = vozvrat;
    }

    public Double getPodarki() {
        return podarki;
    }

    public void setPodarki(Double podarki) {
        this.podarki = podarki;
    }

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

    public Double getSkidka() {
        return skidka;
    }

    public void setSkidka(Double skidka) {
        this.skidka = skidka;
    }

    public Double getItog() {
        return itog;
    }

    public void setItog(Double itog) {
        this.itog = itog;
    }

    public Double getSkidka_proc() {
        return skidka_proc;
    }

    public void setSkidka_proc(Double skidka_proc) {
        this.skidka_proc = skidka_proc;
    }

    public Double getDoljny() {
        return doljny;
    }

    public void setDoljny(Double doljny) {
        this.doljny = doljny;
    }
}
