<template>
  <section class="card">
    <h2>{{ isEdit ? 'Edit Course' : 'Create Course' }}</h2>
    <form @submit.prevent="submit">
      <div class="row">
        <label>Title</label>
        <input v-model="form.title" required />
      </div>
      <div class="row">
        <label>Description</label>
        <textarea v-model="form.description" rows="4" />
      </div>
      <div class="row">
        <label>Category Id</label>
        <input v-model.number="form.categoryId" type="number" min="1" />
      </div>
      <div class="row two">
        <div>
          <label>Price</label>
          <input v-model.number="form.price" type="number" min="0" step="0.01" />
        </div>
        <div>
          <label>Currency</label>
          <input v-model="form.currency" maxlength="3" placeholder="USD" />
        </div>
      </div>
      <div class="row two">
        <div>
          <label>Visibility</label>
          <select v-model="form.visibility">
            <option value="PUBLIC">PUBLIC</option>
            <option value="ENROLLED">ENROLLED</option>
            <option value="ROLE_BASED">ROLE_BASED</option>
          </select>
        </div>
        <div v-if="form.visibility==='ROLE_BASED'">
          <label>Visible Role</label>
          <select v-model="form.visibleRole">
            <option value="USER">STUDENT</option>
            <option value="TEACHER">TEACHER</option>
            <option value="ADMIN">ADMIN</option>
          </select>
        </div>
      </div>
      <div class="row">
        <label>Status</label>
        <select v-model="form.status">
          <option value="PUBLISHED">PUBLISHED</option>
          <option value="DRAFT">DRAFT</option>
          <option value="PENDING_REVIEW">PENDING_REVIEW</option>
          <option value="REJECTED">REJECTED</option>
        </select>
      </div>
      <div class="actions">
        <button type="submit">{{ isEdit ? 'Save' : 'Create' }}</button>
      </div>
    </form>
  </section>
</template>
<script setup>
import { reactive, onMounted, computed } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { CoursesApi } from '../services/api';
import { useToast } from '../stores/toast';

const route = useRoute();
const router = useRouter();
const toast = useToast();
const isEdit = computed(() => !!route.params.id);

const form = reactive({
  title: '',
  description: '',
  categoryId: null,
  price: 0,
  currency: 'USD',
  visibility: 'PUBLIC',
  visibleRole: null,
  status: 'PUBLISHED'
});

onMounted(async () => {
  if (isEdit.value) {
    const { data } = await CoursesApi.get(route.params.id);
    Object.assign(form, data || {});
  }
});

async function submit() {
  try {
    if (isEdit.value) {
      await CoursesApi.update(route.params.id, form);
      toast.success('Course updated');
    } else {
      await CoursesApi.create(form);
      toast.success('Course created');
    }
    router.push('/courses');
  } catch (e) {
    toast.error('Failed to save course');
  }
}
</script>
<style scoped>
.card{background:#fff;border:1px solid #e0e0e0;border-radius:10px;padding:16px;max-width:720px}
.row{display:flex;flex-direction:column;gap:6px;margin-bottom:12px}
.row.two{flex-direction:row;gap:12px}
.row.two>div{flex:1}
label{font-weight:600}
input,textarea,select{border:1px solid #ccc;border-radius:8px;padding:8px}
.actions{display:flex;justify-content:flex-end}
button{background:#111827;color:#fff;border:none;border-radius:8px;padding:8px 14px}
button:hover{opacity:.9}
</style>