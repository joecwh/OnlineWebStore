/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Repository.ICategoryRepository;
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
import model.Result;
import service.CategoryService;

/**
 *
 * @author Lenovo
 */
public class AdminDeleteCategories extends HttpServlet {

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
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Result deleteResult = new Result();
        try
        {
            HttpSession session = request.getSession();
            String categoriesid = request.getParameter("categoriesid");
            boolean checkResult = _categoryRepository.CategoryExistsByCategories(categoriesid);

            if(!checkResult)
            {
                deleteResult = _categoryRepository.DeleteCategories(categoriesid);
                if(deleteResult.getStatus())
                    session.removeAttribute("admincategories");
            }
        }
        catch(Exception ex)
        {
            System.out.println("Categories has failed to delete\n" + ex.getMessage());
        }
        response.sendRedirect(request.getHeader("Referer") + "?alert=" + (deleteResult.getStatus() ? deleteResult.getMessage() : "Categories has failed to delete due to it's bind with product"));
    }
    
    @Override
    public void init() throws ServletException 
    {
        _categoryRepository = new CategoryService(utx, em); // Instantiate the repository object
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
