<%-- 
    Document   : adminviewupdateproduct
    Created on : Jun 23, 2023, 10:40:55 PM
    Author     : Lenovo
--%>

<%@page import="enumeration.UserCode"%>
<%@page import="dto.UserDto"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Categories"%>
<%@page import="java.util.List"%>
<%@page import="model.Category"%>
<%@page import="java.io.Serializable"%>
<%@page import="model.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="AdminGetProduct"/>
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

    Product product = null;
    String productImg = "";
    if(session.getAttribute("product") != null)
    {
        product = (Product) session.getAttribute("product");
        Serializable imageBlob = (Serializable) product.getProductimg();
        byte[] imageBytes = (byte[]) imageBlob;
        productImg = java.util.Base64.getEncoder().encodeToString(imageBytes);
    }
    
    Category admincategory = null;
    if(session.getAttribute("admincategory") != null)
    {
        admincategory = (Category) session.getAttribute("admincategory");
    }
    
    List<Categories> categories = new ArrayList();
    if(session.getAttribute("categories") != null)
    {
        categories = (List<Categories>) session.getAttribute("categories");
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
        <title>Product <%=action %></title>
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
            <p class="text-white fs-5 fw-bold mt-4 text-center">Product <%=action %></p>
        </div>
        
        <div class="container shadow mb-3" style="margin-top: 100px; position: relative; min-height: 530px; background-color: white;">
            <%  if(product != null) {  %>
            
                <form method="post" action="AdminUpdateProduct" enctype="multipart/form-data" class="mx-auto px-3 py-3">
                    <a href="adminproductmanagement.jsp" class="btn btn-primary back-button my-4">Back</a>
                    <div class="form-floating mb-4">
                        <h6>Product ID</h6>
                        <input type="text" class="form-control py-3" id="productid" placeholder="productid" name="productid" value="<%=product.getProductid() %>" readonly/>
                    </div>
                    <div class="form-floating mb-4">
                        <h6>Product Name</h6>
                        <input type="text" class="form-control py-3" id="productname" placeholder="productname" maxlength="250" name="productname" value="<%=product.getProductname() %>" <%=action.equals("Review") ? "readonly" : "required" %> />
                    </div>
                    <div class="form-floating mb-4">
                        <h6>Description</h6>
                        <input type="text" class="form-control py-3" id="description" placeholder="description" maxlength="250" name="description" value="<%=product.getProductdesc() %>" <%=action.equals("Review") ? "readonly" : "required" %> />
                    </div>
                    <div class="form-floating mb-4">
                        <h6>Category</h6>
                        <select class="form-select py-3" aria-label="category" name="categories" <%=action.equals("Review") ? "disabled" : "required" %>>
                            <% for(Categories c : categories) { %>
                                <option value="<%=c.getCategoriesid() %>" <%=c.getCategoriesid().equals(admincategory.getCategoriesid()) ? "selected" : "" %> ><%=c.getCategoryname() %></option>
                            <% } %>
                        </select>
                    </div>
                    <div class="form-floating mb-4">
                        <h6>Price</h6>
                        <input type="text" class="form-control py-3" id="price" placeholder="price" name="price" maxlength="250" value="<%=product.getPrice() %>" <%=action.equals("Review") ? "readonly" : "required" %> />
                    </div>
                    <div class="form-floating mb-4">
                        <h6>Quantity</h6>
                        <input type="number" class="form-control py-3" id="quantity" placeholder="quantity" name="quantity" maxlength="250" value="<%=product.getQuantity() %>" <%=action.equals("Review") ? "readonly" : "required" %> />
                    </div>
                    <div class="mb-4">
                        <h6>Image</h6>
                        <img src="data:image/jpeg;base64,<%=productImg %>" id="previewImage" class="shadow bg-body rounded" width="100%" height="450px" alt="Product Image">
                        <% if(action.equals("Update")) { %>
                        
                            <input type="file" class="form-control my-4" id="fileInput" name="newproductimg">
                            <div class="d-grid gap-2 col-3 mx-auto">
                                <input class="btn btn-primary btn-lg mb-4" type="submit" name="submit" value="Save"/>
                            </div>
                        
                        <% } %>
                    
                    </div>
                </form>
            
            <%  } else { %>
            
                <div class="container">
                    <div class="row justify-content-center my-5">
                        <div class="col-md-6 text-center">
                            <h3>No staff found.</h3>
                            <p>Something went wrong, please try again later.</p>
                            <a href="adminproductmanagement.jsp" class="btn btn-primary back-button">Back</a>
                        </div>
                    </div>
                </div>
                
            <%  } %>
        </div>
        
        <script>
            const fileInput = document.getElementById('fileInput');
            const previewImage = document.getElementById('previewImage');

            fileInput.addEventListener('change', function() {
              const file = fileInput.files[0];
              const reader = new FileReader();

              reader.onload = function(e) {
                previewImage.src = e.target.result;
              };

              reader.readAsDataURL(file);
            });
            
            
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
            
            function redirectToAllCategories() 
            {
                window.location.href = "https://www.example.com"; // Replace with the desired URL
            }
            
            function confirmDelete() {
                if (confirm("Are you sure you want to delete?")) {
                    return true; // submit the form
                } else {
                    return false; // do not submit the form
                }
            }
        </script>

        <jsp:include page="adminfooter.html"/>
    </body>
</html>
