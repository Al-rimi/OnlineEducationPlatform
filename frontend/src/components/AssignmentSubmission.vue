<template>
  <div class="container">
    <h1>Submit Assignment</h1>
    <div v-if="assignment" class="card">
      <h2>{{ assignment.title }}</h2>
      <p>{{ assignment.description }}</p>
      <p>Due Date: {{ assignment.dueDate }}</p>
      
      <form @submit.prevent="submit">
        <div class="form-group">
          <label for="content">Submission Content:</label>
          <textarea id="content" v-model="submission.content" required></textarea>
        </div>
        <div class="form-group">
          <label for="file">Upload File (optional):</label>
          <input type="file" id="file" @change="handleFileChange" />
        </div>
        <button type="submit" class="btn" :disabled="loading">
          {{ loading ? 'Submitting...' : 'Submit Assignment' }}
        </button>
      </form>
    </div>
    <p v-else>Loading assignment...</p>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { AssignmentsApi, AssignmentSubmissionsApi } from '../services/api';
import { useToast } from '../stores/toast';

const route = useRoute();
const router = useRouter();
const toast = useToast();

const assignment = ref(null);
const submission = ref({ content: '', file: null });
const loading = ref(false);

onMounted(async () => {
  try {
    const { data } = await AssignmentsApi.get(route.params.id);
    assignment.value = data;
  } catch (e) {
    console.error(e);
    toast.error('Failed to load assignment');
  }
});

function handleFileChange(event) {
  submission.value.file = event.target.files[0];
}

async function submit() {
  loading.value = true;
  try {
    const formData = new FormData();
    formData.append('assignmentId', route.params.id);
    formData.append('content', submission.value.content);
    if (submission.value.file) {
      formData.append('file', submission.value.file);
    }
    await AssignmentSubmissionsApi.submit(formData);
    toast.success('Assignment submitted successfully');
    router.push('/student');
  } catch (e) {
    console.error(e);
    toast.error('Failed to submit assignment');
  } finally {
    loading.value = false;
  }
}
</script>

<style scoped>
.container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

.card {
  background: white;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  min-height: 100px;
}

.btn {
  padding: 10px 20px;
  background: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}
</style>