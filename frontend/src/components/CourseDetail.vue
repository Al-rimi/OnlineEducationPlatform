<template>
  <section v-if="course">
    <h2>{{ course.title }}</h2>
    <p class="desc">{{ course.description || 'No description' }}</p>
    <div class="meta">
      <span class="pill" v-if="Number(course.price) > 0">{{ Number(course.price).toFixed(2) }} {{ course.currency || 'USD' }}</span>
      <span class="pill gray">{{ course.visibility || 'PUBLIC' }}</span>
    </div>

    <div class="actions" v-if="isAuthed">
      <button v-if="Number(course.price) > 0" @click="purchase">Purchase</button>
      <button v-else-if="course.visibility==='ENROLLED'" @click="enroll">Enroll</button>
    </div>

    <h3>Videos</h3>
    <ul class="video-list">
      <li v-for="v in videos" :key="v.id" class="video-item">
        <router-link :to="`/videos/${v.id}`">▶ {{ v.title }}</router-link>
        <small v-if="v.durationSeconds"> ({{ v.durationSeconds }}s)</small>
      </li>
    </ul>
    <p v-if="!loading && videos.length===0">No videos for this course.</p>
    <div v-if="canManage" class="add-video">
      <h4>Add Video</h4>
      <form @submit.prevent="addVideo">
        <input v-model="vid.title" placeholder="Title" required />
        <input v-model="vid.videoUrl" placeholder="Video URL" required />
        <input v-model.number="vid.durationSeconds" type="number" placeholder="Duration (s)" />
        <button type="submit">Add</button>
      </form>
    </div>
  </section>
  <p v-else-if="!loading">Course not found or access denied.</p>
  <p v-else>Loading...</p>
</template>
<script setup>
import { onMounted, ref, computed } from 'vue';
import { useRoute } from 'vue-router';
import { CoursesApi, VideosApi, UsersApi } from '../services/api';
import { useToast } from '../stores/toast';

const route = useRoute();
const id = Number(route.params.id);
const course = ref(null);
const videos = ref([]);
const loading = ref(false);
const me = ref(null);
const isAuthed = ref(!!localStorage.getItem('token'));
const toast = useToast();
const canManage = computed(() => (me.value?.roles || []).some(r => r === 'ADMIN' || r === 'TEACHER'));
const vid = ref({ title: '', videoUrl: '', durationSeconds: null });

onMounted(async () => {
  loading.value = true;
  try {
    const promises = [CoursesApi.get(id), VideosApi.byCourse(id)];
    if (isAuthed.value) promises.push(UsersApi.me());
    const results = await Promise.all(promises);
    const cRes = results[0];
    const vRes = results[1];
    course.value = cRes.data;
    videos.value = vRes.data || [];
    if (results[2]) me.value = results[2].data;
  } catch (e) {
    console.error('Failed to load course:', e);
  } finally { loading.value = false; }
});

async function enroll() {
  try {
    await CoursesApi.enroll(id);
    toast.success('Enrolled successfully');
    const { data } = await VideosApi.byCourse(id);
    videos.value = data || [];
  } catch(e) { toast.error('Enroll failed'); }
}

async function purchase() {
  try {
    await CoursesApi.purchase(id);
    toast.success('Purchase successful');
    const { data } = await VideosApi.byCourse(id);
    videos.value = data || [];
  } catch(e) { toast.error('Purchase failed'); }
}

async function addVideo() {
  try {
    await VideosApi.create({ courseId: id, ...vid.value });
    vid.value = { title: '', videoUrl: '', durationSeconds: null };
    const { data } = await VideosApi.byCourse(id);
    videos.value = data || [];
    toast.success('Video added');
  } catch(e) { toast.error('Failed to add video'); }
}
</script>
<style scoped>
.desc{margin:8px 0 16px; color:#555}
.video-list{display:flex;flex-direction:column;gap:8px;padding:0}
.video-item{list-style:none;border-bottom:1px dashed #e0e0e0;padding:6px 0}
.meta{display:flex;gap:8px;margin:6px 0 12px}
.pill{background:#f97316;color:#fff;border-radius:999px;padding:2px 8px;font-size:12px}
.pill.gray{background:#e5e7eb;color:#111827}
.actions{display:flex;gap:8px;margin:8px 0}
button{background:#111827;color:#fff;border:none;border-radius:8px;padding:8px 12px}
.add-video{margin-top:16px;padding-top:12px;border-top:1px solid #eee}
.add-video form{display:flex;gap:8px}
.add-video input{border:1px solid #ccc;border-radius:8px;padding:6px}
</style>
