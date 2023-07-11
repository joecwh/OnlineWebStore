/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Repository.IFeedbackRepository;
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
import model.Feedback;
import service.FeedbackService;

/**
 *
 * @author Lenovo
 */
public class AdminGetAllFeedback extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Resource UserTransaction utx;
    @PersistenceContext EntityManager em;
    private IFeedbackRepository _feedbackRepository;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try
        {
            HttpSession session = request.getSession();
            String email = null;
            if(request.getParameter("email") != null)
            {
                email = request.getParameter("email");
            }
            
            List<Feedback> feedbacks = new ArrayList();
            if(email != null)
            {
                System.out.println("get in email");
                List<Feedback> f = _feedbackRepository.GetFeedbackByEmail(email);
                if(!f.isEmpty())
                    feedbacks.addAll(f);
            }
            else
            {
                feedbacks = _feedbackRepository.GetFeedbacks();
            }
            
            if(!feedbacks.isEmpty())
            {
                session.setAttribute("feedbacks", feedbacks);
            }
        }
        catch(Exception ex)
        {
            System.out.println("get all feedback fail : " + ex.getMessage());
        }
        response.sendRedirect(request.getHeader("Referer"));
    }
    
    public void init() throws ServletException
    {
        _feedbackRepository = new FeedbackService(utx, em);
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
