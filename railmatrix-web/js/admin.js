// js/admin.js — Logic for the Admin Dashboard

if (!AdminAuth.isLoggedIn()) {
  window.location.href = 'admin-login.html';
}

const adminUser = AdminAuth.user();
console.log('Admin logged in:', adminUser);

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

// ── Modals ──────────────────────────────────────────────────────
function openBlockModal(userId) {
  console.log('Block modal for', userId);
  document.getElementById('block-modal').classList.add('open');
}
function closeBlockModal() { document.getElementById('block-modal').classList.remove('open'); }
function submitBlock() {
  alert('User suspended (Simulation)');
  closeBlockModal();
}

function openAddTrainModal() { document.getElementById('train-modal').classList.add('open'); }
function closeTrainModal() { document.getElementById('train-modal').classList.remove('open'); }
function saveTrain() {
  alert('Train saved (Simulation)');
  closeTrainModal();
}
function addRouteStop() {
  const rb = document.getElementById('route-builder');
  const d = document.createElement('div');
  d.className = 'route-stop';
  d.innerHTML = `<input type="text" class="input" placeholder="Station Code" style="flex:1;"><input type="number" class="input" placeholder="Day" style="width:80px;"><input type="time" class="input">`;
  rb.appendChild(d);
}

function openAddStationModal() { document.getElementById('station-modal').classList.add('open'); }
function closeStationModal() { document.getElementById('station-modal').classList.remove('open'); }
function saveStation() {
  alert('Station saved (Simulation)');
  closeStationModal();
}

// ── Data Loading ────────────────────────────────────────────────
async function loadStats() {
  try {
    const data = await AdminAPI.getStats();
    document.getElementById('s-users').textContent = data.totalUsers;
    document.getElementById('s-trains').textContent = data.totalTrains;
    document.getElementById('s-stations').textContent = data.totalStations;
    document.getElementById('s-bookings').textContent = data.totalBookings;
  } catch (err) {
    console.error('Stats load err:', err);
  }
}

async function loadUsers() {
  const tb = document.getElementById('users-tbody');
  try {
    const data = await AdminAPI.getUsers();
    if (!data.users || data.users.length === 0) {
      tb.innerHTML = '<tr><td colspan="6" style="text-align:center;">No users found.</td></tr>';
      return;
    }
    tb.innerHTML = data.users.map(u => `
      <tr>
        <td>#${u.user_id}</td>
        <td style="font-weight:600;">${u.name}</td>
        <td>${u.email}</td>
        <td>${u.total_bookings} Bookings</td>
        <td><span class="badge ${u.is_verified ? 'badge-active' : ''}">${u.is_verified ? 'Verified' : 'Unverified'}</span></td>
        <td><button class="btn btn-ghost btn-sm" style="color:var(--red);" onclick="openBlockModal(${u.user_id})">Suspend</button></td>
      </tr>
    `).join('');
  } catch (err) {
    tb.innerHTML = `<tr><td colspan="6" style="color:red; text-align:center;">${err.message}</td></tr>`;
  }
}

async function loadTrains() {
  const tb = document.getElementById('trains-tbody');
  try {
    const data = await AdminAPI.getTrains();
    if (!data.trains || data.trains.length === 0) {
      tb.innerHTML = '<tr><td colspan="6" style="text-align:center;">No trains found.</td></tr>';
      return;
    }
    tb.innerHTML = data.trains.map(t => `
      <tr>
        <td>#${t.train_id}</td>
        <td style="font-weight:600;">${t.train_name}</td>
        <td>${t.train_type}</td>
        <td>${t.total_seats} Seats</td>
        <td><span class="badge badge-active">Active</span></td>
        <td><button class="btn btn-ghost btn-sm" onclick="alert('Manage routes for ${t.train_id}')">Routes</button></td>
      </tr>
    `).join('');
  } catch (err) {
    tb.innerHTML = `<tr><td colspan="6" style="color:red; text-align:center;">${err.message}</td></tr>`;
  }
}

async function loadStations() {
  const tb = document.getElementById('stations-tbody');
  try {
    const data = await AdminAPI.getStations();
    if (!data || data.length === 0) {
      tb.innerHTML = '<tr><td colspan="4" style="text-align:center;">No stations found.</td></tr>';
      return;
    }
    tb.innerHTML = data.slice(0,50).map(s => `
      <tr>
        <td>#${s.station_id}</td>
        <td style="font-weight:600;">${s.station_name}</td>
        <td>${s.city || 'N/A'}</td>
        <td><button class="btn btn-ghost btn-sm" style="color:var(--red);">Delete</button></td>
      </tr>
    `).join('');
  } catch (err) {
    tb.innerHTML = `<tr><td colspan="4" style="color:red; text-align:center;">${err.message}</td></tr>`;
  }
}

async function loadBookings() {
  const tb = document.getElementById('bookings-tbody');
  try {
    const data = await AdminAPI.getBookings();
    if (!data.bookings || data.bookings.length === 0) {
      tb.innerHTML = '<tr><td colspan="5" style="text-align:center;">No bookings found.</td></tr>';
      return;
    }
    tb.innerHTML = data.bookings.map(b => `
      <tr>
        <td style="font-family:monospace;">#${b.booking_id}</td>
        <td>
          <div style="font-weight:600;">${b.user_name}</div>
          <div style="font-size:0.8rem;color:var(--text-muted);">${b.user_email}</div>
        </td>
        <td>
          <div style="font-weight:600;">${b.train_name}</div>
          <div style="font-size:0.8rem;color:var(--text-muted);">${b.train_type}</div>
        </td>
        <td>${new Date(b.journey_date).toLocaleDateString()}</td>
        <td><span class="badge ${b.status === 'ACTIVE' ? 'badge-active' : 'badge-cancelled'}">${b.status}</span></td>
      </tr>
    `).join('');
  } catch (err) {
    tb.innerHTML = `<tr><td colspan="5" style="color:red; text-align:center;">${err.message}</td></tr>`;
  }
}
