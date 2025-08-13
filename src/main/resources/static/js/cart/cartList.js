window.addEventListener('DOMContentLoaded', function() {
    updateTotalPriceAndQuantity();
});

let total_price = document.querySelectorAll(".total-price");
let total_quan = document.getElementById("cart-count");

function changeCount(obj){
    let count = obj.value;
    let cartItemId = obj.id.split('_')[1];
    let price = $('#count_' + cartItemId).data('price');
    let total_price = price * count;
    let s = document.getElementById('price_' + cartItemId);
    s.innerHTML = '$ ' + total_price.toFixed(2);
    updateTotalPriceAndQuantity();
    updateCartItemCount(cartItemId, count);
}

function updateTotalPriceAndQuantity(){
    let counter_p = 0; // Counter for price
    let counter_q = 0; // Counter for quantity

    $("select[name=count]").each(function(){
        let itemId = this.id.split('_')[1];
        let price = $('#count_' + itemId).data('price');
        let count = $(this).val();
        counter_p += count * price;
        counter_q += parseInt(count);
    })
    total_price.forEach(function(element){
        element.innerHTML = "$ " + counter_p.toFixed(2);
    })
    total_quan.innerHTML = "(" + counter_q + ")";

}

/**
 * When user changes item quantity in cart, apply the changes to DB.
 */
function updateCartItemCount(cartItemId, count){
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    let url = "/cartItem/" + cartItemId+"?count=" + count;

    $.ajax({
        url: url,
        type: "PATCH", // update
        beforeSend: function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        dataType: "json",
        cache: false,
        success: function(result, status) {
            console.log("CartItem quantity has been updated successfully")
        },
        error: function(jqXHR, status, error) {
            if(jqXHR.status === 401){
                location.href='/members/login';
            } else {
                alert(jqXHR.responseText);
            }
        }
    })
}

function removeCart(obj){
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    let cartItemId = obj.dataset.id;
    let url = "/cartItem/" + cartItemId;

    $.ajax({
        url: url,
        type: "DELETE",
        beforeSend: function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        dataType: "json",
        cache: false,
        success: function(result, status) {
            location.href="/cart"
        },
        error: function(jqXHR, status, error) {
            if(jqXHR.status === 401){
                location.href='/members/login';
            } else {
                alert(jqXHR.responseText);
            }
        }
    })
}

function placeOrder(){
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    let url = "/cart/orders";

    let dataList = []; // Array
    let paramData = {}; // Object

    $("select[name=count]").each(function(){
        let cartItemId = this.id.split('_')[1];
        let data = {};
        data["cartItemId"] = cartItemId;
        dataList.push(data);
    });

    paramData["cartOrderDtoList"] = dataList;
    let param = JSON.stringify(paramData);

    $.ajax({
        url: url,
        type: "POST",
        contentType: "application/json",
        data: param,
        beforeSend: function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        dataType: "json",
        cache: false,
        success: function(result, status) {
            location.href="/orders";
        },
        error: function(jqXHR, status, error) {

            if(jqXHR.status === 401){
                location.href='members/login';
            } else {
                alert(jqXHR.responseText);
            }
        }

    })

}