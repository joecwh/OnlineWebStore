/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.time.DayOfWeek;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public class AdminDashboardDto 
{
    private String totalRevenue;
    private String totalCustomer;
    private String totalSubcriber;
    private String totalOrderCompleted;
    private List<Double> annualTotalTransaction;
    private List<Double> monthlyTotalTransaction;
    private List<Double> dailyTotalTransaction;
    private List<Integer> visitorCount;
    private List<String> dayOfWeek;
    
    public AdminDashboardDto(){}
    
    public AdminDashboardDto(
            String totalRevenue, 
            String totalCustomer, 
            String totalSubcriber, 
            String totalOrderCompleted, 
            List<Double> annualTotalTransaction, 
            List<Double> monthlyTotalTransaction, 
            List<Double> dailyTotalTransaction,
            List<Integer> visitorCount,
            List<String> dayOfWeek)
    {
        this.totalRevenue = totalRevenue;
        this.totalCustomer = totalCustomer;
        this.totalSubcriber = totalSubcriber;
        this.totalOrderCompleted = totalOrderCompleted;
        this.annualTotalTransaction = annualTotalTransaction;
        this.monthlyTotalTransaction = monthlyTotalTransaction;
        this.dailyTotalTransaction = dailyTotalTransaction;
        this.visitorCount = visitorCount;
        this.dayOfWeek = dayOfWeek;
    }
    
    public void setTotalRevenue(String totalRevenue)
    {
        this.totalRevenue = totalRevenue;
    }
    
    public String getTotalRevenue()
    {
        return totalRevenue;
    }

    public void setTotalCustomer(String totalCustomer)
    {
        this.totalCustomer = totalCustomer;
    }
    
    public String getTotalUser()
    {
        return totalCustomer;
    }
    
    public void setTotalSubscriber(String totalSubcriber)
    {
        this.totalSubcriber = totalSubcriber;
    }
    
    public String getTotalSubscriber()
    {
        return totalSubcriber;
    }
    
    public void setTotalOrderCompleted(String totalOrderCompleted)
    {
        this.totalOrderCompleted = totalOrderCompleted;
    }
    
    public String getTotalOrderCompleted()
    {
        return totalOrderCompleted;
    }
    
    public void setTotalAnnualTransaction(List<Double> annualTotalTransaction)
    {
        this.annualTotalTransaction = annualTotalTransaction;
    }
    
    public List<Double> getTotalAnnualTransaction()
    {
        return annualTotalTransaction;
    }
    
    public void setTotalMonthlyTransaction(List<Double> monthlyTotalTransaction)
    {
        this.monthlyTotalTransaction = monthlyTotalTransaction;
    }
    
    public List<Double> getTotalMonthlyTransaction()
    {
        return monthlyTotalTransaction;
    }
    
    public void setTotalDailyTransaction(List<Double> dailyTotalTransaction)
    {
        this.dailyTotalTransaction = dailyTotalTransaction;
    }
    
    public List<Double> getTotalDailyTransaction()
    {
        return dailyTotalTransaction;
    }
    
    public void setVisitorCount(List<Integer> visitorCount)
    {
        this.visitorCount = visitorCount;
    }
    
    public List<Integer> getVisitorCount()
    {
        return visitorCount;
    }
    
    public void setDayOfWeek(List<String> dayOfWeek)
    {
        this.dayOfWeek = dayOfWeek;
    }
    
    public List<String> getDayOfWeek()
    {
        return dayOfWeek;
    }
}
