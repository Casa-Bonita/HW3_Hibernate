package realisation.Many_To_Many;

import javax.persistence.*;
import java.util.*;

@Entity
public class Auto {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    private String automaker;
    private String model;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            joinColumns = @JoinColumn (name = "dealer_id"),
            inverseJoinColumns = @JoinColumn (name = "auto_id")
    )

    List<Dealer> listDealer = new ArrayList<>();

    public Auto() {
    }

    public Auto(String automaker, String model) {
        this.automaker = automaker;
        this.model = model;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAutomaker() {
        return automaker;
    }

    public void setAutomaker(String automaker) {
        this.automaker = automaker;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Dealer> getListDealer() {
        return listDealer;
    }

    public void setListDealer(List<Dealer> listDealer) {
        this.listDealer = listDealer;
    }

    @Override
    public String toString() {
        return "Auto{" +
                "id=" + id +
                ", automaker='" + automaker + '\'' +
                ", model='" + model + '\'' +
                ", listDealer=" + listDealer +
                '}';
    }
}
