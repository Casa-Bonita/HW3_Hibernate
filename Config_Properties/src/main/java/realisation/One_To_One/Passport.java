package realisation.One_To_One;

import javax.persistence.*;

@Entity
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String series;
    private int number;

    @OneToOne (mappedBy = "passport")
    private Person person;

    public Passport() {
    }

    public Passport(String series, int number) {
        this.series = series;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "Passport{" +
                "id=" + id +
                ", series='" + series + '\'' +
                ", number=" + number +
                '}';
    }
}
