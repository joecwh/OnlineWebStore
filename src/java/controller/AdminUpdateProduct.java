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
import javax.servlet.http.HttpSession;
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
public class AdminUpdateProduct extends HttpServlet {

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
            HttpSession session = request.getSession();
            String productid = request.getParameter("productid");
            String productname = request.getParameter("productname");
            String productdesc = request.getParameter("description");
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
            Part filePart = request.getPart("newproductimg");

            Product productExists = _productRepository.GetProduct(productid).getResult();
            Product product = new Product();
            if(productExists != null)
            {
                product = new Product(productid, productname, productdesc, price, quantity, productExists.getProductimg());
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
                    byte[] newproductimg = buffer.toByteArray();
                    product.setProductimg(newproductimg);
                }

                Category category = _categoryRepository.GetCategoryByProduct(productid).getResult();
                category.setCategoriesid(categories);

                Result updateProductResult = _productRepository.UpdateProduct(product);
                Result updateCategoryResult = _categoryRepository.UpdateCategory(category);
                if(updateProductResult.getStatus() && updateCategoryResult.getStatus())
                {
                    session.removeAttribute("products");
                    session.removeAttribute("admincategory");
                    session.removeAttribute("categories");
                }

                String referer = request.getHeader("Referer");
                response.sendRedirect(referer + "&alert=" + URLEncoder.encode(updateProductResult.getMessage(), "UTF-8"));
            }
        }
        catch(Exception ex)
        {
            System.out.println("update product failed " + ex.getMessage());
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
