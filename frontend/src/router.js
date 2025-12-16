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
  { path: '/admin', component: AdminDashboard, meta: { requiresAuth: true } },
  { path: '/teacher', component: TeacherDashboard, meta: { requiresAuth: true } },
  { path: '/student', component: StudentDashboard, meta: { requiresAuth: true } },
  { path: '/users', component: UserList, meta: { requiresAuth: true } },
  { path: '/add', component: UserForm, meta: { requiresAuth: true } },
  { path: '/edit/:id', component: UserForm, props: true, meta: { requiresAuth: true } },
  { path: '/user/:id', component: UserDetail, props: true },
  { path: '/profile', component: Profile, meta: { requiresAuth: true } },
  // Public catalog routes
  { path: '/courses', component: Courses },
  { path: '/courses/:id', component: CourseDetail, props: true },
  { path: '/courses/new', component: CourseForm, meta: { requiresAuth: true } },
  { path: '/courses/:id/edit', component: CourseForm, props: true, meta: { requiresAuth: true } },
  { path: '/videos/:id', component: VideoPlayer, props: true },
  { path: '/assignments', component: Assignments, meta: { requiresAuth: true } },
  { path: '/assignments/new', component: AssignmentForm, meta: { requiresAuth: true } },
  { path: '/assignments/:id/edit', component: AssignmentForm, props: true, meta: { requiresAuth: true } },
  { path: '/courses/:courseId/assignments', component: Assignments, props: true, meta: { requiresAuth: true } },
  { path: '/assignments/:id/submit', component: AssignmentSubmission, props: true, meta: { requiresAuth: true } },
  { path: '/quizzes', component: Quizzes, meta: { requiresAuth: true } },
  { path: '/quizzes/new', component: QuizForm, meta: { requiresAuth: true } },
  { path: '/quizzes/:id/edit', component: QuizForm, props: true, meta: { requiresAuth: true } },
  { path: '/courses/:courseId/quizzes', component: Quizzes, props: true, meta: { requiresAuth: true } },
  { path: '/quizzes/:id/take', component: QuizTaking, props: true, meta: { requiresAuth: true } },
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
  next();
});

export default router;
