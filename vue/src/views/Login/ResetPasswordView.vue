<template>
  <div>
    <h1>
      비밀번호 재설정하기
    </h1>
    <div id="resetForm">
      <div class="resetFormDetail">
        <label for="id">메일입력 </label>
        <input type="text" id="id" v-model.trim="user.id" placeholder="아이디를 입력해주세요" />
        <button @click="sendCode">인증전송</button>
      </div>
      <div class="resetFormDetail">
        <label for="code">인증번호 </label>
        <input type="text" id="code" v-model.trim="code" placeholder="메일함에서 인증번호 확인 후 입력" />
        <button :class="{ 'hidden-button': !userStore.sendEmail }" @click="checkAssertCode">인증확인</button>
      </div>

      <div v-if="userAssert">
        <div class="resetFormDetail">
          <label for="userpassword">비밀번호 </label>
          <input type="password" id="userpassword" v-model="user.password" />
          <span v-if="user.password && user.password.length < 8">8자 이상으로 입력해주세요.</span>
        </div>
        <div class="resetFormDetail">
          <label for="userpasswordcheck">비밀번호 체크 </label>
          <input type="password" id="userpasswordcheck" v-model="userPasswordCheck" />
        </div>
        <div class="resetFormDetail">
          <span v-show="userPasswordCheck && user.password !== userPasswordCheck
            ">비밀번호가 일치하지 않습니다.</span>
          <span v-show="userPasswordCheck && user.password === userPasswordCheck
            ">비밀번호가 일치합니다.</span>
        </div>
        <button @click="setNewPassword">비밀번호 변경하기</button>
      </div>
    </div>
  </div>
</template>

<script setup>

import { useUserStore } from "@/stores/user";
import { ref } from "vue";

const user = ref({
  id: "",
  password: "",
});

const userStore = useUserStore();

const code = ref("")
const userAssert = ref(false)
const userPasswordCheck = ref('');

const sendCode = function () {
  userStore.sendCode(user.value.id);
}

const checkAssertCode = function () {
  console.log(code.value);
  console.log(userStore.assertCode)
  if (code.value === userStore.assertCode) {
    userAssert.value = true
  }
}

const setNewPassword = function () {
  if (user.value.password !== userPasswordCheck.value) {
    alert("비밀번호가 일치하지 않습니다.")
  } else {
    console.log(user);
    userStore.setNewPassword(user.value);
    userAssert.value = false;
  }
}

</script>

<style scoped>
.hidden-button {
  visibility: hidden;
}

#resetForm {
  margin: 1em 3em 1em;
  padding: 5em;
  border: 1px solid #524c42;
  border-radius: 50px;
}

button {
  padding: 10px 20px 10px;
  font-size: 12px;
  color: white;
  background-color: #ff8911;
  border-radius: 30px;
  border: 2px solid transparent;
}

.resetFormDetail {
  display: flex;
  justify-content: space-between;
  padding: 10px 30%;
  margin: 10px;
}

.resetFormDetail label,
.resetFormDetail input,
.resetFormDetail button,
.resetFormDetail span {
  margin: 10px;
  padding: 10px;
}

.resetFormDetail input {
  border-radius: 20px;
}

.hidden-button {
  visibility: hidden;
}

</style>