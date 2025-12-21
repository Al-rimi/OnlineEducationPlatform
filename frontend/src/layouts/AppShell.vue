<template>
  <div class="layout">
    <header class="app-header">
      <div class="container header-inner">
        <h1 class="brand">Edu<span>Platform</span></h1>
        <nav class="nav">
          <!-- Admin Navigation -->
          <template v-if="hasRole('ADMIN')">
            <router-link to="/admin">Dashboard</router-link>
            <router-link to="/users">Users</router-link>
            <router-link to="/courses">Courses</router-link>
            <router-link to="/assignments">Assignments</router-link>
            <router-link to="/quizzes">Quizzes</router-link>
          </template>
          
          <!-- Teacher Navigation -->
          <template v-else-if="hasRole('TEACHER')">
            <router-link to="/teacher">Dashboard</router-link>
            <router-link to="/courses">My Courses</router-link>
            <router-link to="/assignments">Assignments</router-link>
            <router-link to="/quizzes">Quizzes</router-link>
          </template>
          
          <!-- Student Navigation -->
          <template v-else-if="hasRole('STUDENT')">
            <router-link to="/student">Dashboard</router-link>
            <router-link to="/courses">Browse Courses</router-link>
            <router-link to="/assignments">Assignments</router-link>
            <router-link to="/quizzes">Quizzes</router-link>
          </template>
          
          <!-- Common Navigation -->
          <router-link to="/profile" v-if="token">My Profile</router-link>
          <router-link to="/login" v-if="!token">Login</router-link>
          <router-link to="/register" v-if="!token">Register</router-link>
          <button v-if="token" class="btn btn-danger" @click="logout">Logout</button>
        </nav>
      </div>
    </header>
    <main class="container content">
      <slot />
    </main>
  </div>
  <Toast />
  
</template>
<script setup>
import { ref, computed } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import Toast from '../components/Toast.vue';

const router = useRouter();
const route = useRoute();
const token = ref(typeof localStorage !== 'undefined' ? localStorage.getItem('token') : null);

const userRoles = computed(() => {
  if (!token.value) return [];
  try {
    const payload = JSON.parse(atob(token.value.split('.')[1]));
    return payload.roles || [];
  } catch (e) {
    return [];
  }
});

function hasRole(role) {
  return userRoles.value.includes(role);
}

// Clear all authentication data
function clearAuthData() {
  localStorage.removeItem('token');
  localStorage.removeItem('user');
  // Clear any other stored authentication data
  sessionStorage.clear(); // Clear session storage as well
}

function logout() {
  clearAuthData();
  token.value = null;
  router.push('/login');
}
</script>
<style scoped>
.app-header{position:sticky;top:0;background:var(--surface-1);border-bottom:1px solid var(--border);z-index:10}
.header-inner{display:flex;align-items:center;justify-content:space-between;padding:12px 0}
.brand{font-size:20px;letter-spacing:0.3px}
.brand span{color:var(--primary)}
.nav{display:flex;gap:12px;align-items:center}
.nav a{color:var(--text-2);text-decoration:none;font-weight:600}
.nav a.router-link-active{color:var(--primary)}
.content{padding:20px 0}
.btn{padding:6px 10px;border-radius:6px;border:1px solid transparent;cursor:pointer}
.btn-danger{background:#c62828;color:#fff}
</style>
