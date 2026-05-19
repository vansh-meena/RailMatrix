// js/api.js — RailMatrix API client
// All fetch calls to the Node.js backend go through here

const API = 'https://railmatrix-production.up.railway.app/api';

// ── Token helpers ───────────────────────────────────────────────
const Auth = {
  save(token, user) {
    localStorage.setItem('rm_token', token);
    localStorage.setItem('rm_user', JSON.stringify(user));
  },
  token()  { return localStorage.getItem('rm_token'); },
  user()   { const u = localStorage.getItem('rm_user'); return u ? JSON.parse(u) : null; },
  clear()  { localStorage.removeItem('rm_token'); localStorage.removeItem('rm_user'); },
  isLoggedIn() { return !!this.token(); }
};

// ── Base fetch ──────────────────────────────────────────────────
async function apiFetch(path, opts = {}) {
  const headers = { 'Content-Type': 'application/json', ...opts.headers };
  const token = Auth.token();
  if (token) headers['Authorization'] = `Bearer ${token}`;

  const res = await fetch(`${API}${path}`, { ...opts, headers });
  const data = await res.json().catch(() => ({ error: 'Invalid response from server.' }));

  if (!res.ok) throw new Error(data.error || `HTTP ${res.status}`);
  return data;
}

// ── Auth ────────────────────────────────────────────────────────
const AuthAPI = {
  async register(name, email, password) {
    return apiFetch('/auth/register', {
      method: 'POST',
      body: JSON.stringify({ name, email, password })
    });
  },
  async login(email, password) {
    return apiFetch('/auth/login', {
      method: 'POST',
      body: JSON.stringify({ email, password })
    });
  }
};

// ── Stations ────────────────────────────────────────────────────
const StationsAPI = {
  async suggest(q) {
    if (!q || q.length < 1) return [];
    const data = await apiFetch(`/stations/suggest?q=${encodeURIComponent(q)}`);
    return data.stations || [];
  }
};

// ── Trains ─────────────────────────────────────────────────────
const TrainsAPI = {
  async search(from, to, date) {
    return apiFetch(`/trains/search?from=${encodeURIComponent(from)}&to=${encodeURIComponent(to)}&date=${date}`);
  }
};

// ── Bookings ────────────────────────────────────────────────────
const BookingsAPI = {
  async create(trainId, journeyDate, passengers) {
    return apiFetch('/bookings/create', {
      method: 'POST',
      body: JSON.stringify({ trainId, journeyDate, passengers })
    });
  },
  async getForUser(userId) {
    return apiFetch(`/bookings/user/${userId}`);
  },
  async getById(bookingId) {
    return apiFetch(`/bookings/${bookingId}`);
  },
  async cancel(bookingId) {
    return apiFetch('/bookings/cancel', {
      method: 'POST',
      body: JSON.stringify({ bookingId })
    });
  }
};

// ── UI Helpers ──────────────────────────────────────────────────
function showAlert(elId, msg, type = 'error') {
  const el = document.getElementById(elId);
  if (!el) return;
  el.className = `alert show alert-${type}`;
  el.innerHTML = `<span>${type === 'error' ? '⚠️' : type === 'success' ? '✅' : 'ℹ️'}</span> ${msg}`;
  setTimeout(() => el.classList.remove('show'), 6000);
}

function setLoading(btnId, loading, text = '') {
  const btn = document.getElementById(btnId);
  if (!btn) return;
  btn.disabled = loading;
  if (loading) {
    btn.dataset.orig = btn.innerHTML;
    btn.innerHTML = `<span class="spinner show"></span> ${text || 'Loading...'}`;
  } else {
    btn.innerHTML = btn.dataset.orig || text;
  }
}

function formatDate(dateStr) {
  const d = new Date(dateStr);
  return d.toLocaleDateString('en-IN', { day: 'numeric', month: 'short', year: 'numeric' });
}

function formatTime(t) {
  if (!t) return '—';
  const [h, m] = t.split(':');
  const hour = parseInt(h);
  const ampm = hour >= 12 ? 'PM' : 'AM';
  return `${String(hour % 12 || 12).padStart(2,'0')}:${m} ${ampm}`;
}

// ── Station Autocomplete ─────────────────────────────────────────
function initAutocomplete(inputId, listId, onSelect) {
  const input = document.getElementById(inputId);
  const list  = document.getElementById(listId);
  if (!input || !list) return;

  let debounceTimer;
  let selectedValue = '';

  input.addEventListener('input', () => {
    selectedValue = '';
    clearTimeout(debounceTimer);
    debounceTimer = setTimeout(async () => {
      const q = input.value.trim();
      if (q.length < 1) { list.classList.remove('open'); return; }

      const stations = await StationsAPI.suggest(q);
      list.innerHTML = '';

      if (stations.length === 0) {
        list.classList.remove('open');
        return;
      }

      stations.forEach(s => {
        const item = document.createElement('div');
        item.className = 'autocomplete-item';
        item.innerHTML = `
          <span>🚉</span>
          <div>
            <div>${s.station_name}</div>
            <div class="station-city">${s.city || ''}</div>
          </div>`;
        item.addEventListener('mousedown', e => {
          e.preventDefault();
          input.value = s.station_name;
          selectedValue = s.station_name;
          list.classList.remove('open');
          if (onSelect) onSelect(s);
        });
        list.appendChild(item);
      });

      list.classList.add('open');
    }, 200);
  });

  input.addEventListener('blur', () => setTimeout(() => list.classList.remove('open'), 200));
  input.addEventListener('focus', () => { if (input.value.length > 1) input.dispatchEvent(new Event('input')); });

  return {
    getSelected: () => selectedValue || input.value,
    isValid:     () => !!selectedValue
  };
}

// ── Guard: redirect if not logged in ────────────────────────────
function requireAuth(redirectTo = '/login.html') {
  if (!Auth.isLoggedIn()) {
    window.location.href = redirectTo;
    return false;
  }
  return true;
}

// ── Update navbar based on auth state ───────────────────────────
function updateNavbar() {
  const user = Auth.user();
  const loginLink  = document.getElementById('nav-login');
  const regLink    = document.getElementById('nav-register');
  const dashLink   = document.getElementById('nav-dashboard');
  const logoutBtn  = document.getElementById('nav-logout');
  const nameEl     = document.getElementById('nav-username');

  if (user) {
    if (loginLink)  loginLink.classList.add('hidden');
    if (regLink)    regLink.classList.add('hidden');
    if (dashLink)   dashLink.classList.remove('hidden');
    if (logoutBtn)  logoutBtn.classList.remove('hidden');
    if (nameEl)     nameEl.textContent = user.name?.split(' ')[0] || 'Account';
  } else {
    if (loginLink)  loginLink.classList.remove('hidden');
    if (regLink)    regLink.classList.remove('hidden');
    if (dashLink)   dashLink.classList.add('hidden');
    if (logoutBtn)  logoutBtn.classList.add('hidden');
  }

  document.getElementById('nav-logout')?.addEventListener('click', () => {
    Auth.clear();
    window.location.href = '/index.html';
  });
}
