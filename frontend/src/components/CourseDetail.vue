<template>
  <section v-if="course">
    <h2>{{ course.title }}</h2>
    <p class="desc">{{ course.description || 'No description' }}</p>

    <h3>Videos</h3>
    <ul class="video-list">
      <li v-for="v in videos" :key="v.id" class="video-item">
        <router-link :to="`/videos/${v.id}`">▶ {{ v.title }}</router-link>
        <small v-if="v.durationSeconds"> ({{ v.durationSeconds }}s)</small>
      </li>
    </ul>
    <p v-if="!loading && videos.length===0">No videos for this course.</p>
  </section>
  <p v-else>Loading...</p>
</template>
<script setup>
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import { CoursesApi, VideosApi } from '../services/api';

const route = useRoute();
const id = Number(route.params.id);
const course = ref(null);
const videos = ref([]);
const loading = ref(false);

onMounted(async () => {
  loading.value = true;
  try {
    const [cRes, vRes] = await Promise.all([
      CoursesApi.get(id),
      VideosApi.byCourse(id)
    ]);
    course.value = cRes.data;
    videos.value = vRes.data || [];
  } finally { loading.value = false; }
});
</script>
<style scoped>
.desc{margin:8px 0 16px; color:#555}
.video-list{display:flex;flex-direction:column;gap:8px;padding:0}
.video-item{list-style:none;border-bottom:1px dashed #e0e0e0;padding:6px 0}
</style>
