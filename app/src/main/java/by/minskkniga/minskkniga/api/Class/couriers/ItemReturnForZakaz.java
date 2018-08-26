package by.minskkniga.minskkniga.api.Class.couriers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemReturnForZakaz {

    @SerializedName("id_poz")
    @Expose
    private String id_poz;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("artikul")
    @Expose
    private String artikul;

    @SerializedName("u_cur")
    @Expose
    private String u_cur;

    public String getId_poz() {
        return id_poz;
    }

    public void setId_poz(String id_poz) {
        this.id_poz = id_poz;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtikul() {
        return artikul;
    }

    public void setArtikul(String artikul) {
        this.artikul = artikul;
    }

    public String getU_cur() {
        return u_cur;
    }

    public void setU_cur(String u_cur) {
        this.u_cur = u_cur;
    }
}
