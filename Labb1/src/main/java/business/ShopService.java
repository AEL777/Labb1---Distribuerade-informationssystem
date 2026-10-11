package business;

import data.ProductDAO;
import data.UserDAO;
import dto.CartDTO;
import dto.CartItemDTO;
import dto.ProductDTO;
import dto.UserDTO;
import model.Cart;
import model.CartItem;
import model.Product;
import model.User;

import java.sql.SQLException;
import java.util.List;

public class ShopService {
    private final UserDAO userDAO;
    private final ProductDAO productDAO;

    public ShopService() {
        this(new UserDAO(), new ProductDAO());
    }

    public ShopService(UserDAO userDAO, ProductDAO productDAO) {
        this.userDAO = userDAO;
        this.productDAO = productDAO;
    }

    public UserDTO login(String username, String password) throws SQLException {
        if (username == null || password == null || username.isBlank() || password.isBlank()) {
            return null;
        }

        User user = userDAO.findByUsernameAndPassword(username.trim(), password);

        if (user == null) {
            return null;
        }

        return new UserDTO(
                user.getId(),
                user.getUsername()
        );
    }

    public List<ProductDTO> getProducts() throws SQLException {
        return productDAO.findAll()
                .stream()
                .map(product -> new ProductDTO(
                        product.getId(),
                        product.getName(),
                        product.getPrice()
                ))
                .toList();
    }

    public ProductDTO addProductToCart(Cart cart, int productId) throws SQLException {
        if (cart == null) {
            return null;
        }

        Product product = productDAO.findById(productId);

        if (product == null) {
            return null;
        }

        cart.addProduct(product);

        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getPrice()
        );
    }

    public CartDTO getCartDTO(Cart cart) {
        if (cart == null) {
            return new CartDTO(List.of(), java.math.BigDecimal.ZERO);
        }

        List<CartItemDTO> items = cart.getItems()
                .stream()
                .map(item -> new CartItemDTO(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getProduct().getPrice(),
                        item.getQuantity(),
                        item.getRowTotal()
                ))
                .toList();

        return new CartDTO(
                items,
                cart.getTotalPrice()
        );
    }
}
