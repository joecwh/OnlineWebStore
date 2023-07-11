/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Repository.IBannerRepository;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.transaction.UserTransaction;
import model.Banner;
import model.Result;
import service.BannerService;

/**
 *
 * @author Lenovo
 */
@MultipartConfig
public class AdminAddBanner extends HttpServlet {

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
    private IBannerRepository bannerRepository;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try
        {
            String bannername = request.getParameter("bannername");
            String bannerdesc = request.getParameter("bannerdesc");
            Part bannerimg = request.getPart("bannerimg");

            //get img file from html and convert into byte[]
            InputStream inputStream = bannerimg.getInputStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer, 0, buffer.length)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            byte[] imageBytes = outputStream.toByteArray();

            Banner banner = new Banner(bannername, bannerdesc, imageBytes);
            Result result = bannerRepository.AddBanner(banner);
            String referer = request.getHeader("Referer");
            if(result.getStatus())
            {
                HttpSession session = request.getSession();
                Result banners = bannerRepository.GetBanners();
                session.setAttribute("banners", banners.getResultList());
            }
            response.sendRedirect(referer + "?alert=" + result.getMessage());
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    
    @Override
    public void init() throws ServletException 
    {
        bannerRepository = new BannerService(utx, em); // Instantiate the repository object
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
