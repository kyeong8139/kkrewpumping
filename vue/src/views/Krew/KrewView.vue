<template>
  <div>
    <h1>크루와 함께 도전하기</h1>
    <hr />
    <div class="krewList">
      <div v-if="krewStore.crewList.length == 0">
        <span> 현재 참여 중인 크루가 없습니다.</span>
      </div>
      <div v-else>
        <div v-for="krew in krewStore.crewList">
          <div class="krewContainer">
            <span>{{ krew.name }}</span>
            <button id="enterKrew" @click="enterKrew(krew.id, krew.name)">
              가입
            </button>
          </div>
        </div>
      </div>
    </div>

    <div class="btnContainer">
      <button @click="createKrew">크루 만들기</button>
      <button @click="startChallenge">혼자 참여하기</button>
    </div>
    <RouterView />
  </div>
</template>

<script setup>
import { useKrewStore } from "@/stores/krew";
import { useUserStore } from "@/stores/user";
import { useChallengeStore } from "@/stores/challenge";
import { useRouter, useRoute } from "vue-router";
import { onMounted } from "vue";

const krewStore = useKrewStore();
const userStore = useUserStore();
const challengeStore = useChallengeStore();

const route = useRoute();
const router = useRouter();

onMounted(() => {
  const challengeId = route.params.id;
  challengeStore.getChallenge(challengeId);
  krewStore.getCrewList(challengeId);
});

////////혼자챌린지참여
const startChallenge = function () {
  const userId = userStore.loginUserId;
  const challengeId = route.params.id;

  challengeStore.startChallenge(userId, challengeId);
  router.push({ name: "challengeDoingList" });
};

///////크루에 가입하기
const enterKrew = function (krewId, krewname) {
  const userId = userStore.loginUserId;
  const user = {
    id: userId,
  };
  krewStore.krewTogether(krewId, user);

  alert(`${krewname}에 가입하셨습니다!`);
  router.push({ name: "challengeDoingList" });
};

///////크루 등록하기
const createKrew = function () {
  router.push({ name: "createKrew", params: { id: route.params.id } });
};
</script>

<style scoped>
.krewContainer {
  display: flex;
  justify-content: space-between;
  margin: 20px;
  padding: 10px;
  border: 0.5px solid gray;
  border-radius: 30px;
}

.krewContainer span {
  display: block;
  width: 300px;
  align-content: center;
}

.krewList {
  display: flex;
  justify-content: space-around;
}

button {
  margin: 0 20px;
  padding: 10px 30px 10px;
  font-size: 12px;
  color: white;
  background-color: #ff8911;
  border-radius: 30px;
  border: 2px solid transparent;
}

h1 {
  font-size: 24px;
  font-weight: 800;
  margin: 10px;
  padding: 10px;
}

#enterKrew {
  background-color: white;
  border: #ff8911 solid 2px;
  border-radius: 30px;
  color: black;
  font-size: 14px;
  padding: 5px 10px;
}

button:hover {
  background-color: #7f27ff;
}
</style>
