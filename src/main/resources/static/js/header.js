document.addEventListener('DOMContentLoaded', function () {
    const menuBtn = document.getElementById('menu-button-real');
    const sideMenu = document.getElementById('side-menu');
    const menuLabel = document.getElementById('menu-label');
    const menuIcon = document.getElementById('menu-icon');

    menuBtn.addEventListener('click', function () {
        const isOpen = sideMenu.classList.toggle('active');

        menuLabel.textContent = isOpen ? 'Close' : 'Menu';

        if (isOpen) {
            menuIcon.classList.remove('fa-bars');
            menuIcon.classList.add('fa-xmark');
        } else {
            menuIcon.classList.remove('fa-xmark');
            menuIcon.classList.add('fa-bars');
        }

    });
});
