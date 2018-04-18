package by.minskkniga.minskkniga.api.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Courier_filter_1 {

    @SerializedName("izdatel")
    @Expose
    private ArrayList<String> izdatel = null;
    @SerializedName("class")
    @Expose
    private ArrayList<String> _class = null;

    public ArrayList<String> getIzdatel() {
        return izdatel;
    }

    public void setIzdatel(ArrayList<String> izdatel) {
        this.izdatel = izdatel;
    }

    public ArrayList<String> getClass_() {
        return _class;
    }

    public void setClass_(ArrayList<String> _class) {
        this._class = _class;
    }

}
