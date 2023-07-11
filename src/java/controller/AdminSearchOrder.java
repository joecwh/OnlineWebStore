/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Mapper.Mapper;
import Repository.IOrderShippingRepository;
import Repository.IPaymentMethodRepository;
import Repository.IProductRepository;
import Repository.ITransactionRepository;
import dto.OrderDto;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import model.Cart;
import model.Orders;
import model.PaymentMethod;
import model.Product;
import model.Result;
import model.Shipping;
import model.Transactions;
import service.OrderShippingService;
import service.PaymentMethodService;
import service.ProductService;
import service.TransactionService;

/**
 *
 * @author Lenovo
 */
public class AdminSearchOrder extends HttpServlet {

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
    private IOrderShippingRepository _orderShipppingRepository;
    private ITransactionRepository _transactionRepository;
    private IProductRepository _productRepository;
    private IPaymentMethodRepository _paymentMethodRepository;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try 
        {
            HttpSession session = request.getSession();
            String orderid = request.getParameter("orderid");
            
            if(session.getAttribute("orderDto") != null)
                session.removeAttribute("orderDto");
            
            if(session.getAttribute("paymentMethod") != null)
                session.removeAttribute("paymentMethod");
            
            Orders order = _orderShipppingRepository.GetOrder(orderid);
            List<OrderDto> orderDto = new ArrayList();
            List<PaymentMethod> pm = new ArrayList();
            if(order != null)
            {
                Transactions transaction = _transactionRepository.GetTransaction(order.getTransactionid());
                if(transaction != null)
                {
                    pm.add(_paymentMethodRepository.GetPaymentMethodById(transaction.getPaymentid()));
                }

                Shipping shipping = _orderShipppingRepository.GetShipping(order.getShippingid());

                List<Cart> carts = _transactionRepository.GetCartByTransactionId(order.getTransactionid());


                List<Product> products = new ArrayList();
                if(!carts.isEmpty())
                {
                    for(Cart c : carts)
                    {
                        Result result = _productRepository.GetProduct(c.getProductid());
                        products.add((Product) result.getResult());
                    }
                }
                OrderDto dto = new OrderDto(order, shipping, transaction, Mapper.fromCartListToCartDtoList(carts, products));
                orderDto.add(dto);
                
                session.setAttribute("orderDto", orderDto);
                session.setAttribute("paymentMethod", pm);
            }
        }
        catch(Exception ex)
        {
            System.out.println("get order fail : " + ex.getMessage());
        }
        response.sendRedirect(request.getHeader("Referer"));
    }
        
    @Override
    public void init () throws ServletException
    {
        _orderShipppingRepository = new OrderShippingService(utx, em);
        _transactionRepository = new TransactionService(utx, em);
        _productRepository = new ProductService(utx, em); // Instantiate the repository object
        _paymentMethodRepository = new PaymentMethodService(utx, em);
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
