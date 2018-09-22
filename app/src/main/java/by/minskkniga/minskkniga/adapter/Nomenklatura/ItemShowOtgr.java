package by.minskkniga.minskkniga.adapter.Nomenklatura;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemShowOtgr {

    @SerializedName("id_zak")
    @Expose
    private String id_zak;

    @SerializedName("cena")
    @Expose
    private String cena;

    @SerializedName("name_zak")
    @Expose
    private String name_zak;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("otgruzeno")
    @Expose
    private String otgruzeno;

    @SerializedName("vozvrat")
    @Expose
    private Double vozvrat;

    @SerializedName("podarki")
    @Expose
    private Double podarki;

    @SerializedName("summa")
    @Expose
    private Double summa;

    @SerializedName("barcode")
    @Expose
    private String barcode;

    @SerializedName("sokr_name")
    @Expose
    private String sokr_name;

    public String getId_zak() {
        return id_zak;
    }

    public void setId_zak(String id_zak) {
        this.id_zak = id_zak;
    }

    public String getName_zak() {
        return name_zak;
    }

    public void setName_zak(String name_zak) {
        this.name_zak = name_zak;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOtgruzeno() {
        return otgruzeno;
    }

    public void setOtgruzeno(String otgruzeno) {
        this.otgruzeno = otgruzeno;
    }

    public Double getVozvrat() {
        return vozvrat;
    }

    public void setVozvrat(Double vozvrat) {
        this.vozvrat = vozvrat;
    }

    public Double getPodarki() {
        return podarki;
    }

    public void setPodarki(Double podarki) {
        this.podarki = podarki;
    }

    public Double getSumma() {
        return summa;
    }

    public void setSumma(Double summa) {
        this.summa = summa;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getSokr_name() {
        return sokr_name;
    }

    public void setSokr_name(String sokr_name) {
        this.sokr_name = sokr_name;
    }

    public String getCena() {
        return cena;
    }

    public void setCena(String cena) {
        this.cena = cena;
    }
}
