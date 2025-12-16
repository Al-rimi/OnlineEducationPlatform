<template>
  <div class="container">
    <h1>{{ isEdit ? 'Edit Quiz' : 'Create New Quiz' }}</h1>
    <form @submit.prevent="save" class="card">
      <label>
        Title
        <input v-model="quiz.title" required />
      </label>
      <label>
        Course
        <select v-model="quiz.courseId" required>
          <option value="">Select Course</option>
          <option v-for="course in courses" :key="course.id" :value="course.id">{{ course.title }}</option>
        </select>
      </label>
      <div class="btn-group">
        <button type="submit">{{ isEdit ? 'Update' : 'Create' }} Quiz</button>
        <router-link to="/quizzes" class="btn secondary">Cancel</router-link>
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
const quiz = ref({
  title: '',
  courseId: ''
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
    // Placeholder: fetch quiz
    quiz.value = {
      id: route.params.id,
      title: 'JavaScript Basics Quiz',
      courseId: 1
    };
  }
});

async function save() {
  try {
    // Placeholder: save quiz
    toast.success(isEdit.value ? 'Quiz updated' : 'Quiz created');
    router.push('/quizzes');
  } catch (e) {
    toast.error('Failed to save quiz');
  }
}
</script>