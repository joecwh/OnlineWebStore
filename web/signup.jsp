<%-- 
    Document   : signup
    Created on : Jun 30, 2023, 3:36:16 PM
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
        <title>Signup</title>
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
            <div class="container h-100">
                <div class="row d-flex justify-content-center align-items-center h-100">
                    <div class="col">
                    <div class="card card-registration my-4">
                        <div class="row g-0">
                        
                            <div class="col-xl-6 d-none d-xl-block">
                              <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-registration/img4.webp"
                                alt="Sample photo" class="img-fluid"
                                style="border-top-left-radius: .25rem; border-bottom-left-radius: .25rem;" />
                            </div>
                        
                            <div class="col-xl-6">
                                <form method="post" action="SignupServlet">
                                    <div class="card-body p-md-4 text-black">

                                        <h3 class="mb-4 text-uppercase text-primary text-center"><a href='home.jsp'><img src='Images/homelogo.png' class='w-25'></a>  Registration Form</h3>

                                        <div class="form-floating mb-3">
                                            <input type="text" class="form-control" id="username" placeholder="Username" maxlength="250" name="username" required/>
                                            <label for="username">Username</label>
                                        </div>

                                        <div class="row">
                                            <div class="form-floating col-md-6 mb-3">
                                                <input type="text" class="form-control" id="fname" placeholder="First name" maxlength="250" name="fname" required/>
                                                <label for="fname"> &nbsp; &nbsp;First name</label>
                                            </div>

                                            <div class="form-floating col-md-6 mb-3">
                                                <input type="text" class="form-control" id="lname" placeholder="Last name" maxlength="250" name="lname" required/>
                                                <label for="lname"> &nbsp; &nbsp;Last name</label>
                                            </div>
                                        </div>

                                        <div class="form-floating mb-3">
                                            <input type="email" class="form-control" id="email" placeholder="Email" maxlength="250" name="email" required/>
                                            <label for="email">Email</label>
                                        </div>

                                        <div class="form-floating mb-3">
                                            <input type="password" class="form-control" id="password" placeholder="Password" maxlength="250" name="password" required/>
                                            <label for="password">Password</label>
                                        </div>

                                        <div class="form-floating mb-3">
                                            <input type="text" class="form-control" name='contact' id="contact" placeholder="Contact" maxlength="250" name="contact" required/>
                                            <label for="contact">Contact</label>
                                        </div>

                                        <div class="form-floating mb-3">
                                            <input type="date" class="form-control" name='dob' id="dob" placeholder="Date of birth" name="dob" required/>
                                            <label for="dob">Date of birth</label>
                                        </div>

                                        <div class="form-floating mb-3">
                                            <input type="text" class="form-control" name='address' id="address" placeholder="Address" maxlength="250" name="address" required/>
                                            <label for="address">Address</label>
                                        </div>

                                        <div class="form-floating mb-3">
                                            <input type="text" class="form-control" name='pincode' id="pincode" placeholder="Pincode" maxlength="250" name="pincode" required/>
                                            <label for="pincode">Pincode</label>
                                        </div>

                                        <div class="row">
                                        <div class="form-floating mb-3 col-6">
                                            <input type="text" class="form-control" name='state' id="state" placeholder="State" maxlength="250" name="state" required/>
                                            <label for="state"> &nbsp; &nbsp;State</label>
                                        </div>

                                        <div class="form-floating mb-3 col-6">
                                            <input type="text" class="form-control" name='city' id="city" placeholder="City" maxlength="250" name="city" required/>
                                            <label for="city"> &nbsp; &nbsp;City</label>
                                        </div>
                                        </div>

                                        <div class="form-floating mb-3">
                                            <input type="text" class="form-control" name='country' id="country" placeholder="Country" maxlength="250" name="country" required/>
                                            <label for="country">Country</label>
                                        </div>


                                      <div class="d-flex justify-content-center mt-4">
                                        <button type="submit" class="btn btn-primary btn-lg ms-2">Submit form</button>
                                      </div>

                                    </div>
                                </form>
                            </div>
                            
                        </div>
                    </div>
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
