<template>
  <section class="room-list">
    <h2 class="section-title">TopLikedPosts</h2>
    <div class="slider-container">
      <button class="slider-button prev" @click="prevSlide" :disabled="currentIndex === 0">&lt;</button>
      <div class="slider-wrapper" :style="{ transform: `translateX(-${currentIndex * slideWidth}%)` }">
        <div v-for="(room, index) in  topLikedPosts" :key="room.ID" class="room-card">
          <router-link :to="{ name: 'RoomDetail', params: { id: room.ID } }" class="room-card-link">
            <div class="room-image-container">
              <img class="room-image" :src="room.IMG_PATH" :alt="room.THEME_NM" />
              <div class="room-genre">
                {{ room.GENRE_NM }}
                <span v-if="index === 0" class="ranking-icon">
                  <i class="bi bi-trophy-fill" style="color: gold;"></i> <!-- 1위 아이콘 -->
                </span>
                <span v-if="index === 1" class="ranking-icon">
                  <i class="bi bi-trophy-fill" style="color: silver;"></i> <!-- 2위 아이콘 -->
                </span>
                <span v-if="index === 2" class="ranking-icon">
                  <i class="bi bi-trophy-fill" style="color: #cd7f32;"></i> <!-- 3위 아이콘 -->
                </span>
              </div>
            </div>
            <div class="room-info">
              <h3 class="room-title">{{ room.THEME_NM }} <i class="bi bi-balloon-heart"></i> {{ room.LIKES }}</h3>
              <p class="room-content">{{ room.ROOM_NM }}</p>
              <div class="room-meta">
                <span class="meta-item"><i class="fas fa-users"></i> {{ room.MIN_PARTY }}-{{ room.MAX_PARTY }}명</span>
                <span class="meta-item"><i class="fas fa-clock"></i> {{ room.RUN_TIME }}분</span>
              </div>
            </div>
          </router-link>
        </div>
      </div>
      <button class="slider-button next" @click="nextSlide" :disabled="currentIndex === maxIndex">&gt;</button>
    </div>
  </section>
</template>

<script>
import { ref, computed, onMounted, onUnmounted } from 'vue';

export default {
  name: 'TopLikedPosts',
  props: {
    topLikedPosts: {
      type: Array,
      required: true,
    },
  },
  setup(props) {
    const currentIndex = ref(0);
    const slideWidth = ref(20); // 5개씩 보여줄 때는 20%

    const maxIndex = computed(() => props.topLikedPosts.length - Math.floor(100 / slideWidth.value));

    const prevSlide = () => {
      if (currentIndex.value > 0) {
        currentIndex.value--;
      }
    };

    const nextSlide = () => {
      if (currentIndex.value < maxIndex.value) {
        currentIndex.value++;
      }
    };

    const handleResize = () => {
      if (window.innerWidth < 768) {
        slideWidth.value = 100; // 모바일에서는 1개씩
      } else if (window.innerWidth < 1024) {
        slideWidth.value = 33.33; // 태블릿에서는 3개씩
      } else {
        slideWidth.value = 20; // 데스크톱에서는 5개씩
      }
      currentIndex.value = 0; // 리사이즈 시 첫 슬라이드로 리셋
    };

    onMounted(() => {
      handleResize();
      window.addEventListener('resize', handleResize);
    });

    onUnmounted(() => {
      window.removeEventListener('resize', handleResize);
    });

    return {
      currentIndex,
      slideWidth,
      maxIndex,
      prevSlide,
      nextSlide,
    };
  },
};
</script>


<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;500;700&display=swap');

.room-list {
  max-width: 1200px;
  font-family: 'Noto Sans KR', sans-serif;
}

.section-title {
  font-size: 2.5rem;
  color: #2c3e50;
  text-align: center;
  font-weight: 700;
}

.slider-container {
  position: relative;
  overflow: hidden;
}

.slider-wrapper {
  display: flex;
  transition: transform 0.3s ease-in-out;
}

.room-card {
  flex: 0 0 20%; /* 5개씩 보여줄 때 */
  padding: 0 10px;
  box-sizing: border-box;
}

.room-card-link {
  display: block;
  text-decoration: none;
  color: inherit;
}

.room-image-container {
  position: relative;
  overflow: hidden;
  width: 100%;
  padding-top: 141.42%; /* 1:√2 Aspect Ratio */
}

.room-image {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover; /* 이미지가 컨테이너에 맞게 잘리도록 설정 */
  background-color: #f0f0f0;
  transition: transform 0.3s ease;
}

.room-card:hover .room-image {
  transform: scale(1.05);
}

.room-genre {
  position: absolute;
  top: 10px;
  right: 10px;
  background-color: rgba(0, 0, 0, 0.6);
  color: #fff;
  padding: 5px 10px;
  border-radius: 20px;
  font-size: 0.8rem;
}

.room-info {
  padding: 1rem;
  background-color: #fff;
  border-radius: 0 0 12px 12px;
}

.room-title {
  font-size: 1.2rem;
  color: #2c3e50;
  font-weight: 700;
}

.room-content {
  font-size: 0.9rem;
  color: #34495e;
  margin-bottom: 1rem;
  line-height: 1.6;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.room-meta {
  display: flex;
  justify-content: space-between;
  font-size: 0.8rem;
  color: #7f8c8d;
}

.meta-item {
  display: flex;
  align-items: center;
  gap: 0.3rem;
}

.meta-item i {
  font-size: 0.9rem;
}

.slider-button {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  background-color: rgba(0, 0, 0, 0.5);
  color: #fff;
  border: none;
  padding: 10px 15px;
  font-size: 1.5rem;
  cursor: pointer;
  z-index: 10;
}

.slider-button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.slider-button.prev {
  left: 10px;
}

.slider-button.next {
  right: 10px;
}

@media (max-width: 1024px) {
  .room-card {
    flex: 0 0 33.33%; /* 3개씩 보여줄 때 */
  }
}

@media (max-width: 768px) {
  .room-card {
    flex: 0 0 100%; /* 1개씩 보여줄 때 */
  }
  
  .room-title {
    font-size: 1rem;
  }
  
  .room-content {
    font-size: 0.8rem;
  }
  
  .room-meta {
    font-size: 0.7rem;
  }
}

.ranking-icon {
  margin-left: 5px;
}
</style>