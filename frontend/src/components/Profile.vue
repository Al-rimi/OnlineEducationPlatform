<template>
  <section>
    <h2>My Profile</h2>
    <div v-if="me" class="card">
      <p><strong>Username:</strong> {{ me.username }}</p>
      <p><strong>Email:</strong> {{ me.email || '—' }}</p>
      <p class="dim">(This page is protected and requires login)</p>
    </div>
    <p v-else>Loading...</p>
  </section>
</template>
<script setup>
import { onMounted, ref } from 'vue';
import api from '../services/api';

const me = ref(null);
onMounted(async () => {
  const { data } = await api.get('/api/users/me');
  me.value = data;
});
</script>
<style scoped>
.card{padding:12px;border:1px solid #e0e0e0;border-radius:8px;background:#fff}
.dim{color:#666;margin-top:8px}
</style>
