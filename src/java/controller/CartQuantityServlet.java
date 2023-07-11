/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Repository.ICartRepository;
import Repository.IProductRepository;
import static enumeration.CartCode.*;
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
import model.Cart;
import model.Product;
import model.Result;
import service.CartService;
import service.ProductService;

/**
 *
 * @author Lenovo
 */
public class CartQuantityServlet extends HttpServlet {

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
    private IProductRepository _productRepository;

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try  
        {
            HttpSession session = request.getSession();
            String cartid = request.getParameter("cartid");
            String action = request.getParameter("action");
            
            Cart cart = _cartRepository.GetCartByCartId(cartid);
            Product product = _productRepository.GetProduct(cart.getProductid()).getResult();
            double priceperunit = cart.getTotal()/cart.getQuantity();
            boolean updateCart = false;
            if(action.equals("minus"))
            {
                if(cart.getQuantity() > 1)
                {
                    cart.setQuantity(cart.getQuantity() - 1);
                    cart.setTotal(priceperunit * cart.getQuantity());
                    updateCart = _cartRepository.UpdateCart(cart);
                }
                else if(cart.getQuantity() <= 1)
                {
                    updateCart = _cartRepository.DeleteCart(cartid);
                    session.removeAttribute("carts");
                }
                
                if(updateCart)
                    response.sendRedirect(request.getHeader("Referer") + "?alert=" + CART_MINUS_SUCCESS.getMessage());
                else
                    response.sendRedirect(request.getHeader("Referer") + "?alert=" + CART_MINUS_FAIL.getMessage());
            }
            else if(action.equals("plus"))
            {
                if(cart.getQuantity() < product.getQuantity())
                {
                    cart.setQuantity(cart.getQuantity() + 1);
                    cart.setTotal(priceperunit * cart.getQuantity());
                    updateCart = _cartRepository.UpdateCart(cart);
                }
                
                if(updateCart)
                    response.sendRedirect(request.getHeader("Referer") + "?alert=" + CART_PLUS_SUCCESS.getMessage());
                else
                    response.sendRedirect(request.getHeader("Referer") + "?alert=" + CART_PLUS_FAIL.getMessage());
            }
        }
        catch(Exception ex)
        {
            System.out.println("delete cart failed" + ex.getMessage());
            response.sendRedirect(request.getHeader("Referer") + "?alert=" + ERROR.getMessage());
        }
    }
    
    public void init() throws ServletException
    {
        _cartRepository = new CartService(utx, em);
        _productRepository = new ProductService(utx, em);
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
