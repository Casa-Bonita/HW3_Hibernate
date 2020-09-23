package realisation.One_To_Many;

import javax.persistence.*;

@Entity
public class Slave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nameSlave;
    private String surnameSlave;
    private int age;

    @ManyToOne()
    @JoinColumn(name = "master_id")
    private Master master;

    public Slave() {
    }

    public Slave(String nameSlave, String surnameSlave, int age, Master master) {
        this.nameSlave = nameSlave;
        this.surnameSlave = surnameSlave;
        this.age = age;
        this.master = master;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameSlave() {
        return nameSlave;
    }

    public void setNameSlave(String nameSlave) {
        this.nameSlave = nameSlave;
    }

    public String getSurnameSlave() {
        return surnameSlave;
    }

    public void setSurnameSlave(String surnameSlave) {
        this.surnameSlave = surnameSlave;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Master getMaster() {
        return master;
    }

    public void setMaster(Master master) {
        this.master = master;
    }

    @Override
    public String toString() {
        return "Slave{" +
                "id=" + id +
                ", nameSlave='" + nameSlave + '\'' +
                ", surnameSlave='" + surnameSlave + '\'' +
                ", age=" + age +
                ", master=" + master +
                '}';
    }
}