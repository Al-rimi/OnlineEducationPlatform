<template>
  <div class="container">
    <h1>{{ courseId ? 'Course Quizzes' : 'Quizzes' }}</h1>
    <div class="row mb-4" v-if="!courseId && canManage">
      <router-link to="/quizzes/new" class="btn">Create New Quiz</router-link>
    </div>
    <div class="card">
      <table v-if="quizzes.length">
        <thead>
          <tr>
            <th>Title</th>
            <th v-if="!courseId">Course</th>
            <th>Created At</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="quiz in quizzes" :key="quiz.id">
            <td>{{ quiz.title }}</td>
            <td v-if="!courseId">{{ getCourseTitle(quiz.courseId) }}</td>
            <td>{{ quiz.createdAt }}</td>
            <td class="btn-group">
              <router-link v-if="!courseId && canManage" :to="`/quizzes/${quiz.id}/edit`" class="btn secondary">Edit</router-link>
              <button v-if="!courseId && canManage" @click="deleteQuiz(quiz.id)" class="btn danger">Delete</button>
              <router-link :to="`/quizzes/${quiz.id}/take`" class="btn secondary">Take Quiz</router-link>
            </td>
          </tr>
        </tbody>
      </table>
      <p v-else>No quizzes found.</p>
    </div>
  </div>
</template>
<script setup>
import { onMounted, ref, computed } from 'vue';
import { useRoute } from 'vue-router';
import { QuizzesApi, CoursesApi } from '../services/api';
import { useToast } from '../stores/toast';

const props = defineProps({
  courseId: {
    type: Number,
    default: null
  }
});

const route = useRoute();
const quizzes = ref([]);
const courses = ref([]);
const toast = useToast();
const me = ref(JSON.parse(localStorage.getItem('user') || '{}'));
const canManage = computed(() => me.value.roles?.includes('ADMIN') || me.value.roles?.includes('TEACHER'));

onMounted(async () => {
  try {
    if (props.courseId || route.params.courseId) {
      const courseId = props.courseId || route.params.courseId;
      const { data } = await QuizzesApi.byCourse(courseId);
      quizzes.value = data;
    } else {
      const { data } = await QuizzesApi.list();
      quizzes.value = data;
      const coursesRes = await CoursesApi.list();
      courses.value = coursesRes.data;
    }
  } catch (e) {
    console.error(e);
    toast.error('Failed to load quizzes');
  }
});

function getCourseTitle(courseId) {
  const course = courses.value.find(c => c.id === courseId);
  return course ? course.title : 'Unknown';
}

async function deleteQuiz(id) {
  try {
    await QuizzesApi.delete(id);
    quizzes.value = quizzes.value.filter(q => q.id !== id);
    toast.success('Quiz deleted');
  } catch (e) {
    console.error(e);
    toast.error('Failed to delete quiz');
  }
}
</script>