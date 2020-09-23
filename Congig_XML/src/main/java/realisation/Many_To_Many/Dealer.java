package realisation.Many_To_Many;

import javax.persistence.*;
import java.util.*;

@Entity
public class Dealer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String location;

    @ManyToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Auto> listAuto = new ArrayList<>();

    public Dealer() {
    }

    public Dealer (String name, String location, Auto ... autos){
        this.name = name;
        this.location = location;
        for (int i = 0; i < autos.length; i++) {
            listAuto.add(autos[i]);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Auto> getListAuto() {
        return listAuto;
    }

    public void setListAuto(List<Auto> listAuto) {
        this.listAuto = listAuto;
    }

    @Override
    public String toString() {
        return "Dealer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", listAuto=" + listAuto +
                '}';
    }
}