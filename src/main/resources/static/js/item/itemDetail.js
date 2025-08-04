document.addEventListener('DOMContentLoaded', () => {
    const wrapper = document.querySelector('.text-wrapper');
    const content = document.querySelector('.text-content');
    const btn     = document.querySelector('.toggle-btn');

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
