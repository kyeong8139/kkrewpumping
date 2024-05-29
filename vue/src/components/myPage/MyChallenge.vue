<template>
  <div>
    <h1>내가 등록한 챌린지</h1>
    <div v-if="userChallengeStore.myCreateChallenge.length === 0">
      <span>등록한 챌린지가 없습니다.</span>
    </div>
    <div v-else>
      <div v-for="challenge in userChallengeStore.myCreateChallenge">
        <div class="my-challenge">
          <img :src="challenge.imageUrl" alt="" @click="goDetail(challenge.id)" />
          <div class="my-challenge-Content">
            <span>{{ challenge.title }}</span>
            <p v-if="challenge.purpose === 'DIET'">다이어트</p>
            <p v-else-if="challenge.purpose === 'POSTURE_CORRECTION'">
              자세교정
            </p>
            <p v-else>근력강화</p>
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

  const query = {
    table: "challenge",
  };

  userChallengeStore.findRegistChallenge(userId, query);
});

const goDetail = function (id) {
  router.push({ name: "challengeDetail", params: { id: id } });
};
</script>

<style scoped>
h1 {
  font-size: 24px;
  font-weight: 800;
  margin: 20px;
  padding: 20px;
}

.my-challenge {
  display: flex;
  justify-content: space-between;
  margin: 10px;
  padding: 20px 20%;
}

.my-challenge img {
  width: 320px;
  height: 180px;
  object-fit: cover;
  border-radius: 20px;
}

.my-challenge-Content {
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
}

.my-challenge-Content span {
  font-size: 18px;
  font-weight: 700;
  padding: 10px;
  margin: 10px;
}
</style>
