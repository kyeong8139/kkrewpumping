<template>
  <div>
    <legend>영상을 등록해주세요!</legend>
    <div id="createForm">
      <div class="input" id="titleForm">
        <label for="title">제목</label>
        <div class="inputbox">
          <input id="title" name="title" v-model="video.title" type="text" />
        </div>
      </div>

      <div class="input">
        <label for="keyword">영상</label>
        <div class="inputbox">
          <input id="keyword" name="keyword" placeholder="유튜브 영상 제목" v-model="keyword" type="text"
            @keyup.enter="searchYoutubeVideo" />
          <button @click.prevent="searchYoutubeVideo">유튜브 검색</button>
        </div>
      </div>

      <div id="videoListforYoutube">
        <ul v-for="video in youtubeStore.youtubeVideoList" :key="video.id.videoId">
          <li class="showList">
            <div>
              <img :src="video.snippet.thumbnails.default.url" />
            </div>
            <p>{{ video.snippet.channelTitle }}</p>
            <p>{{ video.snippet.title }}</p>
            <button @click.prevent="setVideoUrl(video.id.videoId)">+</button>
          </li>
        </ul>
      </div>

      <div class="input">
        <label for="url">링크</label>
        <div class="inputbox">
          <input id="url" name="url" readonly v-model="video.videoUrl" type="text" />
        </div>
      </div>

      <div class="input">
        <label for="purpose">목적</label>
        <div class="inputbox">
          <input type="radio" id="diet" name="purpose" value="DIET" v-model="video.purpose" />
          <label for="diet">다이어트</label>
          <input type="radio" id="posture_correction" name="purpose" value="POSTURE_CORRECTION" v-model="video.purpose" />
          <label for="posture_correction">자세교정</label>
          <input type="radio" id="muscular_strength" name="purpose" value="MUSCULAR_STRENGTH" v-model="video.purpose" />
          <label for="muscular_strength">근력강화</label>
        </div>
      </div>

      <div class="input">
        <label for="supplies">준비물</label>
        <div class="inputbox">
          <input type="checkbox" id="foam_roller" name="supplies" value="폼롤러" v-model="video.supplies" />
          <label for="foam_roller">폼롤러</label>
          <input type="checkbox" id="dumbbell" name="supplies" value="아령" v-model="video.supplies" />
          <label for="dumbbell">아령</label>
          <input type="checkbox" id="yoga_mat" name="supplies" value="요가매트" v-model="video.supplies" />
          <label for="yoga_mat">요가매트</label>
        </div>
      </div>

      <div class="input">
        <label for="description">설명</label>
        <div class="inputbox">
          <input type="text" id="description" name="description" readonly v-model="video.description" />
          <button @click.prevent="generateDescription">AI 설명 생성</button>
        </div>
      </div>

      <button type="submit" @click="createVideo">제출</button>
    </div>
  </div>
</template>

<script setup>
import { useVideoStore } from "@/stores/video.js";
import { useUserStore } from "@/stores/user.js";
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useAIStore } from "@/stores/gemini";
import { useYoutubeStore } from "@/stores/youtube";

const store = useVideoStore();
const userStore = useUserStore();

const AIstore = useAIStore();

const video = ref({
  title: "",
  videoUrl: "",
  purpose: "",
  supplies: [],
  writer: userStore.loginUserId,
  description: "",
});

const createVideo = function () {
  store.createVideo(video.value);
};

// 유튜브 영상 검색
const keyword = ref("");
const youtubeStore = useYoutubeStore();

const searchYoutubeVideo = function () {
  youtubeStore.searchYoutubeVideo(keyword.value);
};

const setVideoUrl = function (videoId) {
  const url = "https://www.youtube.com/watch?v=" + videoId;

  video.value.videoUrl = url;
};

// AI 설명 생성
const generateDescription = async function () {
  video.value.description = "AI가 설명을 생성 중에 있습니다.";
  video.value.description = await AIstore.generateDescription(
    video.value.videoUrl
  );
};

onMounted(() => {
  keyword.value = "";
  youtubeStore.youtubeVideoList = [];
});
</script>

<style scoped>
button {
  padding: 10px 30px 10px;
  font-size: 12px;
  color: white;
  background-color: #ff8911;
  border-radius: 30px;
  border: 2px solid transparent;
}

#createForm {
  margin: 1em 3em 1em;
  padding: 5em;
  border: 1px solid #524c42;
  border-radius: 100px;
}

.input {
  display: flex;
  justify-content: space-evenly;
  margin: 0.5em;
  padding: 0.5em;
}

.input input[type="text"] {
  width: 50%;
  padding: 0.5em;
  margin-top: 0.5em;
  border: 1px solid #ccc;
  border-radius: 50px;
}

.input input[type="radio"],
.input input[type="checkbox"] {
  margin-right: 0.5em;
}

.inputbox {
  display: flex;
  width: 350px;
}

.input label {
  display: block;
  width: 80px;
  align-content: center;
}

#titleForm input {
  width: 300px;
  padding: 0.5em;
  border: 1px solid #ccc;
  border-radius: 5px;
}

#videoListforYoutube {
  display: flex;
  flex-direction: column;
}

.showList {
  display: flex;
  justify-content: space-between;
  list-style-type: none;
  text-align: center;
  align-items: center;
  margin: 10px 0;
}

.showList img {
  width: 200px;
  height: 110px;
  margin: 0 auto;
  object-fit: cover;
  border-radius: 20px;
}

.showList p {
  margin: 5px 0;
  display: block;
  width: 200px;
  line-height: 150%;
}
</style>
