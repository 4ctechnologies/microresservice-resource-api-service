package be.foreseegroup.micro.resourceservice.resourceapi.model;

/**
 * Created by Kaj on 6/10/15.
 */
public class Unit {
    private String id;

    private String name;

    public Unit() {
    }

    public Unit(String name) {
        this.name = name;
    }

    public Unit(String id, String name) {
        this.id = id;
        this.name = name;
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
}

