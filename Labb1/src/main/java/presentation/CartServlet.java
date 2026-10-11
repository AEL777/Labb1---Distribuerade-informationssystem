package presentation;

import business.ShopService;
import model.Cart;
import dto.CartDTO;
import dto.ProductDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private final ShopService shopService = new ShopService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = requireLoggedInSession(request, response);
        if (session == null) {
            return;
        }

        CartDTO cartDTO = shopService.getCartDTO(getOrCreateCart(session));
        request.setAttribute("cart", cartDTO);
        request.getRequestDispatcher("/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = requireLoggedInSession(request, response);
        if (session == null) {
            return;
        }

        try {
            int productId = Integer.parseInt(request.getParameter("productId"));

            Cart cart = getOrCreateCart(session);
            ProductDTO product = shopService.addProductToCart(cart, productId);

            if (product != null) {
                session.setAttribute(
                        "cartMessage",
                        product.getName() + " lades i kundvagnen."
                );
            }

            response.sendRedirect(request.getContextPath() + "/products");
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/products");
        } catch (SQLException e) {
            throw new ServletException("Kunde inte lägga produkt i kundvagnen.", e);
        }
    }

    private HttpSession requireLoggedInSession(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return null;
        }
        return session;
    }

    private Cart getOrCreateCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }
}
