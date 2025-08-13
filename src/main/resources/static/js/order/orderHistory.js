function cancelOrder(el) {
    const orderId = el.dataset.orderId;
    if (!orderId) {
        return;
    }
    if (el.textContent.trim().toLowerCase() === 'cancelled') return;
    const token  = document.querySelector('meta[name="_csrf"]')?.content || '';
    const header = document.querySelector('meta[name="_csrf_header"]')?.content || '';

    el.style.pointerEvents = 'none';

    $.ajax({
        url: `/order/${orderId}/cancel`,
        type: 'POST',
        beforeSend: function (xhr) {
            if (header && token) xhr.setRequestHeader(header, token);
        },
        cache: false,
        success: function () {
            location.href = '/orders';
        },
        error: function (jqXHR) {
            el.style.pointerEvents = '';
            if (jqXHR.status === 401) {
                location.href = '/members/login';
            } else {
                alert(jqXHR.responseText || 'Failed to cancel the order.');
            }
        }
    });
}