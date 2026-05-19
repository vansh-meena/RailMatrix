// js/admin.js — Real Logic for the Admin Dashboard

if (!AdminAuth.isLoggedIn()) {
  window.location.href = 'admin-login.html';
}

const adminUser = AdminAuth.user();

window.addEventListener('load', () => {
  loadStats();
  loadUsers();
  loadTrains();
  loadStations();
  loadBookings();
});

// ── View Switching ──────────────────────────────────────────────
function switchView(viewId, btnEl) {
  document.querySelectorAll('.view-section').forEach(el => el.classList.remove('active'));
  document.getElementById(`view-${viewId}`).classList.add('active');
  document.querySelectorAll('.admin-sidebar .nav-item').forEach(el => el.classList.remove('active'));
  btnEl.classList.add('active');
}

// ── Toast Notification ──────────────────────────────────────────
function showToast(msg, type = 'success') {
  const existing = document.getElementById('admin-toast');
  if (existing) existing.remove();
  const t = document.createElement('div');
  t.id = 'admin-toast';
  t.style.cssText = `
    position:fixed; bottom:32px; right:32px; z-index:9999;
    background:${type === 'success' ? 'rgba(52,211,153,0.15)' : 'rgba(248,113,113,0.15)'};
    border:1px solid ${type === 'success' ? 'rgba(52,211,153,0.4)' : 'rgba(248,113,113,0.4)'};
    color:${type === 'success' ? '#34d399' : '#f87171'};
    padding:14px 22px; border-radius:12px; font-weight:600; font-size:0.9rem;
    box-shadow:0 8px 32px rgba(0,0,0,0.4);
    animation: fadeUp 0.3s ease;
  `;
  t.textContent = (type === 'success' ? '✅ ' : '⚠️ ') + msg;
  document.body.appendChild(t);
  setTimeout(() => t.remove(), 4000);
}

// ── Stats ────────────────────────────────────────────────────────
async function loadStats() {
  try {
    const data = await AdminAPI.getStats();
    document.getElementById('s-users').textContent    = data.totalUsers    || 0;
    document.getElementById('s-trains').textContent   = data.totalTrains   || 0;
    document.getElementById('s-stations').textContent = data.totalStations || 0;
    document.getElementById('s-bookings').textContent = data.totalBookings || 0;
  } catch (err) {
    console.error('Stats load err:', err);
  }
}

// ── Users ─────────────────────────────────────────────────────────
let blockTargetId = null;

async function loadUsers() {
  const tb = document.getElementById('users-tbody');
  try {
    const data = await AdminAPI.getUsers();
    if (!data.users || data.users.length === 0) {
      tb.innerHTML = '<tr><td colspan="6" style="text-align:center;color:var(--text-muted);">No users found.</td></tr>';
      return;
    }
    tb.innerHTML = data.users.map(u => `
      <tr>
        <td style="font-family:monospace;">#${u.user_id}</td>
        <td style="font-weight:600;">${escHtml(u.name)}</td>
        <td style="color:var(--text-muted);font-size:0.85rem;">${escHtml(u.email)}</td>
        <td><span class="badge badge-purple">${u.total_bookings} bookings</span></td>
        <td><span class="badge ${u.is_verified ? 'badge-active' : 'badge-cancelled'}">${u.is_verified ? 'Verified' : 'Unverified'}</span></td>
        <td><button class="btn btn-ghost btn-sm" style="color:var(--red);" onclick="openBlockModal(${u.user_id}, '${escHtml(u.name)}')">Suspend</button></td>
      </tr>
    `).join('');
  } catch (err) {
    tb.innerHTML = `<tr><td colspan="6" style="color:var(--red);text-align:center;">${err.message}</td></tr>`;
  }
}

function openBlockModal(userId, userName) {
  blockTargetId = userId;
  const p = document.querySelector('#block-modal p');
  if (p) p.textContent = `Are you sure you want to suspend ${userName}? They will not be able to log in or book tickets.`;
  document.getElementById('block-modal').classList.add('open');
}
function closeBlockModal() {
  document.getElementById('block-modal').classList.remove('open');
  blockTargetId = null;
}
async function submitBlock() {
  if (!blockTargetId) return;
  try {
    await AdminAPI.suspendUser(blockTargetId);
    closeBlockModal();
    showToast('User suspended successfully.');
    loadUsers();
  } catch (err) {
    showToast(err.message, 'error');
  }
}

// ── Trains ────────────────────────────────────────────────────────
let stationList = [];   // cache for autocomplete in route builder

async function loadTrains() {
  const tb = document.getElementById('trains-tbody');
  try {
    const data = await AdminAPI.getTrains();
    if (!data.trains || data.trains.length === 0) {
      tb.innerHTML = '<tr><td colspan="6" style="text-align:center;color:var(--text-muted);">No trains found.</td></tr>';
      return;
    }
    tb.innerHTML = data.trains.map(t => `
      <tr>
        <td style="font-family:monospace;">#${t.train_id}</td>
        <td style="font-weight:600;">${escHtml(t.train_name)}</td>
        <td>${t.train_type}</td>
        <td>${t.total_seats} seats</td>
        <td><span class="badge badge-active">Active</span></td>
        <td style="color:var(--text-muted);font-size:0.85rem;">${t.route_stops} stops</td>
      </tr>
    `).join('');
  } catch (err) {
    tb.innerHTML = `<tr><td colspan="6" style="color:var(--red);text-align:center;">${err.message}</td></tr>`;
  }
}

function openAddTrainModal() {
  // Reset the form
  document.getElementById('train-name').value     = '';
  document.getElementById('train-type').value     = 'Superfast';
  document.getElementById('train-base-fare').value= '';
  document.getElementById('train-fare-km').value  = '';
  document.getElementById('route-builder').innerHTML = buildRouteStopHtml(1);
  document.getElementById('train-modal').classList.add('open');
  document.getElementById('train-modal-alert').classList.remove('show');
  // Prefetch station list for autocomplete
  AdminAPI.getStations().then(d => { stationList = d.stations || d || []; }).catch(() => {});
}

function buildRouteStopHtml(stopNum) {
  return `<div class="route-stop" data-stop="${stopNum}" style="display:grid;grid-template-columns:1fr 70px 1fr 1fr 90px;gap:8px;align-items:center;">
    <input class="input rs-station" type="text" placeholder="Station name..." list="station-datalist-${stopNum}" style="font-size:0.85rem;">
    <input class="input rs-station-id" type="hidden" value="">
    <input class="input rs-dep" type="time" style="font-size:0.85rem;">
    <input class="input rs-arr" type="time" style="font-size:0.85rem;">
    <input class="input rs-dist" type="number" min="0" placeholder="km" style="font-size:0.85rem;">
    <button onclick="this.closest('.route-stop').remove()" style="background:rgba(248,113,113,0.15);color:var(--red);border:1px solid rgba(248,113,113,0.3);border-radius:8px;padding:4px 10px;font-size:0.8rem;cursor:pointer;">✕</button>
  </div>`;
}

function addRouteStop() {
  const rb = document.getElementById('route-builder');
  const stopNum = rb.children.length + 1;
  const d = document.createElement('div');
  d.innerHTML = buildRouteStopHtml(stopNum);
  rb.appendChild(d.firstElementChild);
}

async function saveTrain() {
  const name    = document.getElementById('train-name').value.trim();
  const type    = document.getElementById('train-type').value;
  const base    = parseFloat(document.getElementById('train-base-fare').value);
  const perKm   = parseFloat(document.getElementById('train-fare-km').value);

  if (!name || isNaN(base) || isNaN(perKm)) {
    showModalAlert('train-modal-alert', 'Please fill all required train fields.');
    return;
  }

  // Gather stops — match station names to IDs from cached list
  const stopEls = document.querySelectorAll('#route-builder .route-stop');
  const stops = [];
  for (let i = 0; i < stopEls.el; i++) {
    const el       = stopEls[i];
    const stName   = el.querySelector('.rs-station').value.trim();
    const depTime  = el.querySelector('.rs-dep').value;
    const arrTime  = el.querySelector('.rs-arr').value;
    const distKm   = parseFloat(el.querySelector('.rs-dist').value) || 0;

    const matched = stationList.find(s =>
      s.station_name.toLowerCase() === stName.toLowerCase()
    );
    if (!matched && stName) {
      showModalAlert('train-modal-alert', `Station "${stName}" not found. Pick from suggestions.`);
      return;
    }
    if (matched) {
      stops.push({ stationId: matched.station_id, stopNumber: i + 1, departureTime: depTime || '00:00:00', arrivalTime: arrTime || '00:00:00', distanceKm: distKm });
    }
  }

  const btn = document.getElementById('save-train-btn');
  btn.disabled = true;
  btn.textContent = 'Saving...';
  try {
    await AdminAPI.createTrain({ trainName: name, trainType: type, baseFare: base, farePerKm: perKm, stops });
    closeTrainModal();
    showToast('Train created successfully!');
    loadTrains();
    loadStats();
  } catch (err) {
    showModalAlert('train-modal-alert', err.message);
  } finally {
    btn.disabled = false;
    btn.textContent = 'Save Train';
  }
}

function closeTrainModal() {
  document.getElementById('train-modal').classList.remove('open');
}

// ── Stations ──────────────────────────────────────────────────────
async function loadStations() {
  const tb = document.getElementById('stations-tbody');
  try {
    const data = await AdminAPI.getStations();
    const list = data.stations || data || [];
    if (list.length === 0) {
      tb.innerHTML = '<tr><td colspan="4" style="text-align:center;color:var(--text-muted);">No stations found.</td></tr>';
      return;
    }
    tb.innerHTML = list.slice(0, 100).map(s => `
      <tr>
        <td style="font-family:monospace;">#${s.station_id}</td>
        <td style="font-weight:600;">${escHtml(s.station_name)}</td>
        <td>${escHtml(s.city || '—')}</td>
        <td><span style="font-size:0.8rem;color:var(--text-muted);">${s.station_code || '—'}</span></td>
      </tr>
    `).join('');
  } catch (err) {
    tb.innerHTML = `<tr><td colspan="4" style="color:var(--red);text-align:center;">${err.message}</td></tr>`;
  }
}

function openAddStationModal() {
  document.getElementById('station-code').value  = '';
  document.getElementById('station-name').value  = '';
  document.getElementById('station-city').value  = '';
  document.getElementById('station-modal-alert').classList.remove('show');
  document.getElementById('station-modal').classList.add('open');
}
function closeStationModal() {
  document.getElementById('station-modal').classList.remove('open');
}
async function saveStation() {
  const code = document.getElementById('station-code').value.trim();
  const name = document.getElementById('station-name').value.trim();
  const city = document.getElementById('station-city').value.trim();

  if (!name || !city) {
    showModalAlert('station-modal-alert', 'Station name and city are required.');
    return;
  }
  const btn = document.getElementById('save-station-btn');
  btn.disabled = true; btn.textContent = 'Saving...';
  try {
    await AdminAPI.createStation({ stationCode: code, stationName: name, city });
    closeStationModal();
    showToast('Station added successfully!');
    loadStations();
    loadStats();
  } catch (err) {
    showModalAlert('station-modal-alert', err.message);
  } finally {
    btn.disabled = false; btn.textContent = 'Save Station';
  }
}

// ── Bookings ──────────────────────────────────────────────────────
async function loadBookings() {
  const tb = document.getElementById('bookings-tbody');
  try {
    const data = await AdminAPI.getBookings();
    if (!data.bookings || data.bookings.length === 0) {
      tb.innerHTML = '<tr><td colspan="5" style="text-align:center;color:var(--text-muted);">No bookings found.</td></tr>';
      return;
    }
    tb.innerHTML = data.bookings.map(b => `
      <tr>
        <td style="font-family:monospace;">#${b.booking_id}</td>
        <td>
          <div style="font-weight:600;">${escHtml(b.user_name)}</div>
          <div style="font-size:0.8rem;color:var(--text-muted);">${escHtml(b.user_email)}</div>
        </td>
        <td>
          <div style="font-weight:600;">${escHtml(b.train_name)}</div>
          <div style="font-size:0.8rem;color:var(--text-muted);">${b.train_type}</div>
        </td>
        <td>${new Date(b.journey_date).toLocaleDateString('en-IN')}</td>
        <td><span class="badge ${b.status === 'ACTIVE' ? 'badge-active' : 'badge-cancelled'}">${b.status}</span></td>
      </tr>
    `).join('');
  } catch (err) {
    tb.innerHTML = `<tr><td colspan="5" style="color:var(--red);text-align:center;">${err.message}</td></tr>`;
  }
}

// ── Helpers ───────────────────────────────────────────────────────
function escHtml(str) {
  return String(str || '').replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/"/g,'&quot;');
}
function showModalAlert(elId, msg) {
  const el = document.getElementById(elId);
  if (!el) return;
  el.className = 'alert show alert-error';
  el.innerHTML = `<span>⚠️</span> ${msg}`;
}
