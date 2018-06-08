package by.minskkniga.minskkniga.api.Class.providers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProviderZayavkiNews {

    @SerializedName("itog_ves")
    @Expose
    private String itogVes;
    @SerializedName("mas")
    @Expose
    private List<ProviderNews> mas = null;

    public String getItogVes() {
        return itogVes;
    }

    public void setItogVes(String itogVes) {
        this.itogVes = itogVes;
    }

    public List<ProviderNews> getMas() {
        return mas;
    }

    public void setMas(List<ProviderNews> mas) {
        this.mas = mas;
    }

}
