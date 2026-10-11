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
            +login(username, password) UserDTO
            +getProducts() List~ProductDTO~
            +addProductToCart(cart, productId) ProductDTO
            +getCartDTO(cart) CartDTO
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

    namespace DTO {
        class UserDTO {
            -id int
            -username String
        }

        class ProductDTO {
            -id int
            -name String
            -price BigDecimal
        }

        class CartDTO {
            -items List~CartItemDTO~
            -totalPrice BigDecimal
            +isEmpty() boolean
        }

        class CartItemDTO {
            -productId int
            -productName String
            -price BigDecimal
            -quantity int
            -rowTotal BigDecimal
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

    ShopService --> UserDTO
    ShopService --> ProductDTO
    ShopService --> CartDTO
    CartDTO --> CartItemDTO

    LoginServlet --> UserDTO
    ProductServlet --> ProductDTO
    CartServlet --> ProductDTO
    CartServlet --> Cart
```

## Lager

Presentation layer består av JSP-filer och servlets. Det lagret tar emot HTTP requests, använder `HttpSession` och arbetar med DTO-objekt för data som skickas mellan presentation och business layer.

Business layer består av `ShopService`. Det lagret innehåller applikationslogik för login, produktlista och kundvagn. `ShopService` använder modellobjekt internt och omvandlar dem till DTO-objekt innan data skickas vidare till presentation layer.

Data layer består av `UserDAO`, `ProductDAO` och `DatabaseConnection`. Det lagret använder JDBC och PostgreSQL och arbetar med modellklasserna `User` och `Product`.

DTO-klasserna `UserDTO`, `ProductDTO`, `CartDTO` och `CartItemDTO` används för att transportera data mellan business layer och presentation layer utan att exponera de interna modellobjekten direkt.