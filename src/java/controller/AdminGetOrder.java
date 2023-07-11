/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Repository.IOrderShippingRepository;
import Repository.IPaymentMethodRepository;
import Repository.ITransactionRepository;
import dto.OrderDto;
import java.io.IOException;
import java.util.ArrayList;
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
import model.PaymentMethod;
import model.Transactions;
import service.OrderShippingService;
import service.PaymentMethodService;
import service.TransactionService;

/**
 *
 * @author Lenovo
 */
public class AdminGetOrder extends HttpServlet {

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
    private IOrderShippingRepository _orderShipppingRepository;
    private ITransactionRepository _transactionRepository;
    private IPaymentMethodRepository _paymentMethodRepository;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try 
        {
            HttpSession session = request.getSession();
            if(session.getAttribute("order") != null)
            {
                session.removeAttribute("order");
            }
            
            String orderid = request.getParameter("orderid");
            List<OrderDto> orders = new ArrayList();
            
            if(session.getAttribute("orderDto") != null)
            {
                orders = (List<OrderDto>) session.getAttribute("orderDto");
                for(OrderDto o : orders)
                {
                    if(o.getOrder().getOrderid().equals(orderid))
                    {
                        Transactions t = _transactionRepository.GetTransaction(o.getTransactions().getTransactionid());
                        PaymentMethod pm = _paymentMethodRepository.GetPaymentMethodById(t.getPaymentid());
                        session.setAttribute("singlePm", pm);
                        session.setAttribute("order", o);
                        break;
                    }
                }
            }
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        response.sendRedirect(request.getHeader("Referer"));
    }
    
        
    public void init () throws ServletException
    {
        _orderShipppingRepository = new OrderShippingService(utx, em);
        _transactionRepository = new TransactionService(utx, em);
        _paymentMethodRepository = new PaymentMethodService(utx, em);
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
