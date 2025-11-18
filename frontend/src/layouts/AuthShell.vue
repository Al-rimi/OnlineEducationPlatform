<template>
  <div class="auth-wrapper">
    <div class="auth-card">
      <slot />
    </div>
  </div>
</template>
<script setup>
// Stateless layout shell
</script>
<style scoped>
.auth-wrapper{min-height:100vh;display:grid;place-items:center;background:linear-gradient(135deg,var(--bg) 0%,var(--surface-1) 100%);padding:24px}
.auth-card{width:100%;max-width:420px;background:var(--surface-2);border:1px solid var(--border);border-radius:18px;box-shadow:0 6px 24px rgba(0,0,0,.06);padding:36px 34px 40px;animation:fadeIn .4s ease}
@keyframes fadeIn{from{opacity:0;transform:translateY(12px)}to{opacity:1;transform:translateY(0)}}
</style>
