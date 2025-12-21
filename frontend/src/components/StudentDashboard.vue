<template>
  <div class="container">
    <h1>Student Dashboard</h1>
    <p class="mb-4">Welcome, {{ me?.username }}! Here's your learning overview.</p>
    
    <div class="grid">
      <div class="card">
        <h3>My Learning</h3>
        <div class="stats">
          <div class="stat">
            <span class="number">{{ enrolled.length }}</span>
            <span class="label">Enrolled Courses</span>
          </div>
          <div class="stat">
            <span class="number">{{ pendingAssignments.length }}</span>
            <span class="label">Pending Assignments</span>
          </div>
          <div class="stat">
            <span class="number">{{ availableQuizzes.length }}</span>
            <span class="label">Available Quizzes</span>
          </div>
        </div>
        <div class="actions">
          <router-link to="/courses" class="btn">Browse Courses</router-link>
          <router-link to="/assignments" class="btn secondary">View Assignments</router-link>
        </div>
      </div>
      
      <div class="card">
        <h3>Quick Actions</h3>
        <ul class="action-list">
          <li><router-link to="/courses">📚 Browse & Enroll in Courses</router-link></li>
          <li><router-link to="/assignments">📝 Check Assignments</router-link></li>
          <li><router-link to="/quizzes">🧠 Take Quizzes</router-link></li>
          <li><router-link to="/profile">👤 Update Profile</router-link></li>
        </ul>
      </div>
      
      <div class="card" v-if="enrolled.length > 0">
        <h3>Recent Courses</h3>
        <ul class="course-list">
          <li v-for="c in enrolled.slice(0, 3)" :key="c?.id" v-if="c && c.id">
            <router-link :to="`/courses/${c.id}`">{{ c.title }}</router-link>
          </li>
        </ul>
        <router-link to="/courses" class="view-all">View all courses →</router-link>
      </div>
    </div>
  </div>
</template>
<script setup>
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import { CoursesApi, UsersApi, AssignmentsApi, QuizzesApi } from '../services/api';

const me = ref(null);
const courses = ref([]);
const enrolled = ref([]);
const progress = ref({});
const pendingAssignments = ref([]);
const availableQuizzes = ref([]);
const router = useRouter();

onMounted(async () => {
  try {
    me.value = (await UsersApi.me()).data;
    const { data } = await CoursesApi.list();
    courses.value = data.filter(c => c && c.id); // Filter out invalid courses
    const enrolledRes = await CoursesApi.enrolled();
    enrolled.value = enrolledRes.data.filter(c => c && c.id); // Filter out invalid enrolled courses
    // Load pending assignments and available quizzes
    await loadAssignmentsAndQuizzes();
  } catch (e) {
    console.error('Failed to load dashboard data:', e);
  }
});

async function loadAssignmentsAndQuizzes() {
  try {
    // Get assignments for enrolled courses
    const validEnrolledCourses = enrolled.value.filter(c => c && c.id);
    const assignmentPromises = validEnrolledCourses.map(c => AssignmentsApi.byCourse(c.id));
    const assignmentResults = await Promise.all(assignmentPromises);
    const allAssignments = assignmentResults.flatMap(res => res.data || []);
    // Filter pending assignments (not submitted yet)
    pendingAssignments.value = allAssignments.filter(a => a && !a.submitted);
    
    // Get quizzes for enrolled courses
    const quizPromises = validEnrolledCourses.map(c => QuizzesApi.byCourse(c.id));
    const quizResults = await Promise.all(quizPromises);
    const allQuizzes = quizResults.flatMap(res => res.data || []);
    // Filter available quizzes (not taken yet)
    availableQuizzes.value = allQuizzes.filter(q => q && !q.taken);
  } catch (e) {
    console.error('Failed to load assignments and quizzes:', e);
  }
}

function getProgress(courseId) {
  return progress.value[courseId] || 0;
}

function isEnrolled(courseId) {
  if (!courseId) return false;
  return enrolled.value.some(c => c && c.id === courseId);
}

async function enrollInCourse(courseId) {
  if (!courseId) return;
  try {
    await CoursesApi.enroll(courseId);
    const enrolledRes = await CoursesApi.enrolled();
    enrolled.value = enrolledRes.data.filter(c => c && c.id); // Filter out invalid enrolled courses
    await loadAssignmentsAndQuizzes();
  } catch (e) {
    console.error(e);
  }
}

function viewAssignments(courseId) {
  if (!courseId) return;
  router.push(`/courses/${courseId}/assignments`);
}

function viewQuizzes(courseId) {
  if (!courseId) return;
  router.push(`/courses/${courseId}/quizzes`);
}

function submitAssignment(assignmentId) {
  router.push(`/assignments/${assignmentId}/submit`);
}

function takeQuiz(quizId) {
  router.push(`/quizzes/${quizId}/take`);
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
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 20px;
}

.card {
  background: white;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.stats {
  display: flex;
  gap: 20px;
  margin-bottom: 20px;
}

.stat {
  text-align: center;
}

.number {
  display: block;
  font-size: 2em;
  font-weight: bold;
  color: #2563eb;
}

.label {
  font-size: 0.9em;
  color: #6b7280;
}

.actions {
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
}

.btn {
  padding: 10px 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  text-decoration: none;
  display: inline-block;
  text-align: center;
  font-weight: 500;
  transition: background-color 0.2s;
}

.btn.secondary {
  background: #6c757d;
  color: white;
}

.btn:hover {
  opacity: 0.9;
}

.action-list {
  list-style: none;
  padding: 0;
}

.action-list li {
  margin-bottom: 10px;
}

.action-list a {
  color: #2563eb;
  text-decoration: none;
  display: block;
  padding: 8px;
  border-radius: 4px;
  transition: background-color 0.2s;
}

.action-list a:hover {
  background-color: #f3f4f6;
}

.course-list {
  list-style: none;
  padding: 0;
}

.course-list li {
  padding: 8px 0;
  border-bottom: 1px solid #e5e7eb;
}

.course-list a {
  color: #2563eb;
  text-decoration: none;
}

.view-all {
  display: inline-block;
  margin-top: 10px;
  color: #2563eb;
  text-decoration: none;
  font-weight: 500;
}

.view-all:hover {
  text-decoration: underline;
}
</style>