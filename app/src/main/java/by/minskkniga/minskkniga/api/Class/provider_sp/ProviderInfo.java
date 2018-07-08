package by.minskkniga.minskkniga.api.Class.provider_sp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import by.minskkniga.minskkniga.api.Class.clients.Contact;

public class ProviderInfo {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("short_name")
    @Expose
    private String shortName;
    @SerializedName("zametka")
    @Expose
    private String zametka;
    @SerializedName("info")
    @Expose
    private String info;
    @SerializedName("price_type")
    @Expose
    private String priceType;
    @SerializedName("nakrytka")
    @Expose
    private String nakrytka;
    @SerializedName("credit_size")
    @Expose
    private String creditSize;
    @SerializedName("napravl")
    @Expose
    private String napravl;
    @SerializedName("city_id")
    @Expose
    private String cityId;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("contacts")
    @Expose
    private List<Contact> contacts = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getZametka() {
        return zametka;
    }

    public void setZametka(String zametka) {
        this.zametka = zametka;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public String getNakrytka() {
        return nakrytka;
    }

    public void setNakrytka(String nakrytka) {
        this.nakrytka = nakrytka;
    }

    public String getCreditSize() {
        return creditSize;
    }

    public void setCreditSize(String creditSize) {
        this.creditSize = creditSize;
    }

    public String getNapravl() {
        return napravl;
    }

    public void setNapravl(String napravl) {
        this.napravl = napravl;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }
}
