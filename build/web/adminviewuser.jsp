<%-- 
    Document   : adminviewstaff
    Created on : Jun 22, 2023, 8:06:59 PM
    Author     : Lenovo
--%>

<%@page import="enumeration.UserCode"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="dto.UserDto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="AdminGetStaff"/>
<%
    UserDto user = null;    
    String dateString = "dd/mm/yyyy";
    String userRole = "";
    if(session.getAttribute("userreview") != null)
    {
        user = (UserDto) session.getAttribute("userreview");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if(user.getDob() != null)
            dateString = dateFormat.format(user.getDob());
    }
    
    String alertMsg = "";
    if(request.getParameter("alert") != null)
    {
        alertMsg = request.getParameter("alert");
    }
    
    if(request.getParameter("select") != null)
        userRole = request.getParameter("select");
    
    UserDto userCredential = null;
    if(session.getAttribute("user") != null)
    {
        userCredential = (UserDto) session.getAttribute("user");
    }
    else
    {
        response.sendRedirect("signin.jsp?alert=" + UserCode.SESSION_EXPIRED.getMessage());
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="icon" href="Images/titlelogo.png" type="image/icon type">
        <title><%=userRole %> Review</title>
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
            <p class="text-white fs-5 fw-bold mt-4 text-center"><%=userRole %> Review</p>
        </div>
        
        <div class="container shadow mb-3" style="margin-top: 100px; position: relative; min-height: 530px; background-color: white;">
            <% if(user != null){ %>
            
                <form class="mx-auto px-3 py-3">
                    <a href="#" onclick="goBack()" class="btn btn-primary back-button my-4">Back</a>
                    <div class="form-floating mb-4">
                        <h6>User ID</h6>
                        <input type="text" class="form-control py-3" id="userid" placeholder="userid" name="userid" value="<%=user.getUserid()%>"readonly disabled/>
                    </div>
                    <div class="form-floating mb-4">
                        <h6>Username</h6>
                        <input type="text" class="form-control py-3" id="username" placeholder="username" name="username" value="<%=user.getUsername() %>" readonly disabled/>
                    </div>
                    <div class="form-floating mb-4">
                        <h6>Full Name</h6>
                        <input type="text" class="form-control py-3" id="fullname" placeholder="fullname" name="fullname" value="<%=user.getFullname() %>" readonly disabled/>
                    </div>
                    <div class="form-floating mb-4">
                        <h6>Email</h6>
                        <input type="email" class="form-control py-3" id="email" placeholder="Email" name="email" value="<%=user.getEmail() %>" readonly disabled/>
                    </div>
                    <div class="form-floating mb-4">
                        <h6>Contact</h6>
                        <input type="text" class="form-control py-3" id="contact" placeholder="Contact" name="contact" value="<%=user.getContact() %>" readonly disabled/>
                    </div>
                    <div class="form-floating mb-4">
                        <h6>Date of Birth</h6>
                        <input type="text" class="form-control py-3" id="dob" placeholder="Date of Birth" name="dob" value="<%=dateString %>" readonly disabled/>
                    </div>
                    <div class="form-floating mb-4">
                        <h6>Address</h6>
                        <input type="text" class="form-control py-3" id="dob" placeholder="Date of Birth" name="dob" value="<%=user.getAddress() %>" readonly disabled/>
                    </div>
                    <div class="form-floating mb-4">
                        <h6>Account Status</h6>
                        <input type="text" class="form-control py-3" id="accountstatus" placeholder="accountstatus" name="accountstatus" value="<%=user.getAccountstatus().substring(user.getAccountstatus().lastIndexOf("_") + 1) %>" readonly disabled />
                    </div>
                </form>
                    
            <% } else { %>
            
                <div class="container">
                    <div class="row justify-content-center my-5">
                        <div class="col-md-6 text-center">
                            <h3>No staff found.</h3>
                            <p>Something went wrong, please try again later.</p>
                            <a href="adminstaffmanagement.jsp" class="btn btn-primary back-button">Back</a>
                        </div>
                    </div>
                </div>
                
            <%  } %>
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
              var newUrl = window.location.pathname + searchParams.toString();
              window.history.replaceState({}, document.title, newUrl);
            }

            setTimeout(hideNotificationBox, 5000); // 5000 milliseconds = 5 seconds
        </script>
        
        <jsp:include page="adminfooter.html"/>
    </body>
</html>
