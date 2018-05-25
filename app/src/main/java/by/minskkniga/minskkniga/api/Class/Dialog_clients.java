package by.minskkniga.minskkniga.api.Class;

public class Dialog_clients {

    private String name;
    private String podar;

    public Dialog_clients(String name, String podar) {
        this.name = name;
        this.podar = podar;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPodar() {
        return podar;
    }

    public void setPodar(String podar) {
        this.podar = podar;
    }
}
