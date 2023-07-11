<%-- 
    Document   : order
    Created on : Jun 29, 2023, 2:33:14 PM
    Author     : Lenovo
--%>

<%@page import="model.Shipping"%>
<%@page import="model.PaymentMethod"%>
<%@page import="model.Discount"%>
<%@page import="java.io.Serializable"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="dto.CartDto"%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="dto.UserDto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    UserDto user = null;
    if(session.getAttribute("user") != null)
    {
        user = (UserDto) session.getAttribute("user");
    }
    
    Discount discount = null;
    if(session.getAttribute("discount") != null)
    {
        discount = (Discount) session.getAttribute("discount");
        session.removeAttribute("discount");
    }
    
    Shipping shipping = null;
    if(session.getAttribute("shipping") != null)
    {
        shipping = (Shipping) session.getAttribute("shipping");
        session.removeAttribute("shipping");
    }
    
    PaymentMethod paymentMethod = null;
    if(session.getAttribute("paymentMethod") != null)
    {
        paymentMethod = (PaymentMethod) session.getAttribute("paymentMethod");
        session.removeAttribute("paymentMethod");
    }
    
    List<CartDto> carts = new ArrayList();
    List<String> productImg = new ArrayList();
    double subtotal = 0.0;
    double shippingfee = 0.0;
    double taxes = 0.0;
    double totalAmount = 0.0;
    if(session.getAttribute("carts") != null)
    {
        carts = (List<CartDto>) session.getAttribute("carts");
        for(CartDto c : carts)
        {
            Serializable imageBlob = (Serializable) c.getProductImg();
            byte[] imageBytes = (byte[]) imageBlob;
            productImg.add(java.util.Base64.getEncoder().encodeToString(imageBytes));
            
            subtotal += c.getTotal();
        }
        if(subtotal < 200)
            shippingfee = 25.00;
        
        taxes = (subtotal + shippingfee) * 0.06;
        totalAmount = subtotal + shippingfee + taxes;
        
        if(discount != null)
        {
            double percent = (100 - discount.getPercent()) / 100;
            totalAmount *= percent;
        }
        session.removeAttribute("carts");
    }
    
    String status = "";
    if(request.getParameter("status") != null)
    {
        status = request.getParameter("status");
    }
    String orderid = "";
    if(request.getParameter("orderid") != null)
    {
        orderid = request.getParameter("orderid");
    }
    
    
    
    String text = "This is a sample text, with some, commas, that will cause line breaks.";
    StringTokenizer tokenizer = new StringTokenizer(text, ",");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="icon" href="Images/titlelogo.png" type="image/icon type">
        <title>Order</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <style>
            .invoice-card {
                width: 60%;
            }
            
            @media (max-width: 1000px) {
                .invoice-card {
                    width: 100%;
            }
        </style>
    </head>
    <body>
        <jsp:include page="header.jsp"/>

        <div class="container">
            <div class="invoice-card shadow my-4 border mx-auto p-3">
                
                <% if(status.equals("SUCCESS")) { %>
                
                <h3 class="text-center my-2">Thank You!</h3>
                <p class="text-center mt-3 mb-3">You'll receive a confirmation email soon.</p>
                <p class="text-center mb-4">Order number : <%=orderid %></p>
                
                <% } else { %>
                
                <h3 class="text-center my-2">Transaction Failed</h3>
                <p class="text-center mt-3 mb-3">Unfortunately, your recent transaction has failed due to a declined payment.</p>
                <p class="text-center mb-4">Check details and contact support for assistance.</p>
                
                <% } %>

                <% if(carts != null) { %>
                
                <div class="mb-4 p-3 border" style="">

                    <div class="mb-3">
                        <table class="table">
                            <tbody>
                                
                                <%  int i = 0; 
                                    for(CartDto c : carts) { 
                                %>
                                
                                <tr class="">
                                    <td style="width: 50%;">
                                        <img src="data:image/jpeg;base64,<%= productImg.get(i) %>" class="border me-2" alt="Product Image" style="width: 50px; height: 50px;"/>
                                        <span class="overflow-hidden" style=""><%=c.getProductName() %></span>
                                    </td>
                                    <td style="width: 25%;" class="text-end text-secondary">Qty :  <%=c.getQuantity() %></td>
                                    <td style="width: 25%;" class="text-end text-secondary">RM <%=String.format("%.2f", c.getTotal()) %></td>
                                </tr>
                                
                                <% i++; } %>
                                
                            </tbody>
                        </table>
                    </div>
                    
                    <div class="mb-2 row pe-3">
                        <div class='col'></div>
                        <table class="col table w-50">
                            <tbody>
                                <tr class='text-secondary'>
                                    <td class=''>Subtotal</td>
                                    <td class="text-end">RM <%=String.format("%.2f", subtotal) %></td>
                                </tr>
                                <tr class='text-secondary'>
                                    <td>Shipping Fee</td>
                                    <td class="text-end">RM <%=String.format("%.2f", shippingfee) %></td>
                                </tr>
                                <tr class='text-secondary'>
                                    <td>Sales Tax</td>
                                    <td class="text-end">RM <%=String.format("%.2f", taxes) %></td>
                                </tr>
                                <% if(discount != null) { %>
                                
                                <tr class='text-secondary'>
                                    <td>Discount</td>
                                    <td class="text-end"><%=String.format("%.2f", discount.getPercent()) %>%</td>
                                </tr>
                                
                                <% } %>
                                <tr>
                                    <td><h5>Total</h5></td>
                                    <td class="text-end"><h5>RM <%=String.format("%.2f", totalAmount) %></h5></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                  
                </div>
                
                <% } %>


                <div class='border p-3 row g-2 mx-0'>
                    <div class='col-sm-6 border-bottom'>
                        <h6 class='mb-3'>Shipping Address</h6>
                        <% if(shipping != null) { %>
                        
                        <p style='width: 80%;'><%=shipping.getReceivername() %><br>
                            <span><%=shipping.getShippingaddress() %></span>
                        </p>
                        
                        <% } %>
                    </div>
                    
                    <div class='col-sm-6 border-bottom'>
                        <h6 class='mb-3'>Payment method</h6>
                        <% if(paymentMethod != null) { %>
                        <p style='width: 80%;'><%=paymentMethod.getMethod().replace("_", " ").toLowerCase() %> : <span><%=paymentMethod.getName().toLowerCase() %></span></p>
                        <% } %>
                    </div>
                </div>
                
                <div class='text-center my-3'>
                    <button onclick="window.location.href='allproduct.jsp'" class='btn btn-primary text-decoration-none'>Continue Shopping </button>
                </div>
            </div>
        </div>
            
        <jsp:include page="footer.html"/>
    </body>
</html>
