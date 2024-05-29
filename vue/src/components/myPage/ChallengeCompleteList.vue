<template>
  <div>
    <h1>완료된 챌린지</h1>
    <div v-if="userChallengeStore.myCompleteChallenge.length === 0">
      <span>완료한 챌린지가 없습니다.</span>
    </div>
    <div v-else>
      <div v-for="complete in userChallengeStore.myCompleteChallenge" :key="complete.id">
        <div class="complete">
          <img class="completeImg" :src="complete.challenge.videos[0].imageUrl" alt="끝난 챌린지 화면입니다." />

          <div class="completeStatus">
            <div>
              <p>{{ complete.challenge.title }}</p>
              <p>시작일 : {{ complete.startDate }}</p>
            </div>
            <img src="/src/assets/img/STAMP.png" alt="" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useUserStore } from "@/stores/user";
import { useUserChallengeStore } from "@/stores/userChallenge";
import { onMounted, ref } from "vue";
import router from "@/router";

const userStore = useUserStore();
const userChallengeStore = useUserChallengeStore();

onMounted(() => {
  const userId = userStore.loginUserId;

  userChallengeStore.findCompleteChallenge(userId);
});
</script>

<style scoped>
h1 {
  font-size: 24px;
  font-weight: 700;
  margin: 20px 0;
  padding: 20px;
}

.complete {
  display: flex;
  justify-content: space-around;
  padding: 20px;
  margin: 10px;
  border: 0.5px solid gray;
  border-radius: 20px;
}

.completeStatus {
  display: flex;
  justify-content: space-around;
  align-items: center;
  padding: 10px;
}

.completeStatus img {
  width: 100px;
  height: 100px;
}

.completeImg {
  width: 320px;
  height: 180px;
  object-fit: cover;
  border-radius: 20px;
  filter: grayscale(1);
}
</style>
