package by.minskkniga.minskkniga.api.Class.providers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProvMoneyInfo {

    @SerializedName("oplaty")
    @Expose
    private int oplaty;

    @SerializedName("tovar")
    @Expose
    private int tovar;

    @SerializedName("vozvrat")
    @Expose
    private int vozvrat;

    @SerializedName("itog")
    @Expose
    private int itog;

    public int getOplaty() {
        return oplaty;
    }

    public void setOplaty(int oplaty) {
        this.oplaty = oplaty;
    }

    public int getTovar() {
        return tovar;
    }

    public void setTovar(int tovar) {
        this.tovar = tovar;
    }

    public int getVozvrat() {
        return vozvrat;
    }

    public void setVozvrat(int vozvrat) {
        this.vozvrat = vozvrat;
    }

    public int getItog() {
        return itog;
    }

    public void setItog(int itog) {
        this.itog = itog;
    }
}
