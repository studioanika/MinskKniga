package by.minskkniga.minskkniga.api.Class.providers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 14.4.18.
 */

public class Book {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("classs")
    @Expose
    private String classs;
    @SerializedName("izdatel")
    @Expose
    private String izdatel;
    @SerializedName("sokr_name")
    @Expose
    private String sokrName;
    @SerializedName("zakaz")
    @Expose
    private String zakaz;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClasss() {
        return classs;
    }

    public void setClasss(String classs) {
        this.classs = classs;
    }

    public String getIzdatel() {
        return izdatel;
    }

    public void setIzdatel(String izdatel) {
        this.izdatel = izdatel;
    }

    public String getSokrName() {
        return sokrName;
    }

    public void setSokrName(String sokrName) {
        this.sokrName = sokrName;
    }

    public String getZakaz() {
        return zakaz;
    }

    public void setZakaz(String zakaz) {
        this.zakaz = zakaz;
    }



}
