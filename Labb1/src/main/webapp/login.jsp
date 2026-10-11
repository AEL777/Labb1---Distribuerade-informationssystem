<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="sv">
<head>
    <meta charset="UTF-8">
    <title>Logga in</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<main class="container">
    <h1>Logga in</h1>
    <p><a href="${pageContext.request.contextPath}/">Till startsidan</a></p>

    <% if (request.getAttribute("error") != null) { %>
        <p class="error"><%= request.getAttribute("error") %></p>
    <% } %>

    <form method="post" action="${pageContext.request.contextPath}/login" class="form">
        <label for="username">Användarnamn</label>
        <input id="username" name="username" type="text" required>

        <label for="password">Lösenord</label>
        <input id="password" name="password" type="password" required>

        <button type="submit">Logga in</button>
    </form>
</main>
</body>
</html>
