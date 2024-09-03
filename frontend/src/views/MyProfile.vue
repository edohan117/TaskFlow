<template>
  <section class="profile-container">
    <div class="profile-header">
      <div class="profile-avatar">
        <img :src="profile.PROFILE_PICTURE" :alt="profile.USERNAME">
      </div>
      <h1>{{ profile.USERNAME }}의 프로필</h1>
      <p class="bio">{{ profile.BIO }}</p>
    </div>
    <div class="profile-content">
      <div class="profile-info">
        <div class="info-item">
          <i class="bi bi-person"></i>
          <span>ID: {{ profile.ID }}</span>
        </div>
        <div class="info-item">
          <i class="bi bi-envelope"></i>
          <span>이메일: {{ profile.EMAIL }}</span>
        </div>
        <div class="info-item">
          <i class="bi bi-telephone"></i>
          <span>전화번호: {{ profile.PHONE }}</span>
        </div>
        <div class="info-item">
          <i class="bi bi-geo-alt"></i>
          <span>위치: {{ profile.LOCATION }}</span>
        </div>
        <div class="info-item">
          <i class="bi bi-calendar-check"></i>
          <span>가입일: {{ formatDate(profile.CREATEDATE) }}</span>
        </div>
        <div class="info-item">
          <i class="bi bi-clock-history"></i>
          <span>최종 수정일: {{ formatDate(profile.UPDATEDATE) }}</span>
        </div>
        <div class="info-item">
          <i class="bi bi-link-45deg"></i>
          <a :href="profile.SOCIAL_LINKS" target="_blank" rel="noopener noreferrer">LINK</a>
        </div>
      </div>
    </div>
    <div class="profile-actions">
      <button class="edit-profile">프로필 편집</button>
    </div>
  </section>
</template>

<script>
import axios from 'axios';

export default {
  name: 'UserProfile',
  data() {
    return {
      profile: {}
    };
  },
  created() {
    this.fetchProfileData();
  },
  methods: {
    fetchProfileData() {
      axios.get('api/member/profile')
        .then(response => {
          this.profile = response.data;
          console.log(this.profile);
        })
        .catch(error => {
          console.error('Error fetching profile data:', error);
        });
    },
    formatDate(dateString) {
      const options = { year: 'numeric', month: 'long', day: 'numeric' };
      return new Date(dateString).toLocaleDateString('ko-KR', options);
    }
  }
};
</script>

<style scoped>
.profile-container {
  max-width: 800px;
  margin: 2rem auto;
  background-color: #ffffff;
  border-radius: 15px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.profile-header {
  background: linear-gradient(135deg, #3498db, #8e44ad);
  color: white;
  padding: 2rem;
  text-align: center;
}

.profile-avatar {
  width: 150px;
  height: 150px;
  margin: 0 auto 1rem;
  border-radius: 50%;
  overflow: hidden;
  border: 5px solid white;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
}

.profile-avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.profile-header h1 {
  font-size: 2rem;
  margin: 0 0 0.5rem;
}

.bio {
  font-style: italic;
  margin-bottom: 0;
}

.profile-content {
  padding: 2rem;
}

.profile-info {
  display: grid;
  gap: 1rem;
}

.info-item {
  display: flex;
  align-items: center;
  font-size: 1.1rem;
  color: #333;
}

.info-item i {
  margin-right: 1rem;
  font-size: 1.3rem;
  color: #3498db;
}

.info-item a {
  color: #3498db;
  text-decoration: none;
}

.info-item a:hover {
  text-decoration: underline;
}

.profile-actions {
  padding: 0 2rem 2rem;
  text-align: center;
}

.edit-profile {
  background-color: #3498db;
  color: white;
  border: none;
  padding: 0.8rem 2rem;
  font-size: 1rem;
  border-radius: 25px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.edit-profile:hover {
  background-color: #2980b9;
}
</style>