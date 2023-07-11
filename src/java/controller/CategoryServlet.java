/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Repository.ICategoryRepository;
import Repository.IProductRepository;
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
import model.Category;
import model.Product;
import model.Result;
import service.CategoryService;
import service.ProductService;

/**
 *
 * @author Lenovo
 */
public class CategoryServlet extends HttpServlet {

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
    private ICategoryRepository _categoryRepository;
    private IProductRepository _productRepository;

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try
        {
            HttpSession session = request.getSession();
            if(session.getAttribute("prodCat") != null)
                session.removeAttribute("prodCat");
            String categoriesid = request.getParameter("categoriesid");
            List<Product> prodCat = new ArrayList();

            Result<Category> categories = _categoryRepository.GetCategoryByCategories(categoriesid);
            if(!categories.getResultList().isEmpty())
            {
                List<Category> categoryList = categories.getResultList();
                for(Category c : categoryList)
                {
                    Result result = _productRepository.GetProduct(c.getProductid());
                    prodCat.add((Product) result.getResult());
                }
                session.setAttribute("prodCat", prodCat);
            }

        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        response.sendRedirect(request.getHeader("Referer"));
    }
        
    @Override
    public void init() throws ServletException 
    {
        _categoryRepository = new CategoryService(utx, em);
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
