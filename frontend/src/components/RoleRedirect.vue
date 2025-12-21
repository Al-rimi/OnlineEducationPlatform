<template>
  <div>Redirecting...</div>
</template>
<script setup>
import { onMounted } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

onMounted(() => {
  const token = localStorage.getItem('token');
  if (!token) {
    router.replace('/login');
    return;
  }

  // Check if token is expired
  try {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const payload = JSON.parse(atob(base64));
    const currentTime = Date.now() / 1000;
    
    if (payload.exp < currentTime) {
      console.warn('Token expired during role redirect, clearing data');
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      router.replace('/login');
      return;
    }

    const roles = payload.roles || [];
    const target = pickHome(roles);
    router.replace(target);
  } catch (e) {
    console.error('Token parsing error during role redirect:', e);
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    router.replace('/login');
  }
});

function pickHome(roles = []) {
  if (roles.includes('ADMIN')) return '/admin';
  if (roles.includes('TEACHER')) return '/teacher';
  return '/student';
}
</script>