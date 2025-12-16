<template>
  <div class="container">
    <h1>{{ courseId ? 'Course Assignments' : 'Assignments' }}</h1>
    <div class="row mb-4" v-if="!courseId">
      <router-link to="/assignments/new" class="btn">Create New Assignment</router-link>
    </div>
    <div class="card">
      <table v-if="assignments.length">
        <thead>
          <tr>
            <th>Title</th>
            <th v-if="!courseId">Course</th>
            <th>Due Date</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="assignment in assignments" :key="assignment.id">
            <td>{{ assignment.title }}</td>
            <td v-if="!courseId">{{ getCourseTitle(assignment.courseId) }}</td>
            <td>{{ assignment.dueDate }}</td>
            <td class="btn-group">
              <router-link v-if="!courseId" :to="`/assignments/${assignment.id}/edit`" class="btn secondary">Edit</router-link>
              <button v-if="!courseId" @click="deleteAssignment(assignment.id)" class="btn danger">Delete</button>
              <router-link :to="`/assignments/${assignment.id}/submit`" class="btn secondary">Submit</router-link>
            </td>
          </tr>
        </tbody>
      </table>
      <p v-else>No assignments found.</p>
    </div>
  </div>
</template>
<script setup>
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import { AssignmentsApi, CoursesApi } from '../services/api';
import { useToast } from '../stores/toast';

const props = defineProps({
  courseId: {
    type: Number,
    default: null
  }
});

const route = useRoute();
const assignments = ref([]);
const courses = ref([]);
const toast = useToast();

onMounted(async () => {
  try {
    if (props.courseId || route.params.courseId) {
      const courseId = props.courseId || route.params.courseId;
      const { data } = await AssignmentsApi.byCourse(courseId);
      assignments.value = data;
    } else {
      const { data } = await AssignmentsApi.list();
      assignments.value = data;
      const coursesRes = await CoursesApi.list();
      courses.value = coursesRes.data;
    }
  } catch (e) {
    console.error(e);
    toast.error('Failed to load assignments');
  }
});

function getCourseTitle(courseId) {
  const course = courses.value.find(c => c.id === courseId);
  return course ? course.title : 'Unknown';
}

async function deleteAssignment(id) {
  try {
    await AssignmentsApi.delete(id);
    assignments.value = assignments.value.filter(a => a.id !== id);
    toast.success('Assignment deleted');
  } catch (e) {
    console.error(e);
    toast.error('Failed to delete assignment');
  }
}
</script>