<template>
  <div class="container">
    <h1>{{ isEdit ? 'Edit Assignment' : 'Create New Assignment' }}</h1>
    <form @submit.prevent="save" class="card">
      <label>
        Title
        <input v-model="assignment.title" required />
      </label>
      <label>
        Description
        <textarea v-model="assignment.description" rows="4"></textarea>
      </label>
      <div class="form-row">
        <label>
          Course
          <select v-model="assignment.course_id" required>
            <option value="">Select Course</option>
            <option v-for="course in courses" :key="course.id" :value="course.id">{{ course.title }}</option>
          </select>
        </label>
        <label>
          Due Date
          <input type="date" v-model="assignment.due_date" required />
        </label>
      </div>
      <div class="btn-group">
        <button type="submit">{{ isEdit ? 'Update' : 'Create' }} Assignment</button>
        <router-link to="/assignments" class="btn secondary">Cancel</router-link>
      </div>
    </form>
  </div>
</template>
<script setup>
import { onMounted, ref } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useToast } from '../stores/toast';

const router = useRouter();
const route = useRoute();
const toast = useToast();
const isEdit = ref(false);
const assignment = ref({
  title: '',
  description: '',
  course_id: '',
  due_date: ''
});
const courses = ref([]);

onMounted(async () => {
  // Placeholder: fetch courses
  courses.value = [
    { id: 1, title: 'JavaScript Fundamentals' },
    { id: 2, title: 'Python for Beginners' }
  ];
  
  if (route.params.id) {
    isEdit.value = true;
    // Placeholder: fetch assignment
    assignment.value = {
      id: route.params.id,
      title: 'Build a Calculator',
      description: 'Create a functional calculator using JavaScript',
      course_id: 1,
      due_date: '2025-12-15'
    };
  }
});

async function save() {
  try {
    // Placeholder: save assignment
    toast.success(isEdit.value ? 'Assignment updated' : 'Assignment created');
    router.push('/assignments');
  } catch (e) {
    toast.error('Failed to save assignment');
  }
}
</script>