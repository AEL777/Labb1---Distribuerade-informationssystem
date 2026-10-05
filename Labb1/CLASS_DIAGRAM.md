# Klassdiagram

```mermaid
classDiagram
    direction TB

    namespace Presentation_layer {
        class LoginServlet
        class LogoutServlet
        class ProductServlet
        class CartServlet
    }

    namespace Business_layer {
        class ShopService {
            +login(username, password) User
            +getProducts() List~Product~
            +getProduct(productId) Product
            +addProductToCart(cart, product) void
        }
    }

    namespace Data_layer {
        class UserDAO {
            +findByUsernameAndPassword(username, password) User
        }
        class ProductDAO {
            +findAll() List~Product~
            +findById(id) Product
        }
        class DatabaseConnection {
            +getConnection() Connection
        }
    }

    namespace Model {
        class User {
            -id int
            -username String
        }
        class Product {
            -id int
            -name String
            -price BigDecimal
        }
        class Cart {
            -items Map~Integer, CartItem~
            +addProduct(product) void
            +getItems() Collection~CartItem~
            +getTotalPrice() BigDecimal
        }
        class CartItem {
            -product Product
            -quantity int
            +increaseQuantity() void
            +getRowTotal() BigDecimal
        }
    }

    LoginServlet --> ShopService
    ProductServlet --> ShopService
    CartServlet --> ShopService
    ShopService --> UserDAO
    ShopService --> ProductDAO
    UserDAO --> DatabaseConnection
    ProductDAO --> DatabaseConnection
    UserDAO --> User
    ProductDAO --> Product
    Cart --> CartItem
    CartItem --> Product
    LoginServlet --> User
    CartServlet --> Cart
```

## Lager

Presentation layer består av JSP-filer och servlets. Det lagret tar emot HTTP requests, använder `HttpSession` och skickar data till JSP.

Business layer består av `ShopService`. Det lagret innehåller enkel applikationslogik för login, produktlista och kundvagn.

Data layer består av `UserDAO`, `ProductDAO` och `DatabaseConnection`. Det lagret använder JDBC och PostgreSQL.
