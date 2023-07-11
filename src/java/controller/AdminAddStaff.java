/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Mapper.Mapper;
import Repository.IAdminRepository;
import Repository.IEmailRepository;
import Repository.IUserRepository;
import dto.UserDto;
import static enumeration.EmailCode.COMPANY_EMAIL;
import static enumeration.EmailCode.COMPANY_SMTP_PASSWORD;
import static enumeration.EmailCode.VERIFY_EMAIL_SUBJECT;
import static enumeration.UserCode.ERROR;
import static enumeration.UserCode.USER_REGISTER_FAILED;
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
import model.Result;
import model.Users;
import model.Verification;
import service.AdminService;
import service.EmailService;
import service.UserService;

/**
 *
 * @author Lenovo
 */
public class AdminAddStaff extends HttpServlet {

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
    private IAdminRepository _adminRepository;
    private Mapper _mapper;
    private IUserRepository _userRepository;
    private IEmailRepository _emailRepository;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String message = ERROR.getMessage();
        try
        {
            String username = request.getParameter("uname");
            String fname = request.getParameter("fname");
            String lname = request.getParameter("lname");
            String fullname = fname + " " + lname;
            String email = request.getParameter("em");

            Users staff = new Users(username, fullname, email);

            Verification verification = new Verification(staff.getUserid());
            boolean addVerification = _userRepository.AddVerification(verification);
            Result<UserDto> result = _adminRepository.AddStaff(staff);
            if(result.getStatus()  && addVerification)
            {
                String contextPath = getServletContext().getRealPath("");
                File contextDir = new File(contextPath).getParentFile().getParentFile();
                String htmlTemplate = contextDir.getPath() + "\\web\\EmailTemplates\\accountSetupAssistance_template.html";
                
                Email verifyEmail = new Email(VERIFY_EMAIL_SUBJECT.getMessage(), verification.getToken() , email);
                if(_emailRepository.SendAccountSetupEmail(verifyEmail, htmlTemplate, staff.getPassword()))
                {
                    message = result.getMessage();
                }
            }
            else
                message = USER_REGISTER_FAILED.getMessage();
        }
        catch(Exception ex)
        {
            System.out.println("admin add staff failed : " + ex.getMessage());
        }
        String referer = request.getHeader("Referer");
        response.sendRedirect(referer + "?alert=" + message);
    }
    
    @Override
    public void init() throws ServletException 
    {
        _adminRepository = new AdminService(utx, em, _mapper); // Instantiate the repository object
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
