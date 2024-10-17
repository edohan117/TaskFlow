<template>
<<<<<<< HEAD
  <section class="hero" :style="{ backgroundImage: 'url(' + slides[currentSlide].background + ')' }">
    <div class="arrow left" @click="prevSlide">&#10094;</div>
    <div v-for="(slide, index) in slides" :key="index" v-show="currentSlide === index" class="slide">
      <h2>{{ slide.title }}</h2>
      <p>{{ slide.description }}</p>
      <a href="#" @click.prevent="checkLoginAndNavigate(slide.buttonLink)" class="cta-button">{{ slide.buttonText }}</a>
    </div>
    <div class="arrow right" @click="nextSlide">&#10095;</div>
=======
  <section class="hero">
    <h2>Promote Your Website Here</h2>
    <p>Share your website and connect with an audience eager to see what you have to offer.</p>
    <a href="#" @click.prevent="checkLoginAndNavigate('/ideaSubmit')" class="cta-button">Start Promoting</a>
>>>>>>> 9705c99a777ac519ab2dfe017cf963778d9366a2
  </section>
</template>

<script>
import { computed, onMounted, ref } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';

export default {
  name: 'HeroSection',
  setup() {
    const store = useStore();
    const router = useRouter();

    const isLoggedIn = computed(() => store.getters.isAuthenticated);
    const checkLoginAndNavigate = (path) => {
      if (!isLoggedIn.value) {
        router.push('/loginForm');
      } else {
        router.push(path);
      }
    };

    const slides = ref([
      {
        title: 'Welcome to InnoNest',
        description: 'Share your innovative ideas and connect with like-minded individuals',
        buttonText: 'Start Sharing',
        buttonLink: '/newThemeList',
        background: require('@/img/image_01.jpg'),
      },
      {
        title: 'Innovate Together',
        description: 'Collaborate with a community of thinkers and creators',
        buttonText: 'Join Us',
        buttonLink: '/roomList',
        background: require('@/img/image_02.jpg'),
      },
      {
        title: 'Grow Your Ideas',
        description: 'Turn your dreams into reality with our platform',
        buttonText: 'Get Started',
        buttonLink: '/noticeList',
        background: require('@/img/image_03.jpg'),
      },
    ]);

    const currentSlide = ref(0);

    const nextSlide = () => {
      currentSlide.value = (currentSlide.value + 1) % slides.value.length;
    };

    const prevSlide = () => {
      currentSlide.value = (currentSlide.value - 1 + slides.value.length) % slides.value.length;
    };

    onMounted(() => {
      setInterval(nextSlide, 5000); // 5초마다 슬라이드 자동 전환
    });

    return {
      checkLoginAndNavigate,
      slides,
      currentSlide,
      nextSlide,
      prevSlide,
    };
  },
};
</script>

<style scoped>
.hero {
  position: relative;
<<<<<<< HEAD
  background-size: cover; /* 배경 이미지 크기를 섹션에 맞춤 */
  background-position: center;
  background-repeat: no-repeat;
  width: 100%;
  height: 60vh; /* 섹션 높이를 60%로 설정 */
  color: #fff;
  text-align: center;
=======
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
>>>>>>> 9705c99a777ac519ab2dfe017cf963778d9366a2
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
<<<<<<< HEAD
  border-radius: 20px; /* 배경을 둥글게 만들기 */
  overflow: hidden; /* 둥글게 만든 부분 외의 영역 숨기기 */
  transition: background-image 1s ease;
}

.slide {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 2rem; /* 내부 패딩 조정 */
}

h2 {
  font-size: 2.2rem; /* 제목 크기를 약간 키움 */
  margin-bottom: 1rem;
}

p {
  font-size: 1.1rem; /* 본문 크기를 약간 키움 */
  margin-bottom: 2rem;
=======
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
>>>>>>> 9705c99a777ac519ab2dfe017cf963778d9366a2
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

<<<<<<< HEAD
/* 슬라이드 컨트롤 버튼 */
.arrow {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  font-size: 2rem;
  color: #fff;
  cursor: pointer;
  user-select: none; /* 선택 방지 */
  z-index: 1; /* 화살표 버튼을 위에 표시 */
}

.arrow.left {
  left: 40px; /* 왼쪽 화살표 위치 */
}

.arrow.right {
  right: 40px; /* 오른쪽 화살표 위치 */
}

/* 반응형 디자인 설정 */
@media (max-width: 768px) {
  .hero {
    padding: 2rem 1.5rem; /* 패딩 조정 */
    height: 50vh; /* 높이 줄이기 */
  }

  h2 {
    font-size: 1.8rem; /* 제목 크기 조정 */
  }

  p {
    font-size: 1rem; /* 본문 크기 조정 */
  }

  .cta-button {
    padding: 0.5rem 1rem; /* 버튼 패딩 조정 */
    font-size: 0.9rem; /* 버튼 글자 크기 조정 */
=======
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
>>>>>>> 9705c99a777ac519ab2dfe017cf963778d9366a2
  }
}

@media (max-width: 480px) {
  .hero {
<<<<<<< HEAD
    padding: 2rem 1rem;
    height: 40vh; /* 높이 줄이기 */
  }

  h2 {
    font-size: 1.5rem; /* 제목 크기 조정 */
  }

  p {
    font-size: 0.9rem; /* 본문 크기 조정 */
  }

  .cta-button {
    padding: 0.5rem 0.75rem; /* 버튼 패딩 조정 */
    font-size: 0.8rem; /* 버튼 글자 크기 조정 */
=======
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
>>>>>>> 9705c99a777ac519ab2dfe017cf963778d9366a2
  }
}
</style>
