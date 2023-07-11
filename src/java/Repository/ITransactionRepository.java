/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repository;

import java.time.DayOfWeek;
import java.util.List;
import model.Cart;
import model.PaymentMethod;
import model.Transactions;

/**
 *
 * @author Lenovo
 */
public interface ITransactionRepository 
{
    public List<Transactions> GetAllTransaction();
    public Transactions GetTransaction(String transactionid);
    public List<Cart> GetCartByTransactionId(String transactionid);
    public boolean AddTransaction(Transactions transaction);
    public double GetAnnualTransaction(String year);
    public List<Double> GetPast5yearsAnnualTransaction();
    public double GetSingleMonthTotalTransaction(String year, int month);
    public List<Double> GetAllMonthlyTransaction(String year);
    public double GetDailyTransaction(int day);
    public List<Double> GetAllDailyTransaction();
    public List<String> GetPast7DaysInWeek();
}
