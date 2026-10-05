package business;

import data.ProductDAO;
import data.UserDAO;
import model.Cart;
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

    public User login(String username, String password) throws SQLException {
        if (username == null || password == null || username.isBlank() || password.isBlank()) {
            return null;
        }
        return userDAO.findByUsernameAndPassword(username.trim(), password);
    }

    public List<Product> getProducts() throws SQLException {
        return productDAO.findAll();
    }

    public Product getProduct(int productId) throws SQLException {
        return productDAO.findById(productId);
    }

    public void addProductToCart(Cart cart, Product product) {
        if (cart != null && product != null) {
            cart.addProduct(product);
        }
    }
}
