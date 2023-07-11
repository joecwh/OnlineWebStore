<%-- 
    Document   : category
    Created on : Jun 29, 2023, 8:07:04 PM
    Author     : Lenovo
--%>

<%@page import="dto.UserDto"%>
<%@page import="java.io.Serializable"%>
<%@page import="java.util.List"%>
<%@page import="model.Product"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="VisitorCount"/>
<jsp:include page="CategoryServlet"/>
<%
    List<Product> products = new ArrayList();
    List<String> productImg = new ArrayList();
    UserDto user = new UserDto();
    String category = "";

    if(session.getAttribute("prodCat") != null)
    {
        products = (List<Product>) session.getAttribute("prodCat");
        for(Product p : products)
        {
            Serializable imageBlob = (Serializable) p.getProductimg();
            byte[] imageBytes = (byte[]) imageBlob;
            productImg.add(java.util.Base64.getEncoder().encodeToString(imageBytes));
        }
    }
    
    if(request.getParameter("category") != null)
    {
        category = request.getParameter("category");
    }
    
    if(session.getAttribute("user") != null)
    {
        user = (UserDto) session.getAttribute("user");
    }
    
    String alertMsg = "";
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
        <title>Product</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
        <style>
            .product-card {
              border: 1px solid #ddd;
              border-radius: 8px;
              padding: 20px;
              margin-bottom: 20px;
              box-shadow: 0 2px 6px rgba(0, 0, 0, 0.15);
            }

            .product-card img {
              max-width: 100%;
              height: auto;
              margin-bottom: 20px;
              border-radius: 8px;
            }

            .product-card h3 {
              font-size: 24px;
              margin-bottom: 10px;
            }

            .product-card p {
              font-size: 16px;
              color: #666;
              margin-bottom: 20px;
            }

            .product-card .price {
              font-size: 18px;
              font-weight: bold;
              color: #333;
              margin-bottom: 20px;
            }

            
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
            
            .overflow-hidden {
                height: 92px;
                overflow: hidden;
                display: -webkit-box;
                -webkit-line-clamp: 3; /* Adjust the desired number of lines */
                -webkit-box-orient: vertical;
                word-wrap: break-word;
            }
            
/*            .button-container {
                display: flex;
                justify-content: space-between;
                width: 100%;
            }*/
        </style>
    </head>
    <body>
        <jsp:include page="header.jsp"/>
        
        <div class="notification-box <%=(alertMsg != null && !alertMsg.isEmpty()) ? "" : "hide"%> text-center">
            <span class="close-button">&times;</span>
            <p class="pt-2"><%= alertMsg %></p>
        </div>
        
        <div class="container text-center" style="min-height: 508px;">

        <% if(!products.isEmpty()) { %>
        
        <div class="container" style="min-height: 54vh;">
                <h3 class="text-center my-5" style="word-spacing: 4px;"><%=category %> Category</h3>
                <div class="row">

                <%  int i = 0;
                    for(Product p : products) { 
                    if(p.getQuantity() <= 0) 
                        i++;
                    else {
                %>

                    <div class="col-md-4">
                        <div class="product-card text-center">
                            <a href='product.jsp?productid=<%=p.getProductid() %>'>
                                <img src="data:image/jpeg;base64,<%= productImg.get(i) %>" alt="Product 1" class="img-fluid" style="height: 300px; width: 100%;">
                            </a>
                            <h3><%=p.getProductname() %></h3>
                            <p class="overflow-hidden desc bg-light p-3 rounded fst-italic"><%=p.getProductdesc() %></p>
                            <p class="price">RM<%=String.format("%.2f", p.getPrice()) %></p>
                            
                            <div class="button-container row">
                                <form method="post" action="CartAddServlet" class="col-sm-6">
                                    <input type="hidden" name="productid" value="<%=p.getProductid() %>">
                                    <input type="hidden" name="quantity" value="1">
                                    <input type="hidden" name="total" value="<%=p.getPrice() %>">
                                    <button type="submit" class="btn btn-primary" style="width: 100%;">Add to Cart</button>
                                </form>
                                <form method="post" action="BuyNowServlet?productid=<%=p.getProductid() %>&userid=<%=user.getUserid() %>&quantity=1" class="col-sm-6">
                                    <button type="submit" class="btn btn-primary" style="width: 100%;">Buy Now</button>
                                </form>
                            </div>
                        </div>
                    </div>

                <% i++; } } %>

                </div>
            </div>

        <% } else { %>

        <div class="">
            <h1 class="mt-5 mb-3"><%=category %> Category</h1>

            <div class="alert alert-info">
                <p>Currently no product in this category. Please try again later.</p>
            </div>

            <button onclick="goBack()" class="btn btn-primary">Back</a>
        </div>
        
        <% } %>
        
        </div>
        
        <script>
            function goBack() {
                window.history.back();
            }
            
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
