<template>
  <div class="register-form">
    <h2>회원가입</h2>
    <input v-model="id" type="text" placeholder="아이디" />
    <input v-model="password" type="password" placeholder="비밀번호" />
    <input v-model="username" type="text" placeholder="이름" />
    <input v-model="phone" type="text" placeholder="전화번호" />
    <input v-model="email" type="email" placeholder="이메일" />
    <button @click="register">회원가입</button>
  </div>
</template>

<script>
import { ref } from 'vue';
import { useRouter } from 'vue-router'; // useRouter 훅을 가져옵니다.
import axios from 'axios';

export default {
  name: 'Register',
  setup() {
    const id = ref('');
    const password = ref('');
    const username = ref('');
    const phone = ref('');
    const email = ref('');

    const router = useRouter(); // useRouter 훅을 사용해 router 객체를 가져옵니다.

    const register = async () => {
      if (id.value && password.value && username.value && phone.value && email.value) {
        try {
          const response = await axios.post('/api/member/register', {
            id: id.value,
            password: password.value,
            username: username.value,
            phone: phone.value,
            email: email.value,
          });
          if (response.data.status === 'registered') {
            alert('회원가입 성공!');
            router.push('/loginForm'); // this.$router.push 대신 router.push를 사용합니다.
          } else {
            alert('회원가입 실패!');
          }
        } catch (error) {
          console.error('회원가입 중 오류 발생:', error);
        }
      } else {
        alert('모든 필드를 입력해 주세요');
      }
    };

    return {
      id,
      password,
      username,
      phone,
      email,
      register,
    };
  },
};
</script>

<style scoped>
.register-form {
  display: flex;
  flex-direction: column;
  width: 300px;
  margin: 20px auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
  background-color: #f9f9f9;
}

.register-form input {
  margin-bottom: 10px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.register-form button {
  padding: 10px;
  background-color: #333;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}
</style>
