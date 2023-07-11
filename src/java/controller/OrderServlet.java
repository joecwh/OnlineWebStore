/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Mapper.Mapper;
import Repository.ICartRepository;
import Repository.ICreditDebitCardRepository;
import Repository.IDiscountRepository;
import Repository.IEmailRepository;
import Repository.IOrderShippingRepository;
import Repository.IPaymentMethodRepository;
import Repository.IProductRepository;
import Repository.ITransactionRepository;
import dto.CartDto;
import dto.UserDto;
import static enumeration.EmailCode.COMPANY_EMAIL;
import static enumeration.EmailCode.COMPANY_SMTP_PASSWORD;
import static enumeration.EmailCode.PURCHASE_EMAIL_SUBJECT;
import static enumeration.PaymentCode.*;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
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
import model.*;
import service.CartService;
import service.CreditDebitCardService;
import service.DiscountService;
import service.EmailService;
import service.OrderShippingService;
import service.PaymentMethodService;
import service.ProductService;
import service.TransactionService;

/**
 *
 * @author Lenovo
 */
public class OrderServlet extends HttpServlet {

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
    private ITransactionRepository _transactionRepository;
    private IPaymentMethodRepository _paymentMethodRepository;
    private IDiscountRepository _discountRepository;
    private Mapper _mapper;
    private ICartRepository _cartRepository;
    private IOrderShippingRepository _orderShippingRepository;
    private ICreditDebitCardRepository _creditDebitCardRepository;
    private IProductRepository _productRepository;
    private IEmailRepository _emailRepository;

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "order.jsp?status=" + FAILED.name();
        try
        {
            HttpSession session = request.getSession();
            List<CartDto> carts = (List<CartDto>) session.getAttribute("carts");
            String cartid = "";
            UserDto user = null;
            Card card = null;
            Email emailContent = new Email();
            String emailName = "";
            double subtotal = Double.parseDouble(request.getParameter("subtotal"));
            double shippingfee = Double.parseDouble(request.getParameter("shippingfee"));
            double taxes = Double.parseDouble(request.getParameter("taxes"));
            double total = Double.parseDouble(request.getParameter("total"));
            String coupon = request.getParameter("coupon");
            if(coupon != null || !coupon.equals(""))
            {
                Discount discount = _discountRepository.GetDiscountByCode(coupon);
                session.setAttribute("discount", discount);
            }
            
            if(session.getAttribute("user") != null)
            {
                user = (UserDto) session.getAttribute("user");
            }
            
            for(CartDto c : carts)
            {
                cartid += c.getCartid() + ",";
            }
            cartid = cartid.substring(0, cartid.length() - 1);

            String paymentid = "";
            String paymentMethod = request.getParameter("paymentMethod");
            if(paymentMethod.equals("cash"))
            {
                PaymentMethod getPayid = _paymentMethodRepository.GetCashPaymentMethod();
                if(getPayid != null)
                    session.setAttribute("paymentMethod", getPayid);
                paymentid = getPayid.getPaymentid();
            }
            else if(paymentMethod.equals("onlinebanking"))
            {
                String payid = request.getParameter("onlineBankingSelection");
                if(payid != null || payid.equals(""))
                    paymentid = payid;
                PaymentMethod getPayid = _paymentMethodRepository.GetPaymentMethodById(payid);
                if(getPayid != null)
                    session.setAttribute("paymentMethod", getPayid);

            }
            else if(paymentMethod.equals("card"))
            {
                String fullname = request.getParameter("cardOwner");
                String cardnumber = request.getParameter("cardNumber");
                String expirydate = request.getParameter("cardExpiryDate");
                Date date = java.sql.Date.valueOf(expirydate);
                int cvc = Integer.parseInt(request.getParameter("cardSecurityCode"));

                card = new Card((user != null) ? user.getUserid() : "", fullname, cardnumber, date, cvc);
                PaymentMethod getPayid = _paymentMethodRepository.GetCardPaymentMethod();
                if(getPayid != null)
                    session.setAttribute("paymentMethod", getPayid);
                paymentid = getPayid.getPaymentid();
            }
            Transactions transaction = new Transactions(cartid, paymentid, (user != null) ? user.getUserid() : "", shippingfee, taxes, subtotal, total, coupon);

            String addressoption = request.getParameter("addressOption");
            Shipping shipping = null;
            Calendar calendar = Calendar.getInstance();
            Date today = (Date) calendar.getTime();
            calendar.add(Calendar.DAY_OF_MONTH, 5);
            Date expectedReachDate = (Date) calendar.getTime();
            if(addressoption.equals("newaddress"))
            {
                String fullname = request.getParameter("fullName");
                String email = request.getParameter("email");
                String contact = request.getParameter("contact");
                String address = request.getParameter("address");
                
                shipping = new Shipping(fullname, email, contact, address, expectedReachDate);
                emailContent.setSubject(PURCHASE_EMAIL_SUBJECT.getMessage());
                emailName = fullname;
                emailContent.setRecipient(email);
            }
            else
            {
                if(user != null)
                {
                    shipping = new Shipping(user.getFullname(), user.getEmail(), user.getContact(), user.getAddress(), expectedReachDate);
                    emailContent.setSubject(PURCHASE_EMAIL_SUBJECT.getMessage());
                    emailName = user.getFullname();
                    emailContent.setRecipient(user.getEmail());
                }
            }
            session.setAttribute("shipping", shipping);

            Orders order = new Orders(transaction.getTransactionid(), shipping.getShippingid(), (user != null) ? user.getUserid() : "");
            emailContent.setMessage(order.getOrderid());
            List<Cart> cartList = Mapper.fromCartDtoListToCartList(carts);
            List<Product> productList = Mapper.fromCartDtoListToProductList(carts);
            int count = 0;
            for(Cart c : cartList)
            {
                productList.get(count).setQuantity(productList.get(count).getQuantity() - c.getQuantity());
                count++;
            }
            
            boolean addTransaction = _transactionRepository.AddTransaction(transaction);
            boolean addShipping = _orderShippingRepository.AddShipping(shipping);
            boolean addOrder = _orderShippingRepository.AddOrder(order);
            boolean updateProduct = _productRepository.UpdateProductList(productList);
            boolean addCard = false;
            boolean upSertCart = false;
            
            if(!_cartRepository.CartExist(carts.get(0).getCartid()))
            {
                for(Cart c : cartList)
                {
                    c.setIspaid(true);
                    upSertCart = _cartRepository.AddCart(c);
                }
            }
            else
            {
                for(Cart c : cartList)
                {
                    c.setIspaid(true);
                    upSertCart = _cartRepository.UpdateCart(c);
                }
            }
            
            if(user != null && card != null)
                addCard = _creditDebitCardRepository.AddCard(card);
            
            if(addTransaction && addShipping && addOrder && upSertCart && updateProduct)
            {
                String contextPath = getServletContext().getRealPath("");
                File contextDir = new File(contextPath).getParentFile().getParentFile();
                String htmlTemplate = contextDir.getPath() + "\\web\\EmailTemplates\\purchaseSuccess_template.html";
                
                boolean sendEmail = _emailRepository.SendPurchaseEmail(emailContent, emailName, htmlTemplate);
                if(sendEmail)
                    url = "order.jsp?status=" + SUCCESS.name() + "&orderid=" + order.getOrderid();
            }
        }
        catch(Exception ex)
        {
            System.out.println("Order fail : " + ex.getMessage());
        }
        response.sendRedirect(url);
    }
    
    @Override
    public void init () throws ServletException
    {
        _transactionRepository = new TransactionService(utx, em);
        _paymentMethodRepository = new PaymentMethodService(utx, em);
        _discountRepository = new DiscountService(utx, em);
        _cartRepository = new CartService(utx, em);
        _orderShippingRepository = new OrderShippingService(utx, em);
        _creditDebitCardRepository = new CreditDebitCardService(utx, em);
        _productRepository = new ProductService(utx, em);
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
