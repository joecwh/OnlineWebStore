<%-- 
    Document   : adminproduct
    Created on : May 14, 2023, 1:54:41 AM
    Author     : Lenovo
--%>

<%@page import="enumeration.UserCode"%>
<%@page import="dto.UserDto"%>
<%@page import="model.Categories"%>
<%@page import="model.Product"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Banner, java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="AdminGetAllProduct"/>
<%
    List<Product> products = new ArrayList();
    List<Categories> categories = new ArrayList();
    
    if(session.getAttribute("adminproducts") != null)
    {
        products = (List<Product>) session.getAttribute("adminproducts");
    }
    
    if(session.getAttribute("admincategories") != null)
    {
        categories = (List<Categories>) session.getAttribute("admincategories");
    }
    
    String alertMsg = "";
    if(request.getParameter("alert") != null)
    {
        alertMsg = request.getParameter("alert");
    }
    
    UserDto user = null;
    if(session.getAttribute("user") != null)
    {
        user = (UserDto) session.getAttribute("user");
    }
    else
    {
        response.sendRedirect("signin.jsp?alert=" + UserCode.SESSION_EXPIRED.getMessage());
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="icon" href="Images/titlelogo.png" type="image/icon type">
        <title>Product Management</title>
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
            <p class="text-white fs-5 fw-bold mt-4 text-center">Product Management</p>
        </div>
        
        <div class="container" style="margin-top: 100px; position: relative; min-height: 530px;">
            <div class="accordion" id="accordionExample" style="background-color: white;">
                <div class="accordion-item">
                    <h2 class="accordion-header" id="headingOne">
                        <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                            Add new product
                        </button>
                    </h2>
                    <div id="collapseOne" class="accordion-collapse collapse show" aria-labelledby="headingOne" data-bs-parent="#accordionExample">
                        <form method="post" action="AdminAddProduct" enctype="multipart/form-data">
                            <div class="accordion-body">
                                <div class="form-floating mb-3">
                                    <input type="text" class="form-control" id="productname" placeholder="Product Name" name="productname" required>
                                    <label for="productname">Product Name</label>
                                </div>
                                <div class="form-floating mb-3">
                                    <input type="text" class="form-control" id="productdesc" placeholder="Description" name="productdesc" required>
                                    <label for="productdesc">Description</label>
                                </div>
                                <div class="form-floating mb-3">
                                    <select class="form-select" id="selectOption" aria-label="Select Option" name="categories" required>
                                        <option disabled selected value="">choose one</option>
                                        <%
                                            if(!categories.isEmpty()) {
                                                for(Categories c : categories) {
                                        %>
                                        
                                            <option value="<%=c.getCategoriesid() %>"><%=c.getCategoryname() %></option>
                                        
                                        <%  } } %>
                                    </select>
                                    <label for="selectOption">Category</label>
                                </div>
                                <div class="form-floating mb-3">
                                    <input type="text" class="form-control" id="price" placeholder="Price" name="price" required>
                                    <label for="price">Price</label>
                                </div>
                                <div class="form-floating mb-3">
                                    <input type="text" class="form-control" id="quantity" placeholder="Quantity" name="quantity" required>
                                    <label for="quantity">Quantity</label>
                                </div>
                                <div class="mb-3">
                                    <input class="form-control" id="formFileLg" name="productimg" type="file" required>
                                </div>
                                    
                                <input type="submit" class="btn btn-primary" id="submit" value="ADD">
                                <input type="button" class="btn btn-primary" id="swapButton1" value="VIEW ALL">
                            </div>
                        </form>
                    </div>
                </div>
            </div>
                                    
            <div class="accordion" id="accordionExample2" style="background-color: white;">
                <div class="accordion-item">
                    <h2 class="accordion-header" id="headingOne">
                        <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne2" aria-expanded="true" aria-controls="collapseOne">
                            Add new category
                        </button>
                    </h2>
                    <div id="collapseOne2" class="accordion-collapse collapse show" aria-labelledby="headingOne" data-bs-parent="#accordionExample2">
                        <form method="post" action="AdminAddCategories">
                            <div class="accordion-body">
                                <div class="form-floating mb-3">
                                    <input type="text" class="form-control" id="categoryname" placeholder="Category Name" name="categoryname">
                                    <label for="categoryname">Category Name</label>
                                </div>
                                <div class="form-floating mb-3">
                                    <input type="text" class="form-control" id="categorydesc" placeholder="Category Description" name="categorydesc">
                                    <label for="categorydesc">Category Description</label>
                                </div>
                                <input type="submit" class="btn btn-primary" id="submit" value="ADD">
                                <input type="button" class="btn btn-primary" id="swapButton2" value="VIEW ALL">
                            </div>
                        </form>
                    </div>
                </div>
            </div>
                   
            <div class="row">
                <div class="col-md-12">
                    <table class="table table-striped text-center" style="background-color: white;" id="table1">
                        <thead>
                            <tr>
                                <th scope="col">No</th>
                                <th scope="col">Product Name</th>
                                <th scope="col">Price</th>
                                <th scope="col">Quantity</th>
                                <th scope="col" colspan="3">Functionality</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                if(!products.isEmpty()){
                                    int i = 1;
                                    for(Product p : products){
                            %>
                            <tr>
                                <th scope="row"><%=i%></th>
                                <td><%=p.getProductname() %></td>
                                <td><%=p.getPrice() %></td>
                                <td><%=p.getQuantity() %></td>
                                <form method="post" action="adminviewupdateproduct.jsp?action=Review&productid=<%=p.getProductid() %>">
                                    <input type="hidden" name="bannerid" value="<%=p.getProductid() %>"/>
                                    <td><input type="submit" name="submit" class="text-primary" style="border: none; padding: 0; background-color: transparent;" value="View"/></td>
                                </form>
                                <form method="post" action="adminviewupdateproduct.jsp?action=Update&productid=<%=p.getProductid() %>">
                                    <input type="hidden" name="bannerid" value="<%=p.getProductid() %>"/>
                                    <td><input type="submit" name="submit" class="text-warning" style="border: none; padding: 0; background-color: transparent;" value="Edit"/></td>
                                </form>
                                <form method="post" action="AdminDeleteProduct" id="deleteproduct">
                                    <input type="hidden" name="productid" value="<%=p.getProductid() %>"/>
                                    <td><input type="submit" name="submit" onclick="return confirmDelete()" class="text-danger" style="border: none; padding: 0; background-color: transparent;" value="Delete"/></td>
                                </form>
                            </tr>
                            <%      i++; }   
                                } else { %> 
                            <tr>
                                <td colspan="5">
                                    <strong>No product found.</strong> Please try a different search criteria.
                                </td>
                            </tr>
                            <%  } %>
                        </tbody>
                    </table>
                </div>
                
                <table class="table table-striped text-center d-none" style="background-color: white;" id="table2">
                    <thead>
                        <tr>
                            <th scope="col">No</th>
                            <th scope="col">Category Name</th>
                            <th scope="col">Description</th>
                            <th scope="col" colspan=3>Functionality</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            if(!categories.isEmpty()){
                                int i = 1;
                                for(Categories c : categories){
                        %>
                        <tr>
                            <th scope="row"><%=i%></th>
                            <td><%=c.getCategoryname() %></td>
                            <td><%=c.getCategorydesc() %></td>
                            <form method="post" action="adminviewupdatecategories.jsp?action=Review&categoriesid=<%=c.getCategoriesid() %>">
                                <input type="hidden" name="categoriesid" value="<%=c.getCategoriesid() %>"/>
                                <td><input type="submit" name="submit" class="text-primary" style="border: none; padding: 0; background-color: transparent;" value="View"/></td>
                            </form>
                            <form method="post" action="adminviewupdatecategories.jsp?action=Update&categoriesid=<%=c.getCategoriesid() %>">
                                <input type="hidden" name="categoriesid" value="<%=c.getCategoriesid() %>"/>
                                <td><input type="submit" name="submit" class="text-warning" style="border: none; padding: 0; background-color: transparent;" value="Edit"/></td>
                            </form>
                            <form method="post" action="AdminDeleteCategories" id="deleteCategories">
                                <input type="hidden" name="categoriesid" value="<%=c.getCategoriesid() %>"/>
                                <td><input type="submit" name="submit" onclick="return confirmDelete()" class="text-danger" style="border: none; padding: 0; background-color: transparent;" value="Delete"/></td>
                            </form>
                        </tr>
                        <%      i++; }   
                            } else { %> 
                        <tr>
                            <td colspan="5">
                                <strong>No category found.</strong> Please try a different search criteria.
                            </td>
                        </tr>
                        <%  } %>
                    </tbody>
                </table>
            </div>
        </div>
                
        <script>
            var table1 = document.getElementById('table1');
            var table2 = document.getElementById('table2');

            document.getElementById('swapButton1').addEventListener('click', function() {
                swapTables('table2', 'table1');
            });

            document.getElementById('swapButton2').addEventListener('click', function() {
                swapTables('table1', 'table2');
            });

            function swapTables(tableId1, tableId2) {
                var currentTable = document.getElementById(tableId1);
                var otherTable = document.getElementById(tableId2);

                currentTable.classList.add('d-none');
                otherTable.classList.remove('d-none');
            }
            
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
        
        <jsp:include page="adminfooter.html"/>
    </body>
</html>
