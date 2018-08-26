package by.minskkniga.minskkniga.api.Class.couriers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemReturnForObrazci {

    @SerializedName("komplect_id")
    @Expose
    private String komplect_id;

    @SerializedName("komplect_name")
    @Expose
    private String komplect_name;

    @SerializedName("komplekt_kol")
    @Expose
    private String komplekt_kol;

    public String getKomplect_id() {
        return komplect_id;
    }

    public void setKomplect_id(String komplect_id) {
        this.komplect_id = komplect_id;
    }

    public String getKomplect_name() {
        return komplect_name;
    }

    public void setKomplect_name(String komplect_name) {
        this.komplect_name = komplect_name;
    }

    public String getKomplekt_kol() {
        return komplekt_kol;
    }

    public void setKomplekt_kol(String komplekt_kol) {
        this.komplekt_kol = komplekt_kol;
    }
}
