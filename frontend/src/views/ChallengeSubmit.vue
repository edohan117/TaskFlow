<template>
    <div class="challenge-submission-container">
        <div class="challenge-submission">
            <h1>혁신적인 챌린지</h1>
            <form @submit.prevent="submitChallenge">
                <div class="form-group">
                    <input v-model="challenge.title" id="title" type="text" required placeholder="챌린지 제목">
                    <label for="title">챌린지의 핵심을 간단히 표현해주세요</label>
                </div>
                <div class="form-group">
                    <textarea v-model="challenge.content" id="content" rows="6" required placeholder="챌린지 설명"></textarea>
                    <label for="content">당신의 혁신적인 챌린지를 자세히 설명해주세요</label>
                </div>
                <div class="form-group">
                    <div class="tag-input-container">
                        <input v-model="tagInput" id="tags" type="text" @keyup.enter="addTag" placeholder="태그 입력">
                        <button @click.prevent="addTag" type="button" class="btn-add-tag">
                            <span class="material-icons">add</span>
                        </button>
                    </div>
                    <label for="tags">관련 키워드를 입력하고 엔터를 눌러주세요</label>
                </div>
                <div class="tags">
                    <span v-for="(tag, index) in challenge.tags" :key="index" class="tag">
                        {{ tag }}
                        <button @click="removeTag(index)" type="button" class="remove-tag-btn">
                            <span class="material-icons">close</span>
                        </button>
                    </span>
                </div>
                <div class="form-group">
                    <label for="category">카테고리 선택</label>
                    <select v-model="challenge.category" id="category" required>
                        <option disabled value="">카테고리를 선택해주세요</option>
                        <option v-for="category in categorys" :key="category.ID" :value="category.NAME">
                            {{ category.NAME }}
                        </option>
                    </select>
                </div>
                <button type="submit" class="btn-submit">
                    <span class="material-icons">send</span> 챌린지 제출하기
                </button>
            </form>
        </div>
    </div>
</template>

<script>
import { mapGetters } from 'vuex';
import axios from 'axios';

export default {
    data() {
        return {
            challenge: {
                title: '',
                content: '',
                tags: [],
                category: '' // 카테고리 추가
            },
            tagInput: '',
            categorys: [] // Correctly named categorys to match template
        };
    },
    computed: {
        ...mapGetters(['user']) // Vuex에서 사용자 정보 가져오기
    },
    created() {
        this.fetchCategory();
    },
    methods: {
        addTag() {
            if (this.tagInput.trim() && !this.challenge.tags.includes(this.tagInput.trim())) {
                this.challenge.tags.push(this.tagInput.trim());
                this.tagInput = '';
            }
        },
        removeTag(index) {
            this.challenge.tags.splice(index, 1);
        },
        submitChallenge() {
            const tagsString = this.challenge.tags.join(',');
            const challengeData = { ...this.challenge, tags: tagsString, userId: this.user.id }; // 사용자 ID 추가
            console.log(challengeData);
            axios.post('/api/challenge/submit', challengeData)
                .then(response => {
                    console.log('챌린지 제출 성공:', response.data);
                    this.challenge = { title: '', content: '', tags: [], category: '' };
                    this.$router.push('/challengeList');
                })
                .catch(error => {
                    console.error('챌린지 제출 실패:', error);
                });
        },
        fetchCategory() {
            axios.get('/api/category/list')
                .then(response => {
                    this.categorys = response.data;
                })
                .catch(error => {
                    console.error('카테고리 로드 실패:', error);
                });
        }
    }
};
</script>
  
<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap');
@import url('https://fonts.googleapis.com/icon?family=Material+Icons');

body {
    font-family: 'Poppins', sans-serif;
    background-color: #f0f4f8;
    color: #2d3748;
    line-height: 1.6;
}

.challenge-submission-container {
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 100vh;
    padding: 2rem;
}

.challenge-submission {
    background-color: #ffffff;
    border-radius: 20px;
    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
    padding: 3rem;
    width: 100%;
    max-width: 700px;
}

h1 {
    font-size: 2.5rem;
    font-weight: 600;
    margin-bottom: 2rem;
    text-align: center;
    color: #3a86ff;
}

.form-group {
    margin-bottom: 2rem;
    position: relative;
}

input[type="text"],
textarea,
select {
    width: 100%;
    padding: 1rem;
    border: 2px solid #e2e8f0;
    border-radius: 10px;
    font-size: 1rem;
    transition: all 0.3s ease;
    background-color: #f8fafc;
}

input[type="text"]:focus,
textarea:focus,
select:focus {
    outline: none;
    border-color: #3a86ff;
    box-shadow: 0 0 0 3px rgba(58, 134, 255, 0.1);
}

label {
    position: absolute;
    left: 1rem;
    top: -0.5rem;
    background-color: #ffffff;
    padding: 0 0.5rem;
    font-size: 0.875rem;
    color: #718096;
}

.tag-input-container {
    display: flex;
    gap: 0.5rem;
}

.btn-add-tag {
    background-color: #3a86ff;
    color: #ffffff;
    border: none;
    border-radius: 10px;
    padding: 0.5rem;
    cursor: pointer;
    transition: background-color 0.3s ease;
}

.btn-add-tag:hover {
    background-color: #2563eb;
}

.tags {
    display: flex;
    flex-wrap: wrap;
    gap: 0.5rem;
    margin-bottom: 2rem;
}

.tag {
    background-color: #e2e8f0;
    color: #4a5568;
    padding: 0.5rem 1rem;
    border-radius: 20px;
    font-size: 0.875rem;
    display: flex;
    align-items: center;
    transition: all 0.3s ease;
}

.tag:hover {
    background-color: #cbd5e0;
}

.remove-tag-btn {
    background: none;
    border: none;
    color: #718096;
    margin-left: 0.5rem;
    cursor: pointer;
    padding: 0;
    display: flex;
    align-items: center;
}

.btn-submit {
    width: 100%;
    padding: 1rem;
    background-color: #3a86ff;
    color: #ffffff;
    border: none;
    border-radius: 10px;
    font-size: 1rem;
    font-weight: 500;
    cursor: pointer;
    transition: background-color 0.3s ease;
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 0.5rem;
}

.btn-submit:hover {
    background-color: #2563eb;
}

.material-icons {
    font-size: 1.25rem;
}

@media (max-width: 768px) {
    .challenge-submission {
        padding: 2rem;
    }

    h1 {
        font-size: 2rem;
    }
}
</style>
