<%@page import="dto.UserDto"%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<style>
    a:hover
    {
        color: blue !important;
    }
</style>
<%
    UserDto user = null;
    if(session.getAttribute("user") != null)
    {
        user = (UserDto) session.getAttribute("user");
    }
%>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand text-primary" href="home.jsp"><img src="Images/homelogo.png" alt='homelogo' style="width: 70px;"/></a>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav me-auto my-2 my-lg-0 navbar-nav-scroll" style="bs-scroll-height: 100px;">
          <li class="nav-item">
            <a class="nav-link active" aria-current="page" href="home.jsp">Home</a>
          </li>
          <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" id="navbarScrollingDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
              About
            </a>
            <ul class="dropdown-menu" aria-labelledby="dropdown">
                <li><a class="dropdown-item" href="company.jsp">Company</a></li>
                <li><a class="dropdown-item" href="feedback.jsp">Feedback</a></li>
            </ul>
          </li>
          <li class="nav-item">
              <a class="nav-link" href="allproduct.jsp">Product</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="profile.jsp">Profile</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="cart.jsp">Cart</a>
          </li>
        </ul>
<!--        <form class="d-flex">
          <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
          <button class="btn btn-outline-success" type="submit">Search</button>
        </form>-->
        <% if(user != null) { %>
        
        <div class="d-flex align-items-center justify-content-center">
            <p class="m-0 me-2">Welcome <%=user.getUsername() %></p>
            <button type="button" onclick="window.location.href='LogoutServlet'" class="btn btn-primary">Logout</button>
        </div>
        
        <% } else { %>
        
        <div class="d-flex align-items-center justify-content-center">
            <button type="button" onclick="window.location.href='signin.jsp'" class="btn btn-primary">Login</a>
        </div>
        
        <% } %>
      </div>
    </div>
</nav>
