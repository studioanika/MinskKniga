package by.minskkniga.minskkniga.api.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class Courier_filter_2 {

    @SerializedName("napravl")
    @Expose
    private ArrayList<String> napravl = null;
    @SerializedName("sity")
    @Expose
    private ArrayList<String> sity = null;
    @SerializedName("school")
    @Expose
    private ArrayList<String> school = null;
    @SerializedName("smena")
    @Expose
    private ArrayList<String> smena = null;

    public ArrayList<String> getNapravl() {
        return napravl;
    }

    public void setNapravl(ArrayList<String> napravl) {
        this.napravl = napravl;
    }

    public ArrayList<String> getSity() {
        return sity;
    }

    public void setSity(ArrayList<String> sity) {
        this.sity = sity;
    }

    public ArrayList<String> getSchool() {
        return school;
    }

    public void setSchool(ArrayList<String> school) {
        this.school = school;
    }

    public ArrayList<String> getSmena() {
        return smena;
    }

    public void setSmena(ArrayList<String> smena) {
        this.smena = smena;
    }
}
