package by.minskkniga.minskkniga.api.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Courier_filter_1 {

    @SerializedName("izdatel")
    @Expose
    private ArrayList<String> izdatel = null;
    @SerializedName("clas")
    @Expose
    private ArrayList<String> clas = null;

    public ArrayList<String> getIzdatel() {
        return izdatel;
    }

    public void setIzdatel(ArrayList<String> izdatel) {
        this.izdatel = izdatel;
    }

    public ArrayList<String> getClas() {
        return clas;
    }

    public void setClas(ArrayList<String> clas) {
        this.clas = clas;
    }

}
