package by.minskkniga.minskkniga.api.Class.providerzayavka;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by root on 14.4.18.
 */

public class ProviderZayavkaI {

    @SerializedName("whatZayavka")
    @Expose
    private List<WhatZayavka> whatZayavka = null;

    public List<WhatZayavka> getWhatZayavka() {
        return whatZayavka;
    }

    public void setWhatZayavka(List<WhatZayavka> whatZayavka) {
        this.whatZayavka = whatZayavka;
    }
}
