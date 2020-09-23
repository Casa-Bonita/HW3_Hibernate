package realisation.One_To_Many;

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

                Master master1 = new Master("Pol", "Pol");
                Master master2 = new Master("John", "John");

                List<Slave> listSlave1 = Arrays.asList(new Slave("Tony", "Tony", 35, master1), new Slave("Liza", "Liza", 30, master1));
                List<Slave> listSlave2 = Arrays.asList(new Slave("Jack", "Jack", 25, master2), new Slave("Alla", "Alla", 20, master2));

                master1.setListSlave(listSlave1);
                master2.setListSlave(listSlave2);

                session.beginTransaction();
                session.save(master1);
                session.save(master2);

                for(Slave sl : listSlave1){
                    session.save(sl);
                }

                for(Slave sl : listSlave2){
                    session.save(sl);
                }

                session.getTransaction().commit();

            }
            else if(input.equals("2")){
                System.out.println("2. Read.");

                List<Master> listMaster = session.createQuery("from Master").getResultList();
                System.out.printf("%-20s %-20s %-20s %-20s %n", "Master_ID", "Master_Name", "Master_Surname", "Slaves");
                listMaster.stream()
                        .forEach(x -> System.out.printf("%-20s %-20s %-20s %-20s %n", x.getId(), x.getNameMaster(), x.getSurnameMaster(), x.getListSlave()));

            }
            else if(input.equals("3")){
                System.out.println("3. Update.");

                System.out.println("Люди, имеющиеся в базе:");
                List<Master> listMaster = session.createQuery("from Master").getResultList();
                System.out.printf("%-20s %-20s %-20s %-20s %n", "Master_ID", "Master_Name", "Master_Surname", "Slaves");
                listMaster.stream()
                        .forEach(x -> System.out.printf("%-20s %-20s %-20s %-20s %n", x.getId(), x.getNameMaster(), x.getSurnameMaster(), x.getListSlave()));

                System.out.println("Введите фамилию Master's, которая будет изменена:");
                String oldMasterSurname = scn.nextLine();

                System.out.println("Введите новую фамилию Master's:");
                String newMasterSurname = scn.nextLine();

                Query <Master> query = session.createQuery("from Master where surname := masterParameter");
                query.setParameter("masterParameter", oldMasterSurname);

                Master master = query.getSingleResult();
                master.setSurnameMaster(newMasterSurname);

                session.beginTransaction();

                try{
                    session.save(master);
                    session.getTransaction().commit();
                }catch(Exception ex){
                    ex.printStackTrace();
                    System.out.println(ex);
                    session.getTransaction().rollback();
                }

                System.out.println("Список после изменения фамилии человека:");
                listMaster = session.createQuery("from Master").getResultList();
                System.out.printf("%-20s %-20s %-20s %-20s %n", "Master_ID", "Master_Name", "Master_Surname", "Slaves");
                listMaster.stream()
                        .forEach(x -> System.out.printf("%-20s %-20s %-20s %-20s %n", x.getId(), x.getNameMaster(), x.getSurnameMaster(), x.getListSlave()));


            }
            else if(input.equals("4")){
                System.out.println("4. Delete.");
                System.out.println("Люди, имеющиеся в базе:");
                List<Master> listMaster = session.createQuery("from Master").getResultList();
                System.out.printf("%-20s %-20s %-20s %-20s %n", "Master_ID", "Master_Name", "Master_Surname", "Slaves");
                listMaster.stream()
                        .forEach(x -> System.out.printf("%-20s %-20s %-20s %-20s %n", x.getId(), x.getNameMaster(), x.getSurnameMaster(), x.getListSlave()));


                System.out.println("Введите фамилию Master's, который будет удален из базы:");
                String deletedMasterSurname = scn.nextLine();

                session.getTransaction();

                Query <Master> query = session.createQuery("from Master where surname := masterParameter");
                query.setParameter("masterParameter", deletedMasterSurname);

                Master master = query.getSingleResult();
                master.setSurnameMaster(deletedMasterSurname);

                session.beginTransaction();

                try{
                    session.remove(master);
                    session.getTransaction().commit();
                }catch(Exception ex){
                    System.out.println(ex);
                    ex.printStackTrace();
                    session.getTransaction().rollback();
                }

                System.out.println("Список после удаления человека:");
                listMaster = session.createQuery("from Master").getResultList();
                System.out.printf("%-20s %-20s %-20s %-20s %n", "Master_ID", "Master_Name", "Master_Surname", "Slaves");
                listMaster.stream()
                        .forEach(x -> System.out.printf("%-20s %-20s %-20s %-20s %n", x.getId(), x.getNameMaster(), x.getSurnameMaster(), x.getListSlave()));

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
