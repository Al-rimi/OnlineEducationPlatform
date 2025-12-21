<template>
  <div>Redirecting...</div>
</template>
<script setup>
import { onMounted } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

onMounted(() => {
  console.log('RoleRedirect: Starting redirect');
  const token = localStorage.getItem('token');
  const user = JSON.parse(localStorage.getItem('user') || '{}');
  console.log('RoleRedirect: token exists:', !!token);
  console.log('RoleRedirect: user:', user);
  if (!token || !user.roles) {
    console.log('RoleRedirect: No token or no user roles, redirecting to login');
    router.replace('/login');
    return;
  }

  // Check if token is expired
  try {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const payload = JSON.parse(atob(base64));
    const currentTime = Date.now() / 1000;
    
    if (payload.exp < currentTime) { // No buffer
      console.warn('Token expired during role redirect, clearing data');
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      router.replace('/login');
      return;
    }

    const roles = user.roles || [];
    console.log('RoleRedirect: user roles:', roles);
    const target = pickHome(roles);
    console.log('RoleRedirect: redirecting to:', target);
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