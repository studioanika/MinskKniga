package by.minskkniga.minskkniga.api.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Organizer_info {

    @SerializedName("clients")
    @Expose
    private ArrayList<Organizer_info_mas> clients = null;
    @SerializedName("couriers")
    @Expose
    private ArrayList<Organizer_info_mas> couriers = null;

    public ArrayList<Organizer_info_mas> getClients() {
        return clients;
    }

    public void setClients(ArrayList<Organizer_info_mas> clients) {
        this.clients = clients;
    }

    public ArrayList<Organizer_info_mas> getCouriers() {
        return couriers;
    }

    public void setCouriers(ArrayList<Organizer_info_mas> couriers) {
        this.couriers = couriers;
    }
}
