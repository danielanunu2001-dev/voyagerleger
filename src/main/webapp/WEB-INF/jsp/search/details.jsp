<%@ include file="../fragments/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="card">
    <div class="card-header">
        <h1>${voyage.title}</h1>
    </div>
    <div class="card-body">
        <p><strong>Destination:</strong> ${voyage.destination.city}, ${voyage.destination.country}</p>
        <p><strong>Description:</strong> ${voyage.destination.description}</p>
        <p><strong>Type:</strong> ${voyage.type}</p>
        <p><strong>Prix:</strong> ${voyage.price} €</p>
        <p><strong>Du</strong> ${voyage.startDate} <strong>au</strong> ${voyage.endDate}</p>
        <p><strong>Places disponibles:</strong> ${voyage.seatsAvailable}</p>

        <button id="add-to-cart-btn" class="btn btn-success"
                data-voyage-id="${voyage.id}"
                data-voyage-title="${voyage.title}"
                data-voyage-price="${voyage.price}">
            Ajouter au Panier
        </button>
    </div>
</div>

<%@ include file="../fragments/footer.jsp" %>
