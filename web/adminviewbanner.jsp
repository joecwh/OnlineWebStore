<%-- 
    Document   : adminviewbanner
    Created on : Jun 21, 2023, 1:37:42 PM
    Author     : Lenovo
--%>

<%@page import="enumeration.UserCode"%>
<%@page import="dto.UserDto"%>
<%@page import="java.io.Serializable"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Banner, java.util.List"%>
<jsp:include page="AdminGetBanner"/>
<%
    UserDto user = null;
    if(session.getAttribute("user") != null)
    {
        user = (UserDto) session.getAttribute("user");
    }
    else
    {
        response.sendRedirect("signin.jsp?alert=" + UserCode.SESSION_EXPIRED.getMessage());
    }
    
    Banner banner = null;    
    String bannerImg = "";
    if(session.getAttribute("banner") != null)
    {
        banner = (Banner) session.getAttribute("banner");
        Serializable imageBlob = (Serializable) banner.getBannerimg();
        byte[] imageBytes = (byte[]) imageBlob;
        bannerImg = java.util.Base64.getEncoder().encodeToString(imageBytes);
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
        <title>Banner review</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <style>
            a
            {
                text-decoration: none;
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
        </style>
    </head>
    <body>
        <jsp:include page="adminheader.html"/>
        
        <div class="notification-box <%=(alertMsg != null && !alertMsg.isEmpty()) ? "" : "hide"%> text-center">
            <span class="close-button">&times;</span>
            <p class="pt-2"><%= alertMsg %></p>
        </div>
        
        <div class="container-fluid bg-dark" style="height: 180px; z-index: 0; position: absolute;">
            <p class="text-white fs-5 fw-bold mt-4 text-center">Banner Review</p>
        </div>
        
        <div class="container" style="margin-top: 100px; position: relative; min-height: 530px;">
            <div class="row g-2 shadow" style=" background-color: white;">
                <%
                    if(banner != null){
                %>
                
                <div class="col-md-6 px-5 mb-3">
                    <a href="#" onclick="goBack()" class="btn btn-primary back-button mt-4">Back</a>

                    <h5 class="mt-5">Banner name</h5>
                    <div class="mb-5">
                        <input type="text" class="form-control py-3" id="bannername" placeholder="Banner Name" name="bannername" value="<%=banner.getBannername() %>" disabled readonly>
                    </div>
                    <h5>Description</h5>
                    <div class="form-floating mb-4">
                        <textarea class="form-control" style="height: 190px;" id="description" placeholder="description" name="description" disabled readonly><%=banner.getBannerdesc() %></textarea>
                    </div>
                </div>
                <div class="col-md-6" style="display: flex; align-content: center; justify-content: center;">
                    <img src="data:image/jpeg;base64,<%= bannerImg %>" class="my-4 shadow bg-body rounded" width="80%" height="450px" alt="Banner Image">
                </div>

                <%  } else {  %>
                
                <div class="container">
                    <div class="row justify-content-center my-5">
                        <div class="col-md-6 text-center">
                            <h3>No banner found.</h3>
                            <p>Something went wrong, please try again later.</p>
                            <a href="#" onclick="goBack()" class="btn btn-primary back-button">Back</a>
                        </div>
                    </div>
                </div>
                
                <%  } %>
            </div>
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
        
        <jsp:include page="adminfooter.html"/>
    </body>
</html>
