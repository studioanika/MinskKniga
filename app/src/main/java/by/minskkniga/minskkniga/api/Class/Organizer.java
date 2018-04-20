package by.minskkniga.minskkniga.api.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Organizer {


    @SerializedName("id")
    @Expose
    private String Id;
    @SerializedName("countragent_id")
    @Expose
    private String countragentId;
    @SerializedName("countragent_name")
    @Expose
    private String countragentName;
    @SerializedName("autor_id")
    @Expose
    private String autorId;
    @SerializedName("autor_name")
    @Expose
    private String autorName;
    @SerializedName("ispolnitel_id")
    @Expose
    private String ispolnitelId;
    @SerializedName("ispolnitel_name")
    @Expose
    private String ispolnitelName;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("text")
    @Expose
    private String text;

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getCountragentId() {
        return countragentId;
    }

    public void setCountragentId(String countragentId) {
        this.countragentId = countragentId;
    }

    public String getCountragentName() {
        return countragentName;
    }

    public void setCountragentName(String countragentName) {
        this.countragentName = countragentName;
    }

    public String getAutorId() {
        return autorId;
    }

    public void setAutorId(String autorId) {
        this.autorId = autorId;
    }

    public String getAutorName() {
        return autorName;
    }

    public void setAutorName(String autorName) {
        this.autorName = autorName;
    }

    public String getIspolnitelId() {
        return ispolnitelId;
    }

    public void setIspolnitelId(String ispolnitelId) {
        this.ispolnitelId = ispolnitelId;
    }

    public String getIspolnitelName() {
        return ispolnitelName;
    }

    public void setIspolnitelName(String ispolnitelName) {
        this.ispolnitelName = ispolnitelName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
