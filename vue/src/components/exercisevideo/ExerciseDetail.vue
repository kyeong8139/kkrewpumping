<template>
  <div>
    <div class="videoDetailContainer">
      <main>
        <iframe width="620" height="375" :src="embedURL" title="YouTube video player" frameborder="0"
          allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
          referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>
      </main>

      <aside>
        <h3>{{ store.video.title }}</h3>
        <div class="videoContent">
          <span> 목적 </span>
          <p v-if="store.video.purpose === 'DIET'">다이어트</p>
          <p v-else-if="store.video.purpose === 'POSTURE_CORRECTION'">
            자세교정
          </p>
          <p v-else>근력강화</p>
        </div>
        <div class="videoContent">
          <span>준비물</span>
          <div v-if="store.video.supplies && store.video.supplies.length == 0">
            <p>없음</p>
          </div>
          <div v-for="supply in store.video.supplies" :key="supply.id">
            <p>{{ supply }}</p>
          </div>
        </div>

        <div class="videoDescription">
          {{ store.video.description }}
        </div>

        <div class="buttonBox">
          <button v-show="checkUser" @click="updateVideo">수정</button>
          <button v-show="checkUser" @click="deleteVideo">삭제</button>
        </div>
      </aside>
    </div>
  </div>
</template>

<script setup>
import { useVideoStore } from "@/stores/video";
import { onMounted, computed } from "vue";
import { useRoute, useRouter } from "vue-router";
import axios from "axios";
import { useUserStore } from "@/stores/user";

const store = useVideoStore();
const userStore = useUserStore();

const route = useRoute();
const router = useRouter();
onMounted(() => {
  //비디오 번호 어디에 있나
  store.getVideo(route.params.id);
});

const videoURL = computed(() => {
  return store.video.videoUrl;
});

// YouTube URL에서 비디오 ID를 추출하는 함수
function extractVideoId(inputUrl) {
  const url = String(inputUrl);
  const index = url.indexOf("=");
  return index !== -1 ? url.substring(index + 1) : url;
}

const embedURL = computed(() => {
  const url = videoURL.value;
  const videoId = extractVideoId(String(url));

  return "https://www.youtube.com/embed/" + videoId;
});

const deleteVideo = function () {
  axios
    .delete(`http://13.211.180.178:8080/video/${route.params.id}`)
    .then(() => {
      router.push({ name: "home" });
    })
    .catch(() => {
      alert("챌린지가 등록된 비디오는 삭제할 수 없습니다.");
    });
};

const updateVideo = function () {
  router.push({ name: "videoUpdate", id: route.params.id });
};

const checkUser = computed(() => {
  return userStore.loginUserId === store.video.writer;
});
</script>

<style scoped>
.videoDetailContainer {
  display: flex;
  justify-content: space-evenly;
  border: 0.7px solid gray;
  border-radius: 100px;
  padding: 40px;
  margin: 10px;
}

.videoContent {
  display: flex;
}

.videoContent span {
  display: block;
  width: 120px;
  font-size: 16px;
  text-align: left;
  justify-items: center;
  align-content: center;
}

.videoContent p {
  display: block;
  width: 120px;
  font-size: 16px;
  text-align: left;
  justify-items: center;
  align-content: center;
}

.videoDescription {
  width: 75%;
  font-size: 16px;
  line-height: 180%;
  text-align: justify;
  padding: 20px;
}

main {
  margin: 0px 10px;
}

aside {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-evenly;
  margin: 0px 10px;
}

h3 {
  font-size: 24px;
  font-weight: 800;
  margin: 20px;
}

button {
  font-size: 14px;
  text-decoration: none;
  color: #f2f2f2;
  padding: 10px 20px 10px;
  margin: 0px 15px;
  background-color: #ff8911;
  border-radius: 50px;
  border: none;
}
</style>
