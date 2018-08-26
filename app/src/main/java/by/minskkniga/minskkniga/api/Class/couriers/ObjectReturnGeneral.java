package by.minskkniga.minskkniga.api.Class.couriers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ObjectReturnGeneral {

    @SerializedName("prod_id")
    @Expose
    private String prod_id;

    @SerializedName("otgruzeno")
    @Expose
    private String otgruzeno;

    public String getProd_id() {
        return prod_id;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }

    public String getOtgruzeno() {
        return otgruzeno;
    }

    public void setOtgruzeno(String otgruzeno) {
        this.otgruzeno = otgruzeno;
    }
}
