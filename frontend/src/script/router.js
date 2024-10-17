import { createRouter, createWebHistory } from 'vue-router';
import Home from '@/views/Home.vue';
import LoginForm from '@/views/LoginPage.vue';
import RoomList from '@/views/RoomList.vue';
import MyLikes from '@/views/MyLikes.vue';
import MyRecords from '@/views/MyRecords.vue';
import RoomSubmit from '@/views/RoomSubmit.vue';
import RoomDetail from '@/views/RoomDetail.vue';
import RoomEdit from '@/views/RoomEdit.vue';
import NewThemeList from '@/views/NewThemeList.vue';
import RcmdThemeList from '@/views/RcmdThemeList.vue';
import RoomRank from '@/views/RoomRank.vue';
import NoticeList from '@/views/NoticeList.vue';
import NoticeDetail from '@/views/NoticeDetail.vue';
import NoticeEdit from '@/views/NoticeEdit.vue';
import NoticeCreate from '@/views/NoticeCreate.vue';
import Register from '@/views/RegisterPage.vue';
import MemberList from '@/views/MemberList.vue';
import MyProfile from '@/views/MyProfile.vue';

const routes = [
  { path: '/', name: 'Home', component: Home, meta: { title: '홈' } },
  { path: '/loginForm', name: 'LoginForm', component: LoginForm, meta: { title: '로그인' } },
  { path: '/roomList', name: 'RoomList', component: RoomList, meta: { title: '방탈출 목록' } },
  { path: '/myLikes', name: 'MyLikes', component: MyLikes, meta: { title: '나의 좋아요' } },
  { path: '/myRecords', name: 'MyRecords', component: MyRecords, meta: { title: '나의 기록' } },
  { path: '/roomSubmit', name: 'RoomSubmit', component: RoomSubmit, meta: { requiresAuth: true, admin: true, title: '방탈출 정보 등록' } },
  { path: '/roomDetail/:id', name: 'RoomDetail', component: RoomDetail, meta: { title: '방탈출 상세 정보' } },
  { path: '/roomEdit/:id', name: 'RoomEdit', component: RoomEdit, meta: { requiresAuth: true, admin: true, title: '방탈출 정보 수정' } },
  { path: '/roomRank', name: 'RoomRank', component: RoomRank, meta: { title: '방탈출 평점 순위' } },
  { path: '/newThemeList', name: 'NewThemeList', component: NewThemeList, meta: { title: '새로운 테마 목록' } },
  { path: '/rcmdThemeList', name: 'RcmdThemeList', component: RcmdThemeList, meta: { title: '추천 테마 목록' } },
  { path: '/noticeList', name: 'NoticeList', component: NoticeList, meta: { title: '공지 목록' } },
  { path: '/noticeDetail/:id', name: 'NoticeDetail', component: NoticeDetail, meta: { title: '공지 상세 정보' } },
  { path: '/noticeEdit/:id', name: 'NoticeEdit', component: NoticeEdit, meta: { requiresAuth: true, admin: true, title: '공지 수정' } },
  { path: '/noticeCreate', name: 'NoticeCreate', component: NoticeCreate, meta: { requiresAuth: true, admin: true, title: '공지 등록' } },
  { path: '/register', name: 'Register', component: Register, meta: { title: '회원가입' } },
  { path: '/memberList', name: 'MemberList', component: MemberList, meta: { requiresAuth: true, admin: true, title: '회원 목록' } },
  { path: '/myProfile', name: 'MyProfile', component: MyProfile, meta: { title: '내 프로필' } },
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

// 페이지 제목 설정
router.afterEach((to) => {
  document.title = to.meta.title || '기본 제목'; // 기본 제목을 설정
});

export default router;
