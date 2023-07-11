/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Repository.IFeedbackRepository;
import static enumeration.UserCode.ERROR;
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
import model.Feedback;
import service.FeedbackService;

/**
 *
 * @author Lenovo
 */
public class AdminUpdateFeedbackStatus extends HttpServlet {

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
         String url = request.getHeader("Referer") + "&alert=" + ERROR.getMessage();
        try
        {
            HttpSession session = request.getSession();

            if(session.getAttribute("feedback") != null)
                session.removeAttribute("feedback");
            
            String id = request.getParameter("id");
            String status = request.getParameter("status");
            Feedback feedback = _feedbackRepository.GetFeedback(id);
            if(feedback != null)
            {
                feedback.setStatus(status);
                if(_feedbackRepository.UpdateFeedbackStatus(feedback))
                {
                    session.setAttribute("feedback", feedback);
                    url = request.getHeader("Referer") + "&alert=Update Feedback Status Success.";
                }
            }
        }
        catch(Exception ex)
        {
            System.out.println("get feedback fail : " + ex.getMessage());
        }
        response.sendRedirect(url);
    }
    
    @Override
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
