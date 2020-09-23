package realisation.One_To_One;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import realisation.HibernateUtil;
import java.util.*;

public class Main {
    public static void main(String[] args) {

//    Реализовать отношения сущностей:
//    1. «Один к одному»: паспорт и человек;
//    2. «Один ко многим»: хозяин и питомец;
//    3. «Многие ко многим»: автомобиль и дилерский центр.
//    Для всех отношений реализовать crud-методы (create-read-update-delete) через консольное меню


        SessionFactory sessionFactory = HibernateUtil.createSessionFactory();
        Session session = sessionFactory.openSession();

        Scanner scn = new Scanner(System.in);

        String input = "";
        while(!input.equals("5")){
            System.out.println("1. Create.");
            System.out.println("2. Read.");
            System.out.println("3. Update.");
            System.out.println("4. Delete.");
            System.out.println("5. Exit.");

            input = scn.nextLine();

            if(input.equals("1")){
                System.out.println("1. Create.");

                Person person1 = new Person ("Oleg", "Ivanov");
                Person person2 = new Person ("Sergey", "Petrov");

                Passport passport1 = new Passport("XX11", 123456);
                Passport passport2 = new Passport("YY22", 987654);

                person1.setPassport(passport1);
                person2.setPassport(passport2);

                passport1.setPerson(person1);
                passport2.setPerson(person2);

                session.beginTransaction();
                session.save(person1);
                session.save(person2);
                session.save(passport1);
                session.save(passport2);

                session.getTransaction().commit();

            }
            else if(input.equals("2")){
                System.out.println("2. Read.");

                List<Person> listPerson = session.createQuery("from Person").getResultList();
                listPerson.stream()
                        .forEach(x -> System.out.printf("%-20s %-20s %-20s %-20s %n", x.getId(), x.getName(), x.getSurname(), x.getPassport()));

            }
            else if(input.equals("3")){
                System.out.println("3. Update.");

                System.out.println("Люди, имеющиеся в базе:");
                List<Person> listPerson = session.createQuery("from Person").getResultList();
                listPerson.stream()
                        .forEach(x -> System.out.printf("%-20s %-20s %-20s %-20s %n", x.getId(), x.getName(), x.getSurname(), x.getPassport()));

                System.out.println("Введите фамилию, которая будет изменена:");
                String oldSurname = scn.nextLine();

                System.out.println("Введите новую фамилию:");
                String newSurname = scn.nextLine();

                Query<Person> query = session.createQuery("from Person where surname := surnameParameter");
                query.setParameter("surnameParameter", oldSurname);

                Person person = query.getSingleResult();
                person.setSurname(newSurname);
                session.beginTransaction();

                try{
                    session.remove(person);
                    session.getTransaction().commit();
                }catch(Exception ex){
                    System.out.println(ex);
                    session.getTransaction().rollback();
                }

                System.out.println("Список после изменения фамилии человека:");
                listPerson = session.createQuery("from Person").getResultList();
                listPerson.stream()
                        .forEach(x -> System.out.printf("%-20s %-20s %-20s %-20s %n", x.getId(), x.getName(), x.getSurname(), x.getPassport()));

            }
            else if(input.equals("4")){
                System.out.println("4. Delete.");
                System.out.println("Люди, имеющиеся в базу:");
                List<Person> listPerson = session.createQuery("from Person").getResultList();
                listPerson.stream()
                        .forEach(x -> System.out.printf("%-20s %-20s %-20s %-20s %n", x.getId(), x.getName(), x.getSurname(), x.getPassport()));

                System.out.println("Введите фамилию человека, который будет удален из базы:");
                String deletedSurname = scn.nextLine();

                Query<Person> query = session.createQuery("from Person where surname := surnameParameter");
                query.setParameter("surnameParameter", deletedSurname);

                Person person = query.getSingleResult();
                session.beginTransaction();

                try{
                    session.remove(person);
                    session.getTransaction().commit();
                }catch(Exception ex){
                    System.out.println(ex);
                    session.getTransaction().rollback();
                }

                System.out.println("Список после удаления человека:");
                listPerson = session.createQuery("from Person").getResultList();
                listPerson.stream()
                        .forEach(x -> System.out.printf("%-20s %-20s %-20s %-20s %n", x.getId(), x.getName(), x.getSurname(), x.getPassport()));

            }
            else if(input.equals("5")){
                System.out.println("5. Exit.");

            }
            else{
                System.out.println("Invalid input.");

            }
        }

    }
}

