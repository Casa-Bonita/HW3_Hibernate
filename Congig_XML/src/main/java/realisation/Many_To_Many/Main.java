package realisation.Many_To_Many;

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

        Scanner scn = new Scanner(System.in);

        SessionFactory sessionFactory = HibernateUtil.createSessionFactory();
        Session session = sessionFactory.openSession();

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

                session.beginTransaction();

                session.save(auto1);
                session.save(auto2);

                for(Dealer dl : listDealer1){
                    session.save(dl);
                }

                for(Dealer dl : listDealer2){
                    session.save(dl);
                }

                session.getTransaction().commit();

            }
            else if(input.equals("2")){
                System.out.println("2. Read.");

                List<Auto> listAuto = session.createQuery("from Auto").getResultList();
                System.out.printf("%-20s %-20s %-20s %-20s %n", "Auto_ID", "Automaker", "Model", "Dealer");
                listAuto.stream()
                        .forEach(x -> System.out.printf("%-20s %-20s %-20s %-20s%n", x.getId(), x.getAutomaker(), x.getModel(), x.getListDealer()));

            }
            else if(input.equals("3")){
                System.out.println("3. Update.");

                System.out.println("Автомобили, имеющиеся в базе:");
                List<Auto> listAuto = session.createQuery("from Auto").getResultList();
                System.out.printf("%-20s %-20s %-20s %-20s %n", "Auto_ID", "Automaker", "Model", "Dealer");
                listAuto.stream()
                        .forEach(x -> System.out.printf("%-20s %-20s %-20s %-20s%n", x.getId(), x.getAutomaker(), x.getModel(), x.getListDealer()));

                System.out.println("Введите модель автомобиля, которая будет изменена:");
                String oldModel = scn.nextLine();

                System.out.println("Введите новую модель автомобиля:");
                String newModel = scn.nextLine();

                Query <Auto> query = session.createQuery("from Auto where model := modelParameter");
                query.setParameter("modelParameter", oldModel);

                Auto auto = query.getSingleResult();
                auto.setModel(newModel);

                session.beginTransaction();

                try{
                    session.save(auto);
                    session.getTransaction().commit();
                }catch(Exception ex){
                    System.out.println(ex);
                    ex.printStackTrace();
                    session.getTransaction().rollback();
                }

                System.out.println("Список после изменения модели автомобиля:");
                listAuto = session.createQuery("from Auto").getResultList();
                System.out.printf("%-20s %-20s %-20s %-20s %n", "Auto_ID", "Automaker", "Model", "Dealer");
                listAuto.stream()
                        .forEach(x -> System.out.printf("%-20s %-20s %-20s %-20s%n", x.getId(), x.getAutomaker(), x.getModel(), x.getListDealer()));


            }
            else if(input.equals("4")){
                System.out.println("4. Delete.");

                System.out.println("Автомобили, имеющиеся в базе:");
                List<Auto> listAuto = session.createQuery("from Auto").getResultList();
                System.out.printf("%-20s %-20s %-20s %-20s %n", "Auto_ID", "Automaker", "Model", "Dealer");
                listAuto.stream()
                        .forEach(x -> System.out.printf("%-20s %-20s %-20s %-20s%n", x.getId(), x.getAutomaker(), x.getModel(), x.getListDealer()));

                System.out.println("Введите модель автомобиля, которая будет удалена из базы:");
                String deletedModel = scn.nextLine();

                Query <Auto> query = session.createQuery("from Auto where model := modelParameter");
                query.setParameter("modelParameter", deletedModel);

                Auto auto = query.getSingleResult();

                session.beginTransaction();

                try{
                    session.remove(auto);
                    session.getTransaction().commit();
                }catch(Exception ex){
                    System.out.println(ex);
                    ex.printStackTrace();
                    session.getTransaction().rollback();
                }

                System.out.println("Список после удаления модели автомобиля:");
                listAuto = session.createQuery("from Auto").getResultList();
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

