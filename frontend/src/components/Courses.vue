<template>
  <section>
    <h2>Courses</h2>
    <div class="toolbar">
      <input v-model="keyword" placeholder="Search courses..." @keyup.enter="search" />
      <button @click="search">Search</button>
    </div>
    <ul class="course-list">
      <li v-for="c in courses" :key="c.id" class="course-item">
        <router-link :to="`/courses/${c.id}`">
          <h3>{{ c.title }}</h3>
          <p class="desc">{{ c.description || 'No description' }}</p>
        </router-link>
      </li>
    </ul>
    <p v-if="!loading && courses.length===0">No courses found.</p>
  </section>
</template>
<script setup>
import { onMounted, ref } from 'vue';
import { CoursesApi } from '../services/api';

const courses = ref([]);
const keyword = ref('');
const loading = ref(false);

async function loadAll() {
  loading.value = true;
  try {
    const { data } = await CoursesApi.list();
    courses.value = data || [];
  } finally { loading.value = false; }
}

async function search() {
  loading.value = true;
  try {
    if (!keyword.value) return loadAll();
    const { data } = await CoursesApi.search(keyword.value);
    courses.value = data || [];
  } finally { loading.value = false; }
}

onMounted(loadAll);
</script>
<style scoped>
.toolbar{display:flex;gap:8px;margin-bottom:12px}
.course-list{display:grid;grid-template-columns:repeat(auto-fit,minmax(260px,1fr));gap:12px;padding:0}
.course-item{list-style:none;border:1px solid #e0e0e0;border-radius:8px;padding:12px;background:#fff}
.desc{color:#555;margin-top:6px}
</style>
