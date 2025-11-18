<template>
  <section>
    <h2>Dashboard</h2>
    <p>Welcome back<span v-if="me">, {{ me.username }}</span>! 🎉</p>
    <ul class="cards">
      <li class="card">Total Users: <strong>{{ users.length }}</strong></li>
      <li class="card">Sample Courses: <strong>3</strong></li>
      <li class="card">Your Email: <strong>{{ me?.email || '—' }}</strong></li>
    </ul>
  </section>
</template>
<script setup>
import { onMounted, ref } from 'vue';
import api from '../services/api';

const users = ref([]);
const me = ref(null);

onMounted(async () => {
  try { me.value = (await api.get('/api/users/me')).data; } catch {}
  try { users.value = (await api.get('/api/users')).data; } catch {}
});
</script>
<style scoped>
.cards{display:grid;grid-template-columns:repeat(auto-fit,minmax(200px,1fr));gap:12px;margin-top:12px}
.card{padding:12px;border:1px solid #e0e0e0;border-radius:8px;background:#fafafa}
</style>
