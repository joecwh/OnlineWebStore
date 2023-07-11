/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Mapper.Mapper;
import Repository.IAdminRepository;
import java.io.IOException;
import java.io.PrintWriter;
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
import model.Result;
import model.Users;
import service.AdminService;

/**
 *
 * @author Lenovo
 */
public class UpdateProfileServlet extends HttpServlet {

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
    private IAdminRepository _adminRepository;
    private Mapper _mapper;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Result result = new Result();
        try  
        {
            HttpSession session = request.getSession();
            String userid = request.getParameter("userid");
            String username = request.getParameter("username");
            String fullname = request.getParameter("fullname");
            String email = request.getParameter("email");
            String contact = request.getParameter("contact");
            String dob = request.getParameter("dob");
            Date date = Date.valueOf(dob);
            String address = request.getParameter("address");
            
            Users oldUser = _adminRepository.GetStaff(userid).getResult();
            Users newUser = new Users(userid, username, fullname, email, contact, date, oldUser.getAccountstatus());
            
            newUser.setPassword(oldUser.getPassword());
            newUser.setCreatedat(oldUser.getCreatedat());
            newUser.setLastlogin(oldUser.getLastlogin());
            newUser.setAddress(address);

            result = _adminRepository.UpdateStaff(newUser);
            if(result.getStatus())
                session.setAttribute("user", result.getResult());
            response.sendRedirect(request.getHeader("Referer") + "?alert=" + result.getMessage());
        }
        catch(Exception ex)
        {
            System.out.println("get user failed" + ex.getMessage());
            response.sendRedirect(request.getHeader("Referer") + "?alert=" + result.getMessage());
        }
    }
    
    @Override
    public void init() throws ServletException 
    {
        _adminRepository = new AdminService(utx, em, _mapper); // Instantiate the repository object
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
