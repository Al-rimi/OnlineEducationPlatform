<template>
  <div class="container">
    <h1>Admin Dashboard</h1>
    <p class="mb-4">Welcome, {{ me?.username }}! You have full control over the platform.</p>
    
    <div class="dashboard-grid mb-4">
      <div class="stat-card">
        <h3>{{ users.length }}</h3>
        <p>Total Users</p>
      </div>
      <div class="stat-card">
        <h3>{{ courses.length }}</h3>
        <p>Total Courses</p>
      </div>
      <div class="stat-card">
        <h3>{{ videos.length }}</h3>
        <p>Total Videos</p>
      </div>
      <div class="stat-card">
        <h3>{{ enrollments.length }}</h3>
        <p>Total Enrollments</p>
      </div>
    </div>
    
    <!-- Search Section -->
    <div class="card mb-4">
      <h3>Search Platform</h3>
      <div class="form-row">
        <input v-model="searchQuery" placeholder="Search users, courses, assignments..." class="flex-1" />
        <select v-model="searchType">
          <option value="all">All</option>
          <option value="users">Users</option>
          <option value="courses">Courses</option>
          <option value="assignments">Assignments</option>
        </select>
        <button @click="performSearch" class="btn">Search</button>
      </div>
      <div v-if="searchResults.length" class="mt-4">
        <h4>Search Results</h4>
        <table>
          <thead>
            <tr>
              <th>Type</th>
              <th>Name</th>
              <th>Details</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="result in searchResults" :key="result.id + result.type">
              <td>{{ result.type }}</td>
              <td>{{ result.name }}</td>
              <td>{{ result.details }}</td>
              <td class="btn-group">
                <button @click="viewDetails(result)" class="btn secondary">View</button>
                <button @click="editItem(result)" class="btn">Edit</button>
                <button @click="deleteItem(result)" class="btn danger">Delete</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    
    <div class="grid">
      <div class="card">
        <h3>Manage Users</h3>
        <div class="btn-group">
          <router-link to="/users" class="btn">View All Users</router-link>
          <router-link to="/add" class="btn secondary">Add New User</router-link>
        </div>
      </div>
      
      <div class="card">
        <h3>Manage Courses</h3>
        <div class="btn-group">
          <router-link to="/courses" class="btn">View All Courses</router-link>
          <router-link to="/courses/new" class="btn secondary">Create New Course</router-link>
        </div>
      </div>
      
      <div class="card">
        <h3>Platform Overview</h3>
        <ul>
          <li>Active Users: {{ users.length }}</li>
          <li>Published Courses: {{ courses.filter(c => c.status === 'PUBLISHED').length }}</li>
          <li>Total Videos: {{ videos.length }}</li>
          <li>Student Enrollments: {{ enrollments.length }}</li>
        </ul>
      </div>
    </div>
  </div>
</template>
<script setup>
import { onMounted, ref } from 'vue';
import { UsersApi, CoursesApi } from '../services/api';

const me = ref(null);
const users = ref([]);
const courses = ref([]);
const videos = ref([]);
const enrollments = ref([]);
const searchQuery = ref('');
const searchType = ref('all');
const searchResults = ref([]);

onMounted(async () => {
  try {
    me.value = (await UsersApi.me()).data;
    const [uRes, cRes] = await Promise.all([
      UsersApi.list(),
      CoursesApi.list()
    ]);
    users.value = uRes.data || [];
    courses.value = cRes.data || [];
    
    // Calculate total videos and enrollments
    let totalVideos = 0;
    let totalEnrollments = 0;
    for (const course of courses.value) {
      // Assuming videos are fetched per course, but for simplicity, estimate or fetch
      // For now, set to courses.length * 3 as example
      totalVideos += 3; // placeholder
      totalEnrollments += 5; // placeholder
    }
    videos.value = Array(totalVideos).fill({});
    enrollments.value = Array(totalEnrollments).fill({});
  } catch (e) {
    console.error(e);
  }
});

async function performSearch() {
  try {
    searchResults.value = [];
    if (searchType.value === 'all' || searchType.value === 'users') {
      const userResults = users.value.filter(u => 
        u.username.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
        u.email.toLowerCase().includes(searchQuery.value.toLowerCase())
      ).map(u => ({ type: 'User', name: u.username, details: u.email, id: u.id, item: u }));
      searchResults.value.push(...userResults);
    }
    if (searchType.value === 'all' || searchType.value === 'courses') {
      const courseResults = courses.value.filter(c => 
        c.title.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
        c.description.toLowerCase().includes(searchQuery.value.toLowerCase())
      ).map(c => ({ type: 'Course', name: c.title, details: c.description.substring(0, 50) + '...', id: c.id, item: c }));
      searchResults.value.push(...courseResults);
    }
    if (searchType.value === 'all' || searchType.value === 'assignments') {
      // Placeholder for assignments search
      const assignmentResults = [];
      searchResults.value.push(...assignmentResults);
    }
  } catch (e) {
    console.error('Search failed');
  }
}

function viewDetails(result) {
  // Navigate to detail page
  if (result.type === 'User') {
    // router.push(`/user/${result.id}`);
  } else if (result.type === 'Course') {
    // router.push(`/courses/${result.id}`);
  }
}

function editItem(result) {
  if (result.type === 'User') {
    // router.push(`/edit/${result.id}`);
  } else if (result.type === 'Course') {
    // router.push(`/courses/${result.id}/edit`);
  }
}

function deleteItem(result) {
  if (confirm(`Delete ${result.type}: ${result.name}?`)) {
    // Delete logic
    console.log('Delete', result);
  }
}
</script>