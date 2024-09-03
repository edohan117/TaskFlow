<template>
  <section class="idea-list">
    <h2>Idea Showcase</h2>
    <div class="idea-grid">
      <router-link v-for="idea in paginatedIdeas" :key="idea.ID" :to="{ name: 'IdeaDetail', params: { id: idea.ID } }" class="idea-card-link">
        <article class="idea-card">
          <h3 class="idea-title">{{ idea.TITLE }}</h3>
          <p class="idea-content">{{ truncateText(idea.CONTENT, 80) }}</p> <!-- 한 줄당 최대 80자로 설정 -->
          <div class="idea-meta">
            <div class="idea-author">
              <span>{{ idea.WRITER }}</span>
            </div>
            <div class="idea-stats">
              <span>LIKES {{ idea.LIKE_COUNT }}<i class="bi bi-hand-thumbs-up"></i></span>
              <span>DISLIKES {{ idea.DISLIKE_COUNT }}<i class="bi bi-hand-thumbs-down"></i></span>
              <span>VIEW {{ idea.VIEW_COUNT }}</span>
            </div>
            <div class="idea-date">
              <span>{{ formatDate(idea.WRITERDATE) }}</span>
            </div>
            <div class="idea-tags">
              <span v-for="tag in parseTags(idea.TAGS)" :key="tag" class="tag">{{ tag }}</span>
            </div>
          </div>
        </article>
      </router-link>
    </div>

    <div class="pagination">
      <button @click="changePage(currentPage - 1)" :disabled="currentPage === 1">Previous</button>

      <button v-for="page in pageNumbers" :key="page" @click="changePage(page)"
        :class="{ 'active': page === currentPage }">
        {{ page }}
      </button>

      <button @click="changePage(currentPage + 1)" :disabled="currentPage === totalPages">Next</button>
    </div>
  </section>
</template>

<script>
import axios from 'axios';

export default {
  name: 'IdeaList',
  data() {
    return {
      ideas: [],        // 전체 아이디어 리스트
      currentPage: 1,   // 현재 페이지
      pageSize: 6      // 페이지당 아이디어 개수
    };
  },
  computed: {
    totalPages() {
      return Math.ceil(this.ideas.length / this.pageSize);
    },
    paginatedIdeas() {
      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      return this.ideas.slice(start, end);
    },
    pageNumbers() {
      const pages = [];
      for (let i = 1; i <= this.totalPages; i++) {
        pages.push(i);
      }
      return pages;
    }
  },
  created() {
    this.fetchIdeaList();
  },
  methods: {
    fetchIdeaList() {
      axios.get('api/idea/list')
        .then(response => {
          this.ideas = response.data;
        })
        .catch(error => {
          console.error('Error fetching idea list:', error);
        });
    },
    formatDate(dateString) {
      const options = { year: 'numeric', month: 'long', day: 'numeric' };
      return new Date(dateString).toLocaleDateString(undefined, options);
    },
    changePage(page) {
      if (page >= 1 && page <= this.totalPages) {
        this.currentPage = page;
      }
    },
    parseTags(tagsString) {
      return tagsString.split(',').map(tag => tag.trim());
    },
    truncateText(text, maxLength) {
      // 텍스트를 두 줄로 제한
      let lines = text.split('\n');
      let truncatedText = '';

      // 각 줄을 maxLength로 자르기
      for (let i = 0; i < lines.length; i++) {
        if (i < 2) { // 두 줄까지만 처리
          truncatedText += lines[i].slice(0, maxLength);
          if (i < 1) truncatedText += '\n'; // 줄바꿈 추가
        }
      }

      // 텍스트가 자르고 나서도 길면 말줄임표 추가
      if (text.length > maxLength * 2) {
        truncatedText += '...';
      }

      return truncatedText;
    }
  }
};
</script>

<style scoped>
.idea-list {
  max-width: 1200px;
  margin: 0 auto;
  padding: 2rem;
}

h2 {
  font-size: 2.5rem;
  color: #333;
  margin-bottom: 2rem;
  text-align: center;
}

.idea-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 2rem;
}

.idea-card-link {
  text-decoration: none;
}

.idea-card {
  background-color: #fff;
  border-radius: 12px;
  padding: 1.5rem;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.3);
  transition: all 0.3s ease;
}

.idea-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.6);
}

.idea-title {
  font-size: 1.4rem;
  color: #2c3e50;
  margin-bottom: 0.75rem;
}

/* 줄 수를 제한하는 텍스트 스타일 */
.idea-content {
  font-size: 1rem;
  color: #34495e;
  margin-bottom: 1rem;
  line-height: 1.6;
  white-space: pre-wrap; /* 줄바꿈과 연속 공백을 그대로 유지 */
  overflow: hidden; /* 넘치는 텍스트 숨기기 */
  text-overflow: ellipsis; /* 잘린 텍스트에 말줄임표 표시 */
}

.idea-meta {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  font-size: 0.9rem;
  color: #7f8c8d;
}

.idea-author,
.idea-stats,
.idea-date {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.idea-stats {
  display: flex;
  justify-content: space-between;
}

.idea-stats span {
  display: flex;
  align-items: center;
  gap: 0.3rem;
}

i {
  font-size: 1rem;
  color: #3498db;
}

/* Pagination styles */
.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 0.5rem;
  margin-top: 2rem;
}

.pagination button {
  background-color: #3498db;
  color: #fff;
  border: none;
  border-radius: 4px;
  padding: 0.5rem 1rem;
  cursor: pointer;
  transition: background-color 0.3s;
}

.pagination button:disabled {
  background-color: #bdc3c7;
  cursor: not-allowed;
}

.pagination button:hover:not(:disabled) {
  background-color: #2980b9;
}

.pagination button.active {
  background-color: #2980b9;
  font-weight: bold;
}

.idea-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
  margin-top: 0.5rem;
}

.tag {
  background-color: #3498db;
  color: #fff;
  border-radius: 12px;
  padding: 0.3rem 0.6rem;
  font-size: 0.8rem;
  white-space: nowrap;
}

.tag:hover {
  background-color: #2980b9;
}
</style>
