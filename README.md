# 🛒 E-Commerce Web Application
This is a full-stack e-commerce web application built with Spring Boot for the backend and Thymeleaf for the server-side rendered frontend. The application provides a complete online shopping experience,
from user registration and product browsing to a fully functional shopping cart and order management system.

# Features
For customers : 
- User Authentication : Secure user registration and login/logout functionality.
- Product Search : Search for products by name.
- Shopping Cart :
    - Add / Remove items to cart.
    - Adjust quantity of items in the cart.
- Order Management :
    - Place orders from the cart.
    - View order history.
    - Cancel orders.
 
For administrators :
-  Product Management :
      - Register new products with multiple images up to 5.
      - Update existing product details.
      - View a paginated list of all registered products.
- Role Based Access :
      -  Secure admin-only pages for managing the store.


# Technology Stack
Backend : 
- Spring Boot
- Spring Security
- Spring Data JPA
- Spring Web
- MySQL, H2 (for development)

Frontend :
- HTML, CSS, JavaScript
- Thymeleaf

Testing : 
- JUnit5

Build Tool : 
- Maven

# Project Structure

```
     .
     ├── src
     │   ├── main
     │   │   ├── java/com/example/shop
     │   │   │   ├── config       # Security, MVC, Audit configurations
     │   │   │   ├── constant     # Enum types (Roles, Statuses)
     │   │   │   ├── controller   # Controllers
     │   │   │   ├── dto          # Data Transfer Objects
     │   │   │   ├── entity       # JPA entities
     │   │   │   ├── repository   # Repositories
     │   │   │   └── service      # Services
     │   │   └── resources
     │   │       ├── static       # CSS, JS
     │   │       ├── templates    # Thymeleaf HTML templates
     │   │       └── application.properties # Spring Boot configuration
     │   └── test                 # JUnit5 tests
     ├── pom.xml                  # Maven project configuration
```

