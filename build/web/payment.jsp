<%-- 
    Document   : payment
    Created on : Jun 27, 2023, 7:12:12 PM
    Author     : Lenovo
--%>

<%@page import="model.PaymentMethod"%>
<%@page import="model.Discount"%>
<%@page import="dto.CartDto"%>
<%@page import="java.io.Serializable"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="model.Product"%>
<%@page import="dto.UserDto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="PaymentMethodServlet"/>
<%
    UserDto user = null;
    Discount discount = null;
    List<CartDto> paymentcart = new ArrayList();
    List<PaymentMethod> onlinebanking = new ArrayList();
    List<String> productImg = new ArrayList();
    String alertMsg = "";
    double subtotal = 0.0;
    double shippingfee = 0.0;
    double total = 0.0;
    double taxes = 0.0;
    double ds = 0.0;
    
    if(session.getAttribute("user") != null)
    {
        user = (UserDto) session.getAttribute("user");
    }
    
    if(session.getAttribute("onlinebanking") != null)
    {
        onlinebanking = (List<PaymentMethod>) session.getAttribute("onlinebanking");
    }
    
    if(session.getAttribute("discount") != null)
    {
        discount = (Discount) session.getAttribute("discount");
        session.removeAttribute("discount");
    }
    
    if(session.getAttribute("carts") != null)
    {
        paymentcart = (List<CartDto>) session.getAttribute("carts");
        for(CartDto c : paymentcart)
        {
            Serializable imageBlob = (Serializable) c.getProductImg();
            byte[] imageBytes = (byte[]) imageBlob;
            productImg.add(java.util.Base64.getEncoder().encodeToString(imageBytes));
            
            subtotal += c.getTotal();
        }
        if(subtotal < 200)
            shippingfee = 25.00;
        taxes = (subtotal + shippingfee) * 0.06;
        total = subtotal + taxes + shippingfee;
        
        if(discount != null)
        {
            ds = (100 - discount.getPercent()) / 100;
            ds *= total;
        }
    }
    
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
        <title>Payment</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        
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
        
        
        <div class="container-fluid text-center">
            <div class="container shadow my-4">
                <button type='button' class='btn btn-primary' style='position: absolute; left: 8%; top: 15%;' onclick="goBack()">Back</button>
                <h3 class="py-4 mb-4">Payment Page</h3>

                <form method="post" action="OrderServlet">
                <!-- Use Existing Address or New Address -->
                <div class="mb-4 bg-light rounded  mx-auto p-3">
                    <label class="form-label text-secondary mb-4 mt-3"><i>Select Address : </i></label>
                    <div class="form-check w-50 mx-auto mb-3 bg-light">
                        <input class="form-check-input" type="radio" name="addressOption" value="newaddress" id="newAddressRadio" checked>
                        <label class="form-check-label" for="useNewAddress">Use New Address</label>
                    </div>
                    <div class="form-check w-50 mx-auto mb-3">  
                        <input class="form-check-input" type="radio" name="addressOption" value="existingaddress" id="existingAddressRadio" <%=(user != null) ? "" : "disabled" %>  onclick="toggleAddressForm()"/>
                        <label class="form-check-label" for="useExistingAddress">Use Existing Address</label>
                    </div>
                </div>
                

                <!-- Address Form -->
                <div id="newAddressForm" class="mx-auto bg-light p-4 rounded mb-4">
                    <div class="mb-4 row">
                        <label for="fullName" class="form-label col-sm-4 col-form-label">Full Name</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" id="fullName" name="fullName" maxlength="250" required>
                        </div>
                    </div>
                    
                    <div class="mb-4 row">
                        <label for="email" class="form-label col-sm-4 col-form-label">Email Address</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" id="email" name="email" maxlength="250" required>
                        </div>
                    </div>
                    
                    <div class="mb-4 row">
                        <label for="contact" class="form-label col-sm-4 col-form-label">Phone Number</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" id="contact" name="contact" maxlength="250" required>
                        </div>
                    </div>
                    
                    <div class="row">
                        <label for="address" class="form-label col-sm-4 col-form-label">Shipping Address</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" id="address" name="address" maxlength="250" required>
                        </div>
                    </div>
                </div>
                
                <div id="existingAddressForm" class="" style="display: none; background-color: #FFE385;">
                    <div class="row g-2 mb-3">
                        <div class="col-sm-6">
                            <h6>Full Name</h6>
                            <p><%= (user != null) ? user.getFullname() : "" %></p>
                            <h6>Email address</h6>
                            <p><%= (user != null) ? user.getEmail(): "" %></p>
                        </div>
                        <div class="col-sm-6 text-center">
                            <h6>Phone Number</h6>
                            <p><%= (user != null) ? user.getContact(): "" %></p>
                            <h6>Shipping Address : </h6>
                            <p style="width: 100%;"><%= (user != null) ? user.getAddress(): "" %></p>
                        </div>
                    </div>
                </div>

                <!-- Payment Method -->
                <div class="mb-4 bg-light rounded  mx-auto p-3">
                    <label class="form-label text-secondary mb-4 mt-3"><i>Select Payment Method : </i></label>
                    <div class="form-check w-50 mx-auto mb-3">
                        <input class="form-check-input" type="radio" name="paymentMethod" value="cash" id="cashOnDelivery" onclick="disableForms('cashOnDelivery')" checked>
                        <label class="form-check-label fa fa-money" for="cashOnDelivery"> Cash on Delivery</label>
                    </div>
                    <div class="form-check w-50 mx-auto mb-3">
                        <input class="form-check-input" type="radio" name="paymentMethod" value="onlinebanking" id="onlineBanking" onclick="disableForms('onlineBanking')">
                        <label class="form-check-label fa fa-institution" for="onlineBanking"> Online Banking</label>
                    </div>
                    <div class="form-check w-50 mx-auto mb-3">
                        <input class="form-check-input" type="radio" name="paymentMethod" value="card" id="creditCard" onclick="disableForms('creditCard')">
                        <label class="form-check-label fa fa-credit-card" for="creditCard"> Credit / Debit Card</label>
                    </div>
                </div>
                
                <!-- Online Banking Form -->
                <div class="mx-auto mb-3 bg-light rounded p-3" id='onlineBankingTableList' style="display: none;" >
                    <label class="form-label text-secondary"><i>Online Banking Selection : </i></label>
                    <select class="form-select w-50 mx-auto text-center" id="onlineBankingSelection" name="onlineBankingSelection" disabled>
                        <%  if(!onlinebanking.isEmpty())
                            for (PaymentMethod p : onlinebanking) { %>
                                <option value="<%= p.getPaymentid()%>"><%= p.getName()%></option>
                        <% } %>
                    </select>
                </div>

                <!-- Credit Card Form -->
                <div id="creditCardForm" style="display: none;" class="mx-auto bg-light p-4 rounded mb-4">
                    <img src='Images/visaMasterCard.png' width="25%" height="100px" class='my-3'>
                    <div>
                        <div class="mb-4 row">
                            <label for="cardOwner" class="form-label col-sm-4 col-form-label">Card Owner Name : </label>
                            <div  class="col-sm-8">
                                <input type="text" class="form-control" id="cardOwner" name="cardOwner" maxlength="250" disabled>
                            </div>
                        </div>
                        <div class="mb-4 row">
                            <label for="cardNumber" class="form-label col-sm-4 col-form-label">Card Number : </label>
                            <div  class="col-sm-8">
                                <input type="text" class="form-control" id="cardNumber" name="cardNumber" maxlength="20" disabled>
                            </div>
                        </div>
                        <div class="mb-4 row">
                            <label for="expiryDate" class="form-label col-sm-4 col-form-label">Expiration Date : </label>
                            <div  class="col-sm-8">
                                <input type="date" class="form-control" id="expiryDate" name="cardExpiryDate" disabled>
                            </div>
                        </div>
                        <div class="row">
                            <label for="securityCode" class="form-label col-sm-4 col-form-label">Security Code : </label>
                            <div  class="col-sm-8">
                                <input type="text" class="form-control" id="securityCode" name="cardSecurityCode" maxlength="3" disabled>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Product Details -->
                <div class="mb-4 bg-light rounded mx-auto p-3">
                    <p class="mb-4 text-secondary mt-3"><i>Product Details : </i><p>
                    <div class="row bg-white rounded mb-3">
                        <div class="col-12">
                            <table class="table text-center align-items-center">
                                <thead>
                                    <tr>
                                        <td>Product</td>
                                        <td>Name</td>
                                        <td>Quantity</td>
                                        <td>Total</td>
                                    </tr>
                                </thead>
                                <tbody>

                                <% if(!paymentcart.isEmpty()) { 
                                    int i = 0;
                                    for(CartDto c : paymentcart) { 
                                %>
                                
                                    <tr>
                                        <td class="align-middle"><img src="data:image/jpeg;base64,<%= productImg.get(i) %>" style="width: 50px; height: 50px;"></td>

                                        <td class="align-middle"><%=c.getProductName()%></td>

                                        <td class="align-middle">x <%=c.getQuantity() %></td>

                                        <td class="align-middle" id="total">RM <%=String.format("%.2f", c.getTotal()) %></td>
                                    </tr>
                                    
                                <% i++; } } %>
                                    
                                    <tr class='text-secondary'>
                                        <td colspan="2"></td>
                                        <td>Subtotal</td>
                                        <td>RM <%=String.format("%.2f", subtotal) %></td>
                                    </tr>
                                    <tr class='text-secondary'>
                                        <td colspan="2"></td>
                                        <td>Shipping Fee</td>
                                        <td>RM <%=String.format("%.2f", shippingfee) %></td>
                                    </tr>
                                    <tr class='text-secondary'>
                                        <td colspan="2"></td>
                                        <td>Sale Taxes (6%)</td>
                                        <td>RM <%=String.format("%.2f", taxes)%></td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                
                <div class="mx-auto bg-light p-4 rounded mb-4">
                    <div class="row justify-content-center">
                        <div class="col-md-6">
                            <div class="input-group">
                                <button class="btn btn-primary p-2" onclick="applyCoupon()" type="button" <%= (total == 0.0) ? "disabled" : ""%>>Apply Coupon</button>
                                <input type="text" id="couponInput" name="coupon" class="form-control text-center p-2" <%= (discount != null) ? "value='" + discount.getCode() + "'" : "" %> maxlength="250" placeholder="Enter coupon code">
                            </div>
                        </div>
                    </div>
                </div>
                
                <!-- Place Order Button -->
                <div class="row justify-content-center align-items-center mt-5">
                    <h4 class="col-sm-6 text-center">Total Price : RM <%= (discount == null) ? String.format("%.2f", total) : String.format("%.2f", ds) + " <span class='text-danger text-decoration-line-through fw-light fs-6'>RM " + String.format("%.2f", total) + "</span>" %></h4>
                    <input type="hidden" name="subtotal" value="<%=String.format("%.2f", subtotal) %>"/>
                    <input type="hidden" name="shippingfee" value="<%=String.format("%.2f", shippingfee) %>"/>
                    <input type="hidden" name="taxes" value="<%=String.format("%.2f", taxes) %>"/>
                    <input type="hidden" name="total" value="<%=(discount == null) ? String.format("%.2f", total) : String.format("%.2f", ds) %>"/>
                    <button type="submit" class="btn btn-danger col-sm-6 btn-lg" <%= (total == 0.0) ? "disabled" : ""%>>Place Order</button>
                </div>
                </form>
            </div>
        </div>
        
        <script>
            document.querySelector('.close-button').addEventListener('click', function() {
              hideNotificationBox();
            });
            
            function applyCoupon() {
                var couponCode = document.getElementById('couponInput').value;
                var url = 'DiscountServlet?coupon=' + encodeURIComponent(couponCode);
                window.location.href = url;
            }

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
            
            // Function to handle radio button change event
            function handleAddressOptionChange() {
                var newAddressForm = document.getElementById('newAddressForm');
                var existingAddressForm = document.getElementById('existingAddressForm');
                var formFields = newAddressForm.querySelectorAll('input, select, textarea');

                if (document.getElementById('newAddressRadio').checked) {
                    newAddressForm.style.display = 'block';
                    existingAddressForm.style.display = 'none';
                    formFields.forEach(function(field) {
                        field.disabled = false;
                    });
                } else {
                    newAddressForm.style.display = 'none';
                    existingAddressForm.style.display = 'block';
                    formFields.forEach(function(field) {
                        field.disabled = true;
                    });
                }
            }

            // Add event listener to radio buttons
            document.getElementById('newAddressRadio').addEventListener('change', handleAddressOptionChange);
            document.getElementById('existingAddressRadio').addEventListener('change', handleAddressOptionChange);
            
            function goBack() {
                window.history.back();
            }
            
            // Get the necessary elements
            var onlineBankingTableList = document.getElementById("onlineBankingTableList");
            var creditCardForm = document.getElementById("creditCardForm");

            // Add event listeners to payment method radio buttons
            document.getElementById("cashOnDelivery").addEventListener("click", function() {
                onlineBankingTableList.style.display = "none";
                creditCardForm.style.display = "none";
            });

            document.getElementById("onlineBanking").addEventListener("click", function() {
                onlineBankingTableList.style.display = "block";
                creditCardForm.style.display = "none";
            });

            document.getElementById("creditCard").addEventListener("click", function() {
                onlineBankingTableList.style.display = "none";
                creditCardForm.style.display = "block";
            });
            
            function disableForms(selectedPaymentMethod) {
            const onlineBankingForm = document.getElementById("onlineBankingSelection");
            const cardDetailsForm = document.querySelectorAll('[name^="card"]');

            if (selectedPaymentMethod === "cashOnDelivery") {
                onlineBankingForm.disabled = true;
                cardDetailsForm.forEach((input) => {
                    input.disabled = true;
                });
            } else if (selectedPaymentMethod === "onlineBanking") {
                onlineBankingForm.disabled = false;
                cardDetailsForm.forEach((input) => {
                    input.disabled = true;
                });
            } else if (selectedPaymentMethod === "creditCard") {
                onlineBankingForm.disabled = true;
                cardDetailsForm.forEach((input) => {
                    input.disabled = false;
                });
            }
          }

            setTimeout(hideNotificationBox, 5000); // 5000 milliseconds = 5 seconds
        </script>
        
        <jsp:include page="footer.html"/>
    </body>
</html>
