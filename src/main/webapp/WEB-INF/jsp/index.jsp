<%@ include file="fragments/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h1>Bienvenue sur VoyageConnect</h1>
<p>Votre prochaine aventure commence ici.</p>

<div class="row">
    <c:forEach var="voyage" items="${voyages}">
        <div class="col-md-4 mb-4">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">${voyage.title}</h5>
                    <p class="card-text">
                        <strong>Destination:</strong> ${voyage.destination.city}, ${voyage.destination.country}<br>
                        <strong>Prix:</strong> ${voyage.price} €
                    </p>
                    <a href="<c:url value='/voyage/${voyage.id}'/>" class="btn btn-primary">Voir les détails</a>
                </div>
            </div>
        </div>
    </c:forEach>
</div>

<%@ include file="fragments/footer.jsp" %>
