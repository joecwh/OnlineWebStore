/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.UUID;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import model.Roles;

/**
 *
 * @author Lenovo
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
//    @PersistenceContext EntityManager em;
//    @Resource UserTransaction utx;
//    public NewMain()
//    {
//        // Generate a new UUID
//        UUID uuid = UUID.randomUUID();
//        String rolesID = uuid.toString();
//        Roles roles = new Roles(rolesID, "CUSTOMER");
//        try
//        {
//            utx.begin();
//            em.persist(roles);
//            utx.commit();
//        }
//        catch(Exception ex)
//        {
//            System.out.println(ex.getMessage());
//        }
//    }
    @Resource static UserTransaction utx;
    @PersistenceContext static EntityManager em;
    
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Den_s_ToyPU");

        // Create an instance of EntityManager
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // Generate a random UUID
        String uuid = UUID.randomUUID().toString();

//        try {
//            // Begin a transaction
//            EntityTransaction transaction = entityManager.getTransaction();
//            utx.begin();
//
//            // Create a Role entity object
//            Roles role = new Roles();
//            role.setRolesid(uuid);
//            role.setRolename("CUSTOMER");
//
//            // Persist the Role object
//            em.persist(role);
//
//            // Commit the transaction
//            utx.commit();
//
//            System.out.println("Role inserted successfully!");
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            // Close the EntityManager and EntityManagerFactory
//            entityManager.close();
//            entityManagerFactory.close();
//        }
    }
    
}
