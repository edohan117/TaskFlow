<template>
  <section class="idea-detail">
    <div class="content-container">
      <h2>{{ idea.TITLE }}</h2>
      <div class="meta-info">
        <div class="author-info">
          <i class="fas fa-user-circle"></i>
          <span>{{ idea.WRITER }}</span>
        </div>
        <div class="date-info">
          <i class="fas fa-calendar-alt"></i>
          <span>{{ formatDate(idea.WRITERDATE) }}</span>
        </div>
      </div>
      <div class="idea-content">
        <pre>{{ idea.CONTENT }}</pre>
      </div>
      <div class="interaction-stats">
        <div class="stat-item">
          <span>LIKES {{ idea.LIKE_COUNT }}</span>
          <button class="btn-icon" :class="{ active: userReaction === 'LIKE' }" @click="toggleLike">
            <i class="bi bi-hand-thumbs-up"></i>
          </button>
        </div>
        <div class="stat-item">
          <span>DISLIKES {{ idea.DISLIKE_COUNT }}</span>
          <button class="btn-icon" :class="{ active: userReaction === 'DISLIKE' }" @click="toggleDislike">
            <i class="bi bi-hand-thumbs-down"></i>
          </button>
        </div>
        <div class="stat-item">
          <i class="fas fa-eye"></i>
          <span>VIEW {{ idea.VIEW_COUNT }}</span>
        </div>
      </div>
      <div class="idea-tags">
        <span v-for="tag in parseTags(idea.TAGS)" :key="tag" class="tag">{{ tag }}</span>
      </div>
    </div>
    <div class="action-buttons">
      <router-link to="/ideaList" class="btn btn-secondary">목록</router-link>
      <div v-if="canEditOrDelete" class="edit-delete-buttons">
        <button class="btn btn-primary" @click="goToEditPage">수정</button>
        <button class="btn btn-danger" @click="deleteIdea">삭제</button>
      </div>
    </div>
  </section>
</template>

<script>
import axios from 'axios';
import { computed, ref, onMounted } from 'vue';
import { useStore } from 'vuex';
import { useRoute, useRouter } from 'vue-router';

export default {
  name: 'IdeaDetail',
  setup() {
    const store = useStore();
    const route = useRoute();
    const router = useRouter();

    const user = computed(() => store.getters.user);
    const role = computed(() => store.getters.role);

    const idea = ref({});
    const userReaction = ref(null);

    const fetchIdeaDetail = async () => {
      const ideaId = route.params.id;
      const userId = user.value?.id;
      try {
        const response = await axios.get(`/api/idea/detail/${ideaId}`, {
          headers: {
            'User-Id': userId,
          },
          params: {
            isReaction: false // 일반 조회 시 조회수 증가
          }
        });
        idea.value = response.data;

      } catch (error) {
        console.error('아이디어 세부 정보를 가져오는 중 오류 발생:', error);
      }
    };

    const toggleLike = async () => {
      const ideaId = route.params.id;
      const userId = user.value?.id;
      try {
        if (userReaction.value === 'LIKE') {
          await axios.delete(`/api/idea/reaction/${ideaId}`, {
            data: {
              userId,
              reactionType: 'LIKE',
              isReaction: true // 반응(좋아요/싫어요) 요청임을 나타냄
            }
          });
          userReaction.value = null;
        } else {
          await axios.post(`/api/idea/reaction/${ideaId}`, {
            userId,
            reactionType: 'LIKE',
            isReaction: true // 반응(좋아요/싫어요) 요청임을 나타냄
          });
          userReaction.value = 'LIKE';
        }
        fetchIdeaDetail(); // 변경 사항을 반영하기 위해 다시 로드
      } catch (error) {
        console.error('좋아요 토글 중 오류 발생:', error);
      }
    };

    const toggleDislike = async () => {
      const ideaId = route.params.id;
      const userId = user.value?.id;
      try {
        if (userReaction.value === 'DISLIKE') {
          await axios.delete(`/api/idea/reaction/${ideaId}`, {
            data: {
              userId,
              reactionType: 'DISLIKE',
              isReaction: true // 반응(좋아요/싫어요) 요청임을 나타냄
            }
          });
          userReaction.value = null;
        } else {
          await axios.post(`/api/idea/reaction/${ideaId}`, {
            userId,
            reactionType: 'DISLIKE',
            isReaction: true // 반응(좋아요/싫어요) 요청임을 나타냄
          });
          userReaction.value = 'DISLIKE';
        }
        fetchIdeaDetail(); // 변경 사항을 반영하기 위해 다시 로드
      } catch (error) {
        console.error('싫어요 토글 중 오류 발생:', error);
      }
    };

    const formatDate = (dateString) => {
      const date = new Date(dateString);
      return date.toLocaleDateString();
    };

    const parseTags = (tagsString) => {
      return tagsString ? tagsString.split(',').map(tag => tag.trim()) : [];
    };

    const goToEditPage = () => {
      router.push(`/ideaUpdate/${route.params.id}`);
    };

    const deleteIdea = async () => {
      const ideaId = route.params.id;
      try {
        await axios.delete(`/api/idea/delete/${ideaId}`);
        router.push('/ideaList');
      } catch (error) {
        console.error('아이디어 삭제 중 오류 발생:', error);
      }
    };

    const canEditOrDelete = computed(() => {
      return role.value === 'ADMIN' || user.value?.id === idea.value.WRITER;
    });

    onMounted(() => {
      fetchIdeaDetail();
    });

    return {
      idea,
      userReaction,
      toggleLike,
      toggleDislike,
      formatDate,
      parseTags,
      goToEditPage,
      deleteIdea,
      canEditOrDelete,
    };
  },
};
</script>

<style scoped>
.idea-detail {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

.content-container {
  margin-bottom: 20px;
}

.meta-info {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}

.author-info,
.date-info {
  display: flex;
  align-items: center;
}

.author-info i,
.date-info i {
  margin-right: 5px;
}

.idea-content {
  padding: 20px;
  background-color: #f8f8f8;
  border-radius: 8px;
  margin-bottom: 20px;
}

.interaction-stats {
  display: flex;
  justify-content: space-around;
  margin-bottom: 20px;
}

.stat-item {
  display: flex;
  align-items: center;
}

.stat-item i {
  margin-right: 5px;
}

.stat-item span {
  margin-right: 10px;
}

.btn-icon {
  border: none;
  background: none;
  cursor: pointer;
}

.btn-icon.active i {
  color: #007bff;
}

.idea-tags {
  margin-bottom: 20px;
}

.tag {
  background-color: #007bff;
  color: white;
  padding: 5px 10px;
  border-radius: 5px;
  margin-right: 5px;
}

.action-buttons {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}

.edit-delete-buttons button {
  margin-left: 10px;
}
</style>
