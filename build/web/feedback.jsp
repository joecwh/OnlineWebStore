<%-- 
    Document   : contact
    Created on : May 13, 2023, 6:28:49 PM
    Author     : Lenovo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="VisitorCount"/>

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
        <link rel="icon" href="Images/titlelogo.png" type="image/icon type">
        <title>Feedback</title>
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
        
            <section class="mb-4">
                <div class="container-fluid h-custom">
                  <div class="row d-flex justify-content-center align-items-center h-100">
                    <div class="col-md-9 col-lg-6 col-xl-5">
                      <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.webp"
                        class="img-fluid" alt="Sample image">
                    </div>
                    <div class="col-md-8 col-lg-6 col-xl-4 offset-xl-1">
                        <form method="post" action="SendFeedback">
                        <div class="d-flex flex-row align-items-center justify-content-center justify-content-lg-start text-primary fw-bold fs-5 mt-4">
                          <p>Do you have any questions? Please do not hesitate to contact us directly. </p>
                        </div>
                          
                        <!-- name input -->
                        <div class="form-outline mb-2">
                            <label class="form-label" for="name">Your name</label>
                            <input type="text" id="name" class="form-control form-control-lg" maxlength="250" name="name" />
                        </div>

                        <!-- email input -->
                        <div class="form-outline mb-2">
                            <label class="form-label" for="email">Your email</label>
                            <input type="email" id="email" class="form-control form-control-lg" maxlength="250" name="email" />
                        </div>
                        
                        <div class="form-outline mb-2">
                            <label class="form-label" for="subject">Subject</label>
                            <input type="text" id="subject" class="form-control form-control-lg" maxlength="250" name="subject" />
                        </div>
                        
                        <div class="form-outline mb-2">
                            <label class="form-label" for="message">Your message</label>
                            <textarea class="form-control" id="message" rows="3" maxlength="250" placeholder='within 250 letters' name="message"></textarea>
                        </div>

                        <div class="text-center text-lg-start mt-3 pt-2">
                          <button type="submit" class="btn btn-primary btn-lg"
                            style="padding-left: 2.5rem; padding-right: 2.5rem;">SEND</button>
                        </div>

                      </form>
                    </div>
                  </div>
                </div>
              </section>
            
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
