/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Repository.ITransactionRepository;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import model.*;

/**
 *
 * @author Lenovo
 */
public class TransactionService implements ITransactionRepository
{
    @PersistenceContext EntityManager em;
    @Resource UserTransaction utx;

    public TransactionService(
            UserTransaction utx, 
            EntityManager em)
    {
        this.utx = utx;
        this.em = em;
    }

    @Override
    public List<Transactions> GetAllTransaction() {
        List<Transactions> transactions = em.createNamedQuery("Transactions.findAll").getResultList();
        return transactions.isEmpty() ? new ArrayList() : transactions;
    }

    @Override
    public Transactions GetTransaction(String transactionid) {
        Transactions transaction = em.find(Transactions.class, transactionid);
        return transaction == null ? null : transaction;
    }

    @Override
    public List<Cart> GetCartByTransactionId(String transactionid) {
        Transactions transactions = em.find(Transactions.class, transactionid);
        List<Cart> carts = new ArrayList();
        if(transactions != null)
        {
            List<String> cartids = Arrays.asList(transactions.getCartid().split(","));
            for(String s : cartids)
            {
                Cart cart = em.find(Cart.class, s);
                carts.add(cart);
            }
        }
        return carts.isEmpty() ? new ArrayList() : carts;
    }

    @Override
    public boolean AddTransaction(Transactions transaction) {
        try
        {
            utx.begin();
            em.persist(transaction);
            utx.commit();
            
            return true;
        }
        catch(Exception ex)
        {
            System.out.println("add transaction fail : " + ex.getMessage());
        }
        return false;
    }

    @Override
    public double GetAnnualTransaction(String year) {
        try
        {
            int yearValue = Integer.parseInt(year);
            double annualTotal = em.createQuery("SELECT SUM(t.total) FROM Transactions t WHERE EXTRACT(YEAR FROM t.createdat) = :yearValue", Double.class)
                    .setParameter("yearValue", yearValue)
                    .getSingleResult();

            return annualTotal;
        }
        catch(Exception ex)
        {
            System.out.println("get annual transaction fail : " + ex.getMessage());
        }
        return 0.0; 
    }

    @Override
    public List<Double> GetPast5yearsAnnualTransaction() {
        int currentYear = LocalDate.now().getYear();
        List<Double> past5TotalAnnual = new ArrayList();
        for(int i = 0; i < 5; i++)
        {
            double TotalAnnual = GetAnnualTransaction(String.valueOf(currentYear - i));
            past5TotalAnnual.add(TotalAnnual);
        }

        return past5TotalAnnual;
    }

    @Override
    public double GetSingleMonthTotalTransaction(String year, int month) {
        try {
            double monthlyTotal = em.createQuery("SELECT SUM(t.total) FROM Transactions t WHERE EXTRACT(YEAR FROM t.createdat) = :yearValue AND EXTRACT(MONTH FROM t.createdat) = :monthValue", Double.class)
                    .setParameter("yearValue", year)
                    .setParameter("monthValue", month)
                    .getSingleResult();

            return monthlyTotal;
        } catch (Exception ex) {
            return 0.0;
        }
    }

    @Override
    public List<Double> GetAllMonthlyTransaction(String year) {
        List<Double> totalAnnual = new ArrayList<>();
        for (int i = 1; i < 13; i++) {
            double totalMonth = GetSingleMonthTotalTransaction(year, i);
            totalAnnual.add(totalMonth);
        }
        return totalAnnual;
    }

    @Override
    public double GetDailyTransaction(int day) {
        try
        {
            double daily = em.createQuery("SELECT SUM(t.total) FROM Transactions t WHERE EXTRACT(DAY FROM t.createdat) = :dayValue", Double.class)
                    .setParameter("dayValue", day)
                    .getSingleResult();

            return daily;
        }
        catch(Exception ex)
        {
            System.out.println("get single day transaction fail : " + ex.getMessage());
        }
        return 0.0; 
    }
    
    @Override
    public List<Double> GetAllDailyTransaction() {
        List<Double> dailyTotal = new ArrayList();
        try
        {
            LocalDate currentDate = LocalDate.now();
            
            for(int i = 6; i >= 0; i--)
            {
                LocalDate dateToCheck = currentDate.minusDays(i);
                dailyTotal.add(GetDailyTransaction(dateToCheck.getDayOfMonth()));
            }
            return dailyTotal;
        }
        catch(Exception ex)
        {
            System.out.println("get annual transaction fail : " + ex.getMessage());
        }
        return dailyTotal; 
    }

    @Override
    public List<String> GetPast7DaysInWeek() 
    {
        LocalDate currentDate = LocalDate.now();
        DayOfWeek currentDayOfWeek = currentDate.getDayOfWeek();

        List<String> daysOfWeek = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            daysOfWeek.add(currentDayOfWeek.toString());
            currentDayOfWeek = currentDayOfWeek.minus(1); // Get the next day
        }

        Collections.reverse(daysOfWeek); // Arrange in descending order
        return daysOfWeek;
    }
}
