<template>
  <section class="idea-list">
    <h3>Recent Posts</h3>
    <div class="idea-grid">
      <router-link v-for="idea in recentPosts" :key="idea.ID" :to="{ name: 'IdeaDetail', params: { id: idea.ID } }"
        class="idea-card-link">
        <article class="idea-card">
          <h3 class="idea-title">{{ idea.TITLE }}</h3>
          <p class="idea-content">{{ truncateText(idea.CONTENT, 80) }}</p>
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
  </section>
</template>

<script>
export default {
  name: 'RecentPostsMain',
  props: {
    recentPosts: {
      type: Array,
      required: true,
    },
  },
  methods: {
    formatDate(dateString) {
      const options = { year: 'numeric', month: 'long', day: 'numeric' };
      return new Date(dateString).toLocaleDateString(undefined, options);
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
/* Container for the idea list */
.idea-list {
  max-width: 1200px;
  margin: 0 auto;
  padding: 2rem;
  margin-bottom: 2rem;
  text-align: center;
}

/* Title styles */
h3 {
  font-size: 2rem;
  /* Increased font size for consistency */
  color: #333;
  margin-bottom: 2rem;
}

/* Flex container for the idea cards */
.idea-grid {
  display: flex;
  flex-wrap: wrap;
  gap: 1.5rem;
  /* Gap between cards */
  justify-content: center;
}

/* Style for each idea card link */
.idea-card-link {
  text-decoration: none;
  flex: 1 1 300px;
  /* Allows cards to grow and shrink, with a minimum width of 300px */
}

/* Style for each idea card */
.idea-card {
  background-color: #fff;
  border-radius: 12px;
  padding: 1.5rem;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.3);
  transition: all 0.3s ease;
  display: flex;
  flex-direction: column;
  height: 100%;
  /* Ensures all cards are the same height */
}

/* Hover effect for cards */
.idea-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.6);
}

/* Title styling */
.idea-title {
  font-size: 1.4rem;
  color: #2c3e50;
  margin-bottom: 0.75rem;
}

.idea-content {
  font-size: 1rem;
  color: #34495e;
  margin-bottom: 1rem;
  line-height: 1.6;
  white-space: pre-wrap; /* 줄바꿈과 연속 공백을 그대로 유지 */
  overflow: hidden; /* 넘치는 텍스트 숨기기 */
  text-overflow: ellipsis; /* 잘린 텍스트에 말줄임표 표시 */
}

/* Meta information styling */
.idea-meta {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  font-size: 0.9rem;
  color: #7f8c8d;
}

/* Aligning elements within meta section */
.idea-author,
.idea-stats,
.idea-date {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

/* Flex properties for stats */
.idea-stats {
  display: flex;
  justify-content: space-between;
}

.idea-stats span {
  display: flex;
  align-items: center;
  gap: 0.3rem;
}

/* Icon styling */
i {
  font-size: 1rem;
  color: #3498db;
}

/* Pagination styling */
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

/* Tags styling */
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
