package by.minskkniga.minskkniga.api.Class;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Products_filter {

    @SerializedName("autor")
    @Expose
    private ArrayList<String> autor;
    @SerializedName("izdatel")
    @Expose
    private ArrayList<String> izdatel;
    @SerializedName("obrazec")
    @Expose
    private ArrayList<String> obrazec;
    @SerializedName("clas")
    @Expose
    private ArrayList<String> clas;

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

    public ArrayList<String> getClas() {
        return clas;
    }

    public void setClas(ArrayList<String> clas) {
        this.clas = clas;
    }

}
