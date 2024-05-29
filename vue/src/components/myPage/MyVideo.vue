<template>
  <div>
    <h1>내가 등록한 영상</h1>
  </div>
  <div v-if="userChallengeStore.myCreateVideo !== 0">
    <div v-for="video in userChallengeStore.myCreateVideo">
      <div class="my-Video">
        <img :src="video.imageUrl" @click="goDetail(video.id)" />
        <div class="my-Video-Content">
          <span @click="goDetail(video.id)">{{ video.title }}</span>
          <p v-if="video.purpose === 'DIET'">다이어트</p>
          <p v-else-if="video.purpose === 'POSTURE_CORRECTION'">자세교정</p>
          <p v-else>근력강화</p>
        </div>
      </div>
    </div>
  </div>
  <div v-else><span>등록한 영상이 없습니다.</span></div>
</template>

<script setup>
import { useUserStore } from "@/stores/user";
import { useUserChallengeStore } from "@/stores/userChallenge";
import { useVideoStore } from "@/stores/video";
import { onMounted, ref } from "vue";
import { useRoute, useRouter } from "vue-router";

const userStore = useUserStore();
const userChallengeStore = useUserChallengeStore();

const route = useRoute();
const router = useRouter();

onMounted(() => {
  const userId = userStore.loginUserId;

  const query = {
    table: "video",
  };

  userChallengeStore.findRegistVideo(userId, query);
});

const goDetail = function (id) {
  router.push({ name: "videoDetail", params: { id: id } });
};
</script>

<style scoped>
.my-Video {
  display: flex;
  justify-content: space-evenly;
  margin: 10px;
  padding: 20px;
}

.my-Video img {
  width: 320px;
  height: 180px;
  object-fit: cover;
  border-radius: 20px;
}

.my-Video-Content {
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
}

.my-Video-Content span {
  font-size: 18px;
  font-weight: 700;
  padding: 10px;
  margin: 10px;
  display: block;
  width: 500px;
}

h1 {
  font-size: 24px;
  font-weight: 800;
  margin: 20px;
  padding: 20px;
}
</style>
