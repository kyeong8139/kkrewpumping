<template>
  <div id="myProfile">
    <div class="myProfileContainer">
      <h1>{{ userStore.loginUserName }}</h1>
      <div class="introduction">
        <div v-if="userStore.user.introduction !== null">
          <span>{{ userStore.user.introduction }}</span>
        </div>
        <div v-else><span>운동 각오 한 마디를 넣어주세요!</span></div>
      </div>
      <div class="level">
        <span id="my-level" style="font-size: 24px; font-weight: 700">Lv. {{ Math.floor(userStore.user.exp / 100)
        }}</span>
        <span v-if="Math.floor(userStore.user.exp / 100) === 0">운동 응애</span>
        <span v-else-if="Math.floor(userStore.user.exp / 100) === 1">운동 꼬맹</span>
        <span v-else-if="Math.floor(userStore.user.exp / 100) === 2">운동 엉아</span>
        <span v-else-if="Math.floor(userStore.user.exp / 100) === 3">운동 횽님</span>
        <span v-else-if="Math.floor(userStore.user.exp / 100) === 4">운동 선생</span>
      </div>
    </div>
    <div class="exp">
      <div class="exp-bar">
        <div class="exp-progress" :style="{ width: `${expPercentage}%` }">
          {{ expPercentage }}
        </div>
      </div>
      <div class="exp-status">
        <p>현재 경험치 {{ userStore.user.exp }} XP</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, onMounted } from "vue";
import { useUserStore } from "@/stores/user";

const userStore = useUserStore();

onMounted(() => {
  userStore.getInfo(userStore.loginUserId);
})

const expPercentage = computed(() => userStore.user.exp % 100);
</script>

<style scoped>
.exp-bar {
  width: 90%;
  height: 20px;
  background-color: #e0e0e0;
  border-radius: 10px;
  overflow: hidden;
  margin: 10px;
}

.exp-progress {
  height: 100%;
  background-color: #ffa500;
  border-radius: 10px 0 0 10px;
  transition: width 0.3s ease;
  align-content: center;
  color: white;
  align-content: center;
  font-size: 12px;
}

.exp {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

#myProfile {
  padding: 3% 7%;
  display: flex;
  flex-direction: column;
}

.myProfileContainer {
  display: flex;
  justify-content: space-between;
  margin: 20px;
}

.myProfileContainer span {
  margin: 5px;
  align-items: center;
}

.level {
  background-color: #ffa500;
  color: aliceblue;
  padding: 13px;
  border-radius: 30px;
  margin: 0 10px;
}

h1 {
  font-size: 32px;
  justify-content: center;
  align-content: center;
  margin: 0 10px;
}

.exp-status {
  width: 90%;
  height: 20px;
  margin: 10px;
}

.exp-status p {
  text-align: left;
}

.introduction {
  display: block;
  width: 300px;
  align-content: center;
  border: gray 0.5px solid;
  border-radius: 50px;
}
</style>
