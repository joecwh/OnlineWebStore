/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Repository.ICartRepository;
import dto.UserDto;
import static enumeration.CartCode.*;
import java.io.IOException;
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
import model.Cart;
import service.CartService;

/**
 *
 * @author Lenovo
 */
public class CartAddServlet extends HttpServlet {

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
        String message = CART_ADD_FAIL.getMessage();
        String url = request.getHeader("Referer");
        if (url.contains("?")) {
            url += "&";
        } else {
            url += "?";
        }
        
        try  
        {
            HttpSession session = request.getSession();
            if(session.getAttribute("user") != null)
            {
                UserDto user = (UserDto) session.getAttribute("user");
                String productid = request.getParameter("productid");
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                String totalStr = request.getParameter("total");
                totalStr = totalStr.replace("RM", "").trim();
                double total = Double.parseDouble(totalStr);
                
                Cart newCart = new Cart(user.getUserid(), productid, quantity, total);
                Cart cartExists = _cartRepository.GetCartByUserProductId(user.getUserid(), productid);

                boolean upsertCart = false;
                if(cartExists == null)
                {
                    List<Cart> carts = _cartRepository.GetCartByUserId(user.getUserid());
                    if(carts.size() < 6)
                    {
                        upsertCart = _cartRepository.AddCart(newCart);
                        if(upsertCart)
                            message = CART_ADD_SUCCESS.getMessage();
                    }
                    else
                        message = CART_LIMIT_EXCEED.getMessage();
                }
                else
                {
                    cartExists.setQuantity(cartExists.getQuantity() + quantity);
                    cartExists.setTotal(cartExists.getTotal()+ total);
                    upsertCart = _cartRepository.UpdateCart(cartExists);
                    if(upsertCart)
                        message = CART_UPDATE_SUCCESS.getMessage();
                    else
                        message = CART_UPDATE_FAIL.getMessage();
                }
                response.sendRedirect(url + "alert=" + message);
            }
            else
                response.sendRedirect(url + "alert=" + LOGIN_TO_ADD_CART.getMessage());

        }
        catch(Exception ex)
        {
            System.out.println("get cart failed" + ex.getMessage());
            response.sendRedirect(url + "alert=" + message);
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
