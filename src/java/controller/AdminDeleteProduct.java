/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Repository.ICartRepository;
import Repository.ICategoryRepository;
import Repository.IProductRepository;
import static enumeration.ProductCode.*;
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
import model.Category;
import model.Result;
import service.CartService;
import service.CategoryService;
import service.ProductService;

/**
 *
 * @author Lenovo
 */
public class AdminDeleteProduct extends HttpServlet {

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
    private IProductRepository _productRepository;
    private ICategoryRepository _categoryRepository;
    private ICartRepository _cartRepository;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Result deleteResult = new Result();
        try
        {
            HttpSession session = request.getSession();
            String productid = request.getParameter("productid");

            Result checkResult = _productRepository.GetProduct(productid);

            Result<Category> checkCategory = _categoryRepository.GetCategoryByProduct(productid);

            List<Cart> carts = _cartRepository.GetCartByProductId(productid);
            if(!carts.isEmpty())
                deleteResult.setMessage(PRODUCT_DELETE_FAILED_CART_EXIST.getMessage());

            if(checkResult.getStatus() && checkCategory.getStatus() && carts.isEmpty())
            {
                deleteResult = _productRepository.DeleteProduct(productid);
                Result deleteCategory = _categoryRepository.DeleteCategory(checkCategory.getResult().getCategoryid());
                if(deleteResult.getStatus() && deleteCategory.getStatus())
                {
                    session.removeAttribute("adminproducts");
                }
            }
            response.sendRedirect(request.getHeader("Referer") + "?alert=" + deleteResult.getMessage());
        }
        catch(Exception ex)
        {
            System.out.println("Product has failed to delete\n" + ex.getMessage());
            response.sendRedirect(request.getHeader("Referer") + "?alert=" + deleteResult.getMessage());
        }
    }
    
    @Override
    public void init() throws ServletException 
    {
        _productRepository = new ProductService(utx, em); // Instantiate the repository object
        _categoryRepository = new CategoryService(utx, em); // Instantiate the repository object
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
