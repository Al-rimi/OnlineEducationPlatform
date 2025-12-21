<template>
  <section>
    <h2>{{ isEdit ? 'Edit User' : 'Add User' }}</h2>
    <form @submit.prevent="submit">
      <label>Username <input v-model="form.username" required /></label>
      <label>Password <input v-model="form.password" type="password" :required="!isEdit" /></label>
      <label>Email <input v-model="form.email" type="email" /></label>
      <div v-if="canEditRoles">
        <label>Role</label>
        <select v-model="selectedRole" required>
          <option value="">Select Role</option>
          <option value="ADMIN">Admin</option>
          <option value="TEACHER">Teacher</option>
          <option value="STUDENT">Student</option>
        </select>
      </div>
      <button type="submit">{{ isEdit ? 'Update' : 'Create' }}</button>
      <router-link to="/">Cancel</router-link>
    </form>
  </section>
</template>
<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import api from '../services/api';

const route = useRoute();
const router = useRouter();
const form = ref({ username: '', password: '', email: '' });
const selectedRole = ref('');
const isEdit = computed(() => !!route.params.id);
const me = ref(JSON.parse(localStorage.getItem('user') || '{}'));
const canEditRoles = computed(() => me.value.roles?.includes('ADMIN'));

onMounted(async () => {
  if (isEdit.value) {
    const { data } = await api.get('/api/users/' + route.params.id);
    form.value = { 
      username: data.username, 
      password: '', 
      email: data.email 
    };
    // Set the selected role from the user's roles
    if (data.roles && data.roles.length > 0) {
      selectedRole.value = data.roles[0].name;
    }
  }
});

async function submit() {
  try {
    const payload = { ...form.value };
    if (canEditRoles.value && selectedRole.value) {
      payload.roles = [selectedRole.value];
    }
    
    if (isEdit.value) {
      await api.put('/api/users/' + route.params.id, payload);
    } else {
      await api.post('/api/users', payload);
    }
    router.push('/');
  } catch (e) {
    alert('Save failed (maybe unauthorized).');
  }
}
</script>
