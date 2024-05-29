<template>
  <div>
    <div>
      <h1 style="font-size: 28px;"> 운동 습관 만들러 가볼까요?</h1>
    </div>

    <div id="loginForm">
      <div id="loginId">
        <label for="id">아이디 </label>
        <input type="text" id="id" v-model.trim="id" placeholder="아이디를 입력해주세요" @keyup.enter="login" />
      </div>
      <div id="loginPassword">
        <label for="password">비밀번호 </label>
        <input type="password" id="password" v-model.trim="pw" placeholder="비밀번호를 입력해주세요" @keyup.enter="login" />
      </div>
      <div class="btnContainer">
        <div id="routerLinkBox">
          <RouterLink :to="{ name: 'resetPassword' }">비밀번호 재설정 </RouterLink> |
          <RouterLink :to="{ name: 'join' }">회원가입</RouterLink>
        </div>
        <KakaoLogin></KakaoLogin>
        <button @click="login">로그인</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useUserStore } from "@/stores/user";
import KakaoLogin from "@/components/login/KakaoLogin.vue";

const store = useUserStore();

const id = ref("");
const pw = ref("");

const login = function () {
  if (id.value === "" || pw.value === "") {
    alert("아이디와 비밀번호를 입력해주세요");
  }

  store.userLogin(id.value, pw.value);
};
</script>

<style scoped>
button {
  padding: 10px 20px 10px;
  font-size: 12px;
  color: white;
  background-color: #ff8911;
  border-radius: 30px;
  border: 2px solid transparent;
}

#loginForm {
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin: 1em 3em 1em;
  padding: 5em;
  border: 1px solid #524c42;
  border-radius: 50px;
}

#loginId,
#loginPassword {
  margin: 10px;
}

a {
  text-decoration: none;
  color: black;
  margin: 0 10px;
}

input[type="text"],
input[type="password"] {
  width: 50%;
  padding: 0.5em;
  margin-top: 0.5em;
  border: 1px solid #ccc;
  border-radius: 50px;
  outline: none;
}

.btnContainer {
  display: flex;
  justify-content: space-evenly;
  align-content: center;
  padding: 0 20%;
  margin: 20px;
}

#routerLinkBox {
  align-content: center;
}
</style>
