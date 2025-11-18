<template>
  <section>
    <h2>Login</h2>
    <form @submit.prevent="login">
      <label>Username <input v-model="username" required /></label>
      <label>Password <input type="password" v-model="password" required /></label>
      <button type="submit">Login</button>
      <p v-if="error" class="err">{{ error }}</p>
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
    toast.success('Welcome back!');
    const redirect = route.query.redirect || '/';
    router.push(redirect);
  } catch (e) {
    error.value = 'Invalid credentials';
  }
}
</script>
<style scoped>.err{color:#c62828;margin-top:8px;}</style>
