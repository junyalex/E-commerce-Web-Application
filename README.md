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
- QueryDSL
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

# Installation & Setup

Prerequisites :
- JDK21 or later
- Maven 3.x
- MySQL Server

1. Clone Repository

```bash
git clone https://github.com/junyalex/E-commerce-Web-Application.git
cd shop ( Root directory )
```

2. Configure the database:
- Open src/main/resources/application.properties.
- Update the spring.datasource.url, spring.datasource.username, and password properties to match your local MySQL setup.
- Create a database schema named shop. <br>
 CREATE DATABASE shop;

3. Run the project
- -   On Windows (using PowerShell):
    ```powershell
    .\mvnw.cmd spring-boot:run
    ```
-   On macOS/Linux:
    ```bash
    ./mvnw spring-boot:run
    ```

The application will be accessible at http://localhost

## Default Administrator Account
(Configuration can be found in `src/main/java/com/example/shop/config/DataInitializer.java`)
- **Email**: admin@test.com  
- **Password**: 1234

## API Endpoints

### Public Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/` | Home page |
| GET | `/members/login` | Login page |
| GET | `/members/login/error` | Page shown on login failure   |
| GET | `/members/new` | Sign up page |
| POST | `/members/new` | Process user registration |
| GET | `/items/{itemType}` | Product listing page by category |
| GET | `/item/{itemId}` | Product details page |
| GET | `/search/items` | AJAX endpoint - returns search results as HTML fragment |

### User Endpoints (Authentication Required)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/cart` | View shopping cart page |
| POST | `/cart` | Add item to cart |
| PATCH | `/cartItem/{cartItemId}` | Update cart item quantity |
| DELETE | `/cartItem/{cartItemId}` | Remove item from cart |
| POST | `/cart/orders` | Place an order |
| GET | `/orders` | View user's order history |
| POST | `/order/{orderId}/cancel` | Cancel a specific order |

### Admin Endpoints (Admin Role Required)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/admin/item/new` | Display page to register    |
| POST | `/admin/item/new` | Process new product creation  |
| GET | `/admin/items or /admin/items/{page}` | Product management and listing page |
| GET | `/admin/item/{itemId}` | Display page to edit a specific product |
| POST | `/admin/item/{itemId}` | Process updates for a specific product |
