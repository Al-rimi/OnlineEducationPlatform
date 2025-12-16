<template>
  <section>
    <h2>Courses</h2>
    <div class="toolbar">
      <input v-model="keyword" placeholder="Search courses..." @keyup.enter="search" />
      <button @click="search">Search</button>
    </div>
    <div class="actions">
      <router-link v-if="isAuthed" to="/courses/new" class="btn">+ Create Course</router-link>
    </div>
    <ul class="course-list">
      <li v-for="c in courses" :key="c.id" class="course-item">
        <router-link :to="`/courses/${c.id}`">
          <h3>{{ c.title }}</h3>
          <p class="desc">{{ c.description || 'No description' }}</p>
          <div class="meta">
            <span class="pill" v-if="Number(c.price) > 0">{{ Number(c.price).toFixed(2) }} {{ c.currency || 'USD' }}</span>
            <span class="pill gray">{{ c.visibility || 'PUBLIC' }}</span>
          </div>
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
const isAuthed = ref(!!localStorage.getItem('token'));

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
.actions{display:flex;justify-content:flex-end;margin-bottom:8px}
.btn{display:inline-block;background:#111827;color:#fff;padding:8px 12px;border-radius:8px;text-decoration:none}
.meta{display:flex;gap:8px;margin-top:6px}
.pill{background:#f97316;color:#fff;border-radius:999px;padding:2px 8px;font-size:12px}
.pill.gray{background:#e5e7eb;color:#111827}
</style>
