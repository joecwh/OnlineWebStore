<%-- 
    Document   : adminviewupdatediscount
    Created on : Jun 28, 2023, 6:02:31 PM
    Author     : Lenovo
--%>

<%@page import="enumeration.UserCode"%>
<%@page import="dto.UserDto"%>
<%@page import="model.Discount"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="AdminGetDiscount"/>
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
    
    String action = "";
    if(request.getParameter("action") != null)
        action = request.getParameter("action");
    
    Discount discount = null;
    if(session.getAttribute("discount") != null)
    {
        discount = (Discount) session.getAttribute("discount");
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
        <title>Discount Management</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <style>
            a {
                text-decoration: none;
            }
            
            td {
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
            <p class="text-white fs-5 fw-bold mt-4 text-center">Discount <%=action%></p>
        </div>
        
        <div class="container shadow mb-3" style="margin-top: 100px; position: relative; min-height: 515px; background-color: white;">
            <%  if(discount != null) {  %>
            
                <form method="post" action="AdminUpdateDiscount" class="mx-auto px-3 py-3">
                    <a href="admindiscountmanagement.jsp" class="btn btn-primary back-button my-4">Back</a>
                    
                    <div class="form-floating mb-4">
                        <h6>Discount ID </h6>
                        <input type="text" class="form-control py-3" id="discountid" placeholder="discountid" name="discountid" value="<%=discount.getDiscountid() %>" readonly/>
                    </div>
                    
                    <div class="form-floating mb-4">
                        <h6>Name</h6>
                        <input type="text" class="form-control py-3" id="name" placeholder="name" maxlength="250" name="name" value="<%=discount.getName() %>" <%=action.equals("Review") ? "readonly" : "required" %> />
                    </div>
                    
                    <div class="form-floating mb-4">
                        <h6>Code </h6>
                        <input type="text" class="form-control py-3" id="code" placeholder="code" maxlength="250" name="code" value="<%=discount.getCode() %>" <%=action.equals("Review") ? "readonly" : "required" %> />
                    </div> 
          
                    <div class="form-floating mb-4">
                        <h6>Percentage(%) </h6>
                        <input type="text" oninput="validateNumberInput(this)" class="form-control py-3" maxlength="250" id="percent" placeholder="percent" name="percent" value="<%=discount.getPercent() %>" <%=action.equals("Review") ? "readonly" : "required" %> />
                    </div>
                    
                    <div class="mb-4">
                        <% if(action.equals("Update")) { %>
                        
                            <div class="d-grid gap-2 col-3 mx-auto">
                                <input class="btn btn-primary btn-lg mb-4" type="submit" name="submit" value="Save"/>
                            </div>
                        
                        <% } %>
                    
                    </div>
                </form>
            
            <%  } else { %>
            
                <div class="d-flex justify-content-center w-100">
                    <div class="my-5">
                        <div class="text-center" style="margin-top: 150px;">
                            <h3>No discount found.</h3>
                            <p class="">Something went wrong, please try again later.</p>
                            <a href="admindiscountmanagement.jsp" class="btn btn-primary back-button">Back</a>
                        </div>
                    </div>
                </div>
            
            <%  } %>
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
              var newUrl = window.location.pathname + "?" + searchParams.toString();
              window.history.replaceState({}, document.title, newUrl);
            }
            
            function validateNumberInput(input) {
                input.value = input.value.replace(/\D/g, ''); // Remove non-digit characters
                var value = parseInt(input.value);
                // Set the maximum value
                var maxValue = 100; // Example: Maximum value is 100

                if (isNaN(value)) {
                  // Clear the input if it's not a valid number
                  input.value = "1";
                } else if (value > maxValue) {
                  // Limit the input value to the maximum value
                  input.value = maxValue;
                }
            }

            setTimeout(hideNotificationBox, 5000); // 5000 milliseconds = 5 seconds
        </script>

        <jsp:include page="adminfooter.html"/>
    </body>
</html>
