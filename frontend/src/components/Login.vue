<template>
  <section class="container mt-4">
    <h2>Login to Your Account</h2>
    <form @submit.prevent="login" class="card">
      <label>
        Username
        <input v-model="username" required />
      </label>
      <label>
        Password
        <input type="password" v-model="password" required />
      </label>
      <button type="submit">Login</button>
      <p v-if="error" class="text-center" style="color: var(--error); margin: 8px 0;">{{ error }}</p>
    </form>
  </section>
</template>
<script setup>
import { ref } from 'vue';
import api from '../services/api';
import { useRouter, useRoute } from 'vue-router';
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
    const { data } = await api.post('/api/users/login', { username: username.value, password: password.value });
    localStorage.setItem('token', data.token);
    localStorage.setItem('user', JSON.stringify(data.user));
    toast.success('Welcome back!');
    const redirect = route.query.redirect || '/';
    router.push(redirect);
  } catch (e) {
    error.value = 'Invalid credentials';
  }
}
</script>
