package by.minskkniga.minskkniga.adapter.Zakazy;

public class ObjectToReturn {

    String id;
    String name;
    int u_cur;
    int return_kiniga;

    public ObjectToReturn(String id, String name, int u_cur, int return_kiniga) {
        this.id = id;
        this.name = name;
        this.u_cur = u_cur;
        this.return_kiniga = return_kiniga;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getU_cur() {
        return u_cur;
    }

    public void setU_cur(int u_cur) {
        this.u_cur = u_cur;
    }

    public int getReturn_kiniga() {
        return return_kiniga;
    }

    public void setReturn_kiniga(int return_kiniga) {
        this.return_kiniga = return_kiniga;
    }
}
