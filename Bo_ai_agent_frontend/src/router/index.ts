import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router';

const Home = () => import('../views/Home.vue');
const LoveChat = () => import('../views/LoveChat.vue');
const ManusChat = () => import('../views/ManusChat.vue');

const routes: RouteRecordRaw[] = [
  { path: '/', name: 'home', component: Home },
  { path: '/love', name: 'love', component: LoveChat },
  { path: '/manus', name: 'manus', component: ManusChat }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;

