package by.minskkniga.minskkniga.api.Class.providers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProviderZayavkiIzdatelstva {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("nal")
    @Expose
    private String nal;
    @SerializedName("beznal")
    @Expose
    private String beznal;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNal() {
        return nal;
    }

    public void setNal(String nal) {
        this.nal = nal;
    }

    public String getBeznal() {
        return beznal;
    }

    public void setBeznal(String beznal) {
        this.beznal = beznal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
