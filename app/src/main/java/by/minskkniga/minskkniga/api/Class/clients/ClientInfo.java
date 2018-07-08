package by.minskkniga.minskkniga.api.Class.clients;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClientInfo {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("type_dolg")
    @Expose
    private String type_dolg;

    @SerializedName("sokr_name")
    @Expose
    private String sokrName;
    @SerializedName("zametka")
    @Expose
    private String zametka;
    @SerializedName("print")
    @Expose
    private String print;
    @SerializedName("type_c")
    @Expose
    private String typeC;
    @SerializedName("nacenka")
    @Expose
    private String nacenka;
    @SerializedName("podarki")
    @Expose
    private String podarki;
    @SerializedName("skidka")
    @Expose
    private String skidka;
    @SerializedName("dolg")
    @Expose
    private String dolg;
    @SerializedName("napravl")
    @Expose
    private String napravl;
    @SerializedName("gorod_id")
    @Expose
    private String gorodId;
    @SerializedName("gorod")
    @Expose
    private String gorod;
    @SerializedName("school")
    @Expose
    private String school;
    @SerializedName("smena")
    @Expose
    private String smena;
    @SerializedName("contacts")
    @Expose
    private List<Contact> contacts = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSokrName() {
        return sokrName;
    }

    public void setSokrName(String sokrName) {
        this.sokrName = sokrName;
    }

    public String getZametka() {
        return zametka;
    }

    public void setZametka(String zametka) {
        this.zametka = zametka;
    }

    public String getPrint() {
        return print;
    }

    public void setPrint(String print) {
        this.print = print;
    }

    public String getTypeC() {
        return typeC;
    }

    public void setTypeC(String typeC) {
        this.typeC = typeC;
    }

    public String getNacenka() {
        return nacenka;
    }

    public void setNacenka(String nacenka) {
        this.nacenka = nacenka;
    }

    public String getPodarki() {
        return podarki;
    }

    public void setPodarki(String podarki) {
        this.podarki = podarki;
    }

    public String getSkidka() {
        return skidka;
    }

    public void setSkidka(String skidka) {
        this.skidka = skidka;
    }

    public String getDolg() {
        return dolg;
    }

    public void setDolg(String dolg) {
        this.dolg = dolg;
    }

    public String getNapravl() {
        return napravl;
    }

    public void setNapravl(String napravl) {
        this.napravl = napravl;
    }

    public String getGorodId() {
        return gorodId;
    }

    public void setGorodId(String gorodId) {
        this.gorodId = gorodId;
    }

    public String getGorod() {
        return gorod;
    }

    public void setGorod(String gorod) {
        this.gorod = gorod;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getSmena() {
        return smena;
    }

    public void setSmena(String smena) {
        this.smena = smena;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public String getType_dolg() {
        return type_dolg;
    }

    public void setType_dolg(String type_dolg) {
        this.type_dolg = type_dolg;
    }
}
