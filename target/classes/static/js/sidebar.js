/**
 * js/sidebar.js
 * Injects the unified left-hand navigation drawer into every admin page.
 * Must be loaded AFTER auth.js (which calls requireAuth and provides getCurrentUser).
 *
 * Usage in each page:
 *   <script src="js/auth.js"></script>
 *   <script src="js/sidebar.js"></script>
 *   <script> const user = requireAuth(); injectSidebar(user); </script>
 */

// Nav items definition. visibleTo lists all roles that may see each link.
const NAV_ITEMS = [
    { icon: '📊', label: 'Dashboard & Reports',  href: 'dashboard.html',   roles: ['SUPER_ADMIN','CINEMA_MANAGER','MARKETING_MANAGER'] },
    { icon: '🎬', label: 'Movies & Showtimes',   href: 'movies.html',      roles: ['SUPER_ADMIN','CINEMA_MANAGER','MARKETING_MANAGER'] },
    { icon: '🏛️', label: 'Cinema Halls',          href: 'halls.html',       roles: ['SUPER_ADMIN','CINEMA_MANAGER'] },
    { icon: '🎟️', label: 'Ticketing & Bookings',  href: 'bookings.html',    roles: ['SUPER_ADMIN','CINEMA_MANAGER','TICKETING_STAFF'] },
    { icon: '🍿', label: 'Food & Concessions',    href: 'concessions.html', roles: ['SUPER_ADMIN','TICKETING_STAFF'] },
    { icon: '🏷️', label: 'Promotions & Pricing',  href: 'promotions.html',  roles: ['SUPER_ADMIN','MARKETING_MANAGER'] },
    { icon: '🅿️', label: 'Parking Management',   href: 'parking.html',     roles: ['SUPER_ADMIN','CINEMA_MANAGER','PARKING_ATTENDANT'] },
    { icon: '👥', label: 'Staff Management',      href: 'users.html',       roles: ['SUPER_ADMIN'] },
];

/**
 * Injects the sidebar into document.body.
 * Wraps existing body content in .ss-main and prepends .ss-sidebar.
 */
function injectSidebar(user) {
    if (!user) return;

    const currentPage = window.location.pathname.split('/').pop() || 'dashboard.html';
    const initials    = user.displayName.split(' ').map(w => w[0]).join('').slice(0, 2).toUpperCase();

    // Build visible nav items for this role
    const navHtml = NAV_ITEMS
        .filter(item => item.roles.includes(user.role))
        .map(item => {
            const active = currentPage === item.href ? 'active' : '';
            return `<li>
                <a href="${item.href}" class="ss-nav-link ${active}">
                    <span class="ss-nav-icon">${item.icon}</span>
                    <span>${item.label}</span>
                </a>
            </li>`;
        })
        .join('');

    const sidebarHtml = `
    <aside class="ss-sidebar">
        <div class="ss-brand">
            <div class="ss-brand-logo">🎬 SilverScreen</div>
            <div class="ss-user-chip">
                <div class="ss-avatar">${initials}</div>
                <div class="ss-user-info">
                    <div class="ss-user-name">${user.displayName}</div>
                    <div class="ss-role-badge role-${user.role}">${user.role.replace(/_/g,' ')}</div>
                </div>
            </div>
        </div>
        <ul class="ss-nav">${navHtml}</ul>
        <div class="ss-logout">
            <button class="ss-logout-btn" onclick="logout()">
                <span>🚪</span><span>Log Out</span>
            </button>
        </div>
    </aside>`;

    // Wrap existing body children in .ss-main, prepend sidebar
    const main    = document.createElement('div');
    main.className = 'ss-main';
    while (document.body.firstChild) main.appendChild(document.body.firstChild);

    const layout  = document.createElement('div');
    layout.className = 'ss-layout';
    layout.innerHTML = sidebarHtml;
    layout.appendChild(main);

    document.body.appendChild(layout);
}
