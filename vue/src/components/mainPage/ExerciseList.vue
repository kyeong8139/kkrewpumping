<template>
  <div>
    <nav>
      <div class="buttonContainer">
        <button class="purposeCheck" @click="findExerciseDIET">다이어트</button>
        <span> | </span>
        <button class="purposeCheck" @click="findExercisePosture">
          자세교정
        </button>
        <span> | </span>
        <button class="purposeCheck" @click="findExerciseMuscle">
          근력강화
        </button>
      </div>
      <ExerciseVideoRecommand></ExerciseVideoRecommand>
      <button v-show="checkLogin" class="add">
        <RouterLink :to="{ name: 'videoCreate' }">운동 영상 추가하기</RouterLink>
      </button>
    </nav>
    <div id="videoContainer">
      <div v-for="video in videoList" :key="video.id">
        <div class="card-videoList" style="width: 18rem" @click="goDetail(video.id)">
          <div class="card-body">
            <div class="img-container">
              <img :src="video.imageUrl" class="card-img-top" alt="" />
              <div class="img-overlay">▶</div>
            </div>

            <h5 class="card-title">{{ video.title }}</h5>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useVideoStore } from "@/stores/video";
import { ref, onMounted, computed } from "vue";
import ExerciseVideoRecommand from "../exercisevideo/ExerciseVideoRecommand.vue";
import { useAIStore } from "@/stores/gemini";
import router from "@/router";
import { useUserStore } from "@/stores/user";

const videoStore = useVideoStore();
const AIStore = useAIStore();
const userStore = useUserStore();

const findExerciseDIET = function () {
  AIStore.purpose = "DIET";
  const query = {
    key: "purpose",
    word: "DIET",
  };
  videoStore.searchListPurpose(query);
};

const findExercisePosture = function () {
  AIStore.purpose = "POSTURE_CORRECTION";
  const query = {
    key: "purpose",
    word: "POSTURE_CORRECTION",
  };
  videoStore.searchListPurpose(query);
};

const findExerciseMuscle = function () {
  AIStore.purpose = "MUSCULAR_STRENGTH";

  const query = {
    key: "purpose",
    word: "MUSCULAR_STRENGTH",
  };
  videoStore.searchListPurpose(query);
};

const videoList = computed(() => {
  return videoStore.videoListForPurpose.splice(0, 4);
});

onMounted(() => {
  findExerciseDIET();
});

const goDetail = function (id) {
  router.push({ name: "videoDetail", params: { id: id } });
};

const checkLogin = computed(() => userStore.isLoggedIn)
</script>

<style scoped>
nav {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

nav span {
  margin: 0 5px;
}

.purposeCheck {
  padding: 10px 20px 10px;
  background-color: white;
  border-radius: 50px;
  border: none;
  font-size: 18px;
  font-weight: 700;
  color: gray;
}

.purposeCheck:hover {
  font-weight: bolder;
  color: #ff8911;
}

.add {
  padding: 10px 20px 10px;
  background-color: #ff8911;
  border-radius: 50px;
  border: none;
}

.add a {
  font-size: 14px;
  text-decoration: none;
  color: #f2f2f2;
  padding: 0;
  margin: 0;
}

#videoContainer {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-evenly;
  margin-bottom: 20px;
}

.card-videoList {
  display: flex;
  flex-direction: column;
  width: calc(33.333% - 20px);
  /* Ensure three cards per row with spacing */
  border: 1px solid #ccc;
  border-radius: 20px;
  overflow: hidden;
  background-color: #fff;
}

.card-videoList:hover {
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
</style>
