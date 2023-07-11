/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Repository.IOrderShippingRepository;
import enumeration.OrderCode;
import static enumeration.OrderShippingCode.*;
import enumeration.ShippingCode;
import java.io.IOException;
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
import model.*;
import service.OrderShippingService;

/**
 *
 * @author Lenovo
 */
public class AdminUpdateOrderStatus extends HttpServlet {

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
        String url = request.getHeader("Referer");
        if (url.contains("?")) {
            url += "&";
        } else {
            url += "?";
        }
        String newUrl = url + "alert=" + ORDER_STATUS_UPDATE_FAIL.getMessage();
        try
        {
            HttpSession session = request.getSession();
            String orderid = request.getParameter("orderid");
            String shippingid = request.getParameter("shippingid");
            String status = request.getParameter("orderstatus");
            
            Orders order = _orderShipppingRepository.GetOrder(orderid);
            Shipping shipping = _orderShipppingRepository.GetShipping(shippingid);
            if(order != null && shipping != null)
            {
                if(status.equals(OrderCode.COMPLETED.name()))
                {
                    order.setStatus(status);
                    shipping.setStatus(ShippingCode.RECEIVED.name());
                }
                else
                {
                    order.setStatus(status);
                    shipping.setStatus(ShippingCode.PENDING.name());
                }
                if(_orderShipppingRepository.UpdateOrder(order) && _orderShipppingRepository.UpdateShipping(shipping))
                    newUrl = url + "alert=" + ORDER_STATUS_UPDATE.getMessage();
            }
        }
        catch(Exception ex)
        {
            System.out.println("update order status failed : " + ex.getMessage());
        }
        response.sendRedirect(newUrl);
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
