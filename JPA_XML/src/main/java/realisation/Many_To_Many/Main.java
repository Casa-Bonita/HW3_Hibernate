package realisation.Many_To_Many;

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


        Scanner scn = new Scanner(System.in);

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPA_XML_Unit");
        EntityManager em = entityManagerFactory.createEntityManager();

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

                Auto auto1 = new Auto("Renault", "Logan");
                Auto auto2 = new Auto("KIA", "Rio");

                List<Dealer> listDealer1 = Arrays.asList(new Dealer("One", "Moscow", auto1), new Dealer("Two", "Samara", auto2));
                List<Dealer> listDealer2 = Arrays.asList(new Dealer("Three", "Rostov", auto1), new Dealer("Four", "Saratov", auto2));

                auto1.getListDealer().addAll(listDealer1);
                auto2.getListDealer().addAll(listDealer2);

                em.getTransaction().begin();

                em.persist(auto1);
                em.persist(auto2);

                for(Dealer dl : listDealer1){
                    em.persist(dl);
                }

                for(Dealer dl : listDealer2){
                    em.persist(dl);
                }

                em.getTransaction().commit();

            }
            else if(input.equals("2")){
                System.out.println("2. Read.");

                List<Auto> listAuto = em.createQuery("from Auto").getResultList();
                System.out.printf("%-20s %-20s %-20s %-20s %n", "Auto_ID", "Automaker", "Model", "Dealer");
                listAuto.stream()
                        .forEach(x -> System.out.printf("%-20s %-20s %-20s %-20s%n", x.getId(), x.getAutomaker(), x.getModel(), x.getListDealer()));

            }
            else if(input.equals("3")){
                System.out.println("3. Update.");

                System.out.println("Автомобили, имеющиеся в базе:");
                List<Auto> listAuto = em.createQuery("from Auto").getResultList();
                System.out.printf("%-20s %-20s %-20s %-20s %n", "Auto_ID", "Automaker", "Model", "Dealer");
                listAuto.stream()
                        .forEach(x -> System.out.printf("%-20s %-20s %-20s %-20s%n", x.getId(), x.getAutomaker(), x.getModel(), x.getListDealer()));

                System.out.println("Введите модель автомобиля, которая будет изменена:");
                String oldModel = scn.nextLine();

                System.out.println("Введите новую модель автомобиля:");
                String newModel = scn.nextLine();

                Auto auto = (Auto) em.find(Auto.class, oldModel);
                auto.setModel(newModel);

                em.getTransaction().begin();

                try{
                    em.merge(auto);
                    em.getTransaction().commit();
                }catch (Exception ex){
                    System.out.println(ex);
                    ex.printStackTrace();
                    em.getTransaction().rollback();
                }

                System.out.println("Список после изменения модели автомобиля:");
                listAuto = em.createQuery("from Auto").getResultList();
                System.out.printf("%-20s %-20s %-20s %-20s %n", "Auto_ID", "Automaker", "Model", "Dealer");
                listAuto.stream()
                        .forEach(x -> System.out.printf("%-20s %-20s %-20s %-20s%n", x.getId(), x.getAutomaker(), x.getModel(), x.getListDealer()));


            }
            else if(input.equals("4")){
                System.out.println("4. Delete.");

                System.out.println("Автомобили, имеющиеся в базе:");
                List<Auto> listAuto = em.createQuery("from Auto").getResultList();
                System.out.printf("%-20s %-20s %-20s %-20s %n", "Auto_ID", "Automaker", "Model", "Dealer");
                listAuto.stream()
                        .forEach(x -> System.out.printf("%-20s %-20s %-20s %-20s%n", x.getId(), x.getAutomaker(), x.getModel(), x.getListDealer()));

                System.out.println("Введите модель автомобиля, которая будет удалена из базы:");
                String deletedModel = scn.nextLine();

                Auto auto = (Auto) em.find(Auto.class, deletedModel);

                em.getTransaction().begin();

                try{
                    em.remove(auto);
                    em.getTransaction().commit();
                }catch (Exception ex){
                    System.out.println(ex);
                    ex.printStackTrace();
                    em.getTransaction().rollback();
                }

                System.out.println("Список после удаления модели автомобиля:");
                listAuto = em.createQuery("from Auto").getResultList();
                System.out.printf("%-20s %-20s %-20s %-20s %n", "Auto_ID", "Automaker", "Model", "Dealer");
                listAuto.stream()
                        .forEach(x -> System.out.printf("%-20s %-20s %-20s %-20s%n", x.getId(), x.getAutomaker(), x.getModel(), x.getListDealer()));

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
