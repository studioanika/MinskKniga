package by.minskkniga.minskkniga.api;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Class_Nomenklatura_filter {

    @SerializedName("autor")
    @Expose
    private ArrayList<String> autor = null;
    @SerializedName("izdatel")
    @Expose
    private ArrayList<String> izdatel = null;
    @SerializedName("obrazec")
    @Expose
    private ArrayList<String> obrazec = null;
    @SerializedName("class")
    @Expose
    private ArrayList<String> _class = null;

    public ArrayList<String> getAutor() {
        return autor;
    }

    public void setAutor(ArrayList<String> autor) {
        this.autor = autor;
    }

    public ArrayList<String> getIzdatel() {
        return izdatel;
    }

    public void setIzdatel(ArrayList<String> izdatel) {
        this.izdatel = izdatel;
    }

    public ArrayList<String> getObrazec() {
        return obrazec;
    }

    public void setObrazec(ArrayList<String> obrazec) {
        this.obrazec = obrazec;
    }

    public ArrayList<String> getClass_() {
        return _class;
    }

    public void setClass_(ArrayList<String> _class) {
        this._class = _class;
    }

}
