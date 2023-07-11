/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Mapper.Mapper;
import Repository.IEmailRepository;
import Repository.IUserRepository;
import static enumeration.EmailCode.COMPANY_EMAIL;
import static enumeration.EmailCode.COMPANY_SMTP_PASSWORD;
import static enumeration.EmailCode.EMAIL_NOT_FOUND;
import static enumeration.EmailCode.ERROR;
import static enumeration.EmailCode.RESET_PASSWORD_EMAIL_SENT;
import static enumeration.EmailCode.VERIFY_EMAIL_SUBJECT;
import java.io.File;
import java.io.IOException;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import model.Email;
import model.Temporarypassword;
import service.EmailService;
import service.UserService;

/**
 *
 * @author Lenovo
 */
public class ResetPassword extends HttpServlet {

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
    private IUserRepository _userRepository;
    private Mapper _mapper;
    private IEmailRepository _emailRepository;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = request.getHeader("Referer");
        if (url.contains("?")) {
            url += "&alert=";
        } else {
            url += "?alert=";
        }
        String newUrl = url + ERROR.getMessage();
        
        try
        {
            String email = request.getParameter("email");
            if(_userRepository.GetUserByEmail(email) != null)
            {
                String contextPath = getServletContext().getRealPath("");
                File contextDir = new File(contextPath).getParentFile().getParentFile();
                String htmlTemplate = contextDir.getPath() + "\\web\\EmailTemplates\\resetPassword_template.html";

                Temporarypassword tempass = new Temporarypassword(email);
                if(_userRepository.AddTemporaryPassword(tempass))
                {
                    Email verifyEmail = new Email(VERIFY_EMAIL_SUBJECT.getMessage(), tempass.getTempass(), email);
                    if(_emailRepository.SendResetPasswordEmail(verifyEmail, htmlTemplate))
                        newUrl = url + RESET_PASSWORD_EMAIL_SENT.getMessage();
                }
            }
            else
                newUrl = url + EMAIL_NOT_FOUND.getMessage();
        }
        catch(Exception ex)
        {
            System.out.println("email subscribe failed : " + ex.getMessage());
        }
        response.sendRedirect(newUrl);
    }
    
    @Override
    public void init() throws ServletException 
    {
        _userRepository = new UserService(utx, em, _mapper); // Instantiate the repository object
        _emailRepository = new EmailService(utx, em, COMPANY_EMAIL.getMessage(), COMPANY_SMTP_PASSWORD.getMessage());
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
