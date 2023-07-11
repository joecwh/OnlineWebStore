<%-- 
    Document   : profile
    Created on : May 12, 2023, 12:16:15 AM
    Author     : Lenovo
--%>

<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="dto.UserDto"%>
<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="VisitorCount"/>
<%
    UserDto user = new UserDto();
    String formattedDate = "";
    String dateString = "";
    
    if(session.getAttribute("user") != null)
    {
        user = (UserDto) session.getAttribute("user");
        if(user.getDob() != null)
        {
    //        SimpleDateFormat inputFormat = new SimpleDateFormat("E MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat outputFormat = new SimpleDateFormat("MMMM d, yyyy", new Locale("en"));
            formattedDate = outputFormat.format(inputFormat.parse(inputFormat.format(user.getDob())));

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateString = dateFormat.format(user.getDob());
        }
    }
    else 
    {
        response.sendRedirect("signin.jsp");
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
        <title>Profile</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
        <script>
            function redirectToEditPasswordLink() {
                var link = "https://example.com";
                window.location.href = link;
            }
        </script>
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
        
        <% if(user != null) { %>
        
        <div class="container my-5">
            <div class="row">
                <div class="col-lg-3 col-md-4 col-sm-12 mb-5">
                    <div class="card">
                        <div class="card-body">
                            <h6 class="card-title rounded p-3" style="background-color: #EFEFEF;">ID : <i class="fw-light"><%=user.getUserid()%></i></h6>
                            <p class="card-text px-3"><%=user.getUsername()%></p>
                        </div>
                        <ul class="list-group list-group-flush px-3 py-3">                            
                            <li class="list-group-item"><strong>Full Name : </strong> <%=user.getFullname()%></li>
                            <li class="list-group-item"><strong>Email   :</strong> <%=user.getEmail() %></li>
                            <li class="list-group-item"><strong>Phone :</strong> <%=user.getContact() %></li>
                            <li class="list-group-item"><strong>Date of Birth : </strong> <%=(formattedDate != "") ? formattedDate : null %></li>
                            <li class="list-group-item"><strong>Location :</strong> <%=user.getAddress() %></li>
                        </ul>
                    </div>
                </div>
                
                <div class="col-lg-9 col-md-8 col-sm-12">
                    <h1 class="mb-4">My Profile</h1>
                    
                    <div class="row">
                        <div class="col-md-6 mb-4">
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title">My Orders</h5>
                                    <p class="card-text">View your order history and track current orders.</p>
                                    <button type="button" onclick="window.location.href='vieworder.jsp'" class="btn btn-primary">View Orders</button>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6 mb-4">
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title">Edit Password</h5>
                                    <p class="card-text">Strengthen your password for enhanced security.</p>
                                    <button type="button" onclick="window.location.href='changepassword.jsp'" class="btn btn-primary">Change Password</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="card">
                        <div class="card-body mt-2">
                            <h5 class="card-title mb-4">Edit Profile</h5>
                            <form method="post" action="UpdateProfileServlet">
                                <input type="hidden" name="userid" value="<%=user.getUserid() %>">
                                <div class="mb-3">
                                    <label for="username" class="form-label">Username</label>
                                    <input type="text" class="form-control" id="username" value="<%=user.getUsername() %>" maxlength="250" name="username">
                                </div>
                                <div class="mb-3">
                                    <label for="fname" class="form-label">Full Name</label>
                                    <input type="text" class="form-control" id="fname" value="<%=user.getFullname() %>" maxlength="250" name="fullname">
                                </div>
                                <div class="mb-3">
                                    <label for="email" class="form-label">Email</label>
                                    <input type="email" class="form-control" id="email" value="<%=user.getEmail() %>" maxlength="250" name="email">
                                </div>
                                <div class="mb-3">
                                    <label for="contact" class="form-label">Phone</label>
                                    <input type="tel" class="form-control" id="contact" value="<%=user.getContact() %>" maxlength="250" name="contact">
                                </div>
                                <div class="mb-3">
                                    <label for="dob" class="form-label">Date of Birth</label>
                                    <input type="date" class="form-control" id="dob" value="<%=dateString %>" name="dob">
                                </div>
                                <div class="mb-3">
                                    <label for="address" class="form-label">Location</label>
                                    <input type="text" class="form-control" id="address" value="<%=user.getAddress() %>" maxlength="250" name="address">
                                </div>
                                <button type="submit" class="btn btn-primary">Save Changes</button>
                            </form>
                        </div>
                    </div>
                    
                </div>
            </div>
        </div>
        
        <% } %>
        
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
        
        <jsp:include page="footer.html"/>
    </body>
</html>
