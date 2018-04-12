package by.minskkniga.minskkniga.api.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Nomenklatura {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("artikul")
    @Expose
    private String artikul;
    @SerializedName("izdatel")
    @Expose
    private String izdatel;
    @SerializedName("predmet")
    @Expose
    private String predmet;
    @SerializedName("class")
    @Expose
    private String _class;
    @SerializedName("autor")
    @Expose
    private String autor;
    @SerializedName("sokr_name")
    @Expose
    private String sokrName;
    @SerializedName("barcode")
    @Expose
    private String barcode;
    @SerializedName("ves")
    @Expose
    private String ves;
    @SerializedName("komplect")
    @Expose
    private String komplect;
    @SerializedName("zakup_cena")
    @Expose
    private String zakupCena;
    @SerializedName("prod_cena")
    @Expose
    private String prodCena;
    @SerializedName("obrazec")
    @Expose
    private String obrazec;
    @SerializedName("opisanie")
    @Expose
    private String opisanie;
    @SerializedName("izobr")
    @Expose
    private String izobr;

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

    public String getIzdatel() {
        return izdatel;
    }

    public void setIzdatel(String izdatel) {
        this.izdatel = izdatel;
    }

    public String getPredmet() {
        return predmet;
    }

    public void setPredmet(String predmet) {
        this.predmet = predmet;
    }

    public String getClass_() {
        return _class;
    }

    public void setClass_(String _class) {
        this._class = _class;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getSokrName() {
        return sokrName;
    }

    public void setSokrName(String sokrName) {
        this.sokrName = sokrName;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getVes() {
        return ves;
    }

    public void setVes(String ves) {
        this.ves = ves;
    }

    public String getKomplect() {
        return komplect;
    }

    public void setKomplect(String komplect) {
        this.komplect = komplect;
    }

    public String getZakupCena() {
        return zakupCena;
    }

    public void setZakupCena(String zakupCena) {
        this.zakupCena = zakupCena;
    }

    public String getProdCena() {
        return prodCena;
    }

    public void setProdCena(String prodCena) {
        this.prodCena = prodCena;
    }

    public String getObrazec() {
        return obrazec;
    }

    public void setObrazec(String obrazec) {
        this.obrazec = obrazec;
    }

    public String getOpisanie() {
        return opisanie;
    }

    public void setOpisanie(String opisanie) {
        this.opisanie = opisanie;
    }

    public String getIzobr() {
        return izobr;
    }

    public void setIzobr(String izobr) {
        this.izobr = izobr;
    }
}
