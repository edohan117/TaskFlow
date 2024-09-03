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
          <i class="bi bi-hand-thumbs-up"></i>
        </div>
        <div class="stat-item">
          <span>DISLIKES {{ idea.DISLIKE_COUNT }}</span>
          <i class="bi bi-hand-thumbs-down"></i>
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
import { computed } from 'vue';
import { useStore } from 'vuex';

export default {
  name: 'IdeaDetail',
  data() {
    return {
      idea: {}
    };
  },
  created() {
    this.fetchIdeaDetail();
  },
  setup() {
    const store = useStore();
    const user = computed(() => store.getters.user);
    const role = computed(() => store.getters.role);

    return {
      user,
      role,
    };
  },
  methods: {
    fetchIdeaDetail() {
      const ideaId = this.$route.params.id;
      const userId = this.user?.id; // Vuex에서 가져온 사용자 ID
      axios.get(`/api/idea/detail/${ideaId}`, {
        headers: {
          'User-Id': userId // 사용자 ID를 헤더에 추가
        }
      })
        .then(response => {
          this.idea = response.data;
        })
        .catch(error => {
          console.error('아이디어 세부정보를 가져오는 중 오류 발생:', error);
        });
    },
    formatDate(dateString) {
      const options = { year: 'numeric', month: 'long', day: 'numeric' };
      return new Date(dateString).toLocaleDateString(undefined, options);
    },
    goToEditPage() {
      this.$router.push({ name: 'IdeaEdit', params: { id: this.$route.params.id } });
    },
    deleteIdea() {
      const ideaId = this.$route.params.id;
      if (confirm('정말로 이 아이디어를 삭제하시겠습니까?')) {
        axios.delete(`/api/idea/delete/${ideaId}`)
          .then(() => {
            alert('아이디어가 삭제되었습니다.');
            this.$router.push({ name: 'IdeaList' }); // 아이디어 목록 페이지로 이동
          })
          .catch(error => {
            console.error('아이디어를 삭제하는 중 오류 발생:', error);
          });
      }
    },
    parseTags(tagsString) {
      return tagsString ? tagsString.split(',').map(tag => tag.trim()) : [];
    }
  },
  computed: {
    canEditOrDelete() {
      if (!this.user || !this.user.id || !this.role) {
        return false;
      }
      return this.idea.WRITER === this.user.id || this.role === 'ADMIN';
    }
  }
};
</script>

<style scoped>
.idea-detail {
  max-width: 800px;
  margin: 2rem auto;
  background-color: #ffffff;
  border-radius: 12px;
  box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.content-container {
  padding: 2rem;
}

h2 {
  font-size: 2.2rem;
  color: #2c3e50;
  margin-bottom: 1rem;
  font-weight: 700; /* 두껍게 */
}

.meta-info {
  display: flex;
  justify-content: space-between;
  margin-bottom: 1.5rem;
  font-size: 0.9rem;
  color: #7f8c8d;
}

.author-info,
.date-info {
  display: flex;
  align-items: center;
}

.author-info i,
.date-info i {
  margin-right: 0.5rem;
  font-size: 1.2rem;
}

.idea-content {
  font-size: 1.1rem;
  color: #34495e;
  line-height: 1.6;
  margin-bottom: 2rem;
}

.idea-content pre {
  white-space: pre-wrap; /* 줄바꿈과 연속 공백을 그대로 유지 */
  font-weight: 700; /* 진하게 설정 */
  color: #34495e; /* 원하는 글씨 색상 */
}

.interaction-stats {
  display: flex;
  justify-content: flex-start;
  gap: 2rem;
  margin-bottom: 1.5rem;
}

.stat-item {
  display: flex;
  align-items: center;
  font-size: 1rem;
  color: #7f8c8d;
}

.stat-item i {
  margin-right: 0.5rem;
  font-size: 1.2rem;
}

.idea-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
  margin-bottom: 1.5rem;
}

.tag {
  background-color: #3498db;
  color: #ffffff;
  border-radius: 12px;
  padding: 0.3rem 0.6rem;
  font-size: 0.8rem;
  white-space: nowrap;
  font-weight: 500; /* 태그의 글씨를 약간 두껍게 */
}

.tag:hover {
  background-color: #2980b9;
}

.action-buttons {
  display: flex;
  justify-content: space-between;
  padding: 1rem 2rem;
  background-color: #f8f9fa;
  border-top: 1px solid #e9ecef;
}

.btn {
  padding: 0.6rem 1.2rem;
  border: none;
  border-radius: 5px;
  font-size: 1rem;
  cursor: pointer;
  transition: all 0.3s ease;
  font-weight: 600; /* 버튼 글씨를 두껍게 */
}

.btn-secondary {
  background-color: #6c757d;
  color: #ffffff;
  text-decoration: none;
}

.btn-primary {
  background-color: #007bff;
  color: #ffffff;
}

.btn-danger {
  background-color: #dc3545;
  color: #ffffff;
}

.btn:hover {
  opacity: 0.9;
  transform: translateY(-2px);
}

.edit-delete-buttons {
  display: flex;
  gap: 1rem;
}

@media (max-width: 600px) {
  .idea-detail {
    margin: 1rem;
  }

  .content-container {
    padding: 1.5rem;
  }

  h2 {
    font-size: 1.8rem;
  }

  .interaction-stats {
    flex-wrap: wrap;
    gap: 1rem;
  }

  .action-buttons {
    flex-direction: column;
    gap: 1rem;
  }

  .btn {
    width: 100%;
  }
}
</style>
