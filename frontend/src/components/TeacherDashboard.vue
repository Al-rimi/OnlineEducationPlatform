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
        <div v-for="c in courses" :key="c.id" class="card course-card">
          <h4>{{ c.title }}</h4>
          <p>Status: <span class="status-badge" :class="'status-' + c.status.toLowerCase()">{{ c.status }}</span></p>
          <p>Enrolled Students: {{ getEnrolledCount(c.id) }}</p>
          <div class="btn-group">
            <router-link :to="`/courses/${c.id}`" class="btn secondary">View</router-link>
            <router-link :to="`/courses/${c.id}/edit`" class="btn">Edit</router-link>
            <button @click="toggleStudents(c.id)" class="btn secondary">
              {{ showStudents[c.id] ? 'Hide' : 'Show' }} Students
            </button>
          </div>
          <div v-if="showStudents[c.id]" class="students-list">
            <h5>Enrolled Students:</h5>
            <ul v-if="enrolledStudents[c.id] && enrolledStudents[c.id].length">
              <li v-for="student in enrolledStudents[c.id]" :key="student.id">
                {{ student.username }} ({{ student.email }})
              </li>
            </ul>
            <p v-else>No students enrolled yet.</p>
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
const enrolledStudents = ref({});
const showStudents = ref({});

onMounted(async () => {
  try {
    me.value = (await UsersApi.me()).data;
    // Get only courses created by this teacher
    const { data } = await CoursesApi.myCourses();
    courses.value = data;
    // Load enrolled students for each course
    await loadEnrolledStudents();
  } catch (e) {
    console.error(e);
  }
});

async function loadEnrolledStudents() {
  for (const course of courses.value) {
    try {
      const { data } = await UsersApi.enrolledInCourse(course.id);
      enrolledStudents.value[course.id] = data;
    } catch (e) {
      console.error(`Failed to load students for course ${course.id}:`, e);
      enrolledStudents.value[course.id] = [];
    }
  }
}

function getEnrolledCount(courseId) {
  return enrolledStudents.value[courseId]?.length || 0;
}

function toggleStudents(courseId) {
  showStudents.value[courseId] = !showStudents.value[courseId];
  if (showStudents.value[courseId] && !enrolledStudents.value[courseId]) {
    // Load students if not already loaded
    UsersApi.enrolledInCourse(courseId).then(({ data }) => {
      enrolledStudents.value[courseId] = data;
    }).catch(e => {
      console.error(e);
      enrolledStudents.value[courseId] = [];
    });
  }
}
</script>
<style scoped>
.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(350px, 1fr));
  gap: 20px;
}

.card {
  background: white;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.course-card {
  border: 1px solid #e0e0e0;
}

.status-badge {
  padding: 4px 8px;
  border-radius: 4px;
  font-size: 0.8em;
  font-weight: bold;
}

.status-published {
  background: #d4edda;
  color: #155724;
}

.status-draft {
  background: #fff3cd;
  color: #856404;
}

.status-pending_review {
  background: #cce5ff;
  color: #004085;
}

.btn-group {
  display: flex;
  gap: 10px;
  margin-top: 15px;
  flex-wrap: wrap;
}

.btn {
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  text-decoration: none;
  display: inline-block;
  text-align: center;
  font-size: 0.9em;
}

.btn.secondary {
  background: #6c757d;
  color: white;
}

.btn:hover {
  opacity: 0.9;
}

.students-list {
  margin-top: 15px;
  padding: 10px;
  background: #f8f9fa;
  border-radius: 4px;
}

.students-list h5 {
  margin: 0 0 10px 0;
  color: #495057;
}

.students-list ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.students-list li {
  padding: 5px 0;
  border-bottom: 1px solid #dee2e6;
  font-size: 0.9em;
}

.students-list li:last-child {
  border-bottom: none;
}
</style>