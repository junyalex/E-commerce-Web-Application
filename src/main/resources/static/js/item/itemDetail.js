document.addEventListener('DOMContentLoaded', () => {
    const wrapper = document.querySelector('.text-wrapper');
    const content = document.querySelector('.text-content');
    const btn = document.querySelector('.toggle-btn');
    const collapsedH = content.clientHeight;

    btn.addEventListener('click', () => {
        if (!wrapper.classList.contains('expanded')) {
            const fullH = content.scrollHeight;
            content.style.height = fullH + 'px';
            wrapper.classList.add('expanded');
            btn.textContent = 'Read less';
            setTimeout(() => {
                content.style.height = 'auto';
            }, 600);
        } else {
            content.style.height = collapsedH + 'px';
            wrapper.classList.remove('expanded');
            btn.textContent = 'Read more';
        }
    });
});

function addCart() {
    const token = $("meta[name='_csrf']").attr("content");
    const header = $("meta[name='_csrf_header']").attr("content");
    const url = "/cart";

    const data = {
        itemId: $("#item-id").text(),
        count: $("#count").val(),
    };
    const param = JSON.stringify(data);

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
            showAdded();
        },
        error: function(jqXHR, status, error) {
            if (jqXHR.status === '401') {
                location.href = 'members/login';
            } else {
                alert(jqXHR.responseText);
            }
        }
    });
}

function showAdded() {
    document.getElementById("added-screen").classList.add('show');
    document.getElementById("whole-screen").classList.add('show');
}

function hideAdded() {
    document.getElementById("added-screen").classList.remove('show');
    document.getElementById("whole-screen").classList.remove('show');
}