/**
 * Handles dynamic search functionality on the search page.
 */
$(document).ready(function() {
    // This is a placeholder for a more advanced search.
    // In a real application, you would have a search form.
    // For now, this just demonstrates fetching all voyages via AJAX.

    function fetchAndRenderVoyages() {
        $.ajax({
            url: "/api/voyages",
            type: "GET",
            success: function(voyages) {
                const list = $('#voyage-list');
                list.empty();
                if (voyages.length === 0) {
                    list.html('<p>Aucun voyage trouvé.</p>');
                    return;
                }
                voyages.forEach(function(voyage) {
                    const voyageCard = `
                        <div class="col-md-4 mb-4">
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title">${voyage.title}</h5>
                                    <p class="card-text">
                                        <strong>Destination:</strong> ${voyage.destinationCity}, ${voyage.destinationCountry}<br>
                                        <strong>Prix:</strong> ${voyage.price} €<br>
                                        <strong>Places restantes:</strong> ${voyage.seatsAvailable}
                                    </p>
                                    <a href="/voyage/${voyage.id}" class="btn btn-primary">Détails</a>
                                </div>
                            </div>
                        </div>`;
                    list.append(voyageCard);
                });
            },
            error: function() {
                $('#voyage-list').html('<p class="text-danger">Erreur lors du chargement des voyages.</p>');
            }
        });
    }

    // Initial load, if we were on the search page.
    if ($('#voyage-list').length) {
       // The page is already rendered server-side, so no initial fetch is needed.
       // An advanced implementation might have a search input with debounce.
    }
});
