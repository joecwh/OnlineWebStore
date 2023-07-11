<%-- 
    Document   : cart
    Created on : May 12, 2023, 12:18:08 AM
    Author     : Lenovo
--%>

<%@page import="dto.UserDto"%>
<%@page import="java.util.Formatter"%>
<%@page import="java.io.Serializable"%>
<%@page import="dto.CartDto"%>
<%@page import="model.Cart"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="VisitorCount"/>
<jsp:include page="CartServlet"/>
<%
    UserDto user = null;
    List<CartDto> carts = new ArrayList();
    List<String> productImg = new ArrayList();
    String alertMsg = "";
    double subtotal = 0.0;
    double shippingfee = 0.0;
    double taxes = 0.0;
    double totalAmount = 0.0;
    Formatter formatter = new Formatter();
    
    if(session.getAttribute("user") != null)
    {
        user = (UserDto) session.getAttribute("user");
    }
    
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
    }
    
    if(request.getParameter("alert") != null)
    {
        alertMsg = request.getParameter("alert");
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="icon" href="Images/titlelogo.png" type="image/icon type">
        <title>Cart</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
        <style>
            .notification-box {
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                background-color: #f8f8f8;
                padding: 10px;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
                z-index: 9999;
                opacity: 1;
                transition: opacity 0.5s ease-in-out;
            }

            .notification-box.hide {
                opacity: 0;
            }

            .close-button {
                float: right;
                font-size: 20px;
                font-weight: bold;
                cursor: pointer;
            }
        </style>
    </head>
    <body>
        <jsp:include page="header.jsp"/>
        
        <div class="notification-box <%=(alertMsg != null && !alertMsg.isEmpty()) ? "" : "hide"%> text-center">
            <span class="close-button">&times;</span>
            <p class="pt-2"><%= alertMsg %></p>
        </div>
        
        <div class="container my-5" style="min-height: 54vh;">
            
            <% if(user != null && !carts.isEmpty()) { %>
            
            <h1 class="text-center mb-4">Your Cart</h1>
            
            <div class="row">
                <div class="col-12">
                    <table class="table text-center align-items-center">
                        <thead>
                            <tr>
                                <th>Product</th>
                                <th>Name</th>
                                <th>Unit Price</th>
                                <th>Quantity</th>
                                <th>Total</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <%  int i = 0; 
                                for(CartDto c : carts) { 
                            %>
                            
                            <tr>
                                
                                <td class="align-middle"><img src="data:image/jpeg;base64,<%= productImg.get(i) %>" style="width: 80px; height: 80px;"></td>
                                
                                <td class="align-middle"><%=c.getProductName() %></td>
                                
                                <td class="align-middle">RM <%=String.format("%.2f", c.getProductPrice()) %></td>
                                
                                <td class="align-middle">
                                    <div class="input-group">
                                        <button class="btn border" type="button" onclick="window.location.href='CartQuantityServlet?action=minus&cartid=<%=c.getCartid() %>'" id="button-minus">-</button>
                                        <input type="text" class="form-control text-center" value="<%=c.getQuantity() %>" id="quantityInput" oninput="validateNumberInput(this, <%=c.getInstock() %>)" onchange="handleInput(this.value)">
                                        <button class="btn border" type="button" onclick="window.location.href='CartQuantityServlet?action=plus&cartid=<%=c.getCartid() %>'" id="button-plus">+</button>
                                    </div>
                                </td>
                                
                                <form method="post" action="CartHandleInput" id="handleInputForm">
                                    <input type="hidden" name="cartid" value="<%=c.getCartid() %>">
                                    <input type="hidden" id="hiddenInput" name="quantity">
                                </form>
                                
                                <td class="align-middle" id="total">RM <%=String.format("%.2f", c.getTotal()) %></td>
                                
                                <td class="align-middle">
                                    <button type="button" onclick="window.location.href='CartRemoveServlet?cartid=<%=c.getCartid() %>'" class="btn btn-sm btn-danger">Remove</button>
                                </td>
                                
                            </tr>
                            
                            <% i++; } %>
                        </tbody>
                    </table>
                </div>
            </div>
            
            <div class="row">
                <div class="col-sm-6 mb-5">
                    <button type="button" onclick="window.location.href='allproduct.jsp'" class="btn btn-primary">Continue Shopping</button>
                </div>
                <div class="col-sm-6 text-end">
                    <table class="table text-center mb-4">
                        <tr>
                            <td>Subtotal</td>
                            <td>RM <%=String.format("%.2f", subtotal) %></td>
                        </tr>
                        <tr>
                            <td>Shipping Fee</td>
                            <td>RM <%=String.format("%.2f", shippingfee) %></td>
                        </tr>
                        <tr>
                            <td>Sale Taxes (6%)</td>
                            <td>RM <%=String.format("%.2f", taxes) %></td>
                        </tr>
                        <tr>
                            <td><h4>Total</h4></td>
                            <td><h4>RM <%=String.format("%.2f", totalAmount) %></h4></td>
                        </tr>
                    </table>
                    <form method="post" action="CartCheckoutServlet">
                        <input type="hidden" name="total" value="<%=totalAmount %>"/>
                        <button type="submit" class="btn btn-success w-50">Checkout</button>
                    </form>
                </div>
            </div>
            
            <% } else { %>
            
                <div class="empty-cart-container text-center shadow" style="padding: 100px 0;">
                    <i class="bi bi-cart-x-fill empty-cart-icon"></i>
                    <h2 class="empty-cart-heading">Your cart is empty</h2>
                    <p class="empty-cart-text">There are no items in your cart.</p>
                    <button type="button" onclick="window.location.href='allproduct.jsp';" class="btn btn-primary empty-cart-link">Start Shopping</button>
                </div>
            
            <% } %>
        </div>
        
        
<!--        <div class="container my-5" style="min-height: 54vh;">
            <h1 class="text-center mb-4">No Cart Found</h1>
            <div class="row">
                <div class="col-12">
                    <p class="lead text-center">You have not added any products to your cart yet.</p>
                    <div class="text-center">
                        <a href="product.jsp" class="btn btn-primary">Start Shopping</a>
                    </div>
                </div>
            </div>
        </div>-->

        <script>
            document.querySelector('.close-button').addEventListener('click', function() {
              hideNotificationBox();
            });

            function hideNotificationBox() {
              var notificationBox = document.querySelector('.notification-box');
              notificationBox.classList.add('hide');

              setTimeout(function() {
                notificationBox.style.display = 'none';
              }, 500); // Adjust the timeout duration as needed
            }
            
            function validateNumberInput(input, instock) {
                input.value = input.value.replace(/\D/g, ''); // Remove non-digit characters
                var total = document.getElementById('total');
                var value = parseInt(input.value);
                // Set the maximum value
                var maxValue = instock; // Example: Maximum value is 100

                if (isNaN(value)) {
                  // Clear the input if it's not a valid number
                  input.value = "1";
                } else if (value > maxValue) {
                  // Limit the input value to the maximum value
                  input.value = maxValue;
                }
            }
            
            function handleInput(value) {
                document.getElementById('hiddenInput').value = value;
                document.getElementById('handleInputForm').submit();
            }
            
            // Get the URL parameters
            var searchParams = new URLSearchParams(window.location.search);
            var alertMsg = searchParams.get('alert');

            // Remove the 'alertMsg' parameter from the URL
            if (alertMsg) {
              searchParams.delete('alert');
              var newUrl = window.location.pathname + searchParams.toString();
              window.history.replaceState({}, document.title, newUrl);
            }

            setTimeout(hideNotificationBox, 5000); // 5000 milliseconds = 5 seconds
        </script>
        
        <jsp:include page="footer.html"/>
    </body>
</html>
