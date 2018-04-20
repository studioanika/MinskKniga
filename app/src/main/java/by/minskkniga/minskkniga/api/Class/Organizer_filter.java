package by.minskkniga.minskkniga.api.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Organizer_filter {

    @SerializedName("autor")
    @Expose
    private ArrayList<String> autor = null;
    @SerializedName("komy")
    @Expose
    private ArrayList<String> komy = null;
    @SerializedName("kontragent")
    @Expose
    private ArrayList<String> kontragent = null;
    @SerializedName("status")
    @Expose
    private ArrayList<String> status = null;

    public ArrayList<String> getAutor() {
        return autor;
    }

    public void setAutor(ArrayList<String> autor) {
        this.autor = autor;
    }

    public ArrayList<String> getKomy() {
        return komy;
    }

    public void setKomy(ArrayList<String> komy) {
        this.komy = komy;
    }

    public ArrayList<String> getKontragent() {
        return kontragent;
    }

    public void setKontragent(ArrayList<String> kontragent) {
        this.kontragent = kontragent;
    }

    public ArrayList<String> getStatus() {
        return status;
    }

    public void setStatus(ArrayList<String> status) {
        this.status = status;
    }

}
