<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Connexion - VoyageLuxe</title>
    <link href="${pageContext.request.contextPath}/static/css/design-system.css" rel="stylesheet">
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            background-color: var(--color-neutral-100);
        }
        .login-card {
            width: 100%;
            max-width: 400px;
            padding: var(--spacing-6) var(--spacing-4);
            background: var(--color-neutral-0);
            border-radius: var(--border-radius);
            box-shadow: var(--shadow-lg);
        }
        .login-card__title {
            text-align: center;
            font-size: var(--font-size-xl);
            color: var(--color-primary-deep-blue);
            margin-bottom: var(--spacing-4);
        }
        .form-group {
            margin-bottom: var(--spacing-3);
        }
        .form-group label {
            display: block;
            margin-bottom: var(--spacing-1);
            font-weight: var(--font-weight-bold);
        }
        .form-group input {
            width: 100%;
            padding: var(--spacing-2);
            border: 1px solid var(--color-neutral-300);
            border-radius: var(--border-radius);
        }
        .login-button {
            width: 100%;
            padding: var(--spacing-2);
            border: none;
            border-radius: var(--border-radius);
            background-color: var(--color-accent-emerald);
            color: var(--color-neutral-0);
            font-weight: var(--font-weight-bold);
            cursor: pointer;
            transition: var(--transition-cubic);
        }
        .login-button:hover {
            background-color: var(--color-accent-emerald-darker);
        }
        .error-message {
            background-color: #f8d7da;
            color: #721c24;
            padding: var(--spacing-2);
            border-radius: var(--border-radius);
            margin-bottom: var(--spacing-3);
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="login-card">
        <h1 class="login-card__title">Accès Client</h1>

        <c:if test="${param.error != null}">
            <div class="error-message">
                Identifiants incorrects. Veuillez réessayer.
            </div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/login">
            <div class="form-group">
                <label for="username">Nom d'utilisateur</label>
                <input type="text" id="username" name="username" required autofocus>
            </div>
            <div class="form-group">
                <label for="password">Mot de passe</label>
                <input type="password" id="password" name="password" required>
            </div>
            <button type="submit" class="login-button">Se connecter</button>
        </form>
    </div>
</body>
</html>
