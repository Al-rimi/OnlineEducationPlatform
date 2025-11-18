<template>
  <div v-if="toasts.length" class="toast-wrap">
    <div v-for="t in toasts" :key="t.id" class="toast" :class="t.type">{{ t.msg }}</div>
  </div>
  </template>
<script setup>
import { useToast } from '../stores/toast';
const { toasts } = useToast();
</script>
<style scoped>
.toast-wrap{position:fixed;top:12px;right:12px;display:flex;flex-direction:column;gap:8px;z-index:9999}
.toast{padding:10px 12px;border-radius:6px;color:#fff;box-shadow:0 2px 8px rgba(0,0,0,.15)}
.toast.info{background:#1976d2}
.toast.success{background:#2e7d32}
.toast.error{background:#c62828}
</style>
