<%-- 
    Document   : adminviewupdateorder
    Created on : Jul 1, 2023, 6:58:05 PM
    Author     : Lenovo
--%>

<%@page import="enumeration.UserCode"%>
<%@page import="dto.UserDto"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="model.PaymentMethod"%>
<%@page import="enumeration.OrderCode"%>
<%@page import="enumeration.ShippingCode"%>
<%@page import="java.io.Serializable"%>
<%@page import="dto.CartDto"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dto.OrderDto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="AdminGetOrder"/>
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
    {
        action = request.getParameter("action");
    }
    
    OrderDto order = null;
    List<String> productImg = new ArrayList();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    if(session.getAttribute("order") != null)
    {
        order = (OrderDto) session.getAttribute("order");
        for(CartDto c : order.getCart())
        {
            Serializable imageBlob = (Serializable) c.getProductImg();
            byte[] imageBytes = (byte[]) imageBlob;
            productImg.add(java.util.Base64.getEncoder().encodeToString(imageBytes));
        }
    }
    
    PaymentMethod pm = null;
    if(session.getAttribute("singlePm") != null)
    {
        pm = (PaymentMethod) session.getAttribute("singlePm");
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
        <title>Order & Shipping <%=action %></title>
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
            <p class="text-white fs-5 fw-bold mt-4 text-center">Order & Shippping <%=action %></p>
        </div>
        
        <div class="container shadow mb-3" style="margin-top: 100px; position: relative; min-height: 530px; background-color: white;">
            <%  if(order != null) {  %>
            
                <div class="mx-auto px-3 py-3">
                    <a href="adminordermanagement.jsp" class="btn btn-primary back-button my-4">Back</a>
                    
                    <form method="post" action="AdminUpdateOrderStatus" class="border mb-3 p-4 shadow">
                        <h5 class="mb-4">Order</h5>
                        <div class="row g-3">
                            <div class="col-md">
                                <div class="form-floating">
                                    <input type="text" class="form-control" id="floatingInputGrid" name="orderid" value="<%=order.getOrder().getOrderid() %>" readonly>
                                    <label for="floatingInputGrid">Order ID</label>
                                </div>
                            </div>
                            <div class="col-md">
                                <div class="form-floating">
                                    <select class="form-select" id="floatingSelectGrid" name="orderstatus" aria-label="Floating label select example" <%= (action.equals("Review")) ? "disabled" : "" %>>
                                        <% for(OrderCode oc : OrderCode.values()) { %>
                                        <option value="<%=oc.name() %>" <%=(order.getOrder().getStatus().equals(oc.name())) ? "selected" : "" %>><%=oc.name() %></option>
                                        <% } %>
                                    </select>
                                    <label for="floatingSelectGrid">Order status</label>
                                </div>
                            </div>
                            <div class="col-md d-flex align-items-center">
                                <input type="hidden" name="shippingid" value="<%=order.getShipping().getShippingid() %>">
                                <button type="submit" class="btn btn-primary btn-lg" <%= (action.equals("Review")) ? "disabled" : "" %>>Save</button>
                            </div>
                        </div>
                    </form>
                    
                    <form method="post" action="AdminUpdateShippingStatus" class="border my-5 p-4 shadow">
                        <h5 class="mb-4">Shipping</h5>
                        <div class="row g-3">
                            <div class="col-md">
                                <div class="form-floating">
                                    <input type="date" class="form-control" id="floatingInputGrid" name="expecteddate" value="<%=dateFormat.format(order.getShipping().getExpectedreachdate()) %>" <%= (action.equals("Review")) ? "disabled" : "" %>>
                                    <label for="floatingInputGrid">Excepted Arrival Date</label>
                                </div>
                            </div>
                            <div class="col-md">
                                <div class="form-floating">
                                    <select class="form-select" id="floatingSelectGrid" name="shippingstatus" aria-label="Floating label select example" <%= (action.equals("Review")) ? "disabled" : "" %>>
                                        <% for(ShippingCode sc : ShippingCode.values()) { %>
                                        <option value="<%=sc.name() %>" <%=(order.getShipping().getStatus().equals(sc.name())) ? "selected" : "" %>><%=sc.name() %></option>
                                        <% } %>
                                    </select>
                                    <label for="floatingSelectGrid">Shipping status</label>
                                </div>
                            </div>
                            <div class="col-md d-flex align-items-center">
                                <input type="hidden" name="orderid" value="<%=order.getOrder().getOrderid() %>">
                                <input type="hidden" name="shippingid" value="<%=order.getShipping().getShippingid()%>">
                                <button type="submit" class="btn btn-primary btn-lg" <%= (action.equals("Review")) ? "disabled" : "" %>>Save</button>
                            </div>
                        </div>
                    </form>

                    <div class="accordion" id="accordionExample" style="background-color: white;">
                        <div class="accordion-item">
                            <h2 class="accordion-header" id="headingOne">
                                <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                                    Cart Details
                                </button>
                            </h2>
                            <div id="collapseOne" class="accordion-collapse collapse show" aria-labelledby="headingOne" data-bs-parent="#accordionExample">
                                <div class="accordion-body">
                                    
                                    <div class="mb-4 p-3 border" style="">

                                        <div class="mb-3">
                                            <table class="table">
                                                <tbody>

                                                    <%  int i = 0; 
                                                        for(CartDto c : order.getCart()) { 
                                                    %>

                                                    <tr class="">
                                                        <td style="width: 50%;">
                                                            <img src="data:image/jpeg;base64,<%= productImg.get(i) %>" class="border me-2" alt="Product Image" style="width: 50px; height: 50px;"/>
                                                            <span class="overflow-hidden" style=""><%=c.getProductName() %></span>
                                                        </td>
                                                        <td style="width: 25%;" class="text-end text-secondary">Qty :  <%=c.getQuantity() %></td>
                                                        <td style="width: 25%;" class="text-end text-secondary">RM <%=c.getTotal() %></td>
                                                    </tr>

                                                    <% i++; } %>

                                                </tbody>
                                            </table>
                                        </div>

                                        <div class="mb-2 row pe-3">
                                            <div class='col'></div>
                                            <table class="col table w-50">
                                                <tbody>
                                                    <tr class='text-secondary'>
                                                        <td class=''>Subtotal</td>
                                                        <td class="text-end">RM <%=String.format("%.2f", order.getTransactions().getSubtotal()) %></td>
                                                    </tr>
                                                    <tr class='text-secondary'>
                                                        <td>Shipping Fee</td>
                                                        <td class="text-end">RM <%=String.format("%.2f", order.getTransactions().getShippingfee()) %></td>
                                                    </tr>
                                                    <tr class='text-secondary'>
                                                        <td>Sales Tax</td>
                                                        <td class="text-end">RM <%=String.format("%.2f", order.getTransactions().getTaxes()) %></td>
                                                    </tr>
                                                    <% if(order.getTransactions().getDiscountcode() != null || !order.getTransactions().getDiscountcode().equals("")) { %>

                                                    <tr class='text-secondary'>
                                                        <td>Coupon</td>
                                                        <td class="text-end"><%=order.getTransactions().getDiscountcode() %></td>
                                                    </tr>

                                                    <% } %>
                                                    <tr>
                                                        <td><h5>Total</h5></td>
                                                        <td class="text-end"><h5>RM <%=String.format("%.2f", order.getTransactions().getTotal()) %></h5></td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </div>

                                        <div class='border p-3 row g-2 mx-0 mb-3'>
                                            <div class='col-sm-6 border-bottom'>
                                                <h6 class='mb-3'>Shipping Address</h6>

                                                <p style='width: 80%;'><%=order.getShipping().getReceivername() %><br>
                                                    <span><%=order.getShipping().getShippingaddress() %></span>
                                                </p>

                                            </div>

                                            <div class='col-sm-6 border-bottom'>
                                                <h6 class='mb-3'>Payment method</h6>
                                                <% if(pm != null) { %>
                                                <p style='width: 80%;'><%=pm.getMethod().replace("_", " ").toLowerCase() %> : <span><%=pm.getName().toLowerCase() %></span></p>
                                                <% } %>
                                            </div>
                                        </div>
                                                    
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                    
                </div>
            
            <%  } else { %>
            
                <div class="container">
                    <div class="row justify-content-center my-5">
                        <div class="col-md-6 text-center">
                            <h3>No order found.</h3>
                            <p>Something went wrong, please try again later.</p>
                            <a href="adminordermanagement.jsp" class="btn btn-primary back-button">Back</a>
                        </div>
                    </div>
                </div>
                
            <%  } %>
        </div>
        
        <script>
            function confirmDelete() {
                var result = confirm("Are you sure you want to delete?");
                if (result) {
                    location.reload(); // Refresh the page
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
        
        <jsp:include page="adminfooter.html"/>
    </body>
</html>
