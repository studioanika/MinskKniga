package by.minskkniga.minskkniga.api.Class;

public class Spinner_filter {

    private String name;
    private String checked;

    public Spinner_filter(String name, String checked) {
        this.name = name;
        this.checked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }
}
