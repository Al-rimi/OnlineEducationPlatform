<template>
  <section v-if="video">
    <h2>{{ video.title }}</h2>
    <video :src="video.videoUrl" controls style="width:100%;max-height:60vh;background:#000"></video>
    <p class="meta"><strong>Duration:</strong> {{ video.durationSeconds || '—' }}s</p>
    <router-link :to="`/courses/${video.courseId}`">← Back to Course</router-link>
  </section>
  <p v-else>Loading...</p>
</template>
<script setup>
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import { VideosApi } from '../services/api';

const route = useRoute();
const id = Number(route.params.id);
const video = ref(null);

onMounted(async () => {
  const { data } = await VideosApi.get(id);
  video.value = data;
});
</script>
<style scoped>
.meta{color:#555;margin:8px 0 16px}
</style>
