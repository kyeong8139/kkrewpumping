<template>
  <div>
    <h2>챌린지를 등록해주세요</h2>
    <div id="createChallengeFoam">
      <div>
        <label for="title">제목</label>
        <input type="text" id="title" v-model="challenge.title" />
      </div>
      <div>
        <span>난이도</span>
        <div class="star">
          <div v-for="n in 3" :key="n">
            <label :for="'rate' + n">
              <span :class="{
                filled: n <= challenge.level,
                empty: n > challenge.level,
              }" @click="setLevel(n)">★</span>
            </label>
            <input type="radio" :name="'rate'" :id="'rate' + n" :value="n" v-model="challenge.level"
              style="display: none" />
          </div>
        </div>
      </div>
      <div>
        <span>평균 운동시간</span>
        <input type="text" id="time" v-model="challenge.time" />
      </div>
      <div>
        <span>목적</span>
        <div>
          <input type="radio" id="diet" name="purpose" value="DIET" v-model="challenge.purpose" />
          <label for="diet">다이어트</label>
        </div>

        <div>
          <input type="radio" id="posture_correction" name="purpose" value="POSTURE_CORRECTION"
            v-model="challenge.purpose" />
          <label for="posture_correction">자세교정</label>
        </div>

        <div>
          <input type="radio" id="muscular_strength" name="purpose" value="MUSCULAR_STRENGTH"
            v-model="challenge.purpose" />
          <label for="muscular_strength">근력강화</label>
        </div>
      </div>
      <div>
        <span>기간</span>
        <input type="radio" id="aweek" name="period" value="7" v-model="challenge.period" />
        <label for="aweek">1주</label>

        <input type="radio" id="twoweeks" name="period" value="14" v-model="challenge.period" />
        <label for="twoweeks">2주</label>

        <input type="radio" id="fourweeks" name="period" value="28" v-model="challenge.period" />
        <label for="fourweeks">4주</label>
      </div>
      <div>
        <span>준비물</span>
        <input type="checkbox" id="foam_roller" name="supplies" value="폼롤러" v-model="challenge.supplies" />
        <label for="foam_roller">폼롤러</label>

        <input type="checkbox" id="dumbbell" name="supplies" value="아령" v-model="challenge.supplies" />
        <label for="dumbbell">아령</label>

        <input type="checkbox" id="yoga_mat" name="supplies" value="요가매트" v-model="challenge.supplies" />
        <label for="yoga_mat">요가매트</label>
      </div>
      <hr />
      <p class="putvideoTitle" style="font: bold">챌린지 영상을 넣어주세요</p>
      <div>
        <div id="searchvideoForm">
          <input type="text" v-model="searchInfo.word" @keyup.enter="searchList" placeholder="제목으로 검색됩니다." />
          <button @click="searchList">검색</button>
        </div>
      </div>
      <ul id="searchVideoList" v-for="video in selectedVideos" :key="video.id">
        <li class="showList">
          <div>
            <img :src="video.imageUrl" alt="" />
          </div>
          <div class="videoContent">
            <p>{{ video.title }}</p>
            <p v-if="video.purpose === 'DIET'">다이어트</p>
            <p v-else-if="video.purpose === 'POSTURE_CORRECTION'">자세교정</p>
            <p v-else>근력강화</p>
            <div>
              <div v-if="video.supplies && video.supplies.length == 0">
                <p>준비물 없음</p>
              </div>
              <div v-for="supply in video.supplies" :key="supply.id">
                <p>준비물: {{ supply }}</p>
              </div>
            </div>
          </div>
          <button @click="addVideo(video)">+</button>
        </li>
      </ul>
      <div id="videoListforChallenge">
        <h5>챌린지에 등록한 영상 리스트</h5>
        <div v-if="addedVideos && addedVideos.length == 0">
          아직 등록한 영상이 없습니다.
        </div>
        <ul v-for="(video, index) in addedVideos" :key="video.id">
          <li class="showList">
            <img :src="video.imageUrl" alt="" />
            <p>{{ video.title }}</p>
            <p v-if="video.purpose === 'DIET'">다이어트</p>
            <p v-else-if="video.purpose === 'POSTURE_CORRECTION'">자세교정</p>
            <p v-else>근력강화</p>
            <div>
              <p>준비물</p>
              <div v-if="video.supplies && video.supplies.length == 0">
                <p>없음</p>
              </div>
              <div v-else>
                <div v-for="supply in video.supplies" :key="supply.id">
                  <p>{{ supply }}</p>
                </div>
              </div>
            </div>
            <button @click="deleteVideoOnList(index)">❌</button>
          </li>
        </ul>
      </div>
    </div>
    <button @click="createChallenge">등록</button>
  </div>
</template>

<script setup>
import { ref, computed } from "vue";
import { useRouter } from "vue-router";
import { useUserStore } from "@/stores/user";
import { useVideoStore } from "@/stores/video";
import { useChallengeStore } from "@/stores/challenge";

const userStore = useUserStore();

const challenge = ref({
  title: "",
  level: "",
  purpose: "",
  period: "",
  time: "",
  supplies: [],
  videos: [],
  writer: userStore.loginUserId,
});

//////////밑은 검색처리///////////
const videoStore = useVideoStore();

const searchInfo = ref({
  key: "video",
  word: "",
});

const searchList = function () {
  const query = {
    key: searchInfo.value.key,
    word: searchInfo.value.word,
  };

  videoStore.searchList(query);
};

const selectedVideos = computed(() => {
  return videoStore.videoList.slice(0, 5); //5개만보이게함
});

///////////영상추가
const addedVideos = ref([]);
const addedVideosId = ref([]);

// 영상을 추가하는 함수
const addVideo = function (video) {
  addedVideos.value.push(video);
  addedVideosId.value.push({ id: video.id });
  challenge.value.videos = addedVideosId.value;

  // 검색 데이터 초기화
  // searchInfo.value.word = "";
  // videoStore.videoList = [];
};
//검색초기화
searchInfo.value.word = "";
videoStore.videoList = [];

const deleteVideoOnList = function (index) {
  addedVideos.value.splice(index, 1); //해당 인덱스에서 하나 삭제
  addedVideosId.value.splice(index, 1);
};

const challengeStore = useChallengeStore();

// 챌린지를 생성하는 함수
const createChallenge = function () {
  challengeStore.createChallenge(challenge.value);
};

/////난이도
const setLevel = function (level) {
  challenge.value.level = level;
};
</script>

<style scoped>
#createChallengeFoam {
  border: 1px solid #383838;
  border-radius: 100px;
  padding: 20px;
  margin: 20px;
  display: flexbox;
  flex-direction: column;
  align-items: center;
  text-align: center;
}

#createChallengeFoam>div {
  margin-bottom: 15px;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

#createChallengeFoam>div>span {
  padding-right: 5px;
  margin-right: 5px;
}

.videoContent {
  display: flex;
  justify-content: space-between;

}

label {
  margin-right: 10px;
}

input[type="text"],
input[type="radio"],
input[type="checkbox"],
button {
  margin: 5px;
}

.putvideoTitle {
  margin: 20px 0 10px;
}

.showList {
  display: flex;
  justify-content: space-between;
  list-style-type: none;
  text-align: center;
  align-items: center;
  margin: 10px 0;
  padding: 0 10%;
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

.star {
  display: flex;
  align-items: center;
  justify-content: center;
}

.star label {
  cursor: pointer;
}

.star .filled {
  color: #ffc700;
}

.star .empty {
  color: #ccc;
}

.star span {
  font-size: 24px;
}

#searchvideoForm {
  display: flex;
  justify-content: center;
  margin-bottom: 15px;
}

#searchvideoForm input {
  width: 200px;
  margin-right: 10px;
}

h5 {
  text-align: center;
  width: 100%;
  margin: 10px;
}

button {
  padding: 10px 20px 10px;
  font-size: 12px;
  color: white;
  background-color: #ff8911;
  border-radius: 30px;
  border: 2px solid transparent;
}

#showChallengeList {
  display: flex;
  justify-content: center;
  margin: 15px;
  text-align: center;
}

input[type="text"] {
  border-radius: 50px;
  border: #666 solid 0.7px;
  padding: 10px;
}

input[type="radio"] {
  display: none;
}

input[type="radio"]+label {
  position: relative;
  padding-left: 30px;
  cursor: pointer;
  display: inline-block;
  line-height: 20px;
  color: #666;
}

input[type="radio"]+label:before {
  content: "";
  position: absolute;
  left: 0;
  top: 0;
  width: 10px;
  height: 10px;
  border: 1px solid #666;
  border-radius: 50%;
  background: #fff;
}

input[type="radio"]:checked+label:before {
  border-color: #ffa500;
  background: #ffa500;
}

input[type="radio"]:checked+label:after {
  content: "";
  position: center;
  border-radius: 50%;
  background: #fff;
}

input[type="checkbox"] {
  display: none;
}

input[type="checkbox"]+label {
  position: relative;
  padding-left: 30px;
  cursor: pointer;
  display: inline-block;
  line-height: 20px;
  color: #666;
}

input[type="checkbox"]+label:before {
  content: "";
  position: absolute;
  left: 0;
  top: 0;
  width: 10px;
  height: 10px;
  border: 1px solid #666;
  background: #fff;
}

input[type="checkbox"]:checked+label:before {
  border-color: #ffa500;
  background: #ffa500;
}

input[type="checkbox"]:checked+label:after {
  content: "";
  position: center;
  border-radius: 50%;
  background: #fff;
}

#videoListforChallenge {
  display: flex;
  flex-direction: column;
}
</style>
