package by.minskkniga.minskkniga.api.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Zakaz_filter {

    @SerializedName("status")
    @Expose
    private ArrayList<String> status;
    @SerializedName("gorod")
    @Expose
    private ArrayList<String> gorod;
    @SerializedName("school")
    @Expose
    private ArrayList<String> school;

    public ArrayList<String> getStatus() {
        return status;
    }

    public void setStatus(ArrayList<String> status) {
        this.status = status;
    }

    public ArrayList<String> getGorod() {
        return gorod;
    }

    public void setGorod(ArrayList<String> gorod) {
        this.gorod = gorod;
    }

    public ArrayList<String> getSchool() {
        return school;
    }

    public void setSchool(ArrayList<String> school) {
        this.school = school;
    }
}
