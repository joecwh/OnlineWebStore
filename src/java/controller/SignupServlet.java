/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Mapper.Mapper;
import Repository.IEmailRepository;
import Repository.IUserRepository;
import dto.UserDto;
import static enumeration.EmailCode.*;
import static enumeration.UserCode.ERROR;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import model.*;
import service.EmailService;
import service.UserService;

/**
 *
 * @author Lenovo
 */
public class SignupServlet extends HttpServlet {

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
        String url = request.getHeader("Referer") + "?alert=" + ERROR.getMessage();
        try
        {
            HttpSession session = request.getSession();
            String username = request.getParameter("username");
            String fname = request.getParameter("fname") + " " + request.getParameter("lname");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String contact = request.getParameter("contact");
            String dob = request.getParameter("dob");
            String address = request.getParameter("address");
            String pincode = request.getParameter("pincode");
            String state = request.getParameter("state");
            String city = request.getParameter("city");
            Date date = Date.valueOf(dob);

            String fulladdress = address + ", " + pincode + ", " + state + ", " + city;

            Users user = new Users(username, fname, email, password, contact, date);
            user.setAddress(fulladdress);
            Verification verification = new Verification(user.getUserid());
            boolean addVerification = _userRepository.AddVerification(verification);
            Result register = _userRepository.CustomerRegister(user);
            if(register.getStatus() && addVerification)
            {
                String contextPath = getServletContext().getRealPath("");
                File contextDir = new File(contextPath).getParentFile().getParentFile();
                String htmlTemplate = contextDir.getPath() + "\\web\\EmailTemplates\\verification_template.html";
                
                Email verifyEmail = new Email(VERIFY_EMAIL_SUBJECT.getMessage(), verification.getToken() , email);
                _emailRepository.SendVerifyEmail(verifyEmail, htmlTemplate);
                UserDto userdto = Mapper.fromUserToUserDto(user);
                session.setAttribute("user", userdto);
                url = request.getHeader("Referer") + "?alert=" + register.getMessage();
            }
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        response.sendRedirect(url);
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
