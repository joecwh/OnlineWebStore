/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Repository.IOrderShippingRepository;
import static enumeration.OrderCode.INCOMPLETE;
import static enumeration.OrderShippingCode.*;
import java.io.IOException;
import java.sql.Date;
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
import model.Shipping;
import service.OrderShippingService;

/**
 *
 * @author Lenovo
 */
public class AdminUpdateShippingStatus extends HttpServlet {

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
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = request.getHeader("Referer") + "&alert=" + SHIPPING_STATUS_UPDATE_FAIL.getMessage();
        try
        {
            HttpSession session = request.getSession();
            String orderid = request.getParameter("orderid");
            String shippingid = request.getParameter("shippingid");
            String expecteddate = request.getParameter("expecteddate");
            Date date = Date.valueOf(expecteddate);
            String status = request.getParameter("shippingstatus");
            
            Shipping shipping = _orderShipppingRepository.GetShipping(shippingid);
            Orders order = _orderShipppingRepository.GetOrder(orderid);
            if(shipping != null && order != null)
            {
                order.setStatus(INCOMPLETE.name());
                shipping.setStatus(status);
                shipping.setExpectedreachdate(date);
                if(_orderShipppingRepository.UpdateShipping(shipping) && _orderShipppingRepository.UpdateOrder(order))
                    url = request.getHeader("Referer") + "&alert=" + SHIPPING_STATUS_UPDATE.getMessage();
            }
        }
        catch(Exception ex)
        {
            System.out.println("update order status failed : " + ex.getMessage());
        }
        response.sendRedirect(url);
    }
    
    @Override
    public void init () throws ServletException
    {
        _orderShipppingRepository = new OrderShippingService(utx, em);
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
