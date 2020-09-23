package realisation.One_To_Many;

import javax.persistence.*;
import java.util.*;

@Entity
public class Master {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nameMaster;
    private String surnameMaster;

    @OneToMany (mappedBy = "master", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Slave> listSlave;

    public Master() {
    }

    public Master(String nameMaster, String surnameMaster) {
        this.nameMaster = nameMaster;
        this.surnameMaster = surnameMaster;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameMaster() {
        return nameMaster;
    }

    public void setNameMaster(String nameMaster) {
        this.nameMaster = nameMaster;
    }

    public String getSurnameMaster() {
        return surnameMaster;
    }

    public void setSurnameMaster(String surnameMaster) {
        this.surnameMaster = surnameMaster;
    }

    public List<Slave> getListSlave() {
        return listSlave;
    }

    public void setListSlave(List<Slave> listSlave) {
        this.listSlave = listSlave;
    }

    @Override
    public String toString() {
        return "Master{" +
                "id=" + id +
                ", nameMaster='" + nameMaster + '\'' +
                ", surnameMaster='" + surnameMaster + '\'' +
                ", listSlave=" + listSlave +
                '}';
    }
}
