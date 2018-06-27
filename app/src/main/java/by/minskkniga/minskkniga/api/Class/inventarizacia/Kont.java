package by.minskkniga.minskkniga.api.Class.inventarizacia;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Kont {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("client")
    @Expose
    private String client;
    @SerializedName("kol_vo")
    @Expose
    private String kolVo;
    @SerializedName("type")
    @Expose
    private Integer type;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getKolVo() {
        return kolVo;
    }

    public void setKolVo(String kolVo) {
        this.kolVo = kolVo;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}
