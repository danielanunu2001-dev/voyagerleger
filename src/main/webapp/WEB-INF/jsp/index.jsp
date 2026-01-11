<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Agence de Voyage - Nos Offres</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <!-- Custom CSS -->
    <link href="${pageContext.request.contextPath}/static/css/style.css" rel="stylesheet">
</head>
<body>

    <header class="main-header">
        <nav class="main-header__nav container">
            <a href="/" class="main-header__logo">VoyageLuxe</a>
            <div class="main-header__actions">
                <sec:authorize access="!isAuthenticated()">
                    <a href="${pageContext.request.contextPath}/login" class="card__cta">Connexion</a>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <a href="${pageContext.request.contextPath}/profil" class="card__cta" style="margin-right: 1rem;">Profil</a>
                    <form action="${pageContext.request.contextPath}/logout" method="post" style="display: inline;">
                        <button type="submit" class="card__cta" style="background-color: var(--color-neutral-700);">Déconnexion</button>
                    </form>
                </sec:authorize>
            </div>
        </nav>
    </header>

    <!-- Contenu Principal -->
    <main class="container my-5">
        <div class="text-center mb-5">
            <h1 class="display-4">Découvrez nos destinations de rêve</h1>
            <p class="lead">Des aventures inoubliables vous attendent.</p>
        </div>

        <div class="row row-cols-1 row-cols-md-2 row-cols-lg-4 g-4">
            <c:forEach var="package" items="${packages}">
                <div class="col">
                    <div class="card h-100 shadow-sm travel-card">
                        <img src="${package.imageUrl}" class="card-img-top" alt="Image de ${package.nom}">
                        <div class="card-body">
                            <h5 class="card-title">${package.nom}</h5>
                            <h6 class="card-subtitle mb-2 text-muted">${package.destination}</h6>
                            <p class="card-text">${package.description}</p>
                        </div>
                        <div class="card-footer bg-transparent border-top-0">
                             <div class="d-flex justify-content-between align-items-center">
                                <span class="fw-bold fs-5 text-primary">
                                    <fmt:formatNumber value="${package.prix}" type="currency" currencyCode="EUR" />
                                </span>
                                <a href="#" class="btn btn-primary">Voir détails</a>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </main>

    <!-- Pied de Page -->
    <footer class="bg-light text-center text-lg-start mt-auto">
        <div class="text-center p-3" style="background-color: rgba(0, 0, 0, 0.05);">
            © 2026 Agence de Voyage. Tous droits réservés.
        </div>
    </footer>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
