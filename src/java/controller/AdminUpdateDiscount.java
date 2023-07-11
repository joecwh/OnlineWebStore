/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Repository.IDiscountRepository;
import static enumeration.DiscountCode.*;
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
import model.Discount;
import service.DiscountService;

/**
 *
 * @author Lenovo
 */
public class AdminUpdateDiscount extends HttpServlet {

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
    private IDiscountRepository _discountRepository;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String message = DISCOUNT_UPDATE_FAIL.getMessage();
        try
        {
            HttpSession session = request.getSession();
            String discountid = request.getParameter("discountid");
            String name = request.getParameter("name");
            String code = request.getParameter("code");
            double percent = Double.parseDouble(request.getParameter("percent"));
            Discount discount = _discountRepository.GetDiscount(discountid);
            if(discount != null)
            {
                discount.setName(name);
                discount.setCode(code);
                discount.setPercent(percent);
            }
                    
            boolean updateDiscount = _discountRepository.UpSertDiscount(discount);

            if(updateDiscount)
            {
                session.removeAttribute("discounts");
                message = DISCOUNT_UPDATE_SUCCESS.getMessage();
            }
        }
        catch(Exception ex)
        {
            System.out.println("update discount failed " + ex.getMessage());
        }
        response.sendRedirect(request.getHeader("Referer") + "&alert=" + message);
    }
    
    @Override
    public void init() throws ServletException
    {
        _discountRepository = new DiscountService(utx, em);
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
