/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Repository.ICategoryRepository;
import Repository.IProductRepository;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.StringJoiner;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.transaction.UserTransaction;
import model.Category;
import model.Product;
import model.Result;
import service.CategoryService;
import service.ProductService;

/**
 *
 * @author Lenovo
 */
@MultipartConfig
public class AdminAddProduct extends HttpServlet {

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
    private IProductRepository _productRepository;
    private ICategoryRepository _categoryRepository;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try
        {
            String productname = request.getParameter("productname");
            String productdesc = request.getParameter("productdesc");
            String[] Categories = request.getParameterValues("categories");
            StringJoiner joiner = new StringJoiner(",");
            for (String value : Categories) {
                joiner.add(value);
            }

            String categories = joiner.toString();
            // Remove the last comma if needed
            if (categories.endsWith(",")) {
                categories = categories.substring(0, categories.length() - 1);
            }

            Double price = Double.parseDouble(request.getParameter("price"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            Part productimg = request.getPart("productimg");
            
            //get img file from html and convert into byte[]
            InputStream inputStream = productimg.getInputStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer, 0, buffer.length)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            byte[] imageBytes = outputStream.toByteArray();
            
            Product product = new Product(productname, productdesc, price, quantity, imageBytes);
            Category category = new Category(categories, product.getProductid());
            
            Result addProductResult = _productRepository.AddProduct(product);
            Result addCategoryResult = _categoryRepository.AddCategory(category);

            String referer = request.getHeader("Referer");
            response.sendRedirect(referer + "?alert=" + URLEncoder.encode(addProductResult.getMessage(), "UTF-8"));
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }    
    }
    
    @Override
    public void init() throws ServletException
    {
        _productRepository = new ProductService(utx, em);
        _categoryRepository = new CategoryService(utx, em);
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
