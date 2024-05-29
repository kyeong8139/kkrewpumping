<template>
  <div>
    <h1>진행 중 챌린지</h1>
    <div v-if="userChallengeStore.doingChallengeList.length === 0">
      <span>진행 중인 챌린지가 없습니다.</span>
    </div>
    <div v-else>
      <div v-for="challenge in userChallengeStore.doingChallengeList" :key="challenge.id" class="challenge-item">
        <img :src="challenge.challenge.imageUrl" @click="goDetail(challenge.id)" />
        <div class="challenge-content">
          <p style="font-size: 21px; font-weight: 700" @click="goDetail(challenge.id)">
            {{ challenge.challenge.title }}
          </p>
          <p v-if="challenge.challenge.purpose === 'DIET'">다이어트</p>
          <p v-else-if="challenge.challenge.purpose === 'POSTURE_CORRECTION'">
            자세교정
          </p>
          <p v-else>근력강화</p>

          <span>남은 기간: {{ challenge.challenge.period }}일</span>
          <div class="progress-bar">
            <div class="progress" :style="{
              width: `${Math.floor(
                (challenge.currentCompletion /
                  challenge.challenge.videos.length) *
                100
              )}%`,
            }"></div>
          </div>
          <div class="krewBox">
            <div v-if="challenge.crewId === -1">
              <span>혼자 진행 중</span>
            </div>
            <div v-else>
              <span>현재 크루에서 {{ challenge.ranking }} 등입니다.</span>
            </div>
            <span style="color: gray">진행률:
              {{
                Math.floor(
                  (challenge.currentCompletion /
                    challenge.challenge.videos.length) *
                  100
                )
              }}%</span>
          </div>

        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import router from "@/router";
import { useUserStore } from "@/stores/user";
import { useKrewStore } from "@/stores/krew";
import { useUserChallengeStore } from "@/stores/userChallenge";
import { onMounted, onBeforeUpdate, computed } from "vue";

const userStore = useUserStore();
const userChallengeStore = useUserChallengeStore();
const userId = userStore.loginUserId;
const krewStore = useKrewStore();

onMounted(() => {
  userChallengeStore.findMyDoingChallenge(userId);
});

// onBeforeUpdate(() => {
//   userChallengeStore.findMyDoingChallenge(userId);
// });

const goDetail = function (id) {
  router.push({ name: "myChallengeDetail", params: { id: id } });
};
</script>

<style scoped>
.challenge-item {
  margin-bottom: 20px;
  display: flex;
  justify-content: space-between;
}

.challenge-item img {
  width: 480px;
  height: 250px;
  object-fit: cover;
  border-radius: 20px;
}

.challenge-content {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  width: 45%;
  padding: 5%;
}

.challenge-content p {
  text-align: left;
}

.challenge-content span {
  text-align: right;
}

.progress-bar {
  width: 100%;
  height: 20px;
  background-color: #e0e0e0;
  border-radius: 10px;
  overflow: hidden;
  margin-top: 10px;
}

.progress {
  height: 100%;
  background-color: #ffa500;
  border-radius: 10px 0 0 10px;
  transition: width 0.3s ease;
}

h1 {
  font-size: 28px;
  margin: 20px;
  padding: 10px;
}

.krewBox {
  display: flex;
  justify-content: space-between;
}
</style>
