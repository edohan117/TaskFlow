<template>
  <div class="register-form">
    <h2>회원가입</h2>
    <form @submit.prevent="register">
      <div class="form-group">
        <div class="input-group">
          <input v-model="id" id="id" type="text" required  @input="idChecked = false" placeholder="ID"/>
          <button type="button" @click="checkIdDuplicate" class="btn-secondary">중복확인</button>
        </div>
        <p v-if="!isIdAvailable && idChecked" class="error">이미 사용 중인 아이디입니다.</p>
      </div>
      
      <div class="form-group">
        <input v-model="password" id="password" type="password" placeholder="PASSWORD" required />
        <p v-if="!isPasswordValid && password" class="error">비밀번호는 영문, 숫자, 특수문자를 포함 8자리 이상이어야 합니다.</p>
      </div>
      
      <div class="form-group">
        <input v-model="confirmPassword" id="confirmPassword" type="password" placeholder="PASSWORD 확인" required />
        <p v-if="passwordMismatch" class="error">비밀번호가 일치하지 않습니다.</p>
      </div>
      
      <div class="form-group">
        <input v-model="username" id="username" type="text" placeholder="이름" required />
      </div>
      
      <div class="form-group">
        <label for="phone">전화번호</label>
        <input v-model="phone" id="phone" type="text" @input="formatPhoneNumber" required />
      </div>
      
      <div class="form-group">
        <label for="birthdate">생년월일</label>
        <input v-model="birthdate" id="birthdate" type="date" required />
      </div>
    
      <div class="form-group">
        <label for="email">이메일</label>
        <div class="input-group">
          <input v-model="emailPrefix" id="emailPrefix" type="text" placeholder="이메일 앞부분" @input="updateEmail" />
          <span>@</span>
          <input v-model="emailPrefix" id="emailfix" type="text" placeholder="이메일 뒷부분" @input="updateEmail" />
          <select v-model="emailDomain" @change="updateEmail">
            <option value="">선택</option>
            <option value="gmail.com">gmail.com</option>
            <option value="naver.com">naver.com</option>
            <option value="daum.net">daum.net</option>
            <option value="yahoo.com">yahoo.com</option>
            <option value="direct-input">직접 입력</option>
          </select>
          <input v-if="emailDomain === 'direct-input'" v-model="customEmailDomain" type="text" placeholder="직접 입력" @input="updateEmail" />
        </div>
        <p v-if="!isEmailValid && email" class="error">올바른 이메일 형식이 아닙니다.</p>
      </div>

      <div class="form-group address-group">
        <label>주소</label>
        <div class="input-group">
          <input v-model="postcode" type="text" id="sample4_postcode" placeholder="우편번호" readonly />
          <button type="button" @click="execDaumPostcode" class="btn-secondary">우편번호 찾기</button>
        </div>
        <input type="text" id="sample4_roadAddress" v-model="roadAddress" placeholder="도로명주소" readonly />
        <input type="text" id="sample4_jibunAddress" v-model="jibunAddress" placeholder="지번주소" readonly />
        <div class="input-group">
          <input type="text" id="sample4_detailAddress" v-model="detailAddress" placeholder="상세주소" />
          <input type="text" id="sample4_extraAddress" v-model="extraAddress" placeholder="참고항목" />
        </div>
      </div>

      <div class="form-group">
        <input v-model="sns" id="sns" type="text" placeholder="SNS 주소 (선택)" />
      </div>

      <!-- <div class="form-group">
        <label for="profilePicture">프로필 사진</label>
        <input type="file" id="profilePicture" @change="handleFileUpload" />
      </div> -->

      <button type="submit" :disabled="!canRegister" class="btn-primary">회원가입</button>
      
      <div v-if="!canRegister" class="feedback-message">
        <p>회원가입을 완료하려면 다음 사항을 확인해주세요:</p>
        <ul>
          <li v-if="!id || !isIdAvailable || !idChecked">아이디를 입력하고 중복확인을 해주세요.</li>
          <li v-if="!isPasswordValid">올바른 형식의 비밀번호를 입력해주세요.</li>
          <li v-if="passwordMismatch">비밀번호 확인이 일치하지 않습니다.</li>
          <li v-if="!username">이름을 입력해주세요.</li>
          <li v-if="!phone">전화번호를 입력해주세요.</li>
          <li v-if="!birthdate">생년월일을 입력해주세요.</li>
          <li v-if="!roadAddress">주소를 입력해주세요.</li>
          <li v-if="email && !isEmailValid">올바른 이메일 형식을 입력해주세요.</li>
        </ul>
      </div>
    </form>
  </div>
</template>

<script>
import { ref, computed } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

export default {
  name: 'Register',
  setup() {
    const router = useRouter();
    const id = ref('');
    const password = ref('');
    const confirmPassword = ref('');
    const username = ref('');
    const phone = ref('');
    const birthdate = ref('');
    const email = ref('');
    const postcode = ref('');
    const roadAddress = ref('');
    const jibunAddress = ref('');
    const detailAddress = ref('');
    const extraAddress = ref('');
    const sns = ref('');
    const profilePicture = ref(null);
    const isIdAvailable = ref(false);
    const isEmailValid = ref(true);
    const idChecked = ref(false);

    const passwordMismatch = computed(() => password.value !== confirmPassword.value);
    const isPasswordValid = computed(() => {
      const regex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;
      return regex.test(password.value);
    });

    const isIdValid = computed(() => {
      const regex = /^[a-zA-Z0-9-_]{6,12}$/; // 아이디 정규 표현식
      return regex.test(id.value);
    });

    const canRegister = computed(() => 
      id.value && isIdAvailable.value && isIdValid.value && idChecked && password.value && confirmPassword.value && 
      username.value && phone.value && birthdate.value && !passwordMismatch.value && 
      isPasswordValid.value && roadAddress.value && (email.value === '' || isEmailValid.value)
    );

    const handleFileUpload = (event) => {
      profilePicture.value = event.target.files[0];
    };

    const checkIdDuplicate = async () => {
      if (id.value) {
        if (!isIdValid.value) {
          alert('아이디는 6-12자의 영문, 숫자, 기호(-, _)만 사용할 수 있습니다.');
          id.value = '';
          return;
        }
        try {
          const checkId = id.value.toUpperCase();
          const response = await axios.post('/api/member/check-id', { id: checkId});
          isIdAvailable.value = response.data.available;
          idChecked.value = true;
          alert(isIdAvailable.value ? '사용 가능한 아이디입니다.' : '이미 사용 중인 아이디입니다.');
        } catch (error) {
          console.error('ID 중복 확인 중 오류 발생:', error);
        }
      } else {
        alert('아이디를 입력해주세요.');
      }
    };

    const formatPhoneNumber = () => {
      let formatted = phone.value.replace(/\D/g, '');
      if (formatted.length >= 3) {
        formatted = formatted.substring(0, 3) + '-' + formatted.substring(3);
      }
      if (formatted.length >= 8) {
        formatted = formatted.substring(0, 8) + '-' + formatted.substring(8);
      }
      phone.value = formatted.substring(0, 13);
    };

    const validateEmail = () => {
      const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      isEmailValid.value = regex.test(email.value) || email.value === '';
    };

    const execDaumPostcode = () => {
      new window.daum.Postcode({
        oncomplete: function(data) {
          postcode.value = data.zonecode;
          roadAddress.value = data.roadAddress;
          jibunAddress.value = data.jibunAddress;
          
          if (data.userSelectedType === 'R') {
            extraAddress.value = data.bname && /[동|로|가]$/g.test(data.bname) ? `(${data.bname})` : '';
            extraAddress.value += data.buildingName && data.apartment === 'Y' ? (extraAddress.value ? `, ${data.buildingName}` : `(${data.buildingName})`) : '';
          } else {
            extraAddress.value = '';
          }
        }
      }).open();
    };

    const register = async () => {
      if (canRegister.value) {
        const formData = new FormData();
        formData.append('id', id.value);
        formData.append('password', password.value);
        formData.append('username', username.value);
        formData.append('phone', phone.value);
        formData.append('birthdate', birthdate.value);
        formData.append('email', email.value);
        formData.append('address', `${roadAddress.value} ${detailAddress.value} ${extraAddress.value}`.trim());
        formData.append('sns', sns.value);
        if (profilePicture.value) {
          formData.append('profilePicture', profilePicture.value);
        }

        try {
          const response = await axios.post('/api/member/register', formData, {
            headers: {
              'Content-Type': 'multipart/form-data',
            },
          });
          if (response.data.status === 'registered') {
            alert('회원가입 성공!');
            router.push('/login');
          } else {
            alert('회원가입 실패!');
          }
        } catch (error) {
          console.error('회원가입 중 오류 발생:', error);
          alert('회원가입 중 오류가 발생했습니다.');
        }
      } else {
        alert('모든 필수 항목을 올바르게 입력해주세요.');
      }
    };

    return {
      id,
      password,
      confirmPassword,
      username,
      phone,
      birthdate,
      email,
      postcode,
      roadAddress,
      jibunAddress,
      detailAddress,
      extraAddress,
      sns,
      handleFileUpload,
      register,
      checkIdDuplicate,
      passwordMismatch,
      isPasswordValid,
      isIdValid,
      formatPhoneNumber,
      execDaumPostcode,
      canRegister,
      validateEmail,
      isEmailValid,
      isIdAvailable,
      idChecked
    };
  },
  mounted() {
    const script = document.createElement('script');
    script.src = '//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js';
    document.head.appendChild(script);
  }
};
</script>

<style scoped>
.register-form {
  max-width: 700px;
  margin: 40px auto;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0,0,0,0.1);
}

h2 {
  text-align: center;
  color: #333;
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 20px;
}

label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
  color: #555;
}

input[type="text"],
input[type="password"],
input[type="email"],
input[type="date"],
input[type="file"] {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 16px;
  box-sizing: border-box;
}

.input-group {
  display: flex;
  gap: 10px;
  margin-bottom: 10px;
}

.input-group input {
  flex-grow: 1;
}

.btn-secondary {
  padding: 10px 15px;
  background-color: #6c757d;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
  white-space: nowrap;
}

.btn-primary {
  width: 100%;
  padding: 12px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.3s;
}

.btn-primary:hover {
  background-color: #0056b3;
}

.btn-primary:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}

.error {
  color: #dc3545;
  font-size: 14px;
  margin-top: 5px;
}

input[readonly] {
  background-color: #e9ecef;
}

.feedback-message {
  margin-top: 20px;
  padding: 10px;
  background-color: #f8d7da;
  border: 1px solid #f5c6cb;
  border-radius: 4px;
  color: #721c24;
}

.feedback-message ul {
  padding-left: 20px;
  margin-top: 10px;
}

.address-group input {
  margin-bottom: 10px;
}

.address-group .input-group:last-child input {
  width: 50%;
}
</style>
