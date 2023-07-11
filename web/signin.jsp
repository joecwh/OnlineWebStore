<%-- 
    Document   : signin
    Created on : Jun 30, 2023, 2:40:45 PM
    Author     : Lenovo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="ConfigureRoles"/>

<%
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
        <title>Login</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="icon" href="Images/titlelogo.png" type="image/icon type">
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
        
        <div class="notification-box <%=(alertMsg != null && !alertMsg.isEmpty()) ? "" : "hide"%> text-center">
            <span class="close-button">&times;</span>
            <p class="pt-2"><%= alertMsg %></p>
        </div>
        
        <section class="h-100">

            <div class="container h-100 card card-registration my-4">
              <div class="row d-flex justify-content-center align-items-center h-100">
                <div class="col-sm-6 text-black">

                  <div class="px-5 ms-xl-4">
                    <i class="fas fa-crow fa-2x me-3 pt-5 mt-xl-4" style="color: #709085;"></i>
                    <span class="h1 fw-bold mb-0"><a href='home.jsp'><img src='Images/homelogo.png' class='w-50'></a></span>
                  </div>

                  <div class="d-flex align-items-center h-custom-2 px-5 ms-xl-4 mt-5 pt-5 pt-xl-0 mt-xl-n5">

                    <form style="width: 23rem;" action="LoginServlet" method="post">

                      <h3 class="fw-normal mb-3 pb-3" style="letter-spacing: 1px;">Sign in to Application</h3>

                      <div class="form-outline mb-4">
                        <input type="text" id="form2Example18" class="form-control form-control-lg" maxlength="250" name='email' />
                        <label class="form-label" for="form2Example18">Email address</label>
                      </div>

                      <div class="form-outline mb-4">
                        <input type="password" id="form2Example28" class="form-control form-control-lg" maxlength="250" name='password' />
                        <label class="form-label" for="form2Example28">Password</label>
                      </div>
                      
                      <div class="pt-1 mb-4">
                          <input class="btn btn-primary btn-lg btn-block text-white" type="submit" value="Login"></button>
                      </div>

                      <p class="small mb-5 pb-lg-2"><a class="text-muted" href="resetPassword.jsp">Forgot password?</a></p>
                      <p>Don't have an account? <a href="signup.jsp" class="link-info">Register here</a></p>
                    </form>
                      
                  </div>
                    
                </div>
                <div class="col-sm-6 px-0 d-none d-sm-block">
                  <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/img3.webp"
                    alt="Login image" class="w-100 vh-100" style="object-fit: cover; object-position: left;">
                </div>
              </div>
            </div>
          </section>
        
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
