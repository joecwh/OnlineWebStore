/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Mapper.Mapper;
import Repository.IUserRepository;
import dto.UserDto;
import static enumeration.UserCode.*;
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
import service.UserService;

/**
 *
 * @author Lenovo
 */
public class ChangePasswordServlet extends HttpServlet {

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
    private Mapper _mapper;
    private IUserRepository _userRepository;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String message = ERROR.getMessage();
        try
        {
            HttpSession session = request.getSession();
            if(session.getAttribute("user") != null)
            {
                UserDto user = (UserDto) session.getAttribute("user");
                String oldpassword = request.getParameter("currentPassword");
                String newpassword = request.getParameter("newPassword");
                String confirmpassword = request.getParameter("confirmPassword");
                
                
                if(!newpassword.equals(confirmpassword))
                    message = PASSWORD_NOT_MATCH.getMessage();

                Result result = _userRepository.GetCustomerById(user.getUserid());
                if(result.getStatus() && newpassword.equals(confirmpassword))
                {
                    if(_userRepository.ChangePassword(user.getUserid(), oldpassword, newpassword))
                    {
                        message = CHANGE_PASSWORD_SUCCESS.getMessage();
                    }
                    else
                        message = CHANGE_PASSWORD_FAIL.getMessage();
                }
            }

        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        response.sendRedirect("profile.jsp?alert=" + message);
    }
    
    @Override
    public void init() throws ServletException 
    {
        _userRepository = new UserService(utx, em, _mapper); // Instantiate the repository object
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
