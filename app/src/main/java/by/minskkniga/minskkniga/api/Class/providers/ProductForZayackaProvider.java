package by.minskkniga.minskkniga.api.Class.providers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import by.minskkniga.minskkniga.api.Class.Product;
import by.minskkniga.minskkniga.api.Class.Products;

public class ProductForZayackaProvider{

    Products products;

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    @SerializedName("zayavka")
    @Expose
    private String zayavka;

    public String getZayavka() {
        return zayavka;
    }

    public void setZayavka(String zayavka) {
        this.zayavka = zayavka;
    }
}
