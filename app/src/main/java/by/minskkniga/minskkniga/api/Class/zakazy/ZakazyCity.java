package by.minskkniga.minskkniga.api.Class.zakazy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ZakazyCity {

    @SerializedName("id_city")
    @Expose
    private String id_city;

    @SerializedName("name_city")
    @Expose
    private String name_city;

    @SerializedName("dolg")
    @Expose
    private String dolg;
    @SerializedName("obrazec")
    @Expose
    private String obrazec;

    public void setObrazec(String obrazec) {
        this.obrazec = obrazec;
    }

    public String getObrazec() {

        return obrazec;
    }

    public String getId_city() {
        return id_city;
    }

    public void setId_city(String id_city) {
        this.id_city = id_city;
    }

    public String getName_city() {
        return name_city;
    }

    public void setName_city(String name_city) {
        this.name_city = name_city;
    }

    public String getDolg() {
        return dolg;
    }

    public void setDolg(String dolg) {
        this.dolg = dolg;
    }
}
