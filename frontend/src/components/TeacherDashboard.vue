<template>
  <div class="container">
    <h1>Teacher Dashboard</h1>
    <p class="mb-4">Welcome, {{ me?.username }}! Manage your courses, videos, and assignments.</p>
    
    <div class="row mb-4">
      <router-link to="/courses/new" class="btn">Create New Course</router-link>
      <router-link to="/assignments" class="btn secondary">Manage Assignments</router-link>
      <router-link to="/quizzes" class="btn secondary">Manage Quizzes</router-link>
    </div>
    
    <div class="card">
      <h3>Your Courses</h3>
      <div v-if="courses.length" class="grid">
        <div v-for="c in courses" :key="c.id" class="card">
          <h4>{{ c.title }}</h4>
          <p>Status: <span class="status-badge" :class="'status-' + c.status.toLowerCase()">{{ c.status }}</span></p>
          <div class="btn-group">
            <router-link :to="`/courses/${c.id}`" class="btn secondary">View</router-link>
            <router-link :to="`/courses/${c.id}/edit`" class="btn">Edit</router-link>
          </div>
        </div>
      </div>
      <p v-else>No courses yet. <router-link to="/courses/new">Create one</router-link></p>
    </div>
  </div>
</template>
<script setup>
import { onMounted, ref } from 'vue';
import { CoursesApi, UsersApi } from '../services/api';

const me = ref(null);
const courses = ref([]);

onMounted(async () => {
  try {
    me.value = (await UsersApi.me()).data;
    // Assuming we can get courses by teacher, but for now, get all and filter
    const { data } = await CoursesApi.list();
    courses.value = data.filter(c => c.created_by === me.value.id);
  } catch (e) {
    console.error(e);
  }
});
</script>