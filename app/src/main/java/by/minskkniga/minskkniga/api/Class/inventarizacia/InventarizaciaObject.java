package by.minskkniga.minskkniga.api.Class.inventarizacia;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InventarizaciaObject {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("kniga")
    @Expose
    private Kniga kniga;
    @SerializedName("kont")
    @Expose
    private List<Kont> kont = null;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Kniga getKniga() {
        return kniga;
    }

    public void setKniga(Kniga kniga) {
        this.kniga = kniga;
    }

    public List<Kont> getKont() {
        return kont;
    }

    public void setKont(List<Kont> kont) {
        this.kont = kont;
    }

}
