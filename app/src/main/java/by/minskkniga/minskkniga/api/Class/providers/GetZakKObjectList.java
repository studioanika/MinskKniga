package by.minskkniga.minskkniga.api.Class.providers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetZakKObjectList {

    @SerializedName("client")
    @Expose
    private String client;

    @SerializedName("zak_no")
    @Expose
    private String zak_no;

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getZak_no() {
        return zak_no;
    }

    public void setZak_no(String zak_no) {
        this.zak_no = zak_no;
    }
}
