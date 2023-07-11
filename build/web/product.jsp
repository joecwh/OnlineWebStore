<%-- 
    Document   : product
    Created on : Jun 27, 2023, 12:11:13 AM
    Author     : Lenovo
--%>

<%@page import="dto.UserDto"%>
<%@page import="java.io.Serializable"%>
<%@page import="model.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="VisitorCount"/>
<jsp:include page="GetProduct"/>
<%
    String alertMsg = "";
    if(request.getParameter("alert") != null)
    {
        alertMsg = request.getParameter("alert");
    }
    
    UserDto user = new UserDto();
    if(session.getAttribute("user") != null)
    {
        user = (UserDto) session.getAttribute("user");
    }
    
    Product product = new Product();
    String productImg = "";
    if(session.getAttribute("product") != null)
    {
        product = (Product) session.getAttribute("product");
        Serializable imageBlob = (Serializable) product.getProductimg();
        byte[] imageBytes = (byte[]) imageBlob;
        productImg = java.util.Base64.getEncoder().encodeToString(imageBytes);
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="icon" href="Images/titlelogo.png" type="image/icon type">
        <title>Product</title>
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
        
        <% if(session.getAttribute("product") != null) { %>
        <div class="container my-5" style="min-height: 54vh;">
            <button onclick="window.location.href='allproduct.jsp'" class="btn btn-primary back-button m-2" style="position: absolute;">Back</button>

            <div class="row shadow my-3">
                
                <div class="col-md-7 my-5 d-flex align-items-center justify-content-center">
                    <img src="data:image/jpeg;base64,<%=productImg %>" class="img-fluid" style="width: 450px; height: 450px;" alt="Product Image">
                </div>
                
                <div class="col-md-5 d-flex align-items-center justify-content-center">
                    <form class="container shadow p-4 text-center" style="" method="post" action="CartAddServlet">

                        <input type="hidden" name="productid" value="<%=product.getProductid() %>">
                        
                        <h2 class="p-3"><%=product.getProductname() %></h2>

                        <p class="bg-light p-3 rounded" style="overflow-wrap: break-word;"><%=product.getProductdesc() %></p>

                        <div class="mb-3 row px-3 align-items-center justify-content-center">
                            <label for="price" class="col-sm-5 col-form-label">Price : </label>
                            <div class="col-sm-7 d-flex justify-content-center">
                                <input type="text" class="form-control text-center w-50" id="price" value="RM <%=String.format("%.2f", product.getPrice()) %>">
                            </div>
                        </div>

                        <div class="mb-3 row align-items-center justify-content-center px-3">
                            <label for="quantity" class="col-sm-5 col-form-label">Quantity : </label>
                            <div class="col-sm-7 d-flex justify-content-center">
                              <button class="btn border" type="button" id="minusButton">-</button>
                              <input type="text" class="text-center border w-25"  value="1" id="quantityInput" name="quantity" oninput="validateNumberInput(this)">
                              <button class="btn border" type="button" id="plusButton">+</button>
                            </div>
                        </div>

                        <div class="mb-3 row px-3 align-items-center justify-content-center">
                            <label for="price" class="col-sm-5 col-form-label">Total Amount : </label>
                            <div class="col-sm-7 d-flex justify-content-center">
                                <input type="text" class="form-control text-center w-50" id="total" name="total" value="RM <%=String.format("%.2f", product.getPrice()) %>">
                            </div>
                        </div>

                        <p class="px-3 mb-4"><%=product.getQuantity() %> stock(s) remaining</p>

                        <div class="px-3">
                            <button type="submit" class="btn btn-danger" style="width: 150px;" id="addToCartButton" >Add to Cart</button>
                            <button type="button" class="btn btn-danger" style="width: 150px;" onclick="window.location.href='BuyNowServlet?productid=<%=product.getProductid() %>&userid=<%=user.getUserid() %>&quantity=' + document.getElementById('quantityInput').value" id="addToCartButton">Buy Now</button>
                        </div>
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
            
            // Quantity buttons functionality
            var quantityInput = document.getElementById('quantityInput');
            var minusButton = document.getElementById('minusButton');
            var plusButton = document.getElementById('plusButton');
            var total = document.getElementById('total');
            var price = <%=product.getPrice() %>;
            updateTotal();
            
            minusButton.addEventListener('click', function() {
                var quantity = parseInt(quantityInput.value) || 0;
                if (quantity > 1) 
                {
                    quantityInput.value = quantity - 1;
                    updateTotal();
                }
            });

            plusButton.addEventListener('click', function() {
                var quantity = parseInt(quantityInput.value) || 0;
                var maxValue = <%=product.getQuantity() %>;
                if(quantity < maxValue)
                {
                    quantityInput.value = quantity + 1;
                    updateTotal();
                }
            });
            
            function updateTotal() {
                var quantity = parseInt(quantityInput.value) || 0;
                var amount = price * quantity;
                total.value = "RM " + amount.toFixed(2);
            }

            // Add to Cart button functionality
            var addToCartButton = document.getElementById('addToCartButton');
            addToCartButton.addEventListener('click', function() {
              var quantity = parseInt(quantityInput.value) || 0;
              // Add the product to the cart with the selected quantity
              // ...
            });

            function validateNumberInput(input) {
                input.value = input.value.replace(/\D/g, ''); // Remove non-digit characters
                var value = parseInt(input.value);
                // Set the maximum value
                var maxValue = <%=product.getQuantity() %>; // Example: Maximum value is 100

                if (isNaN(value)) {
                  // Clear the input if it's not a valid number
                  input.value = "1";
                } else if (value > maxValue) {
                  // Limit the input value to the maximum value
                  input.value = maxValue;
                }
                
                updateTotal();
            }
            
            // Get the URL parameters
            var searchParams = new URLSearchParams(window.location.search);
            var alertMsg = searchParams.get('alert');

            // Remove the 'alertMsg' parameter from the URL
            if (alertMsg) {
              searchParams.delete('alert');
              var newUrl = window.location.pathname + "?" + searchParams.toString();
              window.history.replaceState({}, document.title, newUrl);
            }

            setTimeout(hideNotificationBox, 5000); // 5000 milliseconds = 5 seconds
        </script>
        
        <jsp:include page="footer.html"/>
    </body>
</html>
