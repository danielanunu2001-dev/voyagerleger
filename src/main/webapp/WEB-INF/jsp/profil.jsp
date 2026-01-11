<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profil - VoyageLuxe</title>
    <link href="${pageContext.request.contextPath}/static/css/design-system.css" rel="stylesheet">
</head>
<body>
    <header class="main-header">
        <nav class="main-header__nav container">
            <a href="/" class="main-header__logo">VoyageLuxe</a>
            <div class="main-header__actions">
                <form action="${pageContext.request.contextPath}/logout" method="post" style="display: inline;">
                    <button type="submit" class="card__cta" style="background-color: var(--color-neutral-700);">Déconnexion</button>
                </form>
            </div>
        </nav>
    </header>
    <main class="container" style="padding-top: var(--spacing-6);">
        <h1>Bonjour, <sec:authentication property="principal.username" /> !</h1>
        <p>Ceci est votre page de profil sécurisée.</p>
    </main>
</body>
</html>
