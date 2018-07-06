package by.minskkniga.minskkniga.api.Class.providers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetZakK {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("sokr_name")
    @Expose
    private String sokr_name;

    @SerializedName("art")
    @Expose
    private String art;

    @SerializedName("izd")
    @Expose
    private String izd;

    @SerializedName("clas")
    @Expose
    private String clas;

    @SerializedName("zak_no")
    @Expose
    private String zak_no;

    @SerializedName("count_client")
    @Expose
    private String count_client;

    @SerializedName("list")
    @Expose
    private List<GetZakKObjectList> list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSokr_name() {
        return sokr_name;
    }

    public void setSokr_name(String sokr_name) {
        this.sokr_name = sokr_name;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

    public String getIzd() {
        return izd;
    }

    public void setIzd(String izd) {
        this.izd = izd;
    }

    public String getClas() {
        return clas;
    }

    public void setClas(String clas) {
        this.clas = clas;
    }

    public String getZak_no() {
        return zak_no;
    }

    public void setZak_no(String zak_no) {
        this.zak_no = zak_no;
    }

    public String getCount_client() {
        return count_client;
    }

    public void setCount_client(String count_client) {
        this.count_client = count_client;
    }

    public List<GetZakKObjectList> getList() {
        return list;
    }

    public void setList(List<GetZakKObjectList> list) {
        this.list = list;
    }
}
