package by.minskkniga.minskkniga.api.Class.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseProvScheta {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("providers")
    @Expose
    private String providers;
    @SerializedName("scheta")
    @Expose
    private List<Schetum> scheta = null;
    @SerializedName("category")
    @Expose
    private List<Category> category = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProviders() {
        return providers;
    }

    public void setProviders(String providers) {
        this.providers = providers;
    }

    public List<Schetum> getScheta() {
        return scheta;
    }

    public void setScheta(List<Schetum> scheta) {
        this.scheta = scheta;
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
