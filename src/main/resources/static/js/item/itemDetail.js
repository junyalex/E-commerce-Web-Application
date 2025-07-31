document.addEventListener('DOMContentLoaded', () => {
    const wrapper = document.querySelector('.text-wrapper');
    const content = document.querySelector('.text-content');
    const btn     = document.querySelector('.toggle-btn');

    const collapsedH = content.clientHeight;

    btn.addEventListener('click', () => {
        if (!wrapper.classList.contains('expanded')) {
            // 펼치기
            const fullH = content.scrollHeight;
            content.style.height = fullH + 'px';
            wrapper.classList.add('expanded');
            btn.textContent = 'Read less';
            // 애니메이션 끝나면 auto 로 풀어 줘서 내부 크기에 따라 늘어나게
            setTimeout(() => {
                content.style.height = 'auto';
            }, 600); // CSS transition-duration(0.6s)과 맞추세요
        } else {
            // 접기
            content.style.height = collapsedH + 'px';
            wrapper.classList.remove('expanded');
            btn.textContent = 'Read more';
        }
    });
});
