package by.minskkniga.minskkniga.api.Class.cassa;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InfoSchetaResponse {

    @SerializedName("general_itog")
    @Expose
    private InfoSchetaItog general_itog;

    @SerializedName("schet_info")
    @Expose
    private List<DescInfoSchet> schet_info;

    public InfoSchetaItog getGeneral_itog() {
        return general_itog;
    }

    public void setGeneral_itog(InfoSchetaItog general_itog) {
        this.general_itog = general_itog;
    }

    public List<DescInfoSchet> getSchet_info() {
        return schet_info;
    }

    public void setSchet_info(List<DescInfoSchet> schet_info) {
        this.schet_info = schet_info;
    }
}
