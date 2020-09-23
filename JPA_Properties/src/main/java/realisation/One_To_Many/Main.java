package realisation.One_To_Many;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Arrays;
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
        while (!input.equals("5")) {
            System.out.println("1. Create.");
            System.out.println("2. Read.");
            System.out.println("3. Update.");
            System.out.println("4. Delete.");
            System.out.println("5. Exit.");

            input = scn.nextLine();

            if (input.equals("1")) {
                System.out.println("1. Create.");

                Master master1 = new Master("Pol", "Pol");
                Master master2 = new Master("John", "John");

                List<Slave> listSlave1 = Arrays.asList(new Slave("Tony", "Tony", 35, master1), new Slave("Liza", "Liza", 30, master1));
                List<Slave> listSlave2 = Arrays.asList(new Slave("Jack", "Jack", 25, master2), new Slave("Alla", "Alla", 20, master2));

                master1.setListSlave(listSlave1);
                master2.setListSlave(listSlave2);

                em.getTransaction().begin();

                em.persist(master1);
                em.persist(master2);

                for (Slave sl : listSlave1) {
                    em.persist(sl);
                }

                for (Slave sl : listSlave2) {
                    em.persist(sl);
                }

                em.getTransaction().commit();

            } else if (input.equals("2")) {
                System.out.println("2. Read.");

                List<Master> listMaster = em.createQuery("from Master").getResultList();
                listMaster.stream()
                        .forEach(x -> System.out.printf("%-20s %-20s %-20s %-20s %n", x.getId(), x.getNameMaster(), x.getSurnameMaster(), x.getListSlave()));

            } else if (input.equals("3")) {
                System.out.println("3. Update.");

                System.out.println("Люди, имеющиеся в базе:");
                List<Master> listMaster = em.createQuery("from Master").getResultList();
                listMaster.stream()
                        .forEach(x -> System.out.printf("%-20s %-20s %-20s %-20s %n", x.getId(), x.getNameMaster(), x.getSurnameMaster(), x.getListSlave()));

                System.out.println("Введите фамилию Master's, которая будет изменена:");
                String oldMasterSurname = scn.nextLine();

                System.out.println("Введите новую фамилию Master's:");
                String newMasterSurname = scn.nextLine();

                Master master = (Master) em.find(Master.class, oldMasterSurname);
                master.setSurnameMaster(newMasterSurname);

                em.getTransaction().begin();

                try {
                    em.merge(master);
                    em.getTransaction().commit();
                } catch (Exception ex) {
                    System.out.println(ex);
                    ex.printStackTrace();
                    em.getTransaction().rollback();
                }

                System.out.println("Список после изменения фамилии человека:");
                listMaster = em.createQuery("from Master").getResultList();
                listMaster.stream()
                        .forEach(x -> System.out.printf("%-20s %-20s %-20s %-20s %n", x.getId(), x.getNameMaster(), x.getSurnameMaster(), x.getListSlave()));

            } else if (input.equals("4")) {
                System.out.println("4. Delete.");
                System.out.println("Люди, имеющиеся в базе:");
                List<Master> listMaster = em.createQuery("from Master").getResultList();
                listMaster.stream()
                        .forEach(x -> System.out.printf("%-20s %-20s %-20s %-20s %n", x.getId(), x.getNameMaster(), x.getSurnameMaster(), x.getListSlave()));

                System.out.println("Введите фамилию Master's, который будет удален из базы:");
                String deletedMasterSurname = scn.nextLine();

                em.getTransaction().begin();

                Master master = (Master) em.find(Master.class, deletedMasterSurname);

                em.getTransaction().begin();

                try {
                    em.remove(master);
                    em.getTransaction().commit();
                } catch (Exception ex) {
                    System.out.println(ex);
                    ex.printStackTrace();
                    em.getTransaction().rollback();
                }

                System.out.println("Список после удаления человека:");
                listMaster = em.createQuery("from Master").getResultList();
                listMaster.stream()
                        .forEach(x -> System.out.printf("%-20s %-20s %-20s %-20s %n", x.getId(), x.getNameMaster(), x.getSurnameMaster(), x.getListSlave()));

            } else if (input.equals("5")) {
                System.out.println("5. Exit.");

            } else {
                System.out.println("Invalid input.");

            }
        }
        
    }
}
