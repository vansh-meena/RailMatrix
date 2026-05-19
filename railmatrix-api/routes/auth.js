// routes/auth.js
// POST /api/auth/register
// POST /api/auth/login
// GET  /api/auth/verify-email?token=xxx

const express  = require('express');
const router   = express.Router();
const crypto   = require('crypto');
const jwt      = require('jsonwebtoken');
const nodemailer = require('nodemailer');
const pool     = require('../db');

// ── SHA-256 hashing — matches Java UserDAO.hashPassword() exactly ──
function hashPassword(password) {
    return crypto.createHash('sha256').update(password, 'utf8').digest('hex');
}

// ── Password strength validator ───────────────────────────────────
function validatePassword(password) {
    const errors = [];
    if (!password || password.length < 8)
        errors.push('Password must be at least 8 characters.');
    if (!/[A-Z]/.test(password))
        errors.push('Password must contain at least 1 uppercase letter.');
    if (!/[0-9]/.test(password))
        errors.push('Password must contain at least 1 number.');
    if (!/[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/.test(password))
        errors.push('Password must contain at least 1 special character (e.g. @, #, !).');
    return errors;
}

// ── Nodemailer transporter (same Gmail SMTP as EmailService.java) ──
function createTransporter() {
    return nodemailer.createTransport({
        host:   'smtp.gmail.com',
        port:   465,
        secure: true,
        auth: {
            user: process.env.EMAIL_FROM,
            pass: process.env.EMAIL_PASS
        }
    });
}

// ── Send verification email ───────────────────────────────────────
async function sendVerificationEmail(toEmail, userName, token) {
    const verifyLink = `${process.env.API_BASE_URL}/api/auth/verify-email?token=${token}`;

    const html = `
        <!DOCTYPE html>
        <html>
        <head><meta charset="UTF-8"><style>
            body { font-family: 'Helvetica Neue', Arial, sans-serif; background: #f5f0ff; margin:0; padding:20px; }
            .container { max-width:560px; margin:0 auto; background:white; border-radius:16px; overflow:hidden; box-shadow:0 4px 20px rgba(72,52,120,0.15); }
            .header { background:linear-gradient(135deg, #32235a, #483478); padding:28px 32px; }
            .header h1 { color:white; margin:0; font-size:22px; }
            .header p  { color:#c8b8e8; margin:4px 0 0; font-size:14px; }
            .body { padding:28px 32px; }
            .greeting { font-size:16px; color:#32235a; margin-bottom:20px; }
            .btn { display:inline-block; background:#483478; color:white; text-decoration:none;
                   padding:14px 32px; border-radius:10px; font-weight:bold; font-size:15px; margin:20px 0; }
            .note { color:#888; font-size:12px; margin-top:16px; }
            .footer { background:#f8f4ff; padding:16px 32px; text-align:center; color:#aaa; font-size:12px; }
        </style></head>
        <body>
            <div class="container">
                <div class="header">
                    <h1>🚆 RailMatrix</h1>
                    <p>Verify your email address</p>
                </div>
                <div class="body">
                    <p class="greeting">Hello <strong>${userName}</strong>,<br>
                    Thanks for registering! Click the button below to verify your email and activate your account.</p>
                    <a href="${verifyLink}" class="btn">✅ Verify My Email</a>
                    <p class="note">This link expires in 24 hours. If you didn't create an account, you can ignore this email.</p>
                    <p class="note">Or paste this link in your browser:<br>${verifyLink}</p>
                </div>
                <div class="footer">© 2026 RailMatrix · This is an automated email, please do not reply.</div>
            </div>
        </body>
        </html>
    `;

    const transporter = createTransporter();
    await transporter.sendMail({
        from:    `"${process.env.EMAIL_FROM_NAME}" <${process.env.EMAIL_FROM}>`,
        to:      toEmail,
        subject: '🚆 Verify your RailMatrix email',
        html
    });
}

// ─────────────────────────────────────────────────────────────────
// POST /api/auth/register
// Body: { name, email, password, confirmPassword }
// ─────────────────────────────────────────────────────────────────
router.post('/register', async (req, res) => {
    const { name, email, password, confirmPassword } = req.body;

    // ── 1. Basic field validation ──────────────────────────────────
    if (!name || !email || !password || !confirmPassword) {
        return res.status(400).json({ error: 'All fields are required.' });
    }

    if (!email.includes('@') || !email.includes('.')) {
        return res.status(400).json({ error: 'Please enter a valid email address.' });
    }

    if (password !== confirmPassword) {
        return res.status(400).json({ error: 'Passwords do not match.' });
    }

    // ── 2. Password strength check ────────────────────────────────
    const pwErrors = validatePassword(password);
    if (pwErrors.length > 0) {
        return res.status(400).json({ error: pwErrors[0], errors: pwErrors });
    }

    try {
        // ── 3. Check if email already exists ──────────────────────
        const [existing] = await pool.query(
            'SELECT user_id FROM users WHERE email = ?', [email]
        );
        if (existing.length > 0) {
            return res.status(400).json({ error: 'Email already registered. Try logging in.' });
        }

        // ── 4. Generate verification token ───────────────────────
        const token = crypto.randomBytes(32).toString('hex');

        // ── 5. Hash password (SHA-256, same as Java) ──────────────
        const hashedPassword = hashPassword(password);

        // ── 6. Insert user (is_verified = 0, must verify email) ───
        await pool.query(
            'INSERT INTO users (name, email, password, is_verified, verification_token) VALUES (?, ?, ?, 0, ?)',
            [name.trim(), email.trim().toLowerCase(), hashedPassword, token]
        );

        // ── 7. Send verification email ────────────────────────────
        try {
            await sendVerificationEmail(email, name, token);
        } catch (emailErr) {
            console.error('Email send failed (registration still succeeded):', emailErr.message);
            // Don't fail the registration if email fails
        }

        return res.status(201).json({
            message: `Registration successful! A verification email has been sent to ${email}. Please verify before logging in.`
        });

    } catch (err) {
        console.error('Register error:', err);
        return res.status(500).json({ error: 'Registration failed. Please try again.' });
    }
});

// ─────────────────────────────────────────────────────────────────
// POST /api/auth/login
// Body: { email, password }
// Returns: { token, user: { userId, name, email } }
// ─────────────────────────────────────────────────────────────────
router.post('/login', async (req, res) => {
    const { email, password } = req.body;

    if (!email || !password) {
        return res.status(400).json({ error: 'Email and password are required.' });
    }

    try {
        const [rows] = await pool.query(
            'SELECT * FROM users WHERE email = ?',
            [email.trim().toLowerCase()]
        );

        if (rows.length === 0) {
            return res.status(401).json({ error: 'No account found with this email.' });
        }

        const user = rows[0];

        // ── Check password ────────────────────────────────────────
        if (user.password !== hashPassword(password)) {
            return res.status(401).json({ error: 'Incorrect password. Please try again.' });
        }

        // ── Check email verified (only for is_verified column present) ──
        // Existing users have is_verified = 1 (set by migration)
        if (user.is_verified === 0) {
            return res.status(403).json({
                error: 'Email not verified. Please check your inbox and click the verification link.'
            });
        }

        // ── Issue JWT ─────────────────────────────────────────────
        const token = jwt.sign(
            { userId: user.user_id, email: user.email, name: user.name },
            process.env.JWT_SECRET,
            { expiresIn: '24h' }
        );

        return res.json({
            message: 'Login successful!',
            token,
            user: {
                userId: user.user_id,
                name:   user.name,
                email:  user.email
            }
        });

    } catch (err) {
        console.error('Login error:', err);
        return res.status(500).json({ error: 'Login failed. Please try again.' });
    }
});

// ─────────────────────────────────────────────────────────────────
// GET /api/auth/verify-email?token=xxx
// Called when user clicks the link in their email
// ─────────────────────────────────────────────────────────────────
router.get('/verify-email', async (req, res) => {
    const { token } = req.query;

    if (!token) {
        return res.status(400).send(buildVerifyPage(false, 'No verification token provided.'));
    }

    try {
        const [rows] = await pool.query(
            'SELECT user_id, name, is_verified FROM users WHERE verification_token = ?',
            [token]
        );

        if (rows.length === 0) {
            return res.status(400).send(buildVerifyPage(false, 'Invalid or expired verification link.'));
        }

        const user = rows[0];

        if (user.is_verified === 1) {
            return res.send(buildVerifyPage(true, `Your email is already verified, ${user.name}!`));
        }

        // Mark as verified, clear the token
        await pool.query(
            'UPDATE users SET is_verified = 1, verification_token = NULL WHERE user_id = ?',
            [user.user_id]
        );

        return res.send(buildVerifyPage(true, `Email verified successfully! Welcome to RailMatrix, ${user.name}!`));

    } catch (err) {
        console.error('Verify email error:', err);
        return res.status(500).send(buildVerifyPage(false, 'Verification failed. Please try again.'));
    }
});

// ── Resend verification email ─────────────────────────────────────
// POST /api/auth/resend-verification
// Body: { email }
router.post('/resend-verification', async (req, res) => {
    const { email } = req.body;

    if (!email) return res.status(400).json({ error: 'Email is required.' });

    try {
        const [rows] = await pool.query(
            'SELECT user_id, name, is_verified, verification_token FROM users WHERE email = ?',
            [email.trim().toLowerCase()]
        );

        if (rows.length === 0) return res.status(404).json({ error: 'No account found with this email.' });

        const user = rows[0];
        if (user.is_verified === 1) return res.json({ message: 'Email is already verified.' });

        // Generate new token
        const newToken = crypto.randomBytes(32).toString('hex');
        await pool.query('UPDATE users SET verification_token = ? WHERE user_id = ?', [newToken, user.user_id]);

        await sendVerificationEmail(email, user.name, newToken);

        return res.json({ message: 'Verification email resent! Check your inbox.' });

    } catch (err) {
        console.error('Resend verify error:', err);
        return res.status(500).json({ error: 'Failed to resend verification email.' });
    }
});

// ── HTML page returned after email verification ───────────────────
function buildVerifyPage(success, message) {
    const icon  = success ? '✅' : '❌';
    const color = success ? '#28a050' : '#c83232';
    const title = success ? 'Email Verified!' : 'Verification Failed';
    return `
    <!DOCTYPE html><html><head><meta charset="UTF-8">
    <title>RailMatrix — ${title}</title>
    <style>
        body { font-family: 'Helvetica Neue', Arial, sans-serif; background:#f5f0ff;
               display:flex; align-items:center; justify-content:center; min-height:100vh; margin:0; }
        .card { background:white; border-radius:16px; padding:48px 40px; text-align:center;
                max-width:440px; box-shadow:0 4px 20px rgba(72,52,120,0.15); }
        .icon { font-size:56px; margin-bottom:16px; }
        h1 { color:#32235a; margin:0 0 12px; }
        p  { color:#666; font-size:15px; }
        a  { display:inline-block; margin-top:24px; background:#483478; color:white;
             text-decoration:none; padding:12px 28px; border-radius:10px; font-weight:bold; }
    </style></head>
    <body>
        <div class="card">
            <div class="icon">${icon}</div>
            <h1 style="color:${color}">${title}</h1>
            <p>${message}</p>
            <a href="${process.env.FRONTEND_URL}">Go to RailMatrix</a>
        </div>
    </body></html>`;
}

module.exports = router;
