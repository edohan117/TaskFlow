<template>
  <section class="hero">
    <h2>Welcome to InnoNest</h2>
    <p>Share your innovative ideas and connect with like-minded individuals</p>
    <a href="#" @click.prevent="checkLoginAndNavigate('/ideaSubmit')" class="cta-button">Start Sharing</a>
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

    // Vuex 상태와 Getter
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
  background-color: #3498db;
  color: #fff;
  text-align: center;
  padding: 4rem 2rem;
  border-radius: 8px;
  margin-bottom: 2rem;
  /* 기본적으로 flexbox를 사용하여 내용 중앙 정렬 */
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

h2 {
  font-size: 2.5rem;
  margin-bottom: 1rem;
}

p {
  font-size: 1.2rem;
  margin-bottom: 2rem;
}

.cta-button {
  background-color: #fff;
  color: #3498db;
  border: none;
  padding: 0.75rem 1.5rem;
  font-size: 1rem;
  font-weight: bold;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease, color 0.3s ease;
  text-decoration: none;
}

.cta-button:hover {
  background-color: #2980b9;
  color: #fff;
}

/* 반응형 디자인 설정 */
@media (max-width: 768px) {
  .hero {
    padding: 3rem 1.5rem;
  }

  h2 {
    font-size: 2rem;
  }

  p {
    font-size: 1rem;
  }

  .cta-button {
    padding: 0.75rem 1.25rem;
    font-size: 0.9rem;
  }
}

@media (max-width: 480px) {
  .hero {
    padding: 2rem 1rem;
  }

  h2 {
    font-size: 1.75rem;
  }

  p {
    font-size: 0.9rem;
  }

  .cta-button {
    padding: 0.75rem 1rem;
    font-size: 0.8rem;
  }
}
</style>