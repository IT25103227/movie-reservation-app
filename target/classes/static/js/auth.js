/**
 * js/auth.js
 * Hardcoded credential store, login logic, and session guard.
 * All admin pages must load this first and call requireAuth(allowedRoles).
 */

// ── Credential & role registry ─────────────────────────────────────────────
const USERS = [
    {
        username:    'admin',
        password:    'admin123',
        role:        'SUPER_ADMIN',
        displayName: 'Super Administrator',
        landing:     'dashboard.html',
    },
    {
        username:    'manager',
        password:    'mgr123',
        role:        'CINEMA_MANAGER',
        displayName: 'Cinema Manager',
        landing:     'dashboard.html',
    },
    {
        username:    'marketing',
        password:    'mkt123',
        role:        'MARKETING_MANAGER',
        displayName: 'Marketing Manager',
        landing:     'promotions.html',
    },
    {
        username:    'staff',
        password:    'staff123',
        role:        'TICKETING_STAFF',
        displayName: 'Ticketing & F&B Staff',
        landing:     'bookings.html',
    },
    {
        username:    'parking',
        password:    'park123',
        role:        'PARKING_ATTENDANT',
        displayName: 'Parking Attendant',
        landing:     'parking.html',
    },
];

// Pages each role is allowed to visit (used by requireAuth)
const ROLE_PERMISSIONS = {
    SUPER_ADMIN:       ['dashboard.html','movies.html','halls.html','bookings.html',
                        'concessions.html','promotions.html','parking.html','users.html'],
    CINEMA_MANAGER:    ['dashboard.html','movies.html','halls.html','bookings.html'],
    MARKETING_MANAGER: ['dashboard.html','movies.html','promotions.html'],
    TICKETING_STAFF:   ['bookings.html','concessions.html'],
    PARKING_ATTENDANT: ['parking.html'],
};

// ── Login ──────────────────────────────────────────────────────────────────
/**
 * Attempt login. Returns true on success, false on bad credentials.
 * On success stores the user object in localStorage and redirects.
 */
function attemptLogin(username, password) {
    const user = USERS.find(
        u => u.username === username.trim() && u.password === password
    );
    if (!user) return false;
    const session = { username: user.username, role: user.role, displayName: user.displayName };
    localStorage.setItem('currentUser', JSON.stringify(session));
    window.location.href = user.landing;
    return true;
}

// ── Logout ─────────────────────────────────────────────────────────────────
function logout() {
    localStorage.removeItem('currentUser');
    window.location.href = 'login.html';
}

// ── Session helpers ────────────────────────────────────────────────────────
function getCurrentUser() {
    try { return JSON.parse(localStorage.getItem('currentUser')); }
    catch { return null; }
}

/**
 * Call at the top of every admin page.
 * Redirects to login.html immediately if:
 *   - No session exists, OR
 *   - The current page is not in the user's allowed list.
 */
function requireAuth() {
    const user = getCurrentUser();
    if (!user) { window.location.href = 'login.html'; return null; }

    const page     = window.location.pathname.split('/').pop() || 'dashboard.html';
    const allowed  = ROLE_PERMISSIONS[user.role] || [];
    if (!allowed.includes(page)) { window.location.href = 'login.html'; return null; }

    return user;
}
