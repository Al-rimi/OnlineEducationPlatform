<template>
  <div>
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
    <p class="muted">No account? <router-link to="/register">Create one</router-link></p>
  </div>
</template>
<script setup>
import { ref } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { UsersApi } from '../services/api';
import { useToast } from '../stores/toast';

const router = useRouter();
const route = useRoute();
const username = ref('');
const password = ref('');
const error = ref('');
const toast = useToast();

async function login() {
  error.value = '';
  try {
    const { data } = await UsersApi.login({ username: username.value, password: password.value });
    localStorage.setItem('token', data.token);
    toast.success('Logged in');
    const redirect = route.query.redirect || '/';
    router.push(redirect);
  } catch (e) {
    error.value = 'Invalid username or password';
  }
}
</script>
<style scoped>
.title{margin:0;font-size:22px}
.sub{margin:4px 0 16px;color:var(--text-2)}
.auth-form{margin-top:12px}
.err{color:#c62828}
.muted{margin-top:14px;color:var(--text-2)}
</style>
