<template>
  <div>
    <div id="allChallengeContainer">
      <h1>요즘 뜨는 챌린지</h1>
      <button v-show="checkLogin">
        <RouterLink :to="{ name: 'challengeCreate' }">챌린지 등록하기</RouterLink>
      </button>
    </div>
    <div id="challengeContainer">
      <div v-for="challenge in challengeList">
        <div class="card-challengeList" style="width: 18rem" @click="goDetail(challenge.id)">
          <div class="card-body">
            <div class="img-container">
              <img :src="challenge.imageUrl" class="card-img-top" alt="" />
              <div class="img-overlay">▶</div>
            </div>

            <h5 class="card-title">{{ challenge.title }}</h5>
            <div class="contentContainer">
              <p v-if="challenge.purpose === 'DIET'">다이어트</p>
              <p v-else-if="challenge.purpose === 'POSTURE_CORRECTION'">
                자세교정
              </p>
              <p v-else>근력강화</p>

              <div>
                <p v-if="challenge.level === 1">⭐</p>
                <p v-else-if="challenge.level === 2">⭐⭐</p>
                <p v-else>⭐⭐⭐</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import router from "@/router";
import { computed, onMounted } from "vue";
import { useChallengeStore } from "@/stores/challenge";
import { useUserStore } from "@/stores/user";

const challengeStore = useChallengeStore();
const userStore = useUserStore();

onMounted(() => {
  challengeStore.getAllChallenge();
});

const challengeList = computed(() => {
  return challengeStore.allChallengeList.splice(0, 4);
});

const goDetail = function (id) {
  router.push({ name: "challengeDetail", params: { id: id } });
};

const checkLogin = computed(() => userStore.isLoggedIn)
</script>

<style scoped>
h1 {
  margin: 10px;
  padding: 10px;
  font-size: 21px;
}

#allChallengeContainer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

#challengeContainer {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-evenly;
  margin-bottom: 20px;
}

.card-challengeList {
  display: flex;
  flex-direction: column;
  width: calc(33.333% - 20px);
  /* Ensure three cards per row with spacing */
  border: 1px solid #ccc;
  border-radius: 20px;
  overflow: hidden;
  background-color: #fff;
}

.card-challengeList:hover {
  transform: scale(1.1);
  transition: transform 0.3s ease;
}

.card-body {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 15px;
  text-align: left;
}

.card-body p {
  margin: 10px;
  align-items: left;
}

.card-title {
  margin: 10px;
}

.contentContainer {
  display: flex;
  justify-content: space-between;
}

.img-container {
  display: flex;
  position: relative;
  width: 100%;
  height: 140px;
  overflow: hidden;
  margin: 10px;
  border-radius: 10px;
  align-items: center;
}

.card-img-top {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.img-overlay {
  display: flex;
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  color: #f2f2f2;
  align-items: center;
  justify-content: center;
  text-align: center;
}

button {
  padding: 10px 20px 10px;
  background-color: #ff8911;
  border-radius: 50px;
  border: none;
}

button a {
  font-size: 14px;
  text-decoration: none;
  color: #f2f2f2;
  padding: 0;
  margin: 0;
}
</style>
