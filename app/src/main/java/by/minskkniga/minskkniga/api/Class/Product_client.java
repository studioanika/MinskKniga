package by.minskkniga.minskkniga.api.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product_client {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("artikul")
    @Expose
    private String artikul;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("clas")
    @Expose
    private String clas;
    @SerializedName("obrazec")
    @Expose
    private String obrazec;
    @SerializedName("sokr_name")
    @Expose
    private String sokrName;
    @SerializedName("izdatel")
    @Expose
    private String izdatel;
    @SerializedName("autor")
    @Expose
    private String autor;
    @SerializedName("barcode")
    @Expose
    private String barcode;
    @SerializedName("prod_cena")
    @Expose
    private String prodCena;
    @SerializedName("zakup_cena")
    @Expose
    private String zakupCena;
    @SerializedName("standart")
    @Expose
    private String standart;
    @SerializedName("ves")
    @Expose
    private String ves;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("cena")
    @Expose
    private String cena;
    @SerializedName("podarki")
    @Expose
    private String podarki;
    @SerializedName("const_podar")
    @Expose
    private String constPodar;
    @SerializedName("dostupno")
    @Expose
    private String dostupno;
    @SerializedName("ostatok")
    @Expose
    private String ostatok;
    @SerializedName("obrazec_col")
    @Expose
    private String obrazecCol;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArtikul() {
        return artikul;
    }

    public void setArtikul(String artikul) {
        this.artikul = artikul;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClas() {
        return clas;
    }

    public void setClas(String clas) {
        this.clas = clas;
    }

    public String getObrazec() {
        return obrazec;
    }

    public void setObrazec(String obrazec) {
        this.obrazec = obrazec;
    }

    public String getSokrName() {
        return sokrName;
    }

    public void setSokrName(String sokrName) {
        this.sokrName = sokrName;
    }

    public String getIzdatel() {
        return izdatel;
    }

    public void setIzdatel(String izdatel) {
        this.izdatel = izdatel;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getProdCena() {
        return prodCena;
    }

    public void setProdCena(String prodCena) {
        this.prodCena = prodCena;
    }

    public String getZakupCena() {
        return zakupCena;
    }

    public void setZakupCena(String zakupCena) {
        this.zakupCena = zakupCena;
    }

    public String getStandart() {
        return standart;
    }

    public void setStandart(String standart) {
        this.standart = standart;
    }

    public String getVes() {
        return ves;
    }

    public void setVes(String ves) {
        this.ves = ves;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCena() {
        return cena;
    }

    public void setCena(String cena) {
        this.cena = cena;
    }

    public String getPodarki() {
        return podarki;
    }

    public void setPodarki(String podarki) {
        this.podarki = podarki;
    }

    public String getConstPodar() {
        return constPodar;
    }

    public void setConstPodar(String constPodar) {
        this.constPodar = constPodar;
    }

    public String getDostupno() {
        return dostupno;
    }

    public void setDostupno(String dostupno) {
        this.dostupno = dostupno;
    }

    public String getOstatok() {
        return ostatok;
    }

    public void setOstatok(String ostatok) {
        this.ostatok = ostatok;
    }

    public String getObrazecCol() {
        return obrazecCol;
    }

    public void setObrazecCol(String obrazecCol) {
        this.obrazecCol = obrazecCol;
    }

}
