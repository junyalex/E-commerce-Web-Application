document.addEventListener('DOMContentLoaded', function () {
    const menuBtn = document.getElementById('menu-button-real');
    const sideMenu = document.getElementById('side-menu');
    const menuLabel = document.getElementById('menu-label');
    const menuIcon = document.getElementById('menu-icon');

    const searchButton = document.getElementById('search-button');
    const searchOverlay = document.getElementById('search-overlay');
    const closeSearchButton = document.getElementById('close-search');
    const searchInput = document.querySelector('.search-input');

    menuBtn.addEventListener('click', function () {
        const isOpen = sideMenu.classList.toggle('active');
        menuLabel.textContent = isOpen ? 'Close' : 'Menu';

        menuIcon.classList.remove(isOpen ? 'fa-bars' : 'fa-xmark');
        menuIcon.classList.add(isOpen ? 'fa-xmark' : 'fa-bars');
    });

    searchButton.addEventListener('click', () => {
        searchOverlay.classList.add('active');
        document.body.style.overflow = 'hidden'; // 스크롤 막기
        setTimeout(() => searchInput.focus(), 400);
    });

    function closeSearchOverlay() {
        searchOverlay.classList.remove('active');
        document.body.style.overflow = '';
    }

    closeSearchButton.addEventListener('click', closeSearchOverlay);

    document.addEventListener('keydown', (e) => {
        if (e.key === 'Escape' && searchOverlay.classList.contains('active')) {
            closeSearchOverlay();
        }
    });
});
