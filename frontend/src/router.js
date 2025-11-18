import { createRouter, createWebHistory } from 'vue-router';
import { useToast } from './stores/toast';
import UserList from './components/UserList.vue';
import UserForm from './components/UserForm.vue';
import UserDetail from './components/UserDetail.vue';
import LoginPage from './pages/LoginPage.vue';
import RegisterPage from './pages/RegisterPage.vue';
import Dashboard from './components/Dashboard.vue';
import Profile from './components/Profile.vue';
import NotFound from './components/NotFound.vue';
import Courses from './components/Courses.vue';
import CourseDetail from './components/CourseDetail.vue';
import VideoPlayer from './components/VideoPlayer.vue';

const routes = [
  { path: '/', component: Dashboard, meta: { requiresAuth: true } },
  { path: '/users', component: UserList, meta: { requiresAuth: true } },
  { path: '/add', component: UserForm, meta: { requiresAuth: true } },
  { path: '/edit/:id', component: UserForm, props: true, meta: { requiresAuth: true } },
  { path: '/user/:id', component: UserDetail, props: true },
  { path: '/profile', component: Profile, meta: { requiresAuth: true } },
  // Public catalog routes
  { path: '/courses', component: Courses },
  { path: '/courses/:id', component: CourseDetail, props: true },
  { path: '/videos/:id', component: VideoPlayer, props: true },
  { path: '/login', component: LoginPage, meta: { layout: 'auth' } },
  { path: '/register', component: RegisterPage, meta: { layout: 'auth' } },
  { path: '/:pathMatch(.*)*', name: 'NotFound', component: NotFound }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token');
  const toast = useToast();
  if (to.meta.requiresAuth && !token) {
    toast.info('Please login to continue');
    return next({ path: '/login', query: { redirect: to.fullPath } });
  }
  next();
});

export default router;
