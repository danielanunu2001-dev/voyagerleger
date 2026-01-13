<%@ include file="../fragments/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row justify-content-center">
    <div class="col-md-6">
        <h2>Connexion</h2>
        <c:if test="${param.error != null}">
            <div class="alert alert-danger">
                Identifiants incorrects.
            </div>
        </c:if>
        <c:if test="${param.logout != null}">
            <div class="alert alert-success">
                Vous avez été déconnecté.
            </div>
        </c:if>
        <form action="<c:url value='/login'/>" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="mb-3">
                <label for="username" class="form-label">Nom d'utilisateur</label>
                <input type="text" id="username" name="username" class="form-control" required autofocus>
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Mot de passe</label>
                <input type="password" id="password" name="password" class="form-control" required>
            </div>
            <button type="submit" class="btn btn-primary">Se connecter</button>
        </form>
    </div>
</div>

<%@ include file="../fragments/footer.jsp" %>
