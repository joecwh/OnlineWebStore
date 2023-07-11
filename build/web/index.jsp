<!DOCTYPE html>
<html>
<head>
  <title>Product Page</title>
  <!-- Include Bootstrap CSS -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css">
</head>
<body>
  <div class="container py-5">
    <div class="row">
      <div class="col-md-6">
        <!-- Product Image -->
        <img src="titlelogo.png" class="img-fluid shadow" alt="Product Image">
      </div>
      <div class="col-md-6">
        <!-- Product Details -->
        <h2>Product Name</h2>
        <p>Product Description</p>
        <p>Price: $99.99</p>
        <div class="input-group mb-3 d-flex align-items-center ">
            <span class="me-2">Quantity </span>

            <button class="btn btn-outline-secondary border" type="button" id="minusButton">-</button>
            <input type="text" class="text-center border" style="padding: 6px; width: 40px;" value="1" id="quantityInput" name="quantity"  oninput="validateNumberInput(this)">
            <button class="btn btn-outline-secondary border" type="button" id="plusButton">+</button>
            
            <span class="ms-2">10 stock remaining</span>
        </div>
        <button class="btn btn-primary" id="addToCartButton">Add to Cart</button>
      </div>
    </div>
  </div>

  <!-- Include Bootstrap JavaScript -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"></script>

  <!-- JavaScript code -->
  <script>
    // Quantity buttons functionality
    var quantityInput = document.getElementById('quantityInput');
    var minusButton = document.getElementById('minusButton');
    var plusButton = document.getElementById('plusButton');

    minusButton.addEventListener('click', function() {
      var quantity = parseInt(quantityInput.value) || 0;
      if (quantity > 1) {
        quantityInput.value = quantity - 1;
      }
    });

    plusButton.addEventListener('click', function() {
      var quantity = parseInt(quantityInput.value) || 0;
      quantityInput.value = quantity + 1;
    });

    // Add to Cart button functionality
    var addToCartButton = document.getElementById('addToCartButton');
    addToCartButton.addEventListener('click', function() {
      var quantity = parseInt(quantityInput.value) || 0;
      // Add the product to the cart with the selected quantity
      // ...
    });
    
    function validateNumberInput(input) {
  	input.value = input.value.replace(/\D/g, ''); // Remove non-digit characters
    }
  </script>
</body>
</html>
