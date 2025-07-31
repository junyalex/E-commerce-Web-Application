document.addEventListener('DOMContentLoaded', function () {
    const menuBtn = document.getElementById('menu-button-real');
    const sideMenu = document.getElementById('side-menu');
    const menuLabel = document.getElementById('menu-label');
    const menuIcon = document.getElementById('menu-icon');
    const logo = document.querySelector('#logo');

    const searchButton = document.getElementById('search-button');
    const searchOverlay = document.getElementById('search-overlay');
    const closeSearchButton = document.getElementById('close-search');
    const searchInput = document.querySelector('.search-input');
    const searchForm = document.getElementById('search-form');
    const searchResultsContainer = document.getElementById('search-results-container');

    logo.style.cursor = 'pointer';
    logo.addEventListener('click', function () {
        window.location.href = "/";
    })

    menuBtn.addEventListener('click', function () {
        const isOpen = sideMenu.classList.toggle('active');
        menuLabel.textContent = isOpen ? 'Close' : 'Menu';

        menuIcon.classList.remove(isOpen ? 'fa-bars' : 'fa-xmark');
        menuIcon.classList.add(isOpen ? 'fa-xmark' : 'fa-bars');
    });

    searchButton.addEventListener('click', () => {
        searchOverlay.classList.add('active');
        document.body.style.overflow = 'hidden';
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

    // Real-time search functionality
    let debounceTimer;
    searchInput.addEventListener('input', function(e) {
        clearTimeout(debounceTimer);
        const query = e.target.value;

        debounceTimer = setTimeout(() => {
            fetch(`/search/items?query=${encodeURIComponent(query)}`)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.text();
                })
                .then(html => {
                    const tempDiv = document.createElement('div');
                    tempDiv.innerHTML = html;

                    const items = tempDiv.querySelectorAll('.card');

                    searchResultsContainer.innerHTML = '';
                    items.forEach(item => {
                        searchResultsContainer.appendChild(item);
                    });
                })
                .catch(error => {
                    console.error('Error fetching search results:', error);
                    searchResultsContainer.innerHTML = '<p class="error-message">Error loading results. Please try again.</p>';
                });
        }, 300);
    });
});