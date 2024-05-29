<template>
  <div>
    <h1>나만의 크루를 만들어볼까요?</h1>
    <div id="krewFoam">
      <div class="createKrew">
        <span>크루 이름</span>
        <input type="text" v-model="newKrew.name" @keyup.enter="makeKrew" />
        <button @click="makeKrew">등록</button>
      </div>
      <div id="willDoChallenge">
        <div class="nowChallenge">
          <p style="font-weight: 700;">{{ nowChallenge.title }}</p>
          <p v-if="nowChallenge.purpose === 'DIET'">다이어트</p>
          <p v-else-if="nowChallenge.purpose === 'POSTURE_CORRECTION'">
            자세교정
          </p>
          <p v-else>근력강화</p>
          <p>{{ nowChallenge.period }} 일동안 도전!</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useChallengeStore } from "@/stores/challenge";
import { useKrewStore } from "@/stores/krew";
import { useUserStore } from "@/stores/user";
import { onMounted, ref } from "vue";
import { useRoute, useRouter } from "vue-router";

const challengeStore = useChallengeStore();
const userStore = useUserStore();
const krewStore = useKrewStore();

const route = useRoute();
const router = useRouter();
const challengeId = route.params.id;

onMounted(() => {
  challengeStore.getChallenge(challengeId);
});

const nowChallenge = challengeStore.challenge;

///////크루등록하기
const userId = userStore.loginUserId;

const newKrew = ref({
  name: "",
});

const makeKrew = function () {
  krewStore.makeNewKrew(challengeId, newKrew.value);
  router.push({ name: "challengeDoingList" });
};
</script>

<style scoped>
#krewFoam {
  margin: 20px 20%;
  padding: 10px 10%;
  border: 0.5px solid gray;
  border-radius: 20px;
}

h1 {
  font-size: 21px;
  margin: 20px;
  padding: 10px;
}

.createKrew {
  margin: 10px 5px;
  padding: 20px 10%;
  border-bottom: 0.5px solid gray;
}

.createKrew span,
.createKrew input,
.createKrew button {
  margin: 5px;
  padding: 5px;
  align-content: center;
}

.createKrew input {
  border-radius: 20px;
}

.createKrew button {
  padding: 5px 15px 5px;
  font-size: 12px;
  color: white;
  background-color: #ff8911;
  border-radius: 30px;
  border: 2px solid transparent;
}

.nowChallenge {
  display: flex;
  flex-direction: column;
}

.nowChallenge p {
  margin: 10px;
  padding: 0 20%;
}
</style>
