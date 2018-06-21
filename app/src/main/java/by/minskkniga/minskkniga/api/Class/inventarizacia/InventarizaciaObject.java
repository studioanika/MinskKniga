package by.minskkniga.minskkniga.api.Class.inventarizacia;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InventarizaciaObject {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("contragent")
    @Expose
    private String contragent;

    @SerializedName("colvo")
    @Expose
    private String colvo;

    @SerializedName("osnovanie")
    @Expose
    private String osnavanie;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContragent() {
        return contragent;
    }

    public void setContragent(String contragent) {
        this.contragent = contragent;
    }

    public String getColvo() {
        return colvo;
    }

    public void setColvo(String colvo) {
        this.colvo = colvo;
    }

    public String getOsnavanie() {
        return osnavanie;
    }

    public void setOsnavanie(String osnavanie) {
        this.osnavanie = osnavanie;
    }
}
