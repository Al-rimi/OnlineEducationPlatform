<template>
  <div class="container">
    <h1>Student Dashboard</h1>
    <p class="mb-4">Welcome, {{ me?.username }}! Explore courses, complete assignments, and take quizzes.</p>
    
    <div class="grid">
      <div class="card">
        <h3>My Enrolled Courses</h3>
        <div v-if="enrolled.length" class="grid">
          <div v-for="c in enrolled" :key="c.id" class="card">
            <h4>{{ c.title }}</h4>
            <p>Progress: {{ getProgress(c.id) }}%</p>
            <div class="btn-group">
              <router-link :to="`/courses/${c.id}`" class="btn">View Course</router-link>
              <button @click="viewAssignments(c.id)" class="btn secondary">Assignments</button>
              <button @click="viewQuizzes(c.id)" class="btn secondary">Quizzes</button>
            </div>
          </div>
        </div>
        <p v-else>You haven't enrolled in any courses yet.</p>
      </div>
      
      <div class="card">
        <h3>Available Courses</h3>
        <div v-if="courses.length" class="grid">
          <div v-for="c in courses" :key="c.id" class="card" v-if="!isEnrolled(c.id)">
            <h4>{{ c.title }}</h4>
            <p>{{ c.description?.substring(0, 100) }}...</p>
            <button @click="enrollInCourse(c.id)" class="btn">Enroll</button>
          </div>
        </div>
        <p v-else>Loading courses...</p>
      </div>
      
      <div class="card">
        <h3>Pending Assignments</h3>
        <ul v-if="pendingAssignments.length">
          <li v-for="a in pendingAssignments" :key="a.id">
            {{ a.title }} - Due: {{ a.dueDate }}
            <button @click="submitAssignment(a.id)" class="btn secondary">Submit</button>
          </li>
        </ul>
        <p v-else>No pending assignments.</p>
      </div>
      
      <div class="card">
        <h3>Available Quizzes</h3>
        <ul v-if="availableQuizzes.length">
          <li v-for="q in availableQuizzes" :key="q.id">
            {{ q.title }}
            <button @click="takeQuiz(q.id)" class="btn secondary">Take Quiz</button>
          </li>
        </ul>
        <p v-else>No quizzes available.</p>
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
    courses.value = data;
    const enrolledRes = await CoursesApi.enrolled();
    enrolled.value = enrolledRes.data;
    // Load pending assignments and available quizzes
    await loadAssignmentsAndQuizzes();
  } catch (e) {
    console.error(e);
  }
});

async function loadAssignmentsAndQuizzes() {
  try {
    // Get assignments for enrolled courses
    const assignmentPromises = enrolled.value.map(c => AssignmentsApi.byCourse(c.id));
    const assignmentResults = await Promise.all(assignmentPromises);
    const allAssignments = assignmentResults.flatMap(res => res.data);
    // Filter pending assignments (not submitted yet)
    pendingAssignments.value = allAssignments.filter(a => !a.submitted);
    
    // Get quizzes for enrolled courses
    const quizPromises = enrolled.value.map(c => QuizzesApi.byCourse(c.id));
    const quizResults = await Promise.all(quizPromises);
    const allQuizzes = quizResults.flatMap(res => res.data);
    // Filter available quizzes (not taken yet)
    availableQuizzes.value = allQuizzes.filter(q => !q.taken);
  } catch (e) {
    console.error(e);
  }
}

function getProgress(courseId) {
  return progress.value[courseId] || 0;
}

function isEnrolled(courseId) {
  return enrolled.value.some(c => c.id === courseId);
}

async function enrollInCourse(courseId) {
  try {
    await CoursesApi.enroll(courseId);
    const enrolledRes = await CoursesApi.enrolled();
    enrolled.value = enrolledRes.data;
    await loadAssignmentsAndQuizzes();
  } catch (e) {
    console.error(e);
  }
}

function viewAssignments(courseId) {
  router.push(`/courses/${courseId}/assignments`);
}

function viewQuizzes(courseId) {
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

.btn-group {
  display: flex;
  gap: 10px;
  margin-top: 10px;
}

.btn {
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  text-decoration: none;
  display: inline-block;
  text-align: center;
}

.btn.secondary {
  background: #6c757d;
  color: white;
}

.btn:hover {
  opacity: 0.9;
}

ul {
  list-style: none;
  padding: 0;
}

li {
  padding: 10px;
  border-bottom: 1px solid #eee;
}
</style>