<%-- 
    Document   : verify
    Created on : Jul 3, 2023, 9:31:55 PM
    Author     : Lenovo
--%>

<%@page import="enumeration.AccountCode.*"%>
<%@page import="enumeration.AccountCode"%>
<%@page import="enumeration.AccountCode.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dto.UserDto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="UserVerify"/>
<%
    String status = "";
    if(session.getAttribute("status") != null)
    {
        status = (String) session.getAttribute("status");
        session.removeAttribute("status");
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
        <title>Verify</title>
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

        <div class="container mt-5" style="min-height: 61vh;">
            <%-- Success Message --%>
            <% if (status.equals(AccountCode.SUCCESS.name())) { %>
                <div class="alert alert-success text-center" role="alert">
                    <h4 class="alert-heading">Account Verified Successfully!</h4>
                    <p>Your account has been successfully verified. You can now access all the features of our platform.</p>
                    <hr>
                    <p class="">Thank you for verifying your account.</p>
                    <p class="mb-0"><i>Login to your account now.</i></p>
                </div>
            <% } else { %>

            <%-- Error Message --%>
                <div class="alert alert-danger text-center" role="alert">
                    <h4 class="alert-heading">Account Verification Failed!</h4>
                    <p>Sorry, we couldn't verify your account. Please try again or contact our support team for assistance.</p>
                    <hr>
                    <p class="mb-0">Thank you.</p>
                </div>
            <% } %>
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
