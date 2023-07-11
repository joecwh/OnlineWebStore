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
import java.net.URLEncoder;
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
public class AdminUpdateBanner extends HttpServlet {

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
            HttpSession session = request.getSession();
            String bannerid = request.getParameter("bannerid");
            String bannername = request.getParameter("bannername");
            String description = request.getParameter("description");
            Part filePart = request.getPart("newbannerimg");
            
            Banner banner = bannerRepository.GetBanner(bannerid).getResult();
            if(banner != null)
            {
                banner.setBannername(bannername);
                banner.setBannerdesc(description);
                
                if(filePart != null && filePart.getSize() > 0)
                {
                    InputStream fileContent = filePart.getInputStream();
                    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                    int nRead;
                    byte[] data = new byte[1024];

                    while ((nRead = fileContent.read(data, 0, data.length)) != -1) {
                        buffer.write(data, 0, nRead);
                    }
                    buffer.flush();
                    byte[] newbannerimg = buffer.toByteArray();
                    banner.setBannerimg(newbannerimg);
                }
                
                Result result = bannerRepository.UpdateBanner(banner);
                if(result.getStatus())
                    session.setAttribute("banner", result.getResult());
                response.sendRedirect(request.getHeader("Referer") + "&alert=" + URLEncoder.encode(result.getMessage(), "UTF-8"));
            }
            
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
