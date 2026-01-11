<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
    <h2>Connexion</h2>
    <c:if test="${param.error}">
        <p style="color:red;">Identifiants incorrects.</p>
    </c:if>
    <c:if test="${param.logout}">
        <p>Vous êtes déconnecté.</p>
    </c:if>
    <form action="/login" method="post">
        <input type="text" name="username" placeholder="Nom d'utilisateur"><br>
        <input type="password" name="password" placeholder="Mot de passe"><br>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <button type="submit">Se connecter</button>
    </form>
</body>
</html>
