# Details
  1.  Creator : Joecwh
  2.  Project Name : Den's Toy Online Web Store
  3.  Demo video link : [[https://studio.youtube.com/video/KpHIfGQ6avY/edit](https://www.youtube.com/watch?v=I0HMhAPtN4w)](https://www.youtube.com/watch?v=I0HMhAPtN4w&t=49s)
  
# Description
  Personal project with using J2EE as development framework and Bootstrap 5 as frontend stylish for manager, admin and customer to perform functionalites.

# Project software utilize
  1.  IDE : Netbean 8.2
  2.  Database : Derby Database
  3.  Jar : ITextPdf 5.5.9
  
# Database Setup
  The DENS.zip file is the database file, place it under your netbean derby folder.
  For Example "C:\Users\[YOUR_LAPTOP_MODEL]\AppData\Roaming\NetBeans\Derby"

# Email SMTP Setup
  1.  In the enumeration folder, find EmailCode and amend the COMPANY_EMAIL to your gmail address, COMPANY_SMTP_PASSWORD to your gmail app password.
  2.  If you're using different SMTP, instead of changing settings in EmailCode, you have to direct to service folder and find Email Service. Change
      email properties settings in SendEmail() method.

# Content Setup
  1.  In the web folder, open Admin.jsp and login to the Admin page as Manager credential with same default username and password "MANAGER".
  2.  Direct to Role selection in navigation and select Admin, add an admin and verify account in your(admin) inbox.
  3.  Direct to Product in navigation, scroll down to add product category and then add a product to show in home page. 
  4.  Direct to Banner in navigation to add home page banner.
  5.  Add Discount Code (optional)
  6.  logout to direct back to signin.jsp.
  7.  click logo to go home page and perform customer functionalities.

# Functionalities
  Admin
  1.  CRUD New Customer and Admin
  2.  CRUD New Product | Category
  3.  CRUD New Discount
  4.  Add | Delete New Online Payment Method
  5.  CRUD New Banner
  6.  All Customer Functionalities
  
  Manager
  1.  All Admin | Customer Functionalities
  2.  View Manager Dashboard
  3.  Generate | View | Delete Report

  Customer
  1.  Add Product to Cart
  2.  Edit Profile
  3.  Change | Reset password
  4.  Subscribe News
  5.  Send Feedback
  6.  Checkout Cart
  7.  Direct Buy Now

  Guest
  1.  Subscribe News
  2.  Send Feedback
  3.  Direct Buy Now
