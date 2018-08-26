package by.minskkniga.minskkniga.api.Class.zakazy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ZakMoneyInfo {

    @SerializedName("vozvrat")
    @Expose
    private int vozvrat;

    @SerializedName("podarki")
    @Expose
    private int podarki;

    @SerializedName("oplaty")
    @Expose
    private int oplaty;

    @SerializedName("tovar")
    @Expose
    private Double tovar;

    @SerializedName("skidka")
    @Expose
    private Double skidka;

    @SerializedName("itog")
    @Expose
    private Double itog;

    public int getVozvrat() {
        return vozvrat;
    }

    public void setVozvrat(int vozvrat) {
        this.vozvrat = vozvrat;
    }

    public int getPodarki() {
        return podarki;
    }

    public void setPodarki(int podarki) {
        this.podarki = podarki;
    }

    public int getOplaty() {
        return oplaty;
    }

    public void setOplaty(int oplaty) {
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
}
