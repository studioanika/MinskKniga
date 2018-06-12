package by.minskkniga.minskkniga.api.Class.cassa;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SchetaResponse {

    @SerializedName("scheta_itog")
    @Expose
    private List<SchetaItog> schetaItog = null;
    @SerializedName("scheta_no_itog")
    @Expose
    private List<SchetaNoItog> schetaNoItog = null;
    @SerializedName("general_itog")
    @Expose
    private List<GeneralItog> generalItog = null;

    public List<SchetaItog> getSchetaItog() {
        return schetaItog;
    }

    public void setSchetaItog(List<SchetaItog> schetaItog) {
        this.schetaItog = schetaItog;
    }

    public List<SchetaNoItog> getSchetaNoItog() {
        return schetaNoItog;
    }

    public void setSchetaNoItog(List<SchetaNoItog> schetaNoItog) {
        this.schetaNoItog = schetaNoItog;
    }

    public List<GeneralItog> getGeneralItog() {
        return generalItog;
    }

    public void setGeneralItog(List<GeneralItog> generalItog) {
        this.generalItog = generalItog;
    }
}