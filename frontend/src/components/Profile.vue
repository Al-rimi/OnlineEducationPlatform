<template>
  <section>
    <h2>My Profile</h2>
    <div v-if="loading" class="loading">Loading profile...</div>
    
    <div v-else-if="me" class="profile-container">
      <form v-if="isEditing" @submit.prevent="saveProfile" class="profile-form">
        <div class="form-group">
          <label for="username">Username:</label>
          <input 
            id="username" 
            v-model="editForm.username" 
            type="text" 
            required
            :class="{ error: errors.username }"
          />
          <span v-if="errors.username" class="error-text">{{ errors.username }}</span>
        </div>
        
        <div class="form-group">
          <label for="email">Email:</label>
          <input 
            id="email" 
            v-model="editForm.email" 
            type="email" 
            required
            :class="{ error: errors.email }"
          />
          <span v-if="errors.email" class="error-text">{{ errors.email }}</span>
        </div>
        
        <div class="form-group">
          <label for="currentPassword">Current Password (required to save changes):</label>
          <input 
            id="currentPassword" 
            v-model="editForm.currentPassword" 
            type="password" 
            required
            :class="{ error: errors.currentPassword }"
          />
          <span v-if="errors.currentPassword" class="error-text">{{ errors.currentPassword }}</span>
        </div>
        
        <div class="form-group">
          <label for="newPassword">New Password (leave empty to keep current):</label>
          <input 
            id="newPassword" 
            v-model="editForm.newPassword" 
            type="password"
            :class="{ error: errors.newPassword }"
          />
          <span v-if="errors.newPassword" class="error-text">{{ errors.newPassword }}</span>
        </div>
        
        <div class="form-actions">
          <button type="submit" :disabled="saving" class="btn primary">
            {{ saving ? 'Saving...' : 'Save Changes' }}
          </button>
          <button type="button" @click="cancelEdit" class="btn secondary">Cancel</button>
        </div>
      </form>
      
      <div v-else class="profile-view">
        <div class="profile-info">
          <p><strong>Username:</strong> {{ me.username }}</p>
          <p><strong>Email:</strong> {{ me.email || 'Not set' }}</p>
          <p><strong>Role:</strong> {{ getUserRole() }}</p>
        </div>
        
        <div class="profile-actions">
          <button @click="startEdit" class="btn primary">Edit Profile</button>
        </div>
      </div>
    </div>
    
    <div v-else class="error">
      <p>Failed to load profile. Please try logging in again.</p>
    </div>
  </section>
</template>
<script setup>
import { onMounted, ref, computed } from 'vue';
import { UsersApi } from '../services/api';
import { useToast } from '../stores/toast';

const me = ref(null);
const loading = ref(true);
const isEditing = ref(false);
const saving = ref(false);
const editForm = ref({
  username: '',
  email: '',
  currentPassword: '',
  newPassword: ''
});
const errors = ref({});

onMounted(async () => {
  try {
    const { data } = await UsersApi.me();
    me.value = data;
    resetForm();
  } catch (e) {
    console.error('Failed to load profile:', e);
  } finally {
    loading.value = false;
  }
});

function resetForm() {
  if (me.value) {
    editForm.value = {
      username: me.value.username || '',
      email: me.value.email || '',
      currentPassword: '',
      newPassword: ''
    };
  }
  errors.value = {};
}

function startEdit() {
  isEditing.value = true;
  resetForm();
}

function cancelEdit() {
  isEditing.value = false;
  resetForm();
}

function getUserRole() {
  if (!me.value || !me.value.roles || me.value.roles.length === 0) {
    return 'User';
  }
  return me.value.roles[0].name || 'User';
}

async function saveProfile() {
  if (saving.value) return;
  
  saving.value = true;
  errors.value = {};
  
  try {
    // Validate form
    if (!editForm.value.username.trim()) {
      errors.value.username = 'Username is required';
      return;
    }
    if (!editForm.value.email.trim()) {
      errors.value.email = 'Email is required';
      return;
    }
    if (!editForm.value.currentPassword) {
      errors.value.currentPassword = 'Current password is required to save changes';
      return;
    }
    
    // Prepare update data
    const updateData = {
      username: editForm.value.username.trim(),
      email: editForm.value.email.trim(),
      currentPassword: editForm.value.currentPassword
    };
    
    if (editForm.value.newPassword) {
      updateData.newPassword = editForm.value.newPassword;
    }
    
    // Call API to update profile
    const response = await fetch('/api/users/profile', {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      },
      body: JSON.stringify(updateData)
    });
    
    if (!response.ok) {
      const errorData = await response.json();
      throw new Error(errorData.message || 'Failed to update profile');
    }
    
    const updatedUser = await response.json();
    
    // Update local data
    me.value = updatedUser;
    isEditing.value = false;
    
    // Update stored user data
    localStorage.setItem('user', JSON.stringify(updatedUser));
    
    // Show success message
    const toast = useToast();
    toast.show('Profile updated successfully!', 'success');
    
  } catch (e) {
    console.error('Failed to save profile:', e);
    
    // Handle specific errors
    if (e.message.includes('username')) {
      errors.value.username = e.message;
    } else if (e.message.includes('email')) {
      errors.value.email = e.message;
    } else if (e.message.includes('password')) {
      errors.value.currentPassword = e.message;
    } else {
      const toast = useToast();
      toast.show('Failed to update profile. Please try again.', 'error');
    }
  } finally {
    saving.value = false;
  }
}
</script>
<style scoped>
.card{padding:12px;border:1px solid #e0e0e0;border-radius:8px;background:#fff}
.dim{color:#666;margin-top:8px}
</style>
