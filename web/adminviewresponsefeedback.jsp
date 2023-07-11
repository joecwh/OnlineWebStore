<%-- 
    Document   : adminviewresponsefeedback
    Created on : Jul 5, 2023, 12:40:44 AM
    Author     : Lenovo
--%>

<%@page import="enumeration.UserCode"%>
<%@page import="dto.UserDto"%>
<%@page import="enumeration.FeedbackCode"%>
<%@page import="model.Feedback"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="AdminGetFeedback"/>
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
    
    Feedback feedback = null;
    if(session.getAttribute("feedback") != null)
    {
        feedback = (Feedback) session.getAttribute("feedback");
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
        <title>Feedback Review & Response</title>
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
            <p class="text-white fs-5 fw-bold mt-4 text-center">Feedback Review & Response</p>
        </div>
        
        <div class="container" style="margin-top: 100px; position: relative; min-height: 530px;">
            
            <% if(feedback != null) { %>
            
            <div class="accordion" id="accordionExample" style="background-color: white;">
                <div class="accordion-item">
                    <h2 class="accordion-header" id="headingOne">
                        <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                            Feedback
                        </button>
                    </h2>
                    <div id="collapseOne" class="accordion-collapse collapse show" aria-labelledby="headingOne" data-bs-parent="#accordionExample">
                        <form method="post" action="AdminAddCustomer">
                            <div class="accordion-body">
                                <div class="row">
                                    <div class="form-floating mb-3 col-6">
                                        <input type="text" class="form-control" id="firstname" placeholder="First name" name='firstname' value="<%=feedback.getEmail() %>" readonly>
                                        <label for="firstname"> &nbsp; &nbsp;Email</label>
                                    </div>
                                    <div class="form-floating mb-3 col-6">
                                        <input type="text" class="form-control" id="lastname" placeholder="Last name" name="lastname" value="<%=feedback.getName()%>" readonly>
                                        <label for="lastname"> &nbsp; &nbsp;Name</label>
                                    </div>
                                </div>
                                <div class="form-floating mb-3">
                                    <input type="text" class="form-control" id="username" placeholder="Username" name="username" value="<%=feedback.getSubject()%>" readonly>
                                    <label for="username">Subject</label>
                                </div>
                                <div class="form-floating mb-3">
                                    <textarea class="form-control" style="height: 100px" id="exampleFormControlTextarea1" rows="3" disabled><%=feedback.getMessage() %></textarea>
                                    <label for="exampleFormControlTextarea1">Message</label>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            
            <div class="container bg-white p-3 rounded shadow mb-4">
                <h6 class="ps-1 mb-3">Response</h6>
                <form method="post" action="AdminUpdateFeedbackStatus" class="mb-4">
                    <div class="row g-2 mb-3">
                        <div class=" col">
                            <select class="form-select" aria-label="Default select example" name="status">

                                <% for(FeedbackCode c : FeedbackCode.values()) { %>
                                <option value="<%= c.name() %>" <%= (feedback.getStatus().equals(c.name())) ? "selected" : "" %>><%= c.name() %></option>
                                <% } %>

                            </select>
                        </div>
                        <div class="col">
                            <input type="hidden" name="id" value="<%=feedback.getId() %>">
                            <input type="submit" class="btn btn-primary" value="Save"/>
                        </div>
                    </div>
                </form>
                
                <form method="post" action="">
                    <div class="form-floating my-3">
                        <input type="text" class="form-control" id="username" placeholder="subject" maxlength="250" name="subject" value='Response to "<%=feedback.getSubject() %>"'>
                        <label for="username">Response Subject</label>
                    </div>
                    <div class="form-floating mb-3">
                        <textarea class="form-control" style="height: 100px" id="exampleFormControlTextarea1" maxlength="250" rows="3" name="message"></textarea>
                        <label for="exampleFormControlTextarea1">Message (within 250 characters)</label>
                    </div>
                    <input type="submit" class="btn btn-primary" value="Send"></input>
                </form>
                
            </div>
                
            <% } else { %>
            
                <div class="container">
                    <div class="row justify-content-center my-5">
                        <div class="col-md-6 text-center">
                            <h3>No feedback found.</h3>
                            <p>Something went wrong, please try again later.</p>
                            <a href="adminfeedbackmanagement.jsp" class="btn btn-primary back-button">Back</a>
                        </div>
                    </div>
                </div>
            
            <% } %>
                
        </div>
        
        <jsp:include page="adminfooter.html"/>

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

            setTimeout(hideNotificationBox, 5000); // 5000 milliseconds = 5 seconds
        </script>
    </body>
</html>
