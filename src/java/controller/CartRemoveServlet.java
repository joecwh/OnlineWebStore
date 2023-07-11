/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Repository.ICartRepository;
import static enumeration.CartCode.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import model.Cart;
import service.CartService;

/**
 *
 * @author Lenovo
 */
public class CartRemoveServlet extends HttpServlet {

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
    private ICartRepository _cartRepository;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try  
        {
            HttpSession session = request.getSession();
            String cartid = request.getParameter("cartid");

            Cart cartExists = _cartRepository.GetCartByCartId(cartid);
            
            boolean removeCart = false;
            if(cartExists != null)
                removeCart = _cartRepository.DeleteCart(cartid);

            if(removeCart)
            {
                if(session.getAttribute("carts") != null)
                    session.removeAttribute("carts");
                
                response.sendRedirect(request.getHeader("Referer") + "?alert=" + CART_DELETE_SUCCESS.getMessage());
            }
            else
                response.sendRedirect(request.getHeader("Referer") + "?alert=" + CART_DELETE_FAIL.getMessage());

        }
        catch(Exception ex)
        {
            System.out.println("delete cart failed" + ex.getMessage());
            response.sendRedirect(request.getHeader("Referer") + "?alert=" + CART_DELETE_FAIL.getMessage());
        }
    }
    
    public void init() throws ServletException
    {
        _cartRepository = new CartService(utx, em);
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
