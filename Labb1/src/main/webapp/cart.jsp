<%@ page import="dto.CartDTO" %>
<%@ page import="dto.CartItemDTO" %>
<%@ page import="dto.UserDTO" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="sv">
<head>
    <meta charset="UTF-8">
    <title>Kundvagn</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<main class="container">
    <%
        UserDTO user = (UserDTO) session.getAttribute("user");
        CartDTO cart = (CartDTO) request.getAttribute("cart");
    %>
    <h1>Kundvagn</h1>
    <p>Inloggad som <strong><%= user.getUsername() %></strong></p>
    <nav class="nav">
        <a href="${pageContext.request.contextPath}/products">Till produkter</a>
        <a href="${pageContext.request.contextPath}/logout">Logga ut</a>
    </nav>

    <% if (cart == null || cart.isEmpty()) { %>
        <p>Kundvagnen är tom.</p>
    <% } else { %>
        <table>
            <thead>
            <tr>
                <th>Produkt</th>
                <th>Pris</th>
                <th>Antal</th>
                <th>Radtotal</th>
            </tr>
            </thead>
            <tbody>
            <% for (CartItemDTO item : cart.getItems()) { %>
                <tr>
                    <td><%= item.getProductName() %></td>
                    <td><%= item.getPrice() %></td>
                    <td><%= item.getQuantity() %></td>
                    <td><%= item.getRowTotal() %></td>
                </tr>
            <% } %>
            </tbody>
        </table>
        <p class="total">Totalt: <%= cart.getTotalPrice() %> kr</p>
    <% } %>
</main>
</body>
</html>
