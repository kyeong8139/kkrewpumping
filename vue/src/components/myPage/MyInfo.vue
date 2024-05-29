<template>
  <div>
    <h1>회원정보 수정하기</h1>
    <div v-if="isKakaoUser">
      <div class="updateInfo">
        <p>회원 정보 수정</p>

        <div>
          <label for="nickname">닉네임</label>
          <input type="text" id="nickname" v-model="nickname" />
        </div>

        <div>
          <label for="myDecide">한마디</label>
          <input type="text" id="myDecide" v-model="mySentence" />
        </div>

        <button @click="updateMyInfo">수정 완료</button>

        <hr />
        <button @click="deleteMe">회원 탈퇴하기</button>
      </div>
    </div>
    <div v-else-if="userStore.isAuthorized">
      <div class="updateInfo">
        <p>회원 정보 수정</p>
        <div>
          <label for="password"> 비밀번호 </label>
          <input type="password" id="password" v-model="password" />
        </div>

        <div>
          <label for="nickname">닉네임</label>
          <input type="text" id="nickname" v-model="nickname" />
        </div>

        <div>
          <label for="myDecide">한마디</label>
          <input type="text" id="myDecide" v-model="mySentence" />
        </div>

        <button @click="updateMyInfo">수정 완료</button>

        <hr />
        <button @click="deleteMe">회원 탈퇴하기</button>
      </div>
    </div>
    <div v-else>
      <div class="updateInfo">
        <p>비밀번호 인증하기</p>
        <label for="password">비밀번호 재입력 </label>
        <input type="password" id="password" name="password" v-model="passwordInput" @keyup.enter="checkPassword" />
        <button @click="checkPassword">확인</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import router from "@/router";
import { useUserStore } from "@/stores/user";
import { ref, computed } from "vue";

const userStore = useUserStore();
const isKakaoUser = computed(() => {
  return sessionStorage.getItem("social") === "kakao";
});

///////비밀번호 확인
const passwordInput = ref("");

const checkPassword = function () {
  userStore.checkPassword(userStore.loginUserId, passwordInput.value);
};

//////////회원정보 수정

const password = ref("");
const nickname = ref(userStore.loginUserName);
const mySentence = ref("");

const updateMyInfo = function () {
  const user = ref({
    id: userStore.loginUserId,
    password: password.value,
    nickname: nickname.value,
    introduction: mySentence.value,
  });

  if (!isKakaoUser) {
    if (user.value.password === "") {
      alert("비밀번호를 입력해주세요")
    } else {
      userStore.updateMyInfo(user.id, user.value);
      router.push({ name: "myPage" });
    }
  } else {
    userStore.updateMyInfo(user.id, user.value);
    router.push({ name: "myPage" });
  }
};

//////회원탈퇴
const deleteMe = function () {
  const userId = userStore.loginUserId;
  userStore.deleteMe(userId);
  router.push({ name: "home" });
};
</script>

<style scoped>
.updateInfo {
  margin: 10px;
  padding: 10px;
}

.updateInfo p,
.updateInfo label,
.updateInfo input,
.updateInfo button {
  margin: 10px;
}

.updateInfo input {
  border-radius: 20px;
}

.updateInfo button {
  padding: 5px 10px 5px;
  font-size: 12px;
  color: white;
  background-color: #ff8911;
  border-radius: 30px;
  border: 2px solid transparent;
}

h1 {
  font-size: 18px;
  margin: 20px;
}
</style>
