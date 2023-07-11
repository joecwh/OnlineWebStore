<%-- 
    Document   : company
    Created on : May 13, 2023, 6:40:44 PM
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
<html lang="en">
<head>
  <!-- Theme Made By www.w3schools.com -->
    <link rel="icon" href="Images/titlelogo.png" type="image/icon type">
  <title>Company</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
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

    <div class="container-fluid bg-dark" style="height: 400px; display: flex; flex-direction: column; justify-content: center; align-items: center;">
        <div class="text-light text-center">
            <h1>Den's Toy</h1> 
            <p>Where Playtime Comes to Life!</p> 
        </div>
        <form method="post" action="EmailSubscribe">
        <div class="input-group">
            <input type="email" name="email" class="form-control" size="50" placeholder="Email Address" maxlength="250" required>
            <div class="input-group-btn">
                <button type="submit" class="btn btn-danger">Subscribe</button>
            </div>
        </div>
        </form>
    </div>

    <!-- Container (About Section) -->
    <div class="container-fluid my-5">
        <div class="row container mx-auto text-center align-items-center" style=" width: 75%;">
            <div class="col-sm-12">
                <h2 class="mb-4">About Company</h2><br>
                <h5>Welcome to Den's Toy, where we believe that playtime is a vital part of childhood. We're passionate about creating fun and innovative toys that promote learning and imagination. Our company is dedicated to providing safe toys made of high-quality materials for kids of all ages. From plush toys to building blocks, we have a wide variety of products that cater to different interests and skills, including special needs children. </h5><br>
                <i style="color: grey;">Our team of designers and engineers work tirelessly to create toys that help develop essential skills such as problem-solving and creativity. At Den's Toy, we're committed to offering exceptional customer service and quality products to ensure that every child enjoys playtime.</i>
            </div>
            <div class="col-sm-12 text-center">
                <i class='fas fa-chart-bar' style='font-size:250px;color:red'></i>
            </div>
        </div>
    </div>

    <div class="container-fluid bg-light">
        <div class="row container mx-auto text-center" style="width: 75%;">
            <div class="col my-5">
                <h2 class="mb-4">Our Values</h2><br>
                <i><h6><strong class="fs-4">Our mission</strong> is to create safe and innovative toys that inspire creativity and promote learning. We are dedicated to providing children of all ages with fun and engaging playtime experiences that help develop essential skills such as problem-solving and social interaction. We strive to offer a diverse range of products that cater to different interests, including special needs children, while maintaining our commitment to safety and quality.</h6><br></i>
                <i><strong class="fs-5">Our vision</strong> at Den's Toy is to become a leading toy company that is recognized for creating innovative, educational, and safe toys. We aim to inspire children to imagine, learn, and grow while fostering a love of playtime. We aspire to create a world where every child has access to high-quality toys that help them develop essential skills and encourage lifelong learning. Our vision is to be a responsible and ethical company that contributes positively to the communities we serve.</i>
            </div>
        </div>
    </div>

    <!-- Container (Services Section) -->
    <div id="services" class="container-fluid text-center my-5">
        <h2 class="mb-5">SERVICES</h2>
        <h4>What we offer</h4>
        <br>
        <div class="row slideanim">
            <div class="col-sm-4">
                <i class='fa fa-power-off mb-2' style='font-size:48px;color:red'></i>
                <h4>POWER</h4>
                <p>Empowering play and learning</p>
            </div>
            <div class="col-sm-4">
                <i class='fa fa-heart mb-2' style='font-size:48px;color:red'></i>
                <h4>LOVE</h4>
                <p>Toys made with love</p>
            </div>
            <div class="col-sm-4">
                <i class="fa fa-briefcase mb-2" style="font-size:48px;color:red"></i>
                <h4>JOB DONE</h4>
                <p>Quality products, exceptional service</p>
            </div>
        </div>
        <br><br>
        <div class="row slideanim">
            <div class="col-sm-4">
                <i class='fa fa-leaf mb-2' style='font-size:48px;color:red'></i>
                <h4>GREEN</h4>
                <p>Committed to sustainability</p>
            </div>
            <div class="col-sm-4">
                <i class='fa fa-certificate mb-2' style='font-size:48px;color:red'></i>
                <h4>CERTIFIED</h4>
                <p>Trusted safety and quality</p>
            </div>
            <div class="col-sm-4">
                <i class='fa fa-wrench mb-2' style='font-size:48px;color:red'></i>
                <h4 style="color:#303030;">HARD WORK</h4>
                <p>Innovative toys, hard play</p>
            </div>
        </div>
    </div>

    <div class="container-fluid bg-light text-center pb-5">
        <h2 style="padding: 50px 0;">What our customers say</h2>
        <div id="carouselExampleControls" class="carousel carousel-dark slide bg-light pb-5" data-bs-ride="carousel">
            <div class="carousel-inner">
                <div class="carousel-item active">
                    <i><h6>"This company is the best. I am so happy with the result!"<br><span>Michael Roe, Vice President, Comment Box</span></h6></i>
                </div>
                <div class="carousel-item">
                    <i><h6>"One word... WOW!!"<br><span>John Doe, Salesman, Rep Inc</span></h6></i>
                </div>
                <div class="carousel-item">
                    <i><h6>"Could I... BE any more happy with this company?"<br><span>Chandler Bing, Actor, FriendsAlot</span></h6></i>
                </div>
            </div>
            <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </div>
    </div>

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
    
$(document).ready(function(){
  // Add smooth scrolling to all links in navbar + footer link
  $(".navbar a, footer a[href='#myPage']").on('click', function(event) {
    // Make sure this.hash has a value before overriding default behavior
    if (this.hash !== "") {
      // Prevent default anchor click behavior
      event.preventDefault();

      // Store hash
      var hash = this.hash;

      // Using jQuery's animate() method to add smooth page scroll
      // The optional number (900) specifies the number of milliseconds it takes to scroll to the specified area
      $('html, body').animate({
        scrollTop: $(hash).offset().top
      }, 900, function(){
   
        // Add hash (#) to URL when done scrolling (default click behavior)
        window.location.hash = hash;
      });
    } // End if
  });
  
  $(window).scroll(function() {
    $(".slideanim").each(function(){
      var pos = $(this).offset().top;

      var winTop = $(window).scrollTop();
        if (pos < winTop + 600) {
          $(this).addClass("slide");
        }
    });
  });
  
  
})

           
</script>

</body>
</html>

