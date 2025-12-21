<template>
  <section>
    <h2>Course Catalog</h2>
    <p class="subtitle">Browse and enroll in courses to start learning</p>
    
    <div class="toolbar">
      <input v-model="keyword" placeholder="Search courses..." @keyup.enter="search" />
      <button @click="search">Search</button>
    </div>
    
    <div class="actions">
      <router-link v-if="canCreate" to="/courses/new" class="btn primary">+ Create Course</router-link>
    </div>
    
    <div v-if="loading" class="loading">Loading courses...</div>
    
    <div v-else-if="courses.length === 0" class="no-courses">
      <p>No courses found.</p>
      <router-link v-if="canCreate" to="/courses/new" class="btn primary">Create the first course</router-link>
    </div>
    
    <div v-else class="course-grid">
      <div v-for="course in courses" :key="course.id" class="course-card" v-if="course && course.id">
        <div class="course-header">
          <h3>{{ course.title }}</h3>
          <div class="course-meta">
            <span class="status" :class="'status-' + (course.status || 'published').toLowerCase()">
              {{ course.status || 'Published' }}
            </span>
            <span v-if="Number(course.price) > 0" class="price">
              {{ Number(course.price).toFixed(2) }} {{ course.currency || 'USD' }}
            </span>
            <span v-else class="price free">Free</span>
          </div>
        </div>
        
        <p class="course-description">{{ course.description || 'No description available' }}</p>
        
        <div class="course-actions">
          <router-link :to="`/courses/${course.id}`" class="btn secondary">View Details</router-link>
          
          <button 
            v-if="!isEnrolled(course.id) && canEnroll" 
            @click="enrollInCourse(course.id)"
            :disabled="enrolling === course.id"
            class="btn primary"
          >
            {{ enrolling === course.id ? 'Enrolling...' : 'Enroll Now' }}
          </button>
          
          <span v-else-if="isEnrolled(course.id)" class="enrolled-badge">
            ✓ Enrolled
          </span>
        </div>
      </div>
    </div>
  </section>
</template>
<script setup>
import { onMounted, ref, computed } from 'vue';
import { CoursesApi, UsersApi } from '../services/api';
import { useToast } from '../stores/toast';

const courses = ref([]);
const enrolledCourses = ref([]);
const keyword = ref('');
const loading = ref(false);
const enrolling = ref(null);
const isAuthed = ref(!!localStorage.getItem('token'));
const me = ref(JSON.parse(localStorage.getItem('user') || '{}'));
const canCreate = computed(() => isAuthed.value && (me.value.roles?.includes('ADMIN') || me.value.roles?.includes('TEACHER')));
const canEnroll = computed(() => isAuthed.value && me.value.roles?.includes('STUDENT'));

async function loadAll() {
  loading.value = true;
  try {
    const { data } = await CoursesApi.list();
    courses.value = data.filter(c => c && c.id) || [];
    
    // Load enrolled courses if user is authenticated
    if (isAuthed.value) {
      try {
        const enrolledRes = await CoursesApi.enrolled();
        enrolledCourses.value = enrolledRes.data.filter(c => c && c.id) || [];
      } catch (e) {
        console.error('Failed to load enrolled courses:', e);
      }
    }
  } finally {
    loading.value = false;
  }
}

async function search() {
  loading.value = true;
  try {
    if (!keyword.value) return loadAll();
    const { data } = await CoursesApi.search(keyword.value);
    courses.value = data.filter(c => c && c.id) || [];
  } finally {
    loading.value = false;
  }
}

function isEnrolled(courseId) {
  if (!courseId) return false;
  return enrolledCourses.value.some(c => c && c.id === courseId);
}

async function enrollInCourse(courseId) {
  if (!courseId || enrolling.value) return;
  
  enrolling.value = courseId;
  try {
    await CoursesApi.enroll(courseId);
    // Refresh enrolled courses
    const enrolledRes = await CoursesApi.enrolled();
    enrolledCourses.value = enrolledRes.data.filter(c => c && c.id) || [];
    
    // Show success message
    const toast = useToast();
    toast.show('Successfully enrolled in course!', 'success');
  } catch (e) {
    console.error('Failed to enroll in course:', e);
    const toast = useToast();
    toast.show('Failed to enroll in course. Please try again.', 'error');
  } finally {
    enrolling.value = null;
  }
}

onMounted(loadAll);
</script>
<style scoped>
section {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.subtitle {
  color: #6b7280;
  margin-bottom: 20px;
}

.toolbar {
  display: flex;
  gap: 8px;
  margin-bottom: 12px;
}

.toolbar input {
  flex: 1;
  padding: 8px 12px;
  border: 1px solid #d1d5db;
  border-radius: 6px;
  font-size: 14px;
}

.toolbar button {
  padding: 8px 16px;
  background: #2563eb;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
}

.actions {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 20px;
}

.btn {
  display: inline-block;
  padding: 8px 16px;
  border-radius: 6px;
  text-decoration: none;
  font-weight: 500;
  text-align: center;
  transition: background-color 0.2s;
}

.btn.primary {
  background: #2563eb;
  color: white;
}

.btn.secondary {
  background: #6b7280;
  color: white;
}

.btn:hover {
  opacity: 0.9;
}

.btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.loading, .no-courses {
  text-align: center;
  padding: 40px;
  color: #6b7280;
}

.course-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(320px, 1fr));
  gap: 20px;
}

.course-card {
  background: white;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  transition: box-shadow 0.2s;
}

.course-card:hover {
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.course-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 12px;
}

.course-header h3 {
  margin: 0;
  font-size: 1.2em;
  color: #111827;
}

.course-meta {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  gap: 4px;
}

.status {
  padding: 2px 8px;
  border-radius: 12px;
  font-size: 0.75em;
  font-weight: 500;
  text-transform: uppercase;
}

.status-published {
  background: #dcfce7;
  color: #166534;
}

.status-draft {
  background: #fef3c7;
  color: #92400e;
}

.status-pending_review {
  background: #dbeafe;
  color: #1e40af;
}

.price {
  font-weight: 600;
  color: #111827;
}

.price.free {
  color: #059669;
}

.course-description {
  color: #6b7280;
  margin-bottom: 16px;
  line-height: 1.5;
}

.course-actions {
  display: flex;
  gap: 8px;
  align-items: center;
}

.enrolled-badge {
  color: #059669;
  font-weight: 500;
  font-size: 0.9em;
}
</style>
