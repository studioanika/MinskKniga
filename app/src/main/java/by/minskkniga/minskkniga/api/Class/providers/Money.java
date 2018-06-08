package by.minskkniga.minskkniga.api.Class.providers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Money {

    @SerializedName("visible")
    @Expose
    private String visible;

    @SerializedName("tovari")
    @Expose
    private Double tovari;

    @SerializedName("vozvrat")
    @Expose
    private Double vozvrat;

    @SerializedName("opl")
    @Expose
    private String opl;

    @SerializedName("itog")
    @Expose
    private Double itog;

    @SerializedName("list")
    @Expose
    private List<MoneyTovar> list;

    public Double getTovari() {
        return tovari;
    }

    public void setTovari(Double tovari) {
        this.tovari = tovari;
    }

    public Double getVozvrat() {
        return vozvrat;
    }

    public void setVozvrat(Double vozvrat) {
        this.vozvrat = vozvrat;
    }

    public String getOpl() {
        return opl;
    }

    public void setOpl(String opl) {
        this.opl = opl;
    }

    public Double getItog() {
        return itog;
    }

    public void setItog(Double itog) {
        this.itog = itog;
    }

    public List<MoneyTovar> getList() {
        return list;
    }

    public void setList(List<MoneyTovar> list) {
        this.list = list;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }
}
