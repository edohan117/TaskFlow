<template>
  <section class="hero">
    <h2>Promote Your Website Here</h2>
    <p>Share your website and connect with an audience eager to see what you have to offer.</p>
    <a href="#" @click.prevent="checkLoginAndNavigate('/ideaSubmit')" class="cta-button">Start Promoting</a>
  </section>
</template>

<script>
import { computed } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';

export default {
  name: 'HeroSection',
  setup() {
    const store = useStore();
    const router = useRouter();

    const isLoggedIn = computed(() => store.getters.isAuthenticated);
    const user = computed(() => store.getters.user);
    const role = computed(() => store.getters.role);

    const checkLoginAndNavigate = (path) => {
      if (!isLoggedIn.value) {
        router.push('/loginForm');
      } else {
        router.push(path);
      }
    };

    return {
      isLoggedIn,
      user,
      role,
      checkLoginAndNavigate,
    };
  },
  computed: {
    canView() {
      return this.role === 'ADMIN';
    }
  },
};
</script>

<style scoped>
.hero {
  position: relative;
  background-image: url('@/assets/heroBack.jpg');
  /* 경로가 올바른지 확인 */
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  color: #fff;
  text-align: center;
  padding: 5rem 3rem;
  border-radius: 8px;
  margin-bottom: 2rem;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  overflow: hidden;
  /* 가상 요소의 경계를 설정 */
}

.hero::before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  /* 반투명 검은색 오버레이 */
  z-index: 1;
  /* 오버레이가 배경보다 위에 오도록 설정 */
}

h2 {
  font-size: 3rem;
}

p {
  font-size: 1.5rem;
}

h2,
p,
.cta-button {
  position: relative;
  /* 텍스트가 오버레이 위에 오도록 설정 */
  z-index: 2;
  text-shadow: 3px 3px 6px rgba(255, 215, 0, 0.9);
  /* 노란색 그림자 */
}

.cta-button {
  background-color: #fff;
  color: #3498db;
  border: none;
  padding: 1rem 2rem;
  font-size: 1.2rem;
  font-weight: bold;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease, color 0.3s ease;
  text-decoration: none;
  text-shadow: none;
  /* 버튼은 그림자 없음 */
}

.cta-button:hover {
  background-color: #2980b9;
  color: #fff;
}

@media (max-width: 768px) {
  .hero {
    padding: 4rem 2rem;
  }

  h2 {
    font-size: 3rem;
  }

  p {
    font-size: 1.3rem;
  }

  .cta-button {
    padding: 0.9rem 1.75rem;
    font-size: 1.1rem;
  }
}

@media (max-width: 480px) {
  .hero {
    padding: 3rem 1.5rem;
  }

  h2 {
    font-size: 2.5rem;
  }

  p {
    font-size: 1.1rem;
  }

  .cta-button {
    padding: 0.8rem 1.5rem;
    font-size: 1rem;
  }
}
</style>
