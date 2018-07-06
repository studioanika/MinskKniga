package by.minskkniga.minskkniga.api.Class.providers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OzhidaemObject {

    @SerializedName("client")
    @Expose
    private String client;

    @SerializedName("ojidaem")
    @Expose
    private String ojidaem;

    @SerializedName("vozvrat")
    @Expose
    private String vozvrat;

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getOjidaem() {
        return ojidaem;
    }

    public void setOjidaem(String ojidaem) {
        this.ojidaem = ojidaem;
    }

    public String getVozvrat() {
        return vozvrat;
    }

    public void setVozvrat(String vozvrat) {
        this.vozvrat = vozvrat;
    }
}
