<template>
  <section class="idea-edit">
    <h2>아이디어 수정하기</h2>
    <form @submit.prevent="updateIdea">
      <div class="form-group">
        <label for="title">제목</label>
        <input
          type="text"
          id="title"
          v-model="idea.TITLE"
          required
          placeholder="아이디어 제목을 입력하세요"
        />
      </div>
      <div class="form-group">
        <label for="content">내용</label>
        <textarea
          id="content"
          v-model="idea.CONTENT"
          required
          placeholder="아이디어 내용을 입력하세요"
        ></textarea>
      </div>
      <div class="form-actions">
        <button type="submit">수정 완료</button>
        <button type="button" @click="cancelEdit">취소</button>
      </div>
    </form>
  </section>
</template>

<script>
import axios from 'axios';

export default {
  name: 'IdeaEdit',
  data() {
    return {
      idea: {
        TITLE: '',
        CONTENT: ''
      }
    };
  },
  created() {
    this.fetchIdeaDetail();
  },
  methods: {
    fetchIdeaDetail() {
      const ideaId = this.$route.params.id;
      axios.get(`/api/idea/detail/${ideaId}`)
        .then(response => {
          this.idea = response.data;
        })
        .catch(error => {
          console.error('아이디어 세부 사항을 가져오는 중 오류 발생:', error);
        });
    },
    updateIdea() {
      const ideaId = this.$route.params.id;
      axios.put(`/api/idea/update/${ideaId}`, this.idea)
        .then(() => {
          this.$router.push({ name: 'IdeaDetail', params: { id: ideaId } });
        })
        .catch(error => {
          console.error('아이디어 업데이트 중 오류 발생:', error);
        });
    },
    cancelEdit() {
      const ideaId = this.$route.params.id;
      this.$router.push({ name: 'IdeaDetail', params: { id: ideaId } });
    }
  }
};
</script>

<style scoped>
/* 스타일링 */
.idea-edit {
  max-width: 800px;
  margin: 0 auto;
  padding: 2rem;
}

h2 {
  font-size: 2rem;
  color: #333;
  margin-bottom: 1rem;
}

.form-group {
  margin-bottom: 1rem;
}

label {
  display: block;
  font-size: 1rem;
  color: #333;
  margin-bottom: 0.5rem;
}

input[type="text"], textarea {
  width: 100%;
  padding: 0.5rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 1rem;
}

textarea {
  height: 150px;
  resize: vertical;
}

.form-actions {
  display: flex;
  justify-content: space-between;
  margin-top: 1rem;
}

button {
  background-color: #3498db;
  color: #fff;
  border: none;
  border-radius: 4px;
  padding: 0.5rem 1rem;
  cursor: pointer;
  transition: background-color 0.3s;
}

button:hover {
  background-color: #2980b9;
}

button[type="button"] {
  background-color: #e74c3c;
}

button[type="button"]:hover {
  background-color: #c0392b;
}
</style>
