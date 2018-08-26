package by.minskkniga.minskkniga.api.Class.providers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoneyProvResponse {

    @SerializedName("info")
    @Expose
    private ProvMoneyInfo info;


    @SerializedName("list")
    @Expose
    private List<ItemListProvMoney> list;

    public ProvMoneyInfo getInfo() {
        return info;
    }

    public void setInfo(ProvMoneyInfo info) {
        this.info = info;
    }

    public List<ItemListProvMoney> getList() {
        return list;
    }

    public void setList(List<ItemListProvMoney> list) {
        this.list = list;
    }
}
