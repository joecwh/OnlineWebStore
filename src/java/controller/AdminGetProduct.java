/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Repository.ICategoryRepository;
import Repository.IProductRepository;
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
import model.Categories;
import model.Category;
import model.Product;
import model.Result;
import service.CategoryService;
import service.ProductService;

/**
 *
 * @author Lenovo
 */
public class AdminGetProduct extends HttpServlet {

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
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try
        {
            HttpSession session = request.getSession();
            String productid = request.getParameter("productid");
            Result<Product> product = _productRepository.GetProduct(productid);
            if(product.getStatus())
                session.setAttribute("product", product.getResult());
            
            Result<Category> category = _categoryRepository.GetCategoryByProduct(productid);
            if(category.getStatus())
                session.setAttribute("admincategory", category.getResult());
            
            Result<Categories> categories = _categoryRepository.GetCategories();
            if(categories.getStatus())
                session.setAttribute("categories", categories.getResultList());

        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        response.sendRedirect("adminproductmanagement.jsp");
    }
    
    @Override
    public void init() throws ServletException 
    {
        _productRepository = new ProductService(utx, em); // Instantiate the repository object
        _categoryRepository = new CategoryService(utx, em);
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
