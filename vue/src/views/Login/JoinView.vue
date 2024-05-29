<template>
  <div>
    <div id="joinFormHead">
      <h1 style="font-size: 28px;"> 아직 회원이 아니신가요? </h1>
    </div>
    <div id="joinForm" @submit.prevent="submitForm">
      <div>
        <div class="inputConent">
          <label for="userid">아이디 </label>
          <div class="input-typing">
            <input type="email" pattern=".+@..com" id="userid" placeholder="이메일로 가입해주세요" v-model="newUser.id"
              @keyup.enter="checkUserId" />
            <button style="background-color: white; color: black;" @click="checkUserId">중복확인</button>
          </div>
        </div>
        <div v-if="showAlert">
          <span v-if="userStore.userIdCheckResult && newUser.id">이미 있는 아이디입니다.</span>
          <span v-if="!userStore.userIdCheckResult && newUser.id">사용 가능한 아이디입니다.</span>
        </div>
      </div>
      <div>
        <div class="inputConent">
          <label for="userpassword">비밀번호 </label>
          <div class="input-typing">
            <input type="password" id="userpassword" v-model="newUser.password" />
          </div>
        </div>
        <span v-if="newUser.password && newUser.password.length < 8">8자 이상으로 입력해주세요.</span>
      </div>
      <div>
        <div class="inputConent">
          <label for="userpasswordcheck">비밀번호 체크 </label>
          <div class="input-typing">
            <input type="password" id="userpasswordcheck" v-model="newUserPasswordCheck" />
          </div>
        </div>
        <span v-show="newUserPasswordCheck && newUser.password !== newUserPasswordCheck
          ">비밀번호가 일치하지 않습니다.</span>
        <span v-show="newUserPasswordCheck && newUser.password === newUserPasswordCheck
          ">비밀번호가 일치합니다.</span>
      </div>
      <div>
        <div class="inputConent">
          <label for="nickname">닉네임 </label>
          <div class="input-typing">
            <input type="text" id="nickname" v-model="newUser.nickname" />
          </div>
        </div>
      </div>
      <button id="join" @click="joinUser">회원 가입하기</button>
    </div>
  </div>
</template>

<script setup>
import { useUserStore } from "@/stores/user";
import { ref } from "vue";
import { useRouter } from "vue-router";

const newUser = ref({
  nickname: "",
  id: "",
  password: "",
});

const userStore = useUserStore();

const showAlert = ref(false); //밑에 뜨는 글자

const checkUserId = function () {
  const idquery = {
    key: "id",
    word: newUser.value.id,
  };
  userStore.checkUserId(idquery);
  showAlert.value = true;
};

const newUserPasswordCheck = ref("");

const checkPassword = function () {
  if (newUser.value.password === newUserPasswordCheck) {
  }
};

const router = useRouter();

const joinUser = function () {
  userStore.joinUser(newUser.value);
  alert("반갑습니다!");
  router.push({ name: "login" });
};
</script>

<style scoped>
#joinFormHead {
  display: flex;
  justify-content: center;
}

#joinForm {
  margin: 1em 3em 1em;
  padding: 5em;
  border: 1px solid #524c42;
  border-radius: 50px;
}

.inputConent {
  display: flex;
  justify-content: space-evenly;
}

.inputConent>label {
  display: block;
  width: 180px;
  text-align: right;
  align-content: center;
}

.input-typing {
  border: 1px solid gray;
  border-radius: 50px;
  display: flex;
  justify-content: space-between;
  width: 500px;
  margin: 15px;
}

.input-typing button {
  display: block;
  width: 150px;
  font-size: 12px;
  border: none;
  margin: 5px;
}

input {
  display: block;
  width: 350px;
  border: none;
  margin: 5px;
  padding: 5px;
  outline: none;
}

#join {
  padding: 10px 20px 10px;
  font-size: 12px;
  color: white;
  background-color: #ff8911;
  border-radius: 30px;
  border: 2px solid transparent;
  margin-top: 20px;
}

span {
  margin-left: 30%;
}
</style>
