/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Repository.IReportRepository;
import static enumeration.ReportCode.*;
import java.io.File;
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
import model.Report;
import model.Result;
import service.ReportService;

/**
 *
 * @author Lenovo
 */
public class AdminDeleteReport extends HttpServlet {

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
    private IReportRepository _reportRepository;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String header = request.getHeader("Referer");
        String url = header + "?alert=" + REPORT_DELETE_FAILED.getMessage();
        try 
        {
            HttpSession session = request.getSession();
            String reportid = request.getParameter("reportid");
            Report report = _reportRepository.GetReport(reportid).getResult();
            if(report != null)
            {
                String contextPath = getServletContext().getRealPath("");
                File contextDir = new File(contextPath).getParentFile().getParentFile();
                String filePath = contextDir.getPath() + "\\web\\Reports\\" + report.getReportname();
            
                Result deleteReport = _reportRepository.DeleteReport(reportid);
                if(deleteReport.getStatus())
                {
                    File htmlFile = new File(filePath + ".html");
                    File pdfFile = new File(filePath + ".pdf");

                    if (htmlFile.exists() && pdfFile.exists()) {
                        boolean isHtmlDeleted = htmlFile.delete();
                        boolean isPdfDeleted = pdfFile.delete();

                        if (isHtmlDeleted && isPdfDeleted) 
                        {
                            System.out.println("File deleted successfully.");
                        } else {
                            System.out.println("Unable to delete the file.");
                        }
                    } 
                    else 
                    {
                        System.out.println("File does not exist.");
                    }
                    url = header + "?alert=" + REPORT_DELETE_SUCCESS.getMessage();
                }
            }
            else
                url = header + "?alert=" + REPORT_ID_INVALID.getMessage();
        }
        catch(Exception ex)
        {
            System.out.println("delete report failed : " + ex.getMessage());
        }
        response.sendRedirect(url);
    }
    
    @Override
    public void init() throws ServletException
    {
        _reportRepository = new ReportService(utx, em);
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
