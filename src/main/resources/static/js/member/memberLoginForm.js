document.addEventListener('DOMContentLoaded', function () {
    const signupButton = document.getElementById('signup-button');

    if (signupButton) {
        signupButton.addEventListener('click', function () {
            location.href = '/members/new';
        });
    }
});
