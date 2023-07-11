<%-- 
    Document   : vieworder
    Created on : Jul 2, 2023, 11:56:32 AM
    Author     : Lenovo
--%>

<%@page import="enumeration.OrderCode"%>
<%@page import="enumeration.OrderCode.*"%>
<%@page import="model.PaymentMethod"%>
<%@page import="dto.CartDto"%>
<%@page import="enumeration.ShippingCode"%>
<%@page import="enumeration.OrderCode.*"%>
<%@page import="java.io.Serializable"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="dto.OrderDto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="ViewOrder"/>
<%     
    String alertMsg = "";
    if(request.getParameter("alert") != null)
    {
        alertMsg = request.getParameter("alert");
    }
    
    List<OrderDto> orders = new ArrayList();
    SimpleDateFormat inputFormat = new SimpleDateFormat("E MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
    SimpleDateFormat outputFormat = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
    if(session.getAttribute("orderDto") != null)
    {
        orders = (List<OrderDto>) session.getAttribute("orderDto");
        session.removeAttribute("orderDto");
    }
    
    List<PaymentMethod> pm = new ArrayList();
    if(session.getAttribute("paymentMethod") != null)
    {
        pm = (List<PaymentMethod>) session.getAttribute("paymentMethod");
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="icon" href="Images/titlelogo.png" type="image/icon type">
        <title>View Order</title>
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
        
        <div class="container mb-5 mt-3 " style="min-height: 444px;">

            <% 
                if (!orders.isEmpty()) { 
            %>
            
                <h1 class="mb-5 text-center">Your Orders</h1>
                
            <%
                    int i = 0;
                    for(OrderDto o : orders) { 
                    Date date = inputFormat.parse(o.getShipping().getExpectedreachdate().toString());
            %>
            

            <div id="accordion">
              <!-- Replace the below section with JSP code to fetch and display actual orders -->
                <div class="accordion-item">
                    <h2 class="accordion-header" id="order1">
                        <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapse<%=i%>">
                            <h5>Order #<%=o.getOrder().getOrderid() %> </h5>
                        </button>
                    </h2>
                    <div id="collapse<%=i%>" class="accordion-collapse collapse show" aria-labelledby="order1" data-bs-parent="#accordion">
                        <div class="accordion-body">
                            
                            <div class="alert alert-<%=(o.getShipping().getStatus().equals(ShippingCode.PACKAGING.name())) ? "info" : ((o.getShipping().getStatus().equals(ShippingCode.SHIPPING.name())) ? "warning" : ((o.getShipping().getStatus().equals(ShippingCode.RECEIVED.name())) ? "success" : ((o.getShipping().getStatus().equals(ShippingCode.PENDING.name())) ? "secondary" : "danger")))%>" role="alert">
                                <h6>Order&nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;&nbsp; :&nbsp; <%=o.getOrder().getStatus() %> <%=(o.getOrder().getStatus().equals(OrderCode.COMPLETED.name())) ? "<i class='fa fa-check-circle-o' style='font-size:20px'></i>" : "<i class='fa fa-times-circle-o' style='font-size:20px'></i>" %> </h6>
                                
                                <h6>Package Status  &nbsp;&nbsp; :&nbsp; <%=o.getShipping().getStatus() %></h6>

                                <h6>Expected Arrival &nbsp;:&nbsp; <%=outputFormat.format(date) %></h6>
                            </div>
                            
                            <div class="mb-3">
                                <table class="table">
                                    <tbody>
                                    <%  int imgCount = 0;
                                        for (CartDto c : o.getCart()) { 
                                            Serializable imageBlob = (Serializable) o.getCart().get(imgCount).getProductImg();
                                            byte[] imageBytes = (byte[]) imageBlob;
                                            String productImg = java.util.Base64.getEncoder().encodeToString(imageBytes);
                                    %>
                                        <tr class="">
                                            <td style="width: 50%;">
                                                <img src="data:image/jpeg;base64,<%=productImg %>" class="border me-2" alt="Product Image" style="width: 50px; height: 50px;"/>
                                                <span class="overflow-hidden" style=""><%=c.getProductName() %></span>
                                            </td>
                                            <td style="width: 25%;" class="text-end text-secondary">Qty :  <%=c.getQuantity() %></td>
                                            <td style="width: 25%;" class="text-end text-secondary">RM <%=String.format("%.2f", c.getTotal()) %></td>
                                        </tr>
                                    <% imgCount++; } %>
                                    </tbody>
                                </table>
                            </div>
                                        
                            <div class="mb-2 row pe-3">
                                <div class='col'></div>
                                <table class="col table w-50">
                                    <tbody>
                                        <tr class='text-secondary'>
                                            <td class=''>Subtotal</td>
                                            <td class="text-end">RM <%=String.format("%.2f", o.getTransactions().getSubtotal()) %></td>
                                        </tr>
                                        <tr class='text-secondary'>
                                            <td>Shipping Fee</td>
                                            <td class="text-end">RM <%=String.format("%.2f", o.getTransactions().getShippingfee()) %></td>
                                        </tr>
                                        <tr class='text-secondary'>
                                            <td>Sales Tax</td>
                                            <td class="text-end">RM <%=String.format("%.2f", o.getTransactions().getTaxes()) %></td>
                                        </tr>
                                        <% if(o.getTransactions().getDiscountcode() != null || !o.getTransactions().getDiscountcode().equals("")) { %>

                                        <tr class='text-secondary'>
                                            <td>Code</td>
                                            <td class="text-end"><%=o.getTransactions().getDiscountcode() %></td>
                                        </tr>

                                        <% } %>
                                        <tr>
                                            <td><h5>Total</h5></td>
                                            <td class="text-end"><h5>RM <%=String.format("%.2f", o.getTransactions().getTotal()) %></h5></td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                                        
                            <div class='border p-3 row g-2 mx-0 mb-3'>
                                <div class='col-sm-6 border-bottom'>
                                    <h6 class='mb-3'>Shipping Address</h6>

                                    <p style='width: 80%;'><%=o.getShipping().getReceivername() %><br>
                                        <span><%=o.getShipping().getShippingaddress() %></span>
                                    </p>

                                </div>

                                <div class='col-sm-6 border-bottom'>
                                    <h6 class='mb-3'>Payment method</h6>
                                    <% if(!pm.isEmpty()) { %>
                                    <p style='width: 80%;'><%=pm.get(i).getMethod().replace("_", " ").toLowerCase() %> : <span><%=pm.get(i).getName().toLowerCase() %></span></p>
                                    <% } %>
                                </div>
                            </div>
                                
                            <div class="d-flex justify-content-end mb-3">
                                <button type="button" onclick="confirmOrderReceived('<%=o.getOrder().getOrderid() %>', '<%=o.getShipping().getShippingid() %>')" class="btn btn-primary">Order Completed</button>
                            </div>    
                                
                        </div>
                    </div>
                </div>

            </div>
            
            <% i++; } } else { %>
            
            <div class="empty-cart-container text-center shadow" style="padding: 100px 0;">
                <i class="bi bi-cart-x-fill empty-cart-icon"></i>
                <h2 class="empty-cart-heading">Your order is empty</h2>
                <p class="empty-cart-text">There are no record in your order.</p>
                <button type="button" onclick="window.location.href='allproduct.jsp';" class="btn btn-primary empty-cart-link">Start Shopping</button>
            </div>
            
            <% } %>
        </div>
        
        <jsp:include page="footer.html"/>
        
        <script>
            document.querySelector('.close-button').addEventListener('click', function() {
              hideNotificationBox();
            });
            
            function confirmOrderReceived(oid, sid) {
                if (confirm("Are you sure that the package has already been received?")) {
                    // If user clicks OK, redirect to UpdateOrderServlet with orderid=1 parameter
                    window.location.href = "AdminUpdateOrderStatus?orderid=" + oid + "&shippingid=" + sid + "&orderstatus=<%= OrderCode.COMPLETED.name() %>";
                } 
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

            setTimeout(hideNotificationBox, 5000); // 5000 milliseconds = 5 seconds
        </script>
    </body>
</html>
