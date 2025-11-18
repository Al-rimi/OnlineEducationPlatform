<template>
  <div>
    <h2 class="title">Create your account</h2>
    <p class="sub">Join the platform</p>
    <form @submit.prevent="register" class="auth-form">
      <label>Username
        <input v-model="username" placeholder="yourname" required />
      </label>
      <label>Password
        <input type="password" v-model="password" placeholder="••••••••" minlength="3" required />
      </label>
      <label>Email (optional)
        <input type="email" v-model="email" placeholder="you@example.com" />
      </label>
      <button type="submit">Create account</button>
      <p v-if="message" :class="status">{{ message }}</p>
    </form>
    <p class="muted">Already have an account? <router-link to="/login">Sign in</router-link></p>
  </div>
</template>
<script setup>
import { ref } from 'vue';
import { UsersApi } from '../services/api';

const username = ref('');
const password = ref('');
const email = ref('');
const message = ref('');
const status = ref('');

async function register() {
  message.value = '';
  status.value = '';
  try {
    await UsersApi.register({ username: username.value, password: password.value, email: email.value });
    message.value = 'Registration successful. You can log in now.';
    status.value = 'ok';
    username.value = password.value = email.value = '';
  } catch (e) {
    message.value = e?.response?.status === 409 ? 'Username already exists' : 'Registration failed';
    status.value = 'err';
  }
}
</script>
<style scoped>
.title{margin:0;font-size:22px}
.sub{margin:4px 0 16px;color:var(--text-2)}
.auth-form{margin-top:12px}
.ok{color:green}
.err{color:#c62828}
.muted{margin-top:14px;color:var(--text-2)}
</style>
