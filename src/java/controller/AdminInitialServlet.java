/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Mapper.Mapper;
import Repository.IAdminRepository;
import Repository.IOrderShippingRepository;
import Repository.ITransactionRepository;
import Repository.IUserRepository;
import dto.AdminDashboardDto;
import dto.UserDto;
import static enumeration.OrderCode.COMPLETED;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import model.Orders;
import model.Result;
import model.Subscriber;
import model.Transactions;
import service.AdminService;
import service.OrderShippingService;
import service.TransactionService;
import service.UserService;

/**
 *
 * @author Lenovo
 */
public class AdminInitialServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @PersistenceContext EntityManager em;
    @Resource UserTransaction utx;
    private IAdminRepository _adminRepository;
    private IUserRepository _userRepository;
    private Mapper _mapper;    
    private IOrderShippingRepository _orderShipppingRepository;
    private ITransactionRepository _transactionRepository;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try
        {
            HttpSession session = request.getSession();
            List<Transactions> transactions = _transactionRepository.GetAllTransaction();
            String totalRevenue = "0";
            if(!transactions.isEmpty())
            {
                double total = 0.0;
                for(Transactions t : transactions)
                {
                    total += t.getTotal();
                }
                totalRevenue = _adminRepository.GetTotalWithSuffix(total);
            }
            
            Result<UserDto> users = _userRepository.GetCustomers();
            String totalCustomer = "0";
            if(!users.getResultList().isEmpty())
            {
                totalCustomer = _adminRepository.GetTotalWithSuffix(users.getResultList().size());
            }
            
            List<Subscriber> subscribers = em.createNamedQuery("Subscriber.findAll").getResultList();
            String totalSubscriber = "0";
            if(!subscribers.isEmpty())
            {
                totalSubscriber = _adminRepository.GetTotalWithSuffix(subscribers.size());
            }
            
            List<Orders> orders = _orderShipppingRepository.GetAllOrder();
            String totalOrderCompleted = "0";
            if(!orders.isEmpty())
            {
                int count = 0;
                for(Orders o : orders)
                {
                    if(o.getStatus().equals(COMPLETED.name()))
                    {
                        count++;
                    }
                }
                totalOrderCompleted = _adminRepository.GetTotalWithSuffix(count);
            }

            List<Integer> visitorCount = _userRepository.GetMonthlyVisitorCount();
            List<Double> totalAnnualTransaction = _transactionRepository.GetPast5yearsAnnualTransaction();
            List<Double> totalMonthlyTransaction = _transactionRepository.GetAllMonthlyTransaction(String.valueOf(LocalDate.now().getYear()));
            List<Double> totalDailyTransaction = _transactionRepository.GetAllDailyTransaction();
            List<String> dayOfWeek = _transactionRepository.GetPast7DaysInWeek();
            
            AdminDashboardDto dashboardData = new AdminDashboardDto(
                    totalRevenue, 
                    totalCustomer, 
                    totalSubscriber, 
                    totalOrderCompleted, 
                    totalAnnualTransaction, 
                    totalMonthlyTransaction,
                    totalDailyTransaction,
                    visitorCount,
                    dayOfWeek);
            
            session.setAttribute("dashboardData", dashboardData);
        }
        catch(Exception ex)
        {
            System.out.println("admin initial servlet fail : " + ex.getMessage());
        }
        response.sendRedirect(request.getHeader("Referer"));
    }
    
    @Override
    public void init() throws ServletException 
    {
        _adminRepository = new AdminService(utx, em, _mapper); // Instantiate the repository object
        _orderShipppingRepository = new OrderShippingService(utx, em);
        _transactionRepository = new TransactionService(utx, em);
        _userRepository = new UserService(utx, em, _mapper);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
