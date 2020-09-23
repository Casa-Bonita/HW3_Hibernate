package realisation;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    public static SessionFactory createSessionFactory(){

        Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(realisation.One_To_One.Passport.class);
        configuration.addAnnotatedClass(realisation.One_To_One.Person.class);
        configuration.addAnnotatedClass(realisation.One_To_Many.Master.class);
        configuration.addAnnotatedClass(realisation.One_To_Many.Slave.class);
        configuration.addAnnotatedClass(realisation.Many_To_Many.Auto.class);
        configuration.addAnnotatedClass(realisation.Many_To_Many.Dealer.class);
        return configuration.buildSessionFactory();
        /*
        <mapping class="realisation.One_To_One.Passport"/>
        <mapping class="realisation.One_To_One.Person"/>
        <mapping class="realisation.One_To_Many.Master"/>
        <mapping class="realisation.One_To_Many.Slave"/>
        <mapping class="realisation.Many_To_Many.Auto"/>
        <mapping class="realisation.Many_To_Many.Coder"/>
         */
    }
}
