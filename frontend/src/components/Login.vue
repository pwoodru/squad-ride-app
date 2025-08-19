<template>
  <div class="login-container">
    <div class="login-card">
      <div class="logo-section">
        <div class="logo-icon">
          <div class="logo-text">SR</div>
        </div>
        <div class="app-title">Squad Ride</div>
        <div class="app-subtitle">Sign in to Dominate the Night</div>
      </div>
      <form @submit.prevent="login">
        <div class="input-group">
          <label for="username">Username or Email</label>
          <input id="username" v-model="username" type="text" required autocomplete="username" />
        </div>
        <div class="input-group">
          <label for="password">Password</label>
          <input id="password" v-model="password" type="password" required autocomplete="current-password" />
        </div>
        <button class="main-cta" :disabled="loading">
          {{ loading ? 'Signing in...' : 'Login' }}
        </button>
        <div class="login-links">
          <a href="#" @click.prevent="forgotPassword">Forgot password?</a>
          <span>·</span>
    <a href="#" @click.prevent="signUp">Sign up</a>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const username = ref('')
const password = ref('')
const loading = ref(false)
const router = useRouter()

async function login() {
  loading.value = true
  try {
    const response = await fetch('http://localhost:8080/api/users/login', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ username: username.value })
    })
    if (response.ok) {
      loading.value = false
      router.push('/home') // Redirect to home page
    } else {
      const msg = await response.text()
      loading.value = false
      alert(msg || 'Login failed')
    }
  } catch (err) {
    loading.value = false
    alert('Network error')
  }
}
function forgotPassword() {
  alert('Forgot password flow (demo)')
}
function signUp() {
  router.push('/signup')
}
</script>

<style scoped>
.login-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #181c2f;
  position: relative;
}
.login-card {
  background: #23284a;
  border-radius: 24px;
  box-shadow: 0 8px 32px rgba(24,28,47,0.25);
  padding: 2.5rem 2rem 2rem 2rem;
  width: 340px;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.logo-section {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 2rem;
}
.logo-icon {
  background: linear-gradient(135deg, #ffb347 0%, #ffcc33 100%);
  border-radius: 50%;
  width: 56px;
  height: 56px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 0.5rem;
}
.logo-text {
  font-size: 2rem;
  font-weight: bold;
  color: #23284a;
}
.app-title {
  font-size: 1.5rem;
  font-weight: 700;
  color: #fff;
  margin-bottom: 0.25rem;
}
.app-subtitle {
  font-size: 1rem;
  color: #bfc6e0;
  margin-bottom: 1.5rem;
}
.input-group {
  width: 100%;
  margin-bottom: 1.25rem;
}
.input-group label {
  color: #bfc6e0;
  font-size: 0.95rem;
  margin-bottom: 0.25rem;
  display: block;
}
.input-group input {
  width: 100%;
  padding: 0.7rem 1rem;
  border-radius: 8px;
  border: none;
  background: #1a1e33;
  color: #fff;
  font-size: 1rem;
  outline: none;
  margin-top: 0.1rem;
}
.input-group input:focus {
  background: #23284a;
}
.main-cta {
  width: 100%;
  padding: 0.85rem;
  background: linear-gradient(90deg, #ffb347 0%, #ffcc33 100%);
  color: #23284a;
  font-weight: 700;
  font-size: 1.1rem;
  border: none;
  border-radius: 10px;
  margin-top: 0.5rem;
  margin-bottom: 1rem;
  cursor: pointer;
  transition: background 0.2s;
}
.main-cta:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}
.login-links {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 0.5rem;
  font-size: 0.95rem;
  color: #bfc6e0;
}
.login-links a {
  color: #ffcc33;
  text-decoration: none;
  cursor: pointer;
}
.login-links a:hover {
  text-decoration: underline;
}
</style>
