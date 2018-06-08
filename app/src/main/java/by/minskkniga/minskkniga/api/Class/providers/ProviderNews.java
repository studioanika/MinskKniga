package by.minskkniga.minskkniga.api.Class.providers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProviderNews {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("ves")
    @Expose
    private String ves;
    @SerializedName("name")
    @Expose
    private String name;

    public String getVes() {
        return ves;
    }

    public void setVes(String ves) {
        this.ves = ves;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;

    }

}
