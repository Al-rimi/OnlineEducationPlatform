import { createRouter, createWebHistory } from 'vue-router';
import { useToast } from './stores/toast';
import UserList from './components/UserList.vue';
import UserForm from './components/UserForm.vue';
import UserDetail from './components/UserDetail.vue';
import AuthPage from './pages/AuthPage.vue';
import Dashboard from './components/Dashboard.vue';
import AdminDashboard from './components/AdminDashboard.vue';
import TeacherDashboard from './components/TeacherDashboard.vue';
import StudentDashboard from './components/StudentDashboard.vue';
import RoleRedirect from './components/RoleRedirect.vue';
import Profile from './components/Profile.vue';
import NotFound from './components/NotFound.vue';
import Courses from './components/Courses.vue';
import CourseDetail from './components/CourseDetail.vue';
import VideoPlayer from './components/VideoPlayer.vue';
import CourseForm from './components/CourseForm.vue';
import Assignments from './components/Assignments.vue';
import AssignmentForm from './components/AssignmentForm.vue';
import AssignmentSubmission from './components/AssignmentSubmission.vue';
import Quizzes from './components/Quizzes.vue';
import QuizForm from './components/QuizForm.vue';
import QuizTaking from './components/QuizTaking.vue';

const routes = [
  { path: '/', component: RoleRedirect, meta: { requiresAuth: true } },
  { path: '/admin', component: AdminDashboard, meta: { requiresAuth: true, requiresRole: 'ADMIN' } },
  { path: '/teacher', component: TeacherDashboard, meta: { requiresAuth: true, requiresRole: 'TEACHER' } },
  { path: '/student', component: StudentDashboard, meta: { requiresAuth: true, requiresRole: 'STUDENT' } },
  { path: '/users', component: UserList, meta: { requiresAuth: true, requiresRole: 'ADMIN' } },
  { path: '/add', component: UserForm, meta: { requiresAuth: true, requiresRole: 'ADMIN' } },
  { path: '/edit/:id', component: UserForm, props: true, meta: { requiresAuth: true, requiresRole: 'ADMIN' } },
  { path: '/user/:id', component: UserDetail, props: true, meta: { requiresAuth: true, requiresRole: 'ADMIN' } },
  { path: '/profile', component: Profile, meta: { requiresAuth: true } },
  // Public catalog routes
  { path: '/courses', component: Courses },
  { path: '/courses/:id', component: CourseDetail, props: true },
  { path: '/courses/new', component: CourseForm, meta: { requiresAuth: true, requiresRole: 'TEACHER' } },
  { path: '/courses/:id/edit', component: CourseForm, props: true, meta: { requiresAuth: true, requiresRole: 'TEACHER' } },
  { path: '/videos/:id', component: VideoPlayer, props: true },
  { path: '/assignments', component: Assignments, meta: { requiresAuth: true } },
  { path: '/assignments/new', component: AssignmentForm, meta: { requiresAuth: true, requiresRole: 'TEACHER' } },
  { path: '/assignments/:id/edit', component: AssignmentForm, props: true, meta: { requiresAuth: true, requiresRole: 'TEACHER' } },
  { path: '/courses/:courseId/assignments', component: Assignments, props: true, meta: { requiresAuth: true } },
  { path: '/assignments/:id/submit', component: AssignmentSubmission, props: true, meta: { requiresAuth: true, requiresRole: 'STUDENT' } },
  { path: '/quizzes', component: Quizzes, meta: { requiresAuth: true } },
  { path: '/quizzes/new', component: QuizForm, meta: { requiresAuth: true, requiresRole: 'TEACHER' } },
  { path: '/quizzes/:id/edit', component: QuizForm, props: true, meta: { requiresAuth: true, requiresRole: 'TEACHER' } },
  { path: '/courses/:courseId/quizzes', component: Quizzes, props: true, meta: { requiresAuth: true } },
  { path: '/quizzes/:id/take', component: QuizTaking, props: true, meta: { requiresAuth: true, requiresRole: 'STUDENT' } },
  { path: '/login', component: AuthPage, props: { mode: 'login' }, meta: { layout: 'auth' } },
  { path: '/register', component: AuthPage, props: { mode: 'register' }, meta: { layout: 'auth' } },
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
  if (to.meta.requiresRole) {
    if (!token) {
      toast.error('Access denied');
      return next('/login');
    }
    try {
      // JWT is base64url encoded, need to convert to base64 first
      const base64Url = token.split('.')[1];
      const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
      const payload = JSON.parse(atob(base64));
      const roles = payload.roles || [];
      if (!roles.includes(to.meta.requiresRole)) {
        toast.error('Access denied: Insufficient permissions');
        return next('/');
      }
    } catch (e) {
      console.error('Token parsing error:', e);
      toast.error('Invalid token');
      localStorage.removeItem('token');
      return next('/login');
    }
  }
  next();
});

export default router;
