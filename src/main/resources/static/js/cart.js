/**
 * Manages the shopping cart using sessionStorage.
 */
const CartService = {
    getCart: function() {
        const cart = sessionStorage.getItem('voyageConnectCart');
        return cart ? JSON.parse(cart) : [];
    },

    saveCart: function(cart) {
        sessionStorage.setItem('voyageConnectCart', JSON.stringify(cart));
    },

    addToCart: function(voyageId, voyageTitle, price) {
        const cart = this.getCart();
        const existingItem = cart.find(item => item.voyageId === voyageId);

        if (!existingItem) {
            cart.push({ voyageId, voyageTitle, price, quantity: 1 });
            this.saveCart(cart);
            console.log(`Added ${voyageTitle} to cart.`);
            // TODO: Add visual feedback (toast/notification)
        } else {
            console.log(`${voyageTitle} is already in the cart.`);
        }
    },

    removeFromCart: function(voyageId) {
        let cart = this.getCart();
        cart = cart.filter(item => item.voyageId !== voyageId);
        this.saveCart(cart);
        console.log(`Removed voyage ${voyageId} from cart.`);
    },

    clearCart: function() {
        sessionStorage.removeItem('voyageConnectCart');
        console.log("Cart cleared.");
    }
};

$(document).ready(function() {
    // Example of how to add to cart from the details page
    $('#add-to-cart-btn').on('click', function() {
        const voyageId = $(this).data('voyage-id');
        const voyageTitle = $(this).data('voyage-title');
        const price = $(this).data('voyage-price');
        CartService.addToCart(voyageId, voyageTitle, price);
        alert(`${voyageTitle} a été ajouté au panier !`);
    });
});
