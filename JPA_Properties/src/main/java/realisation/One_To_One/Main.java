package realisation.One_To_One;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

//    Реализовать отношения сущностей:
//    1. «Один к одному»: паспорт и человек;
//    2. «Один ко многим»: хозяин и питомец;
//    3. «Многие ко многим»: автомобиль и дилерский центр.
//    Для всех отношений реализовать crud-методы (create-read-update-delete) через консольное меню

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPA_Properties_Unit");
        EntityManager em = entityManagerFactory.createEntityManager();

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

                em.getTransaction().begin();

                em.persist(person1);
                em.persist(person2);
                em.persist(passport1);
                em.persist(passport2);

                em.getTransaction().commit();

            }
            else if(input.equals("2")){
                System.out.println("2. Read.");

                List<Person> listPerson = em.createQuery("from Person").getResultList();
                listPerson.stream()
                        .forEach(x -> System.out.printf("%-20s %-20s %-20s %-20s %n", x.getId(), x.getName(), x.getSurname(), x.getPassport()));

            }
            else if(input.equals("3")){
                System.out.println("3. Update.");

                System.out.println("Люди, имеющиеся в базе:");
                List<Person> listPerson = em.createQuery("from Person").getResultList();
                listPerson.stream()
                        .forEach(x -> System.out.printf("%-20s %-20s %-20s %-20s %n", x.getId(), x.getName(), x.getSurname(), x.getPassport()));

                System.out.println("Введите фамилию, которая будет изменена:");
                String oldSurname = scn.nextLine();

                System.out.println("Введите новую фамилию:");
                String newSurname = scn.nextLine();

                Person changedPersonSurname = (Person) em.find(Person.class, oldSurname);
                changedPersonSurname.setSurname(newSurname);

                em.getTransaction().begin();

                try{
                    em.merge(changedPersonSurname);
                    em.getTransaction().commit();
                }catch (Exception ex){
                    System.out.println(ex);
                    ex.printStackTrace();
                    em.getTransaction().rollback();
                }

                System.out.println("Список после изменения фамилии человека:");
                listPerson = em.createQuery("from Person").getResultList();
                listPerson.stream()
                        .forEach(x -> System.out.printf("%-20s %-20s %-20s %-20s %n", x.getId(), x.getName(), x.getSurname(), x.getPassport()));

            }
            else if(input.equals("4")){
                System.out.println("4. Delete.");
                System.out.println("Люди, имеющиеся в базе:");
                List<Person> listPerson = em.createQuery("from Person").getResultList();
                listPerson.stream()
                        .forEach(x -> System.out.printf("%-20s %-20s %-20s %-20s %n", x.getId(), x.getName(), x.getSurname(), x.getPassport()));

                System.out.println("Введите фамилию человека, который будет удален из базы:");
                String deletedSurname = scn.nextLine();

                Person person = (Person) em.find(Person.class, deletedSurname);

                em.getTransaction().begin();

                try{
                    em.remove(person);
                    em.getTransaction().commit();
                }catch (Exception ex){
                    System.out.println(ex);
                    ex.printStackTrace();
                    em.getTransaction().rollback();
                }

                System.out.println("Список после удаления человека:");
                listPerson = em.createQuery("from Person").getResultList();
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
