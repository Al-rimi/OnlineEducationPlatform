<template>
  <div class="layout">
    <header class="app-header">
      <div class="container header-inner">
        <h1 class="brand">Edu<span>Platform</span></h1>
        <nav class="nav">
          <router-link to="/">Dashboard</router-link>
          <router-link to="/courses">Courses</router-link>
          <router-link to="/users">Users</router-link>
          <router-link to="/add">Add User</router-link>
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
import { ref } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import Toast from '../components/Toast.vue';

const router = useRouter();
const route = useRoute();
const token = ref(typeof localStorage !== 'undefined' ? localStorage.getItem('token') : null);

function logout() {
  localStorage.removeItem('token');
  token.value = null;
  router.push({ path: '/login', query: { redirect: route.fullPath } });
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
