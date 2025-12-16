<template>
  <div>
    <div class="tabs">
      <button :class="{ active: mode === 'login' }" @click="mode = 'login'">Sign In</button>
      <button :class="{ active: mode === 'register' }" @click="mode = 'register'">Sign Up</button>
    </div>
    <div v-if="mode === 'login'">
      <h2 class="title">Welcome back</h2>
      <p class="sub">Sign in to your account</p>
      <form @submit.prevent="login" class="auth-form">
        <label>Username
          <input v-model="username" placeholder="yourname" required />
        </label>
        <label>Password
          <input type="password" v-model="password" placeholder="••••••••" required />
        </label>
        <button type="submit">Sign in</button>
        <p v-if="error" class="err">{{ error }}</p>
      </form>
    </div>
    <div v-if="mode === 'register'">
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
    </div>
  </div>
</template>
<script setup>
import { ref } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { UsersApi } from '../services/api';
import { useToast } from '../stores/toast';

const props = defineProps({
  mode: { type: String, default: 'login' }
});

const router = useRouter();
const route = useRoute();
const mode = ref(props.mode);
const username = ref('');
const password = ref('');
const email = ref('');
const error = ref('');
const message = ref('');
const status = ref('');
const toast = useToast();

async function login() {
  error.value = '';
  try {
    const { data } = await UsersApi.login({ username: username.value, password: password.value });
    localStorage.setItem('token', data.token);
    if (data.user) {
      localStorage.setItem('user', JSON.stringify(data.user));
    }
    toast.success('Logged in');
    const redirect = route.query.redirect || pickHome(data.user?.roles);
    router.push(redirect);
  } catch (e) {
    error.value = 'Invalid username or password';
  }
}

async function register() {
  message.value = '';
  status.value = '';
  try {
    await UsersApi.register({ username: username.value, password: password.value, email: email.value });
    message.value = 'Registration successful. You can log in now.';
    status.value = 'ok';
    username.value = password.value = email.value = '';
    mode.value = 'login';
  } catch (e) {
    message.value = e?.response?.status === 409 ? 'Username already exists' : 'Registration failed';
    status.value = 'err';
  }
}

function pickHome(roles = []) {
  if (roles.includes('ADMIN')) return '/admin';
  if (roles.includes('TEACHER')) return '/teacher';
  return '/student';
}
</script>
<style scoped>
.tabs{display:flex;margin-bottom:16px}
.tabs button{flex:1;padding:8px;border:none;background:#f0f0f0}
.tabs button.active{background:#007bff;color:#fff}
.title{margin:0;font-size:22px}
.sub{margin:4px 0 16px;color:var(--text-2)}
.auth-form{margin-top:12px}
.err{color:#c62828}
.ok{color:green}
</style>