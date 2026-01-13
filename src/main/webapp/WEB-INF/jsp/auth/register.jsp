<%@ include file="../fragments/header.jsp" %>

<div class="row justify-content-center">
    <div class="col-md-6">
        <h2>Inscription</h2>
        <!-- Registration form will be handled by a REST controller and AJAX in a later phase -->
        <form id="register-form">
            <div class="mb-3">
                <label for="username" class="form-label">Nom d'utilisateur</label>
                <input type="text" id="username" name="username" class="form-control" required>
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">Email</label>
                <input type="email" id="email" name="email" class="form-control" required>
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Mot de passe</label>
                <input type="password" id="password" name="password" class="form-control" required>
            </div>
            <button type="submit" class="btn btn-primary">S'inscrire</button>
        </form>
    </div>
</div>

<%@ include file="../fragments/footer.jsp" %>
