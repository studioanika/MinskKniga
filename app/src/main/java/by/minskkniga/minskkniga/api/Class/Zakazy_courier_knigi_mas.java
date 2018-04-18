package by.minskkniga.minskkniga.api.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Zakazy_courier_knigi_mas {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("izdanie")
    @Expose
    private String izdanie;
    @SerializedName("class")
    @Expose
    private String _class;
    @SerializedName("otgruz")
    @Expose
    private String otgruz;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIzdanie() {
        return izdanie;
    }

    public void setIzdanie(String izdanie) {
        this.izdanie = izdanie;
    }

    public String getClass_() {
        return _class;
    }

    public void setClass_(String _class) {
        this._class = _class;
    }

    public String getOtgruz() {
        return otgruz;
    }

    public void setOtgruz(String otgruz) {
        this.otgruz = otgruz;
    }

}
