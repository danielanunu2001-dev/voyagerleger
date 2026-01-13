/**
 * CSRF Token Handling for AJAX requests.
 * Reads the token from the meta tags in the header and sets it up for all jQuery AJAX requests.
 */
$(function () {
    const token = $("meta[name='_csrf']").attr("content");
    const header = $("meta[name='_csrf_header']").attr("content");

    $(document).ajaxSend(function(e, xhr, options) {
        // Exclude non-mutating methods
        if (options.type === "POST" || options.type === "PUT" || options.type === "DELETE") {
            xhr.setRequestHeader(header, token);
        }
    });
});
