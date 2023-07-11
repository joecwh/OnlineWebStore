<%-- 
    Document   : home
    Created on : May 11, 2023, 4:15:40 PM
    Author     : Lenovo
--%>

<%@page import="dto.UserDto"%>
<%@page import="java.io.Serializable"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Categories, model.Banner, java.util.List" %>
<jsp:include page="VisitorCount"/>
<jsp:include page="InitialServlet"/>
<%
    List<Banner> banners = new ArrayList();
    List<String> bannerImg = new ArrayList();
    if(session.getAttribute("banners") != null)
    {
        banners = (List<Banner>) session.getAttribute("banners");
        for(Banner b : banners)
        {
            Serializable imageBlob = (Serializable) b.getBannerimg();
            byte[] imageBytes = (byte[]) imageBlob;
            bannerImg.add(java.util.Base64.getEncoder().encodeToString(imageBytes));
        }
    }
    
    List<Product> products = new ArrayList();
    List<String> productImg = new ArrayList();
    if(session.getAttribute("products") != null)
    {
        products = (List<Product>) session.getAttribute("products");
        for(Product p : products)
        {
            Serializable imageBlob = (Serializable) p.getProductimg();
            byte[] imageBytes = (byte[]) imageBlob;
            productImg.add(java.util.Base64.getEncoder().encodeToString(imageBytes));
        }
    }
    
    List<Categories> categories = new ArrayList();
    if(session.getAttribute("categories") != null)
    {
        categories = (List<Categories>) session.getAttribute("categories");
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
        <title>Den's Toy Enterprise</title>
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
            
            .category-box {
              background-color: #f8f9fa;
              border-radius: 10px;
              padding: 20px;
            }

            .category-title {
              font-size: 24px;
              font-weight: bold;
              color: #333;
            }

            .category-description {
              font-size: 16px;
              color: #777;
              margin-top: 10px;
            }
            
            .overflow-hidden {
                height: 92px;
                overflow: hidden;
                display: -webkit-box;
                -webkit-line-clamp: 3; /* Adjust the desired number of lines */
                -webkit-box-orient: vertical;
                word-wrap: break-word;
            }
            
            #banner-container {
                width: 100%;
                overflow: hidden;
            }

            .banner-text {
                animation: slide-banner 10s linear infinite;
                white-space: nowrap;
            }

            @keyframes slide-banner {
                0% {
                    transform: translateX(100%);
                }
                100% {
                    transform: translateX(-100%);
                }
            }
        </style>
        <script>
            // Activate the carousel on page load
            window.addEventListener('load', function() {
                var bannerCarousel = document.getElementById('banner-carousel');
                var carousel = new bootstrap.Carousel(bannerCarousel, {
                    interval: 5000 // Adjust the interval as needed (in milliseconds)
                });
            });
        </script>
    </head>
    <body>
        <jsp:include page="header.jsp"/>
        
        <div class="notification-box <%=(alertMsg != null && !alertMsg.isEmpty()) ? "" : "hide"%> text-center">
            <span class="close-button">&times;</span>
            <p class="pt-2"><%= alertMsg %></p>
        </div>
        
        <div class="container-fluid">
        <div id="carouselExampleCaptions" class="carousel slide" data-bs-ride="carousel">
            <div class="carousel-indicators">
                <%  if(!banners.isEmpty()) { 
                        int i = 0;
                        for(Banner b : banners){
                %>
                <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="<%=i %>" <% if (i == 0) { %>class="active" aria-current="true"<% } %> aria-label="Slide <%= i + 1 %>"></button>
                   
                <% i++; } } %>
            </div>
            <div class="carousel-inner">
                <%  if(!banners.isEmpty()) { 
                        int i = 0;
                        for(Banner b : banners){
                %>
                
                    <div class="carousel-item <% if (i == 0) { %>active<% } %>">
                        <img src="data:image/jpeg;base64,<%= bannerImg.get(i) %>" class="d-block" alt="..." style="background-color: lightgray; width: 100%; height: 500px;">
                        <div class="carousel-caption d-none d-md-block">
                            <h5><%=b.getBannername() %></h5>
                            <p><%=b.getBannerdesc() %></p>
                        </div>
                    </div>
                    
                <%  i++; } } else { %>
                
                    <div class="carousel-item active">
                        <img src="" class="d-block" alt="..." style="background-color: lightgray; width: 100%; height: 500px;">
                        <div class="carousel-caption d-none d-md-block">
                            <h5 class="card-title">Card title</h5>
                            <p>Some representative placeholder content for the first slide.</p>
                        </div>
                    </div>
                
                <% } %>
            </div>
                
            <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </div>
        </div>
            
        <div id="banner-container" class="container-fluid overflow-hidden">
            <div id="banner-carousel" class="carousel slide" data-bs-ride="carousel">
                <div class="carousel-inner">
                    <div class="carousel-item active bg-danger">
                        <h4 class="banner-text text-white">LIFE TIME PROMOTION - FREE SHIPPING FEE OVER RM 200 IN ONE TRANSACTION - EAST / WEST MALAYSIA</h4>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="container-fluid" style="margin-bottom: 30px;">
            <div class="row">
                <% if(!categories.isEmpty()) { 
                    for(Categories c : categories) { 
                %>
                    
                    <div class="col-md-4 mb-3">
                        <div class="category-box">
                            <h2 class="category-title"><%=c.getCategoryname() %></h2>
                            <p class="category-description overflow-hidden fst-italic"><%=c.getCategorydesc() %></p>
                            <button onclick="window.location.href='category.jsp?category=<%=c.getCategoryname() %>&categoriesid=<%=c.getCategoriesid() %>'" class="btn btn-primary">View Category</a>
                        </div>
                    </div>
                
                <%  } } else { %>
                
                    <div class="col-md-4 mb-3">
                        <div class="category-box">
                            <h2 class="category-title">Category Name</h2>
                            <p class="category-description">This is the description of the category. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla tempor ipsum ac ullamcorper fermentum. Morbi mattis, enim vel tempus tincidunt, sapien nisi placerat mi, ac ullamcorper elit risus id purus.</p>
                            <button type='button' class="btn btn-primary">View Category</button>
                        </div>
                    </div>
                
                <% } %>
            </div>
        </div>
            
        <jsp:include page="footer.html"/>
        
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
    </body>
</html>
