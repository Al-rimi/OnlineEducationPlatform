<template>
  <RouterView v-slot="{ Component }">
    <component :is="layout">
      <component :is="Component" />
    </component>
  </RouterView>
  
</template>

<script setup>
import { computed, onMounted } from 'vue';
import { useRoute, RouterView } from 'vue-router';
import AppShell from './layouts/AppShell.vue';
import AuthShell from './layouts/AuthShell.vue';

const route = useRoute();
const layout = computed(() => route.meta.layout === 'auth' ? AuthShell : AppShell);

// Token validation function
function isTokenExpired(token) {
  try {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const payload = JSON.parse(atob(base64));
    const currentTime = Date.now() / 1000;
    return payload.exp < currentTime;
  } catch (e) {
    console.error('Token validation error:', e);
    return true;
  }
}

// Clear authentication data
function clearAuthData() {
  localStorage.removeItem('token');
  localStorage.removeItem('user');
}

// Validate stored token on app start
onMounted(() => {
  const token = localStorage.getItem('token');
  if (token && isTokenExpired(token)) {
    console.warn('Stored token is expired, clearing authentication data');
    clearAuthData();
  }
});
</script>

<style>
/* Root styles and design system */
:root{
  --bg:#0e1116; /* page background gradient start (used in auth) */
  --surface-1:#ffffff;
  --surface-2:#fcfcfc;
  --border:#e9e9ec;
  --text-1:#111827;
  --text-2:#374151;
  --primary:#2563eb;
  --primary-contrast:#fff;
}
html,body,#app{height:100%}
body{margin:0;background:var(--surface-2);color:var(--text-1);font-family: ui-sans-serif, system-ui, -apple-system, Segoe UI, Roboto, Ubuntu, Cantarell, Noto Sans, Helvetica Neue, Arial, "Apple Color Emoji", "Segoe UI Emoji";}
.container{max-width:1100px;margin:0 auto;padding:0 16px}
a{color:var(--primary)}
input,button{font:inherit}
input,select,textarea{width:100%;padding:10px 12px;border:1px solid var(--border);border-radius:10px;background:#fff}
label{display:flex;flex-direction:column;gap:6px}
form{display:grid;gap:12px}
button{padding:10px 14px;border-radius:10px;border:1px solid var(--border);background:var(--primary);color:var(--primary-contrast);cursor:pointer}
button.secondary{background:#f6f8fb;color:var(--text-1)}
.card{border:1px solid var(--border);border-radius:16px;background:#fff;padding:16px}
</style>
