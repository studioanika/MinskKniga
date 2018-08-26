package by.minskkniga.minskkniga.api.Class.couriers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurZakaz {

    @SerializedName("zakaz_id")
    @Expose
    private String zakaz_id;

    @SerializedName("kur_zak")
    @Expose
    private String kur_zak;

    @SerializedName("client_id")
    @Expose
    private String client_id;

    @SerializedName("client_name")
    @Expose
    private String client_name;

    @SerializedName("zakazano")
    @Expose
    private String zakazano;

    public String getZakaz_id() {
        return zakaz_id;
    }

    public void setZakaz_id(String zakaz_id) {
        this.zakaz_id = zakaz_id;
    }

    public String getKur_zak() {
        return kur_zak;
    }

    public void setKur_zak(String kur_zak) {
        this.kur_zak = kur_zak;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getZakazano() {
        return zakazano;
    }

    public void setZakazano(String zakazano) {
        this.zakazano = zakazano;
    }
}
