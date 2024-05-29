<template>
  <a id="kakao-login-btn" @click="kakaoLogin">
    <img src="https://k.kakaocdn.net/14/dn/btroDszwNrM/I6efHub1SN5KCJqLm1Ovx1/o.jpg" alt="카카오 로그인 버튼" />
  </a>
</template>

<script setup>
import { useUserStore } from "@/stores/user";

const userStore = useUserStore();

const kakaoLogin = function () {
  userStore.doKaKaoLogin();
};
</script>

<style scoped>
#kakao-login-btn {
  display: block;
  width: 145px;
  height: 45px;
}

img {
  width: 145px;
  height: 45px;
  border-radius: 50px;
}
</style>
