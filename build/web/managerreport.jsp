<%-- 
    Document   : managerreport
    Created on : May 14, 2023, 1:41:47 AM
    Author     : Lenovo
--%>

<%@page import="enumeration.UserCode"%>
<%@page import="dto.UserDto"%>
<%@page import="model.Report"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="AdminGetAllReport"/>
<%
    UserDto user = null;
    if(session.getAttribute("user") != null)
    {
        user = (UserDto) session.getAttribute("user");
        if(!user.getRolename().equals(UserCode.MANAGER.name()))
        {
            String url = request.getHeader("Referer");
            if (url.contains("?")) {
                url += "&";
            } else {
                url += "?";
            }
            response.sendRedirect(url + "alert=" + UserCode.CREDENTIAL_NOT_ALLOW.getMessage());
        }
    }
        
    String alertMsg = "";
    if(request.getParameter("alert") != null)
    {
        alertMsg = request.getParameter("alert");
    }
    
    List<Report> reports = new ArrayList();
    if(session.getAttribute("reports") != null)
    {
        reports = (List<Report>) session.getAttribute("reports");
        session.removeAttribute("reports");
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="icon" href="Images/titlelogo.png" type="image/icon type">
        <title>Manager Report</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
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
            <p class="text-white fs-5 fw-bold mt-4 text-center">Report Management</p>
        </div>
        
        <div class="container" style="margin-top: 100px; position: relative; min-height: 530px;">
            <div class="accordion" id="accordionExample" style="background-color: white;">
                <div class="accordion-item">
                    <h2 class="accordion-header" id="headingOne">
                        <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="false" aria-controls="collapseOne">
                            Add New Report
                        </button>
                    </h2>
                    <div id="collapseOne" class="accordion-collapse collapse show" aria-labelledby="headingOne" data-bs-parent="#accordionExample">
                        <div class="accordion-body"><button type="button" onclick="window.location.href='GenerateReport'" class="btn btn-primary">GENERATE</button>
                            <span class="fw-bold">&nbsp;Top & Least 5 Demand Product Report</span>
                        </div>
                    </div>
                </div>
            </div>
            
            <table class="table table-striped text-center" style="background-color: white;">
                <thead>
                    <tr>
                        <th scope="col">No</th>
                        <th scope="col">Report Name</th>
                        <th scope="col" colspan="3">View</th>
                    </tr>
                </thead>
                <tbody>
                    <%  int i = 1;
                        if(!reports.isEmpty()) { 
                            for(Report r : reports) { 
                    %>
                    
                    <tr>
                        <th scope="row"><%=i %></th>
                        <td><%=r.getReportname() %></td>
                        <td><a href="Reports/<%=r.getReportname()%>.html" target="_blank">.html</a></td>
                        <td><a href="AdminViewReportPdf?reportid=<%=r.getReportid() %>" target="_blank">.pdf</a></td>
                        <td><a href="AdminDeleteReport?reportid=<%=r.getReportid() %>" onclick="return confirm('Are you sure you want to delete?')" class="text-danger">Delete</a></td>
                    </tr>
                    
                    <% i++; } } else { %>
                    
                    <tr>
                        <td colspan="5">
                            <strong>No report has found.</strong> Please try a different search criteria.
                        </td>
                    </tr>
                    
                    <% } %>
                </tbody>
            </table>
        </div>
        
        <jsp:include page="adminfooter.html"/>
        
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
