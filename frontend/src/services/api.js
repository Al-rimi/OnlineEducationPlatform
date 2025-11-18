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
  search: (keyword) => api.get('/api/courses/search', { params: { keyword } })
};

export const VideosApi = {
  byCourse: (courseId) => api.get(`/api/courses/${courseId}/videos`),
  get: (id) => api.get(`/api/videos/${id}`)
};
