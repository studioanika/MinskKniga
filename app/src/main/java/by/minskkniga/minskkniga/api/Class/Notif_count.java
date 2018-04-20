package by.minskkniga.minskkniga.api.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notif_count {

    @SerializedName("col")
    @Expose
    private String col;

    public String getCol() {
        return col;
    }

    public void setCol(String col) {
        this.col = col;
    }

}
