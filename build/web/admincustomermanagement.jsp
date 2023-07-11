<%-- 
    Document   : admincustomer
    Created on : May 14, 2023, 1:55:20 AM
    Author     : Lenovo
--%>

<%@page import="enumeration.UserCode"%>
<%@page import="dto.UserDto"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="model.Users"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="AdminGetAllCustomer"/>
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
    
    List<UserDto> customers = new ArrayList();
    if(session.getAttribute("customers") != null)
    {
        customers = (List<UserDto>) session.getAttribute("customers");
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
        <title>Customer Management</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        
        <style>
            a
            {
                text-decoration: none;
            }
            
            td 
            {
                white-space: nowrap; /* prevents text from wrapping */
                overflow: hidden; /* hides overflowed text */
                text-overflow: ellipsis; /* adds ellipsis (...) at the end of the text */
                max-width: 150px; /* sets the maximum width of the container */
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
            <p class="text-white fs-5 fw-bold mt-4 text-center">Customer Management</p>
        </div>
        
        <div class="container" style="margin-top: 100px; position: relative; min-height: 530px;">
            <div class="accordion" id="accordionExample" style="background-color: white;">
                <div class="accordion-item">
                    <h2 class="accordion-header" id="headingOne">
                        <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                            Add new customer
                        </button>
                    </h2>
                    <div id="collapseOne" class="accordion-collapse collapse show" aria-labelledby="headingOne" data-bs-parent="#accordionExample">
                        <form method="post" action="AdminAddCustomer">
                            <div class="accordion-body">
                                <div class="form-floating mb-3">
                                    <input type="text" class="form-control" id="username" placeholder="Username" name="username">
                                    <label for="username">Customer username</label>
                                </div>
                                <div class="row">
                                    <div class="form-floating mb-3 col-6">
                                        <input type="text" class="form-control" id="firstname" placeholder="First name" name='firstname'>
                                        <label for="firstname"> &nbsp; &nbsp;First name</label>
                                    </div>
                                    <div class="form-floating mb-3 col-6">
                                        <input type="text" class="form-control" id="lastname" placeholder="Last name" name="lastname">
                                        <label for="lastname"> &nbsp; &nbsp;Last name</label>
                                    </div>
                                </div>
                                <div class="form-floating mb-3">
                                    <input type="email" class="form-control" id="email" placeholder="Email" name='email'>
                                    <label for="email">Email</label>
                                </div>
<!--                                <div class="form-floating mb-3">
                                    <input type="password" class="form-control" id="password" placeholder="Password" name="password">
                                    <label for="password">Password</label>
                                </div>
                                <div class="form-floating mb-3">
                                    <input type="text" class="form-control" id="contact" placeholder="Contact" name="contact">
                                    <label for="contact">Contact</label>
                                </div>
                                <div class="form-floating mb-3">
                                    <input type="date" class="form-control" id="dob" placeholder="Date of Birth" name="dob">
                                    <label for="dob">Date of Birth</label>
                                </div>-->
                                
                                <input type="submit" class="btn btn-primary" id="submit" value="ADD">
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            
            <table class="table table-striped text-center"  style="background-color: white;">
                <thead>
                    <tr>
                        <th scope="col">No</th>
                        <th scope="col">Username</th>
                        <th scope="col">Customer Name</th>
                        <th scope="col" colspan="3">Functionality</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        if(!customers.isEmpty()){
                            int i = 1;
                            for(UserDto c : customers){
                    %>
                    <tr>
                        <th scope="row"><%=i%></th>
                        <td><%=c.getUsername() %></td>
                        <td><%=c.getFullname() %></td>
                        <form method="post" action="adminviewuser.jsp?select=Customer&userid=<%=c.getUserid() %>">
                            <input type="hidden" name="userid" value="<%=c.getUserid() %>"/>
                            <td><input type="submit" name="submit" class="text-primary" style="border: none; padding: 0; background-color: transparent;" value="View"/></td>
                        </form>
                        <form method="post" action="adminupdateuser.jsp?select=Customer&userid=<%=c.getUserid() %>">
                            <input type="hidden" name="userid" value="<%=c.getUserid() %>"/>
                            <td><input type="submit" name="submit" class="text-warning" style="border: none; padding: 0; background-color: transparent;" value="Edit"/></td>
                        </form>
                        <form method="post" action="AdminDeleteStaff">
                            <input type="hidden" name="userid" value="<%=c.getUserid() %>"/>
                            <td><input type="submit" name="submit" onclick="return confirmDelete()" class="text-danger" style="border: none; padding: 0; background-color: transparent;" value="Delete"/></td>
                        </form>
                    </tr>
                    <%      i++; }   
                        } else { %> 
                    <tr>
                        <td colspan="5">
                            <strong>No customer found.</strong> Please try a different search criteria.
                        </td>
                    </tr>
                    <%  } %>
                </tbody>
            </table>
        </div>
                
        <script>
            function confirmDelete() {
                if (confirm("Are you sure you want to delete?")) {
                    return true; // submit the form
                } else {
                    return false; // do not submit the form
                }
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
