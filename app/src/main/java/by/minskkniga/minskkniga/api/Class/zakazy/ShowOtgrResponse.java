package by.minskkniga.minskkniga.api.Class.zakazy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import by.minskkniga.minskkniga.adapter.Nomenklatura.ItemShowOtgr;

public class ShowOtgrResponse {

    @SerializedName("info")
    @Expose
    private InfoShowOtgr info;

    @SerializedName("list")
    @Expose
    private ArrayList<ItemShowOtgr> list;

    public InfoShowOtgr getInfo() {
        return info;
    }

    public void setInfo(InfoShowOtgr info) {
        this.info = info;
    }

    public ArrayList<ItemShowOtgr> getList() {
        return list;
    }

    public void setList(ArrayList<ItemShowOtgr> list) {
        this.list = list;
    }
}
