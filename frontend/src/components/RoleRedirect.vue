<template>
  <div>Redirecting...</div>
</template>
<script setup>
import { onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { UsersApi } from '../services/api';

const router = useRouter();

onMounted(async () => {
  try {
    const cached = localStorage.getItem('user');
    const cachedUser = cached ? JSON.parse(cached) : null;
    const { data: fetchedUser } = await UsersApi.me();
    const user = fetchedUser || cachedUser;
    const roles = (user && user.roles) || [];
    const target = pickHome(roles);
    localStorage.setItem('user', JSON.stringify(user));
    router.replace(target);
  } catch (e) {
    router.replace('/login');
  }
});

function pickHome(roles = []) {
  if (roles.includes('ADMIN')) return '/admin';
  if (roles.includes('TEACHER')) return '/teacher';
  return '/student';
}
</script>