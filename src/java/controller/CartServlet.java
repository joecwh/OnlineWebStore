/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Mapper.Mapper;
import Repository.ICartRepository;
import Repository.IProductRepository;
import dto.UserDto;
import java.io.IOException;
import java.io.PrintWriter;
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
import model.Cart;
import model.Product;
import service.CartService;
import service.ProductService;

/**
 *
 * @author Lenovo
 */
public class CartServlet extends HttpServlet {

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
            UserDto user = (UserDto) session.getAttribute("user");
            List<Cart> carts = _cartRepository.GetCartByUserId(user.getUserid());
            List<Product> products = new ArrayList();
            for(Cart c : carts)
            {
                if(!c.getIspaid())
                {
                    Product product = _productRepository.GetProduct(c.getProductid()).getResult();
                    products.add(product);
                }
            }
            if(!carts.isEmpty())
                session.setAttribute("carts", Mapper.fromCartListToCartDtoList(carts, products));
        }
        catch(Exception ex)
        {
            System.out.println("get cart failed" + ex.getMessage());
        }
        response.sendRedirect(request.getHeader("Referer"));
    }

    public void init() throws ServletException
    {
        _cartRepository = new CartService(utx, em);
        _productRepository = new ProductService(utx, em); // Instantiate the repository object
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
