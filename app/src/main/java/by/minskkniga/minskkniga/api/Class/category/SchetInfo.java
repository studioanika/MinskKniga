package by.minskkniga.minskkniga.api.Class.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SchetInfo {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("type")
    @Expose
    private int type;

    @SerializedName("start_seson_sum")
    @Expose
    private String start_seson_sum;

    @SerializedName("koment")
    @Expose
    private String koment;

    @SerializedName("itog")
    @Expose
    private String itog;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getStart_seson_sum() {
        return start_seson_sum;
    }

    public void setStart_seson_sum(String start_seson_sum) {
        this.start_seson_sum = start_seson_sum;
    }

    public String getKoment() {
        return koment;
    }

    public void setKoment(String koment) {
        this.koment = koment;
    }

    public String getItog() {
        return itog;
    }

    public void setItog(String itog) {
        this.itog = itog;
    }
}
