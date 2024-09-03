import { createRouter, createWebHistory } from 'vue-router';
import Home from '@/views/Home.vue';
import LoginForm from '@/views/LoginPage.vue';
import IdeaSubmit from '@/views/IdeaSubmit.vue';
import IdeaList from '@/views/IdeaList.vue';
import IdeaDetail from '@/views/IdeaDetail.vue';
import IdeaEdit from '@/views/IdeaEdit.vue';
import NoticeList from '@/views/NoticeList.vue';
import NoticeDetail from '@/views/NoticeDetail.vue';
import NoticeEdit from '@/views/NoticeEdit.vue';
import NoticeCreate from '@/views/NoticeCreate.vue';
import Register from '@/views/RegisterPage.vue';
import MemberList from '@/views/MemberList.vue';
import ChallengeList from '@/views/ChallengeList.vue';
import ChallengeDetail from '@/views/ChallengeDetail.vue';
import ChallengeSubmit from '@/views/ChallengeSubmit.vue';
import ChallengeEdit from '@/views/ChallengeEdit.vue';
import MyProfile from '@/views/MyProfile.vue';
import MyIdea from '@/views/MyIdea.vue';
import MySettings from '@/views/MySettings.vue';

const routes = [
  { path: '/', name: 'Home', component: Home },
  { path: '/loginForm', name: 'LoginForm', component: LoginForm },
  { path: '/ideaSubmit', name: 'IdeaSubmit', component: IdeaSubmit },
  { path: '/ideaList', name: 'IdeaList', component: IdeaList },
  { path: '/ideaDetail/:id', name: 'IdeaDetail', component: IdeaDetail },
  { path: '/ideaEdit/:id', name: 'IdeaEdit', component: IdeaEdit },
  { path: '/noticeList', name: 'NoticeList', component: NoticeList },
  { path: '/noticeDetail/:id', name: 'NoticeDetail', component: NoticeDetail },
  { path: '/noticeEdit/:id', name: 'NoticeEdit', component: NoticeEdit },
  { path: '/noticeCreate', name: 'NoticeCreate', component: NoticeCreate },
  { path: '/register', name: 'Register', component: Register, meta: { requiresAuth: true, admin: true } },
  { path: '/memberList', name: 'MemberList', component: MemberList, meta: { requiresAuth: true, admin: true } },
  { path: '/challengeList', name: 'ChallengeList', component: ChallengeList },
  { path: '/challengeDetail/:id', name: 'ChallengeDetail', component: ChallengeDetail },
  { path: '/challengeSubmit', name: 'ChallengeSubmit', component: ChallengeSubmit },
  { path: '/challengeEdit/:id', name: 'ChallengeEdit', component: ChallengeEdit },
  { path: '/myProfile', name: 'MyProfile', component: MyProfile },
  { path: '/myIdea', name: 'MyIdea', component: MyIdea },
  { path: '/mySettings', name: 'MySettings', component: MySettings },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
});

router.beforeEach((to, from, next) => {
  const isAuthenticated = !!localStorage.getItem('id');
  const role = localStorage.getItem('role');

  if (to.meta.requiresAuth && !isAuthenticated) {
    next('/loginForm'); // 로그인 페이지로 리다이렉트
  } else if (to.meta.admin && role !== 'ADMIN') {
    alert('접근할 수 있는 권한이 아닙니다'); // 권한이 없는 경우 알림 메시지 표시
    next('/'); // 홈 페이지로 리다이렉트
  } else {
    next();
  }
});
export default router;
