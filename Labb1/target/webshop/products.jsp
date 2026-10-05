<%@ page import="java.util.List" %>
<%@ page import="model.Product" %>
<%@ page import="model.User" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="sv">
<head>
    <meta charset="UTF-8">
    <title>Produkter</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<main class="container">
    <%
        User user = (User) session.getAttribute("user");
        List<Product> products = (List<Product>) request.getAttribute("products");
        String cartMessage = (String) session.getAttribute("cartMessage");
        if (cartMessage != null) {
            session.removeAttribute("cartMessage");
        }
    %>
    <h1>Produkter</h1>
    <p>Inloggad som <strong><%= user.getUsername() %></strong></p>
    <nav class="nav">
        <a href="${pageContext.request.contextPath}/cart">Visa kundvagn</a>
        <a href="${pageContext.request.contextPath}/logout">Logga ut</a>
    </nav>

    <% if (cartMessage != null) { %>
        <p class="message"><%= cartMessage %></p>
    <% } %>

    <table>
        <thead>
        <tr>
            <th>Produkt</th>
            <th>Pris</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <% if (products != null) {
            for (Product product : products) { %>
            <tr>
                <td><%= product.getName() %></td>
                <td><%= product.getPrice() %> kr</td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/cart">
                        <input type="hidden" name="productId" value="<%= product.getId() %>">
                        <button type="submit">Lägg i kundvagn</button>
                    </form>
                </td>
            </tr>
        <%  }
        } %>
        </tbody>
    </table>
</main>
</body>
</html>
