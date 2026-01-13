<%@ include file="../fragments/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2>Résultats de la recherche</h2>
<div id="voyage-list" class="row">
    <c:forEach var="voyage" items="${voyages}">
        <div class="col-md-4 mb-4">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">${voyage.title}</h5>
                    <p class="card-text">
                        <strong>Destination:</strong> ${voyage.destination.city}, ${voyage.destination.country}<br>
                        <strong>Prix:</strong> ${voyage.price} €<br>
                        <strong>Places restantes:</strong> ${voyage.seatsAvailable}
                    </p>
                    <a href="<c:url value='/voyage/${voyage.id}'/>" class="btn btn-primary">Détails</a>
                </div>
            </div>
        </div>
    </c:forEach>
</div>

<%@ include file="../fragments/footer.jsp" %>
