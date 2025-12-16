import axios from 'axios';
import { useToast } from '../stores/toast';

const api = axios.create({
  baseURL: '/',
  headers: { 'Content-Type': 'application/json' }
});

api.interceptors.request.use(cfg => {
  const token = localStorage.getItem('token');
  if (token) cfg.headers.Authorization = 'Bearer ' + token;
  return cfg;
});

api.interceptors.response.use(r => r, err => {
  const status = err.response?.status;
  if (status === 401 || status === 403) {
    const toast = useToast();
    toast.error('Session expired or unauthorized. Please login.');
    // optional: redirect
    if (typeof window !== 'undefined') {
      const current = window.location.pathname + window.location.search;
      window.location.href = '/login?redirect=' + encodeURIComponent(current);
    }
  }
  return Promise.reject(err);
});

export default api;

// Convenience API functions
export const UsersApi = {
  login: (payload) => api.post('/api/users/login', payload),
  register: (payload) => api.post('/api/users/register', payload),
  me: () => api.get('/api/users/me')
};

export const CategoriesApi = {
  list: () => api.get('/api/categories')
};

export const CoursesApi = {
  list: () => api.get('/api/courses'),
  get: (id) => api.get(`/api/courses/${id}`),
  byCategory: (categoryId) => api.get(`/api/courses/category/${categoryId}`),
  search: (keyword) => api.get('/api/courses/search', { params: { keyword } }),
  enrolled: () => api.get('/api/courses/enrolled'),
  create: (payload) => api.post('/api/courses', payload),
  update: (id, payload) => api.put(`/api/courses/${id}`, payload),
  remove: (id) => api.delete(`/api/courses/${id}`),
  enroll: (id) => api.post(`/api/courses/${id}/enroll`),
  purchase: (id) => api.post(`/api/courses/${id}/purchase`)
};

export const AssignmentsApi = {
  list: () => api.get('/api/assignments'),
  get: (id) => api.get(`/api/assignments/${id}`),
  byCourse: (courseId) => api.get(`/api/assignments/course/${courseId}`),
  create: (payload) => api.post('/api/assignments', payload),
  update: (id, payload) => api.put(`/api/assignments/${id}`, payload),
  remove: (id) => api.delete(`/api/assignments/${id}`)
};

export const AssignmentSubmissionsApi = {
  byAssignment: (assignmentId) => api.get(`/api/assignment-submissions/assignment/${assignmentId}`),
  get: (id) => api.get(`/api/assignment-submissions/${id}`),
  submit: (payload) => api.post('/api/assignment-submissions', payload),
  update: (id, payload) => api.put(`/api/assignment-submissions/${id}`, payload),
  remove: (id) => api.delete(`/api/assignment-submissions/${id}`)
};

export const QuizzesApi = {
  list: () => api.get('/api/quizzes'),
  get: (id) => api.get(`/api/quizzes/${id}`),
  byCourse: (courseId) => api.get(`/api/quizzes/course/${courseId}`),
  create: (payload) => api.post('/api/quizzes', payload),
  update: (id, payload) => api.put(`/api/quizzes/${id}`, payload),
  remove: (id) => api.delete(`/api/quizzes/${id}`)
};

export const QuizResultsApi = {
  byQuiz: (quizId) => api.get(`/api/quiz-results/quiz/${quizId}`),
  get: (id) => api.get(`/api/quiz-results/${id}`),
  save: (payload) => api.post('/api/quiz-results', payload),
  update: (id, payload) => api.put(`/api/quiz-results/${id}`, payload),
  remove: (id) => api.delete(`/api/quiz-results/${id}`)
};

export const QuestionsApi = {
  byQuiz: (quizId) => api.get(`/api/questions/quiz/${quizId}`)
};

export const OptionsApi = {
  byQuestion: (questionId) => api.get(`/api/options/question/${questionId}`)
};

export const VideosApi = {
  list: () => api.get('/api/videos'),
  get: (id) => api.get(`/api/videos/${id}`),
  byCourse: (courseId) => api.get(`/api/videos/course/${courseId}`),
  create: (payload) => api.post('/api/videos', payload),
  update: (id, payload) => api.put(`/api/videos/${id}`, payload),
  remove: (id) => api.delete(`/api/videos/${id}`)
};
